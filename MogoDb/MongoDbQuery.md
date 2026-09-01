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

If a MongoDB query becomes slow, I would investigate the problem step by step.

## 1. Use explain()

First, I will use explain() to understand how MongoDB is executing the query.

Example:

    db.users.find({
      email: "test@gmail.com"
    }).explain("executionStats");

explain() helps us understand:
- Query execution time
- Which index is being used
- How many documents are checked
- How many keys are checked
- Query execution plan

---

## 2. Check executionStats

Important values:

    executionTimeMillis
    totalDocsExamined
    totalKeysExamined
    nReturned
    winningPlan

Example:

    nReturned = 1
    totalDocsExamined = 1000000

This means MongoDB is checking many documents to return only one document.

This can indicate that the query needs a better index.

---

## 3. Check COLLSCAN and IXSCAN

### COLLSCAN

    COLLSCAN

COLLSCAN means MongoDB is scanning the collection.

For a large collection, this can be slow.

### IXSCAN

    IXSCAN

IXSCAN means MongoDB is using an index.

Indexes can make searching much faster.

---

## 4. Index Analysis

First, check existing indexes:

    db.users.getIndexes();

If the query frequently searches by email, create an index:

    db.users.createIndex({
      email: 1
    });

Then run explain() again:

    db.users.find({
      email: "test@gmail.com"
    }).explain("executionStats");

I will check whether MongoDB is now using IXSCAN.

---

## 5. Compound Index

If a query uses multiple fields, I can create a compound index.

Example:

    db.orders.find({
      userId: 101,
      status: "completed"
    });

Create index:

    db.orders.createIndex({
      userId: 1,
      status: 1
    });

Important:

The order of fields in a compound index matters.

---

## 6. Aggregation Optimization

If the slow query uses aggregation, I will optimize the aggregation pipeline.

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

Important rule:

Put $match as early as possible.

Why?

Because it reduces the number of documents processed by later stages.

I will also avoid unnecessary:
- $lookup
- $sort
- $group
- $unwind

---

## 7. Projection

Projection means returning only required fields.

Example:

    db.users.find(
      {
        status: "active"
      },
      {
        name: 1,
        email: 1
      }
    );

This avoids returning unnecessary data.

---

## 8. Pagination

If there are many documents, I should not return all documents at once.

Example:

    db.users.find({
      status: "active"
    }).limit(20);

Basic pagination:

    db.users.find({
      status: "active"
    })
    .skip(20)
    .limit(20);

For very large datasets, cursor/range-based pagination is better than very large skip() values.

Example:

    db.users.find({
      _id: {
        $gt: lastId
      }
    }).limit(20);

---

## 9. Logging

I will check MongoDB logs and profiler to find slow queries.

Example:

    db.setProfilingLevel(1, {
      slowms: 100
    });

Then check recent profiling data:

    db.system.profile.find()
      .sort({
        ts: -1
      })
      .limit(10);

I will also check:
- CPU
- RAM
- Disk I/O
- Database connections
- Server load

---

## 10. Performance Testing

After optimization, I will run the query again:

    db.users.find({
      email: "test@gmail.com"
    }).explain("executionStats");

Then compare before and after.

Example:

    Before:
    Query Time = 12000 ms

    After:
    Query Time = 50 ms

If the query performs well with production-like data and load, the optimization is successful.

---

# Final Approach — Question 5

    Slow Query
        ↓
    explain()
        ↓
    executionStats
        ↓
    Check COLLSCAN / IXSCAN
        ↓
    Index Analysis
        ↓
    Aggregation Optimization
        ↓
    Projection
        ↓
    Pagination
        ↓
    Logging
        ↓
    Performance Testing

---

# Question 6 — MongoDB Index Types (10 Marks)

## Question

Explain the following MongoDB index types and when you would use each:

1. Single Field
2. Compound
3. Multikey
4. Text
5. TTL
6. Sparse
7. Partial
8. Hashed

Also explain:
- What is MongoDB Sharding?
- What problem does it solve?
- Key components: Shard, Config Server, Mongos
- When should sharding be used?

---

# Answer

## 1. Single Field Index

A Single Field Index is an index created on one field.

Example:

    db.users.createIndex({
      email: 1
    });

Query:

    db.users.find({
      email: "test@gmail.com"
    });

Use:

When we frequently search or sort using one field.

Examples:
- email
- username
- phone

---

## 2. Compound Index

A Compound Index is an index created on multiple fields.

Example:

    db.users.createIndex({
      city: 1,
      age: 1
    });

