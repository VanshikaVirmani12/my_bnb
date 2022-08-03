package Listing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

import Main.Main;
import User.Host;

public class Listing {
  private static Statement st;
  private static ResultSet rs;

  private static Scanner scan = new Scanner(System.in);
  private static String zipcode = null;
  private static String room_type = null;
  private static int type;
  private static String apt_name = null, city = null, country = null;
  private static int longitude;
  private static int latitude;
  private static HashMap<Integer, String> mapListingInfo = new HashMap<>();
  private static String[] room_types = {"Apartment", "House", "Room"};

  public static void newListingScreenBanner() {
    System.out.println("\t\t\t\t\t\t  New Listing Page\n");
  }

  public static void createNewListing() {
    newListingScreenBanner();
    System.out.print("Specify the type of Listing. Enter 1 for Apartment, 2 for House, 3 for Rooms");
    type = scan.nextInt();
    room_type = room_types[type];
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

    redirectListingOptions();

  }

  public static void redirectListingOptions() {
    Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
    try {
      Statement stmt1 = connection.createStatement();
      // inserting into user table

      String insertIntoListingTbl = "INSERT into Listings(room_type, latitude, longitude, postal_code, city, country, apt_name\n " +
      ")  values (" + "'" + room_type + "'" + "," + "'" + latitude + "'" + "," + "'" + longitude + "'"+  "," + "'" +"\n" +
              zipcode + "'" + "," + "'" + city + "'" + "," + "'" + country + "'" + "," + "'" + apt_name +  "'" + ")";
      System.out.println(insertIntoListingTbl);
      stmt1.executeUpdate(insertIntoListingTbl);

      int SIN = User.LoginPage.getSIN();
      String insertIntoOwnsTbl = "INSERT into owns(SIN)\n " +
              "values (" + "'" + SIN +  "'" + ")";
      System.out.println(insertIntoOwnsTbl);
      stmt1.executeUpdate(insertIntoOwnsTbl);

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


  }
