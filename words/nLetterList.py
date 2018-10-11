# This short script is used for creating txt files with words with word lengths from 2 to 16

lengths = [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]
f = open("words.txt", "r")
lines = f.readlines()
for length in lengths:
	o = open("nLetterWords/" + str(length) + "_letter_words.txt", "w")
	previous = ""
	for line in lines:
		if (len(line) > length):
			nLetterWord = line[0:length]
			if (previous != nLetterWord):
				previous = nLetterWord
				print(nLetterWord)
				o.write(nLetterWord + "\n")
	o.close()
f.close()
