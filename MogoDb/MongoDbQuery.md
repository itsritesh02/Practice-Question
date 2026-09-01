# CodeTribe MERN Stack Machine Round
# Section 3 — Database (MongoDB + SQL)

---

# Question 5 — MongoDB Query Optimization (10 Marks)

## Question

A MongoDB query that normally takes 100 ms is now taking 12 seconds in production.

Explain step by step how you would investigate and optimize it.

Include:
- explain()
- executionStats
- Index analysis
- Aggregation optimization
- Projection
- Pagination
- Logging
- Performance testing

---

# Answer

# CodeTribe — MERN Stack Machine Round

## Section 3 — Database (MongoDB)

---

# Q5. MongoDB Query Optimization

## Question

A MongoDB query that normally takes 100 ms is now taking 12 seconds in production.

Explain step by step how you would investigate and optimize it.

### Topics to Cover

- explain()
- executionStats
- Index Analysis
- Aggregation Optimization
- Projection
- Pagination
- Logging
- Performance Testing

---

# Answer

If a MongoDB query becomes slow, I will first find the reason for the slowdown and then optimize the query.

I will follow these steps:

1. Use `explain()`
2. Check `executionStats`
3. Check `COLLSCAN` and `IXSCAN`
4. Analyze indexes
5. Optimize aggregation
6. Use projection
7. Use pagination
8. Check logs and server resources
9. Test the query again

---

## Step 1 — Use explain()

### What is explain()?

`explain()` shows how MongoDB executes a query.

### Example

    db.users.find({
        email: "ritesh@gmail.com"
    }).explain("executionStats");

### Explanation

Suppose our database contains 10 lakh users.

We want to find only one user using email.

`explain()` helps us understand:

- How much time the query takes
- How many documents MongoDB checks
- How many index keys MongoDB checks
- Whether an index is being used
- Which execution plan MongoDB selected

---

## Step 2 — Check executionStats

### What is executionStats?

`executionStats` gives actual performance information after executing the query.

Important values:

    executionTimeMillis
    totalDocsExamined
    totalKeysExamined
    nReturned
    winningPlan

### Example

    executionTimeMillis = 12000
    totalDocsExamined = 1000000
    nReturned = 1

### Explanation

MongoDB checked 10 lakh documents but returned only 1 document.

This means the query is not efficient and we should investigate the query plan and indexes.

---

## Step 3 — Check COLLSCAN and IXSCAN

### COLLSCAN

`COLLSCAN` means Collection Scan.

MongoDB scans documents in the collection to find matching data.

### Example

    10 lakh users
          ↓
       User 1
       User 2
       User 3
         ...
       User 10 lakh
          ↓
       Required User

### Problem

If the collection is very large, scanning many documents can make the query slow.

---

### IXSCAN

`IXSCAN` means Index Scan.

MongoDB uses an index to find matching data.

### Example

    10 lakh users
          ↓
      Email Index
          ↓
    Required User

Using an appropriate index can make searching much faster.

---

## Step 4 — Index Analysis

First, I will check the existing indexes.

### Example

    db.users.getIndexes();

Suppose our query is:

    db.users.find({
        email: "ritesh@gmail.com"
    });

If there is no suitable index on `email`, I can create one.

### Create Index

    db.users.createIndex({
        email: 1
    });

Then I will run `explain()` again.

    db.users.find({
        email: "ritesh@gmail.com"
    }).explain("executionStats");

### What will I check?

I will check whether:

    COLLSCAN

has changed to:

    IXSCAN

I will also check whether `totalDocsExamined` has decreased.

---

# Compound Index

## What is a Compound Index?

A Compound Index is an index created on multiple fields.

### Example Query

    db.orders.find({
        userId: 101,
        status: "completed"
    });

The query uses two fields:

- userId
- status

So we can create:

    db.orders.createIndex({
        userId: 1,
        status: 1
    });

### Explanation

Compound indexes are useful when queries frequently filter or sort using multiple fields.

Important:

The order of fields in a compound index matters.

---

# Step 5 — Aggregation Optimization

## What is Aggregation?

Aggregation processes and transforms documents through multiple stages.

### Example

    db.orders.aggregate([
        {
            $match: {
                status: "completed"
            }
        },
        {
            $project: {
                userId: 1,
                amount: 1
            }
        }
    ]);

### Explanation

Suppose we have:

    10 lakh orders

But only:

    1 lakh completed orders

If we use `$match` early:

    10 lakh orders
          ↓
        $match
          ↓
    1 lakh orders
          ↓
     Next stages

Now the next stages process only 1 lakh documents.

Therefore, I will generally put `$match` early when appropriate.

I will also avoid unnecessary:

- `$lookup`
- `$sort`
- `$group`
- `$unwind`

because they can increase processing cost.

---

# Step 6 — Projection

## What is Projection?

Projection means returning only the fields that are required.

Suppose a user document contains:

    name
    email
    phone
    address
    profile
    createdAt

But the frontend only needs:

    name
    email

We can use:

    db.users.find(
        {
            status: "active"
        },
        {
            name: 1,
            email: 1
        }
    );

### Explanation

Instead of returning the complete document, we return only the required fields.

This reduces unnecessary data transfer and processing.

---

# Step 7 — Pagination

## What is Pagination?

Pagination means returning data in smaller pages instead of returning a large number of documents at once.

Suppose we have:

    10 lakh users

Instead of sending all users:

    10 lakh users → Frontend

We can send:

    Page 1 → 20 users
    Page 2 → 20 users
    Page 3 → 20 users

### Example

    db.users.find({
        status: "active"
    }).limit(20);

Basic pagination can use:

    db.users.find({
        status: "active"
    })
    .skip(20)
    .limit(20);

### Important

For very large datasets, very large `skip()` values can become inefficient.

In that case, cursor/range-based pagination can be better.

Example:

    db.users.find({
        _id: {
            $gt: lastId
        }
    }).limit(20);

---

# Step 8 — Logging and Server Check

I will also check MongoDB logs and profiler to identify slow operations.

### Example

    db.setProfilingLevel(1, {
        slowms: 100
    });

I will also check server resources:

- CPU
- RAM
- Disk I/O
- Database connections
- Server load

### Explanation

Sometimes the query itself is fine, but the server may be under heavy load.

---

# Step 9 — Performance Testing

After optimization, I will run the same query again.

### Example

    db.users.find({
        email: "ritesh@gmail.com"
    }).explain("executionStats");

Before:

    executionTimeMillis = 12000

After:

    executionTimeMillis = 50

### Explanation

If the query becomes significantly faster and performs well with production-like data and load, the optimization is successful.

---

# Q5 — Final Interview Answer

If a MongoDB query becomes slow, I will first use `explain("executionStats")` to identify the bottleneck.

Then I will check `totalDocsExamined`, `executionTimeMillis`, and whether MongoDB is using `COLLSCAN` or `IXSCAN`.

After that, I will analyze existing indexes and create or optimize indexes if required.

I will also optimize aggregation pipelines, use projection and pagination, check MongoDB logs and server resources, and finally test the query again to compare the performance.

---

# Q5 — Quick Flow

    Slow Query
        ↓
    explain()
        ↓
    executionStats
        ↓
    COLLSCAN / IXSCAN
        ↓
    Index Analysis
        ↓
    Aggregation Optimization
        ↓
    Projection
        ↓
    Pagination
        ↓
    Logs + Server
        ↓
    Performance Testing

---

