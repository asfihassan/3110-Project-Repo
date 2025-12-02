# Version 1.4 - folder selection + colored output + input validation + comments on sections of code
import difflib
import os

# ANSI COLORS
GREEN  = "\033[92m"   # unchanged
RED    = "\033[91m"   # deleted
YELLOW = "\033[93m"   # replaced
CYAN   = "\033[96m"   # inserted
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


# -------- DIFF LOGIC -------- #
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


# -------- FOLDER CHOICE -------- #
def select_folder():
    print("\n=== Folder Selection ===")
    print("1. Use default folder: eclipseTest/")
    print("2. Use or create a new folder")
    
    choice = safe_input("Choose option (1/2): ")

    if choice == "1":
        return "../eclipseTest/"

    elif choice == "2":
        folder_name = safe_input("Enter new folder name: ")
        folder_path = "../" + folder_name + "/"

        if not os.path.exists(folder_path):
            print(f"Folder '{folder_name}' does not exist. Creating it...")
            os.makedirs(folder_path)
        else:
            print(f"Using existing folder: {folder_path}")

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
