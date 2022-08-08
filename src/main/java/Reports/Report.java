package Reports;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class Report {

  public static int Listing_ID = 0;
  private static int Booking_ID;
  private static int Host_ID, Renter_ID;
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
  public static int price;
  public static String startDate;
  public static String endDate;
  private static int wifi, washer, ac, kitchen, dryer;
  private static String[] room_types = {"Apartment", "House", "Room"};
  static Statement sql;

  /*
  Total number of bookings in a specific date range by city.
Total number of bookings in a specific date range by postal code within a city.
Total number of listings per country, per country and city, per country, city and postal code.

Rank the hosts by the total number of listings they have overall per country.
Rank the hosts by the total number of listings they have overall per city.
For every city and country a report should provide the hosts that have a number of listings that is more than 10% of the number of listings in that city and country.
Rank the renters by the number of bookings in a specific time period.
Rank the renters by the number of bookings in a specific time period per city.(renters that have made at least 2 booking in the year).
Hosts and renters with the largest number of cancellations within a year.
A report that presents for each listing the set of most popular noun phrases associated with the listing (noun phrases from review).
   */

  public static void dateRangeAndCity() throws SQLException, InterruptedException {
    System.out.print("Specify the date range\n");
    System.out.print("Start Date (YYYY-MM-DD): ");
    startDate = scan.nextLine();
    System.out.print("End Date (YYYY-MM-DD): ");
    endDate = scan.nextLine();

    System.out.print("Specify city ");
    city = scan.nextLine();

    String sqlQ = "select count(*) from bookings as b join listings as l on l.listing_id = b.listing_id where start = '"+ startDate+ "' AND end = '" + endDate+ "' AND city = '" + city+"'\n";

    rs = sql.executeQuery(sqlQ);

    while(rs.next()){
      System.out.print("Total number of bookings between "+startDate+ " and " + endDate+" in "+city+ "is: " + rs.getInt("count(*)"));
    }

  }

  public static void dateRangeAndCityAndPostalCode() throws SQLException, InterruptedException {
    System.out.print("Specify the date range\n");
    System.out.print("Start Date (YYYY-MM-DD): ");
    startDate = scan.nextLine();
    System.out.print("End Date (YYYY-MM-DD): ");
    endDate = scan.nextLine();

    System.out.print("Specify city ");
    city = scan.nextLine();

    System.out.print("Specify postal code ");
    zipcode = scan.nextLine();

    String sqlQ = "select count(*) from bookings as b join listings as l on l.listing_id = b.listing_id where start = '"+ startDate+ "' AND end = '" + endDate+ "' AND city = '" + city+"' AND postal_code = '"+ zipcode+"'\n";

    rs = sql.executeQuery(sqlQ);

    while(rs.next()){
      System.out.print("Total number of bookings between "+startDate+ " and " + endDate+" in "+city+ " with postal code "+ zipcode+ " is: " + rs.getInt("count(*)"));
    }

  }

  public static void listingsPerCountryCityPostalCode() throws SQLException, InterruptedException {
    System.out.print("Enter 1 to get listings per Country, 2 for listings per country and city and 3 for listings per country and city and postal code: ");
    int choice = scan.nextInt();
    if (choice == 1){
      System.out.print("Specify country: ");
      country = scan.nextLine();
      String sqlQ = "select count(*) from listings where country = '"+ country+ "'\n";
      rs = sql.executeQuery(sqlQ);
      while(rs.next()){
        System.out.print("Total number of listings for country "+ country + " is: " + rs.getInt("count(*)"));
      }
    }else if( choice == 2){
      System.out.print("Specify country: ");
      country = scan.nextLine();
      System.out.print("Specify city: ");
      city = scan.nextLine();
      String sqlQ = "select count(*) from listings where country = '"+ country+ "' AND city = '" +city +"'\n";
      rs = sql.executeQuery(sqlQ);
      while(rs.next()){
        System.out.print("Total number of listings for country "+ country + " and city " + city+ " is: " + rs.getInt("count(*)"));
      }
    }else{
      System.out.print("Specify country: ");
      country = scan.nextLine();
      System.out.print("Specify city: ");
      city = scan.nextLine();
      System.out.print("Specify postal code: ");
      zipcode = scan.nextLine();
      String sqlQ = "select count(*) from listings where country = '"+ country+ "' AND city = '" +city +"' AND postal_code = '" + zipcode+"'\n";
      rs = sql.executeQuery(sqlQ);
      while(rs.next()){
        System.out.print("Total number of listings for country "+ country + " and city " + city+ " with postal code " + zipcode +" is: " + rs.getInt("count(*)"));
      }
    }

  }


  public static void rankHosts() throws SQLException {
    System.out.print("Enter 1 to rank hosts by Country and 2 to rank hosts by city: ");
    int choice = scan.nextInt();
    if (choice == 1) {
      System.out.print("Specify country: ");
      country = scan.nextLine();
      String sqlQ = "SELECT firstname, lastname, COUNT(o.SIN) AS value_occurrence FROM owns as o join listings as l on l.listing_id = o.listing_id JOIN user as u on u.SIN = o.SIN where country = '"+country+"' GROUP BY o.SIN ORDER BY value_occurrence DESC;\n";
      rs = sql.executeQuery(sqlQ);
      int i = 1;
      while(rs.next()){
        System.out.print(i+". "+ rs.getString("firstname") + " "+ rs.getString("lastname"));
        i++;
      }
    }else {
      System.out.print("Specify city: ");
      city = scan.nextLine();
      String sqlQ = "SELECT firstname, lastname, COUNT(o.SIN) AS value_occurrence FROM owns as o join listings as l on l.listing_id = o.listing_id JOIN user as u on u.SIN = o.SIN where city = '"+city+"' GROUP BY o.SIN ORDER BY value_occurrence DESC;\n";
      rs = sql.executeQuery(sqlQ);
      int i = 1;
      while(rs.next()) {
        System.out.print(i + ". " + rs.getString("firstname") + " " + rs.getString("lastname"));
        i++;
      }
    }

  }





  }
