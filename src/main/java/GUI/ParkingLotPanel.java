package GUI;

import BackEnd.Car;
import BackEnd.ParkingLot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ParkingLotPanel extends JPanel {
    private ParkingLot parkingLot;
    private LotPanel[][] gridPanel;
    private int x, y;
    private int roadCell;
    private int availableCell;
    private int unavailableCell;
    private int parkCarCell;
    ParkingLotPanel(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.x = parkingLot.getColSize();
        this.y = parkingLot.getRowSize();
        gridPanel = new LotPanel[y][x];
        roadCell = 0;
        unavailableCell = 0;
        availableCell = parkingLot.getAvailableCells();
        parkCarCell = 0;
    }

    public void createPanel() {
        if(x == 0 || y == 0){
            return;
        }
        else {
            this.setLayout(new GridLayout(y, x, 5, 5));
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    parkingLot.addLot(new Lot(j,i), j, i);
                    LotPanel temp = new LotPanel(parkingLot.getLot(j,i));
                    gridPanel[i][j] = temp;
                    temp.setListener(mouseListener());
                    this.add(temp);
                }
            }
        }
    }

    public void loadPanel() {
        if(x == 0 || y == 0){
            return;
        }
        else {
            this.setLayout(new GridLayout(y, x, 5, 5));
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    LotPanel temp = new LotPanel(parkingLot.getLot(j,i));
                    gridPanel[i][j] = temp;
                    temp.setListener(mouseListener());
                    this.add(temp);
                }
            }
        }
    }

    public void occupied(Car car, int x, int y) {
        ParkingWindow instance = ParkingWindow.getInstance();
        parkingLot.parkCar(car, y, x);
        parkingLot.setParkedCarsCells(++parkCarCell);
        parkingLot.setAvailableCells(--availableCell);
        gridPanel[y][x].setBackground(Color.green);
        parkingLot.getLot(x,y).setAvailable(false);
        instance.getTable().setValueAt(parkingLot.getAvailableCells(), 3,1);
        instance.getTable().setValueAt(parkingLot.getParkedCarsCells(), 6,1);

    }

    public void free(int x, int y) {
        ParkingWindow instance = ParkingWindow.getInstance();
        parkingLot.parkCar(null, y, x);
        parkingLot.getLot(x, y).setState("");
        parkingLot.getLot(x, y).setAvailable(true);
        gridPanel[y][x].setBackground(Color.DARK_GRAY);
        parkingLot.setParkedCarsCells(--parkCarCell);
        parkingLot.setAvailableCells(++availableCell);
        instance.getTable().setValueAt(parkingLot.getAvailableCells(), 3,1);
        instance.getTable().setValueAt(parkingLot.getParkedCarsCells(), 6,1);
    }


    private MouseListener mouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ParkingWindow instance = ParkingWindow.getInstance();
                LotPanel panel = (LotPanel) e.getSource();
                if (!panel.getLot().isClicked() && !SwingUtilities.isRightMouseButton(e)){
                    panel.getLot().setClicked(true);
                    if (instance.getMode()) {
                        for (int i = 0; i < instance.getRadioButtons().length; i++) {
                            if (instance.getRadioButtons()[i].isSelected()) {
                                try {
                                    if(panel.getLot().isAvailable()) {
                                        panel.removeAll();
                                        panel.revalidate();
                                        panel.repaint();
                                        lotState(i, panel);
                                    }
                                } catch (Exception x) {
                                    break;
                                }
                            }
                        }
                    }
                }
                else if(instance.getMode() && SwingUtilities.isRightMouseButton(e)) {
                    if(!panel.getLot().isAvailable() && !panel.getLot().getState().equals("Occupied")) {
                        panel.setBackground(Color.DARK_GRAY);
                        lotState(panel.getLot());
                        panel.getLot().setState("");
                        panel.getLot().setClicked(false);
                        panel.getLot().setAvailable(true);
                        panel.add(panel.getParkLabel());
                        panel.revalidate();
                        panel.repaint();
                    }
                }
            }
        };
    }
    private void lotState(int index, LotPanel p) throws Exception{
        ParkingWindow instance = ParkingWindow.getInstance();
        Lot lot = p.getLot();
        switch(index) {
            case 0:
                parkingLot.setUnavailableCells(++unavailableCell);
                parkingLot.setAvailableCells(--availableCell);
                p.setBackground(Color.red);
                instance.getTable().setValueAt(parkingLot.getAvailableCells(), 3,1);
                instance.getTable().setValueAt(parkingLot.getUnavailableCells(), 4,1);
                lot.setState("Lot");
                lot.setAvailable(false);
                break;
            case 1:
                parkingLot.setRoadCells(++roadCell);
                parkingLot.setAvailableCells(--availableCell);
                p.setBackground(Color.black);
                instance.getTable().setValueAt(parkingLot.getAvailableCells(), 3,1);
                instance.getTable().setValueAt(parkingLot.getRoadCells(), 5,1);
                lot.setState("Road");
                lot.setAvailable(false);
                break;
            case 2:
                if(lot.getGridY() == 0 || lot.getGridY() == parkingLot.getRowSize()-1 || lot.getGridX() == 0 || lot.getGridX() == parkingLot.getColSize()-1){
                    parkingLot.setRoadCells(++roadCell);
                    parkingLot.setAvailableCells(--availableCell);
                    p.setBackground(Color.orange);
                    instance.getTable().setValueAt(parkingLot.getAvailableCells(), 3, 1);
                    instance.getTable().setValueAt(parkingLot.getRoadCells(), 5, 1);
                    lot.setState("Entrance");
                    lot.setAvailable(false);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Entrance must be on the corner of the parking lot");
                    p.setBackground(Color.DARK_GRAY);
                    lotState(p.getLot());
                    p.getLot().setClicked(false);
                    p.add(p.getParkLabel());
                    p.revalidate();
                    p.repaint();
                    throw new Exception();
                }
                break;
            case 3:
                if(lot.getGridY() == 0 || lot.getGridY() == parkingLot.getRowSize()-1 || lot.getGridX()==0 || lot.getGridX() == parkingLot.getColSize()-1){
                    parkingLot.setRoadCells(++roadCell);
                    parkingLot.setAvailableCells(--availableCell);
                    p.setBackground(Color.magenta);
                    instance.getTable().setValueAt(parkingLot.getAvailableCells(), 3, 1);
                    instance.getTable().setValueAt(parkingLot.getRoadCells(), 5, 1);
                    lot.setState("Exit");
                    lot.setAvailable(false);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Exit must be on the corner of the parking lot");
                    p.setBackground(Color.DARK_GRAY);
                    lotState(p.getLot());
                    p.getLot().setClicked(false);
                    p.add(p.getParkLabel());
                    p.revalidate();
                    p.repaint();
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
                parkingLot.setAvailableCells(++availableCell);
                instance.getTable().setValueAt(parkingLot.getAvailableCells(), 3,1);
                instance.getTable().setValueAt(parkingLot.getUnavailableCells(), 4,1);
                parkingLot.setAvailableCells(parkingLot.getAvailableCells()+1);
                break;
            case "Road", "Entrance", "Exit":
                parkingLot.setRoadCells(--roadCell);
                parkingLot.setAvailableCells(++availableCell);
                instance.getTable().setValueAt(parkingLot.getAvailableCells(), 3,1);
                instance.getTable().setValueAt(parkingLot.getRoadCells(), 5,1);
                parkingLot.setAvailableCells(parkingLot.getAvailableCells() + 1);
                break;
        }
    }

}
