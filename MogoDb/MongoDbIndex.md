# Q6 — MongoDB Indexes & Sharding

## Question

Explain the following MongoDB index types and when you would use each:

1. Single Field Index
2. Compound Index
3. Multikey Index
4. Text Index
5. TTL Index
6. Sparse Index
7. Partial Index
8. Hashed Index

Also explain:

- What is MongoDB Sharding?
- What problem does Sharding solve?
- What is a Shard?
- What is a Config Server?
- What is Mongos?
- What is a Shard Key?
- What is Targeted Query?
- What is Scatter-Gather?
- When should Sharding be used?
- Vertical Scaling vs Horizontal Scaling

---

# PART 1 — MongoDB Indexes

## 1. What is an Index?

### English

An Index is a special data structure that helps MongoDB find documents efficiently and can improve query performance.

Without an appropriate index, MongoDB may need to scan many documents in a collection.

### Hindi / Hinglish

Index ek special data structure hai jo MongoDB ko documents ko efficiently aur faster find karne mein help karta hai.

Agar collection bahut badi hai aur required index nahi hai, to MongoDB ko bahut saare documents scan karne pad sakte hain.

### Example

```javascript
db.users.find({
  email: "test@gmail.com"
});
```

Agar `email` par index create kiya gaya hai:

```javascript
db.users.createIndex({
  email: 1
});
```

to MongoDB email ke basis par matching documents ko efficiently find kar sakta hai.

---

# 2. How to Check Existing Indexes?

```javascript
db.users.getIndexes();
```

Ye collection ke existing indexes show karta hai.

---

# 3. How to Check Query Performance?

MongoDB query ko analyze karne ke liye:

```javascript
db.users.find({
  email: "test@gmail.com"
}).explain("executionStats");
```

Important values:

```text
executionTimeMillis
totalDocsExamined
totalKeysExamined
nReturned
```

### Hindi / Hinglish

`explain("executionStats")` se hume pata chal sakta hai ki query ka execution kaise ho raha hai aur MongoDB kitne documents/index keys examine kar raha hai.

---

# 4. COLLSCAN vs IXSCAN

## COLLSCAN

### English

COLLSCAN means MongoDB is scanning the collection to find matching documents.

### Hindi / Hinglish

COLLSCAN ka matlab MongoDB collection ke documents ko scan karke matching data find kar raha hai.

Example:

```text
Collection
   |
   ├── Document 1
   ├── Document 2
   ├── Document 3
   ├── Document 4
   └── ...
```

Agar MongoDB ko bahut saare documents check karne pad rahe hain, to query slow ho sakti hai.

---

## IXSCAN

### English

IXSCAN means MongoDB is using an index to find matching documents.

### Hindi / Hinglish

IXSCAN ka matlab MongoDB index ka use karke matching documents ko find kar raha hai.

```text
Query
  |
  ↓
Index
  |
  ↓
Matching Documents
```

### Easy Way to Remember

```text
COLLSCAN → Collection Scan

IXSCAN   → Index Scan
```

---

# 5. Single Field Index

## English

A Single Field Index is an index created on one field.

## Hindi / Hinglish

Single Field Index mein sirf ek field par index create kiya jata hai.

### Example

```javascript
db.users.createIndex({
  email: 1
});
```

### Query

```javascript
db.users.find({
  email: "test@gmail.com"
});
```

### When to Use?

Use it when one field is frequently used for:

- Searching
- Filtering
- Sorting

### Examples

```text
email
username
phone
userId
createdAt
```

### Interview Answer

> "Single field index ek single field par create hota hai. Agar kisi particular field par frequently search, filter ya sort karna ho, to single field index use kar sakte hain."

---

# 6. Compound Index

## English

A Compound Index is an index created on multiple fields.

## Hindi / Hinglish

Compound Index mein multiple fields ko ek hi index mein include kiya jata hai.

### Example

```javascript
db.users.createIndex({
  status: 1,
  createdAt: -1
});
```

### Query

```javascript
db.users.find({
  status: "active"
}).sort({
  createdAt: -1
});
```

### Important

Compound index mein field order very important hota hai.

Example:

```javascript
{
  status: 1,
  createdAt: -1
}
```

aur:

```javascript
{
  createdAt: -1,
  status: 1
}
```

dono indexes ka behavior exactly same nahi hota.

### When to Use?

Jab queries frequently multiple fields ka use karti hain:

- Filtering
- Searching
- Sorting

