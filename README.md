This application consists of a backend and a frontend. To use it, either clone the repository or download it as a zip file.

Requirements for this application:

- Java (16 or higher)
- IntelliJ IDEA (or any other IDE)
- Visual Studio (or any other text editor)
- Node.js
# Setup backend
To set up the backend in this application, open the folder called "backend" in IntelliJ and let Gradle build it. After Gradle has built successfully, the application should start when you run the main method or press the green triangle on the top right. By default, the backend should run on port 8080. If this is not the case, please remember to change the port in the frontend.

# Setup frontend
To set up the frontend in this application, open the folder called "frontend" in Visual Studio. Make sure Node.js is installed. Open a terminal in your frontend folder and run "npm install". After all dependencies have been installed, run "npm run dev" in your terminal to start your application.

# Features
- Email scrape system that saves emails in the database
- Async email loading methods
- API request caching
- Bearer token implementation
- Rate limiting
- IP whitelisting
- Permission system
- Password hashing
  
# Walkthrough
The application is very simple and straightforward. On the home page, you will have the option to log in or navigate to the view email page. The view email page can only be viewed by logged-in users, so either way, you have to log in. You will not be able to create new users yet because only admin accounts can create new users. An admin account has been provided with the following credentials:

Email: admin@gmail.com
Password: admin

If the admin account gets deleted (system should prevent this) or lost, simply clear out the whole database and restart the backend. A new admin user will be generated using JPA.

After you log in, you can view all scraped emails. Additionally, you can view a list of all users by going to the user page. When on the user page, you can add a new user, delete a user, and promote a user to admin. You cannot delete the original admin account.

# Email scrape system that saves emails in the database
Emails will be scraped from the provided API on startup. To improve efficiency and speed, I have used a different API endpoint that will show all comments at once. After the emails have been scraped, they will be loaded into the database.

# Async email loading methods
Retrieving emails from the database can be done asynchronously. This is the only part of the application that is asynchronous since realistically this is the only part that will be used a lot.

# API request caching
The API request to get all the comments is cached.

# Bearer token implementation
I have used bearer tokens instead of API keys. This is more secure because if it gets stolen, the key will be useless after 30 minutes. It is also harder to lose or forget, and it made the permissions system a lot easier.

# Rate limiting
The API endpoints are rate-limited to 100 requests per minute.

# IP whitelisting
There is an IP whitelist where you can add IPs. Only these IPs will be able to use the API. Be aware that since the application is running on your machine, there is a big chance your requests will be let through despite you not being on one of the listed IPs.

# Permission system
There is a system with two user roles: a default user and an admin role. The default user is limited in what they can do, while the admin can do everything.

# Password hashing
Passwords are hashed in the database.
