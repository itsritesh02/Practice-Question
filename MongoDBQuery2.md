# Q5 — MongoDB Query Optimization

## Question

A MongoDB query that normally takes **100 ms** is now taking **12 seconds** in production.

Explain, step by step, how you would investigate and optimize it.

### Your answer should include:

- `explain()`
- `executionStats`
- Index analysis
- Aggregation optimization
- Projection
- Pagination
- Logging
- Performance testing

---

# Answer

## 1. Identify the Slow Query

First, I would identify the exact MongoDB query that is taking 12 seconds.

Example:

```javascript
db.users.find({
  email: "test@gmail.com"
});
```

I would check whether the query is consistently slow or only slow for specific requests or data.

---

## 2. Use `explain()`

I would run:

```javascript
db.users.find({
  email: "test@gmail.com"
}).explain("executionStats");
```

`explain()` helps me understand how MongoDB is executing the query.

I would mainly check:

- `executionTimeMillis`
- `totalDocsExamined`
- `totalKeysExamined`
- `nReturned`

---

## 3. Analyze `executionStats`

I would compare the number of documents MongoDB examines with the number of documents it actually returns.

For example:

```text
nReturned: 10
totalDocsExamined: 1000000
```

This means MongoDB returned only 10 documents but examined 1 million documents.

That indicates the query may not be using an efficient index.

---

## 4. Index Analysis

I would check the query execution plan.

If the plan contains:

```text
COLLSCAN
```

MongoDB is scanning the collection.

If it contains:

```text
IXSCAN
```

MongoDB is using an index.

I would check the existing indexes:

```javascript
db.users.getIndexes();
```

If an appropriate index is missing, I would create one based on the query:

```javascript
db.users.createIndex({
  email: 1
});
```

For multiple query fields, I may use a compound index:

```javascript
db.users.createIndex({
  status: 1,
  email: 1
});
```

After creating or changing an index, I would run `explain()` again.

---

## 5. Aggregation Optimization

If the slow operation uses an aggregation pipeline, I would check every stage.

Example:

```javascript
db.orders.aggregate([
  { $match: { status: "completed" } },
  { $sort: { createdAt: -1 } },
  { $project: { customer: 1, total: 1 } }
]);
```

I would put filtering stages such as `$match` as early as possible so that fewer documents move through the remaining pipeline.

I would also make sure that fields used for filtering and sorting have appropriate indexes where applicable.

I would avoid unnecessary `$lookup`, `$unwind`, `$sort`, or other expensive operations when they are not required.

---

## 6. Projection

I would return only the fields that the application actually needs.

Example:

```javascript
db.users.find(
  { email: "test@gmail.com" },
  { name: 1, email: 1 }
);
```

This can reduce unnecessary data transfer.

---

## 7. Pagination

If the query returns a large number of documents, I would check pagination.

For small datasets:

```javascript
db.users.find({})
  .skip(20)
  .limit(10);
```

For very large datasets, large `skip()` values can become inefficient.

In that case, I would consider cursor-based or range-based pagination using an indexed field such as `_id` or `createdAt`.

---

## 8. Logging

I would check MongoDB and application logs to understand when and why the query became slow.

I would look for:

- Slow query logs
- Query frequency
- Query duration
- Errors
- Timeouts
- Increased traffic
- Recent code changes
- Recent database or index changes

I would also compare the current production behavior with the time when the query was taking only 100 ms.

---

## 9. Check Production Resources

Because the problem is happening in production, I would also check the database and application environment.

I would investigate:

- CPU usage
- Memory usage
- Disk I/O
- Network latency
- Database load
- Concurrent queries
- Connection pool
- Collection size
- Recent deployments

The query itself may be correct, but production resource contention can also cause a large increase in response time.

---

## 10. Performance Testing

Before applying a change directly to production, I would test the optimized query with realistic data.

I would compare:

```text
Before:
100 ms → 12 seconds

After:
New execution time
```

I would run:

```javascript
db.users.find({
  email: "test@gmail.com"
}).explain("executionStats");
```

Then I would compare:

- `executionTimeMillis`
- `totalDocsExamined`
- `totalKeysExamined`
- `nReturned`

If the results are better, I would monitor the query after deployment.

---

# Interview Explanation

If the interviewer asks you to explain it verbally, you can say:

> "Agar MongoDB ki koi query normally 100 milliseconds le rahi hai aur production mein 12 seconds le rahi hai, to sabse pehle main exact slow query identify karunga."

> "Uske baad main `explain("executionStats")` use karunga, jisse mujhe pata chalega ki MongoDB query ko kaise execute kar raha hai."

> "Main mainly `executionTimeMillis`, `totalDocsExamined`, `totalKeysExamined` aur `nReturned` check karunga."

> "Agar execution plan mein `COLLSCAN` aa raha hai, iska matlab MongoDB collection scan kar raha hai. Main query ke according appropriate index create karunga aur query ko dobara `explain()` karke check karunga."

> "Agar aggregation pipeline hai, to main `$match` ko early stage par rakhunga, unnecessary `$lookup`, `$unwind` aur `$sort` ko avoid ya optimize karunga."

> "Main projection se sirf required fields return karunga aur large datasets ke liye pagination ko bhi optimize karunga."

> "Saath hi main logs, CPU, memory, disk I/O, network latency, concurrent queries aur connection pool check karunga."

> "Finally, optimization ke baad `explain("executionStats")` dobara run karke before aur after performance compare karunga aur production mein monitor karunga."

---

# COLLSCAN vs IXSCAN

## COLLSCAN

`COLLSCAN` ka matlab hai MongoDB collection ke documents ko scan karke required data find kar raha hai.

Large collection mein unnecessary collection scan query ko slow bana sakta hai.

## IXSCAN

`IXSCAN` ka matlab hai MongoDB index ke through matching data find kar raha hai.

A suitable index query ko significantly faster bana sakta hai.

---

# Easy Flow to Remember

**Slow Query → `explain()` → `executionStats` → Index Analysis → Aggregation Optimization → Projection → Pagination → Logging → Production Resources → Performance Testing → Re-test**

---

# Short Interview Answer

> "Main pehle slow query identify karunga aur `explain("executionStats")` se execution plan check karunga. `executionTimeMillis`, `totalDocsExamined`, `totalKeysExamined` aur `nReturned` analyze karunga. Agar `COLLSCAN` ho raha hai to appropriate index create karunga. Aggregation mein `$match` ko early stage par rakhunga aur unnecessary expensive stages optimize karunga. Projection aur pagination se unnecessary data processing kam karunga. Saath hi logs aur production resources check karunga. Finally `explain()` dobara run karke performance compare karunga."