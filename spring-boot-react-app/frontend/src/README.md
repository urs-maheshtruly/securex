# Frontend Documentation

This directory contains the frontend part of the Spring Boot and React application. The frontend is built using React and provides a user interface for interacting with the backend services.

## Project Structure

- **public/index.html**: The main HTML file that serves as the entry point for the React application.
- **src/App.js**: The main component that sets up routing and renders the application layout.
- **src/index.js**: The entry point for the React application that renders the `App` component into the DOM.
- **src/components/Login.js**: The component that provides the UI for user login, including form fields for username and password.
- **src/services/AuthService.js**: The service that handles authentication requests to the backend API.

## Getting Started

1. **Install Dependencies**: Navigate to the `frontend` directory and run `npm install` to install the required dependencies.
2. **Run the Application**: Use `npm start` to start the React application. It will be available at `http://localhost:3000`.

## Custom Login Page

The application features a custom login page implemented in the `Login.js` component. This page allows users to enter their credentials and authenticate against the backend.

## API Integration

The frontend communicates with the backend using RESTful API calls. The `AuthService.js` file contains methods for handling login and registration requests.

## Contributing

Feel free to contribute to this project by submitting issues or pull requests. Your feedback and contributions are welcome!