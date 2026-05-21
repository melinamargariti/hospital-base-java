import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Scanner;

public class AppointmentsMenu {
    private static final Scanner sc = new Scanner(System.in);

    //THIS IS THE APPOINTMENTS MENU
    public static void printAppointmentsMenu(ArrayList<Appointment> appointments, ArrayList<Exam> exams, ArrayList<Patient> patients) {
        int choice = -1;

        do {
            System.out.println("\n--------APPOINTMENTS MENU--------");
            System.out.println("1. Add appointment");
            System.out.println("2. Show all appointments");
            System.out.println("3. Show a patient's appointments");
            System.out.println("4. Delete appointment");
            System.out.println("5. Show today's appointments");
            System.out.println("0. Back to main menu");

            System.out.println("Make a choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 :
                    addAppointment(appointments, patients, exams);
                    break;
                case 2 :
                    HelperMethods.showAll(appointments);
                    break;
                case 3 :
                    showPatientsAppointments(patients, appointments);
                    break;
                case 4 :
                    deleteAppointment(appointments);
                    break;
                case 5 :
                    todaysAppointments(appointments, patients, exams);
                    break;
                case 0 :
                    System.out.println("Going back to main menu...");
                    break;
                default :
                    System.out.println("That was not a valid choice. Please try again.");
            }
        } while (choice != 0);

    }

    private static void addAppointment(ArrayList<Appointment> appointments, ArrayList<Patient> patients, ArrayList<Exam> exams) {
        //An appointment with no exam and patient can not exist.
        if (patients.isEmpty()) {
            System.out.println("No patients found. Please add a patient first.");
            return;
        }
        if (exams.isEmpty()) {
            System.out.println("No exams found. Please add an exam first.");
            return;
        }

        //Choose a patient for this appointment, based on their ID
        HelperMethods.showAll(patients);
        System.out.println("\nGive the patient's ID based on this list. ");
        int patientID = Integer.parseInt(sc.nextLine());

        //checks whether the patient actually exists
        Patient myPatient = SearchHelper.findByID(patients, patientID);
        if (myPatient == null) {
            System.out.println("\nPatient not found. Appointment was not added.");
            return;
        }

        System.out.println();
        //Choose an exam for this appointments, based on their ID
        ExamsMenu.showAllExams(exams);
        System.out.println("\nGive the exam's ID based on this list.");
        int examID = Integer.parseInt(sc.nextLine());

        //checks whether the exam actually exists
        Exam myExam = SearchHelper.findByID(exams, examID);
        if(myExam == null) {
            System.out.println("\nExam not found. Appointment was not added");
            return;
        }

        boolean fastResults = readYesNo("\nDo you want fast results? There is an extra charge. Yes or No? ");

        String examDate;

        while(true) {
            //Reading the date using a helper method
            examDate = readDate("\nNow give the exam's date (dd:MM:yyyy)");

            //if there is an available appointment for this date, we jump after the while loop
            if (availableAppointment(appointments, myExam, examDate)) {
                break;
            }
            //we keep asking for a date, until one with available appointments is found
            System.out.println("\nThere are no available appointments for this date, for this specific exam.");
            System.out.println("\nPlease give another date.");
        }

        //if the appointment is never saved, we do not want the counter to increase, so we modify it here, instead of above
        int appointmentID = Appointment.getNextCounter();  //increase the appointments' ID counter
        //create a new Appointment object and add it to the list
        Appointment appointment = new Appointment(appointmentID, patientID, examID, fastResults, examDate);
        appointments.add(appointment);
        //confirmation message
        System.out.println("Appointment with appointment ID:" + appointmentID + " was added successfully");
    }

    //helper method for fastResults
    private static boolean readYesNo(String message) {
        //keeps asking the user until a valid answer is given.
        while (true) {
            System.out.println();
            System.out.println(message);
            String answer = sc.nextLine().trim().toLowerCase();

            if (answer.equals("yes")) {
                return true;
            } else if (answer.equals("no")) {
                return false;
            } else {
                System.out.println("Please answer Yes or No.");
            }
        }
    }

