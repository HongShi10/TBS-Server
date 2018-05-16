package tbs.server;

public class Artist {
    /* This class stores all the information an Artist has like artistID and name. */

    private String _id;
    private String _name;

    public Artist(String id,String name){//Constructor for Artist
        _id = id;
        _name = name;
    }

    public String getArtistId(){//Getter for _id

        return _id;
    }

    public String getArtistName(){//Getter for _name

        return _name;
    }

    public boolean isNameEquals(String name) {//Checks if _name matches with the inputted name

        return (_name.equalsIgnoreCase(name));//Checks not case sensitive
    }

    public boolean isArtistIDEquals(String artistID){//Checks if the artistID is equal to this Artist

        return _id.equalsIgnoreCase(artistID);
    }
}

