import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.sql.ResultSet;

import java.util.*;
import java.lang.*;

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
      System.out.print("Listing Id: "+rs.getString("listing_ID") +", ");
      System.out.println("Price: "+rs.getString("price"));
    }

  }

  public void filter_by_long_lat(int lon_i, int lat_i, int distance) throws SQLException{
    // define a default distance
    // order by distance
    sqlQ = "SELECT * from Listings\n";
    System.out.println("Executing this filter_by_long_lat: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    ResultSet rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      double lon = rs.getDouble("longitude");
      double lat = rs.getDouble("latitude");
      if (get_distance_lon_lat(lon_i, lat_i, lon, lat) <= distance){
        System.out.print("Listing Id: "+rs.getString("listing_ID") +", ");
        System.out.print("Longitude: "+lon+", ");
        System.out.println("Latitude: "+lat);
      }
    }
  }

  public void filter_by_postal_code(String postal_code_i, int distance) throws SQLException{
    // same or adjacent postal codes
    sqlQ = "SELECT * from has_address\n";
    System.out.println("Executing this filter_by_postal_code: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    ResultSet rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      String postal_code = rs.getString("postal_code");
      if (get_distance_postal_code(postal_code, postal_code_i) <= distance){
        System.out.print("Listing Id: "+rs.getString("listing_ID") +", ");
        System.out.println("Postal Code: "+postal_code);
      }
    }
  }

  public void filter_by_address(String apt_name_i, String city_i, String country_i, String postal_code_i) throws SQLException {
    sqlQ = "SELECT * from has_address\n" +
            "\tWHERE apt_name LIKE apt_name_i AND city LIKE city_i AND country LIKE country_i AND postal_code LIKE postal_code_i\n";
    System.out.println("Executing this filter_by_address: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    ResultSet rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      System.out.print("Listing Id: "+rs.getString("listing_ID") +", ");
      System.out.print("Apt Name: "+rs.getString("apt_name") +", ");
      System.out.print("City: "+rs.getString("city") +", ");
      System.out.print("Country: "+rs.getString("country") +", ");
      System.out.print("Postal Code: "+rs.getString("postal_code"));
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


  // Helper functions

  public double get_distance_lon_lat(double lon1, double lat1, double lon2, double lat2){

    double dlon = Math.toRadians(lon2) - Math.toRadians(lon1);
    double dlat = Math.toRadians(lat2) - Math.toRadians(lat1);
    double a = Math.pow(Math.sin(dlat / 2), 2)
            + Math.cos(lat1) * Math.cos(lat2)
            * Math.pow(Math.sin(dlon / 2),2);
    double c = 2 * Math.asin(Math.sqrt(a));
    double r = 6371;
    return c*r;

  }

  public int get_distance_postal_code(String postal_code1, String postal_code2){
    int num1 = postal_code1.hashCode();
    int num2 = postal_code2.hashCode();
    return num2 - num1;
  }


}
