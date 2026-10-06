package project1;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginUI extends JFrame {
    private JLabel jlblLogin; 
    private JLabel jlblExplain; 
    private JLabel jlblId; 
    private JLabel jlblPassword; 
    private JTextField id; 
    private JPasswordField pass; 
    private JButton loginBtn; 

    public LoginUI() {
        super("로그인");
        setLayout(null);

        jlblLogin = new JLabel("로그인");
        jlblLogin.setFont(new Font("맑은 고딕", Font.BOLD, 30));

        jlblExplain = new JLabel("<html>root 계정으로 로그인 하면<br>파일 생성기능 권한은 없습니다.</html>");
        jlblExplain.setFont(new Font("맑은 고딕", Font.PLAIN, 15));

        jlblId = new JLabel("아이디");
        id = new JTextField();

        jlblPassword = new JLabel("비밀번호");
        pass = new JPasswordField();

        loginBtn = new JButton("로그인");

        add(jlblLogin);
        jlblLogin.setBounds(20, 0, 450, 100);
        add(jlblExplain);
        jlblExplain.setBounds(20, 80, 450, 50);

        add(jlblId);
        jlblId.setBounds(20, 160, 450, 20);
        add(id);
        id.setBounds(20, 180, 445, 45);

        add(jlblPassword);
        jlblPassword.setBounds(20, 230, 450, 20);
        add(pass);
        pass.setBounds(20, 250, 445, 45);

        add(loginBtn);
        loginBtn.setBounds(20, 350, 100, 35);
        
        LoginEvt le = new LoginEvt(this);
        loginBtn.addActionListener(le);
        addWindowListener(le);
        
        setResizable(false);
        setBounds(100, 100, 500, 500);
        setVisible(true);
    }

    public JTextField getId() {
        return id;
    }

    public JPasswordField getPass() {
        return pass;
    }

    public JButton getLoginBtn() {
        return loginBtn;
    }
}