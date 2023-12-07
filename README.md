
**Food Delivery System** 

~ A Team Project for Advanced Software Engineering Course - IT 426

**INTRODUCTION**

The Food Delivery App is developed to simplify the entire food delivery system from food management, showcase, pricing to view food listing, choosing items and order online. The customers can order and pay cash on delivery and payment via online payment gateway using stripe. Restaurants can manage the availability of their store, if they have the high demand, they can change the status to online or offline. This application allows the customer to search nearby restaurant to order food by using the google map location search API’s. 

The food delivery app software product is a cutting-edge web application designed to revolutionize the food industry by connecting customers with a diverse range of restaurants. This executive-level summary provides a high-level overview of what food delivery app will and will not do, its intended application, and the associated benefits, objectives, and goals.

Food delivery app will provide a user-friendly platform for customers to easily browse restaurant Products, place orders, and make secure payments. It aims to simplify the food ordering process

**MVC in Food Delivery System**

**Model:**
| Entity | Path | 
| --- | --- |
| Feedback | src\main\java\com\fda\app\model\Feedback.java|
| Product | src\main\java\com\fda\app\model\Product.java|
| Location | src\main\java\com\fda\app\model\GLocation.java|
| Order | src\main\java\com\fda\app\model\Order.java|
| Promocode | src\main\java\com\fda\app\model\PromoCode.java|
| Review & Rating | src\main\java\com\fda\app\model\RatingAndReview.java|
| Report | src\main\java\com\fda\app\model\Report.java|
| Restaurant | src\main\java\com\fda\app\model\Restaurant.java|
| Restaurant Add Request | src\main\java\com\fda\app\model\RestaurantRequest.java|
| TrackOrderHistory | src\main\java\com\fda\app\model\TrackOrderHistory.java|
| User | src\main\java\com\fda\app\model\User.java|
| VerificationToken | src\main\java\com\fda\app\model\VerificationToken.java| 
| Billing | src\main\java\com\fda\app\model\Billing.java| 
| Category | src\main\java\com\fda\app\model\Category.java|


**Controller:**
| Entity | Path |
| --- | --- |
| Feedback Controller | src\main\java\com\fda\app\controller\FeedbackController.java| 
| File Controller | src\main\java\com\fda\app\controller\FileController.java| 
| Google Location Controller | src\main\java\com\fda\app\controller\GoogleLocationController.java| 
| Order Controller | src\main\java\com\fda\app\controller\OrderController.java|
| Payment Controller | src\main\java\com\fda\app\controller\PaymentController.java|
| Product Controller | src\main\java\com\fda\app\controller\ProductController.java|
| Profile Controller | src\main\java\com\fda\app\controller\ProfileController.java|
| Promo Code Controller | src\main\java\com\fda\app\controller\PromoCodeController.java|
| Rating And Review Controller | src\main\java\com\fda\app\controller\RatingAndReviewController.java|
| Report Controller | src\main\java\com\fda\app\controller\ReportController.java|
| Restaurant Controller | src\main\java\com\fda\app\controller\RestaurantController.java|
| User Controller | src\main\java\com\fda\app\controller\UserController.java|
| Verification Token Controller | src\main\java\com\fda\app\controller\VerificationTokenController.java|
| Authentication Controller | src\main\java\com\fda\app\controller\AuthenticationController.java|
| Billing Controller | src\main\java\com\fda\app\controller\BillingController.java|
| Category Controller | src\main\java\com\fda\app\controller\CategoryController.java|
| Contact Controller | src\main\java\com\fda\app\controller\ContactController.java|
| Dashboard Controller | src\main\java\com\fda\app\controller\DashboardController.java|

**Service:**
| Entity | Path |
| --- | --- |
| Rating And Review Service | src\main\java\com\fda\app\service\impl\RatingAndReviewServiceImpl.java|
| Report Service | src\main\java\com\fda\app\service\impl\ReportServiceImpl.java|
| Restaurant Service | src\main\java\com\fda\app\service\impl\RestaurantServiceImpl.java|
| User Service | src\main\java\com\fda\app\service\impl\UserServiceImpl.java|
| Verification Token Service | src\main\java\com\fda\app\service\impl\VerificationTokenServiceImpl.java|
| Billing Service | src\main\java\com\fda\app\service\impl\BillingServiceImpl.java|
| Category Service | src\main\java\com\fda\app\service\impl\CategoryServiceImpl.java|
| Dashboard Service | src\main\java\com\fda\app\service\impl\DashboardServiceImpl.java|
| Email Service | src\main\java\com\fda\app\service\impl\EmailServiceImpl.java|
| Feedback Service | src\main\java\com\fda\app\service\impl\FeedbackServiceImpl.java|
| File Service | src\main\java\com\fda\app\service\impl\FileServiceImpl.java|
| Google Location Service | src\main\java\com\fda\app\service\impl\GoogleLocationServiceImpl.java|
| Location Service | src\main\java\com\fda\app\service\impl\LocationServiceImpl.java|
| Order Service | src\main\java\com\fda\app\service\impl\OrderServiceImpl.java|
| Payment Service | src\main\java\com\fda\app\service\impl\PaymentServiceImpl.java|
| Product Service | src\main\java\com\fda\app\service\impl\ProductServiceImpl.java|
| PromoCode Service | src\main\java\com\fda\app\service\impl\PromoCodeServiceImpl.java|


