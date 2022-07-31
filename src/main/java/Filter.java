import java.util.Date;

public class Filter {

  String city;
  String country;
  String apt_name;
  String postal_code;
  Date start_date;
  Date end_date;
  String room_type;
  Integer price_low;
  Integer price_high;
  Boolean has_wifi;
  Boolean has_kitchen;
  Boolean has_washer;
  Boolean has_dryer;
  Boolean has_ac;

  public Filter() {
    this.city = city;
    this.country = country;
    this.apt_name = apt_name;
    this.postal_code = postal_code;
    this.start_date = start_date;
    this.end_date = end_date;
    this.room_type = room_type;
    this.price_low = price_low;
    this.price_high = price_high;
    this.has_wifi = has_wifi;
    this.has_kitchen = has_kitchen;
    this.has_washer = has_washer;
    this.has_dryer = has_dryer;
    this.has_ac = has_ac;

  }
  public boolean filter_by_address(String city, String country, String apt_name) {
    if (this.city.equalsIgnoreCase(city) && this.country.equalsIgnoreCase(country) &&
            this.apt_name.equalsIgnoreCase(apt_name)) {
      return true;
    }
    return false;
  }

  public

}
