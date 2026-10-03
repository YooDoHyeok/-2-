package project1UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class MainEvt extends WindowAdapter implements ActionListener {

	private MainUI mu; // MainUI 객체
	private StandardUI sd; // Standard 객체
	private boolean isRoot; // 로그인한 계정이 root인지 여부 (LoginEvt에서 전달)
	private int totalLogCount; // 총 로그 줄 수를 받을 int 변수
	private File logDirectory; // log와 관련된 경로 Path
	private File reportDirectory; // report와 관련된 경로 Path
	private File[] logFiles; // log 안에 있는 모든 파일을 File 객체의 배열로 저장
	private List<String> logs; // 모든 log의 내용을 받을 리스트
	private boolean validateFlag; // 유효성 통과 여부

	/**
	 * 기본 생성자
	 * @param mu MainUI객체
	 * @param isRoot LoginEvt에서 설정된 Root값 받기
	 */
	public MainEvt(MainUI mu, boolean isRoot) { 
		//필드 초기화
		this.mu = mu;
		this.isRoot = isRoot;
		this.sd = new StandardUI(mu);

		// 로그 경로 설정 + 모든 파일을 배열로 저장
		logDirectory = new File("c:/dev/log/");
		logFiles = logDirectory.listFiles();

		// 로그파일 내용을 읽어와서 받을 ArrayList
		logs = new ArrayList<String>();

		// 리포트 폴더 설정, 총 줄 수 0으로 초기화
		reportDirectory = new File("c:/dev/report/");
		totalLogCount = 0;
	}

	/**
	 * 로그 폴더와 로그 파일이 있는지 유효성 검증
	 */
	public boolean validateLog() {
		validateFlag = false; // 검증 결과 초기화 (통과하면 true로 변경)

		// 디렉토리가 없는 경우 메세지 출력
		if (!logDirectory.exists() || !logDirectory.isDirectory()) {
			sd.setMsgMessage("현재 해당하는 디렉토리가 없습니다.\n예) c:/dev/log");
			sd.msgDialog();
		} else { // 해당 디렉토리가 있을경우

			logFiles = logDirectory.listFiles(); // 메인창이 뜬 뒤에 넣은 파일도 읽도록 매번 목록을 다시 가져오기

			// 디렉토리에 파일이 없는 경우
			if (logFiles == null || logFiles.length == 0) {
				sd.setMsgMessage("현재 해당하는 디렉토리에 파일이 없습니다.");
				sd.msgDialog();

			} else { // 모든 유효성이 검증이 통과 될시에
				validateFlag = true;

			}

		}

		return validateFlag; // validateFlag값을 리턴
	}

	/**
	 * 로그파일에서 입력한 범위의 줄을 읽어 출력창에 표시
	 * 현재 필터 기능이 완료가 안되어있어서 사용자가 입력한 시작값과 끝값의 로그를 출력하는 
	 * 방식으로 메소드를 만들었습니다.
	 * 
	 * 모든 기능이 완료된다면 이 메소드는 반드시 수정되어야합니다.
	 * @throws IOException
	 */
	public void logPrint() throws IOException {
		int startLine; // 사용자가 입력한 시작값을 저장할 변수 
		int endLine; // 사용자가 입력한 끝값을 저장할 변수

		try {
			startLine = Integer.parseInt(mu.getJtfStart().getText()); // 사용자가 입력한 값을 int로 변환
			endLine = Integer.parseInt(mu.getJtfEnd().getText()); // 사용자가 입력한 값을 int로 변환

		} catch (NumberFormatException e) {
			// 빈칸, 문자, int 범위를 넘는 큰 수를 입력하면 변환에 실패하므로 안내 후 종료
			sd.setMsgMessage("숫자가 너무 크거나 잘못된 문자가 포함되어 있습니다.\n다시 입력해주세요.");
			sd.msgDialog();
			return;
		}

		totalLogCount = 0; // 전체 로그의 로그줄 초기화 (버튼을 누를 때마다 처음부터 다시 셈)
		logs.clear(); // 이전에 읽은 로그 내용 비우기 (중복 방지)

		int lineNumber = 0;  // 지금까지 읽은 줄 수 (반복문 밖에 있어서 파일1에서 파일2로 이어서 증가)

		for (File file : logFiles) {
			FileInputStream fis = null; // 파일 입력 스트림 변수 선언 (null로 시작)
			try {
				fis = new FileInputStream(file); // 파일과 연결되는 스트림 생성

				int readData = 0; // 읽은 1바이트를 저장할 변수
				StringBuilder sb = new StringBuilder(); // 이 파일에서 범위에 해당하는 내용을 모을 곳

				while ((readData = fis.read()) != StandardUI.EOF) { // 1바이트씩 읽고, 파일 끝(EOF)이면 반복 종료

					if (lineNumber >= startLine && lineNumber < endLine) { // 현재 줄이 출력 범위 안이면
						sb.append((char) readData);  // 읽은 글자를 sb에 추가
					}

					if (readData == '\n') { // 줄바꿈 문자를 만나면 한 줄이 끝난 것
						totalLogCount++; // 전체 로그 줄 수 증가
						lineNumber++; // 다음 줄 번호로 이동
					}

				}
				logs.add(sb.toString());  // 이 파일에서 모은 내용을 리스트에 저장
			} finally {

				if (fis != null) { // 스트림이 열려 있으면
					fis.close(); // 예외가 나도 반드시 스트림 닫기
				}
			}
		}

		if (endLine >= totalLogCount) { // 끝값이 전체 로그파일 줄 수 이상이면 에러 메시지
			logs.clear(); // 범위 오류면 저장되지 않도록 비우기
			sd.setMsgMessage("현재 로그는 " + totalLogCount + "개입니다.\n검색 범위를 확인해주세요.");
			sd.msgDialog();
			return;
		}

		if (startLine > endLine) { // 사용자가 입력한 시작값이 끝값보다 크면 에러 메시지
			logs.clear(); // 범위 오류면 저장되지 않도록 비우기
			sd.setMsgMessage("시작 번호는 끝 번호보다 클 수 없습니다.");
			sd.msgDialog();
			return;
		}

		StringBuilder logStr = new StringBuilder(); // 파일별 내용을 이어 붙일 곳
		for (String log : logs) { // 리스트에 담긴 내용을 하나씩 꺼내서
			logStr.append(log); // 이어 붙이기
		}
		mu.getJtaPrint().setText(logStr.toString()); // 출력창에 표시

	}

	/**
	 * 출력된 내용을 c:/dev/report 안에 report_저장한 날짜.dat 파일로 저장합니다.
	 * 
	 * logPrint의 내용을 그대로 dat 파일에 저장합니다.
	 * @throws IOException
	 */
	public void saveLogAsFile() throws IOException {
		if (!isRoot) { // root가 아니면 파일 생성 (root면 else에서 제한)

			if (logs.isEmpty()) { // 출력 버튼을 누르기 전이라 저장할 내용이 없으면
				sd.setMsgMessage("저장할 로그가 없습니다.\n먼저 출력 버튼을 눌러주세요.");
				sd.msgDialog();
				return; // 빈 파일이 저장되지 않도록 메소드 종료
			}

			if (!reportDirectory.exists()) {
				reportDirectory.mkdirs();
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 날짜 형식 지정 (예: 20261003)
			String date = sdf.format(new Date()); // 현재 날짜를 위 형식의 문자열로 변환
			
			// 저장할 파일 객체 지정 (폴더 / 파일명)
			File reportFile = new File(reportDirectory, "report_" + date + ".dat"); 

			FileOutputStream fos = null; // 파일 출력 스트림 변수 선언 (null로 시작)

			try {
				// 저장할 파일과 연결되는 출력 스트림 생성 (같은 이름의 파일이 있으면 덮어씀)
				fos = new FileOutputStream(reportFile);  

				StringBuilder sb = new StringBuilder(); // 파일별 내용을 이어 붙일 곳
				for (String log : logs) { // 리스트에 담긴 내용을 하나씩 꺼내서
					sb.append(log); // 이어 붙이기
				}
				
				fos.write(sb.toString().getBytes()); // 문자열을 byte 배열로 바꿔서 파일에 쓰기
				fos.flush();  // 스트림에 남아 있는 데이터를 파일로 모두 내보내기
			} finally {
				if (fos != null) { // 스트림이 열려 있으면
					fos.close();  // 예외가 나도 반드시 스트림 닫기
				}
			}
			// 저장 경로와 함께 성공 메시지 출력
			sd.setMsgMessage(reportFile.getAbsolutePath() + " 저장되었습니다");
			sd.msgDialog();
			return;
		} else {
			// root로 로그인한 경우 에러 메시지 출력
			sd.setMsgMessage("문서를 생성할 수 있는 권한이 없음");
			sd.msgDialog();
			return;
		}

	}

	/**
	 * MainUI에 있는 JFrame 요소들의 액션 이벤트 설정
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mu.getPrintJbtn()) { // 출력 버튼을 누르면
			try {
				validateLog(); // validateLog를 먼저 실행

				if (validateFlag) { // validateFlag가 true면
					logPrint(); // logPrint 실행
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (ae.getSource() == mu.getSaveJbtn()) { // 저장 버튼이 눌리면
			try {
				saveLogAsFile(); // saveLogAsFile 실행
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (ae.getSource() == mu.getCloseJbtn()) { // 종료 버튼을 누르면
			closeProgram(); // closeProgram 실행
		}

	}

	/**
	 * 실행된 윈도우 창의 X를 눌렀을 때 윈도우 닫기
	 */
	@Override
	public void windowClosing(WindowEvent we) {
		mu.dispose();
	}

	/**
	 * MainUI에서 종료 버튼을 눌렀을 때 종료하는 컨펌 다이얼로그
	 */
	public void closeProgram() {
		sd.setCfMessage("종료하시겠습니까?"); // 기본 종료 메세지 설정
		int flag = sd.cfDialog(); // 컨펌다이얼로그 불러오기

		if (flag == JOptionPane.YES_OPTION) { // "예"를 선택하면 창 닫기
			mu.dispose();
		}
	}
}
