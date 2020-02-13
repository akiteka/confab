package servlet;

import java.util.List;

import model.Mutter;

public class Topicstest {

	public static void main(String[] args) {
		test1();
	}

	public static void test1() {
		Topics_db dao =new Topics_db();
		List<Mutter> topicList = dao.topicAll();
		System.out.println(topicList.get(1));
	}
}
