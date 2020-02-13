package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.Users;

public class UsersIDDAO{
	//データベース接続に使用する情報

	public Account findByUserID(Users users){
		Account account = null;

		//データベースへ接続
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/c:\\data\\user", "sa", "1234");
			//SELECT文を準備
			String sql = "SELECT NAME FROM USERS WHERE NAME=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,users.getName());

			//SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			//一致したユーザがいた場合
			//そのユーザを表すAccountインスタンスを作成
			if(rs.next()) {
				String name = rs.getString("NAME");
				account = new Account(name);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB接続エラー");
			return null;
		}
		return account;
	}
}


