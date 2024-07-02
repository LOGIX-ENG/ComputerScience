import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class StateCapitalGame {

	public static void main(String[] args) {

		// About this Assignment

	    // Please read entire code before grading.

	    // This assignment is a programming assignment for the course CompSci 201, Data Structures and Algorithms.
	    /* Part 1: Sorting Arrays
	       Develop a program that asks the user to enter a capital for a U.S. state. 
	       Upon receiving the user input, the program reports whether the user input is correct. 
	       For this application, the 50 states and their capitals are stored in a two-dimensional array in order by state           name. 
	       Display the current contents of the array then use a bubble sort to sort the content by capital. 
	       Next, prompt the user to enter answers for all the state capitals and then display the total correct count. 
	       The user's answer is not case-sensitive. */

	    /* Part 2: Sorting & Searching HashMap
	       Now revise the code to store the pairs of each state and its capital in a Map using the HashMap function. 
	       Display the content of the Map, then use the TreeMap class to sort the map while using a binary search tree for          storage. 
	       Next, your program should prompt the user to enter a state and it should then display the capital for the state.*/

	    // Instructions
	    // 1. Declare a two-dementional array of 50 states and their capitals in order by State name.
	    // 2. Ask the user to enter a capital for a U.S. State.
	    // 3. Check the user input and report if the user input is correct.
	    // 4. Display the current contents of the array then use a bubble sort to sort the content by capital.
	    // 5 Ask the user to enter answers for all the state capitals and then display the total correct count.
	    // 6. Declare a HashMap and move the contents of the two-dimensional array into the HashMap.
	    // 7. Display the content of the Map.
	    /* 8. Declare a TreeMap class to sort the map while using a binary search tree for storage. (Place the conte of the            HashMap into the TreeMap) */
	    // 9. Ask the user to enter a state and it should then display the capital for the state.

	    /* Grader Notes: From First Attempted Submission:
	     •  Correctness: Part 1: You should prompt the user to enter all state capitals and count the correct answers, after 
	        sorting your list.
	        Part 2: Be sure display the content of the Map, then use the TreeMap class to sort the map.Then, your program 
	        should prompt the user to enter a state and it should display the capital for that state.
	      •	You could create 2 separate codes for each part to improve structure and clarity. */
	    
	    
	    // Initialize global variables
	    Scanner sc = new Scanner(System.in);
	    String userInput = "";
	    boolean match = false;
	    int correctCount = 0;
	    boolean end = false;
	    int index = 0;

	    // 1. Declare a two-dementional array of 50 states and their capitals in order by State name.
	    /*50 states and their capitals are stored in a two-dimensional array in order by state name.*/
	    String[][] statesCapitals = {
	            {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"},
	            {"Montgomery", "Juneau", "Phoenix", "Little Rock", "Sacramento", "Denver", "Hartford", "Dover", "Tallahassee", "Atlanta", "Honolulu", "Boise", "Springfield", "Indianapolis", "Des Moines", "Topeka", "Frankfort", "Baton Rouge", "Augusta", "Annapolis", "Boston", "Lansing", "Saint Paul", "Jackson", "Jefferson City", "Helena", "Lincoln", "Carson City", "Concord", "Trenton", "Santa Fe", "Albany", "Raleigh", "Bismarck", "Columbus", "Oklahoma City", "Salem", "Harrisburg", "Providence", "Columbia", "Pierre", "Nashville", "Austin", "Salt Lake City", "Montpelier", "Richmond", "Olympia", "Charleston", "Madison", "Cheyenne"}
	    };
	    
	    // Print welcome message  
	    // Only request one capital from the User.
	    // Not requesting all of them.
	    System.out.println("*********** Welcom to the State Capital Program ***********");

	    // 2. Ask the user to enter a capital for a U.S. State.
	    // This is a single capital check. No Loops.
	    System.out.println("Please enter a state capital for any U.S. State: ");
	    userInput = sc.nextLine();
	    
	    // Create a sub-array of the capitals to cross reference for user input.
	    String[] capitals = statesCapitals[1];

	    // 3. Check the user input and report if the user input is correct.
	    // Search the Sub-Array for the user input and ignore case.
	    for(String city: capitals){ 
	      if(city.equalsIgnoreCase(userInput)){
	        match = true; 
	      }
	    }
	    
	    // Report the result to the user.
	    // If the user input is a match, then report they are correct.
	    if(match){
	      System.out.println("You are correct!");
	      System.out.println(userInput + " is a U.S. State Capital");
	    }  
	    
	      // If the user input is not a match, then report they are incorrect.
	    else
	    {
	      System.out.println("Sorry, that is not a capital of a U.S. State.!");
	    }
	    
	    // 4. Display the current contents of the array then use a bubble sort to sort the content by capital.
	    // Display the current contents of the array
	    System.out.println("The array is ordered by state name.");
	    System.out.println("The current contents of the array are: ");

	    // This loop will itterate through the array and display the contents.
	    // This is the anwer key for the next step in the program.
	    for(int i = 0; i < statesCapitals[0].length; i++) {
	      System.out.println(statesCapitals[0][i] + " " + statesCapitals[1][i]);
	    }

	    // Use bubble sort to sort the content by capital.
	    System.out.println("We will now use a Bubble Sort to rearrange the array by Capital.");

	    // This loop will itterate over the outer array.
	    for(int i = 0; i < statesCapitals[0].length; i++){
	      // This loop will itterate over the inner array.
	      for(int j = 0; j < statesCapitals[0].length - 1; j++){
	        // Temporary variable to hold the value of the current index.
	        String tempCity = "";
	        String tempState = "";

	        // Compare the current index to the next index.
	        if(statesCapitals[1][j].compareTo(statesCapitals[1][i]) > 0){
	          
	          // Swap the values of the current index and the next index.
	          tempCity = statesCapitals[1][i];
	          statesCapitals[1][i] = statesCapitals[1][j];
	          statesCapitals[1][j] = tempCity;

	          // Swap the values of the current index and the next index.
	          tempState = statesCapitals[0][i];
	          statesCapitals[0][i] = statesCapitals[0][j];
	          statesCapitals[0][j] = tempState;
	        }
	      }
	    }

	    // 5 Ask the user to enter answers for all the state capitals and then display the total correct count.
	    /* Grader Notes from First Attempt:	Part 1: You should prompt the user to enter all state capitals and count the 
	       correct answers, after sorting your list. */
	    // This code existed in the first attemp. The grader clearly did not read the entire code.
	    // Added "+ statesCapitals[0][index]" to the prompt for the user to make it more clear.
	    /* We will now prompt the user to enter answers for all the state capitals and then display the total correct count. 
	       The user's answer is not case-sensitive.*/
	    
	    // Instructions
	    System.out.println("Please enter as many state capitals as you can.");
	    System.out.println("Enter 'quit' to stop entering answers.");

	    // Store Users Answers in a list.
	    ArrayList<String> userAnswers = new ArrayList<String>();
	    
	    // Loop until the user enters 'quit'.
	    while(!end && userAnswers.size() <= statesCapitals[0].length - 1){
	      // Prompt the user to enter a state capital
	      // Added to the Print Line to assist the user in answering.
	      System.out.println("Please enter the state capital for: " + statesCapitals[0][index]);
	      userInput = sc.nextLine();
	      index += 1;

	      // Check if the user entered 'quit'.
	      if(userInput.equalsIgnoreCase("quit")){
	        end = true;
	      }
	      else{
	        // Add the user input to the list.
	        userAnswers.add(userInput);
	      }

	    }
	    // Display Correct Answeres
	    for(String answer: userAnswers){

	      // Search the Sub-Array for the user input and ignore case.
	      for(String city: capitals){ 
	        if(city.equalsIgnoreCase(answer)){
	          correctCount +=1; 
	        }
	      }
	    }
	      // Display the total correct count.
	      System.out.println("Now you have completed the test, let us see what you scored!");
	      System.out.println("You have entered " + correctCount + " correct answers out of 50.");

	      // Place the current array into a Hash Map
	      System.out.println("We will not place the two-dimensional Array of States and Capitals into a Hash Map");

	    // 6. Declare a HashMap and move the contents of the two-dimensional array into the HashMap.
	    // Create a Hash Map to store the state and capital pairs.
	    HashMap<String, String> stateCapitalsMap = new HashMap<String, String>(statesCapitals[0].length);

	    // Loop through the array and add each pair to the Hash Map.
	    for(int i = 0; i < statesCapitals[0].length; i++)
	      {
	          stateCapitalsMap.put(statesCapitals[0][i], statesCapitals[1][i]);
	      }

	    // 7. Display the content of the Map.
	    // Previous Grader Notes: Part 2 Be sure display the content of the Map, then use the TreeMap class to sort the map.
	    // This is the original code. It displays the content of the Map. Then places the content into a Tree Map.
	    // Grader clearly did not read the entire code.
	    // Display the contents of the Hash Map.
	    System.out.println("The contents of the Hash Map are: ");
	    System.out.println(stateCapitalsMap);
	    System.out.println("========== Next we will put the contents of the map into a Tree Map ==========");

	    /* 8. Declare a TreeMap class to sort the map while using a binary search tree for storage. (Place the content of 
	          the HashMap into the TreeMap) */
	    // Now we will sort the map while using a binary search tree for storage.
	    // I will use a binary search tree to store the state and capital pairs.
	    TreeMap<String, String> sortedMap = new TreeMap<String, String>(stateCapitalsMap);

	    // 9. Ask the user to enter a state and it should then display the capital for the state.
	    /* Grader Notes: Then, your program should prompt the user to enter a state and it should display the capital for 
	       that state. */
	    // This is original code that existed in first submission. Read the entire code.
	    // Now ask the user to input a State and the program will output the capital for the state.
	    System.out.println("Please enter a state to find its capital: ");

	    userInput = sc.nextLine();
	    
	    // Search the Tree Map for the user input and ignore case.
	    if(sortedMap.containsKey(userInput))
	    {
	      System.out.println("The capital of " + userInput + " is " + sortedMap.get(userInput));
	    }
	    else 
	    {
	      System.out.println("Sorry that is not a state in the U.S.");
	    }

	    // Ending Message to user.
	    System.out.println("Thank you for using the State Capital Program.");
	    System.out.println("Have a great day!");

	    /* Final note from original grader: •	You could create 2 separate codes for each part to improve structure and 
	       clarity. While I agree with seperation of concerns in programming that was not part of the original scope so it 
	       should not affect my grade. */
	}

}
