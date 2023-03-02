package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

public class OfficeWorkerApplication extends JFrame {
    private final JPanel trianglePanel;
    private JTable table;
    private final JButton showButton;
    private final Map<Integer, Employee> employees;

    public OfficeWorkerApplication() {

        // Set up the main frame
        setTitle("Office workers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Fill the database
        employees = new HashMap<>();
        Employee employee1 = new Employee(1, "Sanchez");
        employee1.addPhone(new Phone("111-111-1111"));
        employee1.addPhone(new Phone("222-222-2222"));
        employees.put(1, employee1);
        Employee employee2 = new Employee(2, "Smith");
        employee2.addPhone(new Phone("333-333-3333"));
        employees.put(2, employee2);
        Employee employee3 = new Employee(3, "Broflovski");
        employee3.addPhone(new Phone("444-444-4444"));
        employee3.addPhone(new Phone("555-555-5555"));
        employee3.addPhone(new Phone("666-666-6666"));
        employees.put(3, employee3);

        // Create the custom triangle panel
        trianglePanel = createTrianglePanel();
        trianglePanel.setBounds(0, getY(), getWidth(), getHeight());

        // Create the layered pane
        JLayeredPane layeredPane = createLayeredPane();
        add(layeredPane);

        // Add the triangle panel to the layered pane
        layeredPane.add(trianglePanel, Integer.valueOf(1));


        // Create database table
        createDataBaseTable();
        JScrollPane scrollPane = createScrollPane();
        layeredPane.add(scrollPane, Integer.valueOf(2));

        // Create the button and listener start
        showButton = createButton();
        buttonListener();
        layeredPane.add(showButton, Integer.valueOf(3));

        // Add component listener to update the bounds of the triangle panel and table
        componentListener(scrollPane);
    }

    private void componentListener(JScrollPane scrollPane) {
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
                showButton.setBounds(tableX, tableY*3, tableWidth, showButton.getHeight());
            }
        });
    }

    private void buttonListener() {
        showButton.addActionListener(e -> {
                // Worker name request
                String name = JOptionPane.showInputDialog(OfficeWorkerApplication.this, "Enter the worker name:");
                if (name != null && !name.isEmpty()) {
                    // Database output and showing results in the dialog window
                    String phoneNumbers = getPhoneNumbersByName(name);
                    if (phoneNumbers.isEmpty()) {
                        JOptionPane.showMessageDialog(OfficeWorkerApplication.this, "No worker found.");
                    } else {
                        JOptionPane.showMessageDialog(OfficeWorkerApplication.this, "Phone numbers of " + name + ":\n" + phoneNumbers);
                        // Hide the triangle
                        trianglePanel.setVisible(false);
                    }
                }
            });
    }

    // This method creates and returns a JButton with "Show phones" as text
    private JButton createButton() {
        final JButton removeButton;
        removeButton = new JButton("Show phones");
        removeButton.setBounds(getX(), getY()*2, 100, 30);
        return removeButton;
    }

    // This method creates and returns a JScrollPane with a JTable as its view
    private JScrollPane createScrollPane() {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, getWidth() / 2, getHeight() / 2);
        return scrollPane;
    }

    // This method creates and returns a JLayeredPane
    private JLayeredPane createLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, getWidth(), getHeight());
        return layeredPane;
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
        Object[][] rowData = new Object[employees.size()][3];
        int i = 0;
        for (Employee employee : employees.values()) {
            rowData[i][0] = employee.getId();
            rowData[i][1] = employee.getName();
            rowData[i][2] = employee.getPhoneNumbersAsString();
            i++;
        }
        // Create a DefaultTableModel using the column names and row data
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
        // Create a JTable using the DefaultTableModel
        table = new JTable(model);
        table.setOpaque(false);
    }

    // This method returns the phone numbers of an employee with the given name
    private String getPhoneNumbersByName(String name) {
        for (Employee employee : employees.values()) {
            if (employee.getName().equalsIgnoreCase(name)) {
                return employee.getPhoneNumbersAsString();
            }
        }
        return "";
    }

    // The main method that creates and shows the OfficeWorkerApplication JFrame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OfficeWorkerApplication frame = new OfficeWorkerApplication();
            frame.setVisible(true);
        });
    }

}