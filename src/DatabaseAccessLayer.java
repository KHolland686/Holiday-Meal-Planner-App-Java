import java.util.*;
import java.sql.*;
import java.util.List;

public class DatabaseAccessLayer {
    private static final Scanner sc = new Scanner(System.in);
    private static final String DBURL = "jdbc:mysql://localhost:3306/mealplanning";
    private Connection connect;
    public DatabaseAccessLayer() {
        this.connect = connect();
    }
    public Connection connect() {
        try {
            if (connect != null && !connect.isClosed()) {
                return connect;
            }
            System.out.print("Enter database username: ");
            String DBUser = sc.nextLine();

            System.out.print("Enter database password: ");
            String DBPassword = sc.nextLine();

            return DriverManager.getConnection(DBURL, DBUser, DBPassword);
        } catch (SQLException e) {
            // Handle any SQL-related exceptions
            System.out.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }

    public List<MenuManager.Cookbook> getCookbooks(){
        List<MenuManager.Cookbook> cookbooks = new ArrayList<>();

        try (CallableStatement callableStatement = connect.prepareCall("{call GetCookbooks()}")) {
            ResultSet myRelation = callableStatement.executeQuery();

            while (myRelation.next()) {
                String cookbookName = myRelation.getString("CookbookName");
                boolean isBook = myRelation.getBoolean("IsBook");
                String website = myRelation.getString("Website");

                MenuManager.Cookbook cookbook = new MenuManager.Cookbook(cookbookName, isBook, website);
                cookbooks.add(cookbook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cookbooks;
    }

    public List<MenuManager.Recipe> getRecipes(String cookbookName) {
        List<MenuManager.Recipe> recipes = new ArrayList<>();

        try (CallableStatement myStoredProcedureCall = connect.prepareCall("{Call GetRecipesForCookbook(?)}")) {
            myStoredProcedureCall.setString(1, cookbookName);
            ResultSet myRelation = myStoredProcedureCall.executeQuery();

            while (myRelation.next()) {
                String recipeName = myRelation.getString("RecipeName");
                int totalServings = myRelation.getInt("TotalServings");
                MenuManager.Recipe recipe = new MenuManager.Recipe(recipeName, cookbookName, totalServings);
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public List<String> getIngredients(String recipeName) {
        List<String> ingredients = new ArrayList<>();

        try (CallableStatement myStoredProcedure = connect.prepareCall("{Call IngredientsInRecipe(?)}")) {
            myStoredProcedure.setString(1, recipeName);
            ResultSet myRelation = myStoredProcedure.executeQuery();

            while (myRelation.next()) {
                String ingredientName = myRelation.getString("IngredientsInRecipe");
                ingredients.add(ingredientName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }
}