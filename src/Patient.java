public class Patient implements Identifiable {
    @Override
    public int getID() {
        return getPatientID();
    }

    private int patientID;
    private String patientName;
    private String patientPhone;
    private String email;

    //constructor
    public Patient(int patientID, String patientName, String patientPhone, String email) {
        this.patientID = patientID;
        this.patientName = patientName;
        this.patientPhone = patientPhone;
        this.email = email;
    }

    //getters
    public int getPatientID() {
        return patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public String getEmail() {
        return email;
    }

    //toString
    public String toString() {
        return "Patient ID: " + patientID +
                ", Name: " + patientName +
                ", Phone: " + patientPhone +
                ", Email: " + email;
    }

    public String toFileString() {
        return patientID + "," + patientName + "," + patientPhone + "," + email;
    }
}