### Interview Answer

> "Compound index multiple fields par create hota hai. Jab query mein multiple fields use hote hain, tab compound index useful hota hai. Isme field order important hota hai."

---

# 7. Multikey Index

## English

A Multikey Index is used for fields that contain arrays.

## Hindi / Hinglish

Multikey Index ka use array fields ke liye hota hai.

### Example Document

```javascript
{
  name: "Arbaj",
  skills: [
    "JavaScript",
    "React",
    "Node.js"
  ]
}
```

### Index

```javascript
db.users.createIndex({
  skills: 1
});
```

### Query

```javascript
db.users.find({
  skills: "React"
});
```

MongoDB array field par index ko multikey index ke roop mein handle kar sakta hai.

### Common Use Cases

```text
skills
tags
categories
roles
products
```

### Interview Answer

> "Multikey index array fields ke liye use hota hai. Agar document mein array field hai aur hume array ke elements par query karni hai, to multikey index useful hota hai."

---

# 8. Text Index

## English

A Text Index is used for text search.

## Hindi / Hinglish

Text Index ka use text ke andar keywords search karne ke liye hota hai.

### Example

```javascript
db.products.createIndex({
  name: "text",
  description: "text"
});
```

### Search

```javascript
db.products.find({
  $text: {
    $search: "laptop"
  }
});
```

### Common Use Cases

```text
Product Search
Blog Search
Article Search
Description Search
Keyword Search
```

### Interview Answer

> "Text index text search ke liye use hota hai. Jaise product name, description ya article ke andar kisi keyword ko search karna ho."

---

# 9. TTL Index

## Full Form

TTL = Time To Live

## English

A TTL Index is used to automatically expire documents after a specified amount of time.

## Hindi / Hinglish

TTL Index ka use time-based data ko automatically expire/remove karne ke liye hota hai.

### Example

```javascript
db.sessions.createIndex(
  {
    createdAt: 1
  },
  {
    expireAfterSeconds: 3600
  }
);
```

`3600 seconds = 1 hour`

### Common Use Cases

```text
Sessions
Temporary Tokens
OTP Records
Temporary Data
Logs
Cache Data
```

### Important

TTL deletion exactly same second par hona guaranteed nahi hota.

MongoDB background process ke through expired documents ko remove karta hai.

### Interview Answer

> "TTL index ka use time-based data ko automatically expire karne ke liye hota hai. Jaise sessions, temporary tokens, OTP records ya logs."

---

# 10. Sparse Index

## English

A Sparse Index only indexes documents where the indexed field exists.

## Hindi / Hinglish

Sparse Index sirf un documents ko index karta hai jisme indexed field exist karti hai.

### Example

```javascript
db.users.createIndex(
  {
    phone: 1
  },
  {
    sparse: true
  }
);
```

Suppose:

```javascript
{
  name: "A",
  phone: "9999999999"
}
```

Aur:

```javascript
{
  name: "B"
}
```

Second document mein `phone` field exist nahi karti.

Sparse index us document ko index nahi karega.

### When to Use?

Jab koi field optional ho aur har document mein present na ho.

### Interview Answer

> "Sparse index sirf un documents ko index karta hai jisme indexed field exist karti hai. Ye optional fields ke liye useful hota hai."

---

# 11. Partial Index

## English

A Partial Index indexes only documents that satisfy a specific condition.

## Hindi / Hinglish

Partial Index sirf un documents ko index karta hai jo specific condition satisfy karte hain.

### Example

```javascript
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
```

Is index mein sirf:

```text
status = active
```

wale documents included honge.

### Query

```javascript
db.users.find({
  status: "active",
  email: "test@gmail.com"
});
```

### When to Use?

Jab collection ke sirf ek specific subset ke liye index chahiye.

### Interview Answer

> "Partial index specific condition ko satisfy karne wale documents par hi create hota hai. Jab hume collection ke sirf ek subset ke liye index chahiye, tab partial index use karte hain."

---

# 12. Hashed Index

## English

A Hashed Index indexes a hashed representation of a field value.

## Hindi / Hinglish

Hashed Index field ki value ka hash generate karke index karta hai.

### Example

```javascript
db.users.createIndex({
  userId: "hashed"
});
```

### Common Use

Hashed indexes are commonly useful for hash-based distribution, especially in sharding.

### Important

Hashed indexes range queries ke liye suitable nahi hote.

Example:

```text
> 100
< 500
BETWEEN values
```

