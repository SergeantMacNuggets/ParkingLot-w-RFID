package GUI;

import BackEnd.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class Lot implements Serializable{
    private int x, y;
    private boolean isClicked;
    private boolean isOccupied;
    private String state;
    private Car car;
    Lot(int x, int y) {
        this.isClicked = false;
        this.isOccupied = false;
        this.x = x;
        this.y = y;
        state = "";

    }

    public void setCar(Car car) {
        this.car = car;
        isOccupied = true;
    }

    public Car getCar() {
        return car;
    }

    public void removeCar(Car car) {
        this.car = null;
        isOccupied = false;
    }

    public void setClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public boolean isOccupied(){
        return isOccupied;
    }

    public int getGridX() {
        return x;
    }
    public int getGridY() {
        return y;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }

}