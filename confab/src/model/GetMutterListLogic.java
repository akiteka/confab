package model;

import java.util.List;

import servlet.MutterDAO;
import servlet.Topics_db;

public class GetMutterListLogic {
	public List<Mutter> execute(Mutter mutter){
		MutterDAO dao =new MutterDAO();
		List<Mutter> mutterList = dao.findAll(mutter);
		return mutterList;
	}
	public List<Mutter> execute(){
		Topics_db dao =new Topics_db();
		List<Mutter> topicList = dao.topicAll();
		return topicList;
	}
}
