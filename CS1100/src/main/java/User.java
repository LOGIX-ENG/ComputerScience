

public class User {  
  // Greeting
  String greeting = "Welcome: This program will calculate the corrected gravity of a liquid from     an observed Temperature to a corrected 60 Degrees F.";
  // Instructions
  String instructions = "You will be asked to enter some informaiton: \n" + "1. The Observed Temperature \n" + "2. The Observed Gravity \n";
  // Example Data to Enter:
  String exampleData = "Example Data: \n" + "1.  Temperature: 89 \n" + "2.  Gravity: 38 \n";

  // Enter Temperature
  String enterTemp = "Enter the Observed Temperature: ";

  // Enter Gravity
  String etnerObsGravity = "Enter the Observed Gravity: ";

  // Output Message
  String output = "The corrected gravity is: ";

  // Error Message
  String errorMessage = "Please enter a number in the format from the instructions" + "\n" + "The Corected Gravity is defaulted to a: 32 Gravity @ 60 Degrees F";
  
  // Greeting
  public String getGreeting() {
    return greeting;
  }

  // Instructions
  public String getInstructions() {
    return instructions;
  }

  // Example Data to Enter
  public String getExampleData() {
    return exampleData;
  }

  // Enter Temperature
  public String setTemp() {
    return enterTemp;
  }

  // Enter Gravity
  public String setObsGravity() {
    return etnerObsGravity;
  }

  // Display Output Message
  public String setOutput() {
    return output;
  }

  // Error Message
  public String setErrorMessage(){
    return errorMessage;
  }
  
}