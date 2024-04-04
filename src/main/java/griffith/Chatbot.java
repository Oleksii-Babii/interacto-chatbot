package griffith;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.text.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
class RoundButton extends JButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public RoundButton(String label) {
        super(label);
        setContentAreaFilled(false); // Remove the default background
        setOpaque(false); // Make the button transparent
        setBorderPainted(false); // Remove the default border
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray); // Change color when button is pressed
        } else {
            g.setColor(getBackground()); // Use the background color
        }
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Draw round shape
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(80, 20); // Set the preferred size
    }
}
class RoundTextField extends JTextField {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoundTextField(int columns) {
        super(columns);
        setOpaque(false); // Make the text field transparent
        setBorder(null); // Remove the default border
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Draw round shape
        super.paintComponent(g2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 20); // Set the preferred size
    }
}

class Chatbot extends JFrame implements ActionListener{
	
	private static final Map<String, List<String>> ingredientMap = new HashMap<>();
	// private static final Map<String, Integer> cookingTimes = new HashMap<>();
	private static final Map<String, Map<String, String>> nutritionalInfo = new HashMap<>();
	private static final Map<String, String> favoriteRecipes = new HashMap<>();
	private static final String RECIPE_API_URL = "https://api.edamam.com/search";
	private static final String APP_ID = "b9576f7d";
	private static final String APP_KEY = "d7a5d085107bf7dd9377ed2a4146576d";

    private static final long serialVersionUID = 1L;
    private static JTextArea ca = new JTextArea();
    private static JTextField cf = new RoundTextField(20); // Use RoundTextField instead of JTextField
    private static JButton b = new RoundButton("SEND"); // Use RoundButton instead of JButton
    private JLabel l = new JLabel();
    private static String input = null;
    
   
    
    

    public Chatbot()  {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(500, 430);
        setLocation(500,300);
        getContentPane().setBackground(Color.gray);
        setTitle("Fitness Program");

        // Wrap the text area inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(ca);
        scrollPane.setBounds(20, 20, 440, 280);
        add(scrollPane);

        add(cf);
        add(b);
        l.setText("SEND");
        b.setSize(90, 30);
        b.setLocation(350, 320);
        ca.setSize(400, 310);
        ca.setLocation(20, 1);
        ca.setBackground(Color.white);
        cf.setSize(300, 30);
        cf.setLocation(20, 320);
        ca.setForeground(Color.black);
        ca.setFont(new Font("SANS_SERIF", Font.BOLD, 12));
        ca.setEditable(false);
        
        cf.addActionListener(this);
        b.addActionListener(this);
        
	}
    
    @Override
	public void actionPerformed(ActionEvent e) {
    	String userInput = getInput();
    	String formattedInput = formatText(userInput);
    	appendToTextArea(formattedInput);
    	
    	input = String.valueOf(userInput);
    	System.out.println(input);
    	
	}
  
    private static String formatText(String input) {
        String trimmedText = input.trim();
        return WordUtils.wrap(trimmedText, 70);
    }

    private static void appendToTextArea(String text) {
        ca.append("\nYou-->\n" + text + "\n");
    }
    
 
    private static String getInput() {
        String inputText = cf.getText();
        cf.setText(""); // Clear the input field after capturing the text
        return inputText;
    }

	public static String input() {
		String userInput = null;
		while(userInput == null) {
			userInput = input;
			System.out.println();
		}
		input = null;
		
		return userInput;
	}
    
    public static void output(String s) {
        ca.append("\nChatBot-->\n" + s + "\n");
    }
    

    public static void main(String[] args) {
       Chatbot chatbot = new Chatbot();
       chatbot.setVisible(true);
       
//       output("Enter your name"); 
//       output("Bye " + input());
//       
//       output("Enter your age");
//       output("Ege: " + input());
       
       
       initializeIngredients();
       initializeNutritionalInfo();


       output("Welcome to Cooking Helper Chatbot! How can I assist you today?");

       while (true) {
    	   
           String userInput = input().toLowerCase();
           if (userInput.equals("exit")) {
        	   output("Exiting Cooking Helper Chatbot. Have a great day!");
               break;
           } else {
               String response = generateResponse(userInput);
               output(response);
           }
       }

       
    }
    
    public static void initializeIngredients() {
        // Initialize ingredients (for demonstration)
        ingredientMap.put("pasta", Arrays.asList("spaghetti", "penne", "linguine"));
        ingredientMap.put("chicken", Arrays.asList("breast", "thigh", "wing"));
        ingredientMap.put("vegetables", Arrays.asList("broccoli", "carrot", "bell pepper"));
    }


    public static void initializeNutritionalInfo() {
        // Initialize nutritional info (for demonstration)
        Map<String, String> pastaNutrition = new HashMap<>();
        pastaNutrition.put("calories", "200");
        pastaNutrition.put("protein", "8g");
        pastaNutrition.put("fat", "1g");
        nutritionalInfo.put("spaghetti", pastaNutrition);

        Map<String, String> chickenNutrition = new HashMap<>();
        chickenNutrition.put("calories", "150");
        chickenNutrition.put("protein", "20g");
        chickenNutrition.put("fat", "5g");
        nutritionalInfo.put("breast", chickenNutrition);

        Map<String, String> broccoliNutrition = new HashMap<>();
        broccoliNutrition.put("calories", "50");
        broccoliNutrition.put("protein", "3g");
        broccoliNutrition.put("fat", "0.5g");
        nutritionalInfo.put("broccoli", broccoliNutrition);
    }

