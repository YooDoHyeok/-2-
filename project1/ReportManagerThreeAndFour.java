package package1;

import java.util.*;

public class ReportManagerThreeAndFour {

    //반환형이 void라서 나중에 사용시  serviceCount1의 값들을 사용해야됩니다
    //404와 200에러 검출 및 빈도를 카운트 하는 함수 있니다

    public void showServiceSuccessOrFailCount(List<logDTO> logDTOlist) {
        // 3. 서비스를 성공적으로 수행한(200) 횟수,실패(404) 횟수
        // 1885번 에러코드: 200||키 값: img||경로값 ora||브라우저 종류: ie||시간대: 11
        //행의 총개수 1987 개

        String errorCode = null;
        HashMap<String, Integer> serviceCount1 = new HashMap<String,Integer>();
        serviceCount1.put("200",0);
        serviceCount1.put("404",0);

        int value =1;
        for (int i = 0; i < logDTOlist.size(); i++) {

            // 리스트에서 에러 코드 가져옴
            errorCode = logDTOlist.get(i).getErrorCode();

            if (errorCode.equals("200") || errorCode.equals("404")) {
                value = serviceCount1.get(errorCode);

                value +=1;

                serviceCount1.put(errorCode, value);

            } // 에러코드 확인 if문
        }


        System.out.println("200의 누적 횟수 :"+serviceCount1.get("200") +"번");
        System.out.println("404에러 의 누적 횟수: "+serviceCount1.get("404")+"번");

        System.out.println("==========");
    }// 메서드 끝


    //가장 바쁜 시간을 출력하는 코드 입니다
    //입력은 선택된 데이터 범위 이고 출력은 가장 바쁜 시간대(String)


    public String showPeakTime2(List<logDTO> logDTOlist) {
        //4. 요청이 가장 많은 시간 [10시]
        //1 시간대를 세트로 만들어서 뭔 시간 있는지 부터 확인

//        Set<String> Times =new HashSet<String>();

        Map<String,Integer> hourMap = new HashMap<String,Integer>();
        String hour =null;
        for (int i = 0; i < logDTOlist.size(); i++) {
            hourMap.put(logDTOlist.get(i).getHour(),hourMap.getOrDefault(logDTOlist.get(i).getHour(),0)+1);
        }

        //가장 큰 값의 키를 찾기
        int max = 0;
        String peakHour ="";
        for(Map.Entry<String,Integer> entry: hourMap.entrySet()) {
            if(max < entry.getValue()) {
                max = entry.getValue();
                peakHour = entry.getKey();

            }System.out.println();


        }
        System.out.println(hourMap);
        System.out.println("가장 많은 시간"+peakHour);

        return peakHour;



    }//peakTime2()
}
