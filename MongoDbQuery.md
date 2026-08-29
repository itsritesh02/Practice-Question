

## Problem

A MongoDB query that normally takes **100 ms** is now taking **12 seconds** in production.

### Task

Explain, step by step, how you would investigate and optimize the query.

Your answer should include:

- `explain()`
- Indexes
- Query performance analysis
- Production-level investigation

---

# Solution

## 1. Identify the Slow Query

First, I would identify the exact query that is taking 12 seconds.

Example:

```javascript
db.users.find({
  email: "test@gmail.com"
});
```

I would check whether the query is consistently slow or only slow for specific requests.

---

## 2. Use `explain()`

I would use `explain("executionStats")` to understand how MongoDB is executing the query.

```javascript
db.users.find({
  email: "test@gmail.com"
}).explain("executionStats");
```

I would mainly check these values:

- `executionTimeMillis`
- `totalDocsExamined`
- `totalKeysExamined`
- `nReturned`

---

## 3. Check Whether an Index Is Being Used

I would check the query execution plan.

If I see:

```text
COLLSCAN
```

MongoDB is scanning the collection instead of efficiently using an index.

For example, I could create an index:

```javascript
db.users.createIndex({
  email: 1
});
```

Then I would run `explain()` again.

Ideally, the query plan should show:

```text
IXSCAN
```

which indicates that an index is being used.

---

## 4. Check `totalDocsExamined`

For example:

```text
nReturned: 10
totalDocsExamined: 1000000
```

This means MongoDB returned only 10 documents but examined 1 million documents.

This indicates that the query or index needs optimization.

---

## 5. Check Existing Indexes

I would check all existing indexes:

```javascript
db.users.getIndexes();
```

I would verify:

- Whether the required index exists
- Whether indexes are being used
- Whether there are duplicate or unnecessary indexes

For queries using multiple fields, I may create a compound index:

```javascript
db.users.createIndex({
  status: 1,
  email: 1
});
```

---

## 6. Return Only Required Fields

If the application does not need the complete document, I would use projection.

```javascript
db.users.find(
  { email: "test@gmail.com" },
  { name: 1, email: 1 }
);
```

This reduces unnecessary data transfer.

---

## 7. Check Sorting and Pagination

If the query uses sorting:

```javascript
.sort({ createdAt: -1 })
```

I would make sure the appropriate index supports the filtering and sorting.

For large datasets, I would also avoid very large `skip()` values and consider cursor-based or range-based pagination.

---

## 8. Check the Production Environment

Because the query was previously taking **100 ms** and is now taking **12 seconds**, I would also check the production environment.

I would investigate:

- Database CPU usage
- Memory usage
- Disk I/O
- Network latency
- Database load
- Concurrent queries
- Connection pool
- Recent code changes
- Recent schema or index changes

---

## 9. Test After Optimization

After making the changes, I would run the query with `explain()` again:

```javascript
db.users.find({
  email: "test@gmail.com"
}).explain("executionStats");
```

Then I would compare the old and new results:

- `executionTimeMillis`
- `totalDocsExamined`
- `totalKeysExamined`
- `nReturned`

The goal is to reduce query execution time and unnecessary document scanning.

---

# Final Answer

> First, I would identify the slow query and run `explain("executionStats")` to understand its execution plan. I would check `executionTimeMillis`, `totalDocsExamined`, `totalKeysExamined`, and `nReturned`. If MongoDB is performing a `COLLSCAN`, I would create an appropriate index based on the query filters and sorting requirements. I would also check existing indexes, projection, pagination, and production resources such as CPU, memory, disk I/O, network latency, and database load. Finally, I would run `explain()` again and compare the results before and after optimization.

---

## Key Takeaways

| Area | What to Check |
|---|---|
| Query Plan | `COLLSCAN` vs `IXSCAN` |
| Execution Time | `executionTimeMillis` |
| Documents | `totalDocsExamined` |
| Index Keys | `totalKeysExamined` |
| Result | `nReturned` |
| Indexes | `getIndexes()` |
| Optimization | Appropriate single/compound indexes |
| Production | CPU, memory, disk, network, load |