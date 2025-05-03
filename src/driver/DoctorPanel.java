/**
 * 
 */
package driver;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.*;
import backend.EmergencyRoomSystem;
import backend.Patient;

/**
 * 
 */
public class DoctorPanel extends JPanel {
	private EmergencyRoomSystem system;
	private Patient currentPatient;
	private Patient firstPatient;
	
	JTextArea currentDescriptionText;
	private JLabel queueLabel;
	private JLabel severityLabel;
	private JLabel patientLabel;
	private JTextArea patientHistoryArea;
	private JPanel centerPanel;
	
	public DoctorPanel(EmergencyRoomSystem system, Patient firstPatient) {
		this.system = system;
		this.firstPatient = firstPatient;
		this.currentPatient = firstPatient;
		
		// Layout for the entire window
		BorderLayout mainLayout = new BorderLayout();
		setLayout(mainLayout);
		
		// ---------------------- Top: Input Form with GridBagLayout ------------------------
		// setting up panel
		JPanel topPanel = new JPanel(new BorderLayout());
		
		queueLabel = new JLabel("Patients remaining: " + (system.getQueue().size() - 1)); // -1 so that it doesn't include the current patient
		// Patient queue top-left visual
		topPanel.add(queueLabel, BorderLayout.WEST);
		
		// Get Next Patient top-right button
		JButton nextPatientButton = new JButton("Get Next Patient");
		NextPatientButtonListener nextPatientButtonListener = new NextPatientButtonListener();
		topPanel.add(nextPatientButton, BorderLayout.EAST);
		
		// placing topPanel into mainLayout
		add(topPanel, BorderLayout.NORTH);
		
		// ---------------------- Center: Current visit information ------------------------
		centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5, 10, 5, 10); // padding around components
		
		// patient name display
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.PAGE_START; // as close to the top as possible
		gbc.fill = GridBagConstraints.HORIZONTAL;
		patientLabel = new JLabel(currentPatient.getName());
		patientLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
		patientLabel.setHorizontalAlignment(SwingConstants.CENTER);
		centerPanel.add(patientLabel, gbc);
		
		// patient severity level display
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		severityLabel = new JLabel("Severity: " + currentPatient.getSeverityString());
		centerPanel.add(severityLabel, gbc);
		
		// patient visit description
		gbc.gridy = 2;
		JLabel currentDescriptionLabel = new JLabel("Visit Description: ");
		centerPanel.add(currentDescriptionLabel, gbc);
		
		gbc.gridy = 3;
		currentDescriptionText = new JTextArea(6, 20);
		currentDescriptionText.setText(currentPatient.getVisitDescription());
		currentDescriptionText.setEditable(false);
		centerPanel.add(currentDescriptionText, gbc);
        
		JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerWrapper.add(centerPanel);
		add(centerWrapper, BorderLayout.CENTER);
		
		// ---------------------- South: Patient history box ------------------------
		patientHistoryArea = new JTextArea(6, 20);
		patientHistoryArea.setText(system.getPatientHistorySummary(currentPatient.getId()));
		patientHistoryArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(patientHistoryArea);
		add(scroll, BorderLayout.SOUTH);
		
		
		
		// ---------------------- Button Actions ------------------------
		nextPatientButton.addActionListener(nextPatientButtonListener);
		
	}
	

	class NextPatientButtonListener implements ActionListener{
		@Override
	    public void actionPerformed(ActionEvent e) {
			if (currentPatient == firstPatient) {
				system.treatNextPatient();
			}
			
			if (system.getQueue().isEmpty()) {
				patientLabel.setText("No Available Patients. Queue empty.");
				severityLabel.setText("Severity: null");
				currentDescriptionText.setText("No Available Patients. Queue Empty.");
				patientHistoryArea.setText("No Available Patients. Queue empty.");
				
				return;
			}
			currentPatient = system.treatNextPatient();
			
			queueLabel.setText("Patients in Queue: " + system.getQueue().size());
			patientLabel.setText(currentPatient.getName());
			severityLabel.setText("Severity: " + currentPatient.getSeverityString());
			currentDescriptionText.setText(currentPatient.getVisitDescription());
			patientHistoryArea.setText(system.getPatientHistorySummary(currentPatient.getId()));
			
			centerPanel.revalidate();
			centerPanel.repaint();
			
	    }
	}
}
