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
              "('full house', 1203, 2383, 'E', 'G', 'G', 'G'), ('apartment', 1292, 1932, 'H', 'R', 'R', 'R'), ('room', 3453, 3453, 'F', 'F', 'F', 'F'),\n" +
              "('room', 56736, 23423, 'Jfg', 'Jdf', 'Hgf', 'Pg'), ('apartment', 67645, 525645, 'U', 'U', 'Y', 'O'), ('house', 3452, 3413, 'Kf', 'gf', 'Mf', 'Pf'), ('room', 34542, " +
              "345413, 'rfK', 'fgG', 'fgM', 'Pg'), ('apartment', 234, 234, 'Kf', 'G', 'fgM', 'Pfg'), ('house', 34342, 2343413, 'Kc', 'fdG', 'fdg', 'gP'), ('house', 353452, 543413, 'K5', 'G45', 'Mfg', 'Pfg'), " +
              "('room', 453452, 341443, 'Krg', 'G', 'M', 'Pfg'), ('apartment', 3452, 3413, 'K', 'G', 'M', 'P'), ('house', 342452, 345413, 'L', 'G', 'M', 'Pr') \n";

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
              "('2022-01-01', 50, 3),\n" +
              "('2022-01-02', 50, 3),\n" +
              "('2022-01-03', 50, 3),\n" +
              "('2022-01-04', 20, 3),\n" +
              "('2022-01-01', 50, 4),\n" +
              "('2022-01-02', 50, 4),\n" +
              "('2022-01-03', 30, 4),\n" +
              "('2022-01-04', 50, 4),\n" +
              "('2022-01-05', 50, 4),\n" +
              "('2022-01-06', 50, 4),\n" +
              "('2022-01-07', 50, 4),\n" +
              "('2022-01-08', 50, 4),\n" +
              "('2022-01-09', 50, 4),\n" +
              "('2022-01-10', 50, 4),\n" +
              "('2022-01-01', 50, 5),\n" +
              "('2022-01-02', 50, 5),\n" +
              "('2022-01-09', 50, 5),\n" +
              "('2022-01-10', 50, 5),\n" +
              "('2022-01-05', 50, 6),\n" +
              "('2022-01-06', 70, 6),\n" +
              "('2022-01-01', 50, 7),\n" +
              "('2022-01-02', 50, 7),\n" +
              "('2022-01-03', 50, 7),\n" +
              "('2022-01-04', 20, 7),\n" +
              "('2022-01-05', 70, 8),\n" +
              "('2022-01-06', 70, 8),\n" +
              "('2022-01-07', 70, 8),\n" +
              "('2022-01-08', 70, 8),\n" +
              "('2022-01-09', 70, 8),\n" +
              "('2022-01-18', 70, 9),\n" +
              "('2022-01-19', 70, 9),\n" +
              "('2022-01-20', 70, 9),\n" +
              "('2022-01-21', 50, 9),\n" +
              "('2022-01-22', 70, 9),\n" +
              "('2022-01-23', 70, 9),\n" +
              "('2022-01-24', 70, 10),\n" +
              "('2022-01-28', 70, 11),\n" +
              "('2022-01-29', 70, 12),\n" +
              "('2022-01-30', 70, 12),\n" +
              "('2022-01-31', 70, 12),\n" +
              "('2022-02-01', 70, 12),\n" +
              "('2022-02-02', 70, 12),\n" +
              "('2022-02-05', 70, 13),\n" +