jaise range queries ke liye hashed index appropriate choice nahi hai.

### Interview Answer

> "Hashed index field ki value ko hash karta hai. Iska common use case sharding mein hash-based data distribution hai. Ye range queries ke liye suitable nahi hota."

---

# 13. All Index Types — Quick Revision

| Index Type | Meaning | Main Use |
|---|---|---|
| Single Field | One field | Search / Filter / Sort |
| Compound | Multiple fields | Multi-field queries |
| Multikey | Array field | Array search |
| Text | Text search | Keyword search |
| TTL | Time based | Auto-expire data |
| Sparse | Field exists | Optional fields |
| Partial | Condition based | Specific subset |
| Hashed | Hash based | Hash distribution |

---

# 14. Sparse vs Partial Index

This is a very common interview question.

## Sparse Index

```text
Field Exists
     ↓
Index
```

Example:

```javascript
{
  sparse: true
}
```

## Partial Index

```text
Condition Matches
       ↓
     Index
```

Example:

```javascript
{
  partialFilterExpression: {
    status: "active"
  }
}
```

### Easy Way to Remember

```text
Sparse  → Field Exists

Partial → Condition Matches
```

---

# PART 2 — MongoDB Sharding

# 15. What is Sharding?

## English

Sharding is a horizontal scaling technique where MongoDB data is distributed across multiple servers called shards.

## Hindi / Hinglish

MongoDB Sharding ek horizontal scaling technique hai jisme large data ko multiple servers/shards mein distribute kiya jata hai.

### Simple Architecture

```text
                 Application
                      |
                      ↓
                    Mongos
                      |
          -------------------------
          |           |           |
          ↓           ↓           ↓
       Shard 1     Shard 2     Shard 3
```

Har shard data ka ek portion store karta hai.

---

# 16. What Problem Does Sharding Solve?

## English

Sharding helps MongoDB scale beyond the resources of a single server.

## Hindi / Hinglish

Agar database itna bada ho jaye ki ek single server ki CPU, RAM, Storage ya Disk I/O capacity sufficient na ho, to data ko multiple servers mein distribute kiya ja sakta hai.

### Main Problems Solved

```text
Large Dataset
High Traffic
High Storage Requirement
High Read/Write Workload
Single Server Bottleneck
Need for Horizontal Scaling
```

---

# 17. Benefits of Sharding

## 1. Large Dataset

Large collections ko multiple servers mein distribute kar sakte hain.

## 2. High Traffic

Multiple shards workload ko distribute kar sakte hain.

## 3. More Storage

Multiple machines ki storage use ki ja sakti hai.

## 4. Horizontal Scaling

Need ke according additional capacity add ki ja sakti hai.

---

# 18. MongoDB Sharding Components

MongoDB sharded cluster ke main components:

```text
1. Shard
2. Config Server
3. Mongos
```

---

# 19. Shard

## English

A Shard stores a portion of the application data.

## Hindi / Hinglish

Shard actual application data ka ek portion store karta hai.

Example:

```text
Shard 1 → Data Part 1
Shard 2 → Data Part 2
Shard 3 → Data Part 3
```

### Interview Answer

> "Shard actual application data ko store karta hai. Sharded cluster mein multiple shards ho sakte hain."

---

# 20. Config Server

## English

The Config Server stores metadata and configuration information for the sharded cluster.

## Hindi / Hinglish

Config Server sharded cluster ka metadata aur configuration information maintain karta hai.

Ye cluster ke data distribution se related metadata maintain karta hai.

Production deployments mein Config Server ko generally Replica Set ke form mein deploy kiya jata hai.

### Interview Answer

> "Config Server sharded cluster ka metadata aur configuration information maintain karta hai."

---

# 21. Mongos

## English

`mongos` is a query router between the application and the shards.

## Hindi / Hinglish

`mongos` application aur shards ke beech query router ki tarah kaam karta hai.

### Architecture

```text
Application
     |
     ↓
   Mongos
     |
     ↓
-------------------------
|          |            |
Shard 1    Shard 2      Shard 3
```

`mongos` request ko appropriate shard ya shards tak route karta hai.

### Interview Answer

> "Mongos application aur shards ke beech query router ki tarah kaam karta hai. Ye request ko appropriate shard ya shards tak route karta hai."

---

# 22. What is a Shard Key?

## English

A Shard Key is a field or set of fields used to distribute documents across shards.

## Hindi / Hinglish

