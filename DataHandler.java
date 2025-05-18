import java.io.*;
import java.util.HashMap;

public class DataHandler {
    private static final String FILE_NAME = "users.dat";

    public static HashMap<String, User> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (HashMap<String, User>) ois.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static void saveUsers(HashMap<String, User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}