//              "('2022-02-06', 70, 14),\n" +
//              "('2022-02-07', 70, 14),\n" +
//              "('2022-02-08', 70, 14),\n" +
//              "('2022-02-09', 100, 14),\n" +
//              "('2022-02-10', 70, 14),\n" +
//              "('2022-02-11', 70, 14),\n" +
//              "('2022-02-12', 70, 14),\n" +
//              "('2022-02-13', 70, 14),\n" +
//              "('2022-02-14', 200, 14),\n" +
//              "('2022-02-15', 70, 14),\n" +
              "('2022-01-06', 60, 3)\n";

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

      sqlQ = "INSERT INTO Amenities(amenity_ID, amenity_type, listing_ID) " +
              "VALUES " +
              "('1', 'Wifi', 1)," +
              "('2', 'Kitchen', 1)," +
              "('3', 'Washer', 1)," +
              "('4', 'Dryer', 1)," +
              "('5', 'AC', 1)," +
              "('2', 'Kitchen', 2)," +
              "('1', 'Wifi', 2)," +
              "('5', 'AC', 2)," +
              "('5', 'AC', 3)," +
              "('4', 'Dryer', 3)," +
              "('1', 'Wifi', 6)," +
              "('2', 'Kitchen', 6)," +
              "('3', 'Washer', 6)," +
              "('4', 'Dryer', 6)," +
              "('5', 'AC', 6)," +
              "('1', 'Wifi', 9)," +
              "('3', 'Washer', 9)," +
              "('2', 'Kitchen', 11)," +
              "('4', 'Dryer', 11)," +
              "('3', 'Washer', 10)," +
              "('3', 'Washer', 12)," +
              "('2', 'Kitchen', 12)," +
              "('1', 'Wifi', 13)," +
              "('4', 'Dryer', 14)," +
              "('5', 'AC', 14)," +

              "\n";

//      sqlQ = "INSERT INTO Amenities VALUES (?,?,?)\n";
//      ps = connection.prepareStatement(sqlQ);
//
//      String[] amenities = {"Wifi", "Kitchen", "Washer", "Dryer", "AC"};
//      for (int i=0; i<5; i++){
//        ps.setInt(1, (i+1));
//        ps.setString(2, amenities[i]);
//        ps.setInt(3, listings[i]);
//        ps.executeUpdate();
//      }
//      ps.close();

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

      sqlQ = "INSERT INTO User VALUES (1,'Annanya1','Sharma', 'Doc', '1975-04-11', 'annanya1','1234'), (2,'Annanya2','Sharma', 'Doc', '1975-04-11', 'annanya2','1234')" +
              ",(3,'Annanya3','Sharma', 'Doc', '1975-04-11', 'annanya3','1234'),(4,'Annanya4','Sharma', 'Doc', '1975-04-11', 'annanya4','1234'),(5,'Annanya5','Sharma', " +
              "'Doc', '1975-04-11', 'annanya5','1234'),(6,'Annanya6','Sharma', 'Doc', '1975-04-11', 'annanya6','1234'),(7,'Annanya7','Sharma', 'Doc', '1975-04-11', 'annanya7'" +
              ",'1234')\n ";
      sql.executeUpdate(sqlQ);

      sqlQ = "INSERT INTO User VALUES (8,'Vanshika8','Virmani', 'Engineer', '1975-04-11', 'van1','1234'), (9,'Vanshika9','Virmani', 'Engineer', '1975-04-11', 'van2','1234')" +
              ",(10,'Vanshika10','Virmani', 'Engineer', '1975-04-11', 'van3','1234'),(11,'Vanshika11','Virmani', 'Engineer', '1975-04-11', 'van4','1234'),(12,'Vanshika12','Virmani', " +
              "'Engineer', '1975-04-11', 'van5','1234') " +",(13,'Vanshika13','Virmani', 'Engineer', '1975-04-11', 'van6','1234'),(14,'Vanshika14','Virmani', 'Engineer', '1975-04-11', 'van7','1234')\n ";

      sql.executeUpdate(sqlQ);
//      sqlQ = "INSERT INTO User VALUES (?,?,?,?,?,?,?)\n";
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

      sqlQ = "create table Renter(\n" +
              "\tSIN integer,\n" +
              "\tpayment varchar(10),\n" +
              "\tprimary key(SIN),\n" +
              "\tFOREIGN KEY (SIN) REFERENCES User(SIN))\n";

      sql.executeUpdate(sqlQ);


      sqlQ = "INSERT INTO Renter VALUES (8,'1293-9287'), (9,'1293-9287'), (10,'1293-9287'), (11,'1293-9287'), (12,'1293-9287'), (13,'1293-9287'), (14,'1293-9287')\n ";
      sql.executeUpdate(sqlQ);

      sqlQ = "create table Host(\n" +
              "\tSIN integer,\n" +
              "\tprimary key(SIN),\n" +
              "\tFOREIGN KEY (SIN) REFERENCES User(SIN))\n";

      sql.executeUpdate(sqlQ);
      sqlQ = "INSERT INTO Host VALUES (1), (2), (3), (4), (5), (6), (7)\n";
      sql.executeUpdate(sqlQ);

