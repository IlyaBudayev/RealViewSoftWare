package com.office;

import com.office.service.OfficeWorkerStartService;

import javax.swing.*;

public class ApplicationStarter {

    // The main method that creates and shows the OfficeWorkerApplication JFrame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OfficeWorkerStartService frame = new OfficeWorkerStartService();
            frame.setVisible(true);
        });
    }
}
