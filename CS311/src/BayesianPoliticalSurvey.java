import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BayesianPoliticalSurvey {

	// Setup variables 
	private static final double CONFIDENCE_THRESHOLD = 0.75; // Threshold for making a decision
    private static Map<String, Double> partyPriors = new HashMap<>();
    private static List<Question> questions = new ArrayList<>();
    private static Map<String, Map<String, Map<String, Double>>> likelihoods = new HashMap<>();
    private static String probabilityString;
    private static String[] parties = {"Democrat", "Republican", "Independent", "Libertarian"};

    // Party Information and questions setup
    // Set party probabilities to %25 to make them equal
    static {
        partyPriors.put("Democrat", 0.001);
        partyPriors.put("Republican", 0.001);
        partyPriors.put("Independent", 0.001);
        partyPriors.put("Libertarian", 0.001);

        // Initialize Questions
        questions.add(new Question("What is the role of government in ensuring social welfare?",
                new String[]{"The government should provide a safety net and social programs to help those in need.", "The government should be limited in its role of providing social welfare, promoting self-reliance and individual responsibility.",
                             "The role of government in social welfare depends on the issue, but a balance between individual responsibility and government support is vital.", "Individuals should be responsible for their own well-being, minimizing government involvement in social welfare programs"}));
        questions.add(new Question("Do you believe in stricter gun control laws?",
                new String[]{"Yes, stricter gun control laws are necessary to reduce gun violence.", "Focus on mental health initiatives and securing the border to prevent gun violence, not stricter gun control laws.",
                             "Some gun control measures might be necessary, but responsible gun ownership and addressing root causes of violence are also priorities.", "Individual gun ownership rights should be protected, with a focus on personal responsibility."}));
        questions.add(new Question("How should we address climate change?",
                new String[]{"We should address climate change through regulations and investments in renewable energy sources.", "Technological innovation and market-based solutions are the best ways to address climate change.",
                             "A mix of regulations, incentives for clean energy, and technological innovation can effectively combat climate change.", "The free market will drive innovation and technological solutions to address climate change."}));
        questions.add(new Question("What is the best approach to healthcare?",
                new String[]{"Universal healthcare or a government-subsidized system is the best approach to ensure everyone has access to affordable healthcare.", "Free-market competition and individual choice should drive the healthcare system, ensuring affordability and innovation.",
                             "Finding solutions that provide affordable healthcare access for everyone is crucial, regardless of the specific approach.", " A free-market healthcare system allows for individual choice and competition, promoting lower costs and better service."}));
        questions.add(new Question("Should the wealthy pay higher taxes?",
                new String[]{"The wealthy should pay higher taxes to contribute more to society and fund social programs.", "Lower taxes stimulate economic growth and benefit everyone.",
                             "Tax increases on the wealthy for specific purposes, like infrastructure or education, might be considered.", "Taxes should be kept as low as possible to allow individuals to keep more of their earnings."}));
        questions.add(new Question("Is stronger regulation of businesses necessary?",
                new String[]{"Some regulations are necessary to protect consumers, workers, and the environment from harmful business practices.", "Regulations should be limited to allow businesses to innovate and create jobs.",
                             "Some regulations are necessary to ensure fair competition and protect consumers, but free markets should be encouraged.", "Minimal government regulation allows businesses to operate freely and innovate."}));
        questions.add(new Question("What is the best way to address income inequality?",
                new String[]{"Progressive taxation and social programs can help reduce income inequality and create a fairer society.", "Job creation and promoting economic opportunity are the best ways to address income inequality.",
                             "A combination of economic policies to create jobs and social programs to help those struggling can address income inequality.", "The free market creates opportunities for everyone to succeed, and income inequality is a natural consequence."}));
        questions.add(new Question("Should the government play a role in education costs?",
                new String[]{"The government should play a role in making education more affordable through subsidies or loan forgiveness programs.", "School choice and less government involvement in education can improve the education system and give parents more options.",
                             "Some government assistance might be necessary to ensure everyone has access to quality education.", "The government should not be involved in funding education, allowing for private and alternative educational institutions to flourish."}));
        questions.add(new Question("How can we strengthen labor unions?",
                new String[]{"Supporting worker rights and collective bargaining strengthens labor unions and improves working conditions.", "Individual contracts and a focus on worker skills are more effective than strong labor unions.",
                             "Reforms that give workers more power without necessarily strengthening traditional unions might be explored.", "Labor unions can be problematic, and individual contracts are the best way to ensure fair worker treatment."}));
        questions.add(new Question("What is the best approach to immigration?",
                new String[]{"A pathway to citizenship for undocumented immigrants is a humane and practical solution.", "Stricter border security and enforcing immigration laws are essential for national security.",
                             "A fair and legal immigration system that prioritizes national security is essential.", "Open borders and free movement of people are ideal, but national security concerns might require some restrictions."}));
        questions.add(new Question("In your opinion, the best way for our country to participate in the global economy is through:",
                new String[]{"Increased protection of domestic industries through tariffs and import quotas.", "Negotiating free trade agreements that reduce barriers to trade with other countries.",
                             "Joining regional economic blocs with similar economic structures.", "Focusing on domestic economic development without relying heavily on international trade."}));

        initializeLikelihoods();
    }

    // Initialize the likelihood of each response based on the party.
    // Likelihoods are the weights used to calculate the probability of the users party.
    private static void initializeLikelihoods() {
        for (String party : partyPriors.keySet()) {
            likelihoods.put(party, new HashMap<>());
        }

     // Likelihoods for Question 1
        setLikelihood("Democrat", 0, questions.get(0).options[0], 0.7);
        setLikelihood("Democrat", 0, questions.get(0).options[1], 0.1);
        setLikelihood("Democrat", 0, questions.get(0).options[2], 0.15);
        setLikelihood("Democrat", 0, questions.get(0).options[3], 0.05);

        setLikelihood("Republican", 0, questions.get(0).options[0], 0.1);
        setLikelihood("Republican", 0, questions.get(0).options[1], 0.7);
        setLikelihood("Republican", 0, questions.get(0).options[2], 0.15);
        setLikelihood("Republican", 0, questions.get(0).options[3], 0.05);

        setLikelihood("Independent", 0, questions.get(0).options[0], 0.15);
        setLikelihood("Independent", 0, questions.get(0).options[1], 0.1);
        setLikelihood("Independent", 0, questions.get(0).options[2], 0.7);
        setLikelihood("Independent", 0, questions.get(0).options[3], 0.05);

        setLikelihood("Libertarian", 0, questions.get(0).options[0], 0.05);
        setLikelihood("Libertarian", 0, questions.get(0).options[1], 0.2);
        setLikelihood("Libertarian", 0, questions.get(0).options[2], 0.15);
        setLikelihood("Libertarian", 0, questions.get(0).options[3], 0.25);

        // Likelihoods for Question 2
        setLikelihood("Democrat", 1, questions.get(1).options[0], 0.6);
        setLikelihood("Democrat", 1, questions.get(1).options[1], 0.1);
        setLikelihood("Democrat", 1, questions.get(1).options[2], 0.15);
        setLikelihood("Democrat", 1, questions.get(1).options[3], 0.15);

        setLikelihood("Republican", 1, questions.get(1).options[0], 0.1);
        setLikelihood("Republican", 1, questions.get(1).options[1], 0.7);
        setLikelihood("Republican", 1, questions.get(1).options[2], 0.15);
        setLikelihood("Republican", 1, questions.get(1).options[3], 0.05);

        setLikelihood("Independent", 1, questions.get(1).options[0], 0.15);
        setLikelihood("Independent", 1, questions.get(1).options[1], 0.1);
        setLikelihood("Independent", 1, questions.get(1).options[2], 0.6);
        setLikelihood("Independent", 1, questions.get(1).options[3], 0.15);

        setLikelihood("Libertarian", 1, questions.get(1).options[0], 0.05);
        setLikelihood("Libertarian", 1, questions.get(1).options[1], 0.1);
        setLikelihood("Libertarian", 1, questions.get(1).options[2], 0.15);
        setLikelihood("Libertarian", 1, questions.get(1).options[3], 0.7);
        
        // Likelihoods for Question 3
        setLikelihood("Democrat", 2, questions.get(2).options[0], 0.6);
        setLikelihood("Democrat", 2, questions.get(2).options[1], 0.1);
        setLikelihood("Democrat", 2, questions.get(2).options[2], 0.15);
        setLikelihood("Democrat", 2, questions.get(2).options[3], 0.15);

        setLikelihood("Republican", 2, questions.get(2).options[0], 0.1);
        setLikelihood("Republican", 2, questions.get(2).options[1], 0.6);
        setLikelihood("Republican", 2, questions.get(1).options[2], 0.15);
        setLikelihood("Republican", 2, questions.get(2).options[3], 0.15);

        setLikelihood("Independent", 2, questions.get(2).options[0], 0.15);
        setLikelihood("Independent", 2, questions.get(2).options[1], 0.1);
        setLikelihood("Independent", 2, questions.get(2).options[2], 0.6);
        setLikelihood("Independent", 2, questions.get(2).options[3], 0.15);

        setLikelihood("Libertarian", 2, questions.get(2).options[0], 0.05);
        setLikelihood("Libertarian", 2, questions.get(2).options[1], 0.1);
        setLikelihood("Libertarian", 2, questions.get(2).options[2], 0.15);
        setLikelihood("Libertarian", 2, questions.get(2).options[3], 0.6);

        // Likelihoods for Question 4
        setLikelihood("Democrat", 3, questions.get(3).options[0], 0.7);
        setLikelihood("Democrat", 3, questions.get(3).options[1], 0.1);
        setLikelihood("Democrat", 3, questions.get(3).options[2], 0.15);
        setLikelihood("Democrat", 3, questions.get(3).options[3], 0.05);

        setLikelihood("Republican", 3, questions.get(3).options[0], 0.1);
        setLikelihood("Republican", 3, questions.get(3).options[1], 0.7);
        setLikelihood("Republican", 3, questions.get(3).options[2], 0.15);
        setLikelihood("Republican", 3, questions.get(3).options[3], 0.05);

        setLikelihood("Independent", 3, questions.get(3).options[0], 0.15);
        setLikelihood("Independent", 3, questions.get(3).options[1], 0.1);
        setLikelihood("Independent", 3, questions.get(3).options[2], 0.7);
        setLikelihood("Independent", 3, questions.get(3).options[3], 0.05);

        setLikelihood("Libertarian", 3, questions.get(3).options[0], 0.05);
        setLikelihood("Libertarian", 3, questions.get(3).options[1], 0.1);
        setLikelihood("Libertarian", 3, questions.get(3).options[2], 0.15);
        setLikelihood("Libertarian", 3, questions.get(3).options[3], 0.7);

        // Likelihoods for Question 5
        setLikelihood("Democrat", 4, questions.get(4).options[0], 0.7);
        setLikelihood("Democrat", 4, questions.get(4).options[1], 0.1);
        setLikelihood("Democrat", 4, questions.get(4).options[2], 0.15);
        setLikelihood("Democrat", 4, questions.get(4).options[3], 0.05);

        setLikelihood("Republican", 4, questions.get(4).options[0], 0.1);
        setLikelihood("Republican", 4, questions.get(4).options[1], 0.7);
        setLikelihood("Republican", 4, questions.get(4).options[2], 0.15);
        setLikelihood("Republican", 4, questions.get(4).options[3], 0.05);

        setLikelihood("Independent", 4, questions.get(4).options[0], 0.15);
        setLikelihood("Independent", 4, questions.get(4).options[1], 0.1);
        setLikelihood("Independent", 4, questions.get(4).options[2], 0.7);
        setLikelihood("Independent", 4, questions.get(4).options[3], 0.05);

        setLikelihood("Libertarian", 4, questions.get(4).options[0], 0.05);
        setLikelihood("Libertarian", 4, questions.get(4).options[1], 0.1);
        setLikelihood("Libertarian", 4, questions.get(4).options[2], 0.15);
        setLikelihood("Libertarian", 4, questions.get(4).options[3], 0.7);

        // Likelihoods for Question 6
        setLikelihood("Democrat", 5, questions.get(5).options[0], 0.7);
        setLikelihood("Democrat", 5, questions.get(5).options[1], 0.1);
        setLikelihood("Democrat", 5, questions.get(5).options[2], 0.15);
        setLikelihood("Democrat", 5, questions.get(5).options[3], 0.05);

        setLikelihood("Republican", 5, questions.get(5).options[0], 0.1);
        setLikelihood("Republican", 5, questions.get(5).options[1], 0.7);
        setLikelihood("Republican", 5, questions.get(5).options[2], 0.15);
        setLikelihood("Republican", 5, questions.get(5).options[3], 0.05);

        setLikelihood("Independent", 5, questions.get(5).options[0], 0.15);
        setLikelihood("Independent", 5, questions.get(5).options[1], 0.1);
        setLikelihood("Independent", 5, questions.get(5).options[2], 0.7);
        setLikelihood("Independent", 5, questions.get(5).options[3], 0.05);

        setLikelihood("Libertarian", 5, questions.get(5).options[0], 0.05);
        setLikelihood("Libertarian", 5, questions.get(5).options[1], 0.1);
        setLikelihood("Libertarian", 5, questions.get(5).options[2], 0.15);
        setLikelihood("Libertarian", 5, questions.get(5).options[3], 0.7);

        // Likelihoods for Question 7
        setLikelihood("Democrat", 6, questions.get(6).options[0], 0.7);
        setLikelihood("Democrat", 6, questions.get(6).options[1], 0.1);
        setLikelihood("Democrat", 6, questions.get(6).options[2], 0.15);
        setLikelihood("Democrat", 6, questions.get(6).options[3], 0.05);

        setLikelihood("Republican", 6, questions.get(6).options[0], 0.1);
        setLikelihood("Republican", 6, questions.get(6).options[1], 0.7);
        setLikelihood("Republican", 6, questions.get(6).options[2], 0.15);
        setLikelihood("Republican", 6, questions.get(6).options[3], 0.05);

        setLikelihood("Independent", 6, questions.get(6).options[0], 0.15);
        setLikelihood("Independent", 6, questions.get(6).options[1], 0.1);
        setLikelihood("Independent", 6, questions.get(6).options[2], 0.7);
        setLikelihood("Independent", 6, questions.get(6).options[3], 0.05);

        setLikelihood("Libertarian", 6, questions.get(6).options[0], 0.05);
        setLikelihood("Libertarian", 6, questions.get(6).options[1], 0.1);
        setLikelihood("Libertarian", 6, questions.get(6).options[2], 0.15);
        setLikelihood("Libertarian", 6, questions.get(6).options[3], 0.7);

        // Likelihoods for Question 8
        setLikelihood("Democrat", 7, questions.get(7).options[0], 0.7);
        setLikelihood("Democrat", 7, questions.get(7).options[1], 0.1);
        setLikelihood("Democrat", 7, questions.get(7).options[2], 0.15);
        setLikelihood("Democrat", 7, questions.get(7).options[3], 0.05);

        setLikelihood("Republican", 7, questions.get(7).options[0], 0.1);
        setLikelihood("Republican", 7, questions.get(7).options[1], 0.7);
        setLikelihood("Republican", 7, questions.get(7).options[2], 0.15);
        setLikelihood("Republican", 7, questions.get(7).options[3], 0.05);

        setLikelihood("Independent", 7, questions.get(7).options[0], 0.15);
        setLikelihood("Independent", 7, questions.get(7).options[1], 0.1);
        setLikelihood("Independent", 7, questions.get(7).options[2], 0.7);
        setLikelihood("Independent", 7, questions.get(7).options[3], 0.05);

        setLikelihood("Libertarian", 7, questions.get(7).options[0], 0.05);
        setLikelihood("Libertarian", 7, questions.get(7).options[1], 0.1);
        setLikelihood("Libertarian", 7, questions.get(7).options[2], 0.15);
        setLikelihood("Libertarian", 7, questions.get(7).options[3], 0.7);

         // Likelihoods for Question 9
        setLikelihood("Democrat", 8, questions.get(8).options[0], 0.7);
        setLikelihood("Democrat", 8, questions.get(8).options[1], 0.1);
        setLikelihood("Democrat", 8, questions.get(8).options[2], 0.15);
        setLikelihood("Democrat", 8, questions.get(8).options[3], 0.05);

        setLikelihood("Republican", 8, questions.get(8).options[0], 0.1);
        setLikelihood("Republican", 8, questions.get(8).options[1], 0.7);
        setLikelihood("Republican", 8, questions.get(8).options[2], 0.15);
        setLikelihood("Republican", 8, questions.get(8).options[3], 0.05);

        setLikelihood("Independent", 8, questions.get(8).options[0], 0.15);
        setLikelihood("Independent", 8, questions.get(8).options[1], 0.1);
        setLikelihood("Independent", 8, questions.get(8).options[2], 0.7);
        setLikelihood("Independent", 8, questions.get(8).options[3], 0.05);

        setLikelihood("Libertarian", 8, questions.get(8).options[0], 0.05);
        setLikelihood("Libertarian", 8, questions.get(8).options[1], 0.1);
        setLikelihood("Libertarian", 8, questions.get(8).options[2], 0.15);
        setLikelihood("Libertarian", 8, questions.get(8).options[3], 0.7);

        // Likelihoods for Question 10
        setLikelihood("Democrat", 9, questions.get(9).options[0], 0.7);
        setLikelihood("Democrat", 9, questions.get(9).options[1], 0.1);
        setLikelihood("Democrat", 9, questions.get(9).options[2], 0.15);
        setLikelihood("Democrat", 9, questions.get(9).options[3], 0.05);

        setLikelihood("Republican", 9, questions.get(9).options[0], 0.1);
        setLikelihood("Republican", 9, questions.get(9).options[1], 0.7);
        setLikelihood("Republican", 9, questions.get(9).options[2], 0.15);
        setLikelihood("Republican", 9, questions.get(9).options[3], 0.05);

        setLikelihood("Independent", 9, questions.get(9).options[0], 0.15);
        setLikelihood("Independent", 9, questions.get(9).options[1], 0.1);
        setLikelihood("Independent", 9, questions.get(9).options[2], 0.7);
        setLikelihood("Independent", 9, questions.get(9).options[3], 0.05);

        setLikelihood("Libertarian", 9, questions.get(9).options[0], 0.05);
        setLikelihood("Libertarian", 9, questions.get(9).options[1], 0.1);
        setLikelihood("Libertarian", 9, questions.get(9).options[2], 0.15);
        setLikelihood("Libertarian", 9, questions.get(9).options[3], 0.7);
        
    }
	

		// Sets the Likelihood of a response
    	private static void setLikelihood(String party, int questionIndex, String response, double value) {
    		Map<String, Map<String, Double>> questionLikelihoods = likelihoods.get(party);
    		String questionKey = "Q" + questionIndex;
    		if (!questionLikelihoods.containsKey(questionKey)) {
    			questionLikelihoods.put(questionKey, new HashMap<>());
    		}
    		questionLikelihoods.get(questionKey).put(response, value);
    	}
        
        // Guesses the users party
        private static String guessParty() {
            return Collections.max(partyPriors.entrySet(), Map.Entry.comparingByValue()).getKey();
        }
        
        // Writes data from the user responses to a data file
        private static void storeUserData(List<String> userResponses, String userParty, String prob, String documentName) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(documentName, true))) {
                for (String response : userResponses) {
                    writer.write(response + ", ");
                }
                writer.write(userParty);
                writer.write(" : ");
                writer.write(prob);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // Gets and prints questions to users
        static class Question {
            private String questionText;
            private String[] options;

            public Question(String questionText, String[] options) {
                this.questionText = questionText;
                this.options = options;
            }

            public void ask(Scanner scanner) {
                System.out.println(questionText);
                for (int i = 0; i < options.length; i++) {
                    System.out.println((i + 1) + ". " + options[i]);
                }
            }

            public String[] getOptions() {
                return options;
            }
        
            
            // Update the probability after each interaction with the user
            public void updateProbabilities(int choice, int questionIndex, Map<String, Double> priors, Map<String, Map<String, Map<String, Double>>> likelihoods) {
                String selectedOption = options[choice - 1];
                String questionKey = "Q" + questionIndex;

                for (String party : priors.keySet()) {
                    double likelihood = likelihoods.get(party).getOrDefault(questionKey, new HashMap<>()).getOrDefault(selectedOption, 0.01);
                    priors.put(party, priors.get(party) * likelihood);
                }
                normalizeProbabilities(priors);
                printProbabilities(priors); // Debug information to track probability updates
            }

            // Normalize the probabilities of prior choices 
            private void normalizeProbabilities(Map<String, Double> probabilities) {
                double sum = probabilities.values().stream().mapToDouble(Double::doubleValue).sum();
                for (String party : probabilities.keySet()) {
                    probabilities.put(party, probabilities.get(party) / sum);
                }
            }

            // Used for debugging and writing probability data to files
            private void printProbabilities(Map<String, Double> probabilities) {
                //System.out.println("Updated probabilities:");
                probabilityString = probabilities.keySet() + ": " + probabilities.values();
                
                for (Map.Entry<String, Double> entry : probabilities.entrySet())
                {
                	probabilityString = "Probability" + " : " + entry.getValue();
                    //System.out.println(entry.getKey() + ": " + entry.getValue());
                    
                }
            }
        }
        
        
        
	public static void main(String[] args) {
		
		// Setup Scanner Object
		// Setup variables needed
		Scanner scanner = new Scanner(System.in);
        List<String> userResponses = new ArrayList<>();
        boolean decisionMade = false;

        // Start
        System.out.println("Welcome to the Bayesian Political Survey!");
        
        /*
         * Loop though each question and ask the user for an input
         * Record the responses and update the Probabilities
         * Run the guessParty method to check against the confidence threshold for guessing a party
         * If any party value probability is above the threshold value then guess the party and exit the program
         */
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            question.ask(scanner);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character after nextInt()
            userResponses.add(question.getOptions()[choice - 1]);
            question.updateProbabilities(choice, i, partyPriors, likelihoods);
            
            switch(choice)
            {
            case(1):
            	// Store user data
                storeUserData(userResponses, "Democrat", probabilityString, "Democrat.txt");
            break;
            case(2):
            	// Store user data
                storeUserData(userResponses, "Republican", probabilityString, "Republican.txt");
            break;
            case(3):
            	// Store user data
                storeUserData(userResponses, "Independent", probabilityString, "Independent.txt");
            break;
            case(4):
            	// Store user data
                storeUserData(userResponses, "Libertarian", probabilityString, "Libertarian.txt");
            break;
            }
            
            // Check if any party's probability exceeds the threshold
            String guessedParty = guessParty();
            if (partyPriors.get(guessedParty) >= CONFIDENCE_THRESHOLD) {
            	System.out.println("Which political party do you affiliate with?");
                System.out.println("1. Democrat\n2. Republican\n3. Independent\n4. Libertarian");
                int userPartySelect = scanner.nextInt();
                String userIdentifiedParty = parties[userPartySelect - 1];
                System.out.println("You have identified your party as: " + userIdentifiedParty);
                scanner.nextLine(); // Consume newline character after nextInt()
                System.out.println("Based on your answers, we guess you are a: " + guessedParty + " with a " + partyPriors.get(guessedParty) + "% Probability.");
                decisionMade = true;
                break;
            }
        }

        /*
         * If a decision is not made then execute this section
         * Ask user for their party and make a final guess based on data
         */
        if (!decisionMade) {
            System.out.println("Which political party do you affiliate with?");
            System.out.println("1. Democrat\n2. Republican\n3. Independent\n4. Libertarian");
            int partyChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character after nextInt()

            
            String userParty = parties[partyChoice - 1];

            // Store user data
            storeUserData(userResponses, userParty, probabilityString, "SurveyData.txt");

            // Final guess if decision wasn't made during the survey
            String finalGuessedParty = guessParty();
            System.out.println("Based on your answers, we guess you are a: " + finalGuessedParty + " with a " + partyPriors.get(finalGuessedParty) + "% Probability.");
        }

        scanner.close();
		
	}

}