//      sqlQ = "INSERT INTO Host VALUES (?\n) ";
//      sqlQ = "INSERT INTO renter(SIN, listing_ID) VALUES (1, 1), (1, 2), ";
//      ps = connection.prepareStatement(sqlQ);
//
//      for (int i=0; i<2; i++){
//        ps.setInt(1, (i*2));
//        ps.executeUpdate();
//      }
//      ps.close();

      sqlQ = "create table owns(\n" +
              "\tSIN integer,\n" +
              "\tlisting_ID integer NOT NULL,\n" +
              "\tprimary key(listing_ID, SIN), index(listing_ID), index(SIN),\n" +
              "\tFOREIGN KEY (SIN) REFERENCES User(SIN))\n";
            //  "\tFOREIGN KEY (listing_ID) REFERENCES Listings(listing_ID))\n";

      sql.executeUpdate(sqlQ);

      sqlQ = "INSERT INTO owns(SIN, listing_ID) VALUES (1, 1), (1, 2), (1, 3), (1, 4), (2, 5), (2, 6), (3, 7), (3, 8), (3, 9), (6, 10), (1, 11), (1, 12), (4, 13), (5, 14)";
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

      sqlQ = "INSERT INTO Bookings " +
              "(listing_ID, renter_ID, start, end, completed) VALUES " +
              "(1, 8, '2022-12-01','2022-12-03', 0), " +
              "(1, 9, '2022-11-05','2022-11-10', 0), " +
              "(4, 14, '2022-09-01','2022-09-03', 1), " +
              "(4, 14, '2022-03-01','2022-03-03', 1), " +
              "(4, 10, '2022-11-01','2022-11-24', 0), " +
              "(6, 11, '2022-07-01','2022-08-03', 1)," +
              "(6, 11, '2022-10-01','2022-10-03', 1), " +
              "(11, 9, '2022-12-01','2022-12-03', 1), " +
              "(11, 8, '2022-11-01','2022-11-03', 0), " +
              "(14, 8, '2022-04-01','2022-05-03', 1), " +
              "(14, 8, '2022-03-01','2022-04-03', 0)," +
              "(14, 8, '2022-03-20','2022-03-30', 1)," +
              "(1, 11, '2022-09-01','2022-09-03', 1), " +
              "(5, 11, '2022-03-01','2022-03-13', 1), " +
              "(2, 11, '2022-01-01','2022-01-03', 1), " +
              "(12, 13, '2022-11-01','2022-11-03', 1), " +
              "(13, 13, '2022-12-01','2022-12-03', 1), " +
              "(7, 13, '2022-01-15','2022-01-31', 0), " +
              "(7, 13, '2022-02-01','2022-02-03', 1), " +
              "(8, 13, '2022-01-12','2022-01-13', 1)," +
              "(11, 8, '2022-05-01','2022-05-03', 0), " +
              "(1, 8, '2022-05-01','2022-05-03', 1)," +
              "(14, 9, '2022-01-01','2022-01-03', 1), " +
              "(1, 14, '2022-03-01','2022-03-03', 1),"  +
              "(2, 14, '2022-07-04', '2022-07-05', 0)\n";

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

      sqlQ = "INSERT INTO rents (SIN, listing_ID) VALUES (8, 2), (14, 3), (9, 6), (13, 8), (13, 9), (12, 12), (14, 14)\n";
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
              "(1, 1, 8,'Amazing view', 4, 0, 0, 1),\n" +
              "(2, 1, 9,'Good host', 3, 0, 1, 0),\n" +
              "(3, 1, 14,'Good host', 3, 0, 1, 0),\n" +
              "(4, 1, 14,'Good host', 3, 0, 1, 0),\n" +
              "(5, 1, 10,'Good host', 3, 0, 1, 0),\n" +
              "(6, 2, 11,'Good host', 3, 0, 1, 0),\n" +
              "(7, 2, 11,'Good host', 3, 0, 1, 0),\n" +
              "(8, 1, 9,'Good host', 3, 0, 1, 0),\n" +
              "(9, 1, 8,'Good host', 3, 0, 1, 0),\n" +
              "(10, 5, 8,'Good host', 3, 0, 1, 0)\n";

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


    } catch (SQLException e) {

      System.out.println("Query Execution Failed!");
      e.printStackTrace();
      return;
    }

  }



}
