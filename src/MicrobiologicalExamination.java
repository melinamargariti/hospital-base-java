//Extends Exam and adds the sample type
public class MicrobiologicalExamination extends Exam {
    //private because other classes can read it through a getter
    private String sampleType;
    //Microbiological exams charge an extra 20% if fast results are requested.
    private final static double COST_INCREASE_RATE = 0.20;
    //Constructor passes "MICROBIOLOGICAL" as the fixed category name to the parent class.
    //"MICROBIOLOGICAL" is uppercase to match the category comparison in FileManager.loadExams().
    public MicrobiologicalExamination(int examID, String examName, int maxSlots,
        double cost, int doctorID, String sampleType) {
            super(examID, examName, "MICROBIOLOGICAL", maxSlots, cost, doctorID);
                this.sampleType = sampleType;
    }

    //returns final cost of the exam
    //if fast results are requested, apply a 20% extra charge
    //cost read directly from parent class (protected)
    @Override
    public double getCost(boolean fastResults) {
        if (fastResults) {
            return cost + cost * COST_INCREASE_RATE;
        }
        //no extra charge if fast results not requested
        return cost;
    }

    public String getSampleType() {
        return sampleType;
    }

    //toString
    public String toString() {
        return super.toString() + ", Sample Type: " + sampleType;
    }

    //Appends the sample type to the parent's toFileString().
    //This ensures sampleType is saved as tokens[6] in the exams file,
    //matching the order expected by FileManager.loadExams()
    @Override
    public String toFileString() {
        return super.toFileString() + "," + sampleType;
    }
}
