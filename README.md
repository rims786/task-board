# Task Board Application

## Overview
This is a simple task board application that allows users to create and manage different lists of tasks. The application is built using Spring Boot and Maven, and it uses an H2 file-based database for persistence.

## Features
- View all lists and tasks
- Create an empty list with a name
- Add new tasks to an existing list with a name and description
- Update the name and description of tasks within a list
- Delete a task from a list
- Delete an entire list with all its tasks
- Move tasks to a different list

## Requirements
- Java 17
- Maven
- Docker (for containerization)

## Setup and Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/task-board.git
    cd task-board
    ```

2. Build the application:
    ```sh
    ./mvnw clean package
    ```

3. Run the application:
    ```sh
    java -jar target/task-board-1.0.0.jar
    ```

4. Alternatively, you can run the application using Docker:
    ```sh
    docker-compose up --build
    ```

## API Endpoints
- **GET /api/tasklists**: Get all task lists
	- Example: http://localhost:8081/api/tasks/1
- **POST /api/tasklists**: Create a new task list
	- Example: http://localhost:8080/api/tasklists?name=NewTaskList
- **POST /api/tasklists/{listId}/tasks**: Add a new task to a list
	- Example: http://localhost:8080/api/tasklists/1/tasks?name=MyTask&description=Description
- **PUT /tasks/{taskId}**: Update a task
- **DELETE /tasks/{taskId}**: Delete a task
	- Example: http://localhost:8081/api/tasks/1
- **DELETE /tasklists/{listId}**: Delete a task list
	- Example: http://localhost:8080/api/tasklists/1
- **PUT /tasks/{taskId}/move**: Move a task to a different list

## License
This project is licensed under the MIT License - see the LICENSE file for details.


## HTTP Request Examples

    - Get all task lists
        Method: GET
        URL: http://localhost:8080/api/tasklists
    -Create a new task list
    Method: POST
        URL: http://localhost:8080/api/tasklists
        Params: name=New List
    - Add a new task to a list
        Method: POST
        URL: http://localhost:8080/api/tasklists/{listId}/tasks
        Params: name=New Task&description=Task Description
    - Update a task
        Method: PUT
        URL: http://localhost:8080/tasks/{taskId}
        Params: name=Updated Task&description=Updated Description
    - Delete a task
        Method: DELETE
        URL: http://localhost:8080/tasks/{taskId}
    - Delete a task list
        Method: DELETE
        URL: http://localhost:8080/api/tasklists/{listId}
    - Move a task to a different list
        Method: PUT
        URL: http://localhost:8080/tasks/{taskId}/move
        Params: newListId={newListId}

##  Database
	- For this application, H2 file-based database for simplicity and ease of setup. 
  - H2 is lightweight and perfect for development and testing purposes. 
  - It allows the application to run without requiring an external database setup, 
	making it easier to get started quickly. 
  - The data is stored in a file, ensuring persistence across application restarts.
  - When you start the application, Spring Boot will automatically execute 
	the schema.sql and data.sql files to set up the database schema and populate it with the sample data.
  - (Optional): Open H2-Console
   - Add following sql command:
       - SELECT * FROM task_list;
       - SELECT * FROM task;
   - Common Issues and Fixes
     - SQL Files Not Executing: Ensure the SQL files are named exactly schema.sql and data.sql and are placed in the src/main/resources directory.
     - Incorrect JDBC URL: Double-check the JDBC URL in the H2 console matches the one in application.properties.
     - Application Not Restarted: Make sure to restart your Spring Boot application after making changes to the SQL files or application.properties.
     - Example output for the task_list table:
         id	name
         1	Personal
         2	Work
         3	Shopping
     
     - Example output for the task_list table:
       id name description task_list_id
       1 Buy groceries Milk, Bread, Eggs 3
       2 Finish report Complete the quarterly report 2
       3 Call plumber Fix the kitchen sink 1
       4 Schedule meeting Team meeting at 10 AM 2
       5 Buy birthday gift  for wife 1
     
	- Note: The data.sql file is required for this application only when data is not populated.

## Project structure

    task-board
    ├── data
    │   └── taskboard.mv.db
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── com
    │   │   │       └── example
    │   │   │           └── taskboard
    │   │   │               ├── controller
    │   │   │               │   └── TaskController.java
    │   │   │               ├── model
    │   │   │               │   ├── Task.java
    │   │   │               │   └── TaskList.java
    │   │   │               ├── repository
    │   │   │               │   └── TaskRepository.java
    │   │   │               ├── service
    │   │   │               │   └── TaskService.java
    │   │   │               └── TaskBoardApplication.java
    │   │   └── resources
    │   │       ├── application.properties
    │   │       ├── schema.sql
    │   │       └── data.sql
    │   └── test
    │       └── java
    │           └── com
    │               └── example
    │                   └── taskboard
    │                       └── TaskBoardApplicationTests.java
    ├── Dockerfile
    ├── docker-compose.yml
    ├── pom.xml
    ├── README.md
    └── LICENSE
