package BackEnd;


import GUI.Lot;

import javax.swing.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class ParkingLot implements Serializable {
    private int colSize, rowSize;
    private int totalCellCount;
    private int availableCells;
    private int roadCells;
    private int unavailableCells;
    private int parkedCarsCells;
    private String parkingLotName;
    private final Lot[][] grid;
    private final HashMap<String, Coordinates> rfidCards;
    private final LinkedList<String> registerCards;
    public ParkingLot(String parkingLotName, int rowSize, int colSize) {
        this.parkingLotName = parkingLotName;
        this.colSize = colSize;
        this.rowSize = rowSize;
        this.totalCellCount = this.rowSize * this.colSize;
        this.availableCells = totalCellCount;
        this.roadCells = 0;
        this.unavailableCells = 0;
        this.parkedCarsCells = 0;
        rfidCards = new HashMap<>();
        registerCards = new LinkedList<>();
        grid = new Lot[rowSize][colSize];
    }

    public void addLot(Lot lot, int x, int y){
        grid[y][x] = lot;
    }

    public HashMap<String, Coordinates> getMap() {
        return rfidCards;
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

    public int getParkedCarsCells() {
        return parkedCarsCells;
    }

    public void register(String id) {
        registerCards.add(id);
    }

    public boolean authorize(String id) {
        return registerCards.contains(id);
    }

    public void setParkedCarsCells(int parkedCarsCells) {
        this.parkedCarsCells = parkedCarsCells;
    }

    public void removeCard(String id) {
        registerCards.remove(id);
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
        grid[row][col].setCar(car);
    }

    public void free(int x, int y) {
        grid[y][x] = new Lot(x,y);
    }

    public Lot getLot(int x, int y) {
        return grid[y][x];
    }

    public Lot[][] getParkingLot() {
        return grid;
    }
}
