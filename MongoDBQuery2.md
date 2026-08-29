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

अगर MongoDB की कोई query normally **100 ms** ले रही है और production में वही query **12 seconds** ले रही है, तो मैं इसे step-by-step investigate और optimize करूँगा।

---

## 1. Slow Query को Identify करना

सबसे पहले मैं identify करूँगा कि कौन-सी exact MongoDB query 12 seconds ले रही है।

Example:

```javascript
db.users.find({
  email: "test@gmail.com"
});
```

मैं यह भी check करूँगा कि query हर बार slow है या सिर्फ किसी specific request या data के लिए slow हो रही है।

---

## 2. `explain()` का इस्तेमाल करना

इसके बाद मैं `explain("executionStats")` का इस्तेमाल करूँगा।

```javascript
db.users.find({
  email: "test@gmail.com"
}).explain("executionStats");
```

`explain()` से पता चलता है कि MongoDB query को किस तरीके से execute कर रहा है।

मैं mainly ये values check करूँगा:

- `executionTimeMillis`
- `totalDocsExamined`
- `totalKeysExamined`
- `nReturned`

---

## 3. `executionStats` को Analyze करना

मैं check करूँगा कि MongoDB कितने documents examine कर रहा है और कितने documents वास्तव में return कर रहा है।

Example:

```text
nReturned: 10
totalDocsExamined: 1000000
```

इसका मतलब है कि MongoDB ने सिर्फ **10 documents return किए**, लेकिन **10 लाख documents examine किए**।

इससे पता चल सकता है कि query efficiently optimized नहीं है या appropriate index का इस्तेमाल नहीं हो रहा है।

---

## 4. Index Analysis

मैं query का execution plan check करूँगा।

अगर execution plan में:

```text
COLLSCAN
```

आ रहा है, तो MongoDB पूरी collection को scan कर रहा है।

अगर:

```text
IXSCAN
```

आ रहा है, तो MongoDB index का इस्तेमाल कर रहा है।

### Existing Indexes Check करना

```javascript
db.users.getIndexes();
```

अगर required index मौजूद नहीं है, तो query के अनुसार appropriate index create करूँगा।

Example:

```javascript
db.users.createIndex({
  email: 1
});
```

अगर query में multiple fields हैं, तो compound index भी इस्तेमाल कर सकता हूँ।

Example:

```javascript
db.users.createIndex({
  status: 1,
  email: 1
});
```

Index create करने के बाद मैं `explain()` दोबारा run करूँगा और performance compare करूँगा।

---

## 5. Aggregation Optimization

अगर slow operation aggregation pipeline का इस्तेमाल कर रहा है, तो मैं pipeline के सभी stages को check करूँगा।

Example:

```javascript
db.orders.aggregate([
  { $match: { status: "completed" } },
  { $sort: { createdAt: -1 } },
  { $project: { customer: 1, total: 1 } }
]);
```

मैं `$match` को जितना possible हो early stage पर रखूँगा ताकि आगे के stages में कम documents process हों।

मैं unnecessary expensive operations को भी avoid या optimize करूँगा, जैसे:

- `$lookup`
- `$unwind`
- `$sort`

Filtering और sorting में इस्तेमाल होने वाले fields के लिए appropriate indexes भी check करूँगा।

---

## 6. Projection

अगर application को पूरे document की जरूरत नहीं है, तो मैं सिर्फ required fields return करूँगा।

Example:

```javascript
db.users.find(
  { email: "test@gmail.com" },
  { name: 1, email: 1 }
);
```

इससे unnecessary data return और transfer कम होगा।

---

## 7. Pagination

अगर query बहुत सारे documents return कर रही है, तो मैं pagination check करूँगा।

छोटे datasets के लिए:

```javascript
db.users.find({})
  .skip(20)
  .limit(10);
```

लेकिन बहुत बड़े datasets में बड़े `skip()` values inefficient हो सकते हैं।

ऐसे cases में मैं cursor-based या range-based pagination इस्तेमाल करूँगा।

इसके लिए `_id` या `createdAt` जैसे indexed fields का इस्तेमाल किया जा सकता है।

---

## 8. Logging

मैं MongoDB और application logs भी check करूँगा ताकि पता चल सके कि query कब और क्यों slow हुई।

मैं check करूँगा:

- Slow query logs
- Query frequency
- Query duration
- Errors
- Timeouts
- Increased traffic
- Recent code changes
- Recent database changes
- Recent index changes

