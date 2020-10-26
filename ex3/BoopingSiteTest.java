//maayan

import oop.ex3.searchengine.Hotel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class should test all the functionalities mentioned in the ex' description
 */
public class BoopingSiteTest {


    private BoopingSite hotelsData1;
    private BoopingSite hotelsData2;
    private Hotel[] emptyHotel = new Hotel[0];
    private String VALID_CITY1 = "manali";
    private String VALID_CITY2 = "bangalore";
    private String UNVALID_CITY = "london";


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


    @Before
    public void iniateHotel() {
        hotelsData1 = new BoopingSite("hotels_tst1.txt");
        hotelsData2 = new BoopingSite("hotels_tst2.txt");
    }
    // --------------- BoopingSite Constructor ---------------

    /**
     * test for BoopingSite constructor create a constructor and is not a null object
     */
    @Test
    public void testBoopingSiteConstructor() {
        // check constructor create an object.
        assertNotNull("object is null- constructor failed to create object", this.hotelsData1);
        assertNotNull("object is null- constructor failed to create object", this.hotelsData2);
    }

    // ---------------getHotelsInCityByRating related methods ---------------

    //• public Hotel[] getHotelsInCityByRating(String city).
    // This method returns an array of hotels located in the given city,
    // sorted from the highest star-rating to the lowest. Hotels that have the
    //same rating will be organized according to the alphabet order of the hotel’s (property) name.
    // In case there are no hotels in the given city, this method returns an empty array.

    /**
     * check illegal input - numbers -  return an empty list
     */
    @Test
    public void testGetHotelsInCityByRatingInvalidInput1() {
        //check ilegal input - numbers -  return an empty list
        Hotel[] sortedByCity = hotelsData1.getHotelsInCityByRating("1234");

        assertEquals("HotelsInCityByRating failed - ilegal input return an empty list",
                this.emptyHotel.length,sortedByCity.length);

    }


    /**
     * check illegal input - empty string -return an empty list
     */
    @Test
    public void testGetHotelsInCityByRatingInvalidInput2() {
        //check ilegal input - empty string -return an empty list

        Hotel[] sortedByCity = hotelsData1.getHotelsInCityByRating("");

        assertEquals("HotelsInCityByRating failed - ilegal input return an empty list",
                this.emptyHotel.length, sortedByCity.length);

    }


    /**
     * test getHotelsInCityByRating City1 - the array of hotels contains hotel from the given city
     */
    @Test
    public void testgetHotelsInCityByRatingCity1() {
        Hotel[] sortedByCity = hotelsData1.getHotelsInCityByRating(this.VALID_CITY1);
        for (Hotel hotel : sortedByCity) {
            assertEquals("HotelsInCityByRating failed - not the requested city",
                    this.VALID_CITY1, hotel.getCity());
        }
    }


    /**
     * test getHotelsInCityByRating City2 - the array of hotels contains hotel from the given city
     */
    @Test
    public void testgetHotelsInCityByRatingCity2() {
        Hotel[] sortedByCity = hotelsData1.getHotelsInCityByRating(this.VALID_CITY2);

        for (Hotel hotel : sortedByCity) {
            assertEquals("HotelsInCityByRating failed - not the requested city",
                    this.VALID_CITY2, hotel.getCity());
        }
    }


    /**
     * test getHotelsInCityByRating - Check array is sorted according to rating
     */
    @Test
    public void testgetHotelsInCityByRatingCheckRating1() {
        Hotel[] sortedByCity = hotelsData1.getHotelsInCityByRating(this.VALID_CITY2);

        for (int i = 0; i < sortedByCity.length; i++) {
            if (!(i == sortedByCity.length - 1)) {
                assertTrue("HotelsInCityByRating failed - not sorted according to rating",
                        sortedByCity[i].getStarRating() >= sortedByCity[i + 1].getStarRating());
            }
        }
    }


    /**
     * test getHotelsInCityByRating -check if sorted by rating and has the same rating, sort alphabetically
     * according to the property's name
            */
    @Test
    public void testgetHotelsInCityByRatingName() {
        Hotel[] sortedByCity = hotelsData1.getHotelsInCityByRating(this.VALID_CITY2);

        for (int i = 0; i < sortedByCity.length; i++) {
            if (!(i == 0)) {
                if (sortedByCity[i].getStarRating() == sortedByCity[i - 1].getStarRating()) {
                    //if the rating is the same, check by a-b-c
                    assertTrue("HotelsInCityByRating failed-not sorted alphabetically" +
                                    " (after rating is sorted)",
                            0 <= sortedByCity[i].getPropertyName().compareTo
                                    (sortedByCity[i + 1].getPropertyName()));
                }
            }
        }
    }


