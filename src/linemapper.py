# Version 1.5 - folder selection + folder listing + colored output + input validation + comments on sections of code + XML OUTPUT ADDED 
import difflib
import os
import xml.etree.ElementTree as ET
import xml.dom.minidom

# ANSI COLORS
GREEN  = "\033[92m"
RED    = "\033[91m"
YELLOW = "\033[93m"
CYAN   = "\033[96m"
RESET  = "\033[0m"


# -------- SAFE INPUT -------- #
def safe_input(prompt):
    """Ensures user never enters an empty string."""
    val = input(prompt).strip()
    while not val:
        print("Input cannot be empty.")
        val = input(prompt).strip()
    return val


# -------- FILE HANDLING -------- #
def load_file(path):
    with open(path, "r", encoding="utf-8", errors="ignore") as f:
        return f.readlines()


# -------- FOLDER LISTING -------- #
def list_files_in_folder(folder):
    print("\n=== Files in selected folder: ===\n")
    try:
        files = sorted(os.listdir(folder))
    except FileNotFoundError:
        print(f"Folder not found: {folder}\n")
        return

    if not files:
        print("(No files found)")
        return

    for f in files:
        if os.path.isfile(os.path.join(folder, f)):
            print(" -", f)
    print()


# -------- PRETTY XML FORMATTER -------- #
def pretty_print_xml(elem):
    """Return pretty-printed XML string."""
    raw = ET.tostring(elem, 'utf-8')
    parsed = xml.dom.minidom.parseString(raw)
    return parsed.toprettyxml(indent="  ")


# -------- XML OUTPUT LOGIC -------- #
def generate_xml_output(old_file_path, new_file_path, opcodes):
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
            for orig in range(i1, i2):
                loc = ET.SubElement(version, "LOCATION")
                loc.set("ORIG", str(orig + 1))
                loc.set("NEW", "-1")
            for new in range(j1, j2):
                loc = ET.SubElement(version, "LOCATION")
                loc.set("ORIG", "-1")
                loc.set("NEW", str(new + 1))

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


# -------- DIFF LOGIC + XML -------- #
def map_files(old_file_path, new_file_path):
    old_lines = load_file(old_file_path)
    new_lines = load_file(new_file_path)

    matcher = difflib.SequenceMatcher(None, old_lines, new_lines)
    opcodes = matcher.get_opcodes()

    print("\n=== LINE MAPPING RESULTS ===\n")

    for tag, i1, i2, j1, j2 in opcodes:

        if tag == "equal":
            for o, n in zip(range(i1, i2), range(j1, j2)):
                print(f"{GREEN}{o+1} -> {n+1}  (unchanged){RESET}")

        elif tag == "replace":
            print(f"{YELLOW}{i1+1}-{i2} replaced with {j1+1}-{j2}{RESET}")

        elif tag == "delete":
            print(f"{RED}{i1+1}-{i2} deleted{RESET}")

        elif tag == "insert":
            print(f"{CYAN}{j1+1}-{j2} inserted{RESET}")

    # -------- Generate XML -------- #
    root = generate_xml_output(old_file_path, new_file_path, opcodes)
    xml_string = pretty_print_xml(root)

    # -------- SAVE XML TO XML_Outputs FOLDER -------- #
    output_folder = os.path.join("..", "XML_Outputs")
    os.makedirs(output_folder, exist_ok=True)

    test_name = os.path.splitext(os.path.basename(old_file_path))[0]
    output_path = os.path.join(output_folder, f"{test_name}_mapping.xml")

    with open(output_path, "w", encoding="utf-8") as f:
        f.write(xml_string)

    print(f"\nXML saved to: {output_path}")


# -------- FOLDER CHOICE -------- #
def select_folder():
    print("\n=== Folder Selection ===")
    print("1. Use default folder: eclipseTest/")
    print("2. Use or create another folder")

    choice = safe_input("Choose option (1/2): ")

    if choice == "1":
        return "../eclipseTest/"

    elif choice == "2":
        folder_name = safe_input("Enter folder name: ")
        folder_path = "../" + folder_name + "/"

        if not os.path.exists(folder_path):
            print(f"Folder '{folder_name}' does not exist. Creating it...")
            os.makedirs(folder_path)
        else:
            print(f"Using existing folder: {folder_path}")
            list_files_in_folder(folder_path)

        return folder_path

    else:
        print("Invalid choice. Defaulting to eclipseTest/")
        return "../eclipseTest/"


# -------- MAIN -------- #
def main():
    print("=== COMP-3110 Line Mapping Tool ===")

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
