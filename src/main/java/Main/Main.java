package Main;

import User.LoginPage;
import User.NewRegistration;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Main {

  private static Scanner scan = new Scanner(System.in);

  public static void welcomeScreenBanner() {
    System.out.println("********************************************************************************************************************************************************");
    System.out.println("\t\t\t\t\t\tWelcome to MyBnb");
    System.out.println("********************************************************************************************************************************************************");
    System.out.println("");
  }

  public static void mainScreenBanner() {
    System.out.println("\t\t\t\t\t\tHome Page\n");
  }

  public static void clearScreen() {
    try {
      if (System.getProperty("os.name").contains("Windows"))
        try {
          new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      else
        Runtime.getRuntime().exec("clear");
    } catch (IOException ex) {System.out.println(ex);}
  }

  public static void getInput() {
    int selectedOption = 0;
    clearScreen();
    welcomeScreenBanner();
    mainScreenBanner();

    Boolean Renter = false;
    System.out.println("Are you a:");
    System.out.println("1. Renter");
    System.out.println("2. Host:");

    try {
      System.out.print("\nInput: ");
      selectedOption = scan.nextInt();
      scan.nextLine();
    } catch(InputMismatchException ime) {
      System.out.println("Input only numbers... Try again!!!");
      System.out.println("Redirecting to LogIn page menu...");
      scan.nextLine();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      clearScreen();
      welcomeScreenBanner();
      getInput();
    }

    if(selectedOption == 1) {
        clearScreen();
        Renter = true;
      }
    else if(selectedOption == 2) {
      Renter = false;
      clearScreen();
    } else {
      System.out.println("Invalid input... Try again!!!");
      System.out.println("Redirecting to LogIn page menu....");
      try{Thread.sleep(1000);
      } catch (InterruptedException e) { e.printStackTrace();}

      clearScreen();
      welcomeScreenBanner();
      getInput();
    }

    System.out.println("Enter your choice properly");
    System.out.println("1. Login");
    System.out.println("2. Create New User Account");
    System.out.println("3. Exit");

    try {
      System.out.print("\nInput: ");
      selectedOption = scan.nextInt();
      scan.nextLine();
    }catch(InputMismatchException ime) {
      System.out.println("Input only numbers... Try again!!!");
      System.out.println("Redirecting to LogIn page menu...");
      scan.nextLine();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      clearScreen();
      welcomeScreenBanner();
      getInput();
    }

    if(selectedOption == 1) {
      try {
        clearScreen();
        welcomeScreenBanner();
        LoginPage.getUserCredentials(Renter);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }else if(selectedOption == 2) {
      clearScreen();
      welcomeScreenBanner();
      User.NewRegistration.createNewUserAccount(Renter);
    }else if(selectedOption == 3){
      System.out.println("Exiting the application...");
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      clearScreen();
      System.exit(0);
    } else {
      System.out.println("Invalid input... Try again!!!");
      System.out.println("Redirecting to LogIn page menu....");
      try{Thread.sleep(1000);
      } catch (InterruptedException e) { e.printStackTrace();}

      clearScreen();
      welcomeScreenBanner();
      getInput();
    }
  }

  public static void main (String[] args) throws Exception {

    InitialiseTables.Initialise_all_tables();
    welcomeScreenBanner();
    getInput();
//
//    String url = "jdbc:mysql://127.0.0.1/mydb";
//
//    String uname = "root";
//    String pass = "";
//    Statement sql;
//    PreparedStatement ps;
//
//    Connection connection;
//
//    //Class.forName("org.cj.mysql.jdbc.Driver");
//    try {
//      connection =  DriverManager.getConnection(url, uname, pass);
//
//    } catch (SQLException ex) {
//      System.out.println("Connection Failed! Check output console");
//      ex.printStackTrace();
//      return;
//
//    }
//    if (connection != null) {
//      System.out.println("You are ready to work with your database!");
//    } else {
//      System.out.println("Failed to make connection!");
//    }
//
//    try {
//
//      //Create a Statement for executing SQL queries
//      sql = connection.createStatement();
//
//      //---------------------------------------------------------------------------------------
//      //Create jdbc_demo table
//      String sqlQ;
//      sqlQ = "DROP TABLE IF EXISTS Listings, Student, Course, Offering,Took\n";
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      sqlQ = "create table Listings(\n" +
//              "\tlisting_ID integer primary key,\n" +
//              "\troom_type varchar(15) not null,\n" +
//              "\tlatitude integer NOT NULL,\n" +
//              "\tlongitude integer NOT NULL)\n";
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      String[] room_types = {"room", "full house", "apartment", "full house", "apartment"};
//      Integer[] latitudes = {1043, 1202, 3402, 4392, 1039};
//      Integer[] longitudes = {2832, 3923, 1848, 8274, 1293};
//
//      sqlQ = "INSERT INTO Listings VALUES (?,?,?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//      for (int i=0; i<5; i++){
//        System.out.println(i + "...\n");
//
//        //Set column one (ID) to i
//        ps.setInt(1,i);
//        ps.setString(2,room_types[i]);
//        ps.setInt(3,latitudes[i]);
//        ps.setInt(4,longitudes[i]);
//        ps.executeUpdate();
//      }
//      ps.close();
//
//      //Create jdbc_demo table
//      sqlQ = "DROP TABLE IF EXISTS Calender, has_availability, Address, has_address, Amenities, has_amenities, User, owns, rents, Review, Renter, Host\n";
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      sqlQ = "create table Calender(\n" +
//              "\tdate Date NOT NULL,\n" +
//              "\tprice integer NOT NULL,\n" +
//              "\tlisting_ID integer NOT NULL,\n" +
//              //"\tis_available integer,\n" +
//              "\tprimary key(date, listing_ID),\n" +
//              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";
//
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      Integer[] prices = {50, 50, 50, 70, 70};
//      //Integer[] availability = {0, 0, 0, 1, 1};
//
//      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//      String[] dates = {"2022-01-01", "2022-01-02", "2022-01-03", "2022-01-04", "2022-01-05"};
//      Integer[] listings = {1, 1, 1, 2, 2};
//
//      sqlQ = "INSERT INTO Calender VALUES (?,?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//      for (int i=0; i<5; i++){
//        System.out.println(i + "...\n");
//
//        Date myDate = formatter.parse(dates[i]);
//        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
//        ps.setDate(1, sqlDate);
//        ps.setInt(2,prices[i]);
//        ps.setInt(3,listings[i]);
//       // ps.setInt(4,availability[i]);
//        ps.executeUpdate();
//      }
//      ps.close();
////
////      sqlQ = "create table has_availability(\n" +
////              "\tdate Date NOT NULL,\n" +
////              "\tlisting_ID integer NOT NULL,\n" +
////              "\tprimary key(date, listing_ID),\n" +
////              "\tFOREIGN KEY (PersonID) REFERENCES Persons(PersonID))\n";
////
////              System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
////      sql.executeUpdate(sqlQ);
////
////      Integer[] listings = {1, 1, 1, 2, 2};
////      String[] dates_listings = {"2022-01-01", "2022-01-02", "2022-01-03", "2022-01-04", "2022-01-05"};
////
////      sqlQ = "INSERT INTO has_availability VALUES (?,?) ";
////      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
////      ps = connection.prepareStatement(sqlQ);
////      for (int i=0; i<5; i++){
////        System.out.println(i + "...\n");
////        //Set column one (ID) to i
////        Date myDate = formatter.parse(dates_listings[i]);
////        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
////        ps.setDate(1, sqlDate);
////        ps.setInt(2,listings[i]);
////        ps.executeUpdate();
////      }
////      ps.close();
//
//      sqlQ = "create table Address(\n" +
//              "\tpostal_code varchar(8),\n" +
//              "\tcity varchar(15),\n" +
//              "\tcountry varchar(15),\n" +
//              "\tapt_name varchar(30),\n" +
//              "\tprimary key(postal_code, city, country, apt_name))\n";
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      String[] postal_codes = {"M1G3T8", "M1G3T8", "M1G3T8", "M1G3T8"};
//      String[] city = {"Toronto", "Ottawa", "London", "Delhi"};
//      String[] country = {"Canada", "Canada", "UK", "India"};
//      String [] apt = {"Highland", "Highland", "Highland", "Highland"};
//
//      sqlQ = "INSERT INTO Address VALUES (?,?,?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//      for (int i=0; i<4; i++){
//        System.out.println(i + "...\n");
//
//        ps.setString(1, postal_codes[i]);
//        ps.setString(2,city[i]);
//        ps.setString(3,country[i]);
//        ps.setString(4,apt[i]);
//        ps.executeUpdate();
//      }
//      ps.close();
//
//      //has_addr(listing_id, postal_code, city, country, number_name)
//      sqlQ = "create table has_address(\n" +
//              "\tlisting_ID integer,\n" +
//              "\tpostal_code varchar(8),\n" +
//              "\tcity varchar(15),\n" +
//              "\tcountry varchar(15),\n" +
//              "\tapt_name varchar(30),\n" +
//              "\tprimary key(listing_ID, postal_code, city, country, apt_name),\n" +
//              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";
//
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      sqlQ = "INSERT INTO has_address VALUES (?,?,?,?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=0; i<4; i++){
//        System.out.println(i + "...\n");
//        ps.setInt(1, i);
//        ps.setString(2, postal_codes[i]);
//        ps.setString(3,city[i]);
//        ps.setString(4,country[i]);
//        ps.setString(5,apt[i]);
//        ps.executeUpdate();
//      }
//      ps.close();
//
//      sqlQ = "create table Amenities(\n" +
//              "\tamenity_ID integer,\n" +
//              "\tamenity_type varchar(30),\n" +
//              "\tlisting_ID integer,\n" +
//              "\tprimary key(amenity_ID, listing_ID),\n" +
//              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      sqlQ = "INSERT INTO Amenities VALUES (?,?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//
//      String[] amenities = {"Wifi", "Kitchen", "Washer", "Dryer", "Air-conditioning"};
//      for (int i=0; i<5; i++){
//        System.out.println(i + "...\n");
//        ps.setInt(1, i);
//        ps.setString(2, amenities[i]);
//        ps.setInt(3, listings[i]);
//        ps.executeUpdate();
//      }
//      ps.close();
//
////
////      sqlQ = "create table has_amenities(\n" +
////              "\tamenity_ID integer,\n" +
////              "\tlisting_ID integer,\n" +
////              "\tprimary key(amenity_ID, listing_ID))\n";
////
////      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
////      sql.executeUpdate(sqlQ);
////
////      sqlQ = "INSERT INTO has_amenities VALUES (?,?) ";
////      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
////      ps = connection.prepareStatement(sqlQ);
////
////      for (int i=0; i<5; i++){
////        System.out.println(i + "...\n");
////        ps.setInt(1, i);
////        ps.setInt(2, listings[i]);
////        ps.executeUpdate();
////      }
////      ps.close();
//
//      sqlQ = "create table User(\n" +
//              "\tSIN integer,\n" +
//              "\tfirstname varchar(30),\n" +
//              "\tlastname varchar(30),\n" +
//              "\toccupation varchar(30),\n" +
//              "\tdob Date,\n" +
//              "\tusername varchar(30),\n" +
//              "\tpassword varchar(30),\n" +
//              "\tprimary key(SIN))\n";
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      sqlQ = "INSERT INTO User VALUES (?,?,?,?,?,?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//
//      String[] firstnames = {"Vanshika", "Annanya", "Bob", "Chandler", "David"};
//      String[] lastnames = {"Virmani", "Sharma", "Kennedy", "Tesla", "Williams"};
//      String[] usernames = {"Vanshika101", "Annanya101", "Bob101", "Chandler101", "David101"};
//      String[] passwords = {"1234", "1234", "1234", "1234", "1234"};
//      String[] occupations = {"Journalist", "Dancer", "Singer", "Artist", "Scientist"};
//      String[] dobs = {"2002-02-06", "2002-02-06", "2002-02-06", "2002-02-06", "2002-02-06"};
//
//      for (int i=0; i<5; i++){
//        System.out.println(i + "...\n");
//        ps.setInt(1, (i*2));
//        ps.setString(2, firstnames[i]);
//        ps.setString(3, lastnames[i]);
//        ps.setString(4, occupations[i]);
//        Date myDate = formatter.parse(dobs[i]);
//        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
//        ps.setDate(5, sqlDate);
//        ps.setString(6, usernames[i]);
//        ps.setString(7, passwords[i]);
//        ps.executeUpdate();
//      }
//      ps.close();
//
//      sqlQ = "create table Renter(\n" +
//              "\tSIN integer,\n" +
//              "\tpayment integer,\n" +
//              "\tprimary key(SIN),\n" +
//              "\tFOREIGN KEY (SIN) REFERENCES User(SIN))\n";
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      sqlQ = "INSERT INTO Renter VALUES (?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=0; i<2; i++){
//        System.out.println(i + "...\n");
//        ps.setInt(1, (i*2));
//        ps.setInt(2, (i*5)+12);
//        ps.executeUpdate();
//      }
//      ps.close();
//
//      sqlQ = "create table Host(\n" +
//              "\tSIN integer,\n" +
//              "\tprimary key(SIN),\n" +
//              "\tFOREIGN KEY (SIN) REFERENCES User(SIN))\n";
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      sqlQ = "INSERT INTO Host VALUES (?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=0; i<2; i++){
//        System.out.println(i + "...\n");
//        ps.setInt(1, (i*2));
//        ps.executeUpdate();
//      }
//      ps.close();
//
//      sqlQ = "create table owns(\n" +
//              "\tSIN integer,\n" +
//              "\tlisting_ID integer,\n" +
//              "\tprimary key(SIN, listing_ID),\n" +
//              "\tFOREIGN KEY (SIN) REFERENCES User(SIN),\n" +
//              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";
//
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      sqlQ = "INSERT INTO owns VALUES (?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=0; i<2; i++){
//        System.out.println(i + "...\n");
//        ps.setInt(1, (i*2));
//        ps.setInt(2, i);
//        ps.executeUpdate();
//      }
//      ps.close();
//
//      sqlQ = "create table rents(\n" +
//              "\tSIN integer,\n" +
//              "\tlisting_ID integer,\n" +
//              "\tstart_date Date,\n" +
//              "\tend_date Date,\n" +
//              "\tprimary key(SIN, listing_ID),\n" +
//              "\tFOREIGN KEY (SIN) REFERENCES User(SIN),\n" +
//              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      Integer[] rents_from = {0, 1, 0, 1, 1};
//      String[] start_dates = {"2022-01-01", "2022-01-02", "2022-01-03", "2022-01-04", "2022-01-05"};
//      String[] end_dates = {"2022-01-02", "2022-01-04", "2022-01-05", "2022-01-05", "2022-01-09"};
//
//      sqlQ = "INSERT INTO rents VALUES (?,?,?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=2; i<5; i++){
//        System.out.println(i + "...\n");
//        ps.setInt(1, (i*2));
//        ps.setInt(2, rents_from[i]);
//        Date my_start_date = formatter.parse(start_dates[i]);
//        java.sql.Date sqlDate = new java.sql.Date(my_start_date.getTime());
//        ps.setDate(3, sqlDate);
//        Date my_end_date = formatter.parse(end_dates[i]);
//        sqlDate = new java.sql.Date(my_end_date.getTime());
//        ps.setDate(4, sqlDate);
//        ps.executeUpdate();
//      }
//      ps.close();
//
//      // review(comment, rating, rev_id, listing_id, host_id, renter_id)
//      sqlQ = "create table Review(\n" +
//              "\treview_ID integer,\n" +
//              "\tlisting_ID integer,\n" +
//              "\thost_ID integer,\n" +
//              "\trenter_ID integer,\n" +
//              "\tcomment varchar(200),\n" +
//              "\trating integer,\n" +
//              "\tprimary key(review_ID),\n" +
//              "\tFOREIGN KEY (host_ID) REFERENCES Host(SIN),\n" +
//              "\tFOREIGN KEY (renter_ID) REFERENCES Renter(SIN),\n" +
//              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      String[] reviews = {"Amazing view", "Best pool", "Poor service"};
//      Integer[] rating = {5, 4, 2};
//      Integer[] renters = {2, 3, 4};
//      Integer[] hosts = {0, 1, 1};
//
//      sqlQ = "INSERT INTO Review VALUES (?,?,?,?,?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=0; i<3; i++){
//        System.out.println(i + "...\n");
//        ps.setInt(1, i);
//        ps.setInt(2, i);
//        ps.setInt(3, hosts[i]);
//        ps.setInt(4, renters[i]);
//        ps.setString(5, reviews[i]);
//        ps.setInt(6, rating[i]);
//        ps.executeUpdate();
//      }
//      ps.close();
//
//    } catch (SQLException e) {
//
//      System.out.println("Query Execution Failed!");
//      e.printStackTrace();
//      return;
//    }

  }

}