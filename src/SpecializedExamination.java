//Extends Exam and adds the specialty
public class SpecializedExamination extends Exam {
    //private because other classes can read it through a getter
    private String specialty;
    //Specialized exams charge an extra 30% if fast results are requested.
    private final static double COST_INCREASE_RATE = 0.30;
    //Constructor passes "SPECIALIZED" as the fixed category name to the parent class.
    //"SPECIALIZED" is uppercase to match the category comparison in FileManager.loadExams().
    public SpecializedExamination(int examID, String examName, int maxSlots,
        double cost, int doctorID, String specialty) {
            super(examID, examName, "SPECIALIZED", maxSlots, cost, doctorID);
            this.specialty = specialty;
    }

    //returns final cost of the exam
    //if fast results are requested, apply a 30% extra charge
    //cost read directly from parent class (protected)
    @Override
    public double getCost(boolean fastResults) {
        if (fastResults) {
            return cost + cost * COST_INCREASE_RATE;
        }
        //no extra charge if fast results not requested
        return cost;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String toString() {
        return super.toString() + ", Specialty: " + specialty;
    }

    //Appends the specialty to the parent's toFileString().
    //This ensures specialty is saved as tokens[6] in the exams file,
    //matching the order expected by FileManager.loadExams()
    public String toFileString() {
        return super.toFileString() + "," + specialty;
    }
}
