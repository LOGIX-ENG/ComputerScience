
import java.util.Scanner;  // Import the Scanner class

public class Main {
  public static void main(String[] args) {
    // Standard Inputs
    double temp = 60.0f;
    double obsGravity = 32.0f;

    
    // Create Scanner Object to get inputs from user
    Scanner myObj = new Scanner(System.in);
    // Create a User Object
    User user = new User();
    // Print out Greetings and Instructions
    System.out.println(user.getGreeting());
    System.out.println(user.getInstructions());
    System.out.println(user.getExampleData());

    // Get and Set user inputs
    System.out.println(user.setTemp());

    // Try Catch NAN values
    try{
      temp = myObj.nextDouble();
    }
    catch(Exception e){
      System.out.println(user.errorMessage);
    }
    
    System.out.println(user.setObsGravity());

    // Try Catch NAN values
    try
      {
      obsGravity = myObj.nextDouble();
      }
    catch(Exception e)
      {
        System.out.println(user.errorMessage);
      }
    
    // Create a FiveATable Object and perform the calculations
    FiveATable fiveATable = new FiveATable();
    double cg = fiveATable.CalcFiveATable(temp, obsGravity);
    System.out.println(user.setOutput() + cg);

    // Close Scanner Object
    myObj.close();
  }
}