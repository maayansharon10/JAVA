import oop.ex3.searchengine.Hotel;

import java.util.Collections;
import java.util.Comparator;

/**
 * this class compares two hotels accoording to their distance from a certain location
 */
public class HotelCompareDistance implements Comparator<Hotel> {

    private double latitude;
    private double longitude;

    /**
     * constructor for this class
     * @param latitude - double
     * @param longitude - double
     */
    public HotelCompareDistance(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * return which hotel is in a greater distance
     * @param hotel1 hotel item
     * @param hotel2 hotel item
     * @return -1 if hotel2 is in greater distance than hotel1, 0 if both in same distance,
     * 1 if hotel1 is in greater distance than hotel2
     */
    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {

        double distanceToHotel1 = getDistanceBetweenCordinatesAndHotel(this.latitude, this.longitude,
                hotel1);
        double distanceToHotel2 = getDistanceBetweenCordinatesAndHotel(this.latitude, this.longitude,
                hotel2);

        if (distanceToHotel1 < distanceToHotel2) {
            return -1;
        } else if (distanceToHotel1 == distanceToHotel2) {
            // return according to number of point of interest
            return hotel2.getNumPOI() - hotel1.getNumPOI();
        } else { //if distanceToHotel1 > distanceToHotel2
            return 1;
        }
    }

    /**
     * return the distance from a coordinate to hotel
     *
     * @param latitude  double
     * @param longitude double
     * @param hotel2    hotel type
     * @return return the distance from a coordinate to hotel
     */
    public double getDistanceBetweenCordinatesAndHotel(double latitude, double longitude, Hotel hotel2) {

        double hotel2Latitude = hotel2.getLatitude();
        double hotel2Longitude = hotel2.getLongitude();

        return Math.sqrt(Math.pow((latitude - hotel2Latitude), 2) +
                Math.pow((longitude - hotel2Longitude), 2));
    }


}