मैं current production performance को उस समय की performance से भी compare करूँगा जब query सिर्फ 100 ms ले रही थी।

---

## 9. Production Resources Check करना

क्योंकि issue production में आ रहा है, इसलिए सिर्फ query ही नहीं बल्कि production environment भी check करूँगा।

मैं check करूँगा:

- CPU usage
- Memory usage
- Disk I/O
- Network latency
- Database load
- Concurrent queries
- Connection pool
- Collection size
- Recent deployments

कभी-कभी query सही होने के बावजूद production में ज्यादा load या resource contention के कारण query slow हो सकती है।

---

## 10. Performance Testing

Optimization करने के बाद मैं query को realistic data के साथ test करूँगा।

### Before

```text
100 ms → 12 seconds
```

### After

Optimization के बाद नया execution time check करूँगा।

फिर `explain()` दोबारा run करूँगा:

```javascript
db.users.find({
  email: "test@gmail.com"
}).explain("executionStats");
```

मैं compare करूँगा:

- `executionTimeMillis`
- `totalDocsExamined`
- `totalKeysExamined`
- `nReturned`

अगर performance improve होती है, तो deployment के बाद भी query को monitor करूँगा।

---

# Interview Explanation

अगर interviewer पूछता है कि आप इस problem को कैसे solve करेंगे, तो आप ऐसे explain कर सकते हैं:

> **"Agar MongoDB ki koi query normally 100 milliseconds le rahi hai aur production mein 12 seconds le rahi hai, to sabse pehle main exact slow query identify karunga."**

> **"Uske baad main `explain("executionStats")` use karunga, jisse mujhe pata chalega ki MongoDB query ko kaise execute kar raha hai."**

> **"Main mainly `executionTimeMillis`, `totalDocsExamined`, `totalKeysExamined` aur `nReturned` check karunga."**

> **"Agar execution plan mein `COLLSCAN` aa raha hai, iska matlab MongoDB collection scan kar raha hai. Main query ke according appropriate index create karunga aur query ko dobara `explain()` karke check karunga."**

> **"Agar aggregation pipeline hai, to main `$match` ko early stage par rakhunga aur unnecessary `$lookup`, `$unwind` aur `$sort` ko optimize karunga."**

> **"Main projection se sirf required fields return karunga aur large datasets ke liye pagination ko bhi optimize karunga."**

> **"Saath hi main logs, CPU, memory, disk I/O, network latency, concurrent queries aur connection pool check karunga."**

> **"Finally, optimization ke baad `explain("executionStats")` dobara run karke before aur after performance compare karunga aur production mein monitor karunga."**

---

# COLLSCAN vs IXSCAN

## COLLSCAN

`COLLSCAN` का मतलब है कि MongoDB पूरी collection के documents को scan करके required data find कर रहा है।

अगर collection बहुत बड़ी है, तो unnecessary collection scan query को slow कर सकता है।

---

## IXSCAN

`IXSCAN` का मतलब है कि MongoDB index के through matching documents को find कर रहा है।

अगर appropriate index मौजूद है, तो query काफी faster हो सकती है।

---

# Easy Flow to Remember

**Slow Query → `explain()` → `executionStats` → Index Analysis → Aggregation Optimization → Projection → Pagination → Logging → Production Resources → Performance Testing → Re-test**

---

# Short Interview Answer

> **"Main pehle slow query identify karunga aur `explain("executionStats")` se execution plan check karunga. `executionTimeMillis`, `totalDocsExamined`, `totalKeysExamined` aur `nReturned` analyze karunga. Agar `COLLSCAN` ho raha hai to appropriate index create karunga. Aggregation mein `$match` ko early stage par rakhunga aur unnecessary expensive stages optimize karunga. Projection aur pagination se unnecessary data processing kam karunga. Saath hi logs aur production resources check karunga. Finally `explain()` dobara run karke performance compare karunga."**

---

# Key Points

| Area | What to Check |
|---|---|
| Query Plan | `COLLSCAN` vs `IXSCAN` |
| Execution Time | `executionTimeMillis` |
| Documents | `totalDocsExamined` |
| Index Keys | `totalKeysExamined` |
| Result | `nReturned` |
| Indexes | `getIndexes()` |
| Aggregation | `$match`, `$lookup`, `$unwind`, `$sort` |
| Projection | Only required fields |
| Pagination | Avoid large `skip()` |
| Logging | Slow queries, errors, timeouts |
| Production | CPU, memory, disk, network, load |
| Testing | Before vs After |