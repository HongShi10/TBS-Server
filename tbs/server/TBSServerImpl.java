package tbs.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TBSServerImpl implements TBSServer {

    private List<Theatre> _theatres = new ArrayList<Theatre>(); //Stores all the theatre objects created in a List
    private List<Artist> _artists = new ArrayList<Artist>(); //Stores all the artist objects created in a List
    private List<Act> _acts = new ArrayList<Act>(); //Stores all the act objects created in a List
    private List<Performance> _performances = new ArrayList<Performance>(); //Stores all the performance objects created in a List
    private List<Ticket> _tickets = new ArrayList<Ticket>(); //Stores all the ticket objects created in a List

    @Override
    public String initialise(String path) {
        /*This method checks if the file format is correct and if it is correct is passes the information from the file
        into a new object called theatre that contains the different pieces of information. If the format is incorrect
        it returns an error message stating what went wrong */

        Format file = new Format();
        String line;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));//Makes the file readable
            line = bufferedReader.readLine();//Reads the line and converts it into a String

            while (line != null) {//loop that runs while there is text in the next line
                if ((file.checkFormat(line))) {//Calls checkFormat to see if 'THEATRE' appears in the each line
                    String[] info = file.readFileInfo(line);// calls method that extracts the information from the file
                    //Changes rows and floor area to integers and stores them in a new object of type theatre
                    Theatre theatre = new Theatre(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    _theatres.add(theatre);//Adds theatre objects to _theatre field
                }
                else {
                    return ("ERROR File format is incorrect");//Returns the error message
                }
                line = bufferedReader.readLine();//Reads the next line
            }
        }    catch (NumberFormatException e) {//Catches wrong format exception
            return ("ERROR Format Incorrect");
        }
        catch(FileNotFoundException e) {//Catches the exception if there is no file found in that location

            return ("ERROR File is not found");
        } catch(IOException e) {//Catches an exception that has a problem with the input

            return("ERROR Input/Output Exception caught");
        }
        return ("");
    }

    @Override
    public List<String> getTheatreIDs() {
        /*This method requests a list of theatreIDs and if there are no theatres then it returns an error message
        saying so */

        List<String> theatreID = new ArrayList<String>();
        if (_theatres == null || _theatres.size() == 0) {//Checks if there are any theatres
            return theatreID;
        }
        for(int i =0;i<_theatres.size();i++){
            theatreID.add(_theatres.get(i).getTheatreId());//Adds the theatreIDs to the new output list
        }
        Collections.sort(theatreID,Collator.getInstance());//Collator.getInstance makes the sort case insensitive
        return theatreID;
    }

    @Override
    public List<String> getArtistIDs() {
        /*This method requests a list of ArtistIDs and if there are no artists then it returns an error message
        saying so */

        List<String> artistID = new ArrayList<String>();
        if (_artists == null || _artists.size() == 0) {//If there are no artists then return a error
            return artistID;
        }
        for (int i = 0; i< _artists.size(); i++){
            artistID.add( _artists.get(i).getArtistId());//Adds the artistID to the new output list
        }
        Collections.sort(artistID,Collator.getInstance());//Sorts alphabetically case insensitively
        return artistID;
    }

    @Override
    public List<String> getArtistNames() {
        /*This method requests a list of Artist names and if there are no artists then it returns an error message
        saying so */

        List<String> artistNames = new ArrayList<String>();
        if (_artists == null || _artists.size() == 0) {//Checks if there are any artist present
            return artistNames;
        }
        for (int i = 0; i< _artists.size(); i++){
            artistNames.add(_artists.get(i).getArtistName());//Adds the artistNames to the new output list
        }
        Collections.sort(artistNames, Collator.getInstance());//sorts the list into alphabetical order case insensitive
        return artistNames;
    }

    @Override
    public List<String> getActIDsForArtist(String artistID) {
        List<String> actIDs = new ArrayList<String>();
        /*This method requests a list of actIDs for a specified artistID and if there are no artists or no actIDs for
          that specified artist then it returns an error message saying so */

        if (artistID == null || artistID.length() == 0) {//Checks if there is a artistID present
            actIDs.add("ERROR No artistID inputted");
            return actIDs;
        }
        for (int i=0;i<_acts.size();i++) {
            if (_acts.get(i).findArtist(artistID)) {//Finds all the acts with the same artist name
                actIDs.add(_acts.get(i).getAct());//Adds the actIDs to the new output list
            }
        }
            return actIDs;
    }

    @Override
    public List<String> getPeformanceIDsForAct(String actID) {
        /*This method requests a list of performanceIDs for a specified actID, if there are no acts or no performanceIDs
          for that specified actID then it returns an error message saying so */

        List<String> performanceIDs = new ArrayList<String>();
        if (actID == null || actID.length() == 0) {//Checks if there is a artistID present
            performanceIDs.add("ERROR No actID inputted");
            return performanceIDs;
        }
        for (int i=0;i<_performances.size();i++){
            if(_performances.get(i).findActID(actID)){//Finds all the performances with the same actID
                performanceIDs.add(_performances.get(i).getPerformance());//Adds the performanceIDs to the output list
            }
        }
            return performanceIDs;
    }

    @Override
    public List<String> getTicketIDsForPerformance(String performanceID) {
        /*This method requests a list of TicketIDS for a specified performance and if there are no Tickets or no
          ticketIDs for that specified performanceID then it returns an error message saying so */

        List<String> ticketIDs = new ArrayList<String>();
        if (performanceID == null || performanceID.length() == 0) {//Checks if there is a artistID present
            ticketIDs.add("ERROR No performanceID inputted");
            return ticketIDs;
        }
        for (int i=0;i<_tickets.size();i++){
            if(_tickets.get(i).findPerformance(performanceID)){//Finds all the tickets with the same performanceID
                ticketIDs.add(_tickets.get(i).getTicket());// Adds the ticketIDs to the final output list
            }
        }
            return ticketIDs;
    }

    @Override
    public String addArtist(String name) {
        /*This method lets the user input an artist name and adds it to the server. If the name is empty or a duplicate
        name (case insensitive) then it also returns an error */

        if (name == null || name.length() == 0) {//Checks if there is a name present that is inputted
            return ("ERROR no name inputted");
        }
        else {
            for(int i=0;i<_artists.size();i++){//Checks for duplicate names
                if(_artists.get(i).isNameEquals(name)){
                    return ("ERROR Duplicate Name");
                }
            }
            String artistID = "Artist" + (_artists.size()+1);//Creates the artistID with Artist+ (number of artists)
            Artist artist = new Artist(artistID, name);//Creates the artist with the ID and name initialised
            _artists.add(artist);//Adds the artist to the _artist field in the TBSServerImpl
            return (artistID);
        }
    }

    @Override
    public String addAct(String title, String artistID, int minutesDuration) {
        /*This method requests the artist ID, Title of the act and the duration of the act and returns a actID. If no
         title is added,duration is less than or equal to 0 or no artistID exists it will return a error */

        if(title==null || title.length()==0){//Checks if a title was inputed
            return("ERROR no title added");
        }
        else if (minutesDuration<=0 || Integer.bitCount(minutesDuration)==0){//Checks if the duration of the act is more than 0
            return("ERROR minutesDuration is 0 or negative");
        }
        for (int i = 0; i < _artists.size(); i++) {
            if (_artists.get(i).isArtistIDEquals(artistID)) {//Checks if there is the artistID present
                for(int j=0;j<_acts.size();j++){//This loop checks if the title for an act already exists for the artist
                    if(_acts.get(j).checkTitle(title) && _acts.get(j).findArtist(artistID)){
                        return ("ERROR title of act already exists for this artist");//Returns the error message
                    }
                }
                Act act = new Act(artistID, minutesDuration, title);//Creates a new act with fields initialised
                _acts.add(act);//Adds the new act to the list of acts in the server
                return act.getAct();
            }
        }
        return ("ERROR artistID not found");
    }

    @Override
    public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr,
                                      String cheapSeatsStr) {
        /*This method takes actID, theatreID, start Time, and the prices of seats as inputs. It creates a performance
          for a certain actID and also creates a list of seats that are available using the theatreID. It returns a
          error if no theatreID or no actID exists or if the formats of the start time and prices are incorrect.*/

        int numbOfPerformances = 0;//This is used to create the performanceID i.e performance1,...,performance3,...
        int count = 0; //Counter to find the index in which theatre object in _theatre to use
        int check = 0; //Checker to find if the TheatreID and actID exists

        for(int i = 0;i<_theatres.size();i++){//Loop to find if theatreID exists
            if (_theatres.get(i).checkTheatreID(theatreID)){
                count = i;//Gets the index position to access the _theatres list
                check ++;
                break;
            }
        }
        if(check == 0){//If the no theatreID exists return an ERROR message
            return ("ERROR theatreID not found");
        }
        List<String> seatList = _theatres.get(count).createSeatList();//Creates the list of seats
        int seatDimension = _theatres.get(count).getSeatingDimension();//Gets the seating dimension
        check = 0;
        for(int k=0;k<_acts.size();k++){//Loop that checks if actID is present
            if(!_acts.get(k).checkActID(actID)){
                check++;
            }
        }
        if(check == _acts.size()){//If the no actID exists return an ERROR message
            return ("ERROR actID not found");
        }
        int premiumPriceInt;
        int cheapSeatsInt;
        if(premiumPriceStr==null || premiumPriceStr.length()==0 || cheapSeatsStr==null || cheapSeatsStr.length()==0){
            return ("ERROR no price inputted"); //Checks if any prices are inputed
        }
        //Checks price format by calling priceFormatCheck method from the Format class
        if(Format.priceFormatCheck(premiumPriceStr,cheapSeatsStr)) {
            try {//Try statement to catch the exceptions
                premiumPriceInt = Integer.parseInt(premiumPriceStr.substring(1));
                //Creates a substring and changes price to an integer
                cheapSeatsInt = Integer.parseInt(cheapSeatsStr.substring(1));
            } catch (NumberFormatException e) { //Catches exception if characters after '$' are not parsable
                return ("ERROR price format incorrect");
            }
        }
        else{
            return ("ERROR price format incorrect");//Returns Error for what went wrong
        }
        if(!(Format.checkTimeFormat(startTimeStr))) {//Checks if the start Time format is correct
            return ("ERROR startTime format incorrect");
        }
        //Loop to check how many performances were made for the particular actID
        for (int j=0;j<_performances.size();j++){
            if(_performances.get(j).findActID(actID)){//Checks if the each performance has the corresponding actID
                numbOfPerformances++;
            }
        }
        //Creates a new performance with all the parameters added
        Performance performance = new Performance(actID,cheapSeatsInt,premiumPriceInt,numbOfPerformances,
                startTimeStr,seatList,seatDimension);
        _performances.add(performance);//Adds the performance to the server
        return performance.getPerformance();
    }

    @Override
    public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
        /* This method takes the performanceID and the seat as input. It returns a ticket ID as well as makes the
        inputted seat taken. It will return a error if the performanceID isn't found or if the seat was already taken.*/

        int check=0;//Checker for if there exists the corresponding performanceID
        int count = 0;//Counter to get the index to access in the performance list for the correct performance
        for (int i=0;i<_performances.size();i++){ //Checks if the performanceID exists
            if(_performances.get(i).checkPerformanceID(performanceID)){
                count = i;
                check ++;
                break;
            }
        }
        if(check == 0){//Returns the error message
            return ("ERROR performanceID not found");
        }
        //Calls the occupySeat method in the performance class and if it returns false that means a seat is taken or
        //makes the seat taken if it returns true
        if(!_performances.get(count).occupySeat(rowNumber,seatNumber)){
            return ("ERROR seat taken or does not exist");
        }
        String ticketID = "Ticket"+(_tickets.size()+1);//Creates the ID by finding how many tickets there are already
        int price = _performances.get(count).issuePrice(rowNumber);//Finds if the seat is cheap or premium price

        Ticket ticket = new Ticket(performanceID,ticketID,rowNumber,seatNumber,price);//Creates the ticket
        _tickets.add(ticket);//Adds the ticket to the server
        return ticketID;
    }

    @Override
    public List<String> seatsAvailable(String performanceID) {
        /* This method takes the performanceID and returns all the seats that remain. It returns an error if no such
        performanceID exists or returns a empty list if all seats are taken */

        List<String> seatListFormatted = new ArrayList<String>();
        int check=0;//Checker for if there exists the corresponding performanceID
        int count = 0;//Counter to get the index to access in the performance list for the correct performance
        for (int i=0;i<_performances.size();i++){//Checks if the performanceID exists
            if(_performances.get(i).checkPerformanceID(performanceID)){
                count = i;
                check ++;
                break;
            }
        }
        if(check == 0){//If no performanceID exists return a Error message
            seatListFormatted.add("ERROR performanceID not found");
            return seatListFormatted;
        }
        List<String> seatList = _performances.get(count).getSeatList();//Gets all seats
        int seatDimension = _performances.get(count).getSeatDimension();//Gets the number of rows/cols of seats
        int seat=0; //Seat counter for the loop so it can access the correct element in the seat list
        //Loop that checks if an element in seatList is equal to null that means its taken if its not taken that means
        //it gets added to the new seatListFormatted
        for(int i=1;i<=seatDimension;i++){
            for(int j=1;j<=seatDimension;j++){
                if(seatList.get(seat) == null){
                    seat++;
                }
                else{
                    //Adds in a string format row + '\t' + col
                    seatListFormatted.add(Integer.toString(i)+"\t"+Integer.toString(j));
                    seat++;
                }
            }
        }
        return seatListFormatted;//Returns the list of seats that are still available
    }

    @Override
    public List<String> salesReport(String actID) {
        /*This method takes actID and returns a report in which shows the performanceID start Time and the total sales
        of each performance in every act. It returns an error if no actID exists */

        List<String> report = new ArrayList<String>();
        for(int i=0;i<_performances.size();i++){//Loops through all performances available
            if(_performances.get(i).findActID(actID)){//Finds the performances which are under the same act
                int totalPrice = 0;//Sets total price of each performance to 0 initially
                int ticketCount = 0;//Counter for amount of tickets sold
                String performanceID = _performances.get(i).getPerformance();//Gets the performance from the list
                String startTime = _performances.get(i).getStartTime();//Gets the startTime of the performance
                for (int j=0;j<_tickets.size();j++){//Loop through each ticket
                    if(_tickets.get(j).findPerformance(performanceID)){//Finds the tickets with the same performanceID
                        ticketCount++;//Increments ticket count
                        totalPrice = totalPrice + _tickets.get(j).getPrice();//Adds the prices of tickets together
                    }
                }//Adds the performanceId start time and total sales to the list
                report.add(performanceID+'\t'+startTime+'\t'+Integer.toString(ticketCount)+'\t'+'$'
                        +Integer.toString(totalPrice));
            }
        }
        return report;
    }

    @Override
    public List<String> dump() {
        return null;
    }
}


