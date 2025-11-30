# Version 1.2 - folder listing feature is added

import difflib
import os

def load_file(path):
    with open(path, "r", encoding="utf-8", errors="ignore") as f:
        return f.readlines()

def list_files_in_folder(folder):
    print("\n===Files in selected folder:===\n")
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
    
def map_files(old_file_path, new_file_path):
    old_lines = load_file(old_file_path)
    new_lines = load_file(new_file_path)

    matcher = difflib.SequenceMatcher(None, old_lines, new_lines)
    opcodes = matcher.get_opcodes()

    print("\n=== LINE MAPPING RESULTS ===\n")

    for tag, i1, i2, j1, j2 in opcodes:
        if tag == "equal":
            for o, n in zip(range(i1, i2), range(j1, j2)):
                print(f"{o+1} -> {n+1}  (unchanged)")
        elif tag == "replace":
            print(f"{i1+1}-{i2} replaced with {j1+1}-{j2}")
        elif tag == "delete":
            print(f"{i1+1}-{i2} deleted")
        elif tag == "insert":
            print(f"{j1+1}-{j2} inserted")

def select_folder():
    print("\n=== Folder Selection ===")
    print("1. Use default folder: eclipseTest/")
    print("2. Use an existing folder or create a new folder\n")
    
    choice = input("Choose option (1/2):\n")

    if choice == "1":
        folder_path = "../eclipseTest/"

    elif choice == "2":
        folder_name = input("Enter new folder name:\n")
        folder_path = "../" + folder_name + "/"

        if not os.path.exists(folder_path):
            print(f"Folder '{folder_name}' does not exist. Creating it...")
            os.makedirs(folder_path)
        else:
            print(f"Using existing folder: {folder_path}")

    else:
        print("Invalid choice. Defaulting to eclipseTest/")
        folder_path = "../eclipseTest/"

    # New feature: list files in folder
    list_files_in_folder(folder_path)

    return folder_path


def main():
    print("=== COMP-3110 Line Mapping Tool ===\n")

    base_folder = select_folder()

    old_name = input("Enter OLD file name:\n")
    new_name = input("Enter NEW file name:\n")

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
