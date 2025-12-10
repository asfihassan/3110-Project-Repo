import os
import xml.etree.ElementTree as ET

GREEN  = "\033[92m"
RED    = "\033[91m"
YELLOW = "\033[93m"
CYAN   = "\033[96m"
RESET  = "\033[0m"


def safe_input(prompt: str) -> str:
    """Ask until the user gives a non-empty answer."""
    val = input(prompt).strip()
    while not val:
        print("Input cannot be empty.")
        val = input(prompt).strip()
    return val


def list_files_in_folder(folder: str) -> None:
    """Print all files in a folder."""
    if not os.path.exists(folder):
        print(f"Folder not found: {folder}")
        return

    files = sorted(
        f for f in os.listdir(folder)
        if os.path.isfile(os.path.join(folder, f))
    )

    if not files:
        print("(No files found in this folder)")
        return

    print("Files in this folder:")
    for name in files:
        print(" -", name)
    print()


def normalize_xml_name(name: str) -> str:
    """Add .xml if the user forgets it."""
    name = name.strip()
    if not name.lower().endswith(".xml"):
        name = name + ".xml"
    return name


def select_xml_file(label: str, fixed_folder: str | None = None) -> str:
    """
    If fixed_folder is given, use that folder and only ask for file name.
    Otherwise ask for folder name and file name.
    Folders are assumed to be one level above src.
    """
    if fixed_folder is not None:
        folder_path = os.path.join("..", fixed_folder)
        if not os.path.isdir(folder_path):
            print(f"{RED}Folder '{folder_path}' does not exist.{RESET}")
            raise SystemExit(1)

        print(f"{label} folder: {folder_path}")
        list_files_in_folder(folder_path)

        while True:
            file_name = safe_input(
                f"{label} file name (for example ArrayReference_1_mapping.xml): "
            )
            file_name = normalize_xml_name(file_name)
            full_path = os.path.join(folder_path, file_name)
            if not os.path.isfile(full_path):
                print(f"{RED}File not found:{RESET} {full_path}\nTry again.\n")
                continue
            return full_path

    while True:
        folder_name = safe_input(f"{label} folder name (for example XML_Outputs): ")
        folder_path = os.path.join("..", folder_name)

        if not os.path.isdir(folder_path):
            print(f"{RED}Folder '{folder_path}' does not exist. Try again.{RESET}\n")
            continue

        list_files_in_folder(folder_path)

        file_name = safe_input(f"{label} file name (for example TEST16.xml): ")
        file_name = normalize_xml_name(file_name)
        full_path = os.path.join(folder_path, file_name)

        if not os.path.isfile(full_path):
            print(f"{RED}File not found:{RESET} {full_path}\nTry again.\n")
            continue

        return full_path


def load_flat_mapping(path):
    """
    Load all (orig, new) pairs from all VERSION elements.
    Returns test_name, file_attr, and a set of (orig, new).
    """
    tree = ET.parse(path)
    root = tree.getroot()

    test_name = root.attrib.get("NAME", "")
    file_attr = root.attrib.get("FILE")

    pairs = set()
    for version in root.findall("VERSION"):
        for loc in version.findall("LOCATION"):
            orig = int(loc.attrib.get("ORIG", "-1"))
            new = int(loc.attrib.get("NEW", "-1"))
            pairs.add((orig, new))

    return test_name, file_attr, pairs


def compute_offset(gold_pairs, our_pairs) -> int:
    """Guess offset so that our smallest ORIG aligns with gold's smallest ORIG."""
    gold_origs = [o for (o, _) in gold_pairs if o != -1]
    our_origs  = [o for (o, _) in our_pairs if o != -1]

    if not gold_origs or not our_origs:
        return 0

    min_gold = min(gold_origs)
    min_our  = min(our_origs)
    return min_gold - min_our


def compare_flat(gold_pairs, our_pairs):
    """
    Compare our_pairs against gold_pairs, but only for ORIG values
    that appear in gold_pairs.
    """
    gold_origs = {o for (o, _) in gold_pairs if o != -1}
    our_relevant = {p for p in our_pairs if p[0] in gold_origs}

    correct = gold_pairs & our_relevant
    missing = gold_pairs - our_relevant
    extra   = our_relevant - gold_pairs

    total_gold = len(gold_pairs)
    accuracy = (len(correct) / total_gold) * 100 if total_gold else 100.0

    return {
        "total_gold": total_gold,
        "correct": len(correct),
        "missing": len(missing),
        "extra": len(extra),
        "accuracy": accuracy,
        "missing_details": sorted(list(missing)),
        "extra_details": sorted(list(extra)),
    }


def print_flat_results(gold_label, our_label, stats, offset):
    print("\nXML comparison (only lines in professor's file)")
    print("----------------------------------------------")
    print(f"Professor file : {gold_label}")
    print(f"Our file       : {our_label}")
    print(f"Guessed offset : {offset}\n")

    acc_str = f"{stats['accuracy']:.2f}%"
    if stats["accuracy"] == 100.0:
        color = GREEN
    elif stats["accuracy"] >= 70:
        color = YELLOW
    else:
        color = RED

    print(f"Gold mappings : {stats['total_gold']}")
    print(f"{GREEN}Correct      : {stats['correct']}{RESET}")
    print(f"{RED}Missing      : {stats['missing']}{RESET}")
    print(f"{YELLOW}Extra        : {stats['extra']}{RESET}")
    print(f"Accuracy      : {color}{acc_str}{RESET}\n")

    if stats["missing_details"]:
        print(f"{RED}Missing pairs (orig, new): {stats['missing_details']}{RESET}")
    if stats["extra_details"]:
        print(f"{YELLOW}Extra pairs   (orig, new): {stats['extra_details']}{RESET}")
    print()


def main():
    cwd = os.getcwd()
    print("COMP-3110 XML mapping comparator")
    print(f"(current folder: {cwd})\n")

    print("First choose the professor's XML file.")
    gold_path = select_xml_file("Professor")

    print("Now choose our XML file (inside XML_Outputs).")
    our_path = select_xml_file("Our", fixed_folder="XML_Outputs")

    gold_name, gold_file_attr, gold_pairs = load_flat_mapping(gold_path)
    our_name,  our_file_attr,  our_pairs  = load_flat_mapping(our_path)

    # choose nicer labels to print
    gold_label = gold_file_attr or os.path.basename(gold_path)
    our_label  = our_name or os.path.basename(our_path)

    # guess offset automatically
    offset = compute_offset(gold_pairs, our_pairs)

    # apply offset to our pairs
    adjusted_our_pairs = set()
    for o, n in our_pairs:
        new_o = o + offset if o != -1 else -1
        if n == -1:
            new_n = -1
        else:
            new_n = n + offset
        adjusted_our_pairs.add((new_o, new_n))

    stats = compare_flat(gold_pairs, adjusted_our_pairs)
    print_flat_results(gold_label, our_label, stats, offset)


if __name__ == "__main__":
    main()
