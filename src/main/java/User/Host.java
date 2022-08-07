package User;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import Listing.Listing;
import Review.Review;
public class Host {

  private static Scanner scan = new Scanner(System.in);

  public static void startPage() throws SQLException, InterruptedException {

    int selectedOption = 0;

    System.out.println("You are a Host!\n");
    System.out.println("Choose an option below: \n");

    System.out.println("1. Create a Listing");
    System.out.println("2. View your Listings");
    System.out.println("3. Update your Listing");
    System.out.println("4. Cancel a Booking");
    System.out.println("5. Remove a Listing");
    System.out.println("6. Review a Renter");
    System.out.println("7. Delete your Account");

    System.out.println("8. Exit");

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
      startPage();
    }

    if(selectedOption == 1) {
      Listing.createNewListing();
    }else if(selectedOption == 2) {
      Listing.viewYourListings();
    }else if(selectedOption == 3){
      System.out.println("Updating Listing");
      Listing.updateListing();
    }else if(selectedOption == 4){
      System.out.println("Cancel a Booking");
      Listing.cancelBooking();
    } else if(selectedOption == 5){
      System.out.println("Delete a Listing");
      Listing.removeListing();
    }else if(selectedOption == 6){
      System.out.println("Review a Renter");
      Review.reviewRenter();
    } else {
      System.out.println("Invalid input... Try again!!!");
      System.out.println("Redirecting to LogIn page menu....");
      try{Thread.sleep(1000);
      } catch (InterruptedException e) { e.printStackTrace();}
    }


  }
}
