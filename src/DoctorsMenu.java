import java.util.ArrayList;
import java.util.Scanner;

public class DoctorsMenu {

    private static final Scanner sc = new Scanner(System.in);
    //THIS IS THE DOCTORS MENU
    public static void printDoctorsMenu(ArrayList<Doctor> doctors, ArrayList<Exam> exams, ArrayList<Appointment> appointments) {
        int choice = -1;

        do {
            System.out.println("\n--------DOCTORS MENU--------");
            System.out.println("1. Add doctor");
            System.out.println("2. Show all doctors");
            System.out.println("3. Show one doctor");
            System.out.println("4. Show doctor's appointments");
            System.out.println("0. Back to main menu");

            System.out.println("Pick a number: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 :
                    addDoctor(doctors);
                    break;
                case 2 :
                    HelperMethods.showAll(doctors);
                    break;
                case 3 :
                    showOneDoctor(doctors, exams);
                    break;
                case 4 :
                    showDoctorAppointments(doctors, exams, appointments);
                    break;
                case 0 :
                    System.out.println("Going back to main menu...");
                    break;
                default :
                    System.out.println("That was not a valid choice. Please try again.");
            }
        } while (choice != 0);

    }

    //THOSE ARE ALL THE SUB-MENUS FOR DOCTORS
    private static void addDoctor(ArrayList<Doctor> doctors) {
        //increase the doctors' ID counter
        int doctorID = HelperMethods.getNextID(doctors);

        System.out.println("\nGive the doctor's name: ");
        String doctorName = sc.nextLine();

        System.out.println("\nGive the doctor's phone: ");
        String doctorPhone = sc.nextLine();

        System.out.println();
        String specialty = pickSpecialty();   //helper method

        int years;

        while (true) {
            System.out.println("\nGive the doctor's years of experience: ");

            try {
                years = Integer.parseInt(sc.nextLine());

                if (years < 0) {
                    System.out.println("Years of experience cannot be negative. Please try again.");
                    continue;   //go back to while(true)
                }

                break; // valid input, leave the loop

            } catch (NumberFormatException e) {  //if the user for example gives "abc"
                System.out.println("Please give a valid number.");
            }
        }

        //create new Doctor object and add it to the list
        Doctor doc = new Doctor(doctorID, doctorName, doctorPhone, specialty, years);
        doctors.add(doc);

        System.out.println("\nDoctor with ID: " + doctorID +" added successfully");
    }


    //helper method for addDoctor
    private static String pickSpecialty() {
        return HelperMethods.chooseFromList(
                "Give the doctor's specialty:",
                Constants.SPECIALTIES
        );
    }

    private static void showOneDoctor(ArrayList<Doctor> doctors, ArrayList<Exam> exams) {
        HelperMethods.showAll(doctors);
        int id = 0;

        System.out.println("Give the doctor's ID");
        id = Integer.parseInt(sc.nextLine());

        boolean doctorFound = false;
        for (Doctor d: doctors) {
            if (d.getDoctorID() == id) {
                //if the doctor is found
                doctorFound = true;
                System.out.println("Exams of doctor: ");
                System.out.println(d);
                System.out.println();
            }
        }

        if (!doctorFound) {
            System.out.println("\nDoctor not found. You gave the wrong ID.");
            return;
        }

        boolean found = false;

        //if the doctor is found, print out all of his exams.
        for (Exam e : exams) {
            if (e.getDoctorID() == id) {
                System.out.println(e);
                //if any exam of this doctor was found
                found = true;
            }
        }
        //if 0 exams where found for this doctor, but the doctor existed
        if (!found) System.out.println("No exams found for this doctor.");

    }

    private static void showDoctorAppointments(ArrayList<Doctor> doctors, ArrayList<Exam> exams, ArrayList<Appointment> appointments) {
        System.out.println();
        HelperMethods.showAll(doctors);

        int id = 0;

        System.out.println("Give the doctor's ID: ");
        id = Integer.parseInt(sc.nextLine());

        boolean doctorFound = false;
        for (Doctor d: doctors) {
            if (d.getDoctorID() == id) {
                //if the doctor was found
                doctorFound = true;
                System.out.println("Appointments of doctor: ");
                System.out.println(d);
                System.out.println();
            }
        }

        if (!doctorFound) {
            System.out.println("\nDoctor not found. You gave the wrong ID.");
            return;
        }

        //if the doctor was found
        boolean found = false;

        for (Appointment a : appointments) {
            int exID = a.getExamID();
            for (Exam e : exams) {
                if (e.getExamID() == exID) {
                    //for each appointment, check all of its exams
                    //for each of these exams, check their doctors' IDs
                    //if it is the doctor chosen above -> print the appointment's details and the exam's name and cost
                    if (e.getDoctorID() == id) {
                        System.out.println(a);
                        System.out.println("Exam: " + e.getExamName());
                        System.out.println("Final cost: " + e.getCost(a.isFastResults()));
                        System.out.println();
                        //if any appointment was found for this doctor
                        found = true;
                    }
                }
            }
        }

        if (!found) System.out.println("No appointments found for this doctor.");

    }


}
