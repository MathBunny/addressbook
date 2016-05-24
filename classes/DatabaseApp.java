//import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**This application provides an AddressBook functionality, permitting the user to add information about individuals, save the information and retrieve it later.
  * 
  * @author Horatiu Lazu
  * @version 1.0.0.0, March 4 2014
  */
public class DatabaseApp extends JFrame implements ActionListener
{
  /**a AddressBook reference, used to access variables and methods.*/
  AddressBook a = new AddressBook();
  /** saveFile File reference variable, used to store a global file which can be used to save / open files.*/
  File saveFile;
  /** hasSaved boolean, which would store if the file has been saved yet or not.*/
  int dialogChoice = JFileChooser.APPROVE_OPTION;
  
  /**Class constructor of the class - which sets up the Frame and implements JPanel elements by encapsulating the AddressBook class.
    * Essentially the code adds the elements in the order in which they appear logically backwards, and provides a message to the user informing on how the application works.
    * 
    * @param newRecord  JMenuItem reference, used to provide the ability for the user to add a new record.
    * @param openRecord JMenuItem reference, used to provide the ability for the user to open a record.
    * @param saveRecord JMenuItem reference, used to provide the ability for the user to save a record.
    * @param saveRecordAs JMenuItem reference, used to provide the ability for the user to save a record with a different fileName.
    * @param file JMenu, provides a file menu within the JMenuBar - makes the formatting more organized.
    * @param myMenu JMenuBar reference, used to provide the ability to store a menuBar at the top of the frame and to provide simple navigation.
    */
  public DatabaseApp()
  {
    super("Personal AddressBook - Horatiu Lazu");
    JOptionPane.showMessageDialog(this, "How To: Enter information in the textboxes, and the stored information will be located below in labels. \nIf you want to" +
                                  " replace any information, simply add the information you want to update the data to in the textboxes and click on the replace information button." + 
                                  "\nIn order to save the database, open or make a new one - click on the file on the main menu at the top. To add a new person just add the information in the textboxes and click on the button to add a new record.", "How To: Use This Application!", JOptionPane.PLAIN_MESSAGE);
    JMenuItem newRecord = new JMenuItem("New Database");
    JMenuItem openRecord = new JMenuItem("Open Record");
    JMenuItem saveRecord = new JMenuItem("Save Record");
    JMenuItem saveRecordAs = new JMenuItem("Save Record As");
    JMenuItem quit = new JMenuItem("Quit");
    JMenu file = new JMenu("File");
    JMenuBar myMenu = new JMenuBar ();
    
    openRecord.addActionListener(this);
    newRecord.addActionListener(this);
    saveRecord.addActionListener(this);
    saveRecordAs.addActionListener(this);
    quit.addActionListener(this);
    
    file.add(newRecord);
    file.add(openRecord);
    file.add(saveRecord);
    file.add(saveRecordAs);
    file.add(quit);
    
    myMenu.add(file);
    setJMenuBar (myMenu);
    add(a);
    
    setSize(780, 200);
    setVisible(true);
    setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
  }
  
  /** This method would get the file using a JFileChooser.
    * The if statement would open the appropriate file.
    * The following if statement would verify if the file is null, and if so it will prevent crashing by returning immediately.
    * The following if statement would verify if the extension was added, and if not it will add it automatically.
    * @param operation String, used to get the operation required internally.
    * @param file File reference, used to store the inputted file.
    * @param fileChooser JFileChooser used to let the user navigate through the files.
    * @returns File Returns a file to the calling method, after the user has found the file they wish to save / open.
    */
  private File getFile(String operation)
  {
    JFileChooser fileChooser= new JFileChooser (".\\");
    fileChooser.setFileFilter(new FileNameExtensionFilter("add files (*.add)", "add"));
    fileChooser.setAcceptAllFileFilterUsed(false);
    if (operation.equals("open"))
      dialogChoice = fileChooser.showOpenDialog (this);
    else
      dialogChoice = fileChooser.showSaveDialog(this);
    File file = fileChooser.getSelectedFile();
    
    if (file == null) //FAST!
      return file;
    
    if(file.getName().length() >= 4){ //Add to 1 if statement.
      if (!file.getName().substring(file.getName().length() - 4, file.getName().length()).equals(".add"))
        file = new File(file.getParent(), file.getName() + ".add");
    }
    else
      file = new File(file.getParent(), file.getName() + ".add");
    return file;
  }
  
