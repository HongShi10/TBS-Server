package tbs.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Format {
    /* This class checks the format of files and reads the information of the file as well as checks if the format of
        specific strings are correct */

    //Checks if the first 7 is equal to 'THEATRE'
    public boolean checkFormat(String line) {
        String theatreNameCheck = line.substring(0, 7);//Creates a substring of the first 7 characters
        return (theatreNameCheck.equals("THEATRE"));
    }

    /* readFileInfo puts theatre id rows/cols and floor area into a string array where the first element is Theatre ID
       and the second element is the rows/cols and lastly floor area */

    public String[] readFileInfo(String line) {
        String[] info = new String[3];
        int count;
        int j = 7;//Starts fr om index 7 as first 7 letters are THEATRE and don't need to be used

        for (int i = 0; i < 3; i++) {
            j++;
            if (i == 0) {
                while (line.charAt(j) != '\t') { //statement that puts theatreID into the string array
                    j++;
                }
                info[i] = line.substring(8, j);//Creates a String that contains the theatreID
                continue;
            }
            if (i == 1) {//statement that puts Rows and Cols into the string array
                count = j;
                while (line.charAt(j) != '\t') {//Counter stops when space is found
                    j++;
                }
                info[i] = line.substring(count, j)//Creates a string that gets the seating dimension
                ;
            } else {//statement that puts floorArea into the string array
                while (line.charAt(j) == '\t') {
                    j++;
                }
                info[i] = line.substring(j, line.length());//Finds the string from end
            }
        }
        return info;
    }

    //Checks if the price format starts with a '$' symbol returns true if '$' is present
    public static boolean priceFormatCheck(String premiumPriceStr, String cheapSeatsStr) {
        return (premiumPriceStr.charAt(0) == '$' && (cheapSeatsStr.charAt(0) == '$'));
    }

    public static boolean checkTimeFormat(String startTimeStr) {
        //This method checks the format for the startTime to see if its correct
        if (startTimeStr.length() == 16) {
            try {//Creates a format for the date and time using 00:00 to 23:00
                SimpleDateFormat simpleDateFormat023 = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm");
                simpleDateFormat023.setLenient(false);//sets lenient to false so errors in date format will be caught
                //Creates a format for the date and time using 01:00 to 24:00
                SimpleDateFormat simpleDateFormat124 = new SimpleDateFormat("yyyy-mm-dd'T'kk:mm");
                simpleDateFormat124.setLenient(false);
                if (startTimeStr.substring(11, 13).equalsIgnoreCase("00")) {
                    //Checks if the date format is correct if midnight is 00:00 using the first format
                    simpleDateFormat023.parse(startTimeStr);
                } else if (startTimeStr.substring(11, 13).equalsIgnoreCase("24")) {
                    //Checks if the date format is correct if midnight is 24:00 using the second created format
                    simpleDateFormat124.parse(startTimeStr);
                } else {
                    simpleDateFormat124.parse(startTimeStr);//Checks if the date format is correct
                }

            } catch (ParseException r) {//Catches the exception if the format is incorrect
                return false;
            }
        }
        else {
            return false;
        }
        return true;
    }
}
