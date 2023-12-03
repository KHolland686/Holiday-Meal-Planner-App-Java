# Holiday Meal Planner App
 Python app allowing users to organize holiday meals. Allows users to create, manage and customize their holiday meals, add and edit recipes,  and plan shopping lists. Built upon a database created by Professor Wendy Minton for Merrimack College

## Usage
1. Clone the repository.
2. Run the provided database and stored procedures SQL files:
   - `RUN ME FIRST - Mealplanning database.sql`
   - `RUN ME SECOND- Stored Procedures for Holiday Meal Planner.sql`
3. Open the `HolidayMealPlanner.java` in your preferred Java environment.
4. Run the app. It will ask you to enter your database credentials during execution.

## Project Structure
- `RUN ME FIRST.sql`: SQL file for initializing the database.
- `RUN ME SECOND.sql`: SQL file containing stored procedures.
- **src**: Contains the source code for the Holiday Meal Planner application.
  - `HolidayMealPlanner.java`: Presentation layer for the app. Run this 
  - `MenuManager.java`: Business Logic Layer (BLL) responsible for managing the menu.
  - `DatabaseAccessLayer.java`: Handles database interactions.
  - `TestDAL.java`: Testing class for the DatabaseAccessLayer.
