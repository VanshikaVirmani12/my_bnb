package Listing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;


import Main.Main;
import User.Host;

public class Listing {
  private static int Listing_ID = 0;
  private static Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private static Scanner scan = new Scanner(System.in);
  private static Statement st;
  private static ResultSet rs;
  private static String zipcode = null;
  private static String room_type = null;
  private static int type;
  private static String apt_name = null, city = null, country = null;
  private static int longitude;
  private static int latitude;
  private static int price;
  private static String startDate;
  private static String endDate;
  private static int wifi, washer, ac, kitchen, dryer;
  private static HashMap<Integer, String> mapListingInfo = new HashMap<>();
  private static String[] room_types = {"Apartment", "House", "Room"};

  public static void newListingScreenBanner() {
    System.out.println("\t\t\t\t\t\t  New Listing Page\n");
  }

  public static void createNewListing() {
    newListingScreenBanner();
    System.out.print("Specify the type of Listing. Enter 1 for Apartment, 2 for House, 3 for Rooms\n");
    type = scan.nextInt();
    scan.nextLine();
    room_type = room_types[type - 1];
    mapListingInfo.put(1, room_type);

    System.out.print("Apartment/Road Name: ");
    apt_name = scan.nextLine();
    mapListingInfo.put(2, apt_name);

    System.out.print("City: ");
    city = scan.nextLine();
    mapListingInfo.put(3, city);
    System.out.print("Country: ");
    country = scan.nextLine();
    mapListingInfo.put(4, country);
    System.out.print("Postal Code: ");
    zipcode = scan.nextLine();
    mapListingInfo.put(5, zipcode);

    System.out.print("Latitude: ");
    latitude = scan.nextInt();
    scan.nextLine();
    mapListingInfo.put(6, String.valueOf(latitude));
    System.out.print("Longitude: ");
    longitude = scan.nextInt();
    scan.nextLine();
    mapListingInfo.put(7, String.valueOf(longitude));

    System.out.print("Enter the start and end dates of when the listing is available \n");

    System.out.print("Start Date (YYYY-MM-DD): ");
    startDate = scan.nextLine();
    mapListingInfo.put(8, startDate);
    System.out.print("End Date (YYYY-MM-DD): ");
    endDate = scan.nextLine();
    mapListingInfo.put(9, endDate);

    System.out.print("Price: ");
    price = scan.nextInt();
    scan.nextLine();
    mapListingInfo.put(10, String.valueOf(price));

    System.out.print("Enter 1 for the amenities that are included in this listing. Enter 0 otherwise\n");
    System.out.print("Wifi: ");
    wifi = scan.nextInt();
    scan.nextLine();
    System.out.print("Washer: ");
    washer = scan.nextInt();
    scan.nextLine();
    System.out.print("AC: ");
    ac = scan.nextInt();
    scan.nextLine();
    System.out.print("Kitchen: ");
    kitchen = scan.nextInt();
    scan.nextLine();
    System.out.print("Dryer: ");
    dryer = scan.nextInt();
    scan.nextLine();

    redirectListingOptions();

  }

  public static int get_Listing_ID() throws SQLException {
    st = connection.createStatement();

    String getAllUserQuery = "SELECT * FROM Listings";
    rs = st.executeQuery(getAllUserQuery);

    while (rs.next()) {
      if (longitude == rs.getInt("longitude")
              && latitude == rs.getInt("latitude")) {
        Listing_ID = rs.getInt("listing_ID");
        break;
      }
    }

    return Listing_ID;

  }

  public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {

    return startDate.datesUntil(endDate)
            .collect(Collectors.toList());
  }