    /**
     * test getHotelsInCityByRating both by city name, rating, and if rating is the same, also
     */
    @Test
    public void testgetHotelsInCityByRatingIntegrate() {
        Hotel[] sortedByCity = hotelsData1.getHotelsInCityByRating(this.VALID_CITY2);
        for (int i = 0; i < sortedByCity.length; i++) {
            if (!(i == 0)) {
                assertTrue("getHotelsInCityByRating failed - star rating of i > i+1",
                        sortedByCity[i].getStarRating() >= sortedByCity[i + 1].getStarRating());
                if (sortedByCity[i].getStarRating() == sortedByCity[i - 1].getStarRating()) {
                    //if the rating is the same, check by a-b-c
                    assertTrue("HotelsInCityByRating failed-not sorted alphabetically " +
                                    "(after rating is sorted)",
                            0 <= sortedByCity[i].getPropertyName().
                                    compareTo(sortedByCity[i + 1].getPropertyName()));
                }
            }
        }
    }


    /**
     * test getHotelsInCityByRating - check func return an empty array if city doesnt eist
     * no hotel in given city
     */
    @Test
    public void testgetHotelsInCityByRatingCityDoesntExist() {
        Hotel[] sortedByCity = hotelsData2.getHotelsInCityByRating(this.UNVALID_CITY);

        assertEquals("when city is not in list, should return an empty hotelArray",
                this.emptyHotel.length, sortedByCity.length);
    }


    // ---------------getHotelsByProximity related methods -------------
    // • public Hotel[] getHotelsByProximity(double latitude, double longitude).
    // This method returns an array of hotels, sorted according to their (euclidean) distance from
    // the given geographic location, in ascending order.
    // Hotels that are at the same distance from the given location are organized according to the number
    // of points-of-interest for which they are close to (in a decreasing order). In
    // case of illegal input, this method returns an empty array


    /**
     * test GetHotelsByProximity - AscendingCheck1
     * check a hotel in a city with many hotels.
     */
    @Test
    public void testGetHotelsByProximityAscendingCheck1() {
        // check a hotel in a city with many hotels
        Hotel[] sortedByProximity = hotelsData1.getHotelsByProximity(12.9058293,
                77.5783209);// Aurick Hotel in Bangalore

        for (int i = 0; i < sortedByProximity.length; i++) {
            if (!(i == sortedByProximity.length - 1)) {
                assertTrue("getHotelsByProximity failed - distance of i is > i+1 from base",
                        getDistanceBetweenCordinatesAndHotel(12.9058293,
                                77.5783209, sortedByProximity[i]) <=
                                getDistanceBetweenCordinatesAndHotel(12.9058293,
                                        77.5783209, sortedByProximity[i + 1]));
            }
        }
    }


    /**
     * test GetHotelsByProximity - Ascending Check 2
     * check the city with the lowest latitude
     */
    @Test
    public void testGetHotelsByProximityAscendingCheck2() {
        // check the city with the lowest latitude
        Hotel[] sortedByProximity = hotelsData1.getHotelsByProximity(8.080476176,
                77.54327283);// Sparsa Resorts (Kanyakumari) in kanyakumari

        for (int i = 0; i < sortedByProximity.length; i++) {
            if (!(i == sortedByProximity.length - 1)) {
                assertTrue("getHotelsByProximity failed - distance of i is > i+1 from base",
                        getDistanceBetweenCordinatesAndHotel(8.080476176,
                                77.54327283, sortedByProximity[i]) <=
                                getDistanceBetweenCordinatesAndHotel(8.080476176,
                                        77.54327283, sortedByProximity[i + 1]));
            }
        }
    }


