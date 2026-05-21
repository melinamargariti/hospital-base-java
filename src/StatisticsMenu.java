import java.util.ArrayList;
import java.util.Scanner;

public class StatisticsMenu {
    private static Scanner sc = new Scanner(System.in);

    //THIS IS THE STATISTICS MENU
    public static void printStatisticsMenu(ArrayList<Patient> patients, ArrayList<Exam> exams, ArrayList<Appointment> appointments) {
        int choice = -1;
        //loops until the user chooses 0
        do {
            System.out.println("\n--------STATISTICS MENU--------");
            System.out.println("1. Show the earnings from each patient, as well as the earnings from all the patients.");
            System.out.println("2. Show the earnings from each exam, as well as the earnings from all the exams.");
            System.out.println("3. Show the earnings of each exam category, as well as the earnings from all the exam categories.");
            System.out.println("0. Back to main menu");

            System.out.println("Make a choice: ");
            choice = Integer.parseInt(sc.nextLine());


            switch (choice) {
                case 1 :
                    patientsEarnings(patients, appointments, exams);
                    break;
                case 2 :
                    examsEarnings(appointments, exams);
                    break;
                case 3 :
                    categoriesEarnings(appointments,exams);
                    break;
                case 0 :
                    System.out.println("Going back to main menu...");
                    break;
                default :
                    System.out.println("That was not a valid choice. Please try again.");
            }
        } while (choice != 0);
    }

    //For each patient, displays all their appointments and the cost of each exam.
    //Calculates per-patient total and overall grand total across all patients.
    //getCost() is called with fastResults to apply the correct surcharge if needed.
    private static void patientsEarnings (ArrayList<Patient> patients, ArrayList<Appointment> appointments, ArrayList<Exam> exams) {
        double totalEarnings = 0;

        for (Patient p : patients) {
            boolean patientAppointments = false;
            double patientEarnings = 0;

            System.out.println("\nPatient: ");
            System.out.println(p);
            for (Appointment a : appointments) {
                if (a.getPatientID() == p.getPatientID()) {
                    patientAppointments = true;

                    System.out.println("\nAppointment: ");
                    System.out.println(a);
                    Exam myExam = SearchHelper.findByID(exams, a.getExamID());

                    if (myExam == null) {
                        System.out.println("\nExam not found for this appointment.");
                        continue;
                    }
                    //getCost() applies the fast results surcharge if requested.
                    double cost = myExam.getCost(a.isFastResults());
                    System.out.println("\nCost: ");
                    System.out.println(cost);
                    patientEarnings += cost;
                    System.out.println();
                }
            }

            if (!patientAppointments) {
                System.out.println("\nNo appointments found for this patient.");
            }

            System.out.println("\nTotal earnings from this patient: " + patientEarnings);

            totalEarnings += patientEarnings;
        }

        System.out.println("\nTotal revenue: " + totalEarnings);
    }

    //For each exam, displays all its appointments and calculates
    //per-exam total earnings and the overall grand total across all exams.
    private static void examsEarnings (ArrayList<Appointment> appointments, ArrayList<Exam> exams) {
        double totalEarnings = 0;

        for (Exam e : exams) {
            double examEarnings = 0;
            boolean foundAppointment = false;

            System.out.println("\nExam: ");
            System.out.println(e);
            System.out.println("\nAppointments: ");
            for (Appointment a : appointments) {
                if (a.getExamID() == e.getExamID()) {
                    foundAppointment = true;

                    System.out.println(a);
                    examEarnings += e.getCost(a.isFastResults());
                }
            }
            if (!foundAppointment) {
                System.out.println("\nNo appointments found for this exam.");
            }

            System.out.println("\nExam's " + e.getExamName() + " total earnings = " + examEarnings);

            totalEarnings += examEarnings;
        }

        System.out.println("\nAll of the exams earnings = " + totalEarnings);

    }
    //For each exam category (IMAGING, MICROBIOLOGICAL, SPECIALIZED),
    //finds all appointments whose exam belongs to that category.
    //Calculates per-category total earnings and the overall grand total.
    //Category matching uses getCategoryName() which returns the uppercase
    //category string set in each subclass constructor (e.g. "IMAGING").
    private static void categoriesEarnings (ArrayList<Appointment> appointments, ArrayList<Exam> exams) {
        double totalEarnings = 0;

        for (String category : Constants.EXAM_CATEGORIES) {
            double categoryEarnings = 0;
            boolean foundAppointment = false;

            System.out.println("\nCategory: ");
            System.out.println(category);
            System.out.println("\nAppointments: ");
            for (Appointment a : appointments) {
                Exam myExam = SearchHelper.findByID(exams, a.getExamID());
                if (myExam == null) {
                    System.out.println("\nNo exam found for this appointment.");
                    continue;
                }
                //Compare category strings — both must be uppercase to match correctly.
                if (myExam.getCategoryName().equals(category)) {
                    foundAppointment = true;

                    System.out.println(a);

                    categoryEarnings += myExam.getCost(a.isFastResults());
                }
            }

            if (!foundAppointment) {
                System.out.println("\nNo appointments found for this category.");
            }
            System.out.println("\nCategory " + category + " has total earnings = " + categoryEarnings);

            totalEarnings += categoryEarnings;
        }

        System.out.println("\nAll of the categories earnings = " + totalEarnings);
    }
}
