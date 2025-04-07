package driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.UUID;
import backend.EmergencyRoomSystem;
import backend.Patient;

public class ERPanel extends JPanel {
    private EmergencyRoomSystem system;

    // Input fields
    private JTextField nameField;
    private JComboBox<Integer> severityBox;
    private JTextArea descriptionArea;

    // Output
    private JTextArea outputArea;

    public ERPanel(EmergencyRoomSystem system) {
        this.system = system;

        BorderLayout mainLayout = new BorderLayout();
        setLayout(mainLayout);

        // ---------------------- Top: Input Form with GridBagLayout ------------------------
        JPanel inputPanel = new JPanel();
        GridBagLayout inputLayout = new GridBagLayout();
        inputPanel.setLayout(inputLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 10, 5, 10); // padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);

        // Row 1: Severity
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Severity (1-5):"), gbc);

        gbc.gridx = 1;
        severityBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        inputPanel.add(severityBox, gbc);

        // Row 2: Description
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        inputPanel.add(new JLabel("Reason for visit:"), gbc);

        gbc.gridx = 1;
        descriptionArea = new JTextArea(3, 20);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        inputPanel.add(descScroll, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // ---------------------- Center: Buttons ------------------------
        JPanel buttonPanel = new JPanel();
        FlowLayout buttonLayout = new FlowLayout(FlowLayout.CENTER);
        buttonPanel.setLayout(buttonLayout);

        JButton checkInButton = new JButton("Check In Patient");
        CheckInButtonListener checkInButtonListener = new CheckInButtonListener();
        JButton viewQueueButton = new JButton("View Current Queue");
        ViewQueueButtonListener viewQueueButtonListener = new ViewQueueButtonListener();

        buttonPanel.add(checkInButton);
        buttonPanel.add(viewQueueButton);
        add(buttonPanel, BorderLayout.CENTER);

        // ---------------------- Bottom: Output ------------------------
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setPreferredSize(new Dimension(600, 150));
        add(outputScroll, BorderLayout.SOUTH);

        // ---------------------- Button Actions ------------------------
        checkInButton.addActionListener(checkInButtonListener);
        viewQueueButton.addActionListener(viewQueueButtonListener);
        
    }
	class CheckInButtonListener implements ActionListener{
		@Override
	    public void actionPerformed(ActionEvent e) {
	        String name = nameField.getText().trim();
	        int severity = (int) severityBox.getSelectedItem();
	        String description = descriptionArea.getText().trim();
	
	        if (name.isEmpty() || description.isEmpty()) {
	            outputArea.setText("Please fill out all fields.");
	            return;
	        }
	
	        String id = UUID.randomUUID().toString().substring(0, 8);
	        Patient patient = new Patient(id, name, severity, description);
	        system.checkInPatient(patient);
	
	        outputArea.setText("Checked in patient:\n" + patient.toString());
	
	        // Clear inputs
	        nameField.setText("");
	        descriptionArea.setText("");
	        severityBox.setSelectedIndex(0);
	    }
	}
	
	class ViewQueueButtonListener implements ActionListener{
		@Override
	    public void actionPerformed(ActionEvent e) {
	        StringBuilder sb = new StringBuilder();
	        sb.append("Current Patient Queue:\n");
	        sb.append("------------------------------------------\n");
	        for (Patient p : system.getQueue().getAllPatients()) {
	            sb.append(p.formattedPatient()).append("\n");
	        }
	        outputArea.setText(sb.toString());
	    }
	}
}
