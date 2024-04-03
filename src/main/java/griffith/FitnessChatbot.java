package griffith;

import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.*;

public class FitnessChatbot {
	
    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Fitness Chatbot! How can I help you today?");

        while (true) {
            String userInput = scanner.nextLine().toLowerCase();
            if (userInput.equals("exit")) {
                System.out.println("Exiting Fitness Chatbot. Have a great day!");
                break;
            } else {
                String lemmatizedInput = lemmatizeText(userInput);
                String response = generateResponse(lemmatizedInput);
                System.out.println(response);
            }
        }
    }

    // Lemmatize the user input using Stanford NLP library
    public static String lemmatizeText(String text) {
        Document doc = new Document(text);
        StringBuilder lemmatizedText = new StringBuilder();
        for (Sentence sent : doc.sentences()) {
            for (String lemma : sent.lemmas()) {
                lemmatizedText.append(lemma).append(" ");
            }
        }
        return lemmatizedText.toString().trim();
    }

    // Generate response based on user input
    public static String generateResponse(String userInput) {
        Pattern exercisePattern = Pattern.compile("\\b(exercise|workout|training)\\b");
        Matcher exerciseMatcher = exercisePattern.matcher(userInput);
        
        if (exerciseMatcher.find()) {
            return "It's great that you're interested in exercising! What type of exercise are you interested in?";
        }

        Pattern dietPattern = Pattern.compile("\\b(diet|nutrition|food)\\b");
        Matcher dietMatcher = dietPattern.matcher(userInput);
        
        if (dietMatcher.find()) {
            return "Maintaining a healthy diet is important for your fitness goals. What specific dietary concerns do you have?";
        }

        return "I'm sorry, I didn't understand your request. Could you please provide more details?";
    }
}