Query:

    db.users.find({
      city: "Chandigarh",
      age: 25
    });

Use:

When queries frequently use multiple fields.

Important:

The order of fields in a compound index matters.

---

## 3. Multikey Index

A Multikey Index is used for fields that contain arrays.

Example:

    {
      name: "Ritesh",
      skills: [
        "React",
        "Node.js",
        "MongoDB"
      ]
    }

Create index:

    db.users.createIndex({
      skills: 1
    });

Query:

    db.users.find({
      skills: "React"
    });

Use:

When documents contain arrays and we need to search array values.

---

## 4. Text Index

A Text Index is used for text searching.

Example:

    db.products.createIndex({
      name: "text",
      description: "text"
    });

Search:

    db.products.find({
      $text: {
        $search: "mobile phone"
      }
    });

Use:
- Product search
- Article search
- Blog search
- Description search

---

## 5. TTL Index

TTL means Time To Live.

A TTL Index automatically removes documents after a specific time.

Example:

    db.sessions.createIndex(
      {
        createdAt: 1
      },
      {
        expireAfterSeconds: 3600
      }
    );

Use:
- OTP data
- Sessions
- Temporary data
- Logs
- Cache data

---

## 6. Sparse Index

A Sparse Index creates index entries only for documents where the indexed field exists.

Example:

    db.users.createIndex(
      {
        phone: 1
      },
      {
        sparse: true
      }
    );

Use:

When a field is optional and many documents do not contain that field.

---

## 7. Partial Index

A Partial Index indexes only documents that match a specific condition.

Example:

    db.users.createIndex(
      {
        email: 1
      },
      {
        partialFilterExpression: {
          status: "active"
        }
      }
    );

Only active users will be included in this index.

Use:

When we only need to index documents that satisfy a condition.

---

## 8. Hashed Index

A Hashed Index stores a hashed value of the indexed field.

Example:

    db.users.createIndex({
      userId: "hashed"
    });

Use:

It is commonly useful with MongoDB sharding when we want a more even distribution of data.

---

# Sparse vs Partial Index

## Sparse Index

Checks whether the field exists.

    Field exists → Include in index
    Field missing → Do not include

## Partial Index

Uses a specific condition.

    status = "active" → Include
    status = "inactive" → Do not include

---

# MongoDB Sharding

## What is Sharding?

Sharding means distributing a large MongoDB database across multiple servers.

Example:

              Database
                 |
        -------------------
        |        |        |
      Shard 1  Shard 2  Shard 3

Data is distributed between multiple shards.

---

# What Problem Does Sharding Solve?

Sharding is used for horizontal scaling.

It helps when:
- Database size becomes very large
- One server cannot handle the workload
- Storage requirements become too high
- Read/write traffic becomes very high
- Data needs to be distributed across multiple servers

---

# MongoDB Sharding Components

## 1. Shard

A Shard stores the actual application data.

    Shard 1
    Shard 2
    Shard 3

Each shard contains a portion of the data.

---

## 2. Config Server

The Config Server stores metadata about the sharded cluster.

It keeps information about:
- Cluster configuration
- Data distribution
- Shard information

---

## 3. Mongos

mongos works as a query router.

Application sends the request to mongos.

    Application
         ↓
       Mongos
       ↓  ↓  ↓
    Shard1 Shard2 Shard3

mongos sends the request to the correct shard or shards.

---

# When Should We Use Sharding?

We should not use sharding just because the database is growing.

First optimize:

    Schema
       ↓
    Indexes
       ↓
    Queries
       ↓
    Aggregation
       ↓
    Server Resources
       ↓
    Scaling
       ↓
    Sharding

Sharding should be considered when a single MongoDB server can no longer efficiently handle:

- Data size
- Storage
- Read/write traffic
- Overall workload

---

# Quick Revision

## Question 5 — Query Optimization

    explain()
    executionStats
    COLLSCAN
    IXSCAN
    Indexes
    Compound Index
    Aggregation
    Projection
    Pagination
    Logging
    Performance Testing

## Question 6 — Index Types

    Single Field → One field
    Compound     → Multiple fields
    Multikey     → Array fields
    Text         → Text search
    TTL          → Automatically expire documents
    Sparse       → Field exists
    Partial      → Condition based
    Hashed       → Hash based indexing / sharding

## Sharding

    Shard         → Stores data
    Config Server → Stores cluster metadata
    Mongos        → Query router

## Main Goal

    Better Query Performance
            +
    Horizontal Scaling