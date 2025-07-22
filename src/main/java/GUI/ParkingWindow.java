package GUI;


import BackEnd.Car;
import BackEnd.Coordinates;
import BackEnd.ObjectSerialize;
import BackEnd.ParkingLot;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

enum OPT {
    CREATE,
    LOAD
}

interface LocalColor {
    Color inputColor = new Color(52,87,52);
    Color editColor = new Color(197, 28,44);
    Color buttonColor = new Color(46,119,174);
}

public class ParkingWindow extends JFrame {
    private static ParkingWindow parkingWindow;
    private ParkingLot parkingLot;
    private boolean editMode;
    private JPanel south;
    private ParkingLotPanel currentLotPanel;
    private JLabel lotNameLabel;
    private JButton newButton;
    private JButton openButton;
    private JRadioButton editLot;
    private JRadioButton editRoad;
    private JRadioButton editExit;
    private JRadioButton editEnter;
    private JTextField rfidInput;
    private JTextField rfidRegisterInput;
    private JTable table;

    public void start() {
        this.parkingLot = new ParkingLot("",0,0);
        this.editMode = false;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1200, 800));
        this.setLayout(new BorderLayout());
        this.setTitle("Parking Lot System");
        this.setJMenuBar(new MenuBar());
        south = southPanel();
        currentLotPanel = new ParkingLotPanel(parkingLot);
        this.add(northPanel(),BorderLayout.NORTH);
        this.add(currentLotPanel, BorderLayout.CENTER);
        this.add(leftPanel(), BorderLayout.WEST);
        this.add(rightPanel(south), BorderLayout.EAST);
        this.add(south, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void panelSwitcher(ParkingLotPanel p) {
        try {
            this.remove(currentLotPanel);
            this.revalidate();
            this.repaint();
            currentLotPanel = p;
            this.add(currentLotPanel, BorderLayout.CENTER);
        } catch (NullPointerException e) {
            System.out.println("Reach");
        }
    }


    public static ParkingWindow getInstance() {
        if(parkingWindow == null) {
            parkingWindow = new ParkingWindow();
        }
        return parkingWindow;
    }
    public boolean getMode() {

        return editMode;
    }

    public JRadioButton[] getRadioButtons() {
        return new JRadioButton[]{editLot,editRoad,editEnter,editExit};
    }

    private JPanel northPanel() {
        JPanel p = new JPanel();

        JPanel left = new JPanel();
        JPanel right = new JPanel();
        newButton = new JButton("New") {{this.setPreferredSize(new Dimension(150,50));
                                         this.setBackground(LocalColor.buttonColor);
                                         this.setFont(new Font("Ariel", Font.BOLD,24));}};

        openButton = new JButton("Open"){{this.setPreferredSize(new Dimension(150,50));
                                         this.setBackground(LocalColor.buttonColor);
                                         this.setFont(new Font("Ariel", Font.BOLD,24));}};
        openButton.addActionListener(e->{
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Save Parking Lot","txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                parkingLot = ObjectSerialize.serializeDataIn(chooser.getSelectedFile().getName());
                this.updateParkingLot(parkingLot,OPT.LOAD);
                table.setValueAt(parkingLot.getAvailableCells(), 3,1);
                table.setValueAt(parkingLot.getUnavailableCells(), 4,1);
                table.setValueAt(parkingLot.getRoadCells(), 5,1);
            }
        });
        newButton.addActionListener(createParkingLot());
        right.setBorder(BorderFactory.createEmptyBorder(20,0,20,40));
        p.setPreferredSize(new Dimension(0, 100));


        p.setLayout(new BorderLayout());
        left.add(newButton);
        left.add(openButton);
        right.add(new JLabel("Parking Lot System") {{this.setFont(new Font("Ariel", Font.BOLD, 32));
                                                    this.setForeground(LocalColor.buttonColor);}});
        p.add(left,BorderLayout.WEST);
        p.add(right,BorderLayout.EAST);
        return p;
    }

    private JPanel leftPanel() {
        JPanel p = new JPanel(new GridLayout(2,1));
        try {
            BufferedImage logo = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream("/rfid.png" )));

            JPanel top = new JPanel(new MigLayout());
            JPanel bottom = new JPanel(new MigLayout());
            lotNameLabel = new JLabel("") {{this.setFont(new Font("Ariel", Font.BOLD, 15));}};
            rfidInput = new JTextField();
            rfidRegisterInput = new JTextField();
            rfidInput.setPreferredSize(new Dimension(0, 40));
            rfidRegisterInput.setPreferredSize(new Dimension(0,40));
            rfidInput.addActionListener(e->
                    {
                        if(!editMode){
                            ParkingLotPanel temp = currentLotPanel;
                            if(parkingLot.getMap().containsKey(rfidInput.getText())) {
                                int parkNumX = parkingLot.getMap().get(rfidInput.getText()).x();
                                int parkNumY = parkingLot.getMap().get(rfidInput.getText()).y();
                                temp.free(parkNumX,parkNumY);
                                parkingLot.free(parkNumX, parkNumY);
                                parkingLot.getMap().remove(rfidInput.getText());
                            }
                            else {
                                if(parkingLot.authorize(rfidInput.getText())){
                                    while (true) {
                                        if(parkingLot.getAvailableCells() <= 0) {
                                            JOptionPane.showMessageDialog(null, "Parking Lot is Fully Occupied");
                                            break;
                                        }
                                        int parkNumX = new Random().nextInt(parkingLot.getColSize());
                                        int parkNumY = new Random().nextInt(parkingLot.getRowSize());
                                        Lot lot = parkingLot.getLot(parkNumX, parkNumY);
                                        lot.setState("Occupied");
                                        if (!lot.isOccupied() && lot.isAvailable()) {
                                            temp.occupied(new Car(rfidInput.getText()), parkNumX, parkNumY);
                                            parkingLot.getMap().put(rfidInput.getText(), new Coordinates(parkNumX, parkNumY));
                                            break;
                                        }
                                    }
                                 
                                }
                                
                            }
                            rfidInput.setText("");
                        }
                    }
            );

            rfidRegisterInput.addActionListener(e->{
                String input = rfidRegisterInput.getText();
                if(parkingLot.authorize(input)) {

                    int opt = JOptionPane.showConfirmDialog(this, "ID Number: " + input + " already exist would you like to delete it?","Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(opt == JOptionPane.YES_OPTION) {
                        ParkingLotPanel temp = currentLotPanel;
                        parkingLot.removeCard(input);
                        try {
                            int parkNumX = parkingLot.getMap().get(input).x();
                            int parkNumY = parkingLot.getMap().get(input).y();
                            parkingLot.free(parkNumX, parkNumY);
                            temp.free(parkNumX, parkNumY);
                            parkingLot.getMap().remove(input);
                        } catch(NullPointerException x) {
                            x.printStackTrace();
                        }
                    }
                }
                else {
                    parkingLot.register(input);
                }
                rfidRegisterInput.setText("");
            });

            p.setPreferredSize(new Dimension(200,0));

            top.add(new JLabel("Enter RFID Number: ") {{this.setFont(new Font("Ariel", Font.BOLD, 16));}}, "wrap");
            top.add(rfidInput, "width 95%");
            top.add(new JLabel(),"wrap 50");
            top.add(new JLabel("Register an RFID Number: ") {{this.setFont(new Font("Ariel", Font.BOLD, 15));}}, "wrap");
            top.add(rfidRegisterInput, "width 95%");
            top.add(new JLabel(), "wrap 50");
            top.add(new JLabel("Parking Lot Name: "){{this.setFont(new Font("Ariel", Font.BOLD, 15));}}, "wrap");
            top.add(lotNameLabel);
            bottom.add(new JLabel(new ImageIcon(logo)), "gapleft 15%");
            bottom.add(new JLabel(),"wrap 50");
            bottom.add(new JLabel("Connected!") {{this.setFont(new Font("Ariel", Font.BOLD, 22));
                                                    this.setForeground(Color.yellow);}}, "gapleft 15%");

            p.add(top);
            p.add(bottom);

        } catch (IOException e) {
            System.out.println("Image Not Found");
        }

        return p;
    }

    public void updateParkingLot(ParkingLot parkingLot, OPT opt) {
        ParkingLotPanel parkingLotPanel = new ParkingLotPanel(parkingLot);
        parkingWindow.setParkingLot(parkingLot);
        parkingWindow.panelSwitcher(parkingLotPanel);
        switch (opt) {
            case CREATE -> parkingLotPanel.createPanel();
            case LOAD -> parkingLotPanel.loadPanel();
        }
        lotNameLabel.setText(parkingLot.getName());
        table.setValueAt(parkingLot.getRowSize(), 0, 1);
        table.setValueAt(parkingLot.getColSize(), 1, 1);
        table.setValueAt(parkingLot.getRowSize() * parkingLot.getColSize(), 2, 1);
        table.setValueAt(parkingLot.getRowSize() * parkingLot.getColSize(), 3, 1);
        table.setValueAt(0,4,1);
        table.setValueAt(0,5,1);
        table.setValueAt(0,6,1);
    }

    public JTable getTable() {
        return table;
    }

    private JPanel rightPanel(JPanel south) {
        ButtonGroup editRadio = new ButtonGroup();
        editLot = new JRadioButton("Lot");
        editRoad = new JRadioButton("Road");
        editEnter = new JRadioButton("Entrance");
        editExit = new JRadioButton("Exit");
        editLot.setEnabled(false);
        editRoad.setEnabled(false);
        editEnter.setEnabled(false);
        editExit.setEnabled(false);
        editRadio.add(editEnter);
        editRadio.add(editLot);
        editRadio.add(editExit);
        editRadio.add(editRoad);
        Object[][] data = {
                {"Row Count", 0},
                {"Column Count", 0},
                {"Total Cell Count", 0},
                {"Total Available Cell", 0},
                {"Total Unavailable Cell", 0},
                {"Total Road Cell", 0},
                {"Total Parked Cars", 0}
        };
        Object[] label = {"Label", "Count"};
        table = new JTable(data,label);
        table.setFont(new Font("Ariel", Font.BOLD, 12));
        JPanel p = new JPanel();
        JPanel top = new JPanel(new GridLayout(1,1));
        JPanel bottom = new JPanel(new MigLayout());
        table.setRowHeight(40);
        top.add(new JScrollPane(table));
        bottom.add(new JButton("Edit") {{
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editMode = !editMode;
                    if(editMode) {
                        south.setBackground(LocalColor.editColor);
                    }
                    else {
                        south.setBackground(LocalColor.inputColor);
                    }
                    editLot.setEnabled(editMode);
                    editRoad.setEnabled(editMode);
                    editEnter.setEnabled(editMode);
                    editExit.setEnabled(editMode);
                }
            });
        }}, "width 100%");
        bottom.add(new JLabel(),"wrap");
        bottom.add(editLot,"wrap 40");
        bottom.add(editRoad, "wrap 40");
        bottom.add(editEnter, "wrap 40");
        bottom.add(editExit, "wrap 40");
        bottom.add(new JButton("Save") {{this.addActionListener(e->{
            try {
                ObjectSerialize.serializeDataOut(parkingLot, lotNameLabel.getText());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });}}, "width 100%");
        p.setLayout(new GridLayout(2,1));
        p.setBackground(new Color(61,62,71));
        p.setPreferredSize(new Dimension(200,0));
        p.add(top);
        p.add(bottom);

        return p;
    }

    private JPanel southPanel() {
        JPanel p = new JPanel();
        p.setLayout(new MigLayout());
        p.setBackground(LocalColor.inputColor); // Green r:52 g:87 b:52   Red r:197 g:28 b:44
        p.setPreferredSize(new Dimension(0,22));
        return p;
    }

    private ActionListener createParkingLot() {
        return _ ->{
            new CreateLotWindow();
        };
    }
}


class MenuBar extends JMenuBar {

    MenuBar() {
        JMenu[] menu = {new JMenu("File"), new JMenu("View"), new JMenu("Help")};

        for(JMenu m: menu) {
            this.add(m);
        }
    }
}