  /**This method provides the ability to open a record - and load up the contents within the memory (in terms of the variables).
    * The first if statement verifies if the file received is null or not, and it will return if so.
    * The following if statement will verify if the file is found, and if not it will then call the appropriate methods.
    * The try catch is used in case of an input output related error.
    * The if statement following verifies if the the file name or header are valid.
    * The nested if statement would then verify if the user enters that yes, they want to retry and recall the method accordingly.
    * The following for loop will cycle through the different options as far as inputs go.
    * @param choice String, used to indicate the preference for the user.
    * @param fileName File reference, used to store the fileName selected from the openRecord.
    * @param in BufferedReader reference, used to get access to the file and to read the contents of the file and apply it into the variables within the other class.
    * @param series int, which is used to store the number of records which are to be found within the file - for example 1 would refer to one person going inside the record!
    * @param i int, for loop variable used to process all of the series in the input.
    * @param e IOException reference, used in case of an IO related exception.
    * @throws IOException, used in case of an input output related exception.
    */
  
  public void openRecord() //Remember to ask if he/she wants to save the previous opening.
  {
    File fileName = getFile("open");
    if (fileName == null || dialogChoice != JFileChooser.APPROVE_OPTION) //Really check!!!
      return;
    if (!exists(fileName)){
      String choice = yesNoCancel("Error: The file does not exist! Retry? Yes or no?", "Invalid File Path.", false, false);
      if (choice.equals("Yes"))
        openRecord();
      return;
    }
    else{
      saveFile = fileName;
      BufferedReader in; //Move to readIn data!
      try{
        
        in = new BufferedReader (new FileReader (fileName)); //VERIFY FILE EXTENSION!!!
        if (!in.readLine().equals("AddressBookHoratiu")){ //OR!!! NOT KISS.
          String choice = yesNoCancel("Invalid file - it was not created by this program! Try again? Yes / No!", "Invalid File.", false, false);
          if (choice.equals("Yes"))
            openRecord();
          return;
        }
        int series = Integer.parseInt(in.readLine());
        a.person = new PersonRecord[a.MAX_RECORD];
        AddressBook.currentNum = 0;
        new PersonRecord();
        for(int i = 0; i <= series; i++)
          a.person[i] = new PersonRecord(in.readLine(), in.readLine(), in.readLine(), in.readLine());
        a.updateEntries();
        a.isSaved = true;
      }
      catch(IOException e){
      }
    }
  }
  
  /**This method verifies if a particular file exists.
    * The try catch tries to set up a BufferedReader for a particular file, and if the file is not found then a return false will occur.
    * @param file File reference, used to store the temporary file within the parameter pass.
    * @param e FileNotFoundException reference, used to identify if the file is not found.
    * @throws FileNotFoundException, which is used in case of the file not being found.
    * @returns Boolean, used to see if the file exists.
    */
  public boolean exists(File file)
  {
    try{
      new BufferedReader(new FileReader(file));
    }
    catch(FileNotFoundException e){
      return false; //No IOException. :)
    }
    return true;
  }
  
  /** This method makes a new record and sets variables accordingly by calling an assortment of methods and constructors.
    */
  public void newRecord()
  {
    a.person = new PersonRecord[a.MAX_RECORD]; //CHECK THIS!!!
    new PersonRecord(); //False here.
    a.isSaved = true;
    AddressBook.currentNum = 0; //FORGOT? COMPARE W/ A
    a.updateEntries();
    saveFile = null;//THIS!
  }
  
