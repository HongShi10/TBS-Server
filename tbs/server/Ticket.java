package tbs.server;

public class Ticket {
    /* This class stores all the information for a ticket like ticketID and the performance as well as the seat the
    ticket is for and the price of the ticket */

    private String _ticketID;
    private String _performanceID;
    private int _rowNumber;
    private int _seatNumber;
    private int _price;

    public Ticket(String performanceID,String ticketID,int rowNumber, int seatNumber,int price){//Constructor for Ticket
        _performanceID = performanceID;
        _ticketID = ticketID;
        _rowNumber = rowNumber;
        _seatNumber = seatNumber;
        _price = price;
    }
    public boolean findPerformance(String performanceID){//Checks if the performanceID is the same (case-insensitively)
        return _performanceID.equalsIgnoreCase(performanceID);
    }

    public String getTicket(){//Getter for ticketID
        return _ticketID;

    }

    public int getPrice(){//Getter for price
        return _price;
    }
}
