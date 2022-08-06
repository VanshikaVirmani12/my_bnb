package Filter;
import User.Renter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.lang.*;
import static Listing.Listing.getDatesBetween;

public class Filter {
  private static int Booking_ID = 0;
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
  private static String sqlQ = "";
  private static Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
  private static Scanner scan = new Scanner(System.in);
  private static Statement st;
  private static ResultSet rs;
  static PreparedStatement ps;
  static Statement sql;
  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");

  // List that will contain filtered listing_ids
  static Set<Integer> LISTING_SET = new HashSet<Integer> ();
  static Set<Integer> postal_code_set = new HashSet<Integer> ();
  static Set<Integer> address_set = new HashSet<Integer> ();
  static Set<Integer> location_set = new HashSet<Integer> ();
  static Set<Integer> prices_set = new HashSet<Integer> ();
  static Set<Integer> amenities_set = new HashSet<Integer> ();
  static Set<Integer> dates_set = new HashSet<Integer> ();

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


  static {
    try {
      sql = connection.createStatement();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  //------------------------------------------GETTING DATA FROM USER--------------------------------------------
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

  //------------------------------------------FILTER LISTING--------------------------------------------
  public static void filter_listings() throws SQLException {
    // Display first 20 listings
    int empty = updated_address+updated_dates+updated_prices+updated_location+updated_amenities+updated_postal_code;
    if (empty == 0){
      System.out.println("No filter applied! here are the first 20 results from listing: ");
      // Display the first 20 listings and return to the filters page
      System.exit(0);
    }

//    st = connection.createStatement();
//    rs = st.executeQuery(QUERY);

    // Fill the LISTING_ARRAY
    set_listing_array();

    if(updated_postal_code == 1){
      set_postal_code();
      LISTING_SET.retainAll(postal_code_set);
    }
    if (updated_amenities == 1){
      set_amenities(has_ac,has_dryer, has_kitchen, has_washer, has_wifi);
      LISTING_SET.retainAll(amenities_set);
    }
    if(updated_location == 1){
      set_location();
      LISTING_SET.retainAll(location_set);
    }
    if(updated_prices == 1){
      set_prices();
      LISTING_SET.retainAll(prices_set);
    }
    if (updated_dates == 1){
      LocalDate StartDate = LocalDate.parse(start_date, formatter);
      LocalDate EndDate = LocalDate.parse(end_date, formatter);
      set_dates(StartDate,EndDate);
      LISTING_SET.retainAll(dates_set);
    }
    if(updated_address == 1) {
      set_address();
      LISTING_SET.retainAll(address_set);
    }
    // Now LISTING_SET has the filtered listing ids.

    print_listings();
  }

  private static void print_listings() {
    // iterate over the listings in LISTING_SET and print data
    System.out.println("Hello, you reached the end");
    int size = LISTING_SET.size();
    System.out.println(LISTING_SET);
  }


  //------------------------------------------BOOKING A LISTING--------------------------------------------
  public static void book_listing() throws SQLException, ParseException {
    System.out.print("Please enter the listing ID for the listing you want to book: ");
    int id = scan.nextInt();
    if (updated_dates == 0){
      System.out.println("PLease specify the days you want to book for: ");
      System.out.print("Start date: ");
      start_date = scan.next();
      System.out.print("End date: ");
      end_date = scan.next();
      int availability = checkAvailability(id);
      if (availability != 0){ // what number is when no availability
        reset_global_variables();
        System.out.println("The listing for the chosen dates aren't available :(");
        System.out.println("To enter another set of dates enter 1. To return to filters page, enter 2");
        int choice = scan.nextInt();
        if (choice == 1){
          book_listing();
        }else{
          reset_global_variables();
          Renter.selectFilter();
        }
      }
    }
    // Valid Listing
    // Insert the listing id with dates and renter Id into the table "booking"
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
    //convert String to LocalDate
    LocalDate StartDate = LocalDate.parse(start_date, formatter);
    LocalDate EndDate = LocalDate.parse(end_date, formatter);
    List<LocalDate> dates = getDatesBetween(StartDate, EndDate);

    int length = dates.size();

    String q = "INSERT INTO Bookings VALUES(?, ?, ?)\n";
    int renter_id = User.LoginPage.getSIN();
    ps = connection.prepareStatement(q);

    for (int i = 0; i < length; i++){

      Date d = Date.valueOf(dates.get(i));
      ps.setDate(1, d);
      ps.setInt(2, id);
      ps.setInt(3, renter_id);
      ps.executeQuery();
    }
    ps.close();
  }

  //------------------------------------------RESETTING GLOBAL VARIABLES--------------------------------------------
  private static void reset_global_variables() {
    updated_dates = 0; // prompt the user when booking
    updated_address = 0;
    updated_postal_code = 0;
    updated_prices = 0;
    updated_location = 0;
    updated_amenities = 0;
    sql_dates = "";
    sql_address = "";
    sql_postal_code = "";
    sql_prices = "";
    sql_location = "";
    sql_amenities = "";
    QUERY ="";
    LISTING_SET.clear();
  }

  //----------------------------------------------SETTING SETS-----------------------------------------------------

  public static void set_listing_array() throws SQLException {
    sqlQ = "SELECT * from Listings\n";
    System.out.println("Setting listing_array: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);
    while(rs.next()){
      LISTING_SET.add(rs.getInt("Listing_ID"));
    }
  }

  public static void set_prices() throws SQLException {
    sqlQ = "SELECT * from Calender\n" +
            "\tWHERE price >= "+ "'"+ low_price+ "'"+ " AND price <= "+"'"+ high_price+"'"+ "\n" +
            "\tORDER BY price\n";
    rs = sql.executeQuery(sqlQ);
    while(rs.next()){
      prices_set.add(rs.getInt("Listing_ID"));
    }
  }

  public static void set_location() throws SQLException{
    // define a default distance
    // order by distance
    sqlQ = "SELECT * from Listings\n";
    System.out.println("Executing this filter_by_long_lat: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      double lon = rs.getDouble("longitude");
      double lat = rs.getDouble("latitude");
      if (get_distance_lon_lat(longitude, latitude, lon, lat) <= distance){
        location_set.add(rs.getInt("Listing_ID"));
      }
    }
  }

  public static void set_postal_code() throws SQLException{
    // same or adjacent postal codes
    sqlQ = "SELECT * from has_address\n";
    System.out.println("Executing this filter_by_postal_code: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      String code = rs.getString("postal_code");
      if (get_distance_postal_code(code, postal_code) <= distance){
        postal_code_set.add(rs.getInt("Listing_ID"));
      }
    }
  }

  public static void set_address() throws SQLException {
    sqlQ = "SELECT * from has_address\n" +
            "\tWHERE apt_name LIKE "+ "'"+ apt_name + "'"+" AND city LIKE "+ "'"+city + "'"+ " AND country LIKE "+"'"+ country +"'"+ " AND postal_code LIKE "+"'"+postal_code+"'"+"\n";
    System.out.println("Executing this filter_by_address: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

    while (rs.next()){
      address_set.add(rs.getInt("Listing_ID"));
    }
  }

  public static void set_dates(LocalDate start_date, LocalDate end_date) throws SQLException {
    sqlQ = "SELECT * from Calender\n";

    System.out.println("Executing this filter_by_date_range: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);
  }

  public static void set_amenities(int has_ac, int has_dryer, int has_kitchen, int has_washer, int has_wifi) throws SQLException {
    sqlQ = "";
    System.out.println("Executing this filter_by_amenities: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

//    while (rs.next()){
//      // Print the data
//    }
  }

  //------------------------------------------HELPER FUNCTIONS--------------------------------------------

  // Getting distance between 2 coordinates
  public static double get_distance_lon_lat(double lon1, double lat1, double lon2, double lat2){

    double dlon = Math.toRadians(lon2) - Math.toRadians(lon1);
    double dlat = Math.toRadians(lat2) - Math.toRadians(lat1);
    double a = Math.pow(Math.sin(dlat / 2), 2)
            + Math.cos(lat1) * Math.cos(lat2)
            * Math.pow(Math.sin(dlon / 2),2);
    double c = 2 * Math.asin(Math.sqrt(a));
    double r = 6371;
    return c*r;

  }

  // Getting distance between 2 postal codes
  public static int get_distance_postal_code(String postal_code1, String postal_code2){
    int num1 = postal_code1.hashCode();
    int num2 = postal_code2.hashCode();
    return  Math.abs(num2 - num1);
  }


  // Checking availability of a date
  public static Integer checkAvailability(int listing_id) throws SQLException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
    LocalDate start = LocalDate.parse(start_date, formatter);
    LocalDate ending = LocalDate.parse(end_date, formatter);
    LocalDate end = ending.plusDays(1);
    st = connection.createStatement();

    String getAllDateQuery = "SELECT COUNT(*) FROM Calender WHERE date >= " + "'" + start_date + "'" + " AND date <= "
            + "'" + end_date +  "'" + " AND listing_ID = " + listing_id + "\n";
    System.out.println(getAllDateQuery);
    ResultSet rs = st.executeQuery(getAllDateQuery);

    int count = 0;
    while(rs.next()) {
      count = rs.getInt("COUNT(*)");
    }
    long noOfDaysBetween = ChronoUnit.DAYS.between(start, ending);
    noOfDaysBetween = noOfDaysBetween + 1;

    if (count == noOfDaysBetween ){
      return 1; // on success
    } if (count == 0 ) {
      return 2;
    } else {
      return 0;
    }
  }


}
