package GUI;


import BackEnd.ParkingLot;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
    private JRadioButton editLot;
    private JRadioButton editRoad;
    private JRadioButton editExit;
    private JRadioButton editEnter;
    private JTextField rfidInput;
    private JTextField RFIDRegisterInput;

    public void start() {
        this.parkingLot = new ParkingLot(10,10);
        this.editMode = false;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1200, 800));
        this.setLayout(new BorderLayout());
        this.setTitle("Parking Lot System");
        this.setJMenuBar(new MenuBar());
        south = southPanel();

        this.add(northPanel(),BorderLayout.NORTH);
        this.add(generateLot(), BorderLayout.CENTER);
        this.add(leftPanel(), BorderLayout.WEST);
        this.add(rightPanel(south), BorderLayout.EAST);
        this.add(south, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }


    public static ParkingWindow getInstance() {
        if(parkingWindow == null) {
            parkingWindow = new ParkingWindow();
        }
        return parkingWindow;
    }

    private JPanel generateLot() {
        JPanel p = new JPanel();
        int x = parkingLot.getColSize(), y = parkingLot.getRowSize();
        if(x != 0 && y != 0){
            p.setLayout(new GridLayout(y, x, 5, 5));
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    Lot lot = new Lot();
                    p.add(lot);
                }
            }
        }
        return p;
    }

    private JPanel northPanel() {
        JPanel p = new JPanel();

        JPanel left = new JPanel();
        JPanel right = new JPanel();
        right.setBorder(BorderFactory.createEmptyBorder(20,0,20,40));
        p.setPreferredSize(new Dimension(0, 100));


        p.setLayout(new BorderLayout());
        left.add(new JButton("New") {{this.setPreferredSize(new Dimension(150,70));
                                    this.setBackground(LocalColor.buttonColor);
                                    this.setFont(new Font("Ariel", Font.BOLD,24));}});
        left.add(new JButton("Open"){{this.setPreferredSize(new Dimension(150,70));
                                    this.setBackground(LocalColor.buttonColor);
                                    this.setFont(new Font("Ariel", Font.BOLD,24));}});
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

            rfidInput = new JTextField();
            RFIDRegisterInput = new JTextField();
            rfidInput.setPreferredSize(new Dimension(0, 40));
            RFIDRegisterInput.setPreferredSize(new Dimension(0,40));
            rfidInput.addActionListener(e->{
                        System.out.println(rfidInput.getText());
                        rfidInput.setText("");
                    }
            );

            p.setPreferredSize(new Dimension(200,0));

            top.add(new JLabel("Enter RFID Number: ") {{this.setFont(new Font("Ariel", Font.BOLD, 16));}}, "wrap");
            top.add(rfidInput, "width 95%");
            top.add(new JLabel(),"wrap 50");
            top.add(new JLabel("Register an RFID Number: ") {{this.setFont(new Font("Ariel", Font.BOLD, 15));}}, "wrap");
            top.add(RFIDRegisterInput, "width 95%");
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

    private JPanel rightPanel(JPanel south) {
        Font f = new Font("Ariel", Font.BOLD, 12);
        editLot = new JRadioButton("Lot");
        editRoad = new JRadioButton("Road");
        editEnter = new JRadioButton("Entrance");
        editExit = new JRadioButton("Exit");
        editLot.setEnabled(false);
        editRoad.setEnabled(false);
        editEnter.setEnabled(false);
        editExit.setEnabled(false);
        ButtonGroup editRadio = new ButtonGroup();
        editRadio.add(editEnter);
        editRadio.add(editLot);
        editRadio.add(editExit);
        editRadio.add(editRoad);
        Object[][] data = {
                {"Row Count", parkingLot.getRowSize()},
                {"Column Count", parkingLot.getColSize()},
                {"Total Cell Count", parkingLot.getRowSize() * parkingLot.getColSize()},
                {"Total Available Cell", 12},
                {"Total Road Cell", 10}
        };
        Object[] label = {"Label", "Count"};
        JTable table = new JTable(data,label);
        table.setFont(new Font("Ariel", Font.BOLD, 12));
        JPanel p = new JPanel();
        JPanel top = new JPanel(new GridLayout(1,1));
        JPanel bottom = new JPanel(new MigLayout());
        table.setRowHeight(50);
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
        bottom.add(new JButton("Save"), "width 100%");
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
}


class MenuBar extends JMenuBar {

    MenuBar() {
        JMenu[] menu = {new JMenu("File"), new JMenu("View"), new JMenu("Help")};

        for(JMenu m: menu) {
            this.add(m);
        }
    }
}