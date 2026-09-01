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

# CodeTribe MERN Stack Machine Round
# MongoDB — Q5 & Q6

==================================================
Q5. MONGODB QUERY OPTIMIZATION
==================================================

QUESTION:

A MongoDB query that normally takes 100 ms is now taking
12 seconds in production.

Explain step by step how you would investigate and optimize it.

--------------------------------------------------
ANSWER
--------------------------------------------------

If a MongoDB query becomes slow, I will first identify
where the problem is and then optimize the query.

I will follow these steps:

1. Use explain()
2. Check executionStats
3. Check COLLSCAN / IXSCAN
4. Check and create indexes
5. Optimize aggregation
6. Use projection
7. Use pagination
8. Check logs and server resources
9. Test the query again


--------------------------------------------------
STEP 1 — USE explain()
--------------------------------------------------

explain() tells us how MongoDB is executing a query.

Example:

db.users.find({
    email: "ritesh@gmail.com"
}).explain("executionStats");

Explanation:

Suppose our users collection has 10 lakh users.

We want to find only one user:

email = "ritesh@gmail.com"

explain() helps us understand:

- How much time the query took
- How many documents MongoDB checked
- How many index keys MongoDB checked
- Whether an index was used
- Which execution plan was selected


--------------------------------------------------
STEP 2 — CHECK executionStats
--------------------------------------------------

Important values are:

executionTimeMillis
totalDocsExamined
totalKeysExamined
nReturned
winningPlan

Example:

executionTimeMillis = 12000
totalDocsExamined = 1000000
nReturned = 1

Explanation:

MongoDB checked 10 lakh documents but returned only 1 document.

This indicates that the query is not efficient and
we should investigate the indexes/query plan.


--------------------------------------------------
STEP 3 — CHECK COLLSCAN AND IXSCAN
--------------------------------------------------

COLLSCAN means Collection Scan.

Example:

COLLSCAN

Explanation:

MongoDB is scanning documents from the collection to find
the required document.

For example:

10 lakh users
     ↓
User 1 → Check
User 2 → Check
User 3 → Check
...
User 10 lakh → Check

This can be slow for a large collection.


IXSCAN means Index Scan.

Example:

IXSCAN

Explanation:

MongoDB is using an index to find the required data.

Instead of checking every document:

10 lakh users
     ↓
Email Index
     ↓
Required User

This can make the query much faster.


--------------------------------------------------
STEP 4 — CHECK EXISTING INDEXES
--------------------------------------------------

First, I will check the existing indexes:

db.users.getIndexes();

Explanation:

This tells me which indexes already exist on the collection.

Example:

Suppose the query is:

db.users.find({
    email: "ritesh@gmail.com"
});

But there is no index on email.

Then I can create one:

db.users.createIndex({
    email: 1
});

Now MongoDB can use the email index for searching.

I will again test:

db.users.find({
    email: "ritesh@gmail.com"
}).explain("executionStats");

I will check whether the execution plan now uses IXSCAN
and whether the number of documents examined has decreased.


--------------------------------------------------
STEP 5 — COMPOUND INDEX
--------------------------------------------------

If a query frequently uses multiple fields, I can use
a compound index.

Example query:

db.orders.find({
    userId: 101,
    status: "completed"
});

Here we are searching using:

userId
status

So I can create:

db.orders.createIndex({
    userId: 1,
    status: 1
});

Explanation:

Compound index means an index on multiple fields.

It can improve queries that commonly filter using those fields.

Important:

The order of fields in a compound index matters.


--------------------------------------------------
STEP 6 — AGGREGATION OPTIMIZATION
--------------------------------------------------

If the slow query uses aggregation, I will optimize the
aggregation pipeline.

Example:

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

Explanation:

Suppose there are 10 lakh orders.

Only 1 lakh orders are completed.

If we filter first:

10 lakh orders
      ↓
   $match
      ↓
1 lakh orders
      ↓
Next stages

Now the next stages process only 1 lakh documents.

Therefore, I will generally put $match as early as possible
when appropriate.

I will also avoid unnecessary:

$lookup
$sort
$group
$unwind

because they can increase processing cost.


--------------------------------------------------
STEP 7 — PROJECTION
--------------------------------------------------

Projection means returning only the fields we need.

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

Then:

db.users.find(
    {
        status: "active"
    },
    {
        name: 1,
        email: 1
    }
);

Explanation:

Instead of returning the complete document, we return
only the required fields.

This reduces unnecessary data transfer and processing.


--------------------------------------------------
STEP 8 — PAGINATION
--------------------------------------------------

If a collection contains a large number of documents,
I should not return all documents at once.

Example:

10 lakh users

Instead of:

10 lakh users → frontend

I can return:

Page 1 → 20 users
Page 2 → 20 users
Page 3 → 20 users

Example:

db.users.find({
    status: "active"
}).limit(20);

Basic pagination can use:

db.users.find({
    status: "active"
})
.skip(20)
.limit(20);

However, for very large datasets, very large skip() values
can become inefficient.

In that case, cursor/range-based pagination can be better.

Example:

db.users.find({
    _id: {
        $gt: lastId
    }
}).limit(20);


--------------------------------------------------
STEP 9 — CHECK LOGS AND SERVER
--------------------------------------------------

I will also check MongoDB logs/profiler for slow queries.

Example:

db.setProfilingLevel(1, {
    slowms: 100
});

This helps identify slow database operations.

I will also check server resources:

CPU
RAM
Disk I/O
Database connections
Server load

Explanation:

Sometimes the query itself is fine, but the server may be
under heavy load.


--------------------------------------------------
STEP 10 — PERFORMANCE TESTING
--------------------------------------------------

After making changes, I will run the same query again.

Example:

db.users.find({
    email: "ritesh@gmail.com"
}).explain("executionStats");

Before:

executionTimeMillis = 12000

After:

executionTimeMillis = 50

Explanation:

If the query time decreases significantly and the query
performs well with production-like data/load, the optimization
has been successful.


--------------------------------------------------
Q5 FINAL ANSWER
--------------------------------------------------

I will first use explain("executionStats") to identify
the bottleneck. Then I will check COLLSCAN and IXSCAN,
analyze existing indexes and create or optimize indexes
if required. I will also optimize aggregation pipelines,
use projection and pagination, check database logs and
server resources, and finally test the query again.


