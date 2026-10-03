package project1UI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 로그인 화면과 메인 화면의 이벤트 클래스(LoginEvt, MainEvt)에서 공통으로 사용하는
 * 메시지창 / 확인창 기능을 모아 둔 클래스입니다.
 * 
 * - jf : 다이얼로그를 띄울 부모 창 (LoginUI 또는 MainUI)
 * - msgMessage : 메시지창에 보여줄 내용
 * - cfMessage : 확인창에 보여줄 내용
 * - flag : 확인창에서 누른 버튼 값
 * 
 * 사용 순서: setter로 문구 설정 후 다이얼로그 메소드 호출
 */
public class StandardUI {
	private JFrame jf; // 다이얼로그의 부모 창 (LoginUI 또는 MainUI)
	private String msgMessage; // 메시지창에 보여줄 내용
	private String cfMessage; // 확인창에 보여줄 내용
	private int flag; // 확인창에서 누른 버튼 값
	public static final int EOF = -1; // 파일 읽기가 끝났을 때 read()가 돌려주는 값

	/**
	 * 기본 생성자
	 * @param jf 다이얼로그를 띄울 부모 창
	 */
	public StandardUI(JFrame jf) {
		this.jf = jf;
	}

	/**
	 * 설정해 둔 문구로 메시지창을 띄웁니다.
	 */
	public void msgDialog() {
		// 메시지창 (부모 창, 설정한 메시지 내용)
		JOptionPane.showMessageDialog(jf, msgMessage);
	}

	/**
	 * 설정해 둔 문구로 예/아니요 확인창을 띄웁니다.
	 * @return 예 - 0 (YES_OPTION), 아니요 - 1 (NO_OPTION), 창의 X 버튼 - -1
	 */
	public int cfDialog() {
		// 확인창 (부모 창, 설정한 메시지 내용, 제목, 예/아니요 버튼)
		flag = JOptionPane.showConfirmDialog(jf, cfMessage, "확인", JOptionPane.YES_NO_OPTION);

		return flag; // 누른 버튼 값 반환
	}

	// 다이얼로그에 사용할 문구의 getter / setter

	public String getMsgMessage() {
		return msgMessage;
	}

	public void setMsgMessage(String msgMessage) {
		this.msgMessage = msgMessage;
	}

	public String getCfMessage() {
		return cfMessage;
	}

	public void setCfMessage(String cfMessage) {
		this.cfMessage = cfMessage;
	}

}
