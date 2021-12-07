
# NetworkSimulation
=======

Network Simulation Project created by Evan Webster, Austin Miller, and Abdulla Yousif.
This application reads information from two files; Graph.txt, and Attack.txt. Files are 
required to use the following format:

Graph.txt: starts with "Ottawa, (45.42, 75.70), firewall" Ottawa being the city name to 
be added to be added to the graph, and (45.42, 75.70) being the location of the city in 
long and lat. ", firewall" is added to any node that is protected by a firewall.
"---" seperates adding nodes from adding connections in the graph. 
After "---" adding connections is in the form "Ottawa, London" each entry on a new line 
for both parts.

Attack.txt: contains information regarding virus attacks in the form of "Chicago, Black, 
2021-05-13, 9:00". This information giving Chicago as the location of the virus attack, 
Black as the type of attack, and 2021-05-13, 9:00 as the date and time of the attack.

# UI Information
=======

The text based UI is used to retrieve any information about the graph or within 
the graph, all commands are case-sensitive and are as follows:
- END : to exit the UI
- info : user gets asked to enter a node name, all information regarding that nodes connections, and virus' is retrieved
- outbreak : outputs all nodes that contains an outbreak and how many outbreaks that node has.
- graph : outputs written list of all the nodes and their connections
- status : outputs the graph's status, this being all infected nodes, attacked nodes that have a firewall, inactive nodes, nodes containing a firewall, and outbreaks
- alerts : user asked to input a node name, retrieves number of alerts on that node
- matrix : outputs the graph's adjacency matrix
- path : user prompted for 2 nodes (seperatly), first being start location, second being destination. all available safe paths are output then the shortest safe path.
- help : shows the user all the commands with brief description

For safe paths which requires user to enter 2 nodes, start location is entered after pressing enter, the prompt for 
destination node is output, and the user must enter the destination node.
