package tbs.server;

public class Act {
    /*This class contains all the information an act has for example artistID and actID as well as the time and title of
    the act */

    private String _actID;
    private String _artistID;
    private int _time;
    private String _title;

    public Act(String artistID,int time,String title){//Constructor for the Act class
        _actID = artistID+title;
        _artistID = artistID;
        _time = time;
        _title = title;
    }
    public boolean findArtist(String artistID){//Checks if the ID inputed is the same for this instance of the object
        return artistID.equalsIgnoreCase(_artistID);//Checks case insensitively
    }

    public String getAct(){//Getter for _actID
        return _actID;

    }

    public boolean checkTitle(String title){
        return _title.equalsIgnoreCase(title);
    }
    public boolean checkActID(String actID){//Checks if the actID is the same as the one inputed
        return _actID.equalsIgnoreCase(actID);//Checks case insensitively
    }

}
