# Coding Test

#### React Version:
Added a parent `pom.xml` file that will build the `frontend` and `configurationapp` projects.  The `frontend` will automatically download Node.js
and built the react app with the `maven-frontend-plugin` to build a **webjar**.  This webjar is included in the Spring Boot application in place of
the jQuery based web content.

Current Demonstrates:
* Minimum & Optional Requirements
* Generate ReactJS w/ Typescript knowledge
* Server Side Validation driving Client Side Rendering (JSR-380)
* Component based UI with state management
* Feedback with Toast component

Could demonstrate:
* JPA with in-memory database
* Unit Tests
* Integration / Selenium Tests
* More error handling scenarios
* CI/CD with GitHub Actions
* Docker / Deployments

**Let me know if you would like to see any of the above.**

### Building
From the root repo directory:
1. Run `mvn clean install`
2. Run `java -jar configurationapp/target/configurationapp.jar`
3. Open your favorite browser and navigate to `http://localhost:9000`