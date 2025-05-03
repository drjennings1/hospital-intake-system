/**
 * 
 */
package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class HospitalQueue {
    private PriorityQueue<Patient> patientQueue;

    public HospitalQueue() {
        patientQueue = new PriorityQueue<>();
    }

    public void checkInPatient(Patient patient) {
    	if (patient == null) throw new IllegalArgumentException("Patient cannot be null");
        if (patient.getName().isBlank()) throw new IllegalArgumentException("Name required");
        if (patient.getSeverity() < 1 || patient.getSeverity() > 5)
            throw new IllegalArgumentException("Severity must be between 1 and 5");
        patientQueue.add(patient);
    }

    public Patient loadFirstPatient() { // necessary so that if the doctor window is closed, patient remains in queue
    	return patientQueue.peek();
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

    public List<Patient> getAllPatients() {
    	
    	// Placing the queue into a temporary list, which allows me to sort it using Collections.sort(). 
    	List<Patient> tempQ = new ArrayList<>(patientQueue);
    	
    	// Collections.sort() is a mixture of both an insertion sort and a merge sort, called a TimSort. In this case, it is effectively used as a merge sort.
    	// A merge sort works by recursively splitting the array into two halves(left and right) until each half is a single element.
    	// When a base case is encountered (the two halves are separately sorted), then the sort will combine the halves into 1. 
    	// To combine into one, it will compare the left-most elements of each half array (which have been sorted, meaning the left-most is the smallest). 
    	// Once one of the halves runs out of elements to compare, then the rest of the other (already sorted) half is appended to the end.
    	// This is efficient because it means that not every element has to be compared to every other element individually. 
    	Collections.sort(tempQ);
        return tempQ;
    }
}
