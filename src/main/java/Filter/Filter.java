package Filter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.sql.ResultSet;

import java.util.*;
import java.lang.*;
import User.Renter;

public class Filter {

  private static int Booking_ID = 0;
  private static Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();

  private static Scanner scan = new Scanner(System.in);

  private static Statement st;
  private static ResultSet rs;

  private static String apt_name = null, city = null, country = null, postal_code = null;
  private static int longitude;
  private static int latitude;
  private static int high_price;
  private static int low_price;
  private static String start_date;
  private static String end_date;
  private static int has_ac;
  private static int has_dryer;
  private static int has_kitchen;
  private static int has_washer;
  private static int has_wifi;
  private static int distance = 10;

  private static int updated_dates = 0; // prompt the user when booking
  private static int updated_address = 0;
  private static int updated_postal_code = 0;
  private static int updated_prices = 0;
  private static int updated_location = 0;
  private static int updated_amenities = 0;

  private static String sql_dates = "";
  private static String sql_address = "";
  private static String sql_postal_code = "";
  private static String sql_prices = "";
  private static String sql_location = "";
  private static String sql_amenities = "";
  private static String QUERY ="";




  public Filter() throws SQLException {
    this.city = city;
    this.country = country;
    this.apt_name = apt_name;
    this.postal_code = postal_code;
    this.start_date = start_date;
    this.end_date = end_date;
    //this.room_type = room_type;   room type? needed???
    this.low_price = low_price;
    this.high_price = high_price;
    this.has_wifi = has_wifi;
    this.has_kitchen = has_kitchen;
    this.has_washer = has_washer;
    this.has_dryer = has_dryer;
    this.has_ac = has_ac;
    this.longitude = longitude;
    this.latitude = latitude;

  }

  PreparedStatement ps;

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


  // Initialize user data

  public static void get_address(){
    System.out.println("Please type in the address:");
    System.out.print("Apt Name: ");
    apt_name = scan.next();
    System.out.print("City: ");
    city = scan.next();
    System.out.print("Country: ");
    country = scan.next();
    System.out.print("Postal Code: ");
    postal_code = scan.next();
    updated_address = 1;
  }


  public static void get_dates(){
    System.out.println("Please type in the dates:");
    System.out.print("Start Date: ");
    start_date = scan.next();
    System.out.print("End Date: ");
    end_date = scan.next();
    updated_dates = 1;
  }

  public static void get_budget(){
    System.out.println("Please type in the budget:");
    System.out.print("Start Price (YYYY-MM-DD): ");
    low_price = scan.nextInt();
    System.out.print("End Price (YYYY-MM-DD): ");
    high_price = scan.nextInt();
    updated_prices = 1;
  }

  public static void get_location(){
    System.out.println("Please type in the location:");
    System.out.print("Longitude: ");
    longitude = scan.nextInt();
    System.out.print("Latitude: ");
    latitude = scan.nextInt();
    System.out.print("The default distance is 10 km. Enter 1 to change the default or 2 to continue: ");
    int option = scan.nextInt();
    if (option == 1){
      System.out.print("Please enter the distance (in km): ");
      distance = scan.nextInt();
    }
    updated_location = 1;
  }

  public static void get_postal_code(){
    System.out.print("Please type in the Postal Code:");
    postal_code = scan.next();
    System.out.print("The default distance is 10 km. Enter 1 to change the default or 2 to continue: ");
    int option = scan.nextInt();
    if (option == 1){
      System.out.print("Please enter the distance (in km): ");
      distance = scan.nextInt();
    }
    updated_postal_code = 1;
  }

  public static void get_amenities(){
    System.out.println("Please type in the amenities required (0 for no, 1 for yes):");
    System.out.print("Has AC: ");
    has_ac = scan.nextInt();
    System.out.print("Has Dryer: ");
    has_dryer = scan.nextInt();
    System.out.print("Has Kitchen: ");
    has_kitchen = scan.nextInt();
    System.out.print("Has washer: ");
    has_washer = scan.nextInt();
    System.out.print("Has Wifi: ");
    has_wifi = scan.nextInt();
    updated_amenities = 1;
  }

  public static void filter_listings() throws SQLException {
    // get the sql query and just execute and print data
    // after executing make all variables null
    st = connection.createStatement();
    rs = st.executeQuery(QUERY);
    while( rs.next()){
      if(updated_postal_code == 1){
        System.out.print("Postal Code: "+rs.getString("postal_code") +", ");
      }
      if (updated_amenities == 1){
        System.out.print("Amenities: "+rs.getString("") +", "); // HOW TO PRINT AMENITIES
      }
      if(updated_location == 1){
        System.out.print("Longitude: "+rs.getString("longitude") +", ");
        System.out.print("Latitude: "+rs.getString("latitude") +", ");
      }
      if(updated_prices == 1){
        System.out.print("Price: "+rs.getString("price") +", ");
      }
      if (updated_dates == 1){
        System.out.print("Listing Id: "+rs.getString("") +", "); // HOW TO PRINT DATES
      }
      if(updated_address == 1){
        System.out.print("Apt Name: "+rs.getString("apt_name") +", ");
        System.out.print("City: "+rs.getString("city") +", ");
        System.out.print("Country: "+rs.getString("country") +", ");
      }
      System.out.print("Listing Id: "+rs.getString("listing_ID"));
    }
  }

