# Tv packages exercise app

## Information

Backend swagger docs are at: http://your_address:your_port/api/swagger-ui/index.html#/

### Some useful features

Package categories and types are separate entities, if this app had some kind of admin interface, user could just add new types and categories on demand,
but currently all of these are initialized in [DatabaseInitialization class](./backend/src/main/java/ee/erik/backend/util/DatabaseInitialization.java).

## Requirements

### Backend

Java 18

### Frontend

Node Lts

Npm

## Setup

### Backend

Gradle will do setup automatically. If intellij is used,
otherwise run `./gradlew.bat build` on windows or `./gradlew build` on linux.

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

Frontend only contains some basic unit and component tests for Redux slices.
To run tests in frontend, just type `npm run test`
