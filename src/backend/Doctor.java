/**
 * 
 */
package backend;

/**
 * 
 */
public class Doctor {
    private String name;
    private String specialization;

    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    public String getName() {
    	return name;
	}
    public void setName(String newName) {
    	this.name = newName;
    }
    
    public String getSpecialization() {
    	return specialization;
	}
    public void setSpecialization(String newSpec) {
    	this.specialization = newSpec;
    }

    @Override
    public String toString() {
        return name + " (" + specialization + ")";
    }
}
