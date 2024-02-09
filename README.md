## E-commerce loomi

## Software Requirements to Deploy the Project

- Java 17
- Docker and Docker Compose

Make sure to have these software installed and properly configured before proceeding with the project.

# GitHub
- https://github.com/torresvictor100/e-commerce-loomi


## Steps to Deploy the Application

1. **Clone the GitHub Repository**:
    ```
    git clone git@github.com:torresvictor100/e-commerce-loomi.git
    ```

2. **Copy Environment Variables**:
    - Copy the `.env.example` file and rename it to `.env`.
    - Fill in the values for the environment variables as necessary.

3. **Build the Project**:
    ```
    ./gradlew clean build
    ```

4. **Deploy the Application with Docker Compose**:
    ```
    docker-compose up --build
    ```

Make sure to follow these steps sequentially to deploy the application successfully.


## Updating Changes and Refreshing the Container

If you want to deploy changes while clearing the database, follow these steps:

1. **Build the Project**:
    ```
    ./gradlew clean build
    ```

2. **Stop the Container and Clear the Database**:
    ```
    docker-compose down -v
    ```

3. **Build and Deploy the Application**:
    ```
    docker-compose up --build
    ```

By following these steps, you can update your changes and refresh the container with a clean database.

## Deploying Without Clearing the Database

To deploy without clearing the database, follow these steps:

1. **Build the Project**:
    ```
    ./gradlew clean build
    ```

2. **Rebuild the Docker Containers**:
    ```
    docker-compose up --build
    ```

Following these steps will update your application without clearing the database.

## Stop Project

1. **To stop the project, use the following command:**:
    ```
    docker-compose stop
    ```

## Bring Down and Clean Up Project

1. **To bring down and clean up the entire project, including removing volumes, use the following command**:
    ```
    docker-compose down -v
    ```
