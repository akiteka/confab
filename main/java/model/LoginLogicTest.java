package model;

public class LoginLogicTest {

	public static void main(String[] args) {
		test1();
		test2();
	}

	public static void test1() {
		Users users = new Users("test","1234");
	    LoginLogic bo = new LoginLogic();
		boolean result = bo.execute(users);
		if(result) {
			System.out.println("test1:成功");
		}else{
			System.out.println("test1:失敗");
		}
	}
	public static void test2() {
		Users users = new Users("test","12345");
	    LoginLogic bo = new LoginLogic();
		boolean result = bo.execute(users);
		if(!result) {
			System.out.println("test2:成功");
		}else{
			System.out.println("test2:失敗");
		}
	}

}