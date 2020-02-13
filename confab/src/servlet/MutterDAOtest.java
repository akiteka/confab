package servlet;

import java.util.List;

import model.Mutter;

public class MutterDAOtest {

	public static void main(String[] args) {
		test1();
		//test2();
	}

	public static void test1() {
		Mutter mutter = new Mutter("ini");
		MutterDAO dao =new MutterDAO();
		List<Mutter> mutterList = dao.findAll(mutter);
		System.out.println(mutterList.get(1));
	}
	public static void test2() {
		Mutter mutter = new Mutter("おためし","お試しです","ROOM1");
		MutterDAO dao =new MutterDAO();
		dao.create(mutter);
	}

}
