package com.mycompany.snakegame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends Canvas implements Runnable, KeyListener {

    public int width = 480;
    public int height = 480;

    public List<Node> lNodes = new ArrayList<Node>();
    public boolean left, right, up, down;
    public int score = 0;
    public int macaX = 0, macaY = 0;

    public int spd = 3;

    public Game() {
        for (int i = 0; i < 5; i++) {
            lNodes.add(new Node(0, 0));
        }
        this.setPreferredSize(new Dimension(width, height));
        this.addKeyListener(this);
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame("Snake");
        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(game).start();
    }

    @Override
    public void run() {
        while (true) {
            tick();
            render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void tick() {

        for (int i = lNodes.size() - 1; i > 0; i--) {
            lNodes.get(i).x = lNodes.get(i - 1).x;
            lNodes.get(i).y = lNodes.get(i - 1).y;
        }

        if (lNodes.get(0).x + 10 < 0) {
            lNodes.get(0).x = width - 10;
        } else if (lNodes.get(0).x + 10 >= width) {
            lNodes.get(0).x = -10;
        }

        if (lNodes.get(0).y + 10 < 0) {
            lNodes.get(0).y = height - 10;
        } else if (lNodes.get(0).y + 10 >= height) {
            lNodes.get(0).y = -10;
        }

        if (right) {
            lNodes.get(0).x += spd;
        } else if (left) {
            lNodes.get(0).x -= spd;
        } else if (up) {
            lNodes.get(0).y -= spd;
        } else if (down) {
            lNodes.get(0).y += spd;
        }

        if (new Rectangle(lNodes.get(0).x, lNodes.get(0).y, 10, 10).intersects(new Rectangle(macaX, macaY, 10, 10))) {
            macaX = new Random().nextInt(width - 10);
            macaY = new Random().nextInt(height - 10);
            score++;
            lNodes.add(new Node(lNodes.get(lNodes.size() - 1).x, lNodes.get(lNodes.size() - 1).y));
            System.out.println("Pontos: " + score);
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < lNodes.size(); i++) {
            g.setColor(Color.BLUE);
            g.fillRect(lNodes.get(i).x, lNodes.get(i).y, 10, 10);
        }

        g.setColor(Color.red);
        g.fillRect(macaX, macaY, 10, 10);

        g.dispose();
        bs.show();
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            left = false;
            right = true;
            up = false;
            down = false;
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
            right = false;
            up = false;
            down = false;
        } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
            left = false;
            right = false;
            up = true;
            down = false;
        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            left = false;
            right = false;
            up = false;
            down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

}
