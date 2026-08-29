# Q10. AI Generated an Incorrect Solution

## Question

Tell us about a real situation where AI generated an incorrect solution.

Explain:

1. What was the problem?
2. Why was the AI solution incorrect?
3. How did you identify the issue?
4. How did you fix it?
5. What did you learn from the experience?

> If you don't have professional experience, you can describe a personal project.

---

# Answer

Agar professional project ka real example nahi hai, to **personal project ka genuine example** explain kar sakte ho.

Interview mein answer ka structure simple rakho:

```text
Problem
   ↓
AI Suggestion
   ↓
Testing
   ↓
Issue Found
   ↓
Root Cause
   ↓
Fix
   ↓
Retesting
   ↓
Learning
```

---

# 1. What Was the Problem?

Example:

Mere personal MERN project mein mujhe MongoDB se filtered data fetch karna tha.

Requirement thi:

```text
Sirf active users
jinki specific role ho
unko return karna hai.
```

Maine AI se MongoDB query generate karne mein help li.

AI ne ek aggregation/query suggest ki.

---

# 2. Why Was the AI Solution Incorrect?

AI-generated query syntax ke according correct lag rahi thi, lekin actual project ke MongoDB schema ke according result incorrect aa raha tha.

Example:

Actual document:

```javascript
{
    name: "Arbaj",
    role: "developer",
    isActive: true
}
```

AI ne assume kar liya ki field ka naam:

```javascript
status: "active"
```

hai.

Is wajah se query expected data return nahi kar rahi thi.

### Important Point

Problem AI ke syntax mein nahi thi.

Problem thi:

```text
AI Assumption
      ≠
Actual Project Schema
```

---

# 3. How Did You Identify the Issue?

Main AI-generated code ko directly production mein use nahi karunga.

Sabse pehle query ko test karunga.

### Step 1

Actual MongoDB document check kiya:

```javascript
db.users.findOne();
```

### Step 2

Actual schema/field names verify kiye:

```text
isActive
role
name
```

### Step 3

AI-generated query ke fields compare kiye.

AI:

```text
status
```

Actual:

```text
isActive
```

Yahin se issue identify hua.

---

# 4. How Did You Fix It?

AI-generated query ko actual schema ke according modify kiya.

Incorrect:

```javascript
db.users.find({
    status: "active"
});
```

Correct:

```javascript
db.users.find({
    isActive: true
});
```

Role filter bhi add kiya:

```javascript
db.users.find({
    isActive: true,
    role: "developer"
});
```

---

# 5. Testing

Fix ke baad main sirf ek document ke saath test nahi karunga.

Main different cases test karunga:

```text
Active Developer
Inactive Developer
Active Designer
Inactive Designer
Missing Fields
Empty Collection
Multiple Users
```

Example:

```text
Input:
5 users

Expected:
2 active developers

Actual:
2 active developers
```

Agar expected aur actual result same hai to solution verify ho gaya.

---

# 6. Edge Cases

Main edge cases bhi check karunga.

For example:

### No Matching User

```text
Result:
[]
```

### Missing isActive

```text
isActive field missing
```

### Missing Role

```text
role field missing
```

### Large Dataset

```text
1000 users
10,000 users
1,000,000 users
```

Large dataset ke case mein performance bhi check karunga.

---

# 7. Performance Check

Agar query MongoDB ki hai to:

```javascript
db.users.explain("executionStats").find({
    isActive: true,
    role: "developer"
});
```

Check karunga:

```text
executionTimeMillis
totalDocsExamined
totalKeysExamined
nReturned
```

Agar required ho to appropriate index consider karunga:

```javascript
db.users.createIndex({
    isActive: 1,
    role: 1
});
```

Index create karne se pehle existing indexes aur actual query pattern check karunga.

---

# 8. Final Verification

Final solution ko:

```text
Actual Schema
     ↓
Actual Data
     ↓
Expected Result
     ↓
Edge Cases
     ↓
Performance
     ↓
Final Code
```

