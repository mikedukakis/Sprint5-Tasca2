# 🐾 Virtual Pet API

<br/>
<br/>
A reactive RESTful API that allows users to create and manage virtual pets with functionality to track pet stats and handle user accounts.

The API provides endpoints for creating and caring for pets, managing user profiles with Spring Boot security through authentication by login and authorisation with JWT. It uses MongoDB for game data persistence.

<br/>

###  📄 Project Statement

The Virtual Pet API enables users to create and manage virtual pets. Features include CRUD operations for pets, user authentication, and interactive pet actions. The API also tracks player statistics and stores it all in a MongoDB database.

<br/>

###  💻 Technologies Used

-     Java
-     Spring Boot
-     MongoDB (for user and virtual pet persistence)
-     Reactive programming with Project Reactor
-     Swagger for API documentation
-     Maven for project build and dependency management

<br/>

### 📋 Requirements

-     Java 21 or higher
-     Spring Boot 3.3.5 or higher
-     Maven 3.9.9 or higher
-     MongoDB v4.4+
-     MySQL (latest version recommended)
-     Internet connection for downloading dependencies

<br/>

###  🛠️ Installation

##### 1. Clone this repository:

`git clone https://github.com/mikedukakis/Sprint5-Tasca2.git`

##### 2. Navigate to the project directory:

`cd Sprint5-Tasca2`

##### 3. Install dependencies using Maven:

`mvn clean install`

##### 4. Configure the application:
        
Update your MongoDB connection details in `application.properties` located in the `src/main/resources directory`like so:
`spring.data.mongodb.uri=mongodb://localhost:27017/virtualpet`



<br/>

###  ▶️ Execution

##### 1. Start MongoDB on your local machine or on a configured server.

##### 2. Run the application:

`mvn spring-boot:run`

##### 3. Access the API: The application will be available at `http://localhost:8080`.

<br/>

###  🌐 API Documentation

The API documentation is available via Swagger:

    Swagger UI

<br/>

###  🌐 Deployment

- Prepare the production environment.

- Upload the project files to your production server.

- Configure the server to connect to the production databases for MongoDB

- Set environment variables for MongoDB configuration in the production environment.

- Verify the application setup by checking the Swagger documentation.

<br/>

###  📚 API Endpoints

<br/>

####  Authentication Endpoints

<br/>

#####  Register a New User

    URL: /virtualpet/auth/register
    Method: POST
    Request Body:
        username (String): Username of the new user.
        password (String): Password for the new user.
    Response: 200 OK with a JSON object containing:
        access_token: The JWT access token for the registered user.
        refresh_token: The JWT refresh token for the registered user.

##### User Login

    URL: /virtualpet/auth/login
    Method: POST
    Request Body:
        username (String): Username of the user.
        password (String): Password for the user.
    Response: 200 OK with a JSON object containing:
        access_token: The JWT access token for the authenticated user.
        refresh_token: The JWT refresh token for the authenticated user.
        
##### User Logout

    URL: /virtualpet/auth/logout
    Method: POST
    Description: Logs the user out by revoking the token in the request headers.
    Response: 200 OK upon successful logout.

##### Refresh Access Token

    URL: /virtualpet/auth/refresh-token
    Method: POST
    Description: Refreshes the access token for the authenticated user. Requires a valid refresh token in the request headers.
    Response: 200 OK with a refreshed access token.

<br/>

####  User Management Endpoints

<br/>

##### Display Login Page

    URL: /virtualpet/user/login
    Method: GET
    Description: Serves the login HTML page.

##### Display User's Pets Page

    URL: /virtualpet/user/mypets
    Method: GET
    Description: Serves the user's pets page.

##### Get user profile (role)

    URL: /virtualpet/user/profile
    Method: GET
    Description: Serves the user's username and role.

<br/>

#### Virtual Pet Endpoints

<br/>

##### Retrieve All Pets (Only Admin role)

    URL: /virtualpet/pet/allpets
    Method: GET
    Description: Retrieves all pets by any user if the requester has role Admin.
    Response: 200 OK with a list of all pets.

##### Retrieve All User’s Pets

    URL: /virtualpet/pet/mypets
    Method: GET
    Description: Retrieves all pets associated with the authenticated user.
    Response: 200 OK with a list of pets for the user.

##### Create a New Pet

    URL: /virtualpet/pet/new
    Method: POST
    Request Body:
        name (String): Name of the new pet.
        petType (String): Type of the pet (DOG or CAT - in capital letters).
        colour (String): Color of the pet.
    Response: 201 Created with the details of the new pet created for the user.

##### Find a Pet by Name

    URL: /virtualpet/pet/find/{name}
    Method: GET
    Path Variable:
        name (String): Name of the pet to retrieve.
    Response: 200 OK with pet details if found, or 404 Not Found if no pet exists with the specified name.

##### Delete a Pet

    URL: /virtualpet/pet/delete/{petId}
    Method: DELETE
    Path Variable:
        petId (String): ID of the pet to delete.
    Response: 204 No Content if the pet was deleted successfully, or 404 Not Found if the pet was not found or the user is unauthorized.

##### Feed a Pet

    URL: /virtualpet/pet/feed/{petId}
    Method: POST
    Path Variable:
        petId (String): ID of the pet to feed.
    Response: 200 OK if the hunger state of the pet has been saved.

##### Pet a Pet

    URL: /virtualpet/pet/pet/{petId}
    Method: POST
    Path Variable:
        petId (String): ID of the pet to pet.
    Response: 200 OK if the mood (happy) state of the pet has been saved.

<br/>

###  🤝 Contributions

Contributions are welcome! Follow these steps to contribute:

##### 1. Fork the repository.
##### 2. Create a new branch for your feature:

`git checkout -b feature/NewFeature`

##### 3. Make your changes and commit:

`git commit -m 'Add New Feature'`

##### 4. Push your changes:

`git push origin feature/NewFeature`

##### 5. Create a pull request for review.