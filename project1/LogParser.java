package project1;

import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
	//정규식 : [응답코드][URL][브라우저][일시] 추출
	private static final Pattern LOG_PATTERN =
			Pattern.compile("\\[(\\d{3})\\]\\[(https?://[^\\]]+)\\]\\[([^\\]]+)\\]\\[([^\\]]+)\\]");
	private int totalCount=0;
	private final Map<String, Integer> statusCodeMap=new HashMap<>();
	private final Map<String, Integer> browserMap=new HashMap<>();
	private final Map<String, Integer> keyMap=new HashMap<>();
	private final List<String> invalidLogLines=new ArrayList<>();

	/**
	 * 로그 파일 파싱 처리
	 * @param filePath 로그 파일 경로
	 */
	public void parseFile(String filePath) {
		try (BufferedReader br=new BufferedReader(new FileReader(filePath))) {
			String line;
			while((line = br.readLine()) != null) {
				line = line.trim();
				if(line.isEmpty()) continue;

				Matcher matcher=LOG_PATTERN.matcher(line);
				if(matcher.find()) {
					totalCount++;
					String statusCode = matcher.group(1);
					String url = matcher.group(2);
					String browser = matcher.group(3);
					String timestamp = matcher.group(4);

					//응답코드 카운트
					statusCodeMap.put(statusCode, statusCodeMap.getOrDefault(statusCode, 0) +1);
					//브라우저 카운트
					browserMap.put(browser, browserMap.getOrDefault(browser, 0) + 1);
					//URL 내 key 추출 및 카운트
					extractAndCountKey(url);
				} else {
					invalidLogLines.add(line);
				}
			}
		}catch (IOException e) {
			System.err.println("파일 읽기 오류: " + e.getMessage());
		}
	}

	/**
	 * URL key 값 추출
	 * @param url
	 */
	private void extractAndCountKey(String url) {
		if(url.contains("key=")) {
			String queryString = url.substring(url.indexOf("?") +1);
			String [] params = queryString.split("&");
			for (String param : params) {
				String [] pair = param.split("=");
				if (pair.length == 2 && pair[0].equals("key")) {
					String keyVal = pair[1];
					keyMap.put(keyVal, keyMap.getOrDefault(keyVal, 0)+1);
				}
			}
		}
	}
	/**
	 * 결과 출력
	 */
	public void printSummary() {
		System.out.println("================ 로그 분석 결과 ================");
		System.out.println("총 로그 건수: " + totalCount + "건");

		System.out.println("\n[1. 응답 상태 코드별 횟수]");
		for (Map.Entry<String, Integer> entry : statusCodeMap.entrySet()) {
			String desc = getStatusDescription(entry.getKey());
			System.out.printf(" - %s (%s): %d회\n", entry.getKey(), desc, entry.getValue());
		}

		System.out.println("\n[2. 브라우저별 요청 횟수]");
		for (Map.Entry<String, Integer> entry : browserMap.entrySet()) {
			System.out.printf(" - %s: %d회\n", entry.getKey(), entry.getValue());
		}

		System.out.println("\n[3. 주요 검색 키워드(key)별 빈도]");
		for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
			System.out.printf(" - %s: %d회\n", entry.getKey(), entry.getValue());
		}

		if (!invalidLogLines.isEmpty()) {
			System.out.println("\n[4. 형식이 올바르지 않은 로그 라인 (" + invalidLogLines.size() + "건)]");
			for (String invalid : invalidLogLines) {
				System.out.println(" - " + invalid);
			}
		}
		System.out.println("================================================");
	}
	private String getStatusDescription(String code) {
		switch (code) {
		case "200": return "성공";
		case "403": return "권한없음";
		case "404": return "페이지없음";
		case "500": return "서버내부오류";
		default: return "기타상태";
		}
	}
}