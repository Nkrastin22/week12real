/*This program calculated carbon footprints of a building car and bicycle with the included GUI aspects
 * Assignment:10 
 * Nick Krastin
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Define the interface CarbonFootprint
interface CarbonFootprint {
    double getCarbonFootprint();
}

// Define the Building class
class Building implements CarbonFootprint {
    private int numFloors;
    private int numRooms;
    private boolean hasElevator;

    public Building(int numFloors, int numRooms, boolean hasElevator) {
        this.numFloors = numFloors;
        this.numRooms = numRooms;
        this.hasElevator = hasElevator;
    }

    public double getCarbonFootprint() {
        // Calculate the carbon footprint of the building based on its attributes
        double carbonFootprint = 0.0;
        if (hasElevator) {
            carbonFootprint += 100.0;
        }
        carbonFootprint += numFloors * 50.0;
        carbonFootprint += numRooms * 10.0;
        return carbonFootprint;
    }

    public String toString() {
        return "Building with " + numFloors + " floors, " + numRooms + " rooms, and " +
                (hasElevator ? "an elevator" : "no elevator");
    }
}

// Define the Car class
class Car implements CarbonFootprint {
    private String make;
    private String model;
    private double mpg;
    private double milesDriven;

    public Car(String make, String model, double mpg, double milesDriven) {
        this.make = make;
        this.model = model;
        this.mpg = mpg;
        this.milesDriven = milesDriven;
    }

    public double getCarbonFootprint() {
        // Calculate the carbon footprint of the car based on its attributes
        return milesDriven / mpg * 19.64;
    }

    public String toString() {
        return "Car: " + make + " " + model;
    }
}

// Define the Bicycle class
class Bicycle implements CarbonFootprint {
    private String type;
    private int numGears;

    public Bicycle(String type, int numGears) {
        this.type = type;
        this.numGears = numGears;
    }

    public double getCarbonFootprint() {
        // Calculate the carbon footprint of the bicycle (assumed to be zero)
        return 0.0;
    }

    public String toString() {
        return "Bicycle: " + type + " with " + numGears + " gears";
    }
}

// Define the application class
class CarbonFootprintApp {
    private static ArrayList<CarbonFootprint> carbonFootprints = new ArrayList<>();

    public static void main(String[] args) {
        // Create objects of each of the three classes
        Building building = new Building(3, 10, true);
        Car car = new Car("Honda", "Civic", 60.0, 5002.0);
        Bicycle bicycle = new Bicycle("Mountain", 32);

        // Add objects to the ArrayList
        carbonFootprints.add(building);
        carbonFootprints.add(car);
        carbonFootprints.add(bicycle);

        // Create the GUI components
        JFrame frame = new JFrame("Carbon Footprint App");
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JLabel numFloorsLabel = new JLabel("Number of floors:");
        JTextField numFloorsField = new JTextField();
        JLabel numRoomsLabel = new JLabel("Number of rooms:");
    JTextField numRoomsField = new JTextField();
    JLabel hasElevatorLabel = new JLabel("Has elevator:");
    JCheckBox hasElevatorCheckBox = new JCheckBox();
    JButton addBuildingButton = new JButton("Add Building");

    // Add action listener to the button
    addBuildingButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Get input values from the text fields and check box
            int numFloors = Integer.parseInt(numFloorsField.getText());
            int numRooms = Integer.parseInt(numRoomsField.getText());
            boolean hasElevator = hasElevatorCheckBox.isSelected();

            // Create a new Building object with the input values
            Building newBuilding = new Building(numFloors, numRooms, hasElevator);

            // Add the new Building object to the ArrayList
            carbonFootprints.add(newBuilding);

            // Show a confirmation message
            JOptionPane.showMessageDialog(null, "Building added!");

            // Clear the input fields and check box
            numFloorsField.setText("");
            numRoomsField.setText("");
            hasElevatorCheckBox.setSelected(false);
        }
    });

    // Add components to the input panel
    inputPanel.add(numFloorsLabel);
    inputPanel.add(numFloorsField);
    inputPanel.add(numRoomsLabel);
    inputPanel.add(numRoomsField);
    inputPanel.add(hasElevatorLabel);
    inputPanel.add(hasElevatorCheckBox);
    inputPanel.add(new JLabel());
    inputPanel.add(addBuildingButton);

    // Create the list panel
    JPanel listPanel = new JPanel(new BorderLayout());
    JLabel listLabel = new JLabel("Carbon Footprints:");
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> carbonFootprintsList = new JList<>(listModel);
    JScrollPane listScrollPane = new JScrollPane(carbonFootprintsList);

    // Add the carbon footprints to the list model
    for (CarbonFootprint cf : carbonFootprints) {
        listModel.addElement(cf.toString() + " - " + String.format("%.2f", cf.getCarbonFootprint()) + " lbs CO2e");
    }

    // Add components to the list panel
    listPanel.add(listLabel, BorderLayout.NORTH);
    listPanel.add(listScrollPane, BorderLayout.CENTER);

    // Create the main panel
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(inputPanel, BorderLayout.NORTH);
    mainPanel.add(listPanel, BorderLayout.CENTER);

    // Set up the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 400);
    frame.setContentPane(mainPanel);
    frame.setVisible(true);
}
}