import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.sql.ResultSet;

public class Filter {

  String city;
  String country;
  String apt_name;
  String postal_code;
  Date start_date;
  Date end_date;
  String room_type;
  Integer price_low;
  Integer price_high;
  Boolean has_wifi;
  Boolean has_kitchen;
  Boolean has_washer;
  Boolean has_dryer;
  Boolean has_ac;
  Integer longitude;
  Integer latitude;

  public Filter() throws SQLException {
    this.city = city;
    this.country = country;
    this.apt_name = apt_name;
    this.postal_code = postal_code;
    this.start_date = start_date;
    this.end_date = end_date;
    this.room_type = room_type;
    this.price_low = price_low;
    this.price_high = price_high;
    this.has_wifi = has_wifi;
    this.has_kitchen = has_kitchen;
    this.has_washer = has_washer;
    this.has_dryer = has_dryer;
    this.has_ac = has_ac;
    this.longitude = longitude;
    this.latitude = latitude;

  }

  PreparedStatement ps;
  Connection connection;
  Statement sql = connection.createStatement();

  //---------------------------------------------------------------------------------------
  //Create jdbc_demo table
  String sqlQ;

//  public boolean filter_by_address(String city, String country, String apt_name) {
//    if (this.city.equalsIgnoreCase(city) && this.country.equalsIgnoreCase(country) &&
//            this.apt_name.equalsIgnoreCase(apt_name)) {
//      return true;
//    }
//    return false;
//  }

  public void filter_by_price_range(int price_low, int price_high) throws SQLException {
    sqlQ = "SELECT * from Calender\n" +
            "\tWHERE price >= price_low AND price <= price_high\n" +
            "\tORDER BY price\n";
    System.out.println("Executing this filter_by_price_range: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    ResultSet rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      System.out.print("City:"+rs.getString("City") +", ");
      // Print the data
    }

  }

  public void filter_by_long_lat(int log, int lat, int distance) throws SQLException{
    // if distance not defined, define a default
    // order by distance
    sqlQ = "";
    System.out.println("Executing this filter_by_long_lat: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    ResultSet rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      // Print the data
    }
  }

  public void filter_by_postal_code(String postal_code) throws SQLException{
    // same or adjacent postal codes
    sqlQ = "";
    System.out.println("Executing this filter_by_postal_code: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    ResultSet rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      // Print the data
    }
  }

  public void filter_by_address(String apt_name, String city, String country, String postal_code) throws SQLException {
    sqlQ = "";
    System.out.println("Executing this filter_by_address: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    ResultSet rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      // Print the data
    }
  }

  public void filter_by_date_range(Date start_date, Date end_date) throws SQLException {
    sqlQ = "";
    System.out.println("Executing this filter_by_date_range: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    ResultSet rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      // Print the data
    }
  }

  public void filter_by_amenities(boolean has_ac, boolean has_dryer, boolean has_kitchen, boolean has_washer, boolean has_wifi) throws SQLException {
    sqlQ = "";
    System.out.println("Executing this filter_by_amenities: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    ResultSet rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      // Print the data
    }
  }



}
