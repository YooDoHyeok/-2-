package project1;

import java.util.HashMap;
import java.util.Map;

public class LoginCheck {
    // 임시 사용자 데이터베이스 (ID, Password)
    private static final Map<String, String> userDb = new HashMap<>();

    static {
        userDb.put("admin", "1234");
        userDb.put("sistUser", "sist123");
        userDb.put("analyzer", "pass123");
    }

    /**
     * 로그인 검증 메서드
     * @param id 사용자 ID
     * @param password 사용자 비밀번호
     * @return 로그인 성공 여부
     */
    public boolean authenticate(String id, String password) {
        if (id == null || password == null) {
            return false;
        }
        
        String storedPassword = userDb.get(id);
        return storedPassword != null && storedPassword.equals(password);
    }
}
