package project1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class MainEvt extends WindowAdapter implements ActionListener {

    private MainUI mu;
    private StandardUI sd;
    private boolean isRoot;
    private File logDirectory;
    private File[] logFiles;
    private List<logDTO> logList;
    private ReportManager reportManager;
    private String lastAnalysisResult = "";

    private static final Pattern LOG_PATTERN =
            Pattern.compile("\\[(\\d{3})\\]\\[(https?://[^\\]]+)\\]\\[([^\\]]+)\\]\\[([^\\]]+)\\]");

    public MainEvt(MainUI mu, boolean isRoot) {
        this.mu = mu;
        this.isRoot = isRoot;
        this.sd = new StandardUI(mu);
        this.reportManager = new ReportManager();

        logDirectory = new File("c:/dev/log/");
        logList = new ArrayList<>();
    }

    public void loadAndPrintLogs() {
        if (!logDirectory.exists() || !logDirectory.isDirectory()) {
            sd.setMsgMessage("현재 해당하는 디렉토리가 없습니다.\n예) c:/dev/log");
            sd.msgDialog();
            return;
        }

        logFiles = logDirectory.listFiles();
        if (logFiles == null || logFiles.length == 0) {
            sd.setMsgMessage("현재 해당하는 디렉토리에 파일이 없습니다.");
            sd.msgDialog();
            return;
        }

        logList.clear();

        for (File file : logFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    Matcher matcher = LOG_PATTERN.matcher(line);
                    if (matcher.find()) {
                        String errorCode = matcher.group(1);
                        String url = matcher.group(2);
                        String browser = matcher.group(3);
                        String datetime = matcher.group(4);

                        String keyValue = "";
                        if (url.contains("key=")) {
                            String query = url.substring(url.indexOf("?") + 1);
                            for (String param : query.split("&")) {
                                String[] pair = param.split("=");
                                if (pair.length == 2 && pair[0].equals("key")) {
                                    keyValue = pair[1];
                                }
                            }
                        }

                        String hour = "";
                        if (datetime.length() >= 13) {
                            hour = datetime.substring(11, 13) + "시";
                        }

                        logList.add(new logDTO(errorCode, keyValue, url, browser, hour));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 사용자가 '시작'과 '끝' 입력란에 라인 번호를 입력했는지 확인
        String startStr = mu.getJtfStart().getText().trim();
        String endStr = mu.getJtfEnd().getText().trim();

        if (!startStr.isEmpty() && !endStr.isEmpty()) {
            try {
                int startLine = Integer.parseInt(startStr);
                int endLine = Integer.parseInt(endStr);
                lastAnalysisResult = reportManager.getLineRangeAnalysisSummary(logList, startLine, endLine);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(mu, "시작과 끝 라인에는 숫자만 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            // 입력값이 없으면 전체 분석 요약 출력
            lastAnalysisResult = reportManager.getAnalysisSummary(logList);
        }

        mu.getJtaPrint().setText(lastAnalysisResult);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == mu.getPrintJbtn()) {
            loadAndPrintLogs();
        }
        if (ae.getSource() == mu.getSaveJbtn()) {
            reportManager.createReportFile(isRoot, logList, lastAnalysisResult);
        }
        if (ae.getSource() == mu.getCloseJbtn()) {
            closeProgram();
        }
    }

    @Override
    public void windowClosing(WindowEvent we) {
        mu.dispose();
    }

    public void closeProgram() {
        sd.setCfMessage("종료하시겠습니까?");
        int flag = sd.cfDialog();
        if (flag == JOptionPane.YES_OPTION) {
            mu.dispose();
        }
    }
}