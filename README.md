
# Tour Planner SWEN2 SS2024 Group 5

## Description
This is our JavaFX project where users can create and rate their hiking, running, or cycling tours. Users can also view maps, generate reports, and much more. The project utilizes JavaFX with a PostgreSQL database, which is started using Docker. Additional tools include Log4J for logging, Hibernate for database operations, OpenRouteService for generating maps and obtaining coordinates, Mapbox for creating map images, among others.

## Prerequisites to Start the Project
1. Environment (we recommend IntelliJ)
2. JavaFX
3. SceneBuilder (optional)
4. Docker
5. API keys from OpenRouteService and Mapbox
   - Store OpenRouteService here: `C:\Users\<your user>\< create a folder called "TourPlanner" >\API_KEY.txt`
   - Store Mapbox here: `C:\Users\<your user>\< create a folder called "TourPlanner" >\MAPBOX_KEY.txt`

## Getting Started
- Open the project in IntelliJ.
- Open a terminal and switch to the database directory:
  ```
  cd src/database
  ```
- To start Docker and our database:
  ```
  docker-compose up -d
  ```
- To stop them:
  ```
  docker-compose down -v
  ```

## Running the Project
- Launch the project from `MainTourPlanner`.
