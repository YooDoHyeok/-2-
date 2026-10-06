package project1;

import java.util.HashMap;
import java.util.Map;

public class LoginCheck {
    private static final Map<String, String> userDb = new HashMap<>();

    static {
        userDb.put("admin", "1234");
        userDb.put("root", "1111");
        userDb.put("administrator", "12345");
    }

    public boolean authenticate(String id, String password) {
        if (id == null || password == null) {
            return false;
        }
        String storedPassword = userDb.get(id);
        return storedPassword != null && storedPassword.equals(password);
    }
}