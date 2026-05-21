import java.util.List;
//Utility class providing a generic search method for any list of Identifiable objects.
public class SearchHelper {
    //Private constructor prevents this utility class from being created as an object.
    private SearchHelper() {
    }
    //Searches a list of any Identifiable type (Doctor, Patient, Exam, Appointment)
    //for an object with the given ID.
    //Returns the object if found, or null if no match exists.
    //The caller is responsible for checking if the return value is null.
    public static <T extends Identifiable> T findByID(List<T> items, int id) {
        for (T item : items) {
            if (item.getID() == id) {
                return item;
            }
        }
        //no match found
        return null;
    }
}
