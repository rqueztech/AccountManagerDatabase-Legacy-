import os
import re
import subprocess

def main():
    filename = "InputOperations.java"
    filepath = os.path.join(os.getcwd(), filename)

    matches = []  # create an empty list for storing matched substrings

    with open(filepath, "r") as file:
        for line in file:
            # Look for characters that start with ^ and end with )
            for match in re.findall(r'\^.*?\)', line):
                matches.append(match)  # add the matched substring to the list
                print(match)

            # Look for characters that start with [ and end with ]
            for match in re.findall(r'\[.*?\]', line):
                matches.append(match)  # add the matched substring to the list
                print(match)
    
    with open(filename + ".txt", "w") as output_file:
        for match in matches:
            output_file.write(match + "\n")

    # Convert the matches list to a string
    matches_string = '\n'.join(matches)
    
    subprocess.Popen(["notepad.exe"], stdin=subprocess.PIPE).communicate(matches_string.encode())

if __name__ == "__main__":
    main()
