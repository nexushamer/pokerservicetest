POKER SERVICE

REQUIREMENTS:

 - Java8
 - Maven
 - Eclipse for checking the code
 
HOW TO RUN THE SERVICE

In a CMD console or a Gitbash console you must run one of the following command 

For run the service
- mvn spring-boot:run
This command will start the spring project in the port 8082, the context of the aplication is 
configured in the application.properties file, if you want to change the port of the context
chage the variables related to the SERVER.

For run the test
- mvn test
This command will run the unit test of the application

HOW TO TEST THE SERVICE

You must import the postman collection in postman application, and then you can start testing the service.

NOTE:
Remember, for start the process of the game you must call the endpoint game and execute the operation 
CreateDEck, CreatePlayer and CreateGame after that you must add the player and the deck to the game,
and after that you can start dealing cards.

THANKS