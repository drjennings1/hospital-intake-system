/**
 * 
 */

/**
 * 
 */
package backend;

import java.util.PriorityQueue;

public class HospitalQueue {
    private PriorityQueue<Patient> patientQueue;

    public HospitalQueue() {
        patientQueue = new PriorityQueue<>();
    }

    public void checkInPatient(Patient patient) {
        patientQueue.add(patient);
    }

    public Patient nextPatient() {
        return patientQueue.poll();
    }

    public boolean isEmpty() {
        return patientQueue.isEmpty();
    }

    public int size() {
        return patientQueue.size();
    }

    public PriorityQueue<Patient> getAllPatients() {
        return new PriorityQueue<>(patientQueue);
    }
}
