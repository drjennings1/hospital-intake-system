/**
 * 
 */
package backend;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class EmergencyRoomSystem {
    private HospitalQueue queue;
    private HashMap<String, List<Patient>> patientRecords;

    public EmergencyRoomSystem() {
        queue = new HospitalQueue();
        patientRecords = new HashMap<>();
    }

    public void checkInPatient(Patient patient) {
        queue.checkInPatient(patient); // add visit to priority queue
        patientRecords.putIfAbsent(patient.getId(), new ArrayList<>());	// If it's a new patient, create a new list to place their visit(and future visits)
        patientRecords.get(patient.getId()).add(patient);				// If it's a returning patient, add this visit to their list 
    }

    // necessary to prevent the removal 
    public Patient loadFirstPatient() {
    	return queue.loadFirstPatient();
    }
    public Patient treatNextPatient() {
        return queue.nextPatient();
    }
    
	// a check for existing IDs. 
    // returns true = does exist, false = does not exist.
    public boolean isValidPatientId(String id) { 
    	return patientRecords.containsKey(id);
    }


    
    // used for Fill Info button
    public List<Patient> getPatientHistory(String patientId) {
        return patientRecords.getOrDefault(patientId, new ArrayList<>());
    }

    // used to display the Patient's history summary in the DoctorPanel's patientHistoryArea.
    public String getPatientHistorySummary(String patientId) {
        List<Patient> history = getPatientHistory(patientId);

        if (history.isEmpty()) {
            return "No history found for patient ID: " + patientId;
        }

        StringBuilder sb = new StringBuilder();
        Patient firstVisit = history.get(0); // to display name & total visits before the loop
        
        sb.append("Name: ").append(firstVisit.getName()).append("\n");
        sb.append("Patient ID: ").append(patientId).append("\n");
        sb.append("Total Visits: ").append(history.size()).append("\n");
        sb.append("--------------------------------------------------\n");
        for (int i = 0; i < history.size() - 1; i++) { // size - 1 to avoid displaying current visit (most recent/last in list)
        	Patient visit = history.get(i);
            sb.append("Check-In Time: ").append(visit.getCheckInTime()).append("\n");
            sb.append("Severity: ").append(visit.getSeverity()).append("\n");
            sb.append("Description: ").append(visit.getVisitDescription()).append("\n");
            sb.append("--------------------------------------------------\n");
        }

        return sb.toString();
    }

    public HospitalQueue getQueue() {
        return queue;
    }
}