  public static void addAmenities() throws SQLException {
    st = connection.createStatement();

    String sqlQ;
    Listing_ID = get_Listing_ID();
    if (wifi == 1) {
      sqlQ = "INSERT into Amenities(amenity_ID, amenity_type, listing_ID)" +
              " values ('1', 'Wifi', '" + Listing_ID + "')\n";
      System.out.println(sqlQ);
      st.executeUpdate(sqlQ);
    }

    if (washer == 1) {
      sqlQ = "INSERT into Amenities(amenity_ID, amenity_type, listing_ID)" +
              " values ('2', 'Washer', '" + Listing_ID + "')\n";
      System.out.println(sqlQ);
      st.executeUpdate(sqlQ);
    }

    if (ac == 1) {
      sqlQ = "INSERT into Amenities(amenity_ID, amenity_type, listing_ID)" +
              " values ('3', 'AC', '" + Listing_ID + "')\n";
      System.out.println(sqlQ);
      st.executeUpdate(sqlQ);
    }
    if (kitchen == 1) {
      sqlQ = "INSERT into Amenities(amenity_ID, amenity_type, listing_ID)" +
              " values ('4', 'Kitchen', '" + Listing_ID + "')\n";
      System.out.println(sqlQ);
      st.executeUpdate(sqlQ);
    }
    if (dryer == 1) {
      sqlQ = "INSERT into Amenities(amenity_ID, amenity_type, listing_ID)" +
              " values ('5', 'Dryer', '" + Listing_ID + "')\n";
      System.out.println(sqlQ);
      st.executeUpdate(sqlQ);
    }


  }

  public static void redirectListingOptions() {

    try {
      Statement stmt1 = connection.createStatement();
      // inserting into user table

      String insertIntoListingTbl = "INSERT into Listings(room_type, latitude, longitude, postal_code, city, country, apt_name\n " +
              ")  values (" + "'" + room_type + "'" + "," + "'" + latitude + "'" + "," + "'" + longitude + "'" + "," + "'" + "\n" +
              zipcode + "'" + "," + "'" + city + "'" + "," + "'" + country + "'" + "," + "'" + apt_name + "'" + ")";
      System.out.println(insertIntoListingTbl);
      stmt1.executeUpdate(insertIntoListingTbl);

      Listing_ID = get_Listing_ID();

      int SIN = User.LoginPage.getSIN();
      String insertIntoOwnsTbl = "INSERT into owns(SIN, listing_ID)\n " +
              "values (" + "'" + SIN + "'" + "," + "'" + Listing_ID + "'" + ")";
      System.out.println(insertIntoOwnsTbl);
      stmt1.executeUpdate(insertIntoOwnsTbl);

      addAvailability();

      addAmenities();

//      LocalDate start = LocalDate.parse(startDate, formatter);
//      LocalDate ending = LocalDate.parse(endDate, formatter);
//      LocalDate end = ending.plusDays(1);
//      List<LocalDate> dates = getDatesBetween(start, end);
//
//      PreparedStatement ps;
//      String sqlQ;
//      int length = dates.size();
//      sqlQ = "INSERT INTO Calender VALUES (?,?,?)\n";
//      ps = connection.prepareStatement(sqlQ);
//      for (int i = 0; i < length; i++) {
//
//        Date sqlDate = Date.valueOf(dates.get(i));
//        ps.setDate(1, sqlDate);
//        ps.setInt(2, price);
//        ps.setInt(3, Listing_ID);
//        ps.executeUpdate();
//      }
//      ps.close();

      System.out.println("Gathering the information...");
      Thread.sleep(200);
      System.out.println("Listing created Successfully ...");
      Thread.sleep(200);
      Host.startPage();

    } catch (SQLException | InterruptedException e) {
      System.out.println("New Listing could not be created...");
      e.printStackTrace();
    }
  }

