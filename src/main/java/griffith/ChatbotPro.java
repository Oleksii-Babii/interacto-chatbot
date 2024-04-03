package griffith;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatbotPro {
    private static final Map<String, List<String>> ingredientMap = new HashMap<>();
    private static final Map<String, Integer> cookingTimes = new HashMap<>();
    private static final Map<String, Map<String, String>> nutritionalInfo = new HashMap<>();
    private static final Random random = new Random();
    private static final Map<String, String> favoriteRecipes = new HashMap<>();
    private static final String RECIPE_API_URL = "https://api.spoonacular.com/recipes/complexSearch?apiKey=1346ec58524c4deab81949969895dbe0&query=";

    public static void main(String[] args) {
        initializeIngredients();
        initializeCookingTimes();
        initializeNutritionalInfo();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Cooking Helper Chatbot! How can I assist you today?");

        while (true) {
            String userInput = scanner.nextLine().toLowerCase();
            if (userInput.equals("exit")) {
                System.out.println("Exiting Cooking Helper Chatbot. Have a great day!");
                break;
            } else {
                String response = generateResponse(userInput);
                System.out.println(response);
            }
        }

        scanner.close();
    }

    private static void initializeIngredients() {
        // Initialize ingredients (for demonstration)
        ingredientMap.put("pasta", Arrays.asList("spaghetti", "penne", "linguine"));
        ingredientMap.put("chicken", Arrays.asList("breast", "thigh", "wing"));
        ingredientMap.put("vegetables", Arrays.asList("broccoli", "carrot", "bell pepper"));
    }

    private static void initializeCookingTimes() {
        // Initialize cooking times (for demonstration)
        cookingTimes.put("spaghetti", 10);
        cookingTimes.put("breast", 15);
        cookingTimes.put("broccoli", 5);
    }

    private static void initializeNutritionalInfo() {
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
            return suggestRecipe(userInput);
        }

        Pattern substitutePattern = Pattern.compile("\\b(substitute|alternative)\\b");
        Matcher substituteMatcher = substitutePattern.matcher(userInput);

        if (substituteMatcher.find()) {
            return suggestSubstitute(userInput);
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
            provideNutritionalInfo(userInput);
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

    private static String suggestRecipe(String userInput) {
        // Dummy implementation for demonstration purposes
        return fetchRecipeFromAPI(userInput);
    }

    private static String suggestSubstitute(String userInput) {
        // Dummy implementation for demonstration purposes
        return "You can substitute with xyz. It's a great alternative!";
    }

    private static void saveFavoriteRecipe(String userInput) {
        // Extract recipe name from user input and save it as favorite
        String[] words = userInput.split("\\s+");
        for (String word : words) {
            if (ingredientMap.containsKey(word)) {
                favoriteRecipes.put(word, word);
                break;
            }
        }
    }

    private static void provideNutritionalInfo(String userInput) {
        for (String ingredient : ingredientMap.keySet()) {
            if (userInput.contains(ingredient)) {
                // Provide nutritional information for the ingredient
                if (nutritionalInfo.containsKey(ingredient)) {
                    Map<String, String> nutrition = nutritionalInfo.get(ingredient);
                    System.out.println("Nutritional Information for " + ingredient + ":");
                    for (Map.Entry<String, String> entry : nutrition.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                } else {
                    System.out.println("Nutritional information for " + ingredient + " is not available.");
                }
                return;
            }
        }
        System.out.println("Could not find nutritional information for the specified ingredient.");
    }

    private static void convertIngredientQuantity(String userInput) {
        // Dummy implementation for demonstration purposes
        System.out.println("Conversion functionality not implemented yet.");
    }

    private static String fetchRecipeFromAPI(String userInput) {
        try {
            // Make HTTP request to the recipe API
            URL url = new URL(RECIPE_API_URL + userInput);
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder responseBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                responseBuilder.append(scanner.nextLine());
            }
            scanner.close();

            // Parse JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBuilder.toString());
            JsonNode recipesNode = rootNode.get("results");
            if (recipesNode != null && recipesNode.isArray() && recipesNode.size() > 0) {
                // Get the first recipe from the response
                JsonNode recipeNode = recipesNode.get(0);
                JsonNode titleNode = recipeNode.get("title");
                JsonNode instructionsNode = recipeNode.get("instructions");
                if (titleNode != null && instructionsNode != null) {
                    String recipeName = titleNode.asText();
                    String recipeInstructions = instructionsNode.asText();
                    return "Here's a recipe for " + recipeName + ":\n" + recipeInstructions;
                } else {
                    return "Sorry, I couldn't find the necessary information for the recipe.";
                }
            } else {
                return "Sorry, I couldn't find any recipes for your query.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred while fetching recipes from the API.";
        }
    }
}
