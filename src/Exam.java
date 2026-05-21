public abstract class Exam implements Identifiable {
    @Override
    public int getID() {
        return getExamID();
    }

    //we make them protected instead of private, so the subclasses can access them directly
    protected final int examID;
    protected final String examName;
    protected final String categoryName;
    protected final int maxSlots;
    //since getCost() only reads this value and never modifies it
    protected final double cost;
    protected final int doctorID;

    //constructor
    public Exam(int examID, String examName, String categoryName, 
        int maxSlots, double cost, int doctorID) {
            this.examID = examID;
            this.examName = examName;
            this.categoryName = categoryName;
            this.maxSlots = maxSlots;
            this.cost = cost;
            this.doctorID = doctorID;
    }
    
    //getters
    public abstract double getCost(boolean fastResults);

    public int getExamID() {
        return examID;
    }

    public String getExamName() {
        return examName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String toString() {
        return "Exam ID: " + examID +
                ", Exam Name: " + examName +
                ", Category Name: " + categoryName +
                ", Max Slots: " + maxSlots +
                ", Cost: " + cost +
                ", Doctor ID: " + doctorID;
    }

    public String toFileString() {
        return examID + "," + examName + "," + categoryName + "," + maxSlots + "," + cost + "," + doctorID;
    }
}
