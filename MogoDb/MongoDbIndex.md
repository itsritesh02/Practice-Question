
---

# Q6. MongoDB Index Types

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

### Definition

An index created on one field is called a Single Field Index.

### Example

    db.users.createIndex({
        email: 1
    });

Query:

    db.users.find({
        email: "ritesh@gmail.com"
    });

### Explanation

If we frequently search users using email, an index on email can make the search faster.

### Easy to Remember

    Single Field = One Field

---

# 2. Compound Index

### Definition

An index created on multiple fields is called a Compound Index.

### Example

    db.users.createIndex({
        city: 1,
        age: 1
    });

Query:

    db.users.find({
        city: "Chandigarh",
        age: 25
    });

### Explanation

Here we are searching using two fields:

- city
- age

So a compound index can be useful.

### Easy to Remember

    Compound = Multiple Fields

### Important

The order of fields in a compound index matters.

---

# 3. Multikey Index

### Definition

A Multikey Index is used for fields that contain arrays.

### Example Document

    {
        name: "Ritesh",
        skills: [
            "React",
            "Node.js",
            "MongoDB"
        ]
    }

### Create Index

    db.users.createIndex({
        skills: 1
    });

### Query

    db.users.find({
        skills: "React"
    });

### Explanation

Because `skills` is an array, MongoDB can use a Multikey Index.

### Easy to Remember

    Multikey = Array

---

# 4. Text Index

### Definition

A Text Index is used for text searching.

### Example

    db.products.createIndex({
        name: "text",
        description: "text"
    });

### Search

    db.products.find({
        $text: {
            $search: "mobile phone"
        }
    });

### Explanation

If we want users to search products, articles or descriptions using words, a text index can be useful.

### Examples

- Product Search
- Blog Search
- Article Search
- Description Search

### Easy to Remember

    Text = Text Search

---

# 5. TTL Index

### Definition

TTL means:

    Time To Live

A TTL Index automatically removes documents after a specified time.

### Example

    db.sessions.createIndex(
        {
            createdAt: 1
        },
        {
            expireAfterSeconds: 3600
        }
    );

### Explanation

Documents can expire based on the `createdAt` field.

### Useful For

- OTP
- Sessions
- Temporary Data
- Cache Data
- Logs

### Easy to Remember

    TTL = Automatically Expire

---

# 6. Sparse Index

### Definition

A Sparse Index creates index entries only for documents where the indexed field exists.

### Example

    db.users.createIndex(
        {
            phone: 1
        },
        {
            sparse: true
        }
    );

### Example Data

User 1:

    {
        name: "A",
        phone: "9999999999"
    }

User 2:

    {
        name: "B"
    }

User 1 has the `phone` field, so it gets an index entry.

User 2 does not have the `phone` field, so it does not get an index entry for that field.

### Easy to Remember

    Sparse = Field Exists

---

# 7. Partial Index

### Definition

A Partial Index indexes only documents that match a specified condition.

### Example

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

### Example Data

    User 1 → active
    User 2 → inactive
    User 3 → active

Only active users are included in the partial index.

### Easy to Remember

    Partial = Condition Based

---

# 8. Hashed Index

### Definition

A Hashed Index indexes the hashed value of a field.

### Example

    db.users.createIndex({
        userId: "hashed"
    });

### Explanation

MongoDB uses a hash of the field value for the index.

Hashed indexes are commonly useful with sharding when we want a more even distribution of data.

### Easy to Remember

    Hashed = Hash / Sharding

---

# Sparse vs Partial Index

## Sparse Index

Checks whether the field exists.

    Field exists
         ↓
    Include in index

    Field missing
         ↓
    No index entry

---

## Partial Index

Checks a condition.

    status = "active"
         ↓
    Include in index

    status = "inactive"
         ↓
    No index entry

---

## Easy Difference

    Sparse  → Field Exists
    Partial → Condition

---

# MongoDB Sharding

## What is Sharding?

Sharding means distributing data across multiple MongoDB servers.

### Example

Suppose we have a very large database.

Instead of:

    One Server
        ↓
    All Data

We can distribute data:

             MongoDB
                |
       -------------------
       |        |        |
    Shard 1  Shard 2  Shard 3

Each shard stores a portion of the data.

