package tbs.server;

import java.util.List;

public class Performance {
    /* This class contains all the information a performance has performanceID,actID,seat prices,list of seats available
        and seat dimension. It also checks if a seat is occupied and if it isn't it occupies it once the method gets
        called. There is also a method that calls and returns the price of a certain seat */
    private String _performanceID;
    private String _actID;
    private int _cheapSeatsPrice;
    private int _premiumSeatsPrice;
    private String _startTime;
    private List<String> _seatList;
    private int _seatDimension;

    public Performance(String actID, int cheapSeatsPrice, int premiumSeatsPrice, int numOfPerformance,
                       String startTime, List<String> seatList, int seatDimension) {//Constructor for performance
        _actID = actID;
        _cheapSeatsPrice = cheapSeatsPrice;
        _premiumSeatsPrice = premiumSeatsPrice;
        _performanceID = actID + "Performance" + (numOfPerformance + 1);
        _startTime = startTime;
        _seatList = seatList;
        _seatDimension = seatDimension;

    }

    public String getPerformance() {//Getter for performanceID
        return _performanceID;
    }

    public boolean findActID(String actID) {//Checks if the performance is in the specified act
        return _actID.equalsIgnoreCase(actID);
    }

    public List<String> getSeatList() {//Returns the list of seats
        return _seatList;
    }

    public String getStartTime(){//Getter for the start time
        return _startTime;
    }

    /* This method takes the rowNumber and seatNumber and returns a boolean value. It checks if the seat is occupied or
       not and if it is occupied it returns false otherwise it replaces the seat with a null value and returns true */
    public boolean occupySeat(int rowNumber, int seatNumber) {
        int count = 0;//Counter so it knows which index of the seatList to access
        for (int i = 1; i <= _seatDimension; i++) {
            for (int j = 1; j <= _seatDimension;j++) {
                if (i == rowNumber && j == seatNumber) {//If RowNum and seatNumber found it checks if the seat is taken
                    if(_seatList.get(count)==null){//Seat is taken if its equal to null so if its taken return false
                        return false;
                    }
                    else {//If its not taken it sets the current seat to null and returns true;
                        _seatList.set(count, null);
                        return true;
                    }
                } else {
                    count++;//Increments the counter so it goes to the next seat
                }
            }
        }
        return false;//Returns false if the seat is not found
    }
    //This method finds if the seat price is premium or cheap
    public int issuePrice(int rowNumber){
        //Premium seats are the first 1/2 of the rows from row 1 so it checks if the row number is less than 1/2
        if(rowNumber <= (_seatDimension/2)){//If row is in the first half of rows it returns the premium price
            return _premiumSeatsPrice;
        }
        else{//Else returns cheap price
            return _cheapSeatsPrice;
        }
    }

    public int getSeatDimension() {//Getter for seat dimension
        return _seatDimension;
    }

    public boolean checkPerformanceID(String performanceID){//checks if the performance ID entered is equivalent
        return _performanceID.equalsIgnoreCase(performanceID);
    }
}

