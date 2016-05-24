import java.awt.event.*;
//import java.util.*;
//import java.io.*;
import javax.swing.*;
import java.awt.*;

/**This application provides an AddressBook functionality, permitting the user to add information about individuals, save the information and retrieve it later.
  * 
  * @author Horatiu Lazu
  * @version 1.0.0.0, March 4 2014
  */

public class AddressBook extends JPanel implements ActionListener
{
  /**MAX_RECORD final int, used to represent the maximum number of records that can be utilized in this application.*/
  final int MAX_RECORD = 50;
  /**currentNum Static int, used to represent the current number that the database is at when displaying information.*/
  static int currentNum = 0;
  /**person Array of PersonRecord's, with the size of the MAX_RECORD.*/
  PersonRecord [] person = new PersonRecord[MAX_RECORD];
  /**firstNameLabelData JLabel reference, used to output the first name to the user.*/
  JLabel firstNameLabelData = new JLabel("First Name: Unknown|");
  /**lastNameLabelData JLabel reference, used to output the last name to the user.*/
  JLabel lastNameLabelData = new JLabel("Last Name: Unknown|");
  /**phoneLabelData JLabel reference, used to output the phone number to the user.*/
  JLabel phoneLabelData = new JLabel ("Phone Number: Unknown|");
  /**emailLabelData JLabel reference, used to output the email to the user.*/
  JLabel emailLabelData = new JLabel("Email: Unknown|"); //Consider making these global!
  /** currentNumLabel JLabel reference, used to output how many entries the user has made.*/
  JLabel currentNumLabel = new JLabel("Number of Entries: 0|"); //Global???
  /**currentTarget JLabel reference, which is used to output the current entry in the program.*/
  JLabel currentTarget = new JLabel("Current Entry: 0"); //FIX TO NONE!!!
  
  /**firstName JTextField reference, used to input the first name.*/
  JTextField firstName = new JTextField();
  /**lastName JTextField reference, used to input the last name.*/
  JTextField lastName = new JTextField();
  /**phone JTextField reference, used to input the phone number.*/
  JTextField phone = new JTextField();
  /**email JTextField reference, used to input the email.*/
  JTextField email = new JTextField();
  /**isSaved boolean value used to indicate if the file is saved or not.*/
  public boolean isSaved = true; 
  
  /**This method updates the information on the screen, in order to be up to date and ensure that the information matches correctly.
    * The first if statement does not update the information if it does not exist.
    * The second if statement is used to prevent a crash when it uses the .equals() method.
    * The ternary operators output Unknown is no value for the specific value is found.
    */
  public void updateEntries(){
    if (PersonRecord.recNumber == -1){
      currentNumLabel.setText("Number of Entries: 0|");
      firstNameLabelData.setText("First Name: Unknown|");
      lastNameLabelData.setText("Last Name: Unknown|");
      phoneLabelData.setText("Phone Number: Unknown|");
      emailLabelData.setText("Email: Unknown|");
      currentTarget.setText("Current Entry: 0");
    }
    else{
      currentNumLabel.setText("Number of Entries: " + (PersonRecord.recNumber + 1) + "|");
      firstNameLabelData.setText("First Name: "+ ((person[currentNum].getFirstName()).equals("") ? ("Unknown") : (person[currentNum].getFirstName())) + "|");
      lastNameLabelData.setText("Last Name: "+ ((person[currentNum].getLastName()).equals("") ? ("Unknown") : (person[currentNum].getLastName())) + "|");
      if (person[currentNum].getPhone() != null) //Weird VERIFY THIS PLEASE!!
        phoneLabelData.setText("Phone Number: "+ ((person[currentNum].getPhone()).equals("") ? ("Unknown") : (person[currentNum].getPhone())) + "|");
      else
        phoneLabelData.setText("Phone Number: Unknown|");
      emailLabelData.setText("Email: "+ ((person[currentNum].getEmail()).equals("") ? ("Unknown") : (person[currentNum].getEmail())) + "|");
      currentTarget.setText("Current Entry: " + (currentNum + 1));
    }
    clearInputFields();
  }
  
  /** This method adds a new record to the database.
    * The first if statement verifies if the fields are valid. 
    * The next if statement verifies if the the maximum number of records has been achieved.
    * The last if statement verifies if the phone check is true.
    */
  private void addNewRecord(){
    if (verifyFields() == true)
      return;
    if (PersonRecord.recNumber == 49){
      JOptionPane.showMessageDialog ( null, "Error: You have reached the maximum number of records!", "Maximum number of records reached!", JOptionPane.PLAIN_MESSAGE); 
      return;
    }
    
    person[PersonRecord.recNumber + 1] = new PersonRecord(firstName.getText(), lastName.getText(), phone.getText(), email.getText());
    //person[PersonRecord.recNumber + 1] = new PersonRecord();
    
    /*person[PersonRecord.recNumber].setFirst(((firstName.getText()) == null) ? ("") : (firstName.getText()));
     person[PersonRecord.recNumber].setLast(((lastName.getText()) == null) ? ("") : (lastName.getText()));
     if (DataCheck.phoneCheck(phone.getText()) != false)   
     person[PersonRecord.recNumber].setPhone(phone.getText());
     person[PersonRecord.recNumber].setEmail(email.getText());*/
    
    clearInputFields();
    updateEntries();
    isSaved = false;
  }
  
