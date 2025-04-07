/**
 * 
 */

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
        queue.checkInPatient(patient);
        patientRecords.putIfAbsent(patient.getId(), new ArrayList<>());	// If it's a new patient, create a new list to place their visit(and future visits)
        patientRecords.get(patient.getId()).add(patient);				// If it's a returning patient, add this visit to their list 
    }

    public Patient treatNextPatient() {
        return queue.nextPatient();
    }

    public List<Patient> getPatientHistory(String patientId) {
        return patientRecords.getOrDefault(patientId, new ArrayList<>());
    }

    public String getPatientHistorySummary(String patientId) {
        List<Patient> history = getPatientHistory(patientId);

        if (history.isEmpty()) {
            return "No history found for patient ID: " + patientId;
        }

        StringBuilder sb = new StringBuilder();
        Patient firstVisit = history.get(0); // to display name & total visits before the loop
        
        sb.append("Name: ").append(firstVisit.getName()).append("\n");
        sb.append("Visit history for patient ID: ").append(patientId).append("\n");
        sb.append("Total Visits: ").append(history.size()).append("\n");
        sb.append("--------------------------------------------------\n");
        for (Patient visit : history) {
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

