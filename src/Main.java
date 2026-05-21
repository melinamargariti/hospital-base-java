//ZIOGA ELENI - 3250067 - p3250067@aueb.gr
//MARGARITI KYRIAKI-MELINA - 3250129 - p3250129@aueb.gr

//Handles program startup, the main menu loop, and program shutdown
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        //Load all data from txt files into collections at startup.
        //If a file doesn't exist, the corresponding load method returns an empty list.
        ArrayList<Doctor> doctors = FileManager.loadDoctors();
        ArrayList<Patient> patients = FileManager.loadPatients();
        ArrayList<Exam> exams = FileManager.loadExams();
        ArrayList<Appointment> appointments = FileManager.loadAppointments();

        //If none of the files exist (e.g. first execution),
        //populate all collections with hardcoded default data.
        //anyDataFilesExist() uses OR so that existing data is never
        //overwritten if only one file happens to be missing
        if (!FileManager.anyDataFilesExist()) {
            addData(doctors, patients, exams, appointments);
        }

        int choice = -1;

        //main menu loop. Keeps running until 0 is chosen (Exit)
        do {
            printMenu();
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> DoctorsMenu.printDoctorsMenu(doctors, exams, appointments);
                case 2 -> PatientsMenu.printPatientsMenu(patients, appointments);
                case 3 -> ExamsMenu.printExamsMenu(exams, doctors, appointments);
                case 4 -> AppointmentsMenu.printAppointmentsMenu(appointments, exams, patients);
                case 5 -> StatisticsMenu.printStatisticsMenu(patients, exams, appointments);
                case 0 -> System.out.println("Saving data...");
                default -> System.out.println("That was not a valid choice. Please try again.");
            }
        } while(choice != 0);

        //On exit, overwrite all txt files with the current state of each collection.
        //FileWriter overwrites by default, so old data is fully replaced.
        FileManager.storeDoctors(doctors);
        FileManager.storePatients(patients);
        FileManager.storeExams(exams);
        FileManager.storeAppointments(appointments);

        System.out.println("Data saved. Program ended.");
    }

    //THIS IS THE MAIN MENU
    private static void printMenu() {
        System.out.println("\n--------MENU--------");
        System.out.println("1. Doctors");
        System.out.println("2. Patients");
        System.out.println("3. Exams");
        System.out.println("4. Appointments");
        System.out.println("5. Statistics");
        System.out.println("0. Exit");

        System.out.println("\nMake a choice:");
    }


    //Populates all four collections with hardcoded default objects.
    //Called only on first execution when no data files exist.
    //No user input is required — objects are created directly via constructors.
    private static void addData(ArrayList<Doctor> doctors, ArrayList<Patient> patients, ArrayList<Exam> exams, ArrayList<Appointment> appointments) {
        doctors.add(new Doctor(1, "George Papadopoulos", "6077622598", "Cardiology", 10));
        doctors.add(new Doctor(2, "Nefeli Sakka", "6944777485", "Radiology", 8));
        doctors.add(new Doctor(3, "Marina Leonardou", "6982749002", "Microbiology", 3));

        patients.add(new Patient(1, "Georgios Palaiokostas", "6982749100", "paleo@gmail.com"));
        patients.add(new Patient(2, "Andreas Petinos", "6902892411", "petin@gmail.com"));
        patients.add(new Patient(3, "Valia Makridi", "6903418084", "makridi@gmail.com"));

        //Two imaging exams, two microbiological, two specialized (two per category as required).
        exams.add(new ImagingExamination(1, "XRAY", 10, 30, 2, "X-Ray"));
        exams.add(new ImagingExamination(2, "XRAY", 10, 30, 3, "X-Ray"));
        exams.add(new MicrobiologicalExamination(3, "PCR", 20, 20, 3, "Blood"));
        exams.add(new MicrobiologicalExamination(4, "PCR", 20, 20, 1, "Blood"));
        exams.add(new SpecializedExamination(5, "Holter", 5, 50, 1, "Cardiology"));
        exams.add(new SpecializedExamination(6, "EEG", 10, 20, 2, "Neurology"));

        //Sample appointments linking existing patients to existing exams.
        appointments.add(new Appointment(1, 1, 1, true, "10:05:2026"));
        appointments.add(new Appointment(2, 2, 2, false, "16:05:2026"));
        appointments.add(new Appointment(3, 1, 2, false, "11:05:2026"));

    }


}
