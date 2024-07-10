# 3rd Project : Lunch Spot Finder

LunchSpotFinder is a web application that helps users find lunch spots based on their location. Users can input their location and receive recommendations for nearby lunch spots, view them on a map, and add favorites for these spots.


# Tech Stack
## Backend

- Java : A robust and widely-used programming language suitable for building scalable backend services.
- Spring Boot : A Java-based framework that simplifies the development of RESTful web services and microservices.
- MySQL : A reliable and efficient relational database management system for storing application data.

# ✅ Features

1. **Location-based Search**
   - Search for lunch spots based on user-provided location.
2. **Recommendation System**
   - Recommend lunch spots based on user location and preferences.
3. **Map Display**
   - Display search results on a map for easy visualization.
4. **User favorite**
   - View and add favorites for each lunch spot.

# ✅ Project Structure

**backend/: Backend code**

- src/main/java/: Java source files
    - github/yumyum/: Main package
        - controller/: REST controllers
        - service/: Business logic
        - repository/: Database access
        - entity/: Entity definitions
- src/main/resources/: Application configuration files

# ✅Challenges Faced

1. **Calculating Locations**
   - Accurately calculating and handling locations based on user input and integrating this with the Maps API was challenging.
2. **Search Filters**
   - Implementing filters for cuisine type, price range, and ratings to enhance the search experience.
3. **User Authentication**
   - Adding user authentication to provide personalized recommendations and secure user data.
4. **Database Schema Design**
   - Designing a database schema that efficiently handles user data, favorites, and locations was challenging but crucial for the application's performance.



## Development Design Goals

1. **Login/Registration**
    - User authentication through email verification (token issuance)
    - Social login feature (KakaoTalk, Google, Naver)
2. **Games/Chat Room** 
    (Host: Person spinning the game roulette) > Game (ladder game, roulette) in the chat room > Save game history in chat room page to calendar
    - Functionality to conduct games such as roulette in chat rooms > Implement only ladder game?? Or not at all?
    - Function to save game history to calendar
        - Library <HTML5 Canvas> - (Audio) WEB APIs (web audio api, web storage api)
    - Community feature through chat rooms
3. **Restaurant List Restaurant List**
    - Confirm restaurant locations through map page (Naver or KakaoMap)
    - Functionality to add and delete foods users want to add to the list
        
        [Implementing Restaurant List Retrieval API]
        
4. **My Page/Wish List**
    - User details
    - Function to manage user's wish list
    - Posting boards, writing comments and ratings > Board implementation X
    - Function to save uploaded photos to calendar 
    
    💡 https://www.figma.com/file/cWozneAf7QXyj8sSFvxnHC/%EB%83%A0%EB%83%A0-%ED%95%9C%EB%81%BC?type=design&node-id=0%3A1&mode=design&t=SgUWleOKceAVDq9T-1
    
   
