public class Doctor implements Identifiable {
    @Override
    public int getID() {
        return getDoctorID();
    }

    private final int doctorID;
    private final String doctorName;
    private final String doctorPhone;
    private final String specialty;
    private final int years;

    //constructor
    public Doctor(int doctorID, String doctorName, String doctorPhone, String specialty, int years) {
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.doctorPhone = doctorPhone;
        this.specialty = specialty;
        this.years = years;
    }

    //getters
    public int getDoctorID() {
        return doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public String getSpecialty() {
        return specialty;
    }

    public int getYears() {
        return years;
    }

    //to String
    public String toString() {
        return "Doctor ID: " + doctorID +
                ", Name: " + doctorName +
                ", Phone: " + doctorPhone +
                ", Specialty: " + specialty +
                ", Years: " + years;
    }

    //used to be saved in the files
    public String toFileString() {
        return doctorID + "," + doctorName + "," + doctorPhone + "," + specialty + "," + years;
    }
}
