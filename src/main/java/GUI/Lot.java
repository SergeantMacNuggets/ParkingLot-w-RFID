package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Lot extends JPanel {
    Lot() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.DARK_GRAY);
        JLabel p = new JLabel("P");
        p.setFont(new Font("Arial", Font.BOLD, 30));
        p.setForeground(Color.LIGHT_GRAY);
        this.add(p);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel panel = (JPanel) e.getSource();
                panel.removeAll();
                panel.setBackground(Color.red);
                panel.setBorder(BorderFactory.createEmptyBorder());
                panel.revalidate(); // Add this line
                panel.repaint(); // Add this line
            }
        });
    }
}