# Spring Boot and React Application

This project is a full-stack application that combines a Spring Boot backend with a React frontend. The backend handles authentication and user management, while the frontend provides a custom login interface.

## Project Structure

- **backend/**: Contains the Spring Boot application.
  - **src/main/java/com/ursmahesh/securex/**: Java source files for the application.
    - **SecurexApplication.java**: Main entry point of the Spring Boot application.
    - **config/**: Contains configuration files for Spring Security.
      - **Configure.java**: Configures Spring Security settings.
    - **controller/**: Contains REST controllers.
      - **AuthController.java**: Handles authentication requests.
    - **model/**: Contains model classes.
      - **User.java**: Represents the user entity.
    - **repository/**: Contains Spring Data JPA repositories.
      - **UserRepository.java**: Provides CRUD operations for the User entity.
    - **service/**: Contains service classes.
      - **UserService.java**: Business logic related to user management.
  - **src/main/resources/**: Contains application resources.
    - **application.properties**: Configuration properties for the Spring Boot application.
    - **static/**: Contains static resources.
      - **index.html**: Default HTML page served by the application.
  - **mvnw**: Maven wrapper script for running Maven commands.
  - **mvnw.cmd**: Windows version of the Maven wrapper script.
  - **pom.xml**: Maven configuration file for project dependencies and build settings.
  - **README.md**: Documentation for the backend part of the project.

- **frontend/**: Contains the React application.
  - **public/**: Contains public assets.
    - **index.html**: Main HTML file for the React application.
  - **src/**: Contains React source files.
    - **App.js**: Main component of the React application.
    - **index.js**: Entry point for the React application.
    - **components/**: Contains React components.
      - **Login.js**: UI for user login.
    - **services/**: Contains service classes for API calls.
      - **AuthService.js**: Methods for making authentication requests to the backend.
    - **README.md**: Documentation for the frontend part of the project.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Node.js and npm

### Backend Setup

1. Navigate to the `backend` directory.
2. Run the following command to start the Spring Boot application:
   ```
   ./mvnw spring-boot:run
   ```

### Frontend Setup

1. Navigate to the `frontend` directory.
2. Install the dependencies:
   ```
   npm install
   ```
3. Start the React application:
   ```
   npm start
   ```

### Custom Login Page

The application uses a custom login page built with React. The `Login.js` component handles user authentication by making requests to the backend API.

## License

This project is licensed under the MIT License.