  public static void updateListing() throws SQLException, InterruptedException {
    System.out.print("Specify the Listing ID of the listing that you want to update\n");
    Listing_ID = scan.nextInt();

    int selectedOption = 0;
    System.out.print("Specify the operation\n");
    System.out.println("1. Update the price");
    System.out.println("2. Update the Available Dates");

    try {
      System.out.print("\nInput: ");
      selectedOption = scan.nextInt();
      scan.nextLine();
    } catch (InputMismatchException ime) {
      System.out.println("Input only numbers... Try again!!!");
      System.out.println("Redirecting to Update menu...");
      updateListing();
    }

    if (selectedOption == 1) {
      System.out.print("Changing price of Listing from start-date to end-date\n");
      System.out.print("Start Date (YYYY-MM-DD): ");
      startDate = scan.nextLine();
      System.out.print("End Date (YYYY-MM-DD): ");
      endDate = scan.nextLine();

      System.out.print("Updated Price: ");
      price = scan.nextInt();
      scan.nextLine();
      // checkAvailability();
      if (checkAvailability() == 1) {
        updatePrice();
      } else {
        System.out.print("Given Dates are not available. Please try again\n");
        Thread.sleep(500);
        updateListing();
      }
    } else if (selectedOption == 2) {
      System.out.print("Changing Availability of Listing from start-date to end-date\n");
      Thread.sleep(500);
      changeAvailability();
    }

  }

  public static void changeAvailability() throws SQLException, InterruptedException {

    int selectedOption = 0;
    System.out.print("Choose an option below\n");
    System.out.println("1. Add Availability for a Listing");
    System.out.println("2. Remove Availability for a Listings");

    try {
      System.out.print("\nInput: ");
      selectedOption = scan.nextInt();
      scan.nextLine();
    } catch (InputMismatchException ime) {
      System.out.println("Input only numbers... Try again!!!");
      System.out.println("Redirecting to Update menu...");
      updateListing();
    }

    if (selectedOption == 1) {
      System.out.println("I want my listing to be available from dates: \n");
      System.out.print("Start Date (YYYY-MM-DD): ");
      startDate = scan.nextLine();
      System.out.print("End Date (YYYY-MM-DD): ");
      endDate = scan.nextLine();
      System.out.print("Price: ");
      price = scan.nextInt();
      scan.nextLine();

      if (checkAvailability() == 1) {
        System.out.print("Given Dates are already available. Please try again\n");
        Thread.sleep(500);
        changeAvailability();
      } else if (checkAvailability() == 0) {
        System.out.print("Some of the given dates are already available. But some of them are not. " +
                "Please specify the range of datees are not already available.\n");
        Thread.sleep(500);
        changeAvailability();
      } else {
        addAvailability();
      }

    } else if (selectedOption == 2) {
      System.out.println("I don't want my listing to be available from dates: \n");
      System.out.print("Start Date (YYYY-MM-DD): ");
      startDate = scan.nextLine();
      System.out.print("End Date (YYYY-MM-DD): ");
      endDate = scan.nextLine();

      if (checkAvailability() == 1) {
        removeAvailability();
      } else {
        System.out.print("Your listing is not available for these dates. Please try again\n");
        changeAvailability();
      }
    }

  }


  public static void removeAvailability() throws SQLException {

    LocalDate start = LocalDate.parse(startDate, formatter);
    LocalDate ending = LocalDate.parse(endDate, formatter);
    LocalDate end = ending.plusDays(1);
    st = connection.createStatement();

    String sqlQ;
    sqlQ = "DELETE FROM Calender WHERE date >= " + "'" + startDate + "'" + " AND date <= "
            + "'" + endDate + "'" + " AND listing_ID = " + Listing_ID + "\n";

    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

  }

  public static void addAvailability() throws SQLException {

    LocalDate start = LocalDate.parse(startDate, formatter);
    LocalDate ending = LocalDate.parse(endDate, formatter);
    LocalDate end = ending.plusDays(1);
    List<LocalDate> dates = getDatesBetween(start, end);
    st = connection.createStatement();

    PreparedStatement ps;
    String sqlQ;
    int length = dates.size();
    sqlQ = "INSERT INTO Calender VALUES (?,?,?)\n";
    ps = connection.prepareStatement(sqlQ);
    for (int i = 0; i < length; i++) {

      Date sqlDate = Date.valueOf(dates.get(i));
      ps.setDate(1, sqlDate);
      ps.setInt(2, price);
      ps.setInt(3, Listing_ID);
      ps.executeUpdate();
    }
    ps.close();

  }

