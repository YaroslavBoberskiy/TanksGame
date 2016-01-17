package com.kademika.boberskiy.engine;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by YB on 15.01.2016.
 */
public class StartMenu extends JFrame implements ActionListener {

    private JPanel chooseAgressorPanel;
    private JPanel chooseDefenderPanel;
    private JPanel agressorComboBoxPanel;
    private JPanel startButtonPanel;
    private JPanel defenderComboboxPanel;
    private JPanel agressorImgPanel;
    private JPanel defenderImgPanel;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newFunctAction;

    private JTextArea defenderInfo;
    private JTextArea agressorInfo;

    private JLabel agressorLabel;
    private JLabel defenderLabel;

    private JComboBox chooseAgressorComboBox;
    private JComboBox chooseDefenderComboBox;
    private JButton startButton;

    private BufferedImage t34Image;
    private BufferedImage tigerImage;
    private BufferedImage bt7Image;

    private final String[] chooseTankModel = {"Tiger", "T-34", "BT-7"};

    private String tigerDescription =
            "Weight.......54 tonnes \n" +
                    "Crew.........5 \n" +
                    "Armour.......25–120 mm \n" +
                    "Engine.......Maybach P45 V-12 \n" +
                    "Power........700 PS \n" +
                    "Speed........45.4 km/h";

    private String t34Description =
            "Weight.......26.5 tonnes \n" +
                    "Crew.........4 \n" +
                    "Armour.......25–76 mm \n" +
                    "Engine.......Model V12 Diesel \n" +
                    "Power........500 PS \n" +
                    "Speed........53 km/h";

    private String bt7Description =
            "Weight.......11.5 tonnes \n" +
                    "Crew.........3 \n" +
                    "Armour.......6-13 mm \n" +
                    "Engine.......Model M5 \n" +
                    "Power........400 PS \n" +
                    "Speed........72 km/h";

    private ActionField af;

    StartMenu() {
        super("Game settings");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(300, 100);
        setResizable(false);
        this.setLayout(new BorderLayout());
        chooseAgressorPanel = new JPanel();
        agressorComboBoxPanel = new JPanel();
        startButtonPanel = new JPanel();
        chooseDefenderPanel = new JPanel();
        defenderComboboxPanel = new JPanel();
        defenderImgPanel = new JPanel();
        agressorImgPanel = new JPanel();
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        fileMenu = new JMenu("File");
        newFunctAction = new JMenuItem("New funct...");
        newFunctAction.setName("future_functionality");
        fileMenu.add(newFunctAction);
        menuBar.add(fileMenu);
        chooseAgressorComboBox = new JComboBox(chooseTankModel);
        chooseAgressorComboBox.setSelectedIndex(0);
        chooseDefenderComboBox = new JComboBox(chooseTankModel);
        chooseDefenderComboBox.setSelectedIndex(1);

        defenderInfo = new JTextArea();
        agressorInfo = new JTextArea();

        startButton = new JButton("START");
        startButton.setName("start");


        try {
            t34Image = ImageIO.read(new File("resources/choose_t34.png"));
            tigerImage = ImageIO.read(new File("resources/choose_tiger.png"));
            bt7Image = ImageIO.read(new File("resources/choose_bt7.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        chooseAgressorComboBox.setName("agressorCB");
        chooseDefenderComboBox.setName("defenderCB");

        chooseAgressorComboBox.addActionListener(this);
        chooseDefenderComboBox.addActionListener(this);
        startButton.addActionListener(this);
        newFunctAction.addActionListener(this);

        chooseAgressorPanel.setLayout(new BoxLayout(chooseAgressorPanel, BoxLayout.Y_AXIS));
        chooseAgressorPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Choose the agressor tank: "));

        chooseDefenderPanel.setLayout(new BoxLayout(chooseDefenderPanel, BoxLayout.Y_AXIS));
        chooseDefenderPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Choose the defender tank: "));

        agressorLabel = new JLabel(new ImageIcon(tigerImage));
        agressorImgPanel.add(agressorLabel);

        defenderLabel = new JLabel(new ImageIcon(t34Image));
        defenderImgPanel.add(defenderLabel);

        agressorComboBoxPanel.add(chooseAgressorComboBox);

        startButtonPanel.add(startButton);

        defenderComboboxPanel.add(chooseDefenderComboBox);

        agressorInfo.setFont(new Font("monospaced", Font.PLAIN, 12));
        defenderInfo.setFont(new Font("monospaced", Font.PLAIN, 12));

        agressorInfo.setText(tigerDescription);
        defenderInfo.setText(t34Description);

        chooseAgressorPanel.add(agressorComboBoxPanel);
        chooseAgressorPanel.add(agressorImgPanel);
        chooseAgressorPanel.add(agressorInfo);

        chooseDefenderPanel.add(defenderComboboxPanel);
        chooseDefenderPanel.add(defenderImgPanel);
        chooseDefenderPanel.add(defenderInfo);

        add(chooseAgressorPanel, BorderLayout.WEST);
        add(chooseDefenderPanel, BorderLayout.EAST);
        add(startButtonPanel, BorderLayout.SOUTH);

        this.pack();
    }

    public void showGUI() {
        this.setVisible(true);
    }

    public void updateAgressorPanel (String description, BufferedImage image) {
        agressorInfo.setText(description);
        agressorInfo.repaint();
        agressorLabel = new JLabel(new ImageIcon(image));
        agressorImgPanel.removeAll();
        agressorImgPanel.add(agressorLabel);
        agressorImgPanel.repaint();
    }

    public void updateDefenderPanel (String description, BufferedImage image) {
        defenderInfo.setText(description);
        defenderInfo.repaint();
        defenderLabel = new JLabel(new ImageIcon(image));
        defenderImgPanel.removeAll();
        defenderImgPanel.add(defenderLabel);
        defenderImgPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JComboBox) {

            JComboBox comboBox = (JComboBox) e.getSource();

            if (comboBox.getName() == "agressorCB") {
                if (comboBox.getSelectedItem() == "Tiger") {
                    System.out.println("agr Tiger");
                    updateAgressorPanel(tigerDescription, tigerImage);

                }
                if (comboBox.getSelectedItem() == "T-34") {
                    System.out.println("agr T-34");
                    updateAgressorPanel(t34Description, t34Image);
                }
                if (comboBox.getSelectedItem() == "BT-7") {
                    System.out.println("agr BT-7");
                    updateAgressorPanel(bt7Description, bt7Image);
                }
            }

            if (comboBox.getName() == "defenderCB") {
                if (comboBox.getSelectedItem() == "Tiger") {
                    System.out.println("def Tiger");
                    updateDefenderPanel(tigerDescription, tigerImage);
                }
                if (comboBox.getSelectedItem() == "T-34") {
                    System.out.println("def T-34");
                    updateDefenderPanel(t34Description, t34Image);
                }
                if (comboBox.getSelectedItem() == "BT-7") {
                    System.out.println("def BT-7");
                    updateDefenderPanel(bt7Description, bt7Image);
                }
            }
        }

        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getName() == "start") {
                System.out.println("Start game button works!");

                Runnable r1 = new Runnable() {
                    public void run() {
                        try {
                            af = new ActionField();
                            af.runTheGame();
                        } catch (InterruptedException iex) {
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                };

                Thread thr1 = new Thread(r1);

                thr1.start();
                this.dispose();
            }
        }

        if (e.getSource() instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) e.getSource();
            if (menuItem.getName() == "future_functionality") {
                System.out.println("Menu item works!");
            }
        }
    }
}
