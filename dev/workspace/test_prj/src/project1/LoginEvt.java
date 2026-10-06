package project1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class LoginEvt extends WindowAdapter implements ActionListener {

    private StandardUI sd;
    private LoginUI lu;
    private boolean isRoot;
    private Map<String, String> account;

    public LoginEvt(LoginUI lu) {
        this.lu = lu;
        this.sd = new StandardUI(lu);
        this.isRoot = false;
        
        // 요구사항 명세에 따른 인증 계정 등록
        account = new HashMap<>();
        account.put("admin", "1234");
        account.put("root", "1111");
        account.put("administrator", "12345");
    }

    public void validateInput() {
        String inputId = lu.getId().getText().trim();
        String inputPass = new String(lu.getPass().getPassword());

        // 1. 빈칸 검사
        if (inputId.isEmpty() || inputPass.isEmpty()) {
            sd.setMsgMessage("아이디와 비밀번호를 입력하세요");
            sd.msgDialog();
            return;
        }

        // 2. 아이디 또는 비밀번호 일치 여부 검사
        if (!account.containsKey(inputId) || !account.get(inputId).equals(inputPass)) {
            sd.setMsgMessage("아이디 또는 비밀번호가 올바르지 않습니다");
            sd.msgDialog();
            return;
        }

        // 3. root 계정 여부 판별 (root 로그인 시 리포트 생성 권한 제한)
        if (inputId.equals("root")) {
            isRoot = true;
        }

        // 4. 로그인 성공
        sd.setMsgMessage("로그인 되었습니다");
        sd.msgDialog();
        
        lu.dispose();
        openMainDialog();
    }

    @Override
    public void actionPerformed(ActionEvent ae) { 
        if (ae.getSource() == lu.getLoginBtn()) {
            validateInput();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {  
        lu.dispose();
    }

    public void openMainDialog() {
        new MainUI(isRoot);
    }
}