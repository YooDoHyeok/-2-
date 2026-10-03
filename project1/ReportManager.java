package project1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class ReportManager {

    /**
     * 5. 비정상적 요청(403) 횟수 및 전체 요청 대비 비율(%)을 구하는 기능
     */
    public void calculate403Error(List<logDTO> logList) {
        if (logList == null || logList.isEmpty()) {
            System.out.println("로그 데이터가 없습니다.");
            return;
        }

        int totalCount = logList.size();
        int error403Count = 0;

        for (logDTO log : logList) {
            // logDTO의 errorCode 필드 확인
            if ("403".equals(log.getErrorCode())) {
                error403Count++;
            }
        }

        // 전체 요청 대비 비율 계산 (0으로 나누는 예외 방지)
        double ratio = (totalCount > 0) ? ((double) error403Count / totalCount) * 100 : 0.0;

        System.out.println("비정상적 요청(403) 횟수: " + error403Count + "회");
        System.out.printf("전체 요청 대비 비율: %.2f%%\n", ratio);
    }

    /**
     * 8. 파일 저장 및 포맷 출력 기능 (Root 권한 체크 포함)
     * @param userId 로그인한 아이디 (root 계정 체크용)[cite: 4]
     * @param logList 전체 로그 리스트[cite: 6]
     * @param analysisResults 1~6번의 분석 결과 문자열 배열 또는 데이터
     */
    public void createReportFile(String userId, List<logDTO> logList, String[] analysisResults) {
        // 1. Root 계정 권한 체크 (root 계정은 report 문서 생성 불가)[cite: 4]
        if ("root".equals(userId)) {
            JOptionPane.showMessageDialog(null, "문서를 생성할 수 있는 권한이 없음", "권한 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. 파일 저장 경로 및 폴더 생성 (c:/dev/report)[cite: 4]
        String dirPath = "c:/dev/report";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs(); // 폴더가 없으면 생성
        }

        // 3. 파일명 생성 (report_생성날짜.dat)[cite: 4]
        long currentTimeMillis = System.currentTimeMillis();
        String fileName = "report_" + currentTimeMillis + ".dat";
        File file = new File(dir, fileName);

        // 현재 날짜 시간 포맷 (생성된 날짜 년-월-일 시간:분:초)[cite: 4]
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(new Date());

        // 4. 요구하시는 파일 출력 예시 포맷에 맞춰 데이터 작성[cite: 4]
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("파일 타이틀\n");
            fw.write("파일명(sist_input_1) log (" + formattedDate + ")\n\n");
            fw.write("출력\n");
            
            // 1~6번 분석 결과 기록[cite: 4]
            if (analysisResults != null) {
                for (int i = 0; i < analysisResults.length; i++) {
                    fw.write((i + 1) + ". " + analysisResults[i] + "\n");
                }
            }
            
            JOptionPane.showMessageDialog(null, "저장되었습니다.\n경로: " + file.getAbsolutePath(), "성공", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 종료 버튼 클릭 시 컨펌로그 창 연동 처리 보조[cite: 4]
     */
    public void confirmExit() {
        int choice = JOptionPane.showConfirmDialog(
            null, 
            "종료 하시겠습니까?", 
            "종료 확인", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0); // 프로그램 종료
        }
    }
}
