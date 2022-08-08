package Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InitialiseTables {

  public static void Initialise_all_tables() {

    Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
    try {
      Statement sql = connection.createStatement();
      String sqlQ;
      sqlQ = "SET FOREIGN_KEY_CHECKS = 0\n";
      sql.executeUpdate(sqlQ);
      sqlQ = "DROP TABLE IF EXISTS Listings, Student, Course, Offering,Took, Calender, has_availability, Address, has_address, Amenities, has_amenities, User, owns, rents, Review, Renter, Host, Bookings, Cancellations\n";
      sql.executeUpdate(sqlQ);
      PreparedStatement ps;

      sqlQ = "SET FOREIGN_KEY_CHECKS = 1\n";
      sql.executeUpdate(sqlQ);

      sqlQ = "create table Listings(\n" +
              "\tlisting_ID INT AUTO_INCREMENT,\n" +
              "\troom_type varchar(15) not null,\n" +
              "\tlatitude integer NOT NULL,\n" +
              "\tlongitude integer NOT NULL,\n" +
              "\tpostal_code varchar(8),\n" +
              "\tcity varchar(15),\n" +
              "\tcountry varchar(15),\n" +
              "\tapt_name varchar(30),\n" +
              "\tprimary key(listing_ID, latitude, longitude))\n";

      sql.executeUpdate(sqlQ);

      String[] room_types = {"room", "full house", "apartment", "full house", "apartment"};
      Integer[] latitudes = {1043, 1202, 3402, 4392, 1039};
      Integer[] longitudes = {2832, 3923, 1848, 8274, 1293};

      sqlQ = "INSERT INTO Listings (room_type, latitude, longitude, postal_code, city, country, apt_name) VALUES ('room', 1043, 1829, 'M', 'S', 'S', 'S'),\n" +
              "('full house', 1203, 2383, 'E', 'G', 'G', 'G'), ('apartment', 1292, 1932, 'H', 'R', 'R', 'R')\n";

      sql.executeUpdate(sqlQ);


      //Create jdbc_demo table

      sqlQ = "create table Calender(\n" +
              "\tdate Date NOT NULL,\n" +
              "\tprice integer NOT NULL,\n" +
              "\tlisting_ID integer NOT NULL,\n" +
              //"\tis_available integer,\n" +
              "\tprimary key(date, listing_ID),\n" +
              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";

      sql.executeUpdate(sqlQ);

      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Integer[] listings = {1, 1, 1, 2, 2};

      sqlQ = "INSERT INTO Calender (date, price, listing_ID) " +
              "VALUES " +
              "('2022-01-01', 50, 1),\n" +
              "('2022-01-02', 50, 1),\n" +
              "('2022-01-03', 50, 1),\n" +
              "('2022-01-04', 20, 1),\n" +
              "('2022-01-05', 70, 1),\n" +
              "('2022-01-06', 70, 1),\n" +
              "('2022-01-04', 60, 2),\n" +
              "('2022-01-05', 60, 2)\n";

      sql.executeUpdate(sqlQ);

//
//      sqlQ = "create table has_availability(\n" +
//              "\tdate Date NOT NULL,\n" +
//              "\tlisting_ID integer NOT NULL,\n" +
//              "\tprimary key(date, listing_ID),\n" +
//              "\tFOREIGN KEY (PersonID) REFERENCES Persons(PersonID))\n";
//
//              System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      Integer[] listings = {1, 1, 1, 2, 2};
//      String[] dates_listings = {"2022-01-01", "2022-01-02", "2022-01-03", "2022-01-04", "2022-01-05"};
//
//      sqlQ = "INSERT INTO has_availability VALUES (?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//      for (int i=0; i<5; i++){
//        System.out.println(i + "...\n");
//        //Set column one (ID) to i
//        Date myDate = formatter.parse(dates_listings[i]);
//        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
//        ps.setDate(1, sqlDate);
//        ps.setInt(2,listings[i]);
//        ps.executeUpdate();
//      }
//      ps.close();

      sqlQ = "create table Address(\n" +
              "\tpostal_code varchar(8),\n" +
              "\tcity varchar(15),\n" +
              "\tcountry varchar(15),\n" +
              "\tapt_name varchar(30),\n" +
              "\tprimary key(postal_code, city, country, apt_name))\n";

      sql.executeUpdate(sqlQ);

      String[] postal_codes = {"M1G3T8", "M1G3T8", "M1G3T8", "M1G3T8"};
      String[] city = {"Toronto", "Ottawa", "London", "Delhi"};
      String[] country = {"Canada", "Canada", "UK", "India"};
      String [] apt = {"Highland", "Highland", "Highland", "Highland"};

      sqlQ = "INSERT INTO Address VALUES (?,?,?,?)";
      ps = connection.prepareStatement(sqlQ);
      for (int i=0; i<4; i++){

        ps.setString(1, postal_codes[i]);
        ps.setString(2,city[i]);
        ps.setString(3,country[i]);
        ps.setString(4,apt[i]);
        ps.executeUpdate();
      }
      ps.close();


      //has_addr(listing_id, postal_code, city, country, number_name)
//      sqlQ = "create table has_address(\n" +
//              "\tlisting_ID integer NOT NULL AUTO_INCREMENT,\n" +
//              "\tpostal_code varchar(8),\n" +
//              "\tcity varchar(15),\n" +
//              "\tcountry varchar(15),\n" +
//              "\tapt_name varchar(30),\n" +
//              "\tprimary key(listing_ID, postal_code, city, country, apt_name),\n" +
//              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";
//
//      sql.executeUpdate(sqlQ);
//
//      sqlQ = "INSERT INTO has_address VALUES (?,?,?,?,?\n)";
//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=0; i<4; i++){
//        ps.setInt(1, i);
//        ps.setString(2, postal_codes[i]);
//        ps.setString(3,city[i]);
//        ps.setString(4,country[i]);
//        ps.setString(5,apt[i]);
//        ps.executeUpdate();
//      }
//      ps.close();

      sqlQ = "create table Amenities(\n" +
              "\tamenity_ID integer,\n" +
              "\tamenity_type varchar(30),\n" +
              "\tlisting_ID integer,\n" +
              "\tprimary key(amenity_ID, listing_ID),\n" +
              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";

      sql.executeUpdate(sqlQ);

      sqlQ = "INSERT INTO Amenities VALUES (?,?,?)\n";
      ps = connection.prepareStatement(sqlQ);

      String[] amenities = {"Wifi", "Kitchen", "Washer", "Dryer", "AC"};
      for (int i=0; i<5; i++){
        ps.setInt(1, (i+1));
        ps.setString(2, amenities[i]);
        ps.setInt(3, listings[i]);
        ps.executeUpdate();
      }
      ps.close();

//
//      sqlQ = "create table has_amenities(\n" +
//              "\tamenity_ID integer,\n" +
//              "\tlisting_ID integer,\n" +
//              "\tprimary key(amenity_ID, listing_ID))\n";
//
//      System.out.println("Executing this command: \n" + sqlQ.replaceAll("\\s+", " ") + "\n");
//      sql.executeUpdate(sqlQ);
//
//      sqlQ = "INSERT INTO has_amenities VALUES (?,?) ";
//      System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=0; i<5; i++){
//        System.out.println(i + "...\n");
//        ps.setInt(1, i);
//        ps.setInt(2, listings[i]);
//        ps.executeUpdate();
//      }
//      ps.close();

      sqlQ = "create table User(\n" +
              "\tSIN integer,\n" +
              "\tfirstname varchar(30),\n" +
              "\tlastname varchar(30),\n" +
              "\toccupation varchar(30),\n" +
              "\tdob Date,\n" +
              "\tusername varchar(30),\n" +
              "\tpassword varchar(30),\n" +
              "\tprimary key(SIN))\n";

      sql.executeUpdate(sqlQ);

      sqlQ = "INSERT INTO User VALUES (?,?,?,?,?,?,?)\n";
      ps = connection.prepareStatement(sqlQ);

      String[] firstnames = {"Vanshika", "Annanya", "Bob", "Chandler", "David"};
      String[] lastnames = {"Virmani", "Sharma", "Kennedy", "Tesla", "Williams"};
      String[] usernames = {"Vanshika101", "Annanya101", "Bob101", "Chandler101", "David101"};
      String[] passwords = {"1234", "1234", "1234", "1234", "1234"};
      String[] occupations = {"Journalist", "Dancer", "Singer", "Artist", "Scientist"};
      String[] dobs = {"2002-02-06", "2002-02-06", "2002-02-06", "2002-02-06", "2002-02-06"};

      for (int i=0; i<5; i++){
        ps.setInt(1, (i*2));
        ps.setString(2, firstnames[i]);
        ps.setString(3, lastnames[i]);
        ps.setString(4, occupations[i]);
        Date myDate = formatter.parse(dobs[i]);
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        ps.setDate(5, sqlDate);
        ps.setString(6, usernames[i]);
        ps.setString(7, passwords[i]);
        ps.executeUpdate();
      }
      ps.close();

      sqlQ = "create table Renter(\n" +
              "\tSIN integer,\n" +
              "\tpayment varchar(10),\n" +
              "\tprimary key(SIN),\n" +
              "\tFOREIGN KEY (SIN) REFERENCES User(SIN))\n";

      sql.executeUpdate(sqlQ);

     // String[] payment_info = {"1293-9287", "8273-8734"};

      sqlQ = "INSERT INTO Renter VALUES (2,'1293-9287'\n) ";
      sql.executeUpdate(sqlQ);

      sqlQ = "create table Host(\n" +
              "\tSIN integer,\n" +
              "\tprimary key(SIN),\n" +
              "\tFOREIGN KEY (SIN) REFERENCES User(SIN))\n";

      sql.executeUpdate(sqlQ);

      sqlQ = "INSERT INTO Host VALUES (?\n) ";
      ps = connection.prepareStatement(sqlQ);

      for (int i=0; i<2; i++){
        ps.setInt(1, (i*2));
        ps.executeUpdate();
      }
      ps.close();

      sqlQ = "create table owns(\n" +
              "\tSIN integer,\n" +
              "\tlisting_ID integer NOT NULL,\n" +
              "\tprimary key(listing_ID, SIN), index(listing_ID), index(SIN),\n" +
              "\tFOREIGN KEY (SIN) REFERENCES User(SIN))\n";
            //  "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";

      sql.executeUpdate(sqlQ);

      sqlQ = "INSERT INTO owns(SIN, listing_ID) VALUES (0, 1), (0, 2)";
      //System.out.println("Prepared Statement: " + sqlQ.replaceAll("\\s+", " ") + "\n");
      sql.executeUpdate(sqlQ);

//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=0; i<2; i++){
//        System.out.println(i + "...\n");
//        ps.setInt(1, (i*2));
//        ps.setInt(2, i);
//        ps.executeUpdate();
//      }
//      ps.close();

      // review(comment, rating, rev_id, listing_id, host_id, renter_id)

      sqlQ = "create table Bookings(\n" +
              "\tbooking_ID INT NOT NULL AUTO_INCREMENT,\n" +
              "\tlisting_ID integer,\n" +
              "\trenter_ID integer,\n" +
              "\tstart Date,\n" +
              "\tend Date,\n" +
              "\tcompleted integer,\n" +
              "\tprimary key(booking_ID))\n";

      sql.executeUpdate(sqlQ);

      sqlQ = "INSERT INTO Bookings (listing_ID, renter_ID, start, end, completed) VALUES (1, 2, '2022-01-01','2022-01-03', 1),\n" +
              "(2, 2, '2022-01-04', '2022-01-05', 0)\n";

      sql.executeUpdate(sqlQ);

      sqlQ = "create table rents(\n" +
              "\tSIN integer,\n" +
              "\tbooking_ID INT AUTO_INCREMENT,\n" +
              "\tlisting_ID integer,\n" +
              "\tprimary key(booking_ID, listing_ID, SIN), index(booking_ID), index(listing_ID), index(SIN),\n" +
              "\tFOREIGN KEY (SIN) REFERENCES User(SIN),\n" +
              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n" ;
           //   "\tFOREIGN KEY (booking_ID) REFERENCES Bookings(booking_ID))\n";

      sql.executeUpdate(sqlQ);

      sqlQ = "INSERT INTO rents (SIN, listing_ID) VALUES (2, 1), (2, 2)\n";
      sql.executeUpdate(sqlQ);

      sqlQ = "create table Review(\n" +
              "\treview_ID INT NOT NULL AUTO_INCREMENT,\n" +
              "\tbooking_ID integer,\n" +
              "\thost_ID integer,\n" +
              "\trenter_ID integer,\n" +
              "\tcomment varchar(200),\n" +
              "\trating integer,\n" +
              "\thost_to_renter integer,\n" +
              "\trenter_to_host integer,\n" +
              "\trenter_to_listing integer,\n" +
              "\tprimary key(review_ID), index(booking_ID), index(host_ID),  index(renter_ID),\n" +
              "\tFOREIGN KEY (host_ID) REFERENCES Host(SIN),\n" +
              "\tFOREIGN KEY (renter_ID) REFERENCES Renter(SIN),\n" +
              "\tFOREIGN KEY (booking_ID) REFERENCES Bookings(booking_ID))\n";

      sql.executeUpdate(sqlQ);

      sqlQ = "INSERT INTO Review (booking_ID, host_ID, renter_ID, comment, rating, host_to_renter, " +
              "renter_to_host, renter_to_listing) VALUES " +
              "(1, 0, 2,'Amazing view', 4, 0, 0, 1),\n" +
              "(2, 0, 2,'Good host', 3, 0, 1, 0),\n" +
              "(1, 0, 2,'Good renter', 4, 1, 0, 0)\n";

      sql.executeUpdate(sqlQ);

//      Integer[] rents_from = {0, 1, 0, 1, 1};
//      String[] start_dates = {"2022-01-01", "2022-01-02", "2022-01-03", "2022-01-04", "2022-01-05"};
//      String[] end_dates = {"2022-01-02", "2022-01-04", "2022-01-05", "2022-01-05", "2022-01-09"};
//
//      sqlQ = "INSERT INTO rents VALUES (?,?,?,?\n) ";
//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=2; i<5; i++){
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


      sqlQ = "create table Cancellations(\n" +
              "\tbooking_ID integer,\n" +
              "\tlisting_ID integer,\n" +
              "\trenter_ID integer,\n" +
              "\tdate Date,\n"+
              "\tprice integer,\n"+
              "\trenter_or_host integer,\n" +
              "\tprimary key(booking_ID, listing_ID, renter_ID, renter_or_host), index(booking_ID), index(listing_ID),  index(renter_ID),\n" +
              "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID),\n" +
              "\tFOREIGN KEY (renter_ID) REFERENCES Renter(SIN),\n" +
              "\tFOREIGN KEY (booking_ID) REFERENCES Bookings(booking_ID))\n";

      sql.executeUpdate(sqlQ);


    } catch (SQLException | ParseException e) {

      System.out.println("Query Execution Failed!");
      e.printStackTrace();
      return;
    }

  }



}
