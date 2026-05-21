public class Appointment implements Identifiable {
    @Override
    public int getID() {
        return getAppointmentID();
    }

    private final int appointmentID;
    private final int patientID;
    private final int examID;
    private final boolean fastResults;
    private final String examDate;

    //constructor
    public Appointment(int appointmentID, int patientID, int examID, boolean fastResults, String examDate) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.examID = examID;
        this.fastResults = fastResults;
        this.examDate = examDate;

        initCounter(appointmentID);
    }

    //Static counter that tracks the highest ID ever assigned.
    //Otherwise, we had an issue where if the appointment with the highest ID was deleted,
    //that ID would be reassigned. (without shutting the program down)
    //This one never decreases, so deleted IDs are never reused.
    //An issue that could still happen: we have IDs 1 to 5, we delete 5, we shut down the program, reopen it -> ID 5 will be reassigned to a new appointment
    private static int idCounter = 0;

    public static void initCounter(int value) {
        if (value > idCounter) {
            idCounter = value;
        }
    }
    public static int getNextCounter() {
        return ++idCounter;
    }


    //getters
    public int getAppointmentID() {
        return appointmentID;
    }

    public int getPatientID() {
        return patientID;
    }

    public int getExamID() {
        return examID;
    }

    public boolean isFastResults() {
        return fastResults;
    }

    public String getExamDate() {
        return examDate;
    }

    public String toString() {
        return "Appointment ID: " + appointmentID +
                ", Patient ID: " + patientID +
                " Exam ID: " + examID +
                ", Fast results: " + fastResults +
                ", Exam Date: " + examDate;
    }

    //used to be saved in the files
    public String toFileString() {
        return appointmentID + "," + patientID + "," + examID + "," + fastResults + "," + examDate;
    }

}
