package org.example;
import BackEnd.ParkingLot;
import GUI.ParkingWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Scanner;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class Main {
    public static void main(String[] args) {
        FlatLaf.setGlobalExtraDefaults( Collections.singletonMap( "@accentColor", "#009DD1" ) );
        FlatMacDarkLaf.setup();
        SwingUtilities.invokeLater(() -> {
            ParkingWindow.getInstance().start();
        });
    }
}