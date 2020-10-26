import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * this class implements some of the functunalities of an hotel booking site,
 * that allows for personalized search methodologies.
 * this class provide the users with the ability to get a list of hotels based on different parameters
 */
public class BoopingSite {


    private Hotel[] hotelDataset;

    // --------------- BoopingSite Constructor ---------------

    /**
     * This constructor receives as parameter a string, which is
     * the name of the dataset (e.g. "hotels dataset.txt"). This parameter can later be passed to the
     * HotelDataset.getHotels(String fileName) function
     *
     * @param name string
     */
    public BoopingSite(String name) {

        this.hotelDataset = HotelDataset.getHotels(name);
        // This parameter can later be passed to the HotelDataset.getHotels(String fileName) function

    }


    // ---------------getHotelsInCityByRating related methods ---------------

    /**
     * sort the array by city
     *
     * @param city string, wish to sort data according to this city
     * @return hotel array with which are only at this city
     */
    private Hotel[] sortByCity(String city, Hotel[] hotelArrayToFilter) {

        ArrayList<Hotel> hotelsInCity = new ArrayList<Hotel>();
        for (Hotel hotel : hotelArrayToFilter) {
            if (hotel.getCity().equals(city)) {
                hotelsInCity.add(hotel);
            }
        }
        return hotelsInCity.toArray(new Hotel[0]);
    }


    /**
     * This method returns an array of hotels located in the given city,
     * sorted from the highest star-rating to the lowest. Hotels that have the
     * same rating will be organized according to the alphabet order of the hotel’s (property) name.
     * In case there are no hotels in the given city, this method returns an empty array.
     *
     * @param city string
     * @return array of hotels
     */
    public Hotel[] getHotelsInCityByRating(String city) {

        Hotel[] sortedByCity = sortByCity(city, this.hotelDataset);
        ArrayList<Hotel> hotelsList = new ArrayList<Hotel>(Arrays.asList(sortedByCity));
        CompareRating compare = new CompareRating();
        Collections.sort(hotelsList, compare);
        return hotelsList.toArray(new Hotel[0]);
    }

    // ---------------getHotelsByProximity related methods -------------

    /**
     * This method
     * returns an array of hotels, sorted according to their (euclidean) distance from the given geographic
     * location, in ascending order. Hotels that are at the same distance from the given location are
     * organized according to the number of points-of-interest for which they are close to
     * (in a decreasing order). In case of illegal input, this method returns an empty array.
     *
     * @param latitude  double
     * @param longitude double
     * @return array of hotels
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        // check if params are valid

        if (!isCoordinatesValid(latitude, longitude)) { //if params are not vaild - return an empty array
            Hotel[] empty = new Hotel[0];
            return empty;
        }
        HotelCompareDistance compare = new HotelCompareDistance(latitude, longitude);
        Hotel[] newHotel = this.hotelDataset.clone();
        Arrays.sort(newHotel, compare);
        return newHotel;
    }

    /**
     * check Latitude -90 <= x <= 90 and check Longitude -180 <= x <= 180
     *
     * @param latitude  double
     * @param longitude double
     * @return true if both are valid, false otherwise
     */
    private boolean isCoordinatesValid(double latitude, double longitude) {
        // check Latitude -90 <= x <= 90 and check Longitude -180 <= x <= 180
        return (((-90 <= latitude) && (latitude <= 90)) && ((-180 <= longitude) && (longitude <= 180)));
    }

    // ---------------getHotelsInCityByProximity related methods -------------

    /**
     * returns an array of hotels in the given city,sorted according to their(euclidean)distance
     * from the given geographic location, in ascending order.Hotels that are at the same distance from the
     * given location are organized according to the number of points-of-interest for which they are close
     * to(in a decreasing order). In case of illegal input, this method returns an empty array
     *
     * @param city      string
     * @param latitude  double
     * @param longitude double
     * @return hotel array
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {

        Hotel[] sortedByProximity = getHotelsByProximity(latitude, longitude);
        return sortByCity(city, sortedByProximity);
    }

}
