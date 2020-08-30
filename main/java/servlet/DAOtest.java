package servlet;
import model.Account;
import model.Users;

public class DAOtest {

	public static void main(String[] args) {
		test1();
		test2();
	}

	public static void test1() {
		Users users = new Users("てすと","1234");
		UsersDAO dao = new UsersDAO();
		Account result = dao.findByLogin(users);
		if(result != null &&
			result.getName().equals("てすと") &&
			result.getPass().equals("1234")
			) {
			System.out.println("test1:成功");
		}else{
			System.out.println("test1:失敗");
		}
	}
	public static void test2() {
		Users users = new Users("てすと","1234");
		UsersDAO dao = new UsersDAO();
		Account result = dao.findByLogin(users);
		if(result == null){
			System.out.println("test2:成功");
		}else{
			System.out.println("test2:失敗");
		}
	}

}
