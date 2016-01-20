package com.kademika.boberskiy.engine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by YB on 16.01.2016.
 */
public class EndMenu extends JFrame implements ActionListener {

    private JLabel resultLabel;
    private JPanel resultPanel;
    private JPanel buttonsPanel;
    private JButton playAgainButton;
    private JButton exitButton;
    private ActionField af;
    private BufferedImage gameOverImage;

    EndMenu(ActionField af) {
        super("Game over");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(300, 100);
        setResizable(false);

        this.af = af;

        try {
            gameOverImage = ImageIO.read(new File("resources/gameover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameOverImage = process(gameOverImage);

        resultLabel = new JLabel(new ImageIcon(gameOverImage));
        resultPanel = new JPanel();
        buttonsPanel = new JPanel();
        playAgainButton = new JButton("Play Again");
        exitButton = new JButton("Exit Game");

        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        playAgainButton.setName("playAgain");
        exitButton.setName("exit");

        playAgainButton.addActionListener(this);
        exitButton.addActionListener(this);

        resultPanel.add(resultLabel);
        resultPanel.setSize(new Dimension(200, 150));
        buttonsPanel.setBackground(Color.black);
        playAgainButton.setBackground(Color.black);
        playAgainButton.setForeground(Color.white);
        exitButton.setBackground(Color.black);
        exitButton.setForeground(Color.white);

        buttonsPanel.add(playAgainButton);
        buttonsPanel.add(exitButton);

        add(resultPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        this.pack();

    }

    private BufferedImage process(BufferedImage old) {
        int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(
                w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(new Color(188, 213, 247));
        g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
        String s = createResultMessage();
        FontMetrics fm = g2d.getFontMetrics();
        int x = img.getWidth() - fm.stringWidth(s) - 15;
        int y = img.getHeight() - fm.getHeight() - 30;
        g2d.drawString(s, x, y);
        g2d.dispose();
        return img;
    }

    public String createResultMessage() {
        if (af.getDefender().isDestroyed()) {
            return "Defender is destroyed!";
        } else if (!af.getBattleField().scanQuadrant(8, 4).getClass().getName().contains("Eagle")) {
            return "Eagle is destroyed!";
        } else if (af.getAgressor().isDestroyed()) {
            return "Agressor is destroyed!";
        } else {
            return "Oops! No algorithm!";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getName() == "playAgain") {
                System.out.println("PLAY AGAIN");
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
            if (button.getName() == "exit") {
                System.out.println("EXIT");
                System.exit(0);
            }
        }
    }
}
