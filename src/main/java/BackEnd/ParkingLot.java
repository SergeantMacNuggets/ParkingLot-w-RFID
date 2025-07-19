package BackEnd;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParkingLot {
    private int colSize, rowSize;
    private int totalCellCount;
    private int availableCells;
    private int roadCells;
    private int unavailableCells;
    private String parkingLotName;
    private final ArrayList<ArrayList<Car>>grid;

    public ParkingLot(String parkingLotName, int rowSize, int colSize) {
        this.parkingLotName = parkingLotName;
        this.colSize = colSize;
        this.rowSize = rowSize;
        this.totalCellCount = this.rowSize * this.colSize;
        this.availableCells = totalCellCount;
        this.roadCells = 0;
        this.unavailableCells = 0;
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

    public String getName() {
        return parkingLotName;
    }

    public int getTotalCellCount() {
        return totalCellCount;
    }

    public int getAvailableCells() {
        return availableCells;
    }

    public int getRoadCells() {
        return roadCells;
    }

    public int getUnavailableCells() {
        return unavailableCells;
    }

    public void setTotalCellCount(int totalCellCount) {
        this.totalCellCount = totalCellCount;
    }

    public void setAvailableCells(int availableCells) {
        this.availableCells = availableCells;
    }

    public void setUnavailableCells(int unavailableCells) {
        this.unavailableCells = unavailableCells;
    }

    public void setRoadCells(int roadCells) {
        this.roadCells = roadCells;
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
