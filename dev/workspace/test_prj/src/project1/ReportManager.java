package project1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class ReportManager {

    // 1~6번 전체 분석 요약 생성
    public String getAnalysisSummary(List<logDTO> logList) {
        if (logList == null || logList.isEmpty()) {
            return "분석할 로그 데이터가 없습니다.";
        }

        int totalCount = logList.size();
        int count200 = 0, count403 = 0, count404 = 0, count500 = 0;
        int booksTotalCount = 0;
        int books500Count = 0;

        Map<String, Integer> browserMap = new HashMap<>();
        Map<String, Integer> keyMap = new HashMap<>();
        Map<String, Integer> hourMap = new HashMap<>();

        for (logDTO log : logList) {
            String code = log.getErrorCode();
            if ("200".equals(code)) count200++;
            else if ("403".equals(code)) count403++;
            else if ("404".equals(code)) count404++;
            else if ("500".equals(code)) count500++;

            // 브라우저 집계
            if (log.getBrowser() != null && !log.getBrowser().isEmpty()) {
                browserMap.put(log.getBrowser(), browserMap.getOrDefault(log.getBrowser(), 0) + 1);
            }
            // 키워드 집계
            if (log.getKeyValue() != null && !log.getKeyValue().isEmpty()) {
                keyMap.put(log.getKeyValue(), keyMap.getOrDefault(log.getKeyValue(), 0) + 1);
            }
            // 시간대 집계
            if (log.getHour() != null && !log.getHour().isEmpty()) {
                hourMap.put(log.getHour(), hourMap.getOrDefault(log.getHour(), 0) + 1);
            }
            // books 요청 및 500 에러 집계
            if (log.getPathFind() != null && log.getPathFind().contains("books")) {
                booksTotalCount++;
                if ("500".equals(code)) {
                    books500Count++;
                }
            }
        }

        double ratio403 = (totalCount > 0) ? ((double) count403 / totalCount) * 100 : 0.0;
        double books500Ratio = (booksTotalCount > 0) ? ((double) books500Count / booksTotalCount) * 100 : 0.0;

        // 최다 사용 키 찾기
        String maxKey = "없음";
        int maxKeyCount = 0;
        for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
            if (entry.getValue() > maxKeyCount) {
                maxKeyCount = entry.getValue();
                maxKey = entry.getKey();
            }
        }

        // 요청이 가장 많은 시간 찾기
        String maxHour = "없음";
        int maxHourCount = 0;
        for (Map.Entry<String, Integer> entry : hourMap.entrySet()) {
            if (entry.getValue() > maxHourCount) {
                maxHourCount = entry.getValue();
                maxHour = entry.getKey();
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== 로그 분석 결과 요약 ===\n");
        sb.append("총 로그 건수: ").append(totalCount).append("건\n\n");
        
        // 1. 최다 사용 키
        sb.append("1. 최다 사용 키: ").append(maxKey).append(" (").append(maxKeyCount).append("회)\n\n");
        
        // 2. 브라우저별 접속 횟수 및 비율
        sb.append("2. 브라우저별 접속 횟수 및 비율:\n");
        for (Map.Entry<String, Integer> entry : browserMap.entrySet()) {
            double browserRatio = (totalCount > 0) ? ((double) entry.getValue() / totalCount) * 100 : 0.0;
            sb.append(" - ").append(entry.getKey()).append(" - ").append(entry.getValue()).append("회 (").append(String.format("%.2f", browserRatio)).append("%)\n");
        }
        
        // 3. 성공(200) 및 실패(404) 횟수
        sb.append("\n3. 서비스를 성공적으로 수행한(200) 횟수: ").append(count200).append("회\n");
        sb.append("   실패(404) 횟수: ").append(count404).append("회\n");
        
        // 4. 요청이 가장 많은 시간
        sb.append("\n4. 요청이 가장 많은 시간: [").append(maxHour).append("]\n");
        
        // 5. 비정상적인 요청(403) 횟수 및 비율
        sb.append("\n5. 비정상적인 요청(403) 횟수: ").append(count403).append("회 (비율: ").append(String.format("%.2f", ratio403)).append("%)\n");
        
        // 6. books에 대한 요청 중 500 에러 발생 횟수 및 비율
        sb.append("\n6. books 요청 중 500 에러 발생 횟수: ").append(books500Count).append("회 (비율: ").append(String.format("%.2f", books500Ratio)).append("%)\n");

        return sb.toString();
    }

    // 7번 요구사항: 특정 라인 범위(예: 1000~1500라인)에 대한 정보 출력
    public String getLineRangeAnalysisSummary(List<logDTO> logList, int startLine, int endLine) {
        if (logList == null || logList.isEmpty()) {
            return "분석할 로그 데이터가 없습니다.";
        }

        Map<String, Integer> keyMap = new HashMap<>();
        int includedCount = 0;

        for (int i = 0; i < logList.size(); i++) {
            int currentLine = i + 1; // 1부터 시작하는 라인 번호
            if (currentLine >= startLine && currentLine <= endLine) {
                includedCount++;
                logDTO log = logList.get(i);
                if (log.getKeyValue() != null && !log.getKeyValue().isEmpty()) {
                    keyMap.put(log.getKeyValue(), keyMap.getOrDefault(log.getKeyValue(), 0) + 1);
                }
            }
        }

        String maxKey = "없음";
        int maxKeyCount = 0;
        for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
            if (entry.getValue() > maxKeyCount) {
                maxKeyCount = entry.getValue();
                maxKey = entry.getKey();
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(startLine).append(" ~ ").append(endLine).append("번째 라인 정보 분석 ===\n");
        sb.append("해당 구간 총 로그 건수: ").append(includedCount).append("건\n");
        sb.append("최다 사용 키의 이름과 횟수: ").append(maxKey).append(" / ").append(maxKeyCount).append("회\n");
        return sb.toString();
    }

    // 8번 요구사항: 파일 저장 (root 권한 체크 및 밀리초 타임스탬프 파일명 적용)
    public void createReportFile(boolean isRoot, List<logDTO> logList, String analysisResult) {
        if (isRoot) {
            JOptionPane.showMessageDialog(null, "문서를 생성할 수 있는 권한이 없음", "권한 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (logList == null || logList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "저장할 로그가 없습니다.\n먼저 출력 버튼을 눌러주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File dir = new File("c:/dev/report");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 요구사항 명세: report_1628605156919.dat 형태의 타임스탬프 적용
        String fileName = "report_" + System.currentTimeMillis() + ".dat";
        File file = new File(dir, fileName);

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(analysisResult);
            JOptionPane.showMessageDialog(null, "저장되었습니다.\n경로: " + file.getAbsolutePath(), "성공", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}