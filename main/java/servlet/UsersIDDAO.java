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
			Class.forName("org.postgresql.Driver");
			//heroku上でデータベース接続に使用する情報
			Connection connection = null;
			String dbUri = System.getenv("url");
			String userName = System.getenv("user");
			String passWord = System.getenv("Password");

			Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbUri, // "jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
            		userName, // ログインロール
            		passWord); // パスワード

			//SELECT文を準備
			String sql = "SELECT NAME FROM USERS WHERE NAME=?";
			PreparedStatement pStmt = connection.prepareStatement(sql);
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


