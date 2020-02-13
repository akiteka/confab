package model;

import servlet.MutterDAO;

public class PostMutterLogic {
  public void execute(Mutter mutter) {
    MutterDAO dao = new MutterDAO();
    dao.create(mutter);
  }
}