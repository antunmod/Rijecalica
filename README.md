# Rijecalica
This app was made for entertainment and for exploring different text search techniques. To search the board, simply type in the 16 characters from the game and the words found will be printed out. 

## Dictionary
The dictionary used was downloaded from http://hr.words-finder.com and altered to words with length [3, 16]. This is not the same dictionary as the one used in the game so many of the words the algorithm finds will not be valid in game. For this reason, the user can add missing words to the dictionary and remove the unnecessary ones after each board search.

## Search Algorithm
The search algorithm in this game uses character node objects. Each object contains the character value, a flag if it is an end to an existing word and a map of possible next characters. The algorithm recursively goes through all the fields and adds the words found to the map which is printed out at the end.
