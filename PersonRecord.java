import java.util.*;

/**This application provides an AddressBook functionality, permitting the user to add information about individuals, save the information and retrieve it later.
  * 
  * @author Horatiu Lazu
  * @version 1.0.0.0, March 4 2014
  */
public class PersonRecord
{
  /**first Private String, used to store the first name.*/
  private String first;
  /**last Private String, used to store the last name.*/
  private String last;
  /**email Private String, used to store the email.*/
  private String email;
  /**phone Private String, used to store the phone number.*/
  private String phone;
  /**recNumber Static integer, used to store the number of people that have been added to the database.*/
  static int recNumber = -1;
  /**This method is a constructor used to add particular aspects to the PersonRecord without using separate set methods.
    * @param firstN String, representing the first name.
    * @param lastN String, representing the last name.
    * @param phoneN String, representing the phone number.
    * @param emailG String, representing the email given.
    */
  public PersonRecord(String firstN, String lastN, String phoneN, String emailG){
    first = nameCheck(firstN);
    last = nameCheck(lastN); 
    phone = phoneFormat(phoneN);
    email = emailG; 
    recNumber++;
  }
  /**Class constructor of PersonRecord(), this constructor is used to reset the value of recNumber.*/
  public PersonRecord(){
    //recNumber++;
    recNumber = -1;
  }
  
  /**This method is a constructor used to add particular aspects to the PersonRecord without using separate set methods.
    * @param firstN String, representing the first name.
    * @param lastN String, representing the last name.
    * @param emailG String, representing the email given.
    */
  public PersonRecord(String firstN, String lastN, String emailG){
    first = nameCheck(firstN);
    last = nameCheck(lastN);
    email = emailG;
    recNumber++;
  }
  /**
   * This method is a constructor used to add particular aspects to the PersonRecord without using separate set methods.
   * @param firstN String, used to representing the first name.
   * 
   */
  public PersonRecord(String firstN){
    first = nameCheck(firstN);
    recNumber++;
  }
  /**This constructor for PersonRecord, which modifies the lastName and is distinguished by a boolean value.
    * @param lastN String, for the new lastName.
    * @param isLast boolean, used to distinguished the method.
    * 
    */
  public PersonRecord(String lastN, boolean isLast){
    last = nameCheck(lastN); 
    recNumber++;
  }
  /**This method sets the first name of the person, according to a variable.
    * @param newName String, used to set the new name.
    */
  public void setFirst(String newName){
    first = nameCheck(newName);
  }
  /**This method gets the last name of the person.
    */
  public String getLastName(){
    return last;
  }
  /**This method sets the email of the person, according to a variable.
    * @param newEmail String, used to set the new email.
    */
  public void setEmail(String newEmail){
    email = newEmail;
  }
  /**This method sets the last name of the person, according to a variable.
    * @param newLast String, used to set the new name.
    */
  public void setLast(String newLast){
    last = nameCheck(newLast);
  }
  /**This method sets the phone number of the person, according to a variable.
    * @param newPhone String, used to set the new phone number.
    */
  public void setPhone(String newPhone){ //Integer?
    phone = phoneFormat(newPhone);
  }
  /**This method gets the phone of the person.
    */
  public String getPhone(){
    return phone;
  }
  /**This method gets the first name of the person.
    */
  public String getFirstName(){
    return first;
  }
  /**This method gets the email of the person.
    */
  public String getEmail(){
    return email;
  }
  
  /**This method verifies the name, and returns a String with the proper name (capitalization).
    * The while loop ensures that the processing occurs for every single word within the input.
    * The if statement then verifies it the first letter of the word must be capitalized.
    * @param data String, used to representing the feeding data.
    * @param st StringTokenizer reference, used to split the information into sentences.
    * @param temp String, used to process the temporary token.
    * @return String, used to return the fixed name.
    */
  private String nameCheck(String data){ 
    StringTokenizer st = new StringTokenizer(data);
    String returnValue = "";
    while(st.hasMoreTokens()){
      String temp = st.nextToken();
      if (temp.charAt(0) >= 97)
        returnValue+= ((char)(temp.charAt(0) - 32) + ((temp.substring(1, temp.length())).toLowerCase())) + " ";
      else
        returnValue+= temp.charAt(0) + ((temp.substring(1, temp.length()))).toLowerCase() + "";
    }
    return returnValue;
  }
  
  /**This method will sort the phone number accordingly.
    * The if statement works such that if the length is not 10 then it will return -1 which will be an error number.
    * The if statement works such that it will only add the character if it's not a space or a dash - in a for loop to go through each index.
    * @param data String, used to input the current phone number.
    * @param temp String, used to add the final output String.
    * @param e NumberFormatException in case the input is not a number.
    * @throws NumberFormatException, used to verify if the input is a number.
    */
  private String phoneFormat(String data){
    if (data.equals(""))
      return "";
    String temp = "";
    for(int i = 0 ; i < data.length(); i++)
      if (data.charAt(i) != ' ' && data.charAt(i) != '-')
      temp+=data.charAt(i);
    return ((temp.substring(0, 3) + "-" + temp.substring(3, 6) + "-" + temp.substring(6, temp.length())));
  }
  
}