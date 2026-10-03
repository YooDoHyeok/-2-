package project1UI;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * extends JFrame 로그인UI 구조
 * - JLabel jlblLogin <br>
 * - JLabel jlblExplain <br>
 * - JLabel jlblId <br>
 * - JLabel jlblPassword <br>
 * - JTextField id <br>
 * - JPasswordField pass <br>
 * - JButton loginBtn <br>
 */
public class LoginUI extends JFrame {
	private JLabel jlblLogin; // 로그인 라벨
	private JLabel jlblExplain; // root로 로그인할시의 설명글
	private JLabel jlblId; // 아이디 입력창 위에 라벨
	private JLabel jlblPassword; // 비밀번호 입력창 위에 라벨
	private JTextField id; // 아이디 필드
	private JPasswordField pass; // 비밀번호 필드
	private JButton loginBtn; // 로그인 버튼

	public LoginUI() {
		super("로그인");
		
		setLayout(null); //배치 관리자 [Layout] 사용 안함

		//모든 라벨 텍스트 / 비밀번호 필드 로그인 버튼까지 컴포넌트 객체 생성
		jlblLogin = new JLabel("로그인");
		jlblLogin.setFont(new Font("맑은 고딕", Font.BOLD, 30));

		jlblExplain = new JLabel("<html>root 계정으로 로그인 하면<br>" + "파일 생성기능 권한은 없습니다.</html>");
		jlblExplain.setFont(new Font("맑은 고딕", Font.PLAIN, 15));

		jlblId = new JLabel("아이디");
		id = new JTextField();

		jlblPassword = new JLabel("비밀번호");
		pass = new JPasswordField();

		loginBtn = new JButton("로그인");

		
		// 각각의 요소들을 화면에 추가 및 크기 위치지정
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
		
		// 이벤트 처리 객체 생성
		LoginEvt le = new LoginEvt(this);
		
		// 로그인 버튼이 눌렀을떄 이벤트 발생
		loginBtn.addActionListener(le);
		
		// 윈도우에서 X를 눌렀을시에 windowClosing 호출
		addWindowListener(le);
		
		setResizable(false); // 크기 변경 불가
		setBounds(100, 100, 500, 500); // 윈도우를 띄울 위치와 창크기 설정
		setVisible(true); // 화면 보이기
	}

	// 각각 JFrame 요소들의 Getter 권한 설정
	
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
