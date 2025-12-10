# Version 1.6 - folder selection + folder listing + colored output + input validation + XML with smarter replace mapping
# Line mapper tool for COMP 3110
# Compares two files, prints a simple mapping, and writes an XML file.

import difflib
import os
import xml.etree.ElementTree as ET
import xml.dom.minidom

# Console colors
GREEN  = "\033[92m"
RED    = "\033[91m"
YELLOW = "\033[93m"
CYAN   = "\033[96m"
RESET  = "\033[0m"


def safe_input(prompt):
    """Keep asking until the user types something."""
    val = input(prompt).strip()
    while not val:
        print("Input cannot be empty.")
        val = input(prompt).strip()
    return val


def load_file(path):
    """Read a file and return the lines as a list."""
    with open(path, "r", encoding="utf-8", errors="ignore") as f:
        return f.readlines()


def list_files_in_folder(folder):
    """Print all regular files in a folder."""
    print("\nFiles in this folder:\n")

    if not os.path.exists(folder):
        print(f"Folder not found: {folder}\n")
        return

    files = sorted(
        f for f in os.listdir(folder)
        if os.path.isfile(os.path.join(folder, f))
    )

    if not files:
        print("(No files found)\n")
        return

    for name in files:
        print(" -", name)
    print()


def pretty_print_xml(elem):
    """Return the XML element as a formatted string."""
    raw = ET.tostring(elem, "utf-8")
    parsed = xml.dom.minidom.parseString(raw)
    return parsed.toprettyxml(indent="  ")


def line_similarity(a, b):
    """Return a simple similarity score between two text lines."""
    a = a.strip()
    b = b.strip()
    if not a and not b:
        return 1.0
    return difflib.SequenceMatcher(None, a, b).ratio()


def best_matches_for_block(old_lines, new_lines, i1, i2, j1, j2, threshold=0.5):
    """
    Try to match lines inside one replace block.

    i1, i2: index range in old file
    j1, j2: index range in new file

    Returns a list of (old_index, new_index) pairs.
    If old_index is -1, that means an insertion.
    If new_index is -1, that means a deletion.
    """
    old_idxs = list(range(i1, i2))
    new_idxs = list(range(j1, j2))

    scores = []
    for oi in old_idxs:
        for nj in new_idxs:
            sim = line_similarity(old_lines[oi], new_lines[nj])
            scores.append((sim, oi, nj))

    # sort by best match first
    scores.sort(reverse=True, key=lambda x: x[0])

    used_old = set()
    used_new = set()
    matches = []

    for sim, oi, nj in scores:
        if sim < threshold:
            break
        if oi in used_old or nj in used_new:
            continue
        used_old.add(oi)
        used_new.add(nj)
        matches.append((oi, nj))

    # remaining old lines are deletions
    for oi in old_idxs:
        if oi not in used_old:
            matches.append((oi, -1))

    # remaining new lines are insertions
    for nj in new_idxs:
        if nj not in used_new:
            matches.append((-1, nj))

    return matches


def generate_xml_output(old_file_path, new_file_path, opcodes, old_lines, new_lines):
    """Create the XML tree for the mapping."""
    test_name = os.path.splitext(os.path.basename(old_file_path))[0]

    root = ET.Element("TEST")
    root.set("NAME", test_name)

    version_num = 1

    for tag, i1, i2, j1, j2 in opcodes:
        version = ET.SubElement(root, "VERSION")
        version.set("NUMBER", str(version_num))
        version.set("CHECKED", "TRUE")

        if tag == "equal":
            for o, n in zip(range(i1, i2), range(j1, j2)):
                loc = ET.SubElement(version, "LOCATION")
                loc.set("ORIG", str(o + 1))
                loc.set("NEW", str(n + 1))

        elif tag == "replace":
            pairs = best_matches_for_block(old_lines, new_lines, i1, i2, j1, j2)
            for oi, nj in pairs:
                loc = ET.SubElement(version, "LOCATION")
                if oi == -1:
                    loc.set("ORIG", "-1")
                else:
                    loc.set("ORIG", str(oi + 1))
                if nj == -1:
                    loc.set("NEW", "-1")
                else:
                    loc.set("NEW", str(nj + 1))

        elif tag == "delete":
            for orig in range(i1, i2):
                loc = ET.SubElement(version, "LOCATION")
                loc.set("ORIG", str(orig + 1))
                loc.set("NEW", "-1")

        elif tag == "insert":
            for new in range(j1, j2):
                loc = ET.SubElement(version, "LOCATION")
                loc.set("ORIG", "-1")
                loc.set("NEW", str(new + 1))

        version_num += 1

    return root


def map_files(old_file_path, new_file_path):
    """Print the mapping and write the XML file."""
    old_lines = load_file(old_file_path)
    new_lines = load_file(new_file_path)

    matcher = difflib.SequenceMatcher(None, old_lines, new_lines)
    opcodes = matcher.get_opcodes()

    print("\nLine mapping:\n")

    for tag, i1, i2, j1, j2 in opcodes:
        if tag == "equal":
            for o, n in zip(range(i1, i2), range(j1, j2)):
                print(f"{GREEN}{o+1} -> {n+1}  (same){RESET}")
        elif tag == "replace":
            print(f"{YELLOW}{i1+1}-{i2} replaced with {j1+1}-{j2}{RESET}")
        elif tag == "delete":
            print(f"{RED}{i1+1}-{i2} deleted{RESET}")
        elif tag == "insert":
            print(f"{CYAN}{j1+1}-{j2} inserted{RESET}")

    # build XML with smarter replace handling
    root = generate_xml_output(old_file_path, new_file_path, opcodes, old_lines, new_lines)
    xml_string = pretty_print_xml(root)

    output_folder = os.path.join("..", "XML_Outputs")
    os.makedirs(output_folder, exist_ok=True)

    test_name = os.path.splitext(os.path.basename(old_file_path))[0]
    output_path = os.path.join(output_folder, f"{test_name}_mapping.xml")

    with open(output_path, "w", encoding="utf-8") as f:
        f.write(xml_string)

    print(f"\nXML saved in: {output_path}")


def select_folder():
    """Choose the folder that holds the test files."""
    print("\nFolder selection")
    print("1. Use default folder: eclipseTest")
    print("2. Use or create another folder")

    choice = safe_input("Choose option (1 or 2): ")

    if choice == "1":
        folder_path = "../eclipseTest/"
        print(f"\nUsing folder: {folder_path}")
        list_files_in_folder(folder_path)
        return folder_path

    if choice == "2":
        folder_name = safe_input("Enter folder name: ")
        folder_path = "../" + folder_name + "/"

        if not os.path.exists(folder_path):
            print(f"Folder '{folder_name}' does not exist. Creating it.")
            os.makedirs(folder_path)
        else:
            print(f"Using folder: {folder_path}")

        list_files_in_folder(folder_path)
        return folder_path

    print("Invalid choice. Using default folder: eclipseTest")
    folder_path = "../eclipseTest/"
    list_files_in_folder(folder_path)
    return folder_path


def main():
    print("COMP 3110 Line Mapping Tool")

    base_folder = select_folder()

    old_name = safe_input("Enter OLD file name: ")
    new_name = safe_input("Enter NEW file name: ")

    old_path = os.path.join(base_folder, old_name)
    new_path = os.path.join(base_folder, new_name)

    if not os.path.exists(old_path):
        print(f"Error: {old_path} does not exist.")
        return
    if not os.path.exists(new_path):
        print(f"Error: {new_path} does not exist.")
        return

    map_files(old_path, new_path)


if __name__ == "__main__":
    main()