    /**
     * test getHotelsByProximity - POI Check
     * checks that hotels that are at the same distance from the given location are organized according to
     * the number of points-of-interest for which they are close to (in a decreasing order)
     */
    @Test
    public void testGetHotelsByProximityPOICheck() {
        Hotel[] sortedByProximity = hotelsData1.getHotelsByProximity(12.9058293,
                77.5783209);// Aurick Hotel in Bangalore

        for (int i = 0; i < sortedByProximity.length; i++) {
            if (!(i == sortedByProximity.length - 1)) {
                if (getDistanceBetweenCordinatesAndHotel(12.9058293,
                        77.5783209, sortedByProximity[i]) ==
                        getDistanceBetweenCordinatesAndHotel(12.9058293,
                                77.5783209, sortedByProximity[i + 1])) {
                    // if two hotels are at the same distance - check POI is in decreasing order
                    assertTrue("getHotelsByProximity failed- POI check",
                            sortedByProximity[i].getNumPOI() >=
                                    sortedByProximity[i + 1].getNumPOI());
                }
            }
        }
    }

    /**
     * test getHotelsByProximity - Invalid Latitude
     */
    @Test
    public void testGetHotelsByProximityInvalidLatitude() {


        assertEquals("latitude is out of range, should return an empty list",
                this.emptyHotel.length,
                        hotelsData1.getHotelsByProximity(-90.2, 77.5783209).length);

    }

    /**
     * test getHotelsByProximity - Invalid Longitude
     */
    @Test
    public void testGetHotelsByProximityInvalidLongitude() {

        assertEquals("longitude is out of range, should return an empty list",
                this.emptyHotel.length,
                        (hotelsData1.getHotelsByProximity(90.2, -180.5).length));

    }

    // ---------------getHotelsInCityByProximity related methods -------------
    //• public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude).
    //This method returns an array of hotels in the given city,
    // sorted according to their (euclidean) distance from the given geographic location,
    // in ascending order. Hotels that are at the same distance from the
    //given location are organized according to the number of points-of-interest for which they are
    // close to (in a decreasing order).
    // In case of illegal input, this method returns an empty array


    /**
     * test GetHotelsInCityByProximity - City Check 1
     * check array has only the requested city
     */
    @Test
    public void testGetHotelsInCityByProximityCityCheck() {
        //check array has only the requested city
        Hotel[] sortedByCityByProximity = hotelsData1.getHotelsInCityByProximity(this.VALID_CITY2,
                8.0829445, 77.5512055); //coordinates of Hotel Rajam

        for (Hotel hotel : sortedByCityByProximity) {
            assertEquals("getHotelsInCityByProximity failed - not the requested city",
                    this.VALID_CITY2, hotel.getCity());
        }
    }


    /**
     * test GetHotelsInCityByProximity - City Check2
     * if city is invalid - empty string - return an empty array
     */
    @Test
    public void testGetHotelsInCityByProximityCityCheck2() {
        //check array has only the requested city
        Hotel[] sortedByCityByProximity = hotelsData1.getHotelsInCityByProximity("",
                8.0829445, 77.5512055); //coordinates of Hotel Rajam

        assertEquals("getHotelsInCityByProximity failed - invalid  city - empty string ",
                    this.emptyHotel.length, sortedByCityByProximity.length);
    }


    /**
     * test GetHotelsInCityByProximity - City Check 3
     * if city is invalid - numbers in string - return an empty array
     */
    @Test
    public void testGetHotelsInCityByProximityCityCheck3() {
        //check array has only the requested city
        Hotel[] sortedByCityByProximity = hotelsData1.getHotelsInCityByProximity("bangalore123",
                8.0829445, 77.5512055); //coordinates of Hotel Rajam
        assertEquals("getHotelsInCityByProximity failed - city is invalid - numbers in string -" +
                        " return an empty array ",
                this.emptyHotel.length,sortedByCityByProximity.length);


    }


    /**
     * test GetHotelsInCityByProximity - City Check 4
     * if city is invalid - city not in dataset - return an empty array
     */
    @Test
    public void testGetHotelsInCityByProximityCityCheck4() {
        //check array - if city is invalid - city not in dataset
        Hotel[] sortedByCityByProximity = hotelsData1.getHotelsInCityByProximity("london",
                8.0829445, 77.5512055); //coordinates of Hotel Rajam
        assertEquals(" city not in dataset - return an empty array ",
                this.emptyHotel.length, sortedByCityByProximity.length);

    }


    /**
     * test GetHotelsInCityByProximity - Empty Data
     * if data set is empty return an empty array
     */
    @Test
    public void testGetHotelsInCityByProximityEmptyData() {
        //check array has only the requested city
        Hotel[] sortedByCityByProximity = hotelsData2.getHotelsInCityByProximity(this.VALID_CITY2,
                8.0829445, 77.5512055); //coordinates of Hotel Rajam

        assertEquals(" data set is empty return an empty array ",
                this.emptyHotel.length,  sortedByCityByProximity.length);
    }


