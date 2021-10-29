import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkGui implements ActionListener {

    //creating all the private objects used in the creation of a GUI
    private JLabel srcLabel;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanel2;
    private JTextField srcIn;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JFrame frame;
    private JFrame popFrame = new JFrame();
    private Graph graphui;
    private JFileChooser jFileChooser;
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem graphItem, attackItem, defaultItem, animateItem;

    private Map<String, JRadioButton> nodes = new HashMap<>();

    //constructor to build the graph GUI this method builds the main graph screen
    public NetworkGui(){
        //graphui = graph;
        jPanel2 = new JPanel();
        srcLabel = new JLabel();
        srcIn = new JTextField();
        textArea1 = new JTextArea();
        textArea2 = new JTextArea();
        textArea3 = new JTextArea();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel5 = new JLabel();
        jLabel4 = new JLabel();
        frame = new JFrame();

        //setting the frame preferences, making the frame exit when the window is closed
        //frame size of 1280x722
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new java.awt.Dimension(1280, 722));
        frame.pack();
        frame.setTitle("Network GUI");

        jPanel2.setLayout(null);

        AddingDropDownMenus();
        menuBar.setBounds(30, 30, 1280, 722);

        graphItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFileChooser = new JFileChooser();
                jFileChooser.setBounds(420, 140, 500, 326);
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

                if (jFileChooser.showOpenDialog(jPanel2) == JFileChooser.APPROVE_OPTION)
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

        attackItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFileChooser = new JFileChooser();
                jFileChooser.setBounds(420, 140, 500, 326);
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

                if (jFileChooser.showOpenDialog(jPanel2) == JFileChooser.APPROVE_OPTION)
                {
                    File file = jFileChooser.getSelectedFile();
                    System.out.printf("\nOpening file = '%s'\n", file);
                    try {
                        attackInit(file);
                    } catch (FileNotFoundException ex) {
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
                    graphui = graphui.createGraph();
                    graphui.printGraph();
                } catch (FileNotFoundException ex) {
                    System.out.println("Failed to open Graph.txt");
                }
            }
        });


        srcLabel.setText("Source");
        srcLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                popUp();
                popFrame.setVisible(true);
                System.out.println("Over");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                popFrame.setVisible(false);
                System.out.println("out");
            }
        });

        JRadioButton test = new JRadioButton();
        jPanel2.add(test);
        test.setBounds(90, 240, 20, 20);

        jPanel2.add(srcLabel);
        srcLabel.setBounds(20, 340, 60, 20);

        jPanel2.add(srcIn);
        srcIn.setBounds(10, 370, 80, 30);
        srcIn.addActionListener(this);

        textArea1.setEditable(false);
        jPanel2.add(textArea1);
        textArea1.setBounds(10, 440, 150, 80);

        textArea2.setEditable(false);
        jPanel2.add(textArea2);
        textArea2.setBounds(10, 570, 110, 80);

        textArea3.setEditable(false);
        jPanel2.add(textArea3);
        textArea3.setBounds(140, 570, 100, 80);

        jLabel2.setText("Connections");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(30, 420, 100, 14);

        jLabel3.setText("Virus'");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(40, 550, 35, 14);

        jLabel5.setText("Virus' Stopped");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(150, 550, 100, 14);

        jLabel4.setIcon(new javax.swing.ImageIcon("World Map.jpg")); // NOI18N
        jPanel2.add(jLabel4);
        jLabel4.setBounds(0, 0, 1280, 720);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );
        frame.setVisible(true);


    }

    //method to produce the file drop-down menu used to retrieve the graph and attack data
    public void AddingDropDownMenus(){
        frame.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        graphItem = fileMenu.add("Graph");
        attackItem = fileMenu.add("Attack");
        defaultItem = fileMenu.add("Default");
        animateItem = fileMenu.add("Animation");
        fileMenu.addSeparator();

        menuBar.add(fileMenu);

    }

    //override method used to fill in the appropriate text areas with graph information
    @Override
    public void actionPerformed(ActionEvent e) {
        Map<String, ArrayList<Vertex>> map = graphui.getMap();
        Map<String, Vertex> vertMap = graphui.getVertmap();
        textArea1.setText("");
        textArea2.setText("");
        textArea3.setText("");

        if (!map.containsKey(srcIn.getText())){
            srcIn.setText("");
            textArea1.append("Node isn't valid");
        }
        else{
            if (map.get(srcIn.getText()).isEmpty()){
                textArea1.append("Node has no connections");
            }
            for (Vertex v : map.get(srcIn.getText())){
                textArea1.append(v.getSrc() +"\n");
            }
        }

        if (vertMap.get(srcIn.getText()).getVirus().isEmpty()){
            textArea2.append("Node has no Virus'");
        }
        else{
            for (int i = 0; i < vertMap.get(srcIn.getText()).getVirus().size(); i++){
                textArea2.append(vertMap.get(srcIn.getText()).getVirus().get(i)+ "\n");
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

        graphui.getVertmap().get("London").addVirus("Black");
        graphui.getVertmap().get("London").addVirus("Red");
        graphui.getVertmap().get("London").addVirus("Black");

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