---

# What Problem Does Sharding Solve?

Sharding is mainly used for horizontal scaling.

It can help when:

- Database size becomes very large
- Storage requirements become very high
- Read/write traffic becomes very high
- One server cannot handle the workload
- Data needs to be distributed across multiple servers

---

# MongoDB Sharding Components

## 1. Shard

### What is it?

A Shard stores the actual application data.

Example:

    Shard 1 → Part of Data
    Shard 2 → Part of Data
    Shard 3 → Part of Data

### Easy to Remember

    Shard = Data

---

## 2. Config Server

### What is it?

Config Server stores metadata and configuration information about the sharded cluster.

### Easy to Remember

    Config Server = Metadata / Configuration

---

## 3. Mongos

### What is it?

`mongos` is a query router.

The application sends a request to `mongos`.

Then `mongos` routes the request to the appropriate shard or shards.

### Architecture

    Application
         ↓
       Mongos
         ↓
    ----------------
    ↓       ↓       ↓
    Shard1 Shard2 Shard3

### Easy to Remember

    Mongos = Query Router

---

# When Should We Use Sharding?

We should not immediately use sharding just because the database is growing.

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

Sharding should be considered when a single MongoDB server can no longer efficiently handle the required:

- Data size
- Storage
- Read/write workload
- Overall traffic

---

# Q6 — Quick Revision

    Single Field
    → One Field

    Compound
    → Multiple Fields

    Multikey
    → Array

    Text
    → Text Search

    TTL
    → Automatically Expire

    Sparse
    → Field Exists

    Partial
    → Condition Based

    Hashed
    → Hash / Sharding

---

# Sharding Quick Revision

    Shard
    → Stores Data

    Config Server
    → Stores Metadata / Configuration

    Mongos
    → Query Router

---

# FINAL INTERVIEW REVISION

## Q5

    explain()
        ↓
    executionStats
        ↓
    COLLSCAN / IXSCAN
        ↓
    Index Analysis
        ↓
    Aggregation
        ↓
    Projection
        ↓
    Pagination
        ↓
    Logging
        ↓
    Performance Testing

## Q6

    Single Field → One Field
    Compound → Multiple Fields
    Multikey → Array
    Text → Text Search
    TTL → Auto Expire
    Sparse → Field Exists
    Partial → Condition
    Hashed → Hash / Sharding

    Shard → Data
    Config Server → Metadata
    Mongos → Router





    Q6. MongoDB Index Types

1. Single Field Index:
   Index on one field.

   db.users.createIndex({ email: 1 });

   Used when we frequently search using one field.

2. Compound Index:
   Index on multiple fields.

   db.users.createIndex({
       city: 1,
       age: 1
   });

   Used when queries use multiple fields.

3. Multikey Index:
   Used for array fields.

   db.users.createIndex({ skills: 1 });

4. Text Index:
   Used for text searching.

   db.products.createIndex({
       name: "text",
       description: "text"
   });

5. TTL Index:
   Automatically expires documents after a specified time.

   db.sessions.createIndex(
       { createdAt: 1 },
       { expireAfterSeconds: 3600 }
   );

   Used for sessions, temporary data, etc.

6. Sparse Index:
   Creates index entries only for documents where the field exists.

   db.users.createIndex(
       { phone: 1 },
       { sparse: true }
   );

7. Partial Index:
   Indexes only documents matching a condition.

   db.users.createIndex(
       { email: 1 },
       {
           partialFilterExpression: {
               status: "active"
           }
       }
   );

8. Hashed Index:
   Creates an index using a hashed value.
   It is commonly useful with sharding for distributing data.

   db.users.createIndex({
       userId: "hashed"
   });


MongoDB Sharding:

Sharding means distributing data across multiple MongoDB servers.
It is used for horizontal scaling when a single server cannot
efficiently handle the data size or workload.

Main components:

1. Shard:
   Stores the actual application data.

2. Config Server:
   Stores metadata and cluster configuration.

3. Mongos:
   Acts as a query router between the application and shards.

Architecture:

Application
     ↓
   Mongos
   ↓  ↓  ↓
Shard1 Shard2 Shard3

Sharding should be considered when database size, storage or
read/write workload becomes too large for a single server.