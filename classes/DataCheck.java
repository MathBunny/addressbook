//import java.util.*;

/**This application provides an AddressBook functionality, permitting the user to add information about individuals, save the information and retrieve it later.
  * 
  * @author Horatiu Lazu
  * @version 1.0.0.0, March 4 2014
  */
public class DataCheck //HORATIU REMEMBER THAT YOU CAN ONLY HAVE SELECT THINGS IN HERE!!!
{
  /**This method verifies if the phone number given is valid, and if yes it will sort it accordingly.
    * The first if statement will verify the length, and if so it will then verify if the dashes were adding in the correct location using several other if statements.
    * The following if statements works such that if the length is not 10 then it will return -1 which will be an error number.
    * The following if statements works such that it will only add the character if it's not a space or a dash - in a for loop to go through each index.
    * @param data String, used to input the current phone number.
    * @param temp String, used to add the final output String.
    * @param e NumberFormatException in case the input is not a number.
    * @throws NumberFormatException, used to verify if the input is a number.
    * @returns boolean Used to indicate if the phoneCheck was valid.
    */
  public static boolean phoneCheck(String data){
    String temp = ""; 
    if (data.length() > 10){ //416-590-9630
      if ((data.charAt(3) == ' ' && data.charAt(7) != ' ') || (data.charAt(3) == '-' && data.charAt(7) != '-'))
        return false;
      else
        if (data.charAt(3) != ' ' && data.charAt(3) != '-')
        return false;
    }
    for(int i =0 ; i < data.length(); i++)
      if (data.charAt(i) != ' ' && data.charAt(i) != '-')
      temp+=data.charAt(i);
    else if ((data.charAt(i) == ' ' && i != 3) && data.charAt(i) == ' ' && i != 7 || ((data.charAt(i) == '-' && i != 3) && data.charAt(i) == '-' && i != 7))
      return false;
    if (temp.length() != 10)
      return false;
    try{Long.parseLong(temp);} catch(NumberFormatException e) { return false;}
    return true;
  }
  
  /**This method verifies if the email is valid within the input field, and if yes it will return a true.
    * The first for loop essentially processes through every possible value of i - which is the size of the input String.
    * The following if statement will then verify if the symbol @ is present, and if it has already been deemed valid.
    * The if statement further down would verify if there is any content beyond the @ sign.
    * The else would trigger with a nested if, only if it is valid and @ is present.
    * The if statement will verify if a non-alphanumeric value is present.
    * The last if statement will finally return the value that is required if the @ symbol was found or not.
    * @param data String, to represent the input information.
    * @param isValid boolean, to represent if the @ is found, and if it is found twice then state that it is illegal.
    * @returns boolean Indicates if the email is valid.
    */
  public static boolean fixEmail(String data){
    boolean isValid = false;
    for(int i = 0;  i < data.length(); i++){
      if (data.charAt(i) == '@' && isValid == false){
        isValid = true;
        if (data.length() - 1 == i)
          return false;
        if (i == 0)
          return false;
      }
      else 
        if (data.charAt(i) == '@' && isValid == true)
        return false;
      if (data.charAt(i) < 46 || data.charAt(i) == 47 || ((data.charAt(i) >= 58 && data.charAt(i) <= 63)) || (data.charAt(i) >= 91 && data.charAt(i) <= 96) || (data.charAt(i) >= 123))
        return false;
    }
    if (isValid == false)
      return false;
    else
      return true;
  }
}