  public static Integer checkAvailability() throws SQLException {

    LocalDate start = LocalDate.parse(startDate, formatter);
    LocalDate ending = LocalDate.parse(endDate, formatter);
    LocalDate end = ending.plusDays(1);
    st = connection.createStatement();

    String getAllDateQuery = "SELECT COUNT(*) FROM Calender WHERE date >= " + "'" + startDate + "'" + " AND date <= "
            + "'" + endDate + "'" + " AND listing_ID = " + Listing_ID + "\n";
    System.out.println(getAllDateQuery);
    ResultSet rs = st.executeQuery(getAllDateQuery);

    int count = 0;
    while (rs.next()) {
      count = rs.getInt("COUNT(*)");
    }
    long noOfDaysBetween = ChronoUnit.DAYS.between(start, ending);
    noOfDaysBetween = noOfDaysBetween + 1;

    if (count == noOfDaysBetween) {
      return 1;
    }
    if (count == 0) {
      return 2;
    } else {
      return 0;
    }

  }

  public static void updatePrice() throws SQLException {
    st = connection.createStatement();

    LocalDate start = LocalDate.parse(startDate, formatter);
    LocalDate ending = LocalDate.parse(endDate, formatter);
    LocalDate end = ending.plusDays(1);
    List<LocalDate> dates = getDatesBetween(start, end);
    int length = dates.size();

    String updatePriceQuery = "UPDATE Calender SET price = " +
            price + " WHERE date >= " + "'" + startDate + "'" + " AND date <= "
            + "'" + endDate + "'" + " AND listing_ID = " + Listing_ID + "\n";
    System.out.println(updatePriceQuery);
    st.executeUpdate(updatePriceQuery);

  }

  public static void viewYourListings() throws SQLException {
    System.out.print("Here are all the listings that you own\n");

    st = connection.createStatement();
    int SIN = User.LoginPage.getSIN();

    String sqlQ;
    sqlQ = "SELECT * \n" +
            "FROM Listings\n" +
            "INNER JOIN owns ON Listings.listing_ID=owns.listing_ID\n" +
            "WHERE owns.SIN = " + SIN + "\n";
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
      System.out.println("-----------------------------------------------\n");


      sqlQ = "SELECT * \n" +
              "FROM Calender\n" +
              "INNER JOIN Listings ON Listings.listing_ID = Calender.listing_ID " +
              "WHERE Calender.listing_ID=" + listing_ID + "\n";

      System.out.println(sqlQ);
      ResultSet result = st.executeQuery(sqlQ);

      String start, end, date;
      int price;

      start = startDate;
      date = start;
      price = result.getInt("price");
      DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");


      while (date != endDate) {
        int new_price = result.getInt("price");
        if (price != new_price) {
          end = date;
          System.out.println("Available from = " + start + " to " + end + " for price " + price);
          start = date;
          LocalDate starting = LocalDate.parse(start, formatter);
          starting = starting.plusDays(1);
          start = dateFormat.format(date);
        }
      }
      end = date;
      System.out.println("Available from = " + start + " to " + end + " for price " + price);

    }

  }


    public static void removeListing() throws SQLException {
    System.out.print("Specify the Listing ID of the listing that you want to delete. CAUTION: This listing" +
            "and all information related to this listing will be permanently deleted\n");
    Listing_ID = scan.nextInt();

    st = connection.createStatement();

    String sqlQ;

    sqlQ = "DELETE FROM Calender WHERE listing_ID = " + Listing_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    sqlQ = "DELETE FROM Amenities WHERE listing_ID = " + Listing_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    sqlQ = "DELETE FROM owns WHERE listing_ID = " + Listing_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    sqlQ = "DELETE FROM Listings WHERE listing_ID = " + Listing_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    // **** CHECK WHETHER THERE IS A BOOKING AVAILABLE FOR THAT LISTING. DELETE ONLY IF THERE IS NO BOOKING
    sqlQ = "DELETE FROM rents WHERE listing_ID = " + Listing_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

    sqlQ = "DELETE FROM Review WHERE listing_ID = " + Listing_ID + "\n";
    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

  }

}
