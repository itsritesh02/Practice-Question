# Q8 — AI-Assisted Engineering

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

### Question:

Describe how you would spend your first two hours understanding the project.

Explain:

1. Your approach
2. How you would use AI
3. How you would verify AI-generated information

---

# Answer

Agar main kisi new project mein join karta hoon jisme 300 APIs aur 150 MongoDB collections hain aur documentation available nahi hai, to main directly coding start nahi karunga.

Main pehle project ka overall architecture aur data flow samajhne ki koshish karunga.

Mera goal first two hours mein **poore project ko samajhna nahi**, balki project ka **high-level structure, important modules, API flow aur database relationships** samajhna hoga.

---

# First 2 Hours — My Approach

## 0–15 Minutes — Project Setup & Repository Overview

Sabse pehle main repository ko locally run karunga.

Main check karunga:

```text
package.json
README.md
.env.example
src/
server/
controllers/
routes/
models/
services/
middlewares/
config/
utils/
```

Main ye bhi check karunga:

```text
Node.js version
npm/yarn/pnpm
Entry point
Environment variables
Database configuration
Build commands
Start commands
Test commands
```

### Commands

```bash
git clone <repository>
cd project
npm install
npm run dev
```

Agar project successfully run ho jata hai, to main basic application flow verify karunga.

### Interview Explanation

> "First 15 minutes mein main project ko locally run karke repository structure, package.json, environment configuration aur application entry point understand karunga."

---

# 15–30 Minutes — Architecture Understand Karna

Ab main project ke important folders aur files ko identify karunga.

Typical MERN project:

```text
Client
   ↓
React
   ↓
API Request
   ↓
Express / Node.js
   ↓
Routes
   ↓
Controllers
   ↓
Services
   ↓
MongoDB
```

Main ye identify karunga:

```text
Frontend
Backend
API Routes
Controllers
Services
Models
Middleware
Authentication
Database
External Services
```

Main especially authentication aur authorization flow samjhunga:

```text
Login
  ↓
JWT
  ↓
Middleware
  ↓
Protected Route
  ↓
Controller
  ↓
Database
```

### Interview Explanation

> "Main architecture ka high-level flow identify karunga — frontend se request kaise backend tak jaati hai, route se controller aur service layer ke through database tak kaise reach karti hai."

---

# 30–50 Minutes — AI Se Project Structure Samajhna

Ab main **ChatGPT / Cursor / GitHub Copilot** ka use karunga.

Lekin main AI ko poora 300 APIs ka code ek saath explain karne ko nahi bolunga.

Main project ko small chunks mein analyze karunga.

### ChatGPT

Main important files ka code AI ko provide karke questions puchunga:

```text
Explain this project's architecture.

What is the request flow?

Which file is the entry point?

How are routes connected to controllers?

How is MongoDB connected?

Where is authentication handled?

What are the main business modules?
```

---

# Cursor Ka Use

Cursor ka advantage ye hai ki wo project ke codebase ke context mein questions answer kar sakta hai.

Main questions puchunga:

```text
Where is user authentication implemented?

Show me the flow from login API to database.

Which controller handles /api/users?

Which MongoDB model is used by this API?

Where is authorization middleware implemented?

Which files depend on User model?
```

Isse mujhe codebase ke andar relationships quickly samajhne mein help milegi.

---

# GitHub Copilot Ka Use

GitHub Copilot ko main mainly code understanding aur small tasks ke liye use karunga.

Example:

```text
Explain this function.
```

Ya:

```text
Explain what this middleware does.
```

Ya:

```text
Generate a simple test for this function.
```

Copilot ka use main blindly code generate karne ke liye nahi karunga.

---

# 50–70 Minutes — APIs Understand Karna

Project mein 300 APIs hain.

Main first two hours mein 300 APIs individually read nahi karunga.

Main APIs ko modules/groups mein divide karunga.

Example:

```text
Authentication
Users
Products
Orders
Payments
Notifications
Admin
Reports
```

Example:

```text
/auth
/users
/products
/orders
/payments
/admin
```

Main pehle important APIs identify karunga:

```text
Login
Register
User Profile
Create Order
Get Order
Update Order
Payment
Admin APIs
```

---

# API Flow Trace Karna

Ek important API ko end-to-end trace karunga.

Example:

```text
POST /api/orders
        ↓
Order Route
        ↓
Auth Middleware
        ↓
Order Controller
        ↓
Order Service
        ↓
Order Model
        ↓
MongoDB
        ↓
Response
```

Isse mujhe actual application flow samajhne mein help milegi.

---

# 70–90 Minutes — MongoDB Understand Karna

Project mein 150 collections hain.

Main sabhi collections individually analyze nahi karunga.

Main pehle important collections identify karunga.

Example:

```text
users
orders
products
payments
transactions
notifications
```

Main models/schema check karunga.

Example:

```javascript
User
Order
Product
Payment
```

Main relationships samjhunga:

```text
User
 ↓
Orders
 ↓
Products
 ↓
Payments
```

MongoDB mein relational database jaisa foreign key system mandatory nahi hota, isliye main specially check karunga:

```text
ObjectId references
Embedded documents
Referenced documents
Indexes
Aggregation pipelines
```

---

# 90–105 Minutes — Important Business Flow

Ab main ek ya do important business flows ko end-to-end understand karunga.

Example:

## User Order Flow

```text
User Login
    ↓
Authentication
    ↓
Product Selection
    ↓
Create Order
    ↓
Order Collection
    ↓
Payment
    ↓
Payment Collection
    ↓
Order Status Update
    ↓
Notification
```

Main identify karunga:

```text
Which APIs are involved?
Which controllers?
Which services?
Which MongoDB collections?
Which external APIs?
```

