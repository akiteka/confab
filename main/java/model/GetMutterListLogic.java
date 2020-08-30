package model;

import java.util.List;

import servlet.MutterDAO;
import servlet.TopicsDB;

public class GetMutterListLogic {
	public List<Mutter> execute(Mutter mutter){
		MutterDAO dao =new MutterDAO();
		List<Mutter> mutterList = dao.findAll(mutter);
		return mutterList;
	}
	public List<Mutter> execute(){
		TopicsDB dao =new TopicsDB();
		List<Mutter> topicList = dao.topicAll();
		return topicList;
	}
}
