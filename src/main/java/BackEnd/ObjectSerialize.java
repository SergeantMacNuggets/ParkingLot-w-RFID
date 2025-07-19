package BackEnd;

import javax.swing.*;
import java.io.*;

public class ObjectSerialize {

    public static void serializeDataOut(ParkingLot ish, String file)throws IOException {
        String fileName= file+".txt";
        FileOutputStream fileOut= new FileOutputStream(fileName);
        ObjectOutputStream outStream= new ObjectOutputStream(fileOut);
        outStream.writeObject(ish);
        outStream.close();
    }

    public static ParkingLot serializeDataIn(String fileName){
        try {
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            ParkingLot parkingLot = (ParkingLot) ois.readObject();
            ois.close();
            return parkingLot;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File Not Found");
        }
        return null;
    }
}
