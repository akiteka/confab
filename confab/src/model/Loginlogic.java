package model;
import servlet.UsersDAO;

public class Loginlogic {
	public boolean execute(Users users) {
	UsersDAO dao  =new UsersDAO();
	Account account = dao.findByLogin(users);
	return account != null;
	}
}
