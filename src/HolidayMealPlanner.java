import java.util.*;

public class HolidayMealPlanner {
    private static final Scanner sc = new Scanner(System.in);
    private static final MenuManager MM = new MenuManager();

    public static void main(String[] args) {
        while (true) {
            displayMain();
        }
    }

    private static void displayMain() {
        int choice;
        do {
            System.out.println("""
                    ===============================
                    Holiday Meal Planning Menu:
                    -------------------------------
                    1. Select Cookbook
                    2. Display Holiday Menu
                    3. Create Shopping List
                    4. Exit
                    ===============================
                    """);
            choice = getChoice();
            switch (choice) {
                case 1:
                    selectCookbook();
                    break;
                case 2:
                    MM.displayMenu();
                    break;
                case 3:
                    MM.createShoppingList();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid input! Please try again.");
            }
        } while (choice != 4);
    }

    private static int getChoice() {
        int choice = -1;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter the number corresponding to your choice: ");
            try {
                choice = sc.nextInt();
                if (choice >= 1 && choice <= 4) {
                    isValid = true;
                } else {
                    System.out.println("Invalid input! Please enter a number from 1-4.");
                }
            } catch (InputMismatchException e) {
                System.out.println(("Invalid input! Please enter valid number."));
                sc.next();
            }
        }
        return choice;
    }

    private static void selectCookbook() {
        MenuManager.Cookbook chosenCookbook = null;
        do {
            chosenCookbook = MM.selectCookbook();

            if (chosenCookbook != null) {
                System.out.println("=======================");
                System.out.println(chosenCookbook.getCookbookName());
                System.out.println("=======================");
                MM.displayRecipes(chosenCookbook.getCookbookName());
                displayCookbookSubMenu(chosenCookbook);
            } else {
                System.out.println("Invalid input! Try again.");
            }
        } while (chosenCookbook == null);
    }

    private static void displayCookbookSubMenu(MenuManager.Cookbook chosenCookbook) {
        int choice;
        do {
            System.out.println("""
                    ===============================
                    Cookbook Menu:
                    -------------------------------
                    1. Select Recipe
                    2. Back to Cookbook
                    3. Main Menu
                    4. Exit
                    ===============================
                    """);
            choice = getChoice();
            switch (choice) {
                case 1:
                    selectRecipe(chosenCookbook);
                    break;
                case 2:
                    selectCookbook();
                    break;
                case 3:
                    displayMain();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid input! Please try again.");
            }
        } while (choice != 3);
    }

    private static void selectRecipe(MenuManager.Cookbook chosenCookbook) {
        MM.displayRecipes(chosenCookbook.getCookbookName());
        MenuManager.Recipe chosenRecipe = null;
        do {
            chosenRecipe = MM.selectRecipe(chosenCookbook.getCookbookName());
            if (chosenRecipe != null) {
                System.out.println("=======================");
                System.out.println(chosenRecipe.getRecipeName());
                System.out.println("=======================");
                displayRecipeSubMenu(chosenRecipe);
            } else {
                System.out.println("Invalid input! Try again.");
            }

        } while (chosenRecipe == null);
    }

    private static void displayRecipeSubMenu(MenuManager.Recipe chosenRecipe) {
        int choice;
        do {
            System.out.println("""
                    ===============================
                    Recipe Menu:
                    -------------------------------
                    1. View Ingredients
                    2. Save to Holiday Menu
                    3. Main Menu
                    4. Exit
                    ===============================
                    """);
            choice = getChoice();
            switch (choice) {
                case 1:
                    MM.viewRecipeIngredients(chosenRecipe.getRecipeName());
                    break;
                case 2:
                    MM.saveRecipeToMenu(chosenRecipe);
                    displayMain();
                    break;
                case 3:
                    displayMain();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid input! Please try again.");
            }
        } while (choice != 3);
    }

//    private static void viewRecipeIngredients(MenuManager.Recipe chosenRecipe) {
//        List<String> ingredients = MM.viewRecipeIngredients(chosenRecipe.getRecipeName());
//        System.out.println("Ingredients in " + chosenRecipe.getRecipeName());
//        System.out.println("========================");
//        for (String ingredient : ingredients) {
//            System.out.println(ingredient);
//        }
//    }
}