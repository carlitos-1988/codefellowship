# codefellowship

## Feature Tasks as of 31Jul2023
Build an app that allows users to log into CodeFellowship.

The site should have a login page.
The login page should have a link to a signup page.
An ApplicationUser should have a username, password (will be hashed using BCrypt), firstName, lastName, dateOfBirth, bio, and any other fields you think are useful.
All of these fields must be set at signup. They will not be editable at any other time.
The site should allow users to create an ApplicationUser on the “sign up” page.
Your Controller should have an @Autowired private PasswordEncoder passwordEncoder; and use that to run passwordEncoder.encode(password) before saving the password into the new user.
Using the cheat sheet above in “Resources”, add the ability for users to log in to your app.
The site should have a homepage at the root route (/) that contains basic information about the site.
This page should have links to login and signup if the user is NOT logged in.
This page should have a link to logout if the user IS logged in.
Ensure that users can log out and are redirected to the home page or login page.
When a user is logged in, the app should display the user’s username on every page (probably in the heading).
Ensure that your homepage, login, and registration routes are accessible to non-logged in users.
The site should be well-styled and attractive.
The site should use templates to display its information.
Ensure that user registration also logs users into your app automatically.

## Feature Tasks 1Aug2023

- Allow users to log in to CodeFellowship, view user profiles, and create posts.

- Upon logging in, users should be taken to a /myprofile route that displays their information.
- This should include a default profile picture, which is the same for every user, and their basic information from ApplicationUser.
- The site should have a page which allows viewing the data about a single ApplicationUser, at a route like /users/{id}.
- This should include a default profile picture, which is the same for every user, and their basic information.
- Continue to ensure that your homepage, login, and registration routes are accessible to non-logged in users. All other routes should be limited to logged-in users.
- Add a Post entity to your app.
- A Post has a body and a createdAt timestamp.
- A logged-in user should be able to create a Post, and a post should belong to the user that created it.
Hint: This is a relationship between two pieces of data
- A user’s posts should be visible on their profile page.
- The site should use reusable templates for its information. (At a minimum, it should have one Thymeleaf fragment that is used on multiple pages.)
- The site should have a non-whitelabel error handling page that lets the user know, at minimum, the error code and a brief message about what went wrong.
