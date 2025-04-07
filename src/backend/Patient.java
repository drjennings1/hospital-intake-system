/**
 * 
 */

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
    
    
    public String getVisitDescription() {
    	return visitDescription;
    }
    public void setVisitDescription(String newVisitDescription) {
    	this.visitDescription = newVisitDescription;
    }
    
    
    public LocalDateTime getCheckInTime() {
    	return checkInTime;
	}
    
    public String formattedPatient() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	String formattedTime = checkInTime.format(formatter);
    	return name + " (Severity: " + severity + ", Check-in: " + formattedTime + ", Description: " + visitDescription + ")";
    }

    @Override
    public int compareTo(Patient other) { // for the merge sort, automatically found because of the "implements Comparable<Patient>"
        if (this.severity != other.severity){
            return Integer.compare(this.severity, other.severity);
        }
        return this.checkInTime.compareTo(other.checkInTime);
    }

    @Override
    public String toString() {
        return name + " (Severity: " + severity + ", Check-in: " + checkInTime + ", Description: " + visitDescription + ")";
    }
}
