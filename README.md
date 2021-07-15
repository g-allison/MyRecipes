# README
**MyMeals:** App that allows users to enter their own recipes or use app suggestions and generates a meal plan and grocery list for the week.

## 1. User Stories

**Required Must-have Stories**

 * User can create an account
 * User can log in
 * User can enter own recipes
 * User can pick recipes from the app using recipe API
 * User is able to generate a weekly menu
 * User is able to generate a weekly shopping list
 * Have a main feed where people's recipes are shared in chronological order
     * filter based on ingredients
 * People can follow others (auto approve)

**Optional Nice-to-have Stories**

 * App tracks price of ingrediants and user is able to designate a max budget
 * User can scan a preexisting recipe and have the app auto fill into a recipe card
 * Approve follow requests 
 * Recipes are shareable over messenging
 * Track items in a user's pantry
     * User can add/delete items from pantry
     * Remind users when expiration date is approaching
     * Be able to suggest a recipe based off of what a user has in their pantry already
  * User can scan barcodes of items to upload to pantry

## 2. Screen Archetypes

 * Login Screen
   * User can login
 * Registration Screen
     * User can create a new account
 * Feed
     * User can view a feed of photos of what people are currently eating
     * User can double tap a photo to like
     * User can post a new photo to their feed
     * User can search for other users
     * User can follow/unfollow another user
 * Weekly Meal Plan
     * User can enter own recipes or pick from app
     * User is able to generate a weekly menu
     * User is able to generate a weekly shopping list
     * App tracks calories of recipes and user is able to designate a meal plan budget for the week
     * App tracks price of ingrediants and user is able to designate a meal plan budget for the week
     * User can scan a preexisting recipe and have the app auto fill into a recipe card
 * Pantry
     * Track items in a user's pantry
         * User can add/delete items from pantry
         * Remind users when expiration date is approaching
         * Be able to suggest a recipe based off of what a user has in their pantry already
         * User can scan barcodes of items to upload to pantry
 * Messaging
     * User can send and receive recipe cards to individuals and groups

## 3. Navigation

**Tab Navigation** (Tab to Screen)

 * Home Feed
     * Search User
     * Post a Photo
 * View pantry
 * Weekly menu
 * Messaging

**Flow Navigation** (Screen to Screen)

 * Login Screen
   * Home
 * Registration Screen
   * Home
 * Stream Screen
     * Search User
     * Post a photo
 * Weekly Menu
     * Add new plan
 * Pantry
     * Add new item
     * Suggest a recipe based on current items
 * Messaging
     * View current conversations
     * Compose new message
         * Start new group