ke against verify karunga.

---

# 9. What Did I Learn?

Is experience se main ye samjha:

```text
AI is helpful
      ↓
But AI can make assumptions
      ↓
Developer must verify
```

AI project ka complete context nahi jaanta jab tak hum usse proper context na dein.

Isliye:

```text
AI Suggestion
     ↓
Understand
     ↓
Verify
     ↓
Test
     ↓
Use
```

---

# Interview Ready Answer — English

> "In one of my personal MERN projects, I used AI to help generate a MongoDB query for filtering users. The query looked syntactically correct, but it returned incorrect results because the AI had assumed a different field name from the actual MongoDB schema."

> "I identified the issue by checking the actual database documents and comparing the schema with the AI-generated query."

> "The AI was using a `status` field, while my actual schema used an `isActive` boolean field."

> "I corrected the query according to the actual schema and then tested it with multiple cases, including active users, inactive users, missing fields and cases with no matching records."

> "After verifying the result, I also checked the query performance and confirmed that the final solution behaved correctly."

> "The main lesson I learned was that AI-generated code should be treated as a suggestion. I always verify it against the actual requirements, schema, tests and runtime behavior."

---

# Interview Ready Answer — Hindi + English

> "Mere ek personal MERN project mein mujhe MongoDB se users ko filter karna tha. Maine AI ki help se query generate ki. Query syntactically correct lag rahi thi, lekin expected result nahi aa raha tha."

> "Maine actual MongoDB documents aur schema check kiya aur pata chala ki AI ne `status` field assume ki thi, jabki mere actual schema mein `isActive` field thi."

> "Maine query ko actual schema ke according correct kiya aur phir active users, inactive users, missing fields aur no-result cases ke saath test kiya."

> "Uske baad maine query ki performance bhi verify ki aur ensure kiya ki final solution correct hai."

> "Isse mujhe ye learning mili ki AI-generated code ko blindly trust nahi karna chahiye. AI development ko fast karta hai, lekin final verification developer ki responsibility hoti hai."

---

# Short Answer — Agar Interviewer Sirf 30 Seconds De

> "In a personal MERN project, AI generated a MongoDB query that looked correct but used a field name that didn't exist in my actual schema. I identified it by comparing the query with the real database documents. I corrected the field according to the actual schema, tested multiple edge cases and verified the final result and performance. The key learning was that AI-generated code should always be verified against the actual project requirements and data."

---

# Important Follow-up Questions

## Q1. Why didn't you blindly use AI's solution?

### Answer

> "Because AI doesn't always have the complete context of the project. It can make assumptions about the schema, business logic or existing architecture."

---

## Q2. How do you verify AI-generated code?

### Answer

Main verify karta hoon:

```text
Requirements
Schema
Existing Code
Test Cases
Edge Cases
Runtime Behaviour
Performance
Security
```

---

## Q3. Can AI completely replace a developer?

### Answer

> "No. AI can improve developer productivity, but understanding requirements, making architectural decisions, validating correctness and taking responsibility for production code still requires the developer."

---

## Q4. What if AI gives a solution that works?

### Answer

> "Working code is not automatically production-ready. I would still check correctness, edge cases, security, maintainability and performance."

---

## Q5. What if AI solution is faster but more complex?

### Answer

> "I would compare the actual requirements and trade-offs. I wouldn't choose complexity only for a small performance gain unless the performance requirement justifies it."

---

# Golden Interview Line

> **"I use AI as a development assistant, not as the source of truth. I validate AI-generated code against the actual requirements, schema, tests and production constraints."**

---

# Quick Revision

```text
AI Generated Solution
        ↓
Understand It
        ↓
Compare With Actual Code/Schema
        ↓
Test
        ↓
Find Issue
        ↓
Fix
        ↓
Test Edge Cases
        ↓
Check Performance
        ↓
Use in Project
```

## Remember

**AI = Assistant**

**Developer = Decision Maker**

**Source Code + Requirements + Tests = Source of Truth**