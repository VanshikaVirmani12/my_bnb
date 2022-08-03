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

import Main.Main;
import User.Host;

public class Listing {
  private static int Listing_ID = 0;
  private static Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();

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
  private static HashMap<Integer, String> mapListingInfo = new HashMap<>();
  private static String[] room_types = {"Apartment", "House", "Room"};

  public static void newListingScreenBanner() {
    System.out.println("\t\t\t\t\t\t  New Listing Page\n");
  }

  public static void createNewListing() {
    newListingScreenBanner();
    System.out.print("Specify the type of Listing. Enter 1 for Apartment, 2 for House, 3 for Rooms\n");
    type = scan.nextInt();
    room_type = room_types[type-1];
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

  public static void redirectListingOptions() {

    try {
      Statement stmt1 = connection.createStatement();
      // inserting into user table

      String insertIntoListingTbl = "INSERT into Listings(room_type, latitude, longitude, postal_code, city, country, apt_name\n " +
      ")  values (" + "'" + room_type + "'" + "," + "'" + latitude + "'" + "," + "'" + longitude + "'"+  "," + "'" +"\n" +
              zipcode + "'" + "," + "'" + city + "'" + "," + "'" + country + "'" + "," + "'" + apt_name +  "'" + ")";
      System.out.println(insertIntoListingTbl);
      stmt1.executeUpdate(insertIntoListingTbl);

      Listing_ID = get_Listing_ID();

      int SIN = User.LoginPage.getSIN();
      String insertIntoOwnsTbl = "INSERT into owns(SIN, listing_ID)\n " +
              "values (" + "'" + SIN + "'" + "," + "'" + Listing_ID + "'" + ")";
      System.out.println(insertIntoOwnsTbl);
      stmt1.executeUpdate(insertIntoOwnsTbl);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      LocalDate start = LocalDate.parse(startDate, formatter);
      LocalDate ending = LocalDate.parse(endDate, formatter);
      LocalDate end = ending.plusDays(1);
      List<LocalDate> dates = getDatesBetween(start, end);

      PreparedStatement ps;
      String sqlQ;
      int length = dates.size();
      sqlQ = "INSERT INTO Calender VALUES (?,?,?)\n";
      ps = connection.prepareStatement(sqlQ);
      for (int i=0; i<length; i++){

        Date sqlDate = Date.valueOf(dates.get(i));
        ps.setDate(1, sqlDate);
        ps.setInt(2, price);
        ps.setInt(3, Listing_ID);
        ps.executeUpdate();
      }
      ps.close();


//      // inserting address
//      String insertIntoAddress = "INSERT into Address(postal_code, city, country, apt_name\n) "
//              + "values(" + "'" + zipcode + "'" + "," + "'" + city + "'" + "," + "'" + country + "'" + "," + "'" + apt_name +  "'" + ")";
//      System.out.println(insertIntoAddress);
//      stmt1.executeUpdate(insertIntoAddress);

//      // inserting address
//      String insertIntoHasAddress = "INSERT into has_address(postal_code, city, country, apt_name\n) "
//              + "values(" + "'" + zipcode + "'" + "," + "'" + city + "'" + "," + "'" + country + "'" + "," + "'" + apt_name +  "'" + ")";
//      System.out.println(insertIntoHasAddress);
//      stmt1.executeUpdate(insertIntoHasAddress);

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

  public static void updateListing() {
    System.out.print("Specify the Listing ID of the listing that you want to update\n");
    Listing_ID = scan.nextInt();

    

  }


  }
