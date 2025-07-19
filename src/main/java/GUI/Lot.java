package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Lot extends JPanel {
    private int x, y;
    private boolean isClicked;
    private String state;
    Lot(int x, int y) {
        this.isClicked = false;
        this.x = x;
        this.y = y;
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.DARK_GRAY);
        JLabel p = new JLabel("P");
        p.setFont(new Font("Arial", Font.BOLD, 30));
        p.setForeground(Color.LIGHT_GRAY);
        this.add(p);
    }

    public void setClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public int getGridX() {
        return x;
    }
    public int getGridY() {
        return y;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
}