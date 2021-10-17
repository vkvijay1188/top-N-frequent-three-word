# top-N-frequent-three-word

This application accepts as arguments a list of one or more file paths (e.g. ./solution.rb file1.txt file2.txt ...).

This application also accepts input on stdin (e.g. cat file1.txt | ./solution.rb).

This application outputs a list of the 100 most common three word sequences in the text, along with a count of how many times each occurred in the text. For example: 231 - i will not, 116 - i do not, 105 - there is no, 54 - i know not, 37 - i am not …

This application ignores punctuation, line endings, and is case insensitive (e.g. “I love\nsandwiches.” should be treated the same as "(I LOVE SANDWICHES!!)")
