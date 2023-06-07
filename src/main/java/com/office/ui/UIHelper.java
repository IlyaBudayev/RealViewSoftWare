package com.office.ui;

import javax.swing.*;
import java.awt.*;

public class UIHelper {

    // This method creates and returns a JButton with "Show phones" as text
    public static JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        return button;
    }

    // This method creates and returns a JScrollPane with a JTable as its view
    public static JScrollPane createScrollPane(Component view, int x, int y, int width, int height) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setBounds(x, y, width, height);
        return scrollPane;
    }

    // This method creates and returns a JLayeredPane
    public static JLayeredPane createLayeredPane(int x, int y, int width, int height) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(x, y, width, height);
        return layeredPane;
    }
}