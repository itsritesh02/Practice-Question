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
Q5. MongoDB Query Optimization

If a MongoDB query becomes slow from 100 ms to 12 seconds, I will follow these steps:

1. First, I will use explain("executionStats") to check how the query is being executed.

   Example:
   db.users.find({ email: "test@gmail.com" })
          .explain("executionStats");

2. I will check:
   - executionTimeMillis
   - totalDocsExamined
   - totalKeysExamined
   - nReturned
   - winningPlan

3. I will check whether the query is using COLLSCAN or IXSCAN.
   COLLSCAN means MongoDB is scanning the collection.
   IXSCAN means MongoDB is using an index.

4. I will check existing indexes:

   db.users.getIndexes();

   If required, I will create an index:

   db.users.createIndex({ email: 1 });

5. If the query uses multiple fields, I will use a compound index.

   db.orders.createIndex({
       userId: 1,
       status: 1
   });

6. If aggregation is used, I will optimize the pipeline.
   I will generally put $match early and avoid unnecessary
   $lookup, $sort, $group and $unwind stages.

7. I will use projection to return only required fields.

   db.users.find(
       { status: "active" },
       { name: 1, email: 1 }
   );

8. I will use pagination instead of returning a large number
   of documents at once.

   db.users.find({ status: "active" }).limit(20);

9. I will check MongoDB logs/profiler and server resources
   such as CPU, RAM, disk I/O and connections.

10. Finally, I will run explain("executionStats") again and
    compare the performance before and after optimization.

Conclusion:
I will identify the bottleneck using explain(), optimize indexes
and queries, reduce unnecessary data processing, and then test
the query again.