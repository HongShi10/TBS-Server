package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
    /* This class stores all the information the theatre has like theatreID, seating dimension and floor area. it also
    creates a list of seats for the theatre using the seating dimension */

    private String _id;
    private int _seatingDimension;
    private int _floorArea;

    public Theatre(String id,int rows,int floorArea){//Constructor for theatre
        _seatingDimension = rows;
        _floorArea = floorArea;
        _id = id;
    }

    public String getTheatreId(){//Getter for theatreID
        return _id;
    }
    //This method creates a list of seats by using the seating dimension in a nested for loop
    public List<String> createSeatList(){
        List<String> seatList = new ArrayList<String>();
        for(int i=0;i<_seatingDimension;i++){
            for(int j=0;j<_seatingDimension;j++){
                seatList.add("Row"+(i+1)+"Seat"+(j+1));//Creates a string that indicates RowNumb and seatNum and adds it
            }
        }
        return seatList;
    }
    public int getSeatingDimension(){//Getter for seating dimension
        return _seatingDimension;
    }

    public boolean checkTheatreID(String theatreID){//Checks if the theatreID is the same for the id of this theatre
        return _id.equalsIgnoreCase(theatreID);//Makes the check case insensitive
    }
}