    private static boolean availableAppointment (ArrayList<Appointment> appointments, Exam myExam, String examDate) {
        int counter = 0;

        //count all the appointments on the date given, for the specific exams
        for (Appointment a : appointments) {
            if (a.getExamID() == myExam.getExamID() && a.getExamDate().equals(examDate)) {
                counter++;
            }
        }
        //return whether there is space for an extra appointment or not
        return counter < myExam.getMaxSlots();
    }

    //helper method for addAppointment
    private static String readDate(String message) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd:MM:uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        while (true) {
            //this will print the message parameter
            System.out.println(message);

            //.trim() removes spaces from the beginning and end of a String
            String dateUser = sc.nextLine().trim();

            //this means: 2 digits:2 digits:4 digits (check its form)
            if (!dateUser.matches("\\d{2}:\\d{2}:\\d{4}")) {
                System.out.println("Wrong format. Please use dd:MM:yyyy");
                continue;
            }

            //check if it is a real date, but our date remains as a String
            try {
                LocalDate.parse(dateUser, formatter);
                return dateUser;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please give a real date.");
            }
        }
    }


    private static void showPatientsAppointments(ArrayList<Patient> patients, ArrayList<Appointment> appointments) {
        //Choose a patient, based on their ID
        HelperMethods.showAll(patients);
        System.out.println("Give the patient's ID based on this list.");
        int patientID = Integer.parseInt(sc.nextLine());
        Patient myPatient = SearchHelper.findByID(patients, patientID);
        //checks whether the patient actually exists
        if (myPatient == null) {
            System.out.println("\nPatient was not found.");
            return;
        }
        //if the patient does exist, all of their appointments are shown
        System.out.println("\nThese are the patient's appointments: ");
        for (Appointment a : appointments) {
            if (a.getPatientID() == patientID) {
                System.out.println(a);
            }
        }
    }

    private static void deleteAppointment(ArrayList<Appointment> appointments) {
        //checks whether there are any appointments or not
        if (appointments.isEmpty()) {
            System.out.println("\nNo appointments found.");
            return;
        }
        HelperMethods.showAll(appointments);
        //choose an appointment to delete, based on its ID
        System.out.println("\nGive the ID of the appointment you want to delete.");
        int appointmentID = Integer.parseInt(sc.nextLine());
        Appointment myAppointment = SearchHelper.findByID(appointments, appointmentID);
        //checks whether the appointment actually exists or not
        if (myAppointment == null) {
            System.out.println("\nAppointment not found.");
            return;
        }
        //if the appointment does exist, we ask for confirmation
        System.out.println("\nAre you sure you want to delete the appointment? (yes/no)");
        String confirm = sc.nextLine().trim().toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("\nThe appointment will not be deleted");
            return;
        }
        //we remove the appointment from the list after confirmation
        appointments.remove(myAppointment);
        System.out.println("\nAppointment with ID: " + appointmentID + " deleted successfully");
    }

    private static void todaysAppointments(ArrayList<Appointment> appointments, ArrayList<Patient> patients, ArrayList<Exam> exams) {
        String date = readDate("Please give a date.");
        boolean appointmentFound = false;

        for (Appointment a : appointments) {
            //all the appointments for today's date are printed out
            if (date.equals(a.getExamDate())) {
                appointmentFound = true;
                System.out.println(a);
                Patient myPatient = SearchHelper.findByID(patients, a.getPatientID());
                //we assume that the system has correct information and no check for null is needed
                //we also print out the patient's name and the exam's name
                System.out.println("Patients's name: " + myPatient.getPatientName());
                Exam myExam = SearchHelper.findByID(exams, a.getExamID());
                System.out.println("Exam's name: " + myExam.getExamName());
            }
        }

        if (!appointmentFound) System.out.println("No appointments found for this date.");
    }

}
