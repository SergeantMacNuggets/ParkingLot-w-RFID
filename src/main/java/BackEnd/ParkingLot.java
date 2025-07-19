package BackEnd;


import GUI.Lot;

import javax.swing.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParkingLot implements Serializable {
    private int colSize, rowSize;
    private int totalCellCount;
    private int availableCells;
    private int roadCells;
    private int unavailableCells;
    private String parkingLotName;
    private final Lot[][] grid;
    public ParkingLot(String parkingLotName, int rowSize, int colSize) {
        this.parkingLotName = parkingLotName;
        this.colSize = colSize;
        this.rowSize = rowSize;
        this.totalCellCount = this.rowSize * this.colSize;
        this.availableCells = totalCellCount;
        this.roadCells = 0;
        this.unavailableCells = 0;
        grid = new Lot[rowSize][colSize];
    }

    public void addLot(Lot lot, int x, int y){
        grid[y][x] = lot;
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


    public void parkCar(Car car, int row, int col) {
        if (grid[row][col] != null) {
            return;
        }
        grid[row][col].setCar(car);
    }

    public Lot getLot(int x, int y) {
        return grid[y][x];
    }

    public Lot[][] getParkingLot() {
        return grid;
    }
}