  public static void get_sql_query(){
    // get sql strings and intersect them
    // get and array of the sql strings with non-empty ones
    // iterate over and put intersect between them
    String[] query_arr = {sql_prices, sql_location, sql_address, sql_dates, sql_amenities, sql_postal_code};
    int non_empty = updated_address+updated_dates+updated_prices+updated_location+updated_amenities+updated_postal_code;
    int i = 0;
    while (non_empty -1 >0){
      if (!query_arr[i].equals("")){
        query_arr[i].concat(" INTERSECT ");
        QUERY.concat(query_arr[i]);
        non_empty = non_empty -1;
      }
      i++;
    }
    while (i < 6){
      if (!query_arr[i].equals("")){
        QUERY.concat(query_arr[i]);
        i = 100;
      }
      i++;
    }

  }

  public static void getting_sql_strings(){
    if (updated_amenities != 0){
      sql_amenities = "SELECT * from Amenities";
    }
    if (updated_postal_code != 0){
      sql_postal_code = "SELECT * from Listings";
    }
    if (updated_location != 0){
      sql_location = "SELECT * from Listings";
    }
    if (updated_prices != 0){
      sql_prices = "SELECT * from Calendar";
    }
    if (updated_dates != 0){
      sql_dates = "SELECT * from Calendar";
    }
    if (updated_address != 0){
      sql_address = "SELECT * from Listing";
    }
  }

  public static void book_listing(){
    System.out.print("Please enter the listing ID for the listing you want to book:");
    int listing_id = scan.nextInt();
  }


  public String filter_by_price_range(int price_low, int price_high) throws SQLException {
    sqlQ = "SELECT * from Calender\n" +
            "\tWHERE price >= price_low AND price <= price_high\n" +
            "\tORDER BY price\n";
    return sqlQ;

  }

  public void filter_by_long_lat(int lon_i, int lat_i, int distance) throws SQLException{
    // define a default distance
    // order by distance
    sqlQ = "SELECT * from Listings\n";
    System.out.println("Executing this filter_by_long_lat: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

//    while (rs.next()){
//      double lon = rs.getDouble("longitude");
//      double lat = rs.getDouble("latitude");
//      if (get_distance_lon_lat(lon_i, lat_i, lon, lat) <= distance){
//        System.out.print("Listing Id: "+rs.getString("listing_ID") +", ");
//        System.out.print("Longitude: "+lon+", ");
//        System.out.println("Latitude: "+lat);
//      }
//    }
  }

  public void filter_by_postal_code(String postal_code_i, int distance) throws SQLException{
    // same or adjacent postal codes
    sqlQ = "SELECT * from has_address\n";
    System.out.println("Executing this filter_by_postal_code: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

//    while (rs.next()){
//      String postal_code = rs.getString("postal_code");
//      if (get_distance_postal_code(postal_code, postal_code_i) <= distance){
//        System.out.print("Listing Id: "+rs.getString("listing_ID") +", ");
//        System.out.println("Postal Code: "+postal_code);
//      }
//    }
  }

  public void filter_by_address(String apt_name_i, String city_i, String country_i, String postal_code_i) throws SQLException {
    sqlQ = "SELECT * from has_address\n" +
            "\tWHERE apt_name LIKE apt_name_i AND city LIKE city_i AND country LIKE country_i AND postal_code LIKE postal_code_i\n";
    System.out.println("Executing this filter_by_address: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

//    while (rs.next()){
//      System.out.print("Listing Id: "+rs.getString("listing_ID") +", ");
//      System.out.print("Apt Name: "+rs.getString("apt_name") +", ");
//      System.out.print("City: "+rs.getString("city") +", ");
//      System.out.print("Country: "+rs.getString("country") +", ");
//      System.out.print("Postal Code: "+rs.getString("postal_code"));
//    }
  }

  public void filter_by_date_range(Date start_date, Date end_date) throws SQLException {
    sqlQ = "";
    System.out.println("Executing this filter_by_date_range: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

//    while (rs.next()){
//      // Print the data
//    }
  }

  public void filter_by_amenities(boolean has_ac, boolean has_dryer, boolean has_kitchen, boolean has_washer, boolean has_wifi) throws SQLException {
    sqlQ = "";
    System.out.println("Executing this filter_by_amenities: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

//    while (rs.next()){
//      // Print the data
//    }
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


  // Combining filters


}