Shard Key wo field ya fields hain jinke basis par MongoDB data ko different shards mein distribute karta hai.

### Example

```javascript
{
  userId: 12345,
  name: "Arbaj"
}
```

Agar `userId` shard key hai:

```text
Shard Key = userId
```

MongoDB shard key ke basis par data distribute karega.

---

# 23. Why is Shard Key Important?

Shard key selection bahut important hai.

Agar shard key properly select nahi ki gayi, to:

```text
Uneven Data Distribution
Hotspots
Poor Query Performance
```

ho sakte hain.

A good shard key generally:

- High cardinality wala ho
- Data ko achhe se distribute kare
- Application ke query patterns ke liye suitable ho
- Workload ko balanced rakhe

---

# 24. Targeted Query

## English

A targeted query can be routed to the relevant shard or shards using the shard key.

## Hindi / Hinglish

Agar query shard key ka use karti hai, to `mongos` request ko relevant shard ya shards tak route kar sakta hai.

```text
Application
     ↓
   Mongos
     ↓
Relevant Shard
```

Isse unnecessary shards ko query karne ki need kam ho sakti hai.

---

# 25. Scatter-Gather

## English

If a query cannot be targeted using the shard key, it may need to contact multiple shards and combine the results.

## Hindi / Hinglish

Agar query shard key ka use nahi kar paati, to request multiple shards tak ja sakti hai.

```text
             Mongos
           /    |    \
          ↓     ↓     ↓
       Shard1 Shard2 Shard3
```

Is approach ko **Scatter-Gather** kaha jata hai.

### Interview Answer

> "Jab query ko specific shard par target nahi kiya ja sakta aur multiple shards ko query karna padta hai, to ise scatter-gather kaha jata hai."

---

# 26. Vertical Scaling

## English

Vertical scaling means increasing the resources of one server.

## Hindi / Hinglish

Vertical scaling mein ek hi server ki resources increase ki jati hain.

Example:

```text
Server
  |
  ├── More CPU
  ├── More RAM
  └── More Storage
```

Isko **Scale Up** kehte hain.

---

# 27. Horizontal Scaling

## English

Horizontal scaling means adding more servers and distributing the workload.

## Hindi / Hinglish

Horizontal scaling mein multiple servers add karke workload/data distribute kiya jata hai.

Example:

```text
Server 1
Server 2
Server 3
Server 4
```

Isko **Scale Out** kehte hain.

MongoDB Sharding horizontal scaling ka example hai.

---

# 28. Vertical vs Horizontal Scaling

| Vertical Scaling | Horizontal Scaling |
|---|---|
| One server | Multiple servers |
| Scale Up | Scale Out |
| More CPU/RAM | Add more machines |
| Single machine capacity | Distributed capacity |
| Sharding nahi | Sharding can be used |

### Easy Way

```text
Vertical
↓
One Server + More Resources

Horizontal
↓
More Servers
```

---

# 29. When Should We Use Sharding?

## English

Sharding should be considered when the dataset, traffic, storage, or workload becomes too large for a single server.

## Hindi / Hinglish

Sharding tab consider karna chahiye jab:

- Dataset bahut large ho
- Traffic bahut high ho
- Storage requirement bahut high ho
- Read/write workload bahut high ho
- Single server bottleneck ban raha ho
- Horizontal scaling ki zarurat ho

---

# 30. When NOT to Use Sharding?

Har application ko sharding ki zarurat nahi hoti.

Agar:

```text
Good Database Design
        +
Proper Indexes
        +
Optimized Queries
        +
Single Server Capacity
```

application ki requirements fulfill kar rahi hain, to unnecessary sharding avoid karna better hai.

Sharding additional operational complexity introduce karta hai.

---

# 31. Complete Sharding Architecture

```text
                         Application
                              |
                              ↓
                           Mongos
                              |
             --------------------------------
             |              |               |
             ↓              ↓               ↓
          Shard 1        Shard 2         Shard 3
             |              |               |
             --------------------------------
                              |
                              ↓
                       Config Server
                       Replica Set
```

### Simple Explanation

```text
Mongos
  ↓
Routes Queries

Shard
  ↓
Stores Data

Config Server
  ↓
Stores Cluster Metadata

Shard Key
  ↓
Controls Data Distribution
```

---

# 32. ⭐ Complete Interview Answer — Indexes

If interviewer asks:

## "Explain MongoDB Indexes."

Say:

