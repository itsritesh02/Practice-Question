
# Section 4 — AI-Assisted Engineering

# Q8. Understanding a Large Project Using AI

## Question

You join a new project that has:

- 300 APIs
- 150 MongoDB collections
- No documentation
- No one available to explain the project

You have access to:

- ChatGPT
- Cursor
- GitHub Copilot

Describe how you would spend your first two hours understanding the project.

Explain:

- Your approach
- How you would use AI
- How you would verify AI-generated information

---

# Answer

I would not try to understand all 300 APIs and 150 collections
at once.

I would first understand the overall project structure and
identify the main flow of the application.

---

# Step 1 — Understand Project Structure

First, I will inspect the repository.

I will look for folders such as:

    frontend/
    backend/
    routes/
    controllers/
    models/
    services/
    middleware/
    config/

I will also check:

    package.json
    .env.example
    README.md
    server.js / app.js

## Explanation

The goal is to understand:

    Frontend
       ↓
    API
       ↓
    Controller
       ↓
    Service
       ↓
    Database

---

# Step 2 — Understand the Entry Point

I will find where the application starts.

For example:

    server.js

or:

    app.js

I will check:

- Server setup
- Database connection
- Middleware
- Routes
- Authentication
- Environment variables

## Example

    app.use("/api/users", userRoutes);

This tells me that user-related APIs are probably inside:

    userRoutes

---

# Step 3 — Understand Routes

I will inspect the main route files.

Example:

    router.get("/users", getUsers);
    router.post("/users", createUser);

Then I will follow the flow:

    Route
      ↓
    Controller
      ↓
    Service
      ↓
    Model
      ↓
    MongoDB

This helps me understand how a request moves through the system.

---

# Step 4 — Understand Important Models

There are 150 MongoDB collections, so I will not study all
150 collections immediately.

I will first identify important collections.

For example:

    User
    Order
    Product
    Payment

Then I will understand relationships between them.

Example:

    User
      ↓
    Orders
      ↓
    Products

---

# Step 5 — Use AI

I can use ChatGPT, Cursor and GitHub Copilot to understand
unfamiliar code faster.

For example, I can give a function to AI and ask:

    "Explain this function step by step.
     What is its input, output and purpose?
     Also explain which database collection it uses."

AI can help me quickly understand:

- Large functions
- Complex logic
- API flow
- MongoDB queries
- Middleware
- Error handling
- Dependencies

---

# Step 6 — Use Cursor

Cursor can help me understand code across the repository.

For example, I can ask:

    "Trace the flow of the login API from route to database."

It may help identify:

    Login Route
        ↓
    Login Controller
        ↓
    Authentication Service
        ↓
    User Model
        ↓
    MongoDB

This is useful because the project has many files.

---

# Step 7 — Use GitHub Copilot

Copilot can help explain or navigate unfamiliar code.

For example, I can select a complex function and ask for
an explanation or use it to understand related code.

I can also use it to identify:

- Function purpose
- Expected parameters
- Possible edge cases
- Related functions

---

# Step 8 — Verify AI Information

This is VERY IMPORTANT.

I will never blindly trust AI-generated information.

I will verify AI's explanation by checking the actual code.

For example:

AI says:

    "This API updates the User collection."

I will verify:

    Route
       ↓
    Controller
       ↓
    Service
       ↓
    User Model
       ↓
    Database Query

If the actual code confirms it, I can trust the explanation.

---

# Step 9 — Run the Application

If possible, I will run the project locally.

I will check:

- Does the server start?
- Does MongoDB connect?
- Which APIs work?
- What happens when an API is called?
- What errors appear?

I will use logs and API tools such as Postman if available.

---

# Step 10 — First Two Hours Plan

## First 30 Minutes

    Project structure
         ↓
    package.json
         ↓
    Entry point
         ↓
    Database connection
         ↓
    Main routes

## Next 30 Minutes

    Important APIs
         ↓
    Controllers
         ↓
    Services
         ↓
    Main MongoDB models

## Next 30 Minutes

    Use ChatGPT
    Use Cursor
    Use Copilot

    Understand:
    API flow
    Authentication
    Database relationships
    Complex functions

## Last 30 Minutes

    Run application
         ↓
    Test important APIs
         ↓
    Check logs
         ↓
    Verify AI explanations
         ↓
    Make notes

---

# Q8 — Final Machine Round Answer

In the first two hours, I would first understand the project
structure, entry point, database connection and main API routes.

Then I would trace important APIs from route to controller,
service and database model.

I would use ChatGPT, Cursor and GitHub Copilot to explain
unfamiliar code and trace API flows quickly.

However, I would not blindly trust AI. I would verify every
important AI-generated explanation against the actual code,
database queries, logs and API behavior.

Finally, I would run the project locally and test important
APIs to confirm my understanding.

---

# Q9. MongoDB Aggregation Performance

## Question Visible in the Image

An AI generates a MongoDB aggregation query that works correctly
in development but causes severe performance issues in production.

The question continues on the next part/page.

---

# Q9 — Important Approach

For a production performance issue, I would first understand
what the AI-generated aggregation is doing.

I would not assume that because the query gives the correct
result, it is also efficient.

I would check:

    explain()
        ↓
    executionStats
        ↓
    Documents Examined
        ↓
    Index Usage
        ↓
    Aggregation Stages
        ↓
    Production Data Size
        ↓
    Performance Test

The complete Q9 answer should be written after checking the
remaining part of the question.