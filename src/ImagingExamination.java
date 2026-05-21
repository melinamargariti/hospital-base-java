//Extends Exam and adds the machine type
public class ImagingExamination extends Exam {
    //private because other classes can read it through a getter
    private String machineType;
    //Imaging exams charge an extra 10% if fast results are requested.
    private final static double COST_INCREASE_RATE = 0.10;
    //Constructor passes "IMAGING" as the fixed category name to the parent class.
    //"IMAGING" is uppercase to match the category comparison in FileManager.loadExams().
    public ImagingExamination(int examID, String examName, int maxSlots,
        double cost, int doctorID, String machineType) {
            super(examID, examName, "IMAGING", maxSlots, cost, doctorID);
            this.machineType = machineType;
    }
    //returns final cost of the exam
    //if fast results are requested, apply a 10% extra charge
    //cost read directly from parent class (protected)
    @Override
    public double getCost(boolean fastResults) {
        if (fastResults) {
            return cost + cost * COST_INCREASE_RATE;
        }
        //no extra charge if fast results not requested
        return cost;
    }

    public String getMachineType() {
        return machineType;
    }

    @Override
    public String toString() {
        return super.toString() + ", Machine Type: " + machineType;
    }

    //Appends the machine type to the parent's toFileString().
    //This ensures machineType is saved as tokens[6] in the exams file,
    //matching the order expected by FileManager.loadExams()
    @Override
    public String toFileString() {
        return super.toFileString() + "," + machineType;
    }
}
