package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

public class LotPanel extends JPanel {
    private Lot lot;
    private JLabel p;
    public LotPanel(Lot lot) {
        this.lot = lot;
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.DARK_GRAY);
        p = new JLabel("P");
        p.setFont(new Font("Arial", Font.BOLD, 30));
        p.setForeground(Color.LIGHT_GRAY);
        lotState(lot);
        this.add(p);
    }

    public void setListener(MouseListener mouseAdapter) {
        this.addMouseListener(mouseAdapter);
    }

    public Lot getLot() {
        return lot;
    }

    private void changeState(Color c) {
        if(c!=Color.GREEN){
            p.setText("");
        }
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.setBackground(c);
    }



    public void lotState(Lot lot) {
        switch (lot.getState()) {
            case "Occupied":
                changeState(Color.GREEN);
                break;
            case "Lot":
                changeState(Color.RED);
                break;
            case "Road":
                changeState(Color.BLACK);
                break;
            case "Entrance":
                changeState(Color.YELLOW);
                break;
            case "Exit":
                changeState(Color.MAGENTA);
                break;
        }

    }
}