  /**The purpose of this method is to save the record.
    * The first if statement verifies if there is anything to be saved, and if so then it will output an error message accordingly to the user followed by stopping the method from executing.
    * The following try catch is used in case of an IO related error.
    * The if statement will verify if the user has to be asked of the file path.
    * The if statement following will verify if the file is null, and if so then it will set the dialogChoice to cancel and close the method, otherwise it'll approve of the decision and continue on.
    * The next if statement will verify if the file exists, and if it does then it will ask the user to overwrite the existing file.
    * The following if statement would then verify if the user enters no, then it will reopen the save dialog box. Otherwise, it will provide a value to dialogChoice and then close accordingly.
    * The following for loop will cycle through the different records of the people within PersonRecord.
    * @param result String, used to see if the user wants to overwrite the existing file.
    * @param f JFileChooser reference, used to select the folder.
    * @param file File reference, used to store the file to be outputted.
    * @param out Writer reference, used to output the file accordingly.
    * @param e IOException e reference variable - used in case of an input output exception.
    * @param i For loop variable used to through each set of information.
    * @param fileName String, used to store the file name of the file.
    * @param wantSaveAs Boolean, used to see if the user wants to save the file as something.
    * @throws IOException, which verifies if an input output error occurs.
    */
  public void saveRecord(boolean wantSaveAs) //ASK IF THE USER WANTS TO SAVE OR NOT
  {
    if (PersonRecord.recNumber == -1)
    {
      JOptionPane.showMessageDialog (this, "You have nothing to save!", "Error!", JOptionPane.ERROR_MESSAGE);
      return; //How could you forget this, Horatiu?
    }
    try{
      if (wantSaveAs || PersonRecord.recNumber == -1 || (a.isSaved == false && wantSaveAs == true || saveFile == null)){
        File file = getFile("save");
        if (file == null){
          dialogChoice = JFileChooser.CANCEL_OPTION;
          return;
        }
        else
          dialogChoice = JFileChooser.APPROVE_OPTION;
        String fileName = file.getName();
        
        file = new File(file.getParent(), fileName); //KISS this.
        
        saveFile = file;
        
        if (exists(saveFile) == true){
          String result = yesNoCancel("Would you like to overwrite the existing file?", "Overwrite File?", false, true);
          if (result.equals("No")){
            saveRecord(true);
            return;
          }
          else if (result.equals("Cancel")){
            dialogChoice = JFileChooser.CANCEL_OPTION;
            return;
          }
          else{
            dialogChoice = JFileChooser.APPROVE_OPTION; //Verify this too!
          }
        }
      }
      PrintWriter out = new PrintWriter(new FileWriter(saveFile));
      out.println("AddressBookHoratiu");
      out.println(PersonRecord.recNumber);
      for(int i = 0; i <= PersonRecord.recNumber; i++)
      {
        out.println(a.person[i].getFirstName());
        out.println(a.person[i].getLastName());
        out.println(a.person[i].getPhone());
        out.println(a.person[i].getEmail());
      }
      out.close();
      a.isSaved = true;
      dialogChoice = JFileChooser.APPROVE_OPTION;
    }
    catch(IOException e){
    }
  }
  
  /**This method would output a message to the user asking them if they want to do something with the options of yes, no and cancel.
    * The first if statement verifies if the user msut check if saved, and if yes it will then use an if statement to check if a.isSaved is true, and if so then it will return false because it must not be saved.
    * The ternary operator within options is used to indicate the size of the array, depending on the shouldCheckIfSaved variable - requesting an additional option within the JOptionPane.
    * The following if statement would provide the third value to the options variable assuming that there is a cancel to be added.
    * The last if statements would return the appropriate values to the method depending on the size of the array (hasCancel), and the ternary operators would return the given value depending on the value of n.
    * @param options Array of objects, used to store the options within the JOptionPane.
    * @param n Int, used to store the decision made by the suer.
    * @returns String, used to indicate the user preference.*/
  private String yesNoCancel(String content, String title, boolean shouldCheckIfSaved, boolean hasCancel){
    if (shouldCheckIfSaved){
      if (a.isSaved == true) 
        return "false";
    }
    Object [] options = new Object[(hasCancel) ? (3) : (2)];
    options[0] = "Yes";
    options[1] = "No";
    if (hasCancel)
      options[2] = "Cancel";
    
    int n = JOptionPane.showOptionDialog(null,content, title,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[(hasCancel) ? (2) : (1)]);
    if (hasCancel)
      return (n == 0) ? ("Yes") : (n == 1) ? ("No") : ("Cancel");
    else
      return (n == 0) ? ("Yes") : ("No");
    
  }
  
  
  /**This method would be called once an action is performed within a GUI element in the class.
    * The first if statement would verify if the command is save record, and if so it will call the appropriate method. Similarly, save record as will be called in the else.
    * The nested if statement would be used to verify if the file was saved, and if not then it will get user input. If the user wants to save, then the save dialog will occur. If the user presses cancel, then the if statement would trigger and return in the method.
    * The following if statement with else / else if would then call the appropriate methods once finished.
    * @param e ActionEvent reference variable, used to get the ActionCommand() and the source of the input.
    * @param save String, used to save the preference of the user.
    */
  public void actionPerformed(ActionEvent e){
    
    if (e.getActionCommand().equals("Save Record"))
      saveRecord(false);
    else if (e.getActionCommand().equals("Save Record As"))
      saveRecord(true);
    else{
      if (a.isSaved == false){
        String save = yesNoCancel("Would you like to save your file?", "Save File?", false, true); //VERIFY THE IF / ELSE'S HERE!!!
        if (save.equals("Yes")){ //Directly implement??
          saveRecord(false);    
          if (dialogChoice != JFileChooser.APPROVE_OPTION)
            return;
        }
        else
          if (save.equals("Cancel"))
          return;
      }
      if (e.getActionCommand().equals("Quit"))
        System.exit(0);
      else if (e.getActionCommand().equals("Open Record"))
        openRecord();
      else
        newRecord();
    }
  }
  /**Main method, used to call the constructor of DatabaseApp
    * @param args Array of Strings, used to throw arguments when starting the application.
    */
  public static void main (String [] args){
    new DatabaseApp();
  }
}