# Section 4 — AI-Assisted Engineering

---

# Q9. Validating an AI-Generated MongoDB Query

## Question

AI generates a MongoDB aggregation query that works correctly in
development but causes severe performance issues in production.

How would you determine:

- Whether the query is correct
- Whether the performance issue is caused by the query
- Whether the indexes are appropriate
- Whether AI's solution should be accepted or rejected

---

# Answer

I would not blindly accept the AI-generated query.

First, I would verify whether the query gives the correct result.
Then I would check its performance using real production-like data.

I would follow these steps:

1. Verify query correctness
2. Use explain()
3. Check executionStats
4. Analyze aggregation stages
5. Check indexes
6. Compare development and production data
7. Test an optimized version
8. Accept or reject the AI solution based on results

---

## Step 1 — Check Query Correctness

First, I will check whether the aggregation query returns
the expected result.

### Example

Suppose AI generates:

    db.orders.aggregate([
        {
            $match: {
                status: "completed"
            }
        },
        {
            $group: {
                _id: "$userId",
                totalAmount: {
                    $sum: "$amount"
                }
            }
        }
    ]);

This query calculates the total completed order amount
for each user.

I will compare the result with the expected business logic.

If the result is correct, I will move to performance testing.

---

# Step 2 — Use explain()

I will use:

    db.orders.explain("executionStats").aggregate([
        {
            $match: {
                status: "completed"
            }
        },
        {
            $group: {
                _id: "$userId",
                totalAmount: {
                    $sum: "$amount"
                }
            }
        }
    ]);

### Explanation

`explain()` helps me understand how MongoDB executes
the aggregation.

I will check:

- executionTimeMillis
- totalDocsExamined
- totalKeysExamined
- winningPlan
- COLLSCAN
- IXSCAN

---

# Step 3 — Check Performance

Suppose I get:

    executionTimeMillis = 15000
    totalDocsExamined = 5000000
    nReturned = 100

This means MongoDB examined 50 lakh documents to return
only 100 results.

This indicates that the query may be inefficient.

---

# Step 4 — Analyze Aggregation Stages

I will check every stage of the pipeline.

Example:

    $match
    $lookup
    $unwind
    $sort
    $group
    $project

I will identify expensive stages.

For example:

    $lookup
    $sort
    $group

can become expensive when processing millions of documents.

I will try to filter data as early as possible.

---

# Step 5 — Check Indexes

I will check existing indexes:

    db.orders.getIndexes();

Suppose the query uses:

    status

but there is no suitable index.

I may create:

    db.orders.createIndex({
        status: 1
    });

Then I will run `explain()` again.

---

# Step 6 — Compare Development and Production

A query may work well in development because development
contains only:

    10,000 documents

But production may contain:

    50,00,000 documents

Therefore, I will test the query using production-like
data volume.

I will also check:

- Data distribution
- Indexes
- Server resources
- CPU
- RAM
- Disk I/O
- Concurrent requests

---

# Step 7 — Test Optimized Query

After optimization, I will compare the old and new query.

Example:

    Before:
    executionTimeMillis = 15000

    After:
    executionTimeMillis = 300

If the result is still correct and performance is
significantly better, the optimized query is a better solution.

---

# Step 8 — Accept or Reject AI Solution

I will accept the AI-generated solution only if:

- The result is correct
- Business requirements are satisfied
- Query performance is acceptable
- Proper indexes are used
- It works with production-scale data
- It does not create unnecessary database load

I will reject or modify it if:

- Results are incorrect
- Query is too slow
- It scans unnecessary documents
- It uses inefficient aggregation stages
- It ignores appropriate indexes
- It does not perform well with production data

---

# Q9 — Final Machine Round Answer

I would not blindly trust the AI-generated aggregation query.

First, I would verify its correctness using expected results and
business requirements.

Then I would use `explain("executionStats")` to check execution time,
documents examined and index usage.

I would analyze the aggregation stages and check whether suitable
indexes exist.

I would also compare development and production data sizes because
a query that works well on small data may become slow on millions
of documents.

Finally, I would optimize and performance-test the query.
I would accept the AI solution only if it is correct, efficient,
and performs well with production-scale data.
