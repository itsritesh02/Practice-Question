# Question 7 — SQL Duplicate Email Addresses

## Given SQL Table

Table Name: `Users`

| Column | Description |
|---|---|
| `id` | User ID |
| `name` | User Name |
| `email` | User Email |

---

# Q7.1 — Find Duplicate Email Addresses

## Question

Write a SQL query to find duplicate email addresses.

## Solution

```sql
SELECT email, COUNT(*) AS count
FROM Users
GROUP BY email
HAVING COUNT(*) > 1;
```

---

## Explanation — Hindi + English

Sabse pehle hum `email` ko `GROUP BY` karenge.

```sql
GROUP BY email
```

Isse same email wale records ek group mein aa jayenge.

Phir:

```sql
COUNT(*)
```

har email ki total frequency count karega.

Finally:

```sql
HAVING COUNT(*) > 1
```

sirf un emails ko return karega jo ek se zyada baar present hain.

### Simple Flow

```text
Users Table
     ↓
GROUP BY email
     ↓
COUNT(*)
     ↓
HAVING COUNT(*) > 1
     ↓
Duplicate Emails
```

---

# Example

Suppose table mein data hai:

```text
id   name    email
1    A       a@gmail.com
2    B       b@gmail.com
3    C       a@gmail.com
4    D       c@gmail.com
5    E       b@gmail.com
```

Query:

```sql
SELECT email, COUNT(*) AS count
FROM Users
GROUP BY email
HAVING COUNT(*) > 1;
```

Output:

```text
email          count
---------------------
a@gmail.com     2
b@gmail.com     2
```

`c@gmail.com` duplicate nahi hai kyunki wo sirf ek baar hai.

---

# Interview Explanation

Interviewer ko bol sakte ho:

> "Main email field ko GROUP BY karunga aur COUNT(*) se har email ki frequency count karunga. HAVING COUNT(*) > 1 use karke sirf duplicate emails ko filter karunga."

---

# Q7.2 — Remove Duplicate Records

## Question

Write a query to remove duplicate records while keeping the oldest record.

### Assumption

Yahan hum assume kar rahe hain ki:

```text
Smaller ID = Older Record
```

Example:

```text
id = 1 → Oldest
id = 2 → Newer
id = 3 → Newer
```

Isliye duplicate records mein hum smallest `id` ko keep karenge.

---

# Solution — MySQL

```sql
DELETE u1
FROM Users u1
JOIN Users u2
  ON u1.email = u2.email
 AND u1.id > u2.id;
```

---

# Explanation — Hindi + English

Hum same email wale records ko aapas mein compare karenge.

Condition:

```sql
u1.email = u2.email
```

iska matlab dono records ka email same hona chahiye.

Second condition:

```sql
u1.id > u2.id
```

iska matlab `u1` ka ID bada hai aur `u2` ka ID chhota hai.

Hum bade ID wale record ko delete karenge.

Isliye:

```text
Small ID → Keep
Large ID → Delete
```

---

# Example

Before:

```text
id   name    email
1    A       a@gmail.com
2    B       a@gmail.com
3    C       b@gmail.com
4    D       b@gmail.com
5    E       c@gmail.com
```

Duplicate emails:

```text
a@gmail.com → 2 records
b@gmail.com → 2 records
```

Delete query:

```sql
DELETE u1
FROM Users u1
JOIN Users u2
  ON u1.email = u2.email
 AND u1.id > u2.id;
```

After deletion:

```text
id   name    email
1    A       a@gmail.com
3    C       b@gmail.com
5    E       c@gmail.com
```

### Why?

For `a@gmail.com`:

```text
id 1 → Keep
id 2 → Delete
```

For `b@gmail.com`:

```text
id 3 → Keep
id 4 → Delete
```

---

# ⚠️ Important Production Practice

Production database mein directly `DELETE` run nahi karna chahiye.

Pehle same logic ko `SELECT` ke through verify karna better hai.

```sql
SELECT u1.*
FROM Users u1
JOIN Users u2
  ON u1.email = u2.email
 AND u1.id > u2.id;
```