    public static String generateResponse(String userInput) {
        Pattern recipePattern = Pattern.compile("\\b(recipe|cook|make|prepare)\\b");
        Matcher recipeMatcher = recipePattern.matcher(userInput);

        if (recipeMatcher.find()) {
        	output("Enter the name of the dish: ");
        	String dish = input();
            try {
				return suggestRecipe(dish);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        Pattern favoritePattern = Pattern.compile("\\b(favorite|save)\\b");
        Matcher favoriteMatcher = favoritePattern.matcher(userInput);

        if (favoriteMatcher.find()) {
            saveFavoriteRecipe(userInput);
            return "Recipe saved to favorites.";
        }

        Pattern nutritionPattern = Pattern.compile("\\b(nutrition|nutritional info)\\b");
        Matcher nutritionMatcher = nutritionPattern.matcher(userInput);

        if (nutritionMatcher.find()) {
        	output("Enter the name of the dish or product: ");
        	String dish = input();
            output(provideNutritionalInfo(dish));
            return ""; // Response handled within the method
        }

        Pattern convertPattern = Pattern.compile("\\b(convert|quantity)\\b");
        Matcher convertMatcher = convertPattern.matcher(userInput);

        if (convertMatcher.find()) {
            convertIngredientQuantity(userInput);
            return ""; // Response handled within the method
        }

        return "I'm sorry, I didn't understand your request. Could you please provide more details?";
    }

    public static String suggestRecipe(String userInput) throws IOException {
        JsonNode rootNode = getRootNode(userInput);
		JsonNode hitsNode = rootNode.get("hits");
		if (hitsNode != null && hitsNode.isArray() && hitsNode.size() > 0) {
		    // Get the first recipe from the response
		    JsonNode recipeNode = hitsNode.get(0).get("recipe");
		    
		    JsonNode labelNode = recipeNode.get("label");
		    JsonNode urlNode = recipeNode.get("url");
		    if (labelNode != null && urlNode != null) {
		        String recipeName = labelNode.asText();
		        String recipeUrl = urlNode.asText();
		        JsonNode ingredientLines = recipeNode.get("ingredientLines");
		        String ingredients = "";
		        for(int i = 0; i < ingredientLines.size(); i++) {
		        	ingredients = ingredients + " - " +  ingredientLines.get(i).asText() + "\n";
		        }
		        String cookingTime = recipeNode.get("totalTime").asText();
		        
		        return "Here's a recipe for " + recipeName + ".\n"
		        		+ "Cooking time: " + cookingTime + " min\n"
		        		+ "Ingredients:\n"
		        		+ ingredients + "\n"
		        		+ provideNutritionalInfo(userInput) + "\n"
		        		+ "You can find it here: " + recipeUrl;
		    } else {
		        return "Sorry, I couldn't find the necessary information for the recipe.";
		    }
		} else {
		    return "Sorry, I couldn't find any recipes for your query.";
		}
    }

    public static void saveFavoriteRecipe(String userInput) {
        // Extract recipe name from user input and save it as favorite
        String[] words = userInput.split("\\s+");
        for (String word : words) {
            if (ingredientMap.containsKey(word)) {
                favoriteRecipes.put(word, word);
                break;
            }
        }
    }
    
    public static String provideNutritionalInfo(String userInput) {
    	
    	 	JsonNode rootNode = getRootNode(userInput);
    	 	JsonNode hitsNode = rootNode.get("hits");
            if (hitsNode != null && hitsNode.isArray() && hitsNode.size() > 0) {
            	// Get the first recipe from the response
            	JsonNode recipeNode = hitsNode.get(0).get("recipe");
            	JsonNode labelNode = recipeNode.get("label");
            	JsonNode urlNode = recipeNode.get("url");
            	
            	 if (labelNode != null && urlNode != null) {
            		 String recipeName = labelNode.asText();
            		 double hundredGramParts = recipeNode.get("totalWeight").asDouble() / 100.0;
            		 JsonNode caloriesNode = recipeNode.get("calories");
            		 String calories = String.format("%.02f", caloriesNode.asDouble()/hundredGramParts);
            		 JsonNode totalNutrientsNode = recipeNode.get("totalNutrients");
            		 JsonNode fatQuantity = totalNutrientsNode.get("FAT").get("quantity");
            		 
            		 //String fatQuantity = fatNode.get("quantity").asText();
            		 String fat = String.format("%.02f", fatQuantity.asDouble()/hundredGramParts);
            		 JsonNode proteinQuantity = totalNutrientsNode.get("PROCNT").get("quantity");
            		 String protein = String.format("%.02f", proteinQuantity.asDouble()/hundredGramParts);
            		 
            		 return "Nutritional Information:\n"
            			   + " Calories: " + calories + " kcal\n"
            			   + " Protein: " + protein + " g\n"
            		 	   + " Fat: " + fat + " g\n";
            	 } else {
            		 return "Sorry, I couldn't find the necessary nutritional information.";
            	 }
            	
            } else {
            	return "Sorry, I couldn't find any nutritional information for your query.";
            }
	      
    }
    
    public static JsonNode getRootNode(String userInput) {
    	try {
    		// Make HTTP request to the recipe API
            userInput = URLEncoder.encode(userInput, StandardCharsets.UTF_8);
            URL url = new URL(RECIPE_API_URL + "?q=" + userInput + "&app_id=" + APP_ID + "&app_key=" + APP_KEY);
            //output(url);
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder responseBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                responseBuilder.append(scanner.nextLine());
            }
            scanner.close();

            // Parse JSON response
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(responseBuilder.toString());
    	} catch (Exception e) {
    		e.printStackTrace();
		}
		return null;
    	
    }
    
    public static void convertIngredientQuantity(String userInput) {
        // Dummy implementation for demonstration purposes
        output("Conversion functionality not implemented yet.");
    }
	
}
