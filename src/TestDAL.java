
import java.util.List;

public class TestDAL {
    public static void main(String[] args) {
        DatabaseAccessLayer dal = new DatabaseAccessLayer();

        List<MenuManager.Cookbook> cookbooks = dal.getCookbooks();

        // Print the retrieved cookbooks
        for (MenuManager.Cookbook cookbook : cookbooks) {
            System.out.println("Cookbook Name: " + cookbook.getCookbookName());
            System.out.println("Is Book: " + cookbook.isBook());
            System.out.println("Website: " + cookbook.getWebsite());
            System.out.println("--------------------------");
        }
        String cookbookName = "Dude Diet";

        List<MenuManager.Recipe> recipes = dal.getRecipes(cookbookName);

        if (!recipes.isEmpty()) {
            System.out.println("Recipes in " + cookbookName + ":");
            for (MenuManager.Recipe recipe : recipes) {
                System.out.println("Recipe Name: " + recipe.getRecipeName());
                System.out.println("Cookbook Name: " + recipe.getCookbookName());
                System.out.println("Total Servings: " + recipe.getTotalServings());
                System.out.println("---------------");
            }
        } else {
            System.out.println("No recipes found for cookbook: " + cookbookName);
        }
        String recipeName = "Stuffing";
        List<String> ingredients = dal.getIngredients(recipeName);
        System.out.println("Ingredients in " + recipeName);
        System.out.println("---------------------------");
        for (String ingredient : ingredients) {
            System.out.println(ingredient);
        }
    }
}


