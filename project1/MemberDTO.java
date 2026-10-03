package project1;

/**
 * 1프로그램 시작이 메뉴 패널을 보여준다
내용 - 메뉴의 번호를 입력해 주세요 \n  예) 1입력 2출력 3종료


2번 의 경우
이름과 나이를 입력할수 있는 패널을 보여준다
내용 - 이름과 나이를 ,로 구분하여 입력하시오 \n 홍길동,20


3입력 받은 나이와 이름 형식이 조건에 맞다면  MemberDTO에 추가하고
DTO 를 인스턴스 변수로 선언된 list에 추가하고 다시 처음 화면 을 보여준다 -> 1번

4메뉴창에서 2를 입력하면 인스턴스 변수로 선언된 list 의 모든 값을 아래 형식으로 콘솔에 출력한다
번호 이름 나이
1     홍길   34
2     김박  45
3      오도  34
나이 합[63세]


5 메뉴종료를 누르면 프로그램을 종료한다
 */
public class MemberDTO {
	
	private String name;
	private int age;
	
	
	
	

	public MemberDTO(String name, int age) {
		
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	


}
