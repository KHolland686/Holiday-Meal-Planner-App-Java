import java.util.*;

public class MenuManager {
    private final DatabaseAccessLayer dal;
    private Map<String, List<Recipe>> holidayMenu;
    private static final Scanner sc = new Scanner(System.in);

    public MenuManager() {
        this.dal = new DatabaseAccessLayer();
        this.holidayMenu = new HashMap<>();
    }

    public Cookbook selectCookbook() {
        List<MenuManager.Cookbook> cookbooks = dal.getCookbooks();
        for (int i = 0; i < cookbooks.size(); i++) {
            MenuManager.Cookbook cookbook = cookbooks.get(i);
            System.out.println((i +1) + ".  Cookbook Name: " + cookbook.getCookbookName());
            System.out.println("Is Book: " + cookbook.isBook());
            System.out.println("Website: " + cookbook.getWebsite());
            System.out.println("--------------------------");
        }
        System.out.print("Enter the number corresponding to the cookbook: ");
        int cookbookChoice = sc.nextInt();
        if (cookbookChoice >= 1 && cookbookChoice <= cookbooks.size()) {
            return cookbooks.get(cookbookChoice - 1);
        } else {
            return null;
        }
    }

    public void displayRecipes(String cookbookName) {
        List<MenuManager.Recipe> recipes = dal.getRecipes(cookbookName);
        if (!recipes.isEmpty()) {
            System.out.println("Recipes in " + cookbookName + ":");
            System.out.println("============================");
            for (int i = 0; i < recipes.size(); i++) {
                MenuManager.Recipe recipe = recipes.get(i);
                System.out.println((i +1) + ".  Recipe Name: " + recipe.getRecipeName());
                System.out.println("Total Servings: " + recipe.getTotalServings());
                System.out.println("------------------------");
            }
        } else {
            System.out.println("No recipes found for cookbook: " + cookbookName);
        }
    }

    public Recipe selectRecipe(String cookbookName) {
        List<MenuManager.Recipe> recipes = dal.getRecipes(cookbookName);

        System.out.print("Enter the number corresponding to the recipe: ");
        int recipeChoice = sc.nextInt();
        if (recipeChoice >= 1 && recipeChoice <= recipes.size()) {
            return recipes.get(recipeChoice - 1);
        } else {
            System.out.println("Invalid number! Try again.");
        }
        return null;
    }

    public List<String> viewRecipeIngredients(String recipeName) {
        List<String> ingredients = dal.getIngredients(recipeName);
        System.out.println("Ingredients in " + recipeName);
        System.out.println("========================");
        for (String ingredient : ingredients) {
            System.out.println(ingredient);
        }
        return ingredients;
    }

    public void saveRecipeToMenu(Recipe chosenRecipe) {
        String cookbookName = chosenRecipe.cookbookName;
        int totalServings = chosenRecipe.totalServings;
        List<String> ingredients = dal.getIngredients(chosenRecipe.recipeName);
        if (cookbookName == null || totalServings <= 0 || ingredients.isEmpty()) {
            System.out.println("Recipe information incomplete. Not added to menu.");
            return;
        }
        String course = promptForCourse();
        if (course == null) {
            System.out.println("No course selected. Recipe not added to menu.");
            return;
        }
        List<Recipe> recipes = holidayMenu.get(course);
        if (recipes == null) {
            recipes = new ArrayList<>();
            holidayMenu.put(course, recipes);
        }
        chosenRecipe.setIngredients(ingredients);
        chosenRecipe.setCourse(course);

        if (!recipes.contains(chosenRecipe)) {
            recipes.add(chosenRecipe);
            System.out.println(chosenRecipe.recipeName + " successfully added to menu!");
        } else {
            System.out.println("Recipe already exists in menu.");
        }
    }

    private String promptForCourse () {
        List<String> courses = Arrays.asList("Breakfast", "Lunch", "Appetizer", "Dinner", "Dessert");
        while (true) {
            System.out.println("Courses:");
            for (int i = 0; i < courses.size(); i++) {
                System.out.println((i + 1) + ".  " + courses.get(i));
            }
            System.out.print("Enter the number corresponding to the course for the recipe.\n Type 'exit' to quit: ");
            if (sc.hasNextInt()) {
                int coursenum = sc.nextInt();
                if (coursenum >= 1 && coursenum <= courses.size()) {
                    return courses.get(coursenum - 1);
                }else {
                System.out.println("Invalid Number! Please try again");
                }
            } else {
                String input = sc.next();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting . . .");
                    break;
                } else {
                    System.out.println("Invalid input! Please enter a number or 'exit'");
                }
            }
        }
        return null;
    }

    public void createShoppingList() {
        Set<String> shoppingList = new HashSet<>();

        for (List<Recipe> recipes : holidayMenu.values()) {
            for (Recipe recipe : recipes) {
                List<String> ingredients = dal.getIngredients((recipe.getRecipeName()));
                shoppingList.addAll(ingredients);
            }
        }
        System.out.println("    Shopping List:");
        System.out.println("-------------------");
        for (String ingredient : shoppingList) {
            System.out.println("-  " + ingredient);
        }
    }

    public void displayMenu() {
        for (Map.Entry<String, List<Recipe>> entry : holidayMenu.entrySet()) {
            String course = entry.getKey();
            List<Recipe> recipes = entry.getValue();

            System.out.println("=======================");
            System.out.println("Course: " + course);
            System.out.println("=======================");
            for (Recipe recipe : recipes) {
                System.out.println("  Recipe Name: " + recipe.getRecipeName());
                System.out.println("  Cookbook Name: " + recipe.getCookbookName());
                System.out.println("  Total Servings: " + recipe.getTotalServings());
                List<String> ingredients = dal.getIngredients((recipe.getRecipeName()));
                System.out.println("  Ingredients: " + String.join(", ", ingredients));
                System.out.println("-----------------------");
            }
        }
    }

    public static class Cookbook {
        private String cookbookName;
        private boolean isBook;
        private String website;

        public Cookbook(String cookbookName, boolean isBook, String website) {
            this.cookbookName = cookbookName;
            this.isBook = isBook;
            this.website = website;
        }

        public String getCookbookName() {

            return cookbookName;
        }
        public boolean isBook() {

            return isBook;
        }
        public String getWebsite() {

            return website;
        }
    }

    public static class Recipe {
        private String recipeName;
        private String cookbookName;
        private int totalServings;
        private List<String> ingredients = new ArrayList<>();
        private String course;

        public Recipe(String recipeName, String cookbookName, int totalServings) {
            this.recipeName = recipeName;
            this.cookbookName = cookbookName;
            this.totalServings = totalServings;
        }
        public String getRecipeName() {return recipeName; }
        public String getCookbookName() {return cookbookName; }
        public int getTotalServings() {return totalServings; }
        public void setIngredients(List<String> ingredients){
            this.ingredients.addAll(ingredients);
        }
        public void setCourse(String course) {
            this.course = course;
        }

    }
}
