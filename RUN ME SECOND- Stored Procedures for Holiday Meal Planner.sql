-- PROCEDURES/FUNCTIONS FOR HOLIDAY MEAL PLANNER
DROP PROCEDURE IF EXISTS GetCookbooks;
DELIMITER **
CREATE PROCEDURE GetCookbooks()
BEGIN
    SELECT * FROM Cookbook;
END **
DELIMITER ;


DROP PROCEDURE IF EXISTS GetRecipesForCookbook

DELIMITER **
CREATE PROCEDURE GetRecipesForCookbook(IN cookbookNameP VARCHAR(255))
BEGIN
    SELECT * FROM Recipe WHERE CookbookName = cookbookNameP;
END **
DELIMITER ;


DELIMITER $$

DROP FUNCTION IF EXISTS doesRecipeExist;$$
CREATE FUNCTION doesRecipeExist(myRecipeName VARCHAR(200))
RETURNS BOOLEAN DETERMINISTIC
BEGIN

-- And yet another to way to use variables and null checks
 declare result varchar(200);
  SET result = (SELECT RecipeName FROM Recipe WHERE RecipeName = myRecipeName);
    RETURN (result IS NOT NULL);

END;$$

DELIMITER ;


DROP PROCEDURE IF EXISTS IngredientsInRecipe;

DELIMITER **
CREATE PROCEDURE IngredientsInRecipe (
IN Recipe varchar(100)
)
BEGIN
 
IF DoesRecipeExist(Recipe) THEN
	SELECT i.IngredientName AS IngredientsInRecipe
	FROM MEAL AS m
	LEFT OUTER JOIN Ingredients AS i
	ON m.IngredientID = i.Id
	WHERE m.RecipeName = Recipe
	ORDER BY i.IngredientName;
ELSE
	SELECT 'Recipe doesn''t exist!' AS IngredientsInRecipe;
END IF;
END**
DELIMITER ;