package Filter;
import User.Host;
import User.LoginPage;
import User.Renter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.lang.*;
import static Listing.Listing.getDatesBetween;
import Listing.*;
import static Listing.Listing.get_Listing_ID;

public class Filter {
  private static int Booking_ID = 0;
  private static int Listing_ID = 0;
  private static int renter_ID = 0;
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
  private static String sqlQ = "";

  // USING HASHMAPS IN SORTING THE HASHMAPS ACCORDING TO THE DISTANCE OR PRICES
  static HashMap<Integer, Double> location_hashmap = new HashMap<Integer, Double>();
  static HashMap<Integer, Integer> postal_code_hashmap = new HashMap<Integer, Integer>();
  static HashMap<Integer, Integer> prices_hashmap = new HashMap<Integer, Integer>();
  private static Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
  private static Scanner scan = new Scanner(System.in);
  private static Statement st;
  private static ResultSet rs;
  static PreparedStatement ps;
  static Statement sql;
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  // List that will contain filtered listing_ids
  static Set<Integer> LISTING_SET = new HashSet<Integer>();
  static Set<Integer> postal_code_set = new HashSet<Integer>();
  static Set<Integer> address_set = new HashSet<Integer>();
  static Set<Integer> location_set = new HashSet<Integer>();
  static Set<Integer> prices_set = new HashSet<Integer>();
  static Set<Integer> amenities_set = new HashSet<Integer>();
  static Set<Integer> dates_set = new HashSet<Integer>();

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
  public static void get_address() {
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

  public static void get_dates() {
    System.out.println("Please type in the dates:");
    System.out.print("Start Date: ");
    start_date = scan.next();
    System.out.print("End Date: ");
    end_date = scan.next();
    updated_dates = 1;
  }

  public static void get_budget() throws SQLException {
    if (updated_dates == 0) {
      System.out.println("Please specify the dates you would want to look for.");
      System.out.print("Start date: ");
      start_date = scan.next();
      System.out.print("End date: ");
      end_date = scan.next();
      set_dates();

      while (dates_set.isEmpty()) {
        System.out.print("Sorry, the dates entered aren't available. Please enter 1 to change dates or 2 to return to the menu: ");
        int choice = scan.nextInt();
        if (choice == 1) {
          System.out.print("Start date: ");
          start_date = scan.next();
          System.out.print("End date: ");
          end_date = scan.next();
          set_dates();
        } else {
          return;
        }
      }


//      if(dates_set.isEmpty()) {
//        System.out.print("Sorry, the dates entered aren't available. Please enter 1 to change dates or 2 to return to the menu: ");
//        int choice = scan.nextInt();
//        if (choice == 1){
//          get_budget();
//        }else{
//          return;
//      }
//      Listing.startDate = start_date;
//      Listing.endDate = end_date;
//      int avail = Listing.checkAvailability();
//      if (avail != 1){
//        System.out.print("Sorry, the dates entered aren't available. Please enter 1 to change dates or 2 to return to the menu: ");
//        int choice = scan.nextInt();
//        if (choice == 1){
//          get_budget();
//        }else{
//          return;
//        }
      updated_dates = 1;
    }
    System.out.println("Please type in the budget:");
    System.out.print("Start Price: ");
    low_price = scan.nextInt();
    System.out.print("End Price: ");
    high_price = scan.nextInt();
    updated_prices = 1;
  }

  public static void get_location() {
    System.out.println("Please type in the location:");
    System.out.print("Longitude: ");
    longitude = scan.nextInt();
    System.out.print("Latitude: ");
    latitude = scan.nextInt();
    System.out.print("The default distance is 10 km. Enter 1 to change the default or 2 to continue: ");
    int option = scan.nextInt();
    if (option == 1) {
      System.out.print("Please enter the distance (in km): ");
      distance = scan.nextInt();
    }
    updated_location = 1;
  }

  public static void get_postal_code() {
    System.out.print("Please type in the Postal Code:");
    postal_code = scan.next();
    System.out.print("The default distance is 10 km. Enter 1 to change the default or 2 to continue: ");
    int option = scan.nextInt();
    if (option == 1) {
      System.out.print("Please enter the distance (in km): ");
      distance = scan.nextInt();
    }
    updated_postal_code = 1;
  }

  public static void get_amenities() {
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
  public static void filter_listings() throws SQLException, InterruptedException {
    // Display first 20 listings
    int empty = updated_address + updated_dates + updated_prices + updated_location + updated_amenities + updated_postal_code;
    if (empty == 0) {
      System.out.println("No filter applied! here are the first 20 results from listing: ");
      // Display the first 20 listings and return to the filters page

      st = connection.createStatement();
      String sqlQ;

      sqlQ = "SELECT * FROM Listings Limit 20 \n";
      System.out.println(sqlQ);
      ResultSet rs = st.executeQuery(sqlQ);

      String room, postal, city, country, apt;
      int listing_ID;

      while (rs.next()) {
        listing_ID = rs.getInt("listing_ID");
        room = rs.getString("room_type");
        apt = rs.getString("apt_name");
        city = rs.getString("city");
        country = rs.getString("country");
        postal = rs.getString("postal_code");

        System.out.println("Listing ID = " + listing_ID);
        System.out.println("Room type = " + room);
        System.out.println("Apartment name/Road = " + apt);
        System.out.println("City = " + city);
        System.out.println("Country = " + country);
        System.out.println("Postal code = " + postal);

        displayAmenities();
        System.out.println("-----------------------------------------------\n");
      }


      return;
    }

//    st = connection.createStatement();
//    rs = st.executeQuery(QUERY);

    // Fill the LISTING_ARRAY
    set_listing_array();

    if (updated_postal_code == 1) {
      //System.out.println("list before: "+ LISTING_SET);
      set_postal_code();
      //System.out.println("set of filtered: "+ postal_code_set);
      LISTING_SET.retainAll(postal_code_set);
      //System.out.println("list after: "+ LISTING_SET);
      //sort_by_postal_code();
      //System.out.println("list after sorting: "+ LISTING_SET);
    }
    if (updated_amenities == 1) {
      //System.out.println(LISTING_SET);
      set_amenities();
      //System.out.println("Amenitites Set: "+ amenities_set);
      LISTING_SET.retainAll(amenities_set);
      //System.out.println("After listing set: "+LISTING_SET);
    }
    if (updated_location == 1) {
      //System.out.println("list before: "+ LISTING_SET);
      set_location();
      //System.out.println("set of filtered: "+ location_set);
      LISTING_SET.retainAll(location_set);
      //System.out.println("list after: "+ LISTING_SET);
    }
    if (updated_prices == 1) {
      set_prices();
      LISTING_SET.retainAll(prices_set);
      //sort_by_prices();
    }
    if (updated_dates == 1) {
      set_dates();
      System.out.println(LISTING_SET);
      LISTING_SET.retainAll(dates_set);
      System.out.println(LISTING_SET);
    }
    if (updated_address == 1) {
      set_address();
      LISTING_SET.retainAll(address_set);
    }
    // Now LISTING_SET has the filtered listing ids.
    print_listings();
    displayListingsRenter();
    reset_global_variables();
  }

  public static void displayListingsRenter() throws SQLException, InterruptedException {
    System.out.print("Here are all the listings available after the filtering\n");

    st = connection.createStatement();
    int SIN = User.LoginPage.getSIN();
    String sqlQ;

    if (!LISTING_SET.isEmpty()) {
      for (int element : LISTING_SET) {
        Listing_ID = element;
        if (start_date == null && end_date == null) {

          sqlQ = "SELECT * \n" +
                  "FROM Listings \n" +
                  "WHERE listing_ID = " + Listing_ID + "\n";
          System.out.println(sqlQ);
          ResultSet rs = st.executeQuery(sqlQ);

          String room, postal, city, country, apt;
          int listing_ID;
          rs.next();

          listing_ID = rs.getInt("listing_ID");
          room = rs.getString("room_type");
          apt = rs.getString("apt_name");
          city = rs.getString("city");
          country = rs.getString("country");
          postal = rs.getString("postal_code");

          System.out.println("Listing ID = " + listing_ID);
          System.out.println("Room type = " + room);
          System.out.println("Apartment name/Road = " + apt);
          System.out.println("City = " + city);
          System.out.println("Country = " + country);
          System.out.println("Postal code = " + postal);

          displayAmenities();
          System.out.println("-----------------------------------------------\n");

        } else {
          sqlQ = "SELECT * \n" +
                  "FROM Listings l JOIN Calender c on l.listing_ID = c.listing_ID\n" +
                  "WHERE l.listing_ID = " + Listing_ID + "" +
                  " AND c.date >='" + start_date + "' AND c.date <= '" + end_date + "'\n";

          System.out.println(sqlQ);
          ResultSet rs = st.executeQuery(sqlQ);

          String room, postal, city, country, apt;
          int listing_ID, price;
          Date start, end, date;

          rs.next();
          price = rs.getInt("price");
          start = rs.getDate("date");
          end = start;
          date = start;

          listing_ID = rs.getInt("listing_ID");
          room = rs.getString("room_type");
          apt = rs.getString("apt_name");
          city = rs.getString("city");
          country = rs.getString("country");
          postal = rs.getString("postal_code");
          Listing_ID = listing_ID;

          System.out.println("Listing ID = " + listing_ID);
          System.out.println("Room type = " + room);
          System.out.println("Apartment name/Road = " + apt);
          System.out.println("City = " + city);
          System.out.println("Country = " + country);
          System.out.println("Postal code = " + postal);

          displayAmenities();

          int new_price;
          while (rs.next()) {
            end = date;
            new_price = rs.getInt("price");
            date = rs.getDate("date");
            if (new_price != price) {
              System.out.println("Available from " + start + " to " + end + " for price " + price);
              start.setDate(end.getDate() + 1);
              price = new_price;
            }
          }
          start = end;
          end = date;
          //start.setDate(end.getDate() - 1);
          System.out.println("Available from " + start + " to " + end + " for price " + price);
          System.out.println("-----------------------------------------------\n");
        }
      }
    } else {
      System.out.println("No results Found \n");
    }

  }

  private static void print_listings() throws SQLException {

    if (updated_prices == 1 && updated_location == 1 && updated_postal_code == 1) {
      System.out.println("Enter 1 to see the filtering ordered by prices, 2 ordered by location and 3 ordered by postal code: ");
      int choice = scan.nextInt();
      if (choice == 1) {
        updated_prices = 1;
        updated_location = 0;
        updated_postal_code = 0;
      } else if (choice == 2) {
        updated_prices = 0;
        updated_location = 1;
        updated_postal_code = 0;
      } else if (choice == 3) {
        updated_prices = 0;
        updated_location = 0;
        updated_postal_code = 1;
      } else {
        System.out.print("Invalid option!");
      }
    } else if (updated_postal_code == 1 && updated_location == 1) {
      System.out.println("Enter 1 to see the filtering ordered by postal code, 2 ordered by location: ");
      int choice = scan.nextInt();
      if (choice == 1) {
        updated_location = 0;
        updated_postal_code = 1;
      } else if (choice == 2) {
        updated_location = 1;
        updated_postal_code = 0;
      } else {
        System.out.println("Invalid choice!");
      }
    } else if (updated_postal_code == 1 && updated_prices == 1) {
      System.out.println("Enter 1 to see the filtering ordered by prices, 2 ordered by postal code: ");
      int choice = scan.nextInt();
      if (choice == 1) {
        updated_prices = 1;
        updated_postal_code = 0;
      } else if (choice == 2) {
        updated_prices = 0;
        updated_postal_code = 1;
      } else {
        System.out.println("Invalid choice!");
      }
    } else if (updated_location == 1 && updated_prices == 1) {
      System.out.println("Enter 1 to see the filtering ordered by prices, 2 ordered by location: ");
      int choice = scan.nextInt();
      if (choice == 1) {
        updated_prices = 1;
        updated_location = 0;
      } else if (choice == 2) {
        updated_prices = 0;
        updated_location = 1;
      } else {
        System.out.println("Invalid choice!");
      }
    }

    // The above code makes sure either one of the 3 is true!
    if (updated_location == 1) {
      sort_by_location();
      LISTING_SET.clear();
      for (HashMap.Entry<Integer, Double> entry : location_hashmap.entrySet()) {
        LISTING_SET.add(entry.getKey());
        //System.out.println(entry.getKey());
        //System.out.println("This is the hashmap: "+ location_hashmap);
        //System.out.println("This is the new listing_set: "+ LISTING_SET);
      }
      //System.out.println(location_hashmap);
    } else if (updated_postal_code == 1) {
      sort_by_postal_code();
      LISTING_SET.clear();
      for (HashMap.Entry<Integer, Integer> entry : postal_code_hashmap.entrySet()) {
        LISTING_SET.add(entry.getKey());
      }
      //System.out.println(postal_code_hashmap);
    } else if (updated_prices == 1) {
      sort_by_prices();
      LISTING_SET.clear();
      for (HashMap.Entry<Integer, Integer> entry : prices_hashmap.entrySet()) {
        LISTING_SET.add(entry.getKey());
      }
      System.out.println(prices_hashmap);
    }


    System.out.println("THE FILTERED LISTINGS ARE: " + LISTING_SET);

    // iterate over the listings in LISTING_SET and print data
//    ArrayList <Integer> array = new ArrayList<>(); // to make sure that no duplicate listings are displayed
//
//    System.out.println("Here are the filtered listings: ");
//    int size = LISTING_SET.size();
//    //System.out.println(LISTING_SET);
//    for (int listing_id : LISTING_SET){
//      sqlQ = "SELECT * \n" +
//              "FROM Calender\n" +
//              "INNER JOIN Listings ON Listings.listing_ID = Calender.listing_ID " +
//              "WHERE Calender.listing_ID=" + listing_id + "\n";
//
//      rs = sql.executeQuery(sqlQ);
//      while (rs.next()){
//        if(!array.contains(listing_id)){
//          array.add(listing_id);
//          System.out.println("Listing ID = " + rs.getString("listing_ID"));
//          System.out.println("Room type = " + rs.getString("room_type"));
//          System.out.println("Apartment name/Road = " + rs.getString("apt_name"));
//          System.out.println("City = " + rs.getString("city"));
//          System.out.println("Country = " + rs.getString("country"));
//          System.out.println("Postal code = " + rs.getString("postal_code"));
//
//          String start, end, date;
//          int price;
//
//          start = start_date;
//          date = start;
//          price = rs.getInt("price");
//          DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//
//
//          while (date != end_date) {
//            int new_price = rs.getInt("price");
//            if (price != new_price) {
//              end = date;
//              System.out.println("Available from = " + start + " to " + end + " for price " + price);
//              start = date;
//              LocalDate starting = LocalDate.parse(start, formatter);
//              starting = starting.plusDays(1);
//              start = dateFormat.format(date);
//            }
//          }
//          end = date;
//          if (end == null && start == null){
//            System.out.println("Available from = 'Not Specified' to = 'Not specified' for price " + price);
//          }else {
//            System.out.println("Available from = " + start + " to " + end + " for price " + price);
//          }
//
//
//          System.out.println("------------------------------------------------------------------------------");
//
//        }
//
//      }
//
//    }
  }


  //------------------------------------------BOOKING A LISTING--------------------------------------------
  public static void book_listing() throws SQLException, ParseException, InterruptedException {
    System.out.print("Please enter the listing ID for the listing you want to book: ");
    int id = scan.nextInt();
    if (updated_dates == 0) {
      System.out.println("PLease specify the days you want to book for: ");
      System.out.print("Start date: ");
      start_date = scan.next();
      System.out.print("End date: ");
      end_date = scan.next();
      int availability = checkAvailability(id);
      if (availability != 1) { // what number is when no availability
        //reset_global_variables();
        System.out.println("The listing for the chosen dates aren't available :(");
        System.out.println("To enter another set of dates enter 1. To return to filters page, enter 2");
        int choice = scan.nextInt();
        if (choice == 1) {
          book_listing();
        } else {
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

    int renter_id = User.LoginPage.getSIN();
    String q = "INSERT INTO Bookings(listing_ID, renter_ID, start, end, completed) VALUES (" +
            id + ", " + renter_id + ", '" + start_date + "', '" + end_date + "', 0)\n";
    System.out.println(q);
    sql.executeUpdate(q);


    Date date;
    int price;

    q = "INSERT INTO Cancellations(booking_id, listing_ID, renter_ID, date, price) select b.booking_id, " +
            "b.listing_id, b.renter_id, c.date, c.price from bookings b join calender c on b.listing_id = c.listing_id " +
            "where c.date >= b.start and c.date <= b.end and b.listing_id =" + id + "\n";
    System.out.println(q);
    sql.executeUpdate(q);

    q = "Select booking_ID from Bookings where listing_id =" + id + " and" +
            "start = '" + start_date + "' and end = '" + end_date + "'\n";
    System.out.println(q);
    rs = sql.executeQuery(sqlQ);
    while (rs.next()) {
      Booking_ID = rs.getInt("booking_id");
    }

    q = "INSERT INTO Rents(listing_ID, SIN) VALUES (" +
            //  Booking_ID + ", " + id + ", " + renter_id + ")\n";
            id + ", " + renter_id + ")\n";
    System.out.println(q);
    sql.executeUpdate(q);

    String sqlQ;
    sqlQ = "DELETE FROM Calender WHERE date >= " + "'" + start_date + "'" + " AND date <= "
            + "'" + end_date + "'" + " AND listing_ID = " + Listing_ID + "\n";

    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

  }

  //------------------------------------------RESETTING GLOBAL VARIABLES--------------------------------------------
  private static void reset_global_variables() {
    updated_dates = 0; // prompt the user when booking
    updated_address = 0;
    updated_postal_code = 0;
    updated_prices = 0;
    updated_location = 0;
    updated_amenities = 0;
    LISTING_SET.clear();
    dates_set.clear();
    location_set.clear();
    address_set.clear();
    amenities_set.clear();
    postal_code_set.clear();
    prices_set.clear();
    postal_code_hashmap.clear();
    location_hashmap.clear();
    prices_hashmap.clear();
  }

  //----------------------------------------------SETTING SETS-----------------------------------------------------

  public static void set_listing_array() throws SQLException {
    sqlQ = "SELECT * from listings\n";
    //System.out.println("Setting listing_array: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);
    while (rs.next()) {
      LISTING_SET.add(rs.getInt("Listing_ID"));
    }
  }

  public static void set_prices() throws SQLException {
    set_dates();
//    if (dates_set.isEmpty()) {
//      System.out.print("Sorry, there are no listings available for the dates specified! Click 1 to enter new set of dates and 2 to go back to the menu: ");
//      return;
//    }
//      int choice = scan.nextInt();
//      if (choice == 1) {
//        updated_dates = 0;
//        get_budget();
//      }else{
//        return;
//      }
//    }
    int price;
    for (int listing_id : LISTING_SET) {
      sqlQ = "SELECT price from Calender where listing_ID = " + "'" + listing_id + "'" + "\n";
      rs = sql.executeQuery(sqlQ);
      int add = 1;
      while (rs.next()) {
        price = rs.getInt("price");
        if (price > high_price || price < low_price) {
          add = 0;
        }
      }
      if (add == 1) {
        prices_set.add(listing_id);
      }
    }
  }

  public static void set_location() throws SQLException {
    // define a default distance
    // order by distance
    sqlQ = "SELECT * from listings\n";
    //System.out.println("Executing this filter_by_long_lat: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

    while (rs.next()) {
      double lon = rs.getDouble("longitude");
      double lat = rs.getDouble("latitude");
      double get_distance = get_distance_lon_lat(longitude, latitude, lon, lat);
      //System.out.println("get_distance = "+ get_distance+ " and distance = " +distance);
      if (get_distance <= distance) {
        location_set.add(rs.getInt("Listing_ID"));
      }
    }
  }

  public static void set_postal_code() throws SQLException {
    // same or adjacent postal codes
    sqlQ = "SELECT * from listings\n";
    //System.out.println("Executing this filter_by_postal_code: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

    while (rs.next()) {
      String code = rs.getString("postal_code");
      double get_distance = get_distance_postal_code(code, postal_code);
      //System.out.println("get_distance = "+ get_distance+ " and distance = " +distance);
      if (get_distance <= distance) {
        postal_code_set.add(rs.getInt("Listing_ID"));
      }
    }
  }

  public static void set_address() throws SQLException {
    sqlQ = "SELECT * from listings\n" +
            "\tWHERE apt_name LIKE " + "'" + apt_name + "'" + " AND city LIKE " + "'" + city + "'" + " AND country LIKE " + "'" + country + "'" + " AND postal_code LIKE " + "'" + postal_code + "'" + "\n";
    //System.out.println("Executing this filter_by_address: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);

    while (rs.next()) {
      address_set.add(rs.getInt("Listing_ID"));
    }
  }

  public static void set_dates() throws SQLException {
    sqlQ = "SELECT * from Listings\n";
    //System.out.println("Executing this set_dates: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
    rs = sql.executeQuery(sqlQ);
    int avail;
    while (rs.next()) {
      int listing = rs.getInt("listing_ID");
      avail = checkAvailability(listing);
      //System.out.println("AVAILABILITY FOR LISTING ID = "+listing+ ": "+ avail);
      if (avail == 1) {
        dates_set.add(listing);
        //System.out.println(listing);
        //System.out.println(dates_set);
      }
    }
  }

  public static void set_amenities() throws SQLException {
    ArrayList<Integer> desired_amen = new ArrayList<Integer>(5);
    if (has_ac == 1) {
      desired_amen.add(5);
    }
    if (has_dryer == 1) {
      desired_amen.add(4);
    }
    if (has_kitchen == 1) {
      desired_amen.add(2);
    }
    if (has_washer == 1) {
      desired_amen.add(3);
    }
    if (has_wifi == 1) {
      desired_amen.add(1);
    }
    int amen_id;
    ArrayList<Integer> has_amen = new ArrayList<Integer>(5);
    for (int listing_id : LISTING_SET) {
      has_amen.clear();
      int satisfies = 1;
      sqlQ = "Select amenity_ID from amenities where listing_ID = " + "'" + listing_id + "'" + "\n";
      rs = sql.executeQuery(sqlQ);
      while (rs.next()) {
        amen_id = rs.getInt("amenity_ID");
        has_amen.add(amen_id);
        if (listing_id == 3) {
          System.out.println("Adding amen!!!! : " + amen_id);
        }
      }
      for (int amen : desired_amen) {
        if (listing_id == 3) {
          System.out.println("Value of amen: " + amen);
          System.out.println("Value of has_amen: " + has_amen);
          System.out.println("Value of bool: " + !has_amen.contains(amen));

        }
        if (!has_amen.contains(amen)) {
          satisfies = 0;
        }
      }
      if (satisfies == 1) {
        amenities_set.add(listing_id);
      }
      if (listing_id == 3) {
        System.out.println("Value of satisfies: " + satisfies);
      }
    }

  }


  //------------------------------------------HELPER FUNCTIONS--------------------------------------------

  // Getting distance between 2 coordinates
  public static double get_distance_lon_lat(double lon1, double lat1, double lon2, double lat2) {

    double dlon = Math.toRadians(lon2) - Math.toRadians(lon1);
    double dlat = Math.toRadians(lat2) - Math.toRadians(lat1);
    double a = Math.pow(Math.sin(dlat / 2), 2)
            + Math.cos(lat1) * Math.cos(lat2)
            * Math.pow(Math.sin(dlon / 2), 2);
    double c = 2 * Math.asin(Math.sqrt(a));
    double r = 6371;
    return c * r;

  }

  // Getting distance between 2 postal codes
  public static int get_distance_postal_code(String postal_code1, String postal_code2) {
    int num1 = postal_code1.hashCode();
    int num2 = postal_code2.hashCode();
    //System.out.print(num1);
    //System.out.println(", "+ num2);
    return Math.abs(num2 - num1);
  }


  // Checking availability of a date
  public static Integer checkAvailability(int listing) throws SQLException {
    LocalDate start = LocalDate.parse(start_date, formatter);
    LocalDate ending = LocalDate.parse(end_date, formatter);
    st = connection.createStatement();

    String getAllDateQuery = "SELECT COUNT(*) FROM Calender WHERE date >= " + "'" + start_date + "'" + " AND date <= "
            + "'" + end_date + "'" + " AND listing_ID = " + listing + "\n";
    //System.out.println(getAllDateQuery);
    ResultSet rs = st.executeQuery(getAllDateQuery);

    int count = 0;
    while (rs.next()) {
      count = rs.getInt("COUNT(*)");
    }
    long noOfDaysBetween = ChronoUnit.DAYS.between(start, ending);
    noOfDaysBetween = noOfDaysBetween + 1;

    if (count == noOfDaysBetween) {
      return 1; // on success
    }
    if (count == 0) {
      return 2;
    } else {
      return 0;
    }
  }

  public static void removeAvailability(int Listing_ID) throws SQLException {

    LocalDate start = LocalDate.parse(start_date, formatter);
    LocalDate ending = LocalDate.parse(end_date, formatter);
    LocalDate end = ending.plusDays(1);
    st = connection.createStatement();

    String sqlQ;
    sqlQ = "DELETE FROM Calender WHERE date >= " + "'" + start_date + "'" + " AND date <= "
            + "'" + end_date + "'" + " AND listing_ID = " + Listing_ID + "\n";

    //System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

  }

//  public static void sort_by_distance() throws SQLException {
//    if (updated_postal_code == 1 && updated_location == 1){
//      System.out.print("Enter 1 to sort by postal code. Enter 2 to sort by location. Where the distance is "+distance+". : ");
//      int choice = scan.nextInt();
//      if(choice == 1){
//        sort_by_postal_code();
//      }else{
//        sort_by_location();
//      }
//    }else if (updated_location == 1){
//      sort_by_location();
//    }else if (updated_postal_code == 1){
//      sort_by_postal_code();
//    }else{
//      System.out.println("No filter provided to sort through distance!");
//    }
//  }

  public static void sort_by_location() throws SQLException {
    // key is listing and value is distance
    for (int listing_id : LISTING_SET) {
      sqlQ = "select * from listings where listing_ID =" + "'" + listing_id + "'" + "\n";
      //System.out.println("Executing this sort_by_location: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
      rs = sql.executeQuery(sqlQ);
      int lon, lat;
      while (rs.next()) {
        lon = rs.getInt("longitude");
        lat = rs.getInt("latitude");
        double dis = get_distance_lon_lat(lon, lat, longitude, latitude);
        location_hashmap.put(listing_id, dis);
        //System.out.println("THIS IS THE DISTANCE FOR LOCATIONS: "+dis);
      }
    }
    location_hashmap = sortByValue_double(location_hashmap);
  }

  public static void sort_by_postal_code() throws SQLException {
    // key is listing and value is distance
    for (int listing_id : LISTING_SET) {
      sqlQ = "select * from listings where listing_ID =" + "'" + listing_id + "'" + "\n";
      //System.out.println("Executing this sort_by_postal_code: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
      rs = sql.executeQuery(sqlQ);
      String post;
      while (rs.next()) {
        post = rs.getString("postal_code");
        int dis = get_distance_postal_code(post, postal_code);
        postal_code_hashmap.put(listing_id, dis);
        //System.out.println("THIS IS THE DISTANCE FOR POSTAL CODE: "+dis);
      }
    }
    postal_code_hashmap = sortByValue_integer(postal_code_hashmap);

  }

  public static void sort_by_prices() throws SQLException {
    for (int listing_id : LISTING_SET) {
      sqlQ = "select * from calender where listing_ID =" + "'" + listing_id + "'" + "\n";
      //System.out.println("Executing this sort_by_postal_code: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
      rs = sql.executeQuery(sqlQ);
      int pri;
      List<Integer> price_arr = new ArrayList<Integer>();
      while (rs.next()) {
        pri = rs.getInt("price");
        price_arr.add(pri);
        //System.out.println("THIS IS THE Price FOR prices: "+pri);
      }
      if (!price_arr.isEmpty()) {
        pri = Collections.max(price_arr);
        prices_hashmap.put(listing_id, pri);
      }
    }
    prices_hashmap = sortByValue_integer(prices_hashmap);

  }


  public static HashMap<Integer, Double> sortByValue_double(HashMap<Integer, Double> hm) {
    List<Map.Entry<Integer, Double>> list = new LinkedList<Map.Entry<Integer, Double>>(hm.entrySet());

    // Sorting the list
    Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
      @Override
      public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
        return (o1.getValue().compareTo(o2.getValue()));
      }
    });

    // put data from sorted list to hashmap
    HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
    for (Map.Entry<Integer, Double> aa : list) {
      temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
  }

  public static HashMap<Integer, Integer> sortByValue_integer(HashMap<Integer, Integer> hm) {
    List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(hm.entrySet());

    // Sorting the list
    Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
      @Override
      public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
        return (o1.getValue().compareTo(o2.getValue()));
      }
    });

    // put data from sorted list to hashmap
    HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
    for (Map.Entry<Integer, Integer> aa : list) {
      temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
  }


  public static void viewBookings() throws SQLException {
    System.out.print("Here are all the bookings that you have made in the past as well as the future\n");

    st = connection.createStatement();
    int SIN = User.LoginPage.getSIN();
    String sqlQ;
    // for each listing_id you rented, what are the bookings associated with it?

    sqlQ = "SELECT *\n" +
            "FROM Bookings b INNER JOIN rents r ON b.booking_ID = r.booking_ID\n" +
            "WHERE r.SIN=" + SIN + "\n";
    System.out.println(sqlQ);
    ResultSet rs = st.executeQuery(sqlQ);

    Date start, end;
    String c = "No";
    int listing_ID, renter_ID, completed, booking_ID;

    while (rs.next()) {
      booking_ID = rs.getInt("booking_ID");
      listing_ID = rs.getInt("listing_ID");
      renter_ID = rs.getInt("renter_ID");
      start = rs.getDate("start");
      end = rs.getDate("end");
      completed = rs.getInt("completed");
      if (completed == 1) {
        c = "Yes";
      } else {
        c = "No";
      }
      System.out.println("Booking ID = " + booking_ID);
      System.out.println("Listing ID = " + listing_ID);
      System.out.println("Renter ID = " + renter_ID);
      System.out.println("Start date of Booking = " + start);
      System.out.println("End date of Booking = " + end);
      System.out.println("Booking completed = " + c);
      System.out.println("------------------------------------\n");

    }
  }


  public static void cancelBooking() throws SQLException, InterruptedException {
    viewBookings();
    System.out.print("Enter the Booking Id of the booking that you would like to cancel\n");
    Booking_ID = scan.nextInt();

    st = connection.createStatement();
    int SIN = LoginPage.getSIN();
    String sqlQ;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // for each listing_id you rent, what are the bookings associated with it that have already been completed?
    //delete b from bookings b join owns o on b.listing_ID=o.listing_ID where b.listing_ID=1;
    sqlQ = "UPDATE Cancellations SET cancelled = 1 WHERE booking_id=" + Booking_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    sqlQ = "INSERT INTO Calender(date, price, listing_ID) SELECT date, price, listing_id FROM Cancellations WHERE booking_ID = " +
            "" + Booking_ID + " AND cancelled=1\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    //ResultSet rs = st.executeQuery(sqlQ);

//    Date start = null, end = null, date = null;
//    int id = 0, price = 50;
//    while (rs.next()) {
//      date = rs.getDate("date");
//      id = rs.getInt("listing_ID");
//      price = rs.getInt("price");
//      sqlQ = "INSERT INTO Calender(date, price, listing_ID) values ('" + date + "', " +
//              id + ", " + price + ")\n";
//      System.out.println(sqlQ);
//      st.executeUpdate(sqlQ);
//    }

    sqlQ = "DELETE b FROM Bookings b JOIN rents r ON b.booking_ID = r.booking_ID WHERE b.booking_ID = " +
            "" + Booking_ID + " AND r.SIN=" + SIN + " AND b.completed=0\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    sqlQ = "DELETE r FROM rents r WHERE r.booking_ID=" +
            "" + Booking_ID + " AND r.SIN=" + SIN + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    System.out.println("This booking has been cancelled\n");

  }

  public static void displayAmenities() throws SQLException, InterruptedException {
    List<String> amenities = new ArrayList<>();
    st = connection.createStatement();
    String sqlQ;
    sqlQ = "SELECT * \n" +
            "FROM Listings l JOIN Amenities a ON l.listing_ID=a.listing_ID\n" +
            "WHERE l.listing_ID=" + Listing_ID + "\n";
    System.out.println(sqlQ);
    ResultSet rs = st.executeQuery(sqlQ);
    String amenity;
    while (rs.next()) {
      amenity = rs.getString("amenity_type");
      amenities.add(amenity);
    }

    System.out.println("Amenities = " + amenities);

  }


  public static void deleteRenter() throws SQLException, InterruptedException {
    st = connection.createStatement();
    renter_ID = LoginPage.getSIN();
    String sqlQ;

    sqlQ = "SELECT * FROM Bookings b JOIN rents r ON b.booking_ID=r.booking_ID WHERE" +
            " r.SIN=" + renter_ID + " AND b.completed=0\n";
    System.out.println(sqlQ);
    ResultSet rs = st.executeQuery(sqlQ);

    Date start = null, end = null;
    int id = 0;
//    while (rs.next()) {
//      start = rs.getDate("start");
//      end = rs.getDate("end");
//      id = rs.getInt("listing_ID");
//
//      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//      start_date = dateFormat.format(start);
//      end_date = dateFormat.format(end);
//      Listing.startDate = start_date;
//      Listing.endDate = end_date;
//      Listing.Listing_ID = id;
//      Listing.price = 50;
//      Listing.addAvailability();
//
//    }

    sqlQ = "DELETE r\n" +
            "FROM Review r WHERE r.renter_ID =" + renter_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    sqlQ = "DELETE b FROM Bookings b JOIN rents r ON b.booking_ID = r.booking_ID WHERE" +
            " r.SIN=" + renter_ID + " AND b.completed=0\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);
    System.out.println("This booking has been cancelled\n");

    sqlQ = "DELETE r\n" +
            "FROM rents r WHERE r.SIN =" + renter_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);


    sqlQ = "DELETE r\n" +
            "FROM renter r WHERE r.SIN =" + renter_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    sqlQ = "DELETE u\n" +
            "FROM User u WHERE u.SIN =" + renter_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);
  }

}
