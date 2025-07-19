package GUI;

import BackEnd.ParkingLot;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CreateLotWindow extends JFrame {
    private ParkingLot parkingLot;
    private JTextField lotName;
    private JTextField rowInput;
    private JTextField colInput;
    private JButton submit;
    public CreateLotWindow() {
        this.setPreferredSize(new Dimension(300,300));
        JPanel p = new JPanel(new GridLayout(7,1,0,10));
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        lotName = new JTextField();
        rowInput = new JTextField();
        colInput = new JTextField();
        submit = new JButton("Create");
        p.add(new JLabel("Enter Parking Lot Name:"));
        p.add(lotName);
        p.add(new JLabel("Enter Number of Rows:"));
        p.add(rowInput);
        p.add(new JLabel("Enter Number of Columns:"));
        p.add(colInput);

        p.add(submit);
        this.submit.addActionListener(e->{
            this.parkingLot = new ParkingLot(lotName.getText(),Integer.parseInt(rowInput.getText()), Integer.parseInt(colInput.getText()));
            ParkingWindow.getInstance().updateParkingLot(parkingLot,OPT.CREATE);
            this.setVisible(false);
            this.dispose();
        });
        this.add(p);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