Isse mujhe project ka actual business logic samajhne mein help milegi.

---

# 105–120 Minutes — Verify & Create Notes

Last 15 minutes mein main jo samjha hai usko verify karunga.

Main ek small architecture note banaunga:

```text
Project
│
├── Frontend
│
├── Backend
│   ├── Routes
│   ├── Controllers
│   ├── Services
│   ├── Middleware
│   └── Models
│
├── MongoDB
│   ├── Users
│   ├── Orders
│   ├── Products
│   └── Payments
│
└── External Services
```

Saath mein important APIs aur collections ki list maintain karunga.

---

# How I Would Use AI

Main AI ko mainly 4 purposes ke liye use karunga:

## 1. Code Understanding

```text
Explain this controller.
```

```text
Explain this middleware.
```

```text
Explain this MongoDB aggregation.
```

---

## 2. Code Navigation

Cursor se:

```text
Where is this function used?
```

```text
Which APIs use this model?
```

```text
Where is this API route defined?
```

---

## 3. Documentation Generation

AI se project ke existing code ko understand karke draft documentation banwa sakta hoon.

Example:

```text
Create a summary of this API:
- Endpoint
- Method
- Authentication
- Request body
- Response
- Database collection
```

---

## 4. Testing

AI ki help se main test cases generate kar sakta hoon.

Example:

```text
Generate test cases for this API including:
- Success case
- Validation error
- Unauthorized request
- Not found
- Server error
```

---

# How I Would Verify AI-Generated Information

Ye sabse important part hai.

Main AI ke answer ko blindly trust nahi karunga.

AI sirf ek assistant hai.

Main information ko actual source code ke saath verify karunga.

---

## Verification Method

### Step 1 — AI Explanation

AI mujhe batata hai:

```text
Login API uses JWT authentication.
```

### Step 2 — Actual Code Check

Main actual code mein check karunga:

```text
login route
    ↓
login controller
    ↓
JWT generation
    ↓
authentication middleware
```

### Step 3 — Database Check

Main check karunga ki actual MongoDB model/collection kaunsa use ho raha hai.

### Step 4 — Run the Application

API ko Postman/Insomnia se test karunga.

Example:

```text
POST /api/login
```

### Step 5 — Logs / Response Check

Main actual response aur logs ko AI ke explanation se compare karunga.

---

# Important Rule

```text
AI Suggestion
     ↓
Read Actual Code
     ↓
Run / Test
     ↓
Verify Result
     ↓
Accept Information
```

AI ki generated information ko **source of truth** nahi maanunga.

Actual code, tests, runtime behavior aur database behavior ko source of truth maanunga.

---

# What I Would NOT Do

Main first two hours mein:

```text
❌ 300 APIs manually read nahi karunga
❌ 150 collections individually study nahi karunga
❌ AI ke output ko blindly trust nahi karunga
❌ Random code changes nahi karunga
❌ Production database mein unnecessary queries nahi chalaunga
❌ Large refactoring start nahi karunga
```

Instead:

```text
Architecture
    ↓
Important APIs
    ↓
Important Collections
    ↓
Business Flow
    ↓
Verification
```

---

# Interview Ready Answer

Agar interviewer bole:

**"You have only two hours. How will you understand this project?"**

To main bolunga:

> "First, I would run the project locally and understand the repository structure, package.json, entry point, environment configuration and database connection."

> "Then I would identify the high-level architecture — routes, controllers, services, middleware, models and external services."

> "Since there are 300 APIs, I would not try to understand every API individually. I would group them by business modules such as authentication, users, orders, payments and admin."

> "I would select one or two critical APIs and trace them end-to-end from route to controller, service, MongoDB model and response."

> "For the 150 MongoDB collections, I would identify the important collections and understand their schemas, references, indexes and aggregation pipelines."

> "I would use Cursor to navigate the codebase, ChatGPT to explain complex code and architecture, and GitHub Copilot for smaller code understanding and test-generation tasks."

> "However, I would never blindly trust AI-generated information. I would verify every important assumption against the actual source code, run the application, test APIs and check database behavior."

> "By the end of two hours, my goal would be to have a high-level architecture map, understand the main business flow, identify important APIs and collections, and create a list of questions or unknown areas for further investigation."

---

# ⭐ Short Interview Version

Agar interviewer short answer maange:

```text
1. Run the project locally.
2. Understand repository structure.
3. Identify architecture and entry points.
4. Group 300 APIs into business modules.
5. Identify important MongoDB collections.
6. Trace 1–2 critical APIs end-to-end.
7. Use Cursor for codebase navigation.
8. Use ChatGPT for explanations.
9. Use GitHub Copilot for small coding/testing tasks.
10. Verify AI answers using actual code, tests, logs and database behavior.
11. Document what I learned.
12. List remaining unknowns for further investigation.
```

---

# 🧠 Golden Line For Interview

> **"I would use AI to accelerate understanding, not to replace engineering judgment. AI gives me hypotheses, but the source code, tests and actual runtime behavior are the source of truth."**

---

# Final Summary

## First 2 Hours

```text
0–15 min
Project Setup + Repository

15–30 min
Architecture

30–50 min
AI-assisted Code Understanding

50–70 min
API Modules + Critical APIs

70–90 min
MongoDB Models + Collections

90–105 min
Business Flow

105–120 min
Verification + Documentation
```

## Tools

```text
Cursor
→ Codebase Navigation

ChatGPT
→ Architecture & Code Explanation

GitHub Copilot
→ Small Coding & Testing Assistance
```

## Golden Rule

```text
USE AI
   ↓
UNDERSTAND
   ↓
VERIFY
   ↓
TEST
   ↓
TRUST
```