package project1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 인증정보는 ID,PASSWORD
 * ‘admin,1234’, ‘root, 1111’,’administrator,12345’로 인증 될 수 있습니다.
	이때 root 계정으로 인증하면 report 문서를 생성할 수 없습니다. “문서를 생성할 수 있는 권한이 없음”을 보여줍니다
 */
public class LoginCheck {
	public LoginCheck() {
		Map<String, String> userDatabase=new HashMap<String, String>();
		//회원 ID, PASSWORD 정보
		userDatabase.put("admin", "1234");
		userDatabase.put("root", "1111");
		userDatabase.put("administrator", "12345");

		Scanner sc=new Scanner(System.in);
		System.out.print("아이디 : ");
		String id=sc.nextLine();
		System.out.print("비밀번호 : ");
		String password=sc.nextLine();

		if(userDatabase.containsKey(id)) {
			if(userDatabase.get(id).equals(password)) {
				if(id.equals("root")) {
					System.out.println("이 계정은 문서를 생성할 수 있는 권한이 없습니다.");
				}else {
					System.out.println(id+"님 인증되셨습니다.");
				}
			}else {
				System.out.println("비밀번호가 일치하지 않습니다.");
			}

		}else {
			System.out.println("아이디가 존재하지 않습니다.");
		}


	}
	public static void main(String[] args) {
		new LoginCheck();
	}
}
