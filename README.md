# Food Delivery System

### Description
This system is designed to help customers order food online from multiple restaurants. Restaurants can register and add their menus, allowing customers to browse and order food. Developed using Spring Boot and MySQL, each restaurant can manage its menu, and customers can register, update their accounts, and place orders.

### Key Features
- Restaurant Management:

    - Restaurants can create, read, update, and remove their profiles.

    - Restaurants can create, update, read, and delete their menu items.

- Customer Management:

    - Customers can register, update, and delete their accounts.

    - Customers can read paginated menus for each restaurant.

    - Customers can add food items to their cart.

    - Customers can place orders.

   - Customers can write reviews for specific menu items.

### Technologies Used
<b>Spring Boot</b>: The backend framework used.

<b>MySQL</b>: The relational database system used for persisting data.


<b>JPA Entities</b>: Used for object-relational mapping.

<b>REST APIs</b>: Used for effective communication between frontend and backend.


### ERD Diagram

![ERD](https://github.com/user-attachments/assets/2b64cf61-21d1-4d6b-8ab5-293ec4430284)


### API Documentation
We have incorporated a collection of REST APIs to manipulate and manage the data. The Postman collection is attached [here](https://github.com/EmadHussien/Food-Ordering-Application/blob/main/Food%20Order%20Delivery%20System.postman_collection.json).
### Setup
Instructions to get this project up and running.
1. Clone the repository or download the zip.<placeholder>
2. Open the project in your favorite IDE.
3. Ensure the correct dependencies are installed.
4. Configure the MySQL database:
    1. Create a database.
    2. Update the application.properties file with your database credentials.
5. Run the application.
