package model;

public class LoginLogicTest {

	public static void main(String[] args) {
		test1();
		test2();
	}

	public static void test1() {
		Users users = new Users("てすと","12");
	    Loginlogic bo = new Loginlogic();
		boolean result = bo.execute(users);
		if(result) {
			System.out.println("test1:成功");
		}else{
			System.out.println("test1:失敗");
		}
	}
	public static void test2() {
		Users users = new Users("てすと","12345");
	    Loginlogic bo = new Loginlogic();
		boolean result = bo.execute(users);
		if(!result) {
			System.out.println("test2:成功");
		}else{
			System.out.println("test2:失敗");
		}
	}

}