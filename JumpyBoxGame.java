//Jumpy box game I used GUI Libraries, the Swing and awt
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class JumpyBoxGame extends JFrame implements ActionListener, KeyListener {

    private JPanel canvas;
    private Timer timer;
    private Rectangle box;
    private ArrayList<Rectangle> obstacles;
    private boolean isJumping;
    private int jumpSpeed;
    private final int gravity;
    private final int jumpStrength;

    public JumpyBoxGame() {
        setTitle("Jumpy Box Game");
     setSize(600, 400);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.BLUE);
                g.fillRect(box.x, box.y, box.width, box.height);
                g.setColor(Color.RED);
                     for (Rectangle obstacle : obstacles) {
                    g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
                }
            }
        };
        canvas.setBackground(Color.LIGHT_GRAY);
        add(canvas);

        box = new Rectangle(5, 350, 50, 50);
        obstacles = new ArrayList<>();
         isJumping = false;
        jumpSpeed = 0;
         gravity = 1;
        jumpStrength = -15;

            addKeyListener(this);

        timer = new Timer(20, this);
        timer.start();

        createObstacle();
    }

            private void createObstacle() {
        Random rand = new Random();
        int x = 600;
        int y = 350;
        int width = 20;
        int height = 50;
        Rectangle obstacle = new Rectangle(x, y, width, height);
        obstacles.add(obstacle);
        timer.setInitialDelay(rand.nextInt(2000) + 2000);
    }

            private void moveObstacles() {
        ArrayList<Rectangle> obstaclesToRemove = new ArrayList<>();
        for (Rectangle obstacle : obstacles) {
            obstacle.x -= 10;
            if (obstacle.x + obstacle.width < 0) {
                obstaclesToRemove.add(obstacle);
            }
        }
        obstacles.removeAll(obstaclesToRemove);
    }

private void applyGravity() {
        if (isJumping || box.y < 350) {
            box.y += jumpSpeed;
            jumpSpeed += gravity;
        }
        if (box.y >= 350) {
            box.y = 350;
            isJumping = false;
            jumpSpeed = 0;
        }
    }
  private boolean checkCollisions() {
        for (Rectangle obstacle : obstacles) {
            if (box.intersects(obstacle)) {
                return true;
            }
        }
        return false;
    }

 private void endGame() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    @Override
 public void actionPerformed(ActionEvent e) {
        moveObstacles();
        applyGravity();
        if (checkCollisions()) {
            endGame();
        }
        canvas.repaint();
    }

    @Override
public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_SPACE && !isJumping) {
            isJumping = true;
            jumpSpeed = jumpStrength;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

       public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JumpyBoxGame game = new JumpyBoxGame();
            game.setVisible(true);//Setting the JFrame to be true
        });
    }
}
/*
 * Copyright (c) 2024 
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (JumpyBoxGame), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */