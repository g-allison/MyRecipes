# javascript-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>com.spoonacular</groupId>
    <artifactId>javascript-client</artifactId>
    <version>1.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.spoonacular:javascript-client:1.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

- target/javascript-client-1.0.jar
- target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import com.spoonacular.DefaultApi;

public class DefaultApiExample {

    public static void main(String[] args) {
        DefaultApi apiInstance = new DefaultApi();
        String username = dsky; // String | The username.
        String hash = 4b5v4398573406; // String | The private hash for the username.
        InlineObject11 inlineObject11 = new InlineObject11(); // InlineObject11 | 
        try {
            Object result = apiInstance.addToMealPlan(username, hash, inlineObject11);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#addToMealPlan");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://api.spoonacular.com*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*DefaultApi* | [**addToMealPlan**](docs/DefaultApi.md#addToMealPlan) | **POST** /mealplanner/{username}/items | Add to Meal Plan
*DefaultApi* | [**addToShoppingList**](docs/DefaultApi.md#addToShoppingList) | **POST** /mealplanner/{username}/shopping-list/items | Add to Shopping List
*DefaultApi* | [**analyzeARecipeSearchQuery**](docs/DefaultApi.md#analyzeARecipeSearchQuery) | **GET** /recipes/queries/analyze | Analyze a Recipe Search Query
*DefaultApi* | [**analyzeRecipeInstructions**](docs/DefaultApi.md#analyzeRecipeInstructions) | **POST** /recipes/analyzeInstructions | Analyze Recipe Instructions
*DefaultApi* | [**autocompleteIngredientSearch**](docs/DefaultApi.md#autocompleteIngredientSearch) | **GET** /food/ingredients/autocomplete | Autocomplete Ingredient Search
*DefaultApi* | [**autocompleteMenuItemSearch**](docs/DefaultApi.md#autocompleteMenuItemSearch) | **GET** /food/menuItems/suggest | Autocomplete Menu Item Search
*DefaultApi* | [**autocompleteProductSearch**](docs/DefaultApi.md#autocompleteProductSearch) | **GET** /food/products/suggest | Autocomplete Product Search
*DefaultApi* | [**autocompleteRecipeSearch**](docs/DefaultApi.md#autocompleteRecipeSearch) | **GET** /recipes/autocomplete | Autocomplete Recipe Search
*DefaultApi* | [**classifyCuisine**](docs/DefaultApi.md#classifyCuisine) | **POST** /recipes/cuisine | Classify Cuisine
*DefaultApi* | [**classifyGroceryProduct**](docs/DefaultApi.md#classifyGroceryProduct) | **POST** /food/products/classify | Classify Grocery Product
*DefaultApi* | [**classifyGroceryProductBulk**](docs/DefaultApi.md#classifyGroceryProductBulk) | **POST** /food/products/classifyBatch | Classify Grocery Product Bulk
*DefaultApi* | [**clearMealPlanDay**](docs/DefaultApi.md#clearMealPlanDay) | **DELETE** /mealplanner/{username}/day/{date} | Clear Meal Plan Day
*DefaultApi* | [**computeGlycemicLoad**](docs/DefaultApi.md#computeGlycemicLoad) | **POST** /food/ingredients/glycemicLoad | Compute Glycemic Load
*DefaultApi* | [**connectUser**](docs/DefaultApi.md#connectUser) | **POST** /users/connect | Connect User
*DefaultApi* | [**convertAmounts**](docs/DefaultApi.md#convertAmounts) | **GET** /recipes/convert | Convert Amounts
*DefaultApi* | [**createRecipeCard**](docs/DefaultApi.md#createRecipeCard) | **POST** /recipes/visualizeRecipe | Create Recipe Card
*DefaultApi* | [**deleteFromMealPlan**](docs/DefaultApi.md#deleteFromMealPlan) | **DELETE** /mealplanner/{username}/items/{id} | Delete from Meal Plan
*DefaultApi* | [**deleteFromShoppingList**](docs/DefaultApi.md#deleteFromShoppingList) | **DELETE** /mealplanner/{username}/shopping-list/items/{id} | Delete from Shopping List
*DefaultApi* | [**detectFoodInText**](docs/DefaultApi.md#detectFoodInText) | **POST** /food/detect | Detect Food in Text
*DefaultApi* | [**extractRecipeFromWebsite**](docs/DefaultApi.md#extractRecipeFromWebsite) | **GET** /recipes/extract | Extract Recipe from Website
*DefaultApi* | [**generateMealPlan**](docs/DefaultApi.md#generateMealPlan) | **GET** /mealplanner/generate | Generate Meal Plan
*DefaultApi* | [**generateShoppingList**](docs/DefaultApi.md#generateShoppingList) | **POST** /mealplanner/{username}/shopping-list/{start-date}/{end-date} | Generate Shopping List
*DefaultApi* | [**getARandomFoodJoke**](docs/DefaultApi.md#getARandomFoodJoke) | **GET** /food/jokes/random | Get a Random Food Joke
*DefaultApi* | [**getAnalyzedRecipeInstructions**](docs/DefaultApi.md#getAnalyzedRecipeInstructions) | **GET** /recipes/{id}/analyzedInstructions | Get Analyzed Recipe Instructions
*DefaultApi* | [**getComparableProducts**](docs/DefaultApi.md#getComparableProducts) | **GET** /food/products/upc/{upc}/comparable | Get Comparable Products
*DefaultApi* | [**getConversationSuggests**](docs/DefaultApi.md#getConversationSuggests) | **GET** /food/converse/suggest | Get Conversation Suggests
*DefaultApi* | [**getDishPairingForWine**](docs/DefaultApi.md#getDishPairingForWine) | **GET** /food/wine/dishes | Get Dish Pairing for Wine
*DefaultApi* | [**getIngredientInformation**](docs/DefaultApi.md#getIngredientInformation) | **GET** /food/ingredients/{id}/information | Get Ingredient Information
*DefaultApi* | [**getIngredientSubstitutes**](docs/DefaultApi.md#getIngredientSubstitutes) | **GET** /food/ingredients/substitutes | Get Ingredient Substitutes
*DefaultApi* | [**getIngredientSubstitutesByID**](docs/DefaultApi.md#getIngredientSubstitutesByID) | **GET** /food/ingredients/{id}/substitutes | Get Ingredient Substitutes by ID
*DefaultApi* | [**getMealPlanTemplate**](docs/DefaultApi.md#getMealPlanTemplate) | **GET** /mealplanner/{username}/templates/{id} | Get Meal Plan Template
*DefaultApi* | [**getMealPlanTemplates**](docs/DefaultApi.md#getMealPlanTemplates) | **GET** /mealplanner/{username}/templates | Get Meal Plan Templates
*DefaultApi* | [**getMealPlanWeek**](docs/DefaultApi.md#getMealPlanWeek) | **GET** /mealplanner/{username}/week/{start-date} | Get Meal Plan Week
*DefaultApi* | [**getMenuItemInformation**](docs/DefaultApi.md#getMenuItemInformation) | **GET** /food/menuItems/{id} | Get Menu Item Information
*DefaultApi* | [**getProductInformation**](docs/DefaultApi.md#getProductInformation) | **GET** /food/products/{id} | Get Product Information
*DefaultApi* | [**getRandomFoodTrivia**](docs/DefaultApi.md#getRandomFoodTrivia) | **GET** /food/trivia/random | Get Random Food Trivia
*DefaultApi* | [**getRandomRecipes**](docs/DefaultApi.md#getRandomRecipes) | **GET** /recipes/random | Get Random Recipes
*DefaultApi* | [**getRecipeEquipmentByID**](docs/DefaultApi.md#getRecipeEquipmentByID) | **GET** /recipes/{id}/equipmentWidget.json | Get Recipe Equipment by ID
*DefaultApi* | [**getRecipeInformation**](docs/DefaultApi.md#getRecipeInformation) | **GET** /recipes/{id}/information | Get Recipe Information
*DefaultApi* | [**getRecipeInformationBulk**](docs/DefaultApi.md#getRecipeInformationBulk) | **GET** /recipes/informationBulk | Get Recipe Information Bulk
*DefaultApi* | [**getRecipeIngredientsByID**](docs/DefaultApi.md#getRecipeIngredientsByID) | **GET** /recipes/{id}/ingredientWidget.json | Get Recipe Ingredients by ID
*DefaultApi* | [**getRecipeNutritionWidgetByID**](docs/DefaultApi.md#getRecipeNutritionWidgetByID) | **GET** /recipes/{id}/nutritionWidget.json | Get Recipe Nutrition Widget by ID
*DefaultApi* | [**getRecipePriceBreakdownByID**](docs/DefaultApi.md#getRecipePriceBreakdownByID) | **GET** /recipes/{id}/priceBreakdownWidget.json | Get Recipe Price Breakdown by ID
*DefaultApi* | [**getRecipeTasteByID**](docs/DefaultApi.md#getRecipeTasteByID) | **GET** /recipes/{id}/tasteWidget.json | Get Recipe Taste by ID
*DefaultApi* | [**getShoppingList**](docs/DefaultApi.md#getShoppingList) | **GET** /mealplanner/{username}/shopping-list | Get Shopping List
*DefaultApi* | [**getSimilarRecipes**](docs/DefaultApi.md#getSimilarRecipes) | **GET** /recipes/{id}/similar | Get Similar Recipes
*DefaultApi* | [**getWineDescription**](docs/DefaultApi.md#getWineDescription) | **GET** /food/wine/description | Get Wine Description
*DefaultApi* | [**getWinePairing**](docs/DefaultApi.md#getWinePairing) | **GET** /food/wine/pairing | Get Wine Pairing
*DefaultApi* | [**getWineRecommendation**](docs/DefaultApi.md#getWineRecommendation) | **GET** /food/wine/recommendation | Get Wine Recommendation
*DefaultApi* | [**guessNutritionByDishName**](docs/DefaultApi.md#guessNutritionByDishName) | **GET** /recipes/guessNutrition | Guess Nutrition by Dish Name
*DefaultApi* | [**imageAnalysisByURL**](docs/DefaultApi.md#imageAnalysisByURL) | **GET** /food/images/analyze | Image Analysis by URL
*DefaultApi* | [**imageClassificationByURL**](docs/DefaultApi.md#imageClassificationByURL) | **GET** /food/images/classify | Image Classification by URL
*DefaultApi* | [**ingredientSearch**](docs/DefaultApi.md#ingredientSearch) | **GET** /food/ingredients/search | Ingredient Search
*DefaultApi* | [**mapIngredientsToGroceryProducts**](docs/DefaultApi.md#mapIngredientsToGroceryProducts) | **POST** /food/ingredients/map | Map Ingredients to Grocery Products
*DefaultApi* | [**parseIngredients**](docs/DefaultApi.md#parseIngredients) | **POST** /recipes/parseIngredients | Parse Ingredients
*DefaultApi* | [**quickAnswer**](docs/DefaultApi.md#quickAnswer) | **GET** /recipes/quickAnswer | Quick Answer
*DefaultApi* | [**searchAllFood**](docs/DefaultApi.md#searchAllFood) | **GET** /food/search | Search All Food
*DefaultApi* | [**searchCustomFoods**](docs/DefaultApi.md#searchCustomFoods) | **GET** /food/customFoods/search | Search Custom Foods
*DefaultApi* | [**searchFoodVideos**](docs/DefaultApi.md#searchFoodVideos) | **GET** /food/videos/search | Search Food Videos
*DefaultApi* | [**searchGroceryProducts**](docs/DefaultApi.md#searchGroceryProducts) | **GET** /food/products/search | Search Grocery Products
*DefaultApi* | [**searchGroceryProductsByUPC**](docs/DefaultApi.md#searchGroceryProductsByUPC) | **GET** /food/products/upc/{upc} | Search Grocery Products by UPC
*DefaultApi* | [**searchMenuItems**](docs/DefaultApi.md#searchMenuItems) | **GET** /food/menuItems/search | Search Menu Items
*DefaultApi* | [**searchRecipes**](docs/DefaultApi.md#searchRecipes) | **GET** /recipes/complexSearch | Search Recipes
*DefaultApi* | [**searchRecipesByIngredients**](docs/DefaultApi.md#searchRecipesByIngredients) | **GET** /recipes/findByIngredients | Search Recipes by Ingredients
*DefaultApi* | [**searchRecipesByNutrients**](docs/DefaultApi.md#searchRecipesByNutrients) | **GET** /recipes/findByNutrients | Search Recipes by Nutrients
*DefaultApi* | [**searchSiteContent**](docs/DefaultApi.md#searchSiteContent) | **GET** /food/site/search | Search Site Content
*DefaultApi* | [**summarizeRecipe**](docs/DefaultApi.md#summarizeRecipe) | **GET** /recipes/{id}/summary | Summarize Recipe
*DefaultApi* | [**talkToChatbot**](docs/DefaultApi.md#talkToChatbot) | **GET** /food/converse | Talk to Chatbot
*DefaultApi* | [**visualizeEquipment**](docs/DefaultApi.md#visualizeEquipment) | **POST** /recipes/visualizeEquipment | Visualize Equipment
*DefaultApi* | [**visualizeIngredients**](docs/DefaultApi.md#visualizeIngredients) | **POST** /recipes/visualizeIngredients | Visualize Ingredients
*DefaultApi* | [**visualizeMenuItemNutritionByID**](docs/DefaultApi.md#visualizeMenuItemNutritionByID) | **GET** /food/menuItems/{id}/nutritionWidget | Visualize Menu Item Nutrition by ID
*DefaultApi* | [**visualizePriceBreakdown**](docs/DefaultApi.md#visualizePriceBreakdown) | **POST** /recipes/visualizePriceEstimator | Visualize Price Breakdown
*DefaultApi* | [**visualizeProductNutritionByID**](docs/DefaultApi.md#visualizeProductNutritionByID) | **GET** /food/products/{id}/nutritionWidget | Visualize Product Nutrition by ID
*DefaultApi* | [**visualizeRecipeEquipmentByID**](docs/DefaultApi.md#visualizeRecipeEquipmentByID) | **GET** /recipes/{id}/equipmentWidget | Visualize Recipe Equipment by ID
*DefaultApi* | [**visualizeRecipeIngredientsByID**](docs/DefaultApi.md#visualizeRecipeIngredientsByID) | **GET** /recipes/{id}/ingredientWidget | Visualize Recipe Ingredients by ID
*DefaultApi* | [**visualizeRecipeNutrition**](docs/DefaultApi.md#visualizeRecipeNutrition) | **POST** /recipes/visualizeNutrition | Visualize Recipe Nutrition
*DefaultApi* | [**visualizeRecipeNutritionByID**](docs/DefaultApi.md#visualizeRecipeNutritionByID) | **GET** /recipes/{id}/nutritionWidget | Visualize Recipe Nutrition by ID
*DefaultApi* | [**visualizeRecipePriceBreakdownByID**](docs/DefaultApi.md#visualizeRecipePriceBreakdownByID) | **GET** /recipes/{id}/priceBreakdownWidget | Visualize Recipe Price Breakdown by ID
*DefaultApi* | [**visualizeRecipeTaste**](docs/DefaultApi.md#visualizeRecipeTaste) | **POST** /recipes/visualizeTaste | Visualize Recipe Taste
*DefaultApi* | [**visualizeRecipeTasteByID**](docs/DefaultApi.md#visualizeRecipeTasteByID) | **GET** /recipes/{id}/tasteWidget | Visualize Recipe Taste by ID


## Documentation for Models

 - [InlineObject](docs/InlineObject.md)
 - [InlineObject1](docs/InlineObject1.md)
 - [InlineObject10](docs/InlineObject10.md)
 - [InlineObject11](docs/InlineObject11.md)
 - [InlineObject12](docs/InlineObject12.md)
 - [InlineObject13](docs/InlineObject13.md)
 - [InlineObject14](docs/InlineObject14.md)
 - [InlineObject15](docs/InlineObject15.md)
 - [InlineObject16](docs/InlineObject16.md)
 - [InlineObject2](docs/InlineObject2.md)
 - [InlineObject3](docs/InlineObject3.md)
 - [InlineObject4](docs/InlineObject4.md)
 - [InlineObject5](docs/InlineObject5.md)
 - [InlineObject6](docs/InlineObject6.md)
 - [InlineObject7](docs/InlineObject7.md)
 - [InlineObject8](docs/InlineObject8.md)
 - [InlineObject9](docs/InlineObject9.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### apiKeyScheme

- **Type**: API key

- **API key parameter name**: apiKey
- **Location**: URL query string


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

david@spoonacular.com