> "MongoDB mein indexes query performance improve karne ke liye use hote hain. Single field index ek field ke liye, compound index multiple fields ke liye aur multikey index array fields ke liye use hota hai."

> "Text index text search ke liye, TTL index time-based data ko automatically expire karne ke liye, sparse index existing field wale documents ke liye aur partial index specific condition wale documents ke liye use hota hai."

> "Hashed index field value ka hash create karta hai aur hash-based distribution ya sharding mein useful ho sakta hai."

---

# 33. ⭐ Complete Interview Answer — Sharding

If interviewer asks:

## "What is MongoDB Sharding?"

Say:

> "MongoDB sharding ek horizontal scaling technique hai jisme large data ko multiple servers ya shards mein distribute kiya jata hai."

> "Iska main purpose large datasets, high traffic, high storage requirements aur high workload ko handle karna hai jab single server sufficient nahi hota."

> "Sharded cluster ke main components Shard, Config Server aur Mongos hain."

> "Shard application data ka portion store karta hai, Config Server cluster ka metadata maintain karta hai aur Mongos application aur shards ke beech query router ki tarah kaam karta hai."

> "Shard Key ke basis par MongoDB data ko shards mein distribute karta hai."

> "Agar query shard key ke through target nahi hoti, to multiple shards ko query karna pad sakta hai, jise scatter-gather kaha jata hai."

> "Sharding tab consider karunga jab dataset, traffic, storage ya workload single server ki capacity se beyond ho jaye aur horizontal scaling ki requirement ho."

---

# 34. 🎯 Important Interview Questions

## Q1. What is an Index?

### English

An index is a data structure that helps MongoDB find documents efficiently.

### Hindi

Index MongoDB ko documents efficiently find karne mein help karta hai.

---

## Q2. Why do we use indexes?

### English

Indexes can reduce the amount of data MongoDB needs to examine for supported queries and can improve query performance.

### Hindi

Indexes ki help se MongoDB ko matching data find karne ke liye fewer documents examine karne pad sakte hain, jis se supported queries faster ho sakti hain.

---

## Q3. What is COLLSCAN?

> "COLLSCAN means MongoDB is scanning the collection to find matching documents."

Hindi:

> "COLLSCAN ka matlab MongoDB collection ko scan karke matching documents find kar raha hai."

---

## Q4. What is IXSCAN?

> "IXSCAN means MongoDB is using an index to find matching documents."

Hindi:

> "IXSCAN ka matlab MongoDB index ke through matching documents find kar raha hai."

---

## Q5. What is Single Field Index?

> "Single field index ek field par create hota hai."

---

## Q6. What is Compound Index?

> "Compound index multiple fields par create hota hai aur isme field order important hota hai."

---

## Q7. What is Multikey Index?

> "Multikey index array fields ke liye use hota hai."

---

## Q8. What is Text Index?

> "Text index text aur keyword search ke liye use hota hai."

---

## Q9. What is TTL Index?

> "TTL index documents ko specified time ke baad automatically expire karne ke liye use hota hai."

---

## Q10. Sparse vs Partial Index?

> "Sparse index field ke existence ke basis par index karta hai, jabki partial index specific condition ke basis par documents ko index karta hai."

Easy:

```text
Sparse  → Field Exists

Partial → Condition Matches
```

---

## Q11. What is Hashed Index?

> "Hashed index indexed value ka hash create karta hai. Ye hash-based distribution aur sharding mein useful ho sakta hai."

---

## Q12. What is Sharding?

> "Sharding MongoDB ki horizontal scaling technique hai jisme data ko multiple shards/servers mein distribute kiya jata hai."

---

## Q13. What is a Shard?

> "Shard sharded cluster mein application data ka ek portion store karta hai."

---

## Q14. What is Config Server?

> "Config Server sharded cluster ka metadata aur configuration information maintain karta hai."

---

## Q15. What is Mongos?

> "Mongos application aur shards ke beech query router ki tarah kaam karta hai."

---

## Q16. What is a Shard Key?

> "Shard Key wo field ya fields hain jinke basis par documents ko shards mein distribute kiya jata hai."

---

## Q17. What is Targeted Query?

> "Targeted query mein shard key ki help se request ko relevant shard ya shards tak route kiya ja sakta hai."

---

## Q18. What is Scatter-Gather?

> "Jab query ko specific shard par target nahi kiya ja sakta aur multiple shards ko query karna padta hai, to ise scatter-gather kaha jata hai."

---

## Q19. Vertical vs Horizontal Scaling?

