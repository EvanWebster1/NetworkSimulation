Network Simulation Project created by Evan Webster, Austin Miller, and Abdulla Yousif.
This application reads information from two files; Graph.txt, and Attack.txt. Files are 
required to use the following format:

Graph.txt: starts with "Ottawa, (45.42, 75.70)" Ottawa being the city name to be added to
be added to the graph, and (45.42, 75.70) being the location of the city in long and lat.
"---" seperates adding nodes from adding connections in the graph. After "---" adding 
connections is in the form "Ottawa, London" each entry on a new line for both parts.

Attack.txt: contains information regarding virus attacks in the form of "Chicago, Black, 
2021-05-13, 9:00". This information giving Chicago as the location of the virus attack, 
Black as the type of attack, and 2021-05-13, 9:00 as the date and time of the attack.

The GUI displays the current graph. to starts working with the GUI you must go to the file
menu drop-down and select Graph, Attack, or default. Graph and Attack pull up a file 
selector screen to select the file from a navigation screen. Default takes the path
where the file is normally located within the project folder. 