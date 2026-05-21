import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Utility class containing common helper methods used across the project.
public class HelperMethods {

    private static final Scanner sc = new Scanner(System.in);
    //method showAll can be common for all (except exams, that need a sorting)
    //generic type <T> so it works for Doctor, Patient, Appointment
    public static <T> void showAll (ArrayList<T> listName) {
        if (listName.isEmpty()) {
            System.out.println("This list is empty. No objects found.");
            return;
        }
        for (T item : listName) {
            //calls the toString() of whatever object T is at runtime
            System.out.println(item);
        }
    }

    //displays numbered list of options and returns the user's choice
    //used wherever the user must choose from a predefined list (e.g. SPECIALTIES)
    //loops until a valid number is entered
    public static String chooseFromList(String message, List<String> options) {
        int choice;

        while (true) {
            System.out.println();
            System.out.println(message);

            for (int i = 0; i < options.size(); i++) {
                //user-friendly indexing (1 based)
                System.out.println((i + 1) + ". " + options.get(i));
            }

            System.out.println("\nChoose: ");
            try {
                choice = Integer.parseInt(sc.nextLine());

                if (choice >= 1 && choice <= options.size()) {
                    //convert back to 0 based index to retrieve the choice.
                    return options.get(choice - 1);
                }

                System.out.println("That was not a valid choice. Please try again.");
            } catch(NumberFormatException e) {
                System.out.println("Please give a number.");
            }
        }
    }

    //returns the next available ID for any list of Identifiable objects
    //generic type <T> so it works for Doctor, Exam, Patient
    //appointments can be deleted, so we need a different approach
    public static <T extends Identifiable> int getNextID(List<T> items) {
        int max = 0;
        //safe approach even if IDs are not in order
        for (T item : items) {
            if (item.getID() > max) {
                //find the current maximum ID
                max = item.getID();
            }
        }
        //return the maximum ID found + 1
        return max + 1;
    }
}
