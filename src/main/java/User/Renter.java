package User;

import Main.Main;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Filter.Filter;
import Review.Review;

public class Renter {

  private static String apt_name = null, city = null, country = null, postal_code = null;
  private static int longitude;
  private static int latitude;
  private static int high_price;
  private static int low_price;
  private static String startDate;
  private static String endDate;

  private static int has_ac;
  private static int has_dryer;
  private static int has_kitchen;
  private static int has_washer;
  private static int has_wifi;

  private static Scanner scan = new Scanner(System.in);

  public static void selectFilter() throws SQLException, ParseException, InterruptedException {
    Main.clearScreen();
    Main.welcomeScreenBanner();
    System.out.println("\t\t\t\t\t\t  View Filters \n");

    int selectedOption = 0;

    System.out.println("1. Filter by Address");
    System.out.println("2. Filter by Date");
    System.out.println("3. Filter by Price");
    System.out.println("4. Filter by Longitude and Latitude");
    System.out.println("5. Filter by Postal Code");
    System.out.println("6. Filter by Amenities");
    System.out.println("7. Search Listings");
    System.out.println("8. Book a listing");
    System.out.println("9. View Bookings");
    System.out.println("10. Cancel Booking");
    System.out.println("11. Review a Listing");
    System.out.println("12. Review a Host");
    System.out.println("13. Delete your Account");
    System.out.println("14. Exit");


    try {
      System.out.print("\nInput: ");
      selectedOption = scan.nextInt();
      scan.nextLine();
    }catch(InputMismatchException ime) {
      System.out.println("Input only numbers... Try again!!!");
      System.out.println("Redirecting to Filter.Filter page menu...");
      scan.nextLine();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      selectFilter();
    }

    while (selectedOption != 14){

      if(selectedOption == 1){
        Filter.get_address();

      }else if(selectedOption == 2){
        Filter.get_dates();

      }else if(selectedOption == 3){
        Filter.get_budget();

      }else if (selectedOption == 4){
        Filter.get_location();

      }else if (selectedOption == 5){
        Filter.get_postal_code();

      }else if (selectedOption == 6){
        Filter.get_amenities();

      }else if (selectedOption == 7) {
        Filter.filter_listings();
      }else if (selectedOption == 8){
        //System.out.println("hi, selected 8");
        Filter.book_listing();
      }
      else if (selectedOption == 9){
        System.out.println("View Bookings");
        Filter.viewBookings();
      }
      else if (selectedOption == 10){
        System.out.println("Cancel a Booking");
        Filter.cancelBooking();
      }
      else if (selectedOption == 11){
        System.out.println("Review a Listing");
        Review.reviewListing();
      }
      else if (selectedOption == 12){
        System.out.println("Review a Host");
        Review.reviewHost();
      }
      else if (selectedOption == 13){
        System.out.println("Delete your Account");
        Filter.deleteRenter();
      }
      else {
        System.out.println("Input only numbers... Try again!!!");
        System.out.println("Redirecting to Filter.Filter page menu...");
        scan.nextLine();
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        selectFilter();
      }
      System.out.println("Data Recorded");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      selectFilter();
    }
    System.out.println("Exiting the application...");
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.exit(0);
  }




}