### Vertical

```text
One Server
    ↓
More CPU
More RAM
More Storage
```

### Horizontal

```text
Server 1
Server 2
Server 3
    ↓
Workload Distributed
```

---

## Q20. When should you use Sharding?

> "Jab dataset, traffic, storage ya workload single server ki capacity se beyond ho jaye aur horizontal scaling ki zarurat ho, tab sharding consider karunga."

---

# 35. 🧠 One-Line Revision

```text
Single Field → One Field

Compound → Multiple Fields

Multikey → Array

Text → Text Search

TTL → Expire Data

Sparse → Field Exists

Partial → Condition Based

Hashed → Hash Based
```

---

# 36. 🧠 Sharding One-Line Revision

```text
Shard
↓
Stores Data

Config Server
↓
Stores Metadata

Mongos
↓
Query Router

Shard Key
↓
Data Distribution

Targeted Query
↓
Relevant Shard(s)

Scatter-Gather
↓
Multiple Shards
```

---

# 37. 🚀 Complete MongoDB Revision Flow

```text
                         MongoDB
                            |
              -----------------------------
              |                           |
            Indexes                    Sharding
              |                           |
      -------------------          ------------------
      |  |  |  |  |  |  |          |       |        |
      ↓  ↓  ↓  ↓  ↓  ↓  ↓          ↓       ↓        ↓
   Single Compound Multikey       Shard   Config   Mongos
   Text   TTL   Sparse            Server  Server
   Partial Hashed
                                      |
                                      ↓
                                  Shard Key
                                      |
                           ----------------------
                           |                    |
                           ↓                    ↓
                     Targeted Query      Scatter-Gather
                           |                    |
                           ↓                    ↓
                     Relevant Shard(s)   Multiple Shards
```

---

# 38. 🎯 Interview Strategy

Interview mein sirf definition yaad mat karo.

Har concept ke liye ye 4 cheezein yaad rakho:

```text
WHAT
 ↓
WHY
 ↓
EXAMPLE
 ↓
WHEN
```

### Example — Compound Index

#### What?

Multiple fields ka index.

#### Why?

Multiple-field queries ko optimize karne ke liye.

#### Example

```javascript
db.users.createIndex({
  status: 1,
  createdAt: -1
});
```

#### When?

Jab application frequently `status` par filter aur `createdAt` par sort karti ho.

---

# 39. ⭐ Most Important Things to Remember

```text
Index
→ Query Performance

COLLSCAN
→ Collection Scan

IXSCAN
→ Index Scan

Single Field
→ One Field

Compound
→ Multiple Fields

Multikey
→ Array

Text
→ Text Search

TTL
→ Expire Data

Sparse
→ Field Exists

Partial
→ Condition

Hashed
→ Hash Based

Sharding
→ Horizontal Scaling

Shard
→ Data Store

Config Server
→ Metadata

Mongos
→ Query Router

Shard Key
→ Data Distribution

Targeted Query
→ Relevant Shard(s)

Scatter-Gather
→ Multiple Shards
```

---

# 40. ⭐ Final Short Interview Answer

> "MongoDB mein indexes query performance improve karne ke liye use hote hain. Single field index ek field ke liye, compound multiple fields ke liye, multikey arrays ke liye aur text index text search ke liye use hota hai. TTL index time-based data ko expire karta hai, sparse index existing fields wale documents ko index karta hai, partial index specific conditions wale documents ko aur hashed index hash-based distribution ke liye useful ho sakta hai."

> "MongoDB sharding horizontal scaling technique hai jisme large data ko multiple shards mein distribute kiya jata hai. Shard data store karta hai, Config Server metadata maintain karta hai aur Mongos query router ka kaam karta hai. Shard Key ke basis par data distribute hota hai."

> "Agar query shard key ke basis par target nahi hoti, to multiple shards ko query karna pad sakta hai, jise scatter-gather kehte hain."

> "Main sharding tab consider karunga jab dataset, traffic, storage ya workload single server ki capacity se beyond ho jaye aur horizontal scaling ki zarurat ho."

---

# ✅ Final Revision Formula

```text
INDEXES
   ↓
WHAT + WHY + EXAMPLE + WHEN

SHARDING
   ↓
SHARD + CONFIG SERVER + MONGOS
   ↓
SHARD KEY
   ↓
DATA DISTRIBUTION
   ↓
TARGETED QUERY / SCATTER-GATHER
   ↓
HORIZONTAL SCALING
```