import java.util.ArrayList;
import java.util.Scanner;

public class PatientsMenu {
    private static Scanner sc = new Scanner(System.in);
    //THIS IS THE PATIENTS MENU
    public static void printPatientsMenu(ArrayList<Patient> patients, ArrayList<Appointment> appointments) {
        int choice = -1;



        do {
            System.out.println("\n--------PATIENTS MENU--------");
            System.out.println("1. Add patient");
            System.out.println("2. Show all patients");
            System.out.println("3. Show one patient");
            System.out.println("0. Back to main menu");

            System.out.println("Make a choice: ");
            choice = Integer.parseInt(sc.nextLine());


            switch (choice) {
                case 1 :
                    addPatient(patients);
                    break;
                case 2 :
                    HelperMethods.showAll(patients);
                    break;
                case 3 :
                    showOnePatient(patients, appointments);
                    break;
                case 0 :
                    System.out.println("Going back to main menu...");
                    break;
                default :
                    System.out.println("That was not a valid choice. Please try again.");
            }
        } while (choice != 0);
    }

    //THOSE ARE ALL THE SUB-MENUS FOR PATIENTS
    private static void addPatient(ArrayList<Patient> patients) {
        int patientID = HelperMethods.getNextID(patients);

        System.out.println("\nGive the patient's name: ");
        String patientName = sc.nextLine();

        System.out.println("\nGive the patient's phone: ");
        String patientPhone = sc.nextLine();

        System.out.println("\nGive the patient's email: ");
        String patientEmail = sc.nextLine();

        Patient patient = new Patient(patientID, patientName, patientPhone, patientEmail);
        patients.add(patient);

        System.out.println("\nPatient with ID: " + patientID +" added successfully");
    }



    private static void showOnePatient(ArrayList<Patient> patients, ArrayList<Appointment> appointments) {
        HelperMethods.showAll(patients);
        int id = 0;

        System.out.println("\nGive the patient's ID");
        id = Integer.parseInt(sc.nextLine());

        boolean patientFound = false;
        for (Patient p : patients) {
            if (p.getPatientID() == id) {
                patientFound = true;
                System.out.println("\nAppointments of patient: ");
                System.out.println(p);
                System.out.println();
            }
        }

        if (!patientFound) {
            System.out.println("\nPatient not found. You gave the wrong ID.");
            return;
        }

        boolean found = false;

        for (Appointment a : appointments) {
            if (a.getPatientID() == id) {
                System.out.println(a);
                System.out.println();

                found = true;
            }
        }

        if (!found) System.out.println("No appointments found for this patient.");

    }
}
