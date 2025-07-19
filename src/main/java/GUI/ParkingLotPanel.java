package GUI;

import BackEnd.ParkingLot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ParkingLotPanel extends JPanel {
    private ParkingLot parkingLot;
    private int roadCell;
    private int unavailableCell;
    ParkingLotPanel(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        int x = parkingLot.getColSize(), y = parkingLot.getRowSize();
        roadCell = 0;
        unavailableCell = 0;
        if(x != 0 && y != 0){
            this.setLayout(new GridLayout(y, x, 5, 5));
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    parkingLot.addLot(new Lot(j,i),j, i);
                    Lot lot = parkingLot.getLot(j,i);
                    lot.addMouseListener(mouseListener());
                    this.add(lot);
                }
            }
        }
    }


    private MouseListener mouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ParkingWindow instance = ParkingWindow.getInstance();
                Lot panel = (Lot) e.getSource();
                if (!panel.isClicked()){
                    panel.setClicked(true);
                    if (instance.getMode()) {
                        for (int i = 0; i < instance.getRadioButtons().length; i++) {
                            if (instance.getRadioButtons()[i].isSelected()) {
                                try {
                                    panel.removeAll();
                                    panel.revalidate();
                                    panel.repaint();
                                    lotState(i, panel);
                                } catch (Exception x) {
                                    break;
                                }
                            }
                        }
                    }
                }
                else if(instance.getMode() && SwingUtilities.isRightMouseButton(e)) {
                        panel.setBackground(Color.DARK_GRAY);
                        JLabel l = new JLabel("P");
                        l.setFont(new Font("Arial", Font.BOLD, 30));
                        l.setForeground(Color.LIGHT_GRAY);
                        lotState(panel);
                        panel.setClicked(false);
                        panel.add(l);
                        panel.revalidate();
                        panel.repaint();
                }
            }
        };
    }
    private void lotState(int index, Lot p) throws Exception{
        ParkingWindow instance = ParkingWindow.getInstance();
        switch(index) {
            case 0:
                parkingLot.setUnavailableCells(++unavailableCell);
                p.setBackground(Color.red);
                instance.getTable().setValueAt(parkingLot.getAvailableCells()-1, 3,1);
                instance.getTable().setValueAt(parkingLot.getUnavailableCells(), 4,1);
                parkingLot.setAvailableCells(parkingLot.getAvailableCells()-1);
                p.setState("Lot");
                break;
            case 1:
                parkingLot.setRoadCells(++roadCell);
                p.setBackground(Color.black);
                instance.getTable().setValueAt(parkingLot.getAvailableCells()-1, 3,1);
                instance.getTable().setValueAt(parkingLot.getRoadCells(), 5,1);
                parkingLot.setAvailableCells(parkingLot.getAvailableCells()-1);
                p.setState("Road");
                break;
            case 2:
                if(p.getGridY() == 0 || p.getGridY() == parkingLot.getRowSize()-1 || p.getGridX() == 0 || p.getGridX() == parkingLot.getColSize()-1){
                    parkingLot.setRoadCells(++roadCell);
                    p.setBackground(Color.orange);
                    instance.getTable().setValueAt(parkingLot.getAvailableCells() - 1, 3, 1);
                    instance.getTable().setValueAt(parkingLot.getRoadCells(), 5, 1);
                    parkingLot.setAvailableCells(parkingLot.getAvailableCells() - 1);
                    p.setState("Entrance");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Entrance must be on the corner of the parking lot");
                    throw new Exception();
                }
                break;
            case 3:
                if(p.getGridY() == 0 || p.getGridY() == parkingLot.getRowSize()-1 || p.getGridX()==0 || p.getGridX() == parkingLot.getColSize()-1){
                    parkingLot.setRoadCells(++roadCell);
                    p.setBackground(Color.magenta);
                    instance.getTable().setValueAt(parkingLot.getAvailableCells() - 1, 3, 1);
                    instance.getTable().setValueAt(parkingLot.getRoadCells(), 5, 1);
                    parkingLot.setAvailableCells(parkingLot.getAvailableCells() - 1);
                    p.setState("Exit");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Exit must be on the corner of the parking lot");
                    throw new Exception();
                }
                break;
        }
        p.setBorder(BorderFactory.createEmptyBorder());
    }

    private void lotState(Lot p) {
        ParkingWindow instance = ParkingWindow.getInstance();
        switch(p.getState()) {
            case "Lot":
                parkingLot.setUnavailableCells(--unavailableCell);
                instance.getTable().setValueAt(parkingLot.getAvailableCells()+1, 3,1);
                instance.getTable().setValueAt(parkingLot.getUnavailableCells(), 4,1);
                parkingLot.setAvailableCells(parkingLot.getAvailableCells()+1);
                break;
            case "Road", "Entrance", "Exit":
                parkingLot.setRoadCells(--roadCell);
                instance.getTable().setValueAt(parkingLot.getAvailableCells() + 1, 3,1);
                instance.getTable().setValueAt(parkingLot.getRoadCells(), 5,1);
                parkingLot.setAvailableCells(parkingLot.getAvailableCells() + 1);
                break;
        }
    }

}
