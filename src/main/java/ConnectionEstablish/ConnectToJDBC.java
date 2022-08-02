package ConnectionEstablish;

import java.sql.*;

public class ConnectToJDBC {

  public static Connection getMySqlConnection() {
    Connection connection = null;
    System.out.println("Hello");
    String url = "jdbc:mysql://127.0.0.1/mydb";
    String uname = "root";
    String pass = "geetvirm";

    try {
      connection =  DriverManager.getConnection(url, uname, pass);

    } catch (SQLException ex) {
      System.out.println("Connection Failed! Check output console");
      ex.printStackTrace();
    }
    return connection;
  }

  public static void main(String...args) {}

}