  /**This method is used to undergo particular actions when GUI elements are pressed.
    * The if statements simply sort between the different possibilities of action commands, and call the appropriate methods.
    * @param e ActionEvent e reference, used to get the actionCommand.
    */
  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("Next Record"))
      nextRecord();
    else if (e.getActionCommand().equals("Previous Record"))
      prevRecord();
    else if (e.getActionCommand().equals("Add New Record"))
    {
      addNewRecord();
    }
    else if (e.getActionCommand().equals("Empty Fields"))
      clearInputFields();
    else
    {
      updateRecord();
    }
  }
  
  /** This method updates the record with new information from the textfield.
    *  The first if statement will verify if a record was ever added.
    *  The following if statement will verify if the fields are valid.
    *  The last if statement will verify if the phone is valid to be applied.
    * 
    */
  private void updateRecord(){
    if (PersonRecord.recNumber == -1){
      JOptionPane.showMessageDialog ( null, "Error: There is nothing to replace!", "Cannot replace!", JOptionPane.PLAIN_MESSAGE); 
      return;
    }
    if (verifyFields() == true)
      return;
    //person[currentNum] = new PersonRecord();
    person[currentNum].setFirst(((firstName.getText()) == null) ? ("") : (firstName.getText()));
    person[currentNum].setLast((((lastName.getText()) == null) ? ("") : (lastName.getText())));
    //if (DataCheck.phoneCheck(phone.getText()) != false)   //CHECK THIS!!!
      person[currentNum].setPhone((phone.getText()));
    person[currentNum].setEmail(email.getText());
    clearInputFields();
    updateEntries();
    isSaved = false;
  }
  
  /**clearInputFields is a helper method used to clear all of the input JTextField's.*/
  private void clearInputFields()
  {
    email.setText("");
    firstName.setText("");
    lastName.setText("");
    phone.setText("");
  }
  /**This method verifies if the entry fields are valid or not - and returns a boolean.
    * The first if statement verifies if the email is valid, and if it is not valid then an error message will be outputted to the user.
    * The next if statement will verify if the phone number is valid, and if not it will then output an error message to the user.
    * the next if statement will verify of the fields have at least 1 occupied - otherwise the user cannot add a blank record.
    * @return boolean Used to indicate if the fields are valid or not.
    */
  private boolean verifyFields()
  {
    if (DataCheck.fixEmail(email.getText()) == false && !email.getText().equals("")){
      JOptionPane.showMessageDialog (null, "Error: Invalid email! Please enter an email with the @ symbol once and alphanumeric digits.", "Invalid email.", JOptionPane.PLAIN_MESSAGE); 
      email.setText("");
      email.requestFocus();
      return true;
    }
    if (DataCheck.phoneCheck(phone.getText()) == false &&  !phone.getText().equals("")){
      JOptionPane.showMessageDialog (null, "Error: Invalid phone number. Please enter a valid 10 digit phone number!", "Invalid phone number.", JOptionPane.PLAIN_MESSAGE);  
      phone.setText("");
      phone.requestFocus();
      return true;
    }
    if (firstName.getText().equals("") && lastName.getText().equals("") && phone.getText().equals("") && email.getText().equals("")){
      JOptionPane.showMessageDialog (null, "Error: Please enter at least one field.", "Error: Invalid Information", JOptionPane.PLAIN_MESSAGE); 
      return true;
    }
    return false;
  }
  
  
  
  /**This is the class constructor of the AddressBook class.
    * Essentially this method initializes the GUI of the JPanel, and adds all of the elements and configures them with colour accordingly.
    * @param nextRecord JButton reference to provide the ability for the user to go the next button.
    * @param previousRecord JButton reference used to provide the ability for the user to go to the previous button.
    * @param newRecord JButton reference, used to the provide the ability for the user to make / add a new record.
    * @param modifyRecord JButton reference, used to modify a person record.
    * @param emptyRecord JButton reference, used to empty the fields to a record.
    * @param firstNameLabel JLabel reference, used to output "First name" to the user.
    * @param lastNameLabel JLabel reference, used to output "Last Name" to the user.
    * @param phoneLabel JLabel reference, used to output "Phone" to the user.
    * @param emailLabel JLabel reference, used to output "Email" to the user.
    */
  public AddressBook(){
    setBackground(Color.BLACK);
    
    firstName.setFont(new Font("Courier New", Font.BOLD, 12));
    firstName.setForeground(Color.GREEN);
    firstName.setBackground(Color.GRAY);
    lastName.setFont(new Font("Courier New", Font.BOLD, 12));
    lastName.setForeground(Color.GREEN);
    lastName.setBackground(Color.GRAY);
    phone.setFont(new Font("Courier New", Font.BOLD, 12));
    phone.setForeground(Color.GREEN);
    phone.setBackground(Color.GRAY);
    email.setFont(new Font("Courier New", Font.BOLD, 12));
    email.setForeground(Color.GREEN);
    email.setBackground(Color.GRAY);
    lastNameLabelData.setFont(new Font("Courier New", Font.BOLD, 12));
    lastNameLabelData.setForeground(Color.GREEN);
    phoneLabelData.setFont(new Font("Courier New", Font.BOLD, 12));
    phoneLabelData.setForeground(Color.GREEN);
    firstNameLabelData.setFont(new Font("Courier New", Font.BOLD, 12));
    firstNameLabelData.setForeground(Color.GREEN);
    emailLabelData.setFont(new Font("Courier New", Font.BOLD, 12));
    emailLabelData.setForeground(Color.GREEN);
    currentTarget.setFont(new Font("Courier New", Font.BOLD, 12));
    currentTarget.setForeground(Color.GREEN);
    currentNumLabel.setFont(new Font("Courier New", Font.BOLD, 12));
    currentNumLabel.setForeground(Color.GREEN);
    JButton nextRecord = new JButton("Next Record");
    nextRecord.setFont(new Font("Courier New", Font.BOLD, 12));
    nextRecord.setForeground(Color.GREEN);
    JButton previousRecord = new JButton("Previous Record");
    previousRecord.setFont(new Font("Courier New", Font.BOLD, 12));
    previousRecord.setForeground(Color.GREEN);
    JButton newRecord = new JButton("Add New Record");
    newRecord.setFont(new Font("Courier New", Font.BOLD, 12));
    newRecord.setForeground(Color.GREEN);
    JButton modifyRecord = new JButton("Modify Current Entry");
    modifyRecord.setFont(new Font("Courier New", Font.BOLD, 12));
    modifyRecord.setForeground(Color.GREEN);
    JButton emptyRecord = new JButton("Empty Fields");
    emptyRecord.setFont(new Font("Courier New", Font.BOLD, 12));
    emptyRecord.setForeground(Color.GREEN);
    
    newRecord.addActionListener(this);
    previousRecord.addActionListener(this);
    nextRecord.addActionListener(this);
    modifyRecord.addActionListener(this);
    emptyRecord.addActionListener(this);
    
    
    firstName.setPreferredSize(new Dimension(100,24));
    lastName.setPreferredSize(new Dimension(120,24));
    phone.setPreferredSize(new Dimension(100,24));
    email.setPreferredSize(new Dimension(100,24));
    
    JLabel firstNameLabel = new JLabel("First Name:");
    firstNameLabel.setFont(new Font("Courier New", Font.BOLD, 12));
    firstNameLabel.setForeground(Color.GREEN);
    JLabel lastNameLabel = new JLabel("Last Name: " );
    lastNameLabel.setFont(new Font("Courier New", Font.BOLD, 12));
    lastNameLabel.setForeground(Color.GREEN);
    JLabel phoneLabel = new JLabel("Phone Number: " );
    phoneLabel.setFont(new Font("Courier New", Font.BOLD, 12));
    phoneLabel.setForeground(Color.GREEN);
    JLabel emailLabel = new JLabel("Email: " );
    emailLabel.setFont(new Font("Courier New", Font.BOLD, 12));
    emailLabel.setForeground(Color.GREEN);
    
    add(previousRecord);
    add(newRecord);
    add(modifyRecord);
    add(emptyRecord);
    add(nextRecord);
    
    add(firstNameLabel);
    add(firstName);
    add(lastNameLabel);
    add(lastName);
    add(phoneLabel);
    add(phone);
    add(emailLabel);
    add(email);
    
    
    add(firstNameLabelData);
    add(lastNameLabelData);
    add(phoneLabelData);
    add(emailLabelData);
    add(currentNumLabel);
    add(currentTarget);
    
  }
  
  /**This method will go to the next record.
    * The first if statement will verify if there are any records created, and if there was not then an error message / notification will appear.
    * The following else if would verify if it is at the maximum number, and it would then loop back.
    * The else would then increment the current focus.
    */
  public void nextRecord(){ //Combine these 2 methods?
    if (PersonRecord.recNumber == -1){
      JOptionPane.showMessageDialog (null, "No records created! Please add a record.", "No Records Created!", JOptionPane.PLAIN_MESSAGE); 
      //return; //To avoid null
    }
    else if (PersonRecord.recNumber == currentNum)
      currentNum = 0;
    else
      currentNum++; //Make sure it doesn't mess up with 1 please Horatiu.
    updateEntries(); //Refresh regardless.
  }
  
  /**This method would go back to the previous record.
    * The first if statement would verify if the current number being processed is imposisble - since no tracks have been made yet.
    * The following if statement would verify if the number must be looped back since it hit 0.
    * The else would then de-increment the number.
    */
  
  public void prevRecord()
  {
    if (PersonRecord.recNumber == -1){
      JOptionPane.showMessageDialog (null, "No records created! Please add a record.", "No Records Created!", JOptionPane.PLAIN_MESSAGE); 
    }
    else if (currentNum == 0)
      currentNum = PersonRecord.recNumber;
    else
      currentNum--;
    updateEntries(); //Refresh regardlessly.
  }
}