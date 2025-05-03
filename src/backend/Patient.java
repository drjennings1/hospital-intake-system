/**
 * 
 */
package backend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Patient implements Comparable<Patient> {
    private String id;
    private String name;
    private int severity; // Lower number = more severe
    private String visitDescription;
    private LocalDateTime checkInTime;

    public Patient(String id, String name, int severity, String visitDescription) {
        this.id = id;
        this.name = name;
        this.severity = severity;
        this.visitDescription = visitDescription;
        this.checkInTime = LocalDateTime.now();
    }

    // getters and setters
    public String getId() { 
    	return id; 
	}
    public void setId(String newId) {
    	this.id = newId;
    }
    
    
    public String getName() {
    	return name;
	}
    public void setName(String newName) {
    	this.name = newName;
    }
    
    
    public int getSeverity() {
    	return severity;
	}
    public void setSeverity(int newSeverity) {
    	this.severity = newSeverity;
    }
    public String getSeverityString() { // necessary to properly display severity
    	String severityString = Integer.toString(severity);
    	return severityString;
	}
    
    
    public String getVisitDescription() {
    	return visitDescription;
    }
    public void setVisitDescription(String newVisitDescription) {
    	this.visitDescription = newVisitDescription;
    }
    
    
    public LocalDateTime getCheckInTime() {
    	return checkInTime;
	}
    
    public String formattedPatient() { // changing the format of the date/time to display more cleanly.
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	String formattedTime = checkInTime.format(formatter);
    	return name + " (Severity: " + severity + ", Check-in: " + formattedTime + ", Description: " + visitDescription + ")";
    }

    @Override
    public int compareTo(Patient other) { // for the priorityQueue to prioritize and for the display to output in the right order
        if (this.severity != other.severity){ // If severity is unequal, return the higher priority severity (the lowest number)
            return Integer.compare(this.severity, other.severity); // lower Integer severity = more severe, higher priority
        }
        return this.checkInTime.compareTo(other.checkInTime); // if severity is equal, return the higher priority check-in time (the earliest)
    }

    @Override
    public String toString() {
        return name + " (Severity: " + severity + ", Check-in: " + checkInTime + ", Description: " + visitDescription + ")";
    }
}