Ye batayega ki kaunse records delete hone wale hain.

Verify karne ke baad hi:

```sql
DELETE
```

run karna chahiye.

---

# Interview Explanation

Interviewer ko bol sakte ho:

> "Main same email wale records ko self JOIN ke through compare karunga. Agar u1 ka email u2 ke email ke equal hai aur u1.id > u2.id hai, to u1 newer duplicate record hoga. Main usko delete karunga aur smaller ID wale oldest record ko retain karunga."

---

# ⭐ Important Concept — Self JOIN

Yahan hum same table ko do aliases ke saath use kar rahe hain:

```sql
Users u1
Users u2
```

Ye **Self JOIN** hai.

Simple meaning:

> "Ek table ko khud ke saath join karna Self JOIN kehlata hai."

Example:

```sql
FROM Users u1
JOIN Users u2
```

---

# ⭐ Why `HAVING` Instead of `WHERE`?

Ye interview mein poocha ja sakta hai.

### WHERE

`WHERE` grouping se pehle rows ko filter karta hai.

```sql
WHERE email = 'a@gmail.com'
```

### HAVING

`HAVING` grouping ke baad aggregated result ko filter karta hai.

```sql
HAVING COUNT(*) > 1
```

Isliye duplicate find karne ke liye:

```sql
GROUP BY email
HAVING COUNT(*) > 1
```

use karte hain.

---

# ⭐ WHERE vs HAVING

| WHERE | HAVING |
|---|---|
| Rows ko filter karta hai | Groups ko filter karta hai |
| GROUP BY se pehle | GROUP BY ke baad |
| Aggregate functions ke liye generally nahi | Aggregate functions ke saath use hota hai |
| Example: `WHERE age > 18` | Example: `HAVING COUNT(*) > 1` |

---

# ⭐ Q7 Quick Revision

## Duplicate Emails

```sql
SELECT email, COUNT(*) AS count
FROM Users
GROUP BY email
HAVING COUNT(*) > 1;
```

### Remember:

```text
GROUP BY email
      ↓
COUNT(*)
      ↓
HAVING COUNT(*) > 1
      ↓
Duplicates
```

---

# Remove Duplicates

```sql
DELETE u1
FROM Users u1
JOIN Users u2
  ON u1.email = u2.email
 AND u1.id > u2.id;
```

### Remember:

```text
Same Email
    ↓
Compare IDs
    ↓
Small ID → Oldest → KEEP
    ↓
Large ID → Duplicate → DELETE
```

---

# 🎯 One-Minute Interview Answer

Agar interviewer kahe:

## "How would you find and remove duplicate emails?"

Bolna:

> "Duplicate emails find karne ke liye main email par GROUP BY karunga aur COUNT(*) use karunga. HAVING COUNT(*) > 1 se mujhe sirf duplicate emails milengi."

```sql
SELECT email, COUNT(*) AS count
FROM Users
GROUP BY email
HAVING COUNT(*) > 1;
```

> "Duplicates remove karne ke liye main table ko khud ke saath self JOIN karunga. Same email wale records mein jis record ki ID badi hai usko delete karunga, aur smallest ID wale oldest record ko keep karunga."

```sql
DELETE u1
FROM Users u1
JOIN Users u2
  ON u1.email = u2.email
 AND u1.id > u2.id;
```

> "Production mein DELETE se pehle main SELECT query se affected records verify karunga aur backup/transaction strategy follow karunga."

---

# 🧠 Final Formula

```text
DUPLICATE FIND

GROUP BY
    ↓
COUNT
    ↓
HAVING > 1


DUPLICATE DELETE

SELF JOIN
    ↓
SAME EMAIL
    ↓
COMPARE ID
    ↓
SMALL ID = OLD RECORD = KEEP
    ↓
LARGE ID = DUPLICATE = DELETE
```

# ✅ Q7 Complete

- Find duplicate emails
- `GROUP BY`
- `COUNT(*)`
- `HAVING`
- Remove duplicates
- Self JOIN
- Keep oldest record
- Delete newer duplicate
- Production safety
- Interview explanation