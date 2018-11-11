f = open("initial_words.txt", "r")
o = open("words.txt", "w")
lines = f.readlines()
for line in lines:
	if len(line) > 3 and 'y' not in line and 'x' not in line and len(line) < 18:
		o.write(line)

f.close()
o.close()
