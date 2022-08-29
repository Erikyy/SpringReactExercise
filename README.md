# Tv packages exercise app

## Information

Backend swagger docs are at: http://your_address:your_port/api/swagger-ui/index.html#/

## Requirements

### Backend

Java 18

### Frontend

Node 16

Npm

## Setup

### Backend

Gradle will do setup automatically.

### Frontend

Before running on docker or locally, frontend needs .env file.
Check .env.example. Only thing that it needs is backend api url.

Run
`npm i`
in frontend folder to install all required dependencies

## Running

### Running Docker

If docker is installed on the system, then, using
`docker-compose up` will run both applications in containers.

Docker will automatically run tests during backend image build.

### Running locally

Ide is your own choice and completly optional, possible to run using terminal or
using vscode plugins or quickly opening this project in Intellij Idea Ultimate version.

When using terminal in backend folder, just simply type in `./gradlew.bat :bootRun`
or `./gradlew :bootRun` on linux to start spring boot application.

Starting frontend from terminal is straightfoward, `npm run start`.

### Testing

Backend contains multiple unit tests and a single integration test.
To run backend tests in terminal, type in `./gradlew.bat test -i`
or `./gradlew test -i` on linux to run junit tests.

Frontend only contains some basic unit tests for Redux slices.
To run tests in frontend, just type `npm run test`
