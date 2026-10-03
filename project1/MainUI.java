package project1UI;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainUI extends JFrame {

	private JLabel jlblPrint;
	private JTextArea jtaPrint;
	private JScrollPane jspPrint;
	
	private JLabel jlblStart;
	private JTextField jtfStart;
	private JLabel jlblEnd;
	private JTextField jtfEnd;

	private JButton saveJbtn;
	private JButton closeJbtn;
	private JButton printJbtn;

	public MainUI(boolean isRoot) {
		super("메인 프로그램");
		
		setLayout(null); //배치 관리자 [Layout] 사용 안함

		//모든 라벨 텍스트 / 비밀번호 필드 로그인 버튼까지 컴포넌트 객체 생성
		jlblPrint = new JLabel("출력");
		jlblPrint.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		
		jtaPrint = new JTextArea(); 
		jtaPrint.setEditable(false); // 출력창은 보기 전용 (사용자가 내용을 고치지 못하게)
		jspPrint = new JScrollPane(jtaPrint); // 출력창을 스크롤 패널로 감싸기


		jlblStart = new JLabel("시작 :");
		jlblStart.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		jtfStart = new JTextField();
		jlblEnd = new JLabel("끝 :");
		jlblEnd.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		jtfEnd = new JTextField();

		saveJbtn = new JButton("저장");
		closeJbtn = new JButton("종료");
		printJbtn = new JButton("출력");

		// 각각의 요소들을 화면에 추가 및 크기 위치지정
		add(jlblPrint);
		jlblPrint.setBounds(20, 0, 100, 100);
		add(jspPrint);
		jspPrint.setBounds(20, 90, 280, 280);

		add(saveJbtn);
		saveJbtn.setBounds(340, 90, 100, 35);
		add(closeJbtn);
		closeJbtn.setBounds(340, 130, 100, 35);

		add(jlblStart);
		jlblStart.setBounds(20, 390, 100, 35);
		add(jtfStart);
		jtfStart.setBounds(70, 397, 80, 25);

		add(jlblEnd);
		jlblEnd.setBounds(170, 390, 100, 35);
		add(jtfEnd);
		jtfEnd.setBounds(210, 397, 80, 25);

		add(printJbtn);
		printJbtn.setBounds(340, 392, 100, 35);

		// 이벤트 처리 객체 생성 (root 여부를 함께 전달)
		MainEvt me = new MainEvt(this, isRoot);

		// 버튼 클릭 시 actionPerformed 호출
		saveJbtn.addActionListener(me);
		closeJbtn.addActionListener(me);
		printJbtn.addActionListener(me);

		// 윈도우에서 X를 눌렀을 떄 windowClosing 호출
		addWindowListener(me);
		
		setResizable(false); // 크기 변경 불가
		setBounds(100, 100, 500, 500); // 윈도우를 띄울 위치와 창크기 설정
		setVisible(true);  // 화면 보이기
	}

	// 각각 JFrame 요소들의 Getter / Setter 권한 설정
	public JTextArea getJtaPrint() {
		return jtaPrint;
	}

	public JTextField getJtfStart() {
		return jtfStart;
	}

	public JTextField getJtfEnd() {
		return jtfEnd;
	}

	public JButton getSaveJbtn() {
		return saveJbtn;
	}

	public JButton getCloseJbtn() {
		return closeJbtn;
	}

	public JButton getPrintJbtn() {
		return printJbtn;
	}

}
