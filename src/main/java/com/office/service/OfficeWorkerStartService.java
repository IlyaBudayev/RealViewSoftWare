package com.office.service;

import com.office.entity.Employee;
import com.office.repo.EmployeeRepo;
import com.office.repo.PhoneRepo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static com.office.ui.UIHelper.createButton;
import static com.office.ui.UIHelper.createLayeredPane;
import static com.office.ui.UIHelper.createScrollPane;

public class OfficeWorkerStartService extends JFrame {
    private JPanel trianglePanel;
    private JTable table;
    private JButton showButton;

    EmployeeRepo employees = new EmployeeRepo();
    PhoneRepo phones = new PhoneRepo();

    private static final String APP_TITLE = "Office workers";
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;

    public OfficeWorkerStartService() {

        setupMainFrame();

        initializeDataBase();

        uiArrangements();
    }

    private void uiArrangements() {
        // Create the custom triangle panel
        trianglePanel = createTrianglePanel();
        trianglePanel.setBounds(0, getY(), getWidth(), getHeight());

        // Create the layered pane
        JLayeredPane layeredPane = createLayeredPane(0, 0, getWidth(), getHeight());
        add(layeredPane);

        // Add the triangle panel to the layered pane
        layeredPane.add(trianglePanel, Integer.valueOf(1));


        // Create database table
        createDataBaseTable();
        JScrollPane scrollPane = createScrollPane(table, 0, 0, getWidth() / 2, getHeight() / 2);
        layeredPane.add(scrollPane, Integer.valueOf(2));

        // Create the button and listener start
        showButton = createButton("Show Phones", getX(), getY() * 2, 100, 30);
        showPhonesButtonListener();
        layeredPane.add(showButton, Integer.valueOf(3));

        // Add component listener to update the bounds of the triangle panel and table
        resizeComponentListener(scrollPane);
    }

    private void initializeDataBase() {
        // Fill the database
        DataBaseInitializationService dbInitializationService = new DataBaseInitializationService(employees, phones);
        dbInitializationService.initializeDatabase();
    }

    private void setupMainFrame() {
        setTitle(APP_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void resizeComponentListener(JScrollPane scrollPane) {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Update the bounds of the triangle panel
                int w = getWidth();
                int h = getHeight();
                trianglePanel.setBounds(0, 0, w, h);

                // Update the bounds of the table
                int tableWidth = getWidth() / 2;
                int tableHeight = getHeight() / 2;
                int tableX = (getWidth() - tableWidth) / 2;
                int tableY = (getHeight() - tableHeight) / 2;
                scrollPane.setBounds(tableX, tableY, tableWidth, tableHeight);
                table.setBounds(tableX, tableY, tableWidth, tableHeight);

                // Update the button bounds
                showButton.setBounds(tableX, tableY * 3, tableWidth, showButton.getHeight());
            }
        });
    }

    private void showPhonesButtonListener() {
        showButton.addActionListener(e -> {
            // Worker name request
            String name = JOptionPane.showInputDialog(OfficeWorkerStartService.this, "Enter the worker name:");
            if (name != null && !name.isEmpty()) {
                // Get employee by name
                Employee employee = employees.getEmployeeByName(name);
                if (employee != null) {
                    // Get phones by employee ID
                    int employeeId = employee.getId();
                    String phoneNumbers = phones.getPhonesById(employeeId).toString();
                    if (phoneNumbers.isEmpty()) {
                        JOptionPane.showMessageDialog(OfficeWorkerStartService.this, "No phone numbers found for the worker.");
                    } else {
                        JOptionPane.showMessageDialog(OfficeWorkerStartService.this, "Phone numbers of " + name + ":\n" + phoneNumbers);
                        // Hide the triangle
                        trianglePanel.setVisible(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(OfficeWorkerStartService.this, "No worker found with the given name.");
                }
            }
        });
    }

    // This method creates and returns a JPanel that displays a blue triangle
    private JPanel createTrianglePanel() {
        final JPanel trianglePanel;
        trianglePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Polygon p = new Polygon();
                p.addPoint(0, h);
                p.addPoint(w / 2, 0);
                p.addPoint(w, h);
                g2d.setColor(Color.BLUE);
                g2d.fill(p);
            }
        };
        return trianglePanel;
    }

    // This method creates a JTable that displays employee data in a database
    private void createDataBaseTable() {
        // Create column names and row data for the JTable
        String[] columnNames = {"ID", "Name", "Phone Numbers"};
        Object[][] rowData = new Object[employees.getValues().size()][3];
        int i = 0;
        for (Employee employee : employees.getValues()) {
            int id = employee.getId();
            rowData[i][0] = id;
            rowData[i][1] = employee.getName();
            rowData[i][2] = phones.getPhonesById(id);
            i++;
        }
        // Create a DefaultTableModel using the column names and row data
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        // Create a JTable using the DefaultTableModel
        table = new JTable(model);
        table.setOpaque(false);
    }


}