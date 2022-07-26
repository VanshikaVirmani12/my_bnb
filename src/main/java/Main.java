import java.sql.*;
import java.util.Date;
import java.util.Arrays;
import java.util.List;


public class Main {

  public static void main (String[] args) throws Exception{

    String url = "jdbc:mysql://127.0.0.1/mydb";

    String uname = "root";
    String pass = "Vansvirm21#";

    //Class.forName("org.cj.mysql.jdbc.Driver");

    Connection con = DriverManager.getConnection(url, uname, pass);

    PreparedStatement execStat=con.prepareStatement(
            "SELECT * FROM Course");

    ResultSet rs = execStat.executeQuery();

    while (rs.next()) {
      String course_num = rs.getString("cNum");
      String sname = rs.getString("name");
      String dept = rs.getString("dept");
      int breadth = rs.getInt("breadth");

      System.out.println("Course number is " + course_num);
      System.out.println("Course name is " + sname);
      System.out.println("Department  is " + dept);
      System.out.println("Breadth  is " + breadth);
    }

  }
}