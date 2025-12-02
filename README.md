# Nutrition & Wellness Tracking Application

## About
A Java-based nutrition and wellness tracking application that helps users manage their diet, discover recipes, set nutritional goals, and compete with friends through social features and leaderboards.

## How to Run
`src/main/java/app/StartFromDash.java`

## Features & User Stories

### Account Management
- **Login** - User authentication with session management
- **Create Account** - New user registration
- **Logout** - Session termination

### Profile Management
- **View Profile** - Display user information and friends list
- **Update Profile** - Edit personal information and dietary preferences

### Social Features (incomplete)
- **Add Friend** - Send friend requests to other users
- **Accept Friend Request** - Accept pending friend requests
- **Reject Friend Request** - Decline friend requests
- **Remove Friend** - Remove existing friends
- **View Leaderboard** - Compete with friends on goal completion

### Recipe Management
- **Recipe Search** - Search for recipes by name
- **Save Recipe** - Save favorite recipes
- **View Saved Recipes** - Display saved recipe collection
- **Delete Saved Recipe** - Remove recipes from collection

### Meal Tracking & Nutrition
- **Log Meals** - Track meals with automatic nutritional information lookup
- **Edit Logs** - Edit the nutrition information of logged meals
- **Dashboard** - View daily nutrition totals and progress

### Goals & Competition
- **Set Nutritional Goals** - Create personalized calorie and macro targets
- **Track Goals** - Monitor goal completion


## APIs Used

### TheMealDB API
- **Purpose:** Recipe search and discovery
- **Endpoints:**
  - Search recipes by name
  - Lookup recipe details
- **Data:** Recipe instructions, ingredients, images, categories

### CalorieNinjas API
- **Purpose:** Nutritional information lookup
- **Endpoint:** Nutrition query API
- **Data:** Calories, protein, carbs, fat, fiber, sugar

### University of Toronto Remote Auth Server
- **Purpose:** User authentication and account storage
- **Base URL:** `http://vm003.teach.cs.toronto.edu:20112`
- **Endpoints:** User management and authentication
