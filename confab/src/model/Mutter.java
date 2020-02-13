package model;

import java.io.Serializable;

public class Mutter implements Serializable {
  private int id; //id名
  private String topic; //テーブル名
  private String userName; // ユーザー名
  private String text; // つぶやき内容

  public Mutter() {  }

  public Mutter(String topic) {
	this.topic = topic;
  }

  public Mutter(String userName, String text, String topic) {
    this.userName = userName;
    this.text = text;
    this.topic = topic;
  }

  public Mutter(int id,String userName, String text,String topic) {
	this.id  =id;
    this.userName = userName;
    this.text = text;
    this.topic = topic;
  }

  public int getId() {return id;}
  public String getTopic() {return topic;}
  public String getUserName() {return userName;}
  public String getText() {return text;}
}