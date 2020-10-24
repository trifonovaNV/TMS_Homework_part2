package by.tms.lesson2.classes;

import by.tms.lesson2.exceptions.UserException;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
public class UserBase {

    private static HashMap<String, User> data = new HashMap<>();

    public static void addUser(User user) throws UserException {
        if (!data.containsKey(user.getUsername())) {
            data.put(user.getUsername(), user);
        } else {
            throw new UserException("This user already exists.");
        }
    }

    public static User getUser(String username) {
        return data.get(username);
    }
}
