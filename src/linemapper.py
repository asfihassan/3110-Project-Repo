# Version 1.0

import difflib
import os

def load_file(path):
    with open(path, "r", encoding="utf-8", errors="ignore") as f:
        return f.readlines()

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

def main():
    print("=== COMP-3110 Line Mapping Tool ===")
    
    base_folder = "../eclipseTest/"

    old_name = input("Enter OLD file name (inside eclipseTest/): ")
    new_name = input("Enter NEW file name (inside eclipseTest/): ")

    old_path = os.path.join(base_folder, old_name)
    new_path = os.path.join(base_folder, new_name)

    # check if files exist
    if not os.path.exists(old_path):
        print(f"Error: {old_path} does not exist.")
        return
    if not os.path.exists(new_path):
        print(f"Error: {new_path} does not exist.")
        return

    map_files(old_path, new_path)

if __name__ == "__main__":
    main()
