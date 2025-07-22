package GUI;

import BackEnd.Car;
import java.io.Serializable;

public class Lot implements Serializable{
    private int x, y;
    private boolean isClicked;
    private boolean isAvailable;
    private String state;
    private Car car;
    public Lot(int x, int y) {
        this.isClicked = false;
        this.isAvailable = true;
        this.x = x;
        this.y = y;
        state = "";

    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Car getCar() {
        return car;
    }

    public void removeCar(Car car) {
        this.car = null;
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public boolean isClicked() {
        return isClicked;
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