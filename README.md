**Java-Junit-Mockito**

This project demonstrates the unit testing of service layer
with dependencies using Mockito framework.

The domain object is <a href="src/main/java/org/example/javajunitmockito/model/Book.java">Book</a> and CRUD operation is performed on that model.

The Service tier has a dependency of Dao tier, 
and Mockito is used to mock the behaviour of Dao during unit testing of Service Tier.

To Run:
1. Clone the project
2. Run `mvn clean compile test` from terminal
3. Or, import in an IDE, then run tests