**MVC x LAYERED ARCHITECTURE**

<img width="2155" alt="Drawing1 - ASE" src="https://github.com/ArukalaHimateja/FDA/assets/53402333/672bacfe-9680-4c63-9c8e-232338deff0b">

In this architecture, the MVC pattern helps separate concerns within the presentation layer, with controllers handling user interactions and views handling the presentation of data. The N-tier architecture provides a clear separation of concerns across multiple layers, with the service layer containing business logic and the data access layer dealing with data storage.
Communication flows from the presentation layer, where user interactions occur, to the application layer, which routes requests to the service layer for processing. The service layer interacts with the data access layer to retrieve or update data from the database. 

**INSTALLATION**

| Hardware/Software | Source| 
| --- | --- |
| Java (JDK 11) | https://www.oracle.com/java/technologies/downloads/#java11-windows |
| Eclipse  | https://spring.io/tools   |
| MySQL | https://www.mysql.com/   |

**APPLIED TECHNOLOGIES**

| **IDE**                | Eclipse / Spring Tool Suite, VSCode          |
|------------------------|--------------------------------------------|
| **Programming Languages** | Java (JDK 11)                              |
| **Frameworks and Libraries** | Spring Boot, Swagger 2.0               |
| **Application Server** | Apache Tomcat                              |
| **Front End**          | HTML, CSS, JavaScript, TypeScript, Angular |
| **Backend**            | Java, Spring Boot, Spring Web Security, Hibernate, Spring JPA, Tomcat, MySQL, Swagger (API Documentation), Node.js, Spring Boot Framework |
| **Database Server/DMBS** | MySQL                                    |


**TESTS**

Test cases:
| Entity | Path |
| --- | --- | 
| ProductControllerTest | src\test\java\com\fda\controller\ProductControllerTest.java|
| ProfileControllerTest | src\test\java\com\fda\controller\ProfileControllerTest.java|
| PromoCodeControllerTest | src\test\java\com\fda\controller\PromoCodeControllerTest.java|
| RatingAndReviewControllerTest | src\test\java\com\fda\controller\RatingAndReviewControllerTest.java|
| ReportControllerTest | src\test\java\com\fda\controller\ReportControllerTest.java|
| RestaurantControllerTest | src\test\java\com\fda\controller\RestaurantControllerTest.java|
| UserControllerTest | src\test\java\com\fda\controller\UserControllerTest.java|
| VerificationTokenControllerTest | src\test\java\com\fda\controller\VerificationTokenControllerTest.java|
| AuthenticationControllerTest | src\test\java\com\fda\controller\AuthenticationControllerTest.java|
| BillingControllerTest | src\test\java\com\fda\controller\BillingControllerTest.java|
| CategoryControllerTest | src\test\java\com\fda\controller\CategoryControllerTest.java|
| ContactControllerTest | src\test\java\com\fda\controller\ContactControllerTest.java|
| DashboardControllerTest | src\test\java\com\fda\controller\DashboardControllerTest.java|
| FeedbackControllerTest | src\test\java\com\fda\controller\FeedbackControllerTest.java|
| GoogleLocationControllerTest | src\test\java\com\fda\controller\GoogleLocationControllerTest.java|
| OrderControllerTest | src\test\java\com\fda\controller\OrderControllerTest.java|
| PaymentControllerTest | src\test\java\com\fda\controller\PaymentControllerTest.java|


**Test Link** - https://github.com/ArukalaHimateja/FDA/tree/main/Food-delivery-app-deliverable-1-code/src/test/java/com/fda

**AUTHORS**

Yashaswini Mekala (ymekala)

Himateja Arukala (harukal)

Praneel Reddy Mallu (pmallu)

Rajapraful Reddy Kasarla (rkasarl)

Gnanesh Reddy Kadiyam (gkadiy)

