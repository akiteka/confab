package model;
import servlet.UsersIDDAO;

public class UsersIDlogic {
	public boolean execute(Users users) {
	UsersIDDAO dao  =new UsersIDDAO();
	Account account = dao.findByUserID(users);
	return account != null;
	}
}
