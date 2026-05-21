import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    //Those are my files' names.
    private static final String DOCTORS_TXT = "doctors.txt";
    private static final String PATIENTS_TXT = "patients.txt";
    private static final String EXAMS_TXT = "exams.txt";
    private static final String APPOINTMENTS_TXT = "appointments.txt";

    //new File(DOCTORS_TXT) creates an object that points to the file path
    //.exists() checks whether the file actually exists
    //We chose anyDataFilesExist instead of AllDataFilesExist to avoid overwriting
    //existing data if for example only one file is missing
    public static boolean anyDataFilesExist() {
        return new File(DOCTORS_TXT).exists()
                || new File(PATIENTS_TXT).exists()
                || new File(EXAMS_TXT).exists()
                || new File(APPOINTMENTS_TXT).exists();
    }

    //load doctors from files
    public static ArrayList<Doctor> loadDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        //path to DOCTORS_TXT
        File file = new File(DOCTORS_TXT);

        //used in the first execution
        if (!file.exists()) {
            return doctors;  //return an empty list (avoid NPE by not returning null)
        }

        //if the txt does exist
        //Open the file using BufferedReader for efficient line-by-line reading.
        //The try-with-resources ensures the file is automatically closed after reading,
        //even if an exception occurs.
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                //skip empty lines
                if (line.isBlank()) {
                    continue;
                }

                //split the line by comma (,) into an array.
                //-1 is used, because we also want to preserve empty fields. 0 by default does not keep them
                //For example: String line = "Maria,,1,true"
                String[] tokens = line.split(",", -1);

                //Parse each token into its correct type and store into variables
                int doctorID = Integer.parseInt(tokens[0]);
                String doctorName = tokens[1];
                String doctorPhone = tokens[2];
                String specialty = tokens[3];
                int years = Integer.parseInt(tokens[4]);
                //order matches the Doctor.toFileString()
                doctors.add(new Doctor(doctorID, doctorName, doctorPhone, specialty, years));
            }
        } catch (Exception e) { //inform the user for any error, without crashing the program
            System.out.println("Could not load doctors: " + e.getMessage());
        }
        //return the populated list
        return doctors;
    }

    //load patients from files
    public static ArrayList<Patient> loadPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        //path to patients.txt
        File file = new File(PATIENTS_TXT);

        //used in the first execution
        if (!file.exists()) {
            return patients;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] tokens = line.split(",", -1);

                int patientID = Integer.parseInt(tokens[0]);
                String patientName = tokens[1];
                String patientPhone = tokens[2];
                String email = tokens[3];
                //field order matches Patient.toFileString()
                patients.add(new Patient(patientID, patientName, patientPhone, email));
            }
        } catch (Exception e) {
            System.out.println("Could not load patients: " + e.getMessage());
        }
        return patients;
    }

    //load exams from files
    //Exams are polymorphic so we read the category first,
    //to know the subclass
    public static ArrayList<Exam> loadExams() {
        ArrayList<Exam> exams = new ArrayList<>();
        File file = new File(EXAMS_TXT);

        //used in the first execution
        if (!file.exists()) {
            return exams;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] tokens = line.split(",", -1);
                //read category first to determine subclass
                String category = tokens[2];

                int examID = Integer.parseInt(tokens[0]);
                String examName = tokens[1];
                int maxSlots = Integer.parseInt(tokens[3]);
                double cost = Double.parseDouble(tokens[4]);
                int doctorID = Integer.parseInt(tokens[5]);
                //this holds the subclass-specific field:
                //machineType for IMAGING, sampleType for MICROBIOLOGICAL,
                //specialty for SPECIALIZED.
                String extraField = tokens[6];

                //we are saving the categories in upper case to avoid case-sensitivity issues
                if (category.equals("IMAGING")) {
                    exams.add(new ImagingExamination(examID, examName, maxSlots, cost, doctorID, extraField));
                } else if (category.equals("MICROBIOLOGICAL")) {
                    exams.add(new MicrobiologicalExamination(examID, examName, maxSlots, cost, doctorID, extraField));
                } else if (category.equals("SPECIALIZED")) {
                    exams.add(new SpecializedExamination(examID, examName, maxSlots, cost, doctorID, extraField));
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load exams: " + e.getMessage());
        }
        return exams;
    }

    //load appointments from file
    public static ArrayList<Appointment> loadAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        File file = new File(APPOINTMENTS_TXT);

        //used in the first execution
        if (!file.exists()) {
            return appointments;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] tokens = line.split(",", -1);

                int appointmentID = Integer.parseInt(tokens[0]);
                //keep counter in sync with loaded IDs
                Appointment.initCounter(appointmentID);

                int patientID = Integer.parseInt(tokens[1]);
                int examID = Integer.parseInt(tokens[2]);
                //Boolean.parseBoolean returns true only if the string is "true"
                boolean fastResults = Boolean.parseBoolean(tokens[3]);
                String examDate = tokens[4];
                //reconstruct the Appointment object
                //Field order matches the Appointments.toFileString()
                appointments.add(new Appointment(appointmentID, patientID, examID, fastResults, examDate));
            }
        } catch (Exception e) {
            System.out.println("Could not load appointments: " + e.getMessage());
        }
        return appointments;
    }

    //store all doctors to file, one per line.
    //FileWriter overwrites the file by default, replacing old data with current list.
    public static void storeDoctors(ArrayList<Doctor> doctors) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOCTORS_TXT))) {
            for (Doctor doctor : doctors) {
                //toFileString() formats the doctor as comma-separated line.
                writer.write(doctor.toFileString());
                writer.newLine();
            }
        } catch(IOException e) {
            System.out.println("Can not save doctors: " + e.getMessage());
        }
    }

    //store all patients to file, one per line.
    //FileWriter overwrites the file by default, replacing old data with current list.
    public static void storePatients(ArrayList<Patient> patients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATIENTS_TXT))) {
            for (Patient patient : patients) {
                writer.write(patient.toFileString());
                writer.newLine();
            }
        } catch(IOException e) {
            System.out.println("Can not save patients: " + e.getMessage());
        }
    }

    //Store all exams to file, one per line.
    //Each subclass overrides toFileString() to include its own specific field
    //(machineType, sampleType or specialty) after the common fields.
    //FileWriter overwrites the file by default, replacing old data with current list.
    public static void storeExams(ArrayList<Exam> exams) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXAMS_TXT))) {
            for (Exam exam : exams) {
                writer.write(exam.toFileString());
                writer.newLine();
            }
        } catch(IOException e) {
            System.out.println("Can not save exams: " + e.getMessage());
        }
    }

    //store all appointments to file, one per line.
    public static void storeAppointments(ArrayList<Appointment> appointments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPOINTMENTS_TXT))) {
            for (Appointment appointment : appointments) {
                writer.write(appointment.toFileString());
                writer.newLine();
            }
        } catch(IOException e) {
            System.out.println("Can not save appointments: " + e.getMessage());
        }
    }

}
