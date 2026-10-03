package project1UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class LoginEvt extends WindowAdapter implements ActionListener {

	private StandardUI sd; //Standard클래스 객체
	private LoginUI lu; //LoginUI클래스 객체
	private boolean isRoot; //아이디 root인지 아닌지 판별
	private Map<String, String> account; //고정된 아이디 비밀번호값들 저장

	public LoginEvt(LoginUI lu) { // 생성자 로그인 UI의 객체를 받는다
		// 필드값 초기화
		this.lu = lu; 
		this.sd = new StandardUI(lu);
		this.isRoot = false;
		
		// account맵을 해쉬맵으로 만들고 아이디와 비밀번호 값들을 추가
		account = new HashMap<>();
		account.put("admin", "1234");
		account.put("root", "1111");
		account.put("administrator", "12345");
	}

	/**
	 * 아이디와 비밀번호 유효성 검사
	 * 1.아이디 비밀번호 둘중 하나입력란을 비웠을떄 혹은 둘다 비웠을때 
	 * 2.잘못된 아이디나 비밀번호 혹은 둘다 틀렸을떄
	 * 3.root로 로그인할때 boolean값 저장
	 * 4.로그인 성공시 메세지를 띄우고, 로그인 창을 닫고 메인창을 띄운다.
	 */
	public void validateInput() {

	    String inputId = lu.getId().getText().trim(); // loginUI에 있는 아이디 값 가져오기 + 공백제거
	    String inputPass = new String(lu.getPass().getPassword()); // loginUI에 있는 비밀번호 값 가져오기

	    // 1. 빈칸 검사
	    if (inputId.isEmpty() || inputPass.isEmpty()) { // 아이디와 혹은 비밀번호가 없을시
	        sd.setMsgMessage("아이디와 비밀번호를 입력하세요");
	        sd.msgDialog();
	        return; // 메소드종료
	    }

	    // 2. 아이디 또는 비밀번호 검사
	    if (!account.containsKey(inputId)
	            || !account.get(inputId).equals(inputPass)) { // account맵과 같은 아이디 비밀번호와 다를시

	        sd.setMsgMessage("아이디 또는 비밀번호가 올바르지 않습니다");
	        sd.msgDialog();
	        return; //메소드 종료
	    }

	    // 3. root인지 확인
	    if (inputId.equals("root")) { // root아이디로 로그인 했을시
	        isRoot = true;
	    }

	    // 4. 로그인 성공
	    sd.setMsgMessage("로그인 되었습니다"); // Standard의 메세지 다이얼로그의 문구 수정
	    sd.msgDialog(); // 메세지 다이얼로그 실행

	    
	    lu.dispose(); // 원래 로그인 창닫기
	    openMainDialog(); // 메인 창 열기
	    
	}

	/**
	 * 각각 JFrame요소에 액션이벤트 설정
	 */
	@Override
	public void actionPerformed(ActionEvent ae) { 
		if (ae.getSource() == lu.getLoginBtn()) { // 로그인 버튼이 눌리면
			validateInput(); // validateInput 실행
		}

	}

	/**
	 * 윈도우 창을 닫을시에
	 */
	@Override
	public void windowClosing(WindowEvent e) {  
		lu.dispose(); // 윈도우 창 종료
	}


	/**
	 * MainUI를실행 시킬 메소드
	 */
	public void openMainDialog() {
		new MainUI(isRoot); // root의 boolean값을 같이 MainUI에 넘긴다.
	}

	
}
