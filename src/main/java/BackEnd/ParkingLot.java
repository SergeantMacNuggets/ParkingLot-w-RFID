package BackEnd;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParkingLot {
    private int colSize, rowSize;
    private final ArrayList<ArrayList<Car>>grid;

    public ParkingLot(int rowSize, int colSize) {
        this.colSize = colSize;
        this.rowSize = rowSize;
        grid = new ArrayList<>();
        for(int i=0; i<colSize;i++) {
            grid.add(new ArrayList<>());
        }
    }

    public ParkingLot() {
        colSize = 0;
        rowSize = 0;
        grid = new ArrayList<>();
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public void addRow(int row) {
        for(int y=0; y<row;y++) {
            grid.add(new ArrayList<>());
        }
    }

    public void addCol(int col) {
        for(int x=0; x<col;x++) {
            grid.get(x).add(null);
        }
    }

    public void parkCar(Car car, int row, int col) {
        if (grid.get(row).get(col) != null) {
            return;
        }
        grid.get(row).add(car);
    }

    public ArrayList<ArrayList<Car>> getParkingLot() {
        return grid;
    }
}
