# FDA
Food Delivery Application 
Requirements in Deliverable 1:
•	Create Account
•	Login to Account
•	Manage User Roles
•	Manage Profile
•	Forgot Password
•	Register your Restaurant.

Model Classes:
•	VerificationToken : This model stores verification information such as tokens and expiration dates generated during the user login process.
•	Restaurant: This model class manages and processes a restaurant using FDA

Controller Classes:
•	AuthenticationController: The Controller manages user login, validation, and authentication tasks.
•	RestaurantController : It's responsible for handling various operations associated with restaurants, such as listing available restaurants, providing details, and managing restaurant information.
•	UserController : The User Controller manages user-related operations and functionalities
•	VerificationTokenController : During the login process, this Controller retrieves the verification token and expiration date information.
•	ProfileController : It allows users to view and edit their own profiles and may also enable administrators to manage user profiles.

Services:
Services are implemented using the following interfaces
•	IRestaurantService : This interface implements services related to restaurant management, food ordering, reviews etc.
•	IUserService : This interface implements services that involves user management including registeration, authentication and managing user profiles.
•	IVerificationTokenService : This interface implements services that require user account verification, resetting password or anything involving sending and verifying tokens.
•	IEmailService : "IEmailService" typically suggests an interface for an email service in a software development context.

MVC x LAYERED ARCHITECTURE

<img width="2169" alt="Drawing1 - ASE" src="https://github.com/ArukalaHimateja/FDA/assets/53402333/a56e0142-c234-4e0a-80ec-c5c4fc9c9294"> 

In this architecture, the MVC pattern helps separate concerns within the presentation layer, with controllers handling user interactions and views handling the presentation of data. The N-tier architecture provides a clear separation of concerns across multiple layers, with the service layer containing business logic and the data access layer dealing with data storage.
Communication flows from the presentation layer, where user interactions occur, to the application layer, which routes requests to the service layer for processing. The service layer interacts with the data access layer to retrieve or update data from the database. 




