import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkGui implements ActionListener {

    //creating all the private objects used in the creation of a GUI
    private JLabel srcLabel;
    private JLabel connectTitle;
    private JLabel virusTitle;
    private JLabel background;
    private JLabel blockedTitle;
    private JPanel screen;
    private JTextField srcIn;
    private JTextArea connectList;
    private JTextArea VirusList;
    private JTextArea BlockedList;
    private JFrame frame;
    private JFrame popFrame = new JFrame();
    private Graph graphui;
    private JFileChooser jFileChooser;
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem graphItem, attackItem, defaultItem, animateItem;
    private JMenuItem infectedItem, firewallItem, outbreaksItem, inActiveItem;

    private Map<String, JRadioButton> nodes = new HashMap<>();

    //constructor to build the graph GUI this method builds the main graph screen
    public NetworkGui(){
        //graphui = graph;
        screen = new JPanel();
        srcLabel = new JLabel();
        srcIn = new JTextField();
        connectList = new JTextArea();
        VirusList = new JTextArea();
        BlockedList = new JTextArea();
        connectTitle = new JLabel();
        virusTitle = new JLabel();
        blockedTitle = new JLabel();
        background = new JLabel();
        frame = new JFrame();

        //setting the frame preferences, making the frame exit when the window is closed
        //frame size of 1280x722
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new java.awt.Dimension(1280, 722));
        frame.pack();
        frame.setTitle("Network GUI");

        screen.setLayout(null);

        AddingDropDownMenus();
        menuBar.setBounds(30, 30, 1280, 722);

        //action listener for clicking on the Graph button in the file drop-down
        //code is exectuted when the button is pressed
        graphItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFileChooser = new JFileChooser();
                jFileChooser.setBounds(420, 140, 500, 326);
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

                if (jFileChooser.showOpenDialog(screen) == JFileChooser.APPROVE_OPTION)
                {
                    File file = jFileChooser.getSelectedFile();
                    System.out.printf("\nOpening file = '%s'\n", file);
                    try {
                        initialize(file);
                    } catch (FileNotFoundException ex) {
                        System.out.printf("\nFailed to open %s\n", jFileChooser.getSelectedFile());
                    }
                }
            }
        });

        //action listener for when the user clicks on
        //the Attack button in the file drop-down
        attackItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFileChooser = new JFileChooser();
                jFileChooser.setBounds(420, 140, 500, 326);
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

                if (jFileChooser.showOpenDialog(screen) == JFileChooser.APPROVE_OPTION)
                {
                    File file = jFileChooser.getSelectedFile();
                    System.out.printf("\nOpening file = '%s'\n", file);
                    try {
                        graphui.createAttack(file);
                        //attackInit(file);
                    } catch (FileNotFoundException | ParseException ex) {
                        System.out.printf("\nFailed to open %s\n", jFileChooser.getSelectedFile());
                    }
                }
            }
        });

        defaultItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphui = new Graph();
                try {
                    graphui = new Graph();
                    graphui.createGraph();
                    graphui.printGraph();
                    //graphui.printProtected();
                    //graphui.listInfected();
                    //graphui.listattacked();
                    graphui.Outbreak(new ArrayList<String>());
                } catch (FileNotFoundException | ParseException fileNotFoundException) {
                    System.out.println("Failed to open Graph.txt");
                }
            }
        });

        infectedItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphui.listInfected();
            }
        });

        firewallItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphui.printProtected();
                graphui.listattacked();
            }
        });

        outbreaksItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphui.Outbreak(new ArrayList<>());
            }
        });

        inActiveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphui.inActive();
            }
        });

        srcLabel.setText("Source");
        srcLabel.setForeground(Color.WHITE);
        srcLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                popUp();
                popFrame.setVisible(true);
                //System.out.println("Over");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                popFrame.setVisible(false);
                //System.out.println("out");
            }
        });

        JRadioButton test = new JRadioButton();
        screen.add(test);
        test.setBackground(Color.CYAN);
        test.setBounds(268, 258, 20, 20);

        screen.add(srcLabel);
        srcLabel.setBounds(20, 340, 60, 20);

        screen.add(srcIn);
        srcIn.setBounds(10, 370, 80, 30);
        srcIn.addActionListener(this);

        connectList.setEditable(false);
        JScrollPane conn_scroll = new JScrollPane(connectList);
        conn_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        screen.add(conn_scroll);
        //screen.add(connectList);
        //connectList.setBounds(10, 440, 150, 80);
        conn_scroll.setBounds(10, 440, 150, 80);

        VirusList.setEditable(false);
        JScrollPane virus_scroll = new JScrollPane(VirusList);
        virus_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        screen.add(virus_scroll);
        //jPanel2.add(VirusList);
        //VirusList.setBounds(10, 570, 168, 80);
        virus_scroll.setBounds(10, 570, 168, 80);

        BlockedList.setEditable(false);
        JScrollPane Bvirus_scroll = new JScrollPane(BlockedList);
        Bvirus_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        screen.add(Bvirus_scroll);
        Bvirus_scroll.setBounds(470, 570, 168, 80);
        //jPanel2.add(BlockedList);
        //BlockedList.setBounds(450, 570, 168, 80);

        connectTitle.setText("Connections");
        connectTitle.setForeground(Color.WHITE);
        screen.add(connectTitle);
        connectTitle.setBounds(30, 420, 100, 14);

        virusTitle.setText("Virus'");
        virusTitle.setForeground(Color.WHITE);
        screen.add(virusTitle);
        virusTitle.setBounds(40, 550, 35, 14);

        blockedTitle.setText("Virus' Stopped");
        blockedTitle.setForeground(Color.WHITE);
        screen.add(blockedTitle);
        blockedTitle.setBounds(470, 550, 100, 14);

        background.setIcon(new javax.swing.ImageIcon("WorldM.jpg")); // NOI18N
        screen.add(background);
        background.setBounds(0, 0, 1280, 720);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(screen, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(screen, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );
        frame.add(screen);
        frame.setVisible(true);


    }

    //method to produce the file drop-down menu used to retrieve the graph and attack data
    public void AddingDropDownMenus(){
        frame.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");
        graphItem = fileMenu.add("Graph");
        attackItem = fileMenu.add("Attack");
        defaultItem = fileMenu.add("Default");
        animateItem = fileMenu.add("Animation");
        fileMenu.addSeparator();
        infectedItem = viewMenu.add("Infected");
        firewallItem = viewMenu.add("Firewalls");
        outbreaksItem = viewMenu.add("Outbreaks");
        inActiveItem = viewMenu.add("InActive");

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
    }

    //override method used to fill in the appropriate text areas with graph information
    @Override
    public void actionPerformed(ActionEvent e) {
        Map<String, ArrayList<Vertex>> map = graphui.getMap();
        Map<String, Vertex> vertMap = graphui.getVertmap();
        connectList.setText("");
        VirusList.setText("");
        BlockedList.setText("");

        if (!map.containsKey(srcIn.getText())){
            srcIn.setText("");
            connectList.append("Node isn't valid");
        }
        else{
            if (map.get(srcIn.getText()).isEmpty()){
                connectList.append("Node has no connections");
            }
            for (Vertex v : map.get(srcIn.getText())){
                connectList.append(v.getSrc() +"\n");
            }
        }

        if (vertMap.get(srcIn.getText()).getVirus().isEmpty()){
            VirusList.append("Node has no Virus'");
        }
        else{
            for (int i = 0; i < vertMap.get(srcIn.getText()).getVirus().size(); i++){
                VirusList.append(vertMap.get(srcIn.getText()).getVirus().get(i).getType()+ " @ "
                        + vertMap.get(srcIn.getText()).getVirus().get(i).getDate()+ "\n");
            }
        }
        if (vertMap.get(srcIn.getText()).getFirewall_virus().isEmpty()){
            BlockedList.append("No attacks stopped");
        }
        else{
            for (int i = 0; i < vertMap.get(srcIn.getText()).getFirewall_virus().size(); i++){
                BlockedList.append(vertMap.get(srcIn.getText()).getFirewall_virus().get(i).getType()+ " @ "
                        + vertMap.get(srcIn.getText()).getFirewall_virus().get(i).getDate()+"\n");
            }
        }

        srcIn.setText("");
    }

    //creating the graph using the file selected from the file selector
    //utilizes the override of create graph which takes in a FILE param
    private void initialize(File source) throws FileNotFoundException {
        graphui = new Graph();
        graphui = graphui.createGraph(source);
        graphui.printGraph();

        /*
        graphui.getVertmap().get("London").addVirus("Black");
        graphui.getVertmap().get("London").addVirus("Red");
        graphui.getVertmap().get("London").addVirus("Black");
                 */

        // System.out.println(graph.getVertmap().get("London").getVirus());

        //graph.printVirus();
        //graph.listVirus("London");
    }

    private void attackInit(File attack)throws FileNotFoundException{

    }

    //settup of the pop up window to display node information
    private void popUp(){

        JPanel popPanel = new JPanel();
        JTextArea conected_txtArea = new JTextArea();
        JTextArea virus_txtArea = new JTextArea();
        JTextArea virus_stoptxt = new JTextArea();
        JLabel connected = new JLabel();
        JLabel virus = new JLabel();
        JLabel virus_stop = new JLabel();

        popFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        popFrame.setPreferredSize(new java.awt.Dimension(450, 450));
        popFrame.pack();
        popFrame.setTitle("Node Information");

        popPanel.setLayout(null);

        conected_txtArea.setEditable(false);
        popPanel.add(conected_txtArea);
        conected_txtArea.setBounds(10, 30, 150, 80);

        virus_txtArea.setEditable(false);
        popPanel.add(virus_txtArea);
        virus_txtArea.setBounds(10, 150, 150, 80);

        virus_stoptxt.setEditable(false);
        popPanel.add(virus_stoptxt);
        virus_stoptxt.setBounds(10, 300, 150, 80);

        connected.setText("Connections");
        popPanel.add(connected);
        connected.setBounds(30, 10, 100, 14);

        virus.setText("Virus'");
        popPanel.add(virus);
        virus.setBounds(30, 120, 35, 14);

        virus_stop.setText("Virus' Stopped");
        popPanel.add(virus_stop);
        virus_stop.setBounds(30, 280, 100, 14);

        popFrame.add(popPanel);

    }
}
