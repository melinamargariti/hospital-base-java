import java.util.ArrayList;
import java.util.Scanner;

public class ExamsMenu {
    private static final Scanner sc = new Scanner(System.in);

    //THIS IS THE EXAMS MENU
    public static void printExamsMenu(ArrayList<Exam> exams, ArrayList<Doctor> doctors, ArrayList<Appointment> appointments) {
        int choice = -1;

        do {
            System.out.println("\n--------EXAMS MENU--------");
            System.out.println("1. Add exam");
            System.out.println("2. Show all exams");
            System.out.println("3. Show one exam");
            System.out.println("0. Back to main menu");

            System.out.println("Make a choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 :
                    addExam(exams, doctors);
                    break;
                case 2 :
                    showAllExams(exams);
                    break;
                case 3 :
                    showOneExam(exams, appointments);
                    break;
                case 0 :
                    System.out.println("Going back to main menu...");
                    break;
                default :
                    System.out.println("That was not a valid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void addExam(ArrayList<Exam> exams, ArrayList<Doctor> doctors) {
        //increase the exams' ID counter
        int examID = HelperMethods.getNextID(exams);

        System.out.println("\nGive exam name: ");
        String examName = sc.nextLine();

        System.out.println("\nGive max slots per day: ");
        int maxSlots = Integer.parseInt(sc.nextLine());

        System.out.println("\nGive cost: ");
        double cost = Double.parseDouble(sc.nextLine());

        int id = 0;
        int choice = 0;
        String finalChoice = null;

        System.out.println();
        //choose a doctor based on their ID
        HelperMethods.showAll(doctors);
        System.out.println("\nPlease choose the doctor responsible for this exam.");
        System.out.println("\nType the doctor's ID: ");

        id = Integer.parseInt(sc.nextLine());

        Doctor doc = SearchHelper.findByID(doctors, id);
        //if the doctor the user chose doesn't exist, end the procedure
        if (doc == null) {
            System.out.println("Doctor not found. Exam was not added.");
            return;
        }

        String category = HelperMethods.chooseFromList("\nChoose exam category: ", Constants.EXAM_CATEGORIES);

        Exam exam;

        //.equals() because they are Strings.
        //it checks whether they have got the same text, not if they are the same object in memory.
        if (category.equals("Imaging")) {
            //if it is an Imaging Exam, the user needs to choose a machine type
            String machineType = HelperMethods.chooseFromList("Choose machine type:", Constants.MACHINE_TYPES);

            //create a new ImagingExamination object
            exam = new ImagingExamination(
                    examID,
                    examName,
                    maxSlots,
                    cost,
                    id,
                    machineType
            );

        } else if (category.equals("Microbiological")) {
            //if it is a Microbiological Exam, the user needs to choose a sample type
            String sampleType = HelperMethods.chooseFromList("Choose sample type:", Constants.SAMPLE_TYPES);

            //create a new MicroBiological object
            exam = new MicrobiologicalExamination(
                    examID,
                    examName,
                    maxSlots,
                    cost,
                    id,
                    sampleType
            );

        } else {
            //if it is a Specialized exam, the user needs to choose a specialty
            String specialty =HelperMethods.chooseFromList("Choose exam specialty:", Constants.SPECIALTIES);

            //create a new SpecializedExamination object
            exam = new SpecializedExamination(
                    examID,
                    examName,
                    maxSlots,
                    cost,
                    id,
                    specialty
            );
        }
        //add the new object to the exams list
        exams.add(exam);
        //confirmation message
        System.out.println("Exam with ID " + examID + " added successfully.");
    }

    public static void showAllExams(ArrayList<Exam> exams) {
        //check for empty list
        if (exams.isEmpty()) {
            System.out.println("No exams found. The list is empty.");
            return;
        }

        //sorting a copy so the original version is not changed.
        ArrayList<Exam> sortedExams = new ArrayList<>(exams);  //this line creates a shallow copy (the list is new, but the objets inside are the same references)
        //this line sorts alphabetically
        sortedExams.sort((ex1, ex2) -> ex1.getExamName().compareToIgnoreCase(ex2.getExamName()));

        System.out.println();
        //print out the exams alphabetically
        for (Exam e : sortedExams) {
            System.out.println(e);
        }
    }

    private static void showOneExam(ArrayList<Exam> exams, ArrayList<Appointment> appointments) {
        showAllExams(exams);
        int id = 0;
        //choose an exam based on its ID
        System.out.println("Give the exam's ID");
        id = Integer.parseInt(sc.nextLine());

        Exam exam = SearchHelper.findByID(exams, id);
        //if the exam doesn't exist, end the procedure
        if (exam == null) {
            System.out.println("Exam not found.");
            return;
        }
        //if the exam exists
        boolean appointmentsFound = false;

        for (Appointment a : appointments) {
            //print each appointment, with the exam ID chosen above
            if (a.getExamID() == id) {
                System.out.println(a);
                System.out.println();
                //at least one appointment found
                appointmentsFound = true;
            }
        }
        //if 0 appointments were found
        if (!appointmentsFound) System.out.println("No appointments found for this exam.");
    }

}
