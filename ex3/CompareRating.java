import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**
 * this class compare between two hotels according to their star ratings
 */
public class CompareRating implements Comparator<Hotel> {


    /**
     * compare hotels acoording to star rating. if star rating is the same, acoording to alphbetic order
     * @param hotel1 Hotel object
     * @param hotel2 Hotel object
     * @return -1 if hotel1 is greater hotel2,  if they're equal, 1 if 2 is greater than 1
     */
    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {
        if (hotel1.getStarRating() < hotel2.getStarRating()) {
            return 1;
        } else if (hotel1.getStarRating() == hotel2.getStarRating()) {
            return hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
        } else {
            return -1;
        }
    }
}
