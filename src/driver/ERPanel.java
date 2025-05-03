package driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.UUID;
import backend.EmergencyRoomSystem;
import backend.Patient;
import java.util.List;

public class ERPanel extends JPanel {
    private EmergencyRoomSystem system;

    // Input fields
    private JTextField nameField;
    private JComboBox<Integer> severityBox;
    private JTextField idBox;
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

        // Row 2: ID
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("ID (if returning)"), gbc);
        
        gbc.gridx = 1;
        idBox = new JTextField(8);
        inputPanel.add(idBox, gbc);
        
        gbc.gridx = 2;
        JButton fillInfoButton = new JButton("Fill Info");
        FillInfoButtonListener fillInfoButtonListener = new FillInfoButtonListener();
        inputPanel.add(fillInfoButton, gbc);
        
        // Row 3: Description/Reason for visit
        gbc.gridx = 0;
        gbc.gridy = 3;
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

        //check in patient button
        JButton checkInButton = new JButton("Check In Patient");
        CheckInButtonListener checkInButtonListener = new CheckInButtonListener();
        //view the queue button
        JButton viewQueueButton = new JButton("View Current Queue");
        ViewQueueButtonListener viewQueueButtonListener = new ViewQueueButtonListener();
        //doctor view button
        JButton doctorViewButton = new JButton("Doctor View");
        DoctorViewButtonListener doctorViewButtonListener = new DoctorViewButtonListener();
        
        buttonPanel.add(checkInButton);
        buttonPanel.add(viewQueueButton);
        buttonPanel.add(doctorViewButton);
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
        doctorViewButton.addActionListener(doctorViewButtonListener);
        fillInfoButton.addActionListener(fillInfoButtonListener);
    }
    
	class CheckInButtonListener implements ActionListener{
		@Override
	    public void actionPerformed(ActionEvent e) {
	        String name = nameField.getText().trim();
	        int severity = (int) severityBox.getSelectedItem();
	        String id = idBox.getText().trim();
	        String description = descriptionArea.getText().trim();
	
	        if (name.isEmpty() || description.isEmpty()) {
	            outputArea.setText("Please fill out all fields required to check in (name and description).");
	            return;
	        }
	
	        if (!name.matches("^[a-zA-Z\\s'-]+$")) { // prevents the name field from allowing characters that don't belong in names
	            outputArea.setText("Name field has invalid characters.");
	            return;
	        }

	        if (id.isEmpty()) { // if the ID field is empty, create a new ID
		        id = UUID.randomUUID().toString().substring(0, 5);
	        }
	        
	        Patient patient = new Patient(id, name, severity, description); // creating patient object to record visit + save the ID
	        system.checkInPatient(patient);
	
	        outputArea.setText("Checked in patient:\n" + patient.formattedPatient());
	
	        // Clear inputs
	        nameField.setText("");
	        idBox.setText("");
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
	
	class DoctorViewButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (system.getQueue().isEmpty()) {
				outputArea.setText("The queue is empty -- could not load doctor's view.");
				return;
			}
			JFrame doctorFrame = new JFrame("Doctor View");
			doctorFrame.setSize(600, 400);
			doctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Patient firstPatient = system.loadFirstPatient();
			
			DoctorPanel doctorPanel = new DoctorPanel(system, firstPatient);
			doctorFrame.add(doctorPanel);
			doctorFrame.setLocationRelativeTo(null);
			doctorFrame.setVisible(true);
		}
	}
	
	class FillInfoButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String enteredId = idBox.getText().trim();

			if (!enteredId.isEmpty() && system.isValidPatientId(enteredId)) { // validating id field
				List<Patient> visitHistory = system.getPatientHistory(enteredId);
				if (!visitHistory.isEmpty()) {
					// getting Patient object from ID's last visit
					Patient returningPatient = visitHistory.get(visitHistory.size() - 1);
					nameField.setText(returningPatient.getName());
				}
			} else {
				nameField.setText("Not a valid ID :( ");
			}
		}
	}
}