    /**
     * test GetHotelsInCityByProximityCheckDistance
     * sorted according to their (euclidean) distance from the given geographic location
     * when coordinated are not related to this city
     */
    @Test
    public void testGetHotelsInCityByProximityCheckDistance1() {
        //check array had hotels sorted according to their distance, in ascending order
        Hotel[] sortedByCityByProximity = hotelsData1.getHotelsInCityByProximity(this.VALID_CITY2,
                8.0829445, 77.5512055); //coordinates of Hotel Rajam


        for (int i = 0; i < sortedByCityByProximity.length; i++) {
            if (!(i == sortedByCityByProximity.length - 1)) {
                assertTrue("getHotelsByProximity failed - distance of i is > i+1 from base",
                        getDistanceBetweenCordinatesAndHotel(8.0829445, 77.5512055,
                                sortedByCityByProximity[i]) <=
                                getDistanceBetweenCordinatesAndHotel(8.0829445, 77.5512055,
                                        sortedByCityByProximity[i + 1]));
            }
        }
    }


    /**
     * test GetHotelsInCityByProximity - Check Distance with empty dataset
     * sorted according to their (euclidean) distance from the given geographic location
     * when coordinated are not related to this city
     */
    @Test
    public void testGetHotelsInCityByProximityCheckDistance1Empty() {
        //check array had hotels sorted according to their distance,
        // in ascending order when data set is empty!!
        Hotel[] sortedByCityByProximity = hotelsData2.getHotelsInCityByProximity(this.VALID_CITY2,
                12.9058293, 77.5783209);// Aurick Hotel in Bangalore

        for (int i = 0; i < sortedByCityByProximity.length; i++) {
            if (!(i == sortedByCityByProximity.length - 1)) {
                assertTrue("getHotelsByProximity failed - distance of i is > i+1 from base",
                        getDistanceBetweenCordinatesAndHotel(12.9058293,
                                77.5783209, sortedByCityByProximity[i]) <=
                                getDistanceBetweenCordinatesAndHotel(12.9058293,
                                        77.5783209, sortedByCityByProximity[i + 1]));
            }
        }
    }


    /**
     * test GetHotelsInCityByProximityCheckDistance
     * sorted according to their (euclidean) distance from the given geographic location
     * when coordinated are from this city
     */
    @Test
    public void testGetHotelsInCityByProximityCheckDistance2() {
        //check array had hotels sorted according to their distance, in ascending order
        Hotel[] sortedByCityByProximity = hotelsData1.getHotelsInCityByProximity(this.VALID_CITY2,
                12.83811769, 77.40180736); //coordinates of Wonderla Resort

        for (int i = 0; i < sortedByCityByProximity.length; i++) {
            if (!(i == sortedByCityByProximity.length - 1)) {
                assertTrue("getHotelsByProximity failed - distance of i is > i+1 from base",
                        getDistanceBetweenCordinatesAndHotel(12.9058293,
                                77.5783209, sortedByCityByProximity[i]) <=
                                getDistanceBetweenCordinatesAndHotel(12.9058293,
                                        77.5783209, sortedByCityByProximity[i + 1]));
            }
        }
    }


    /**
     * test GetHotelsInCityByProximity - Check Distance
     * check hotels that are at the same distance from the
     * given location are organized according to the number of points-of-interest for which they are close
     * to (in a decreasing order).
     */
    @Test
    public void testGetHotelsInCityByProximityCheckPOI() {
        //check array had hotels sorted according to their distance, in ascending order
        Hotel[] sortedByCityByProximity = hotelsData1.getHotelsInCityByProximity(this.VALID_CITY2,
                8.0829445, 77.5512055); //coordinates of Hotel Rajam

        for (int i = 0; i < sortedByCityByProximity.length; i++) {
            if (!(i == sortedByCityByProximity.length - 1)) {
                if (getDistanceBetweenCordinatesAndHotel(8.0829445,
                        77.5512055, sortedByCityByProximity[i]) ==
                        getDistanceBetweenCordinatesAndHotel(8.0829445,
                                77.5512055, sortedByCityByProximity[i + 1])) {
                    // if two hotels are at the same distance - check POI is in decreasing order
                    assertTrue("etHotelsByProximity failed- POI check",
                            sortedByCityByProximity[i].getNumPOI() >=
                                    sortedByCityByProximity[i + 1].getNumPOI());
                }
            }
        }

    }

}
