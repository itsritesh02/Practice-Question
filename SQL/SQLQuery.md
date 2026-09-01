# SQL Interview Questions & Answers — MERN Developer

## Database Table Used

Hum examples ke liye `Employees` table use karenge.

```sql
CREATE TABLE Employees (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    salary INT,
    department VARCHAR(100)
);
```

Example Data:

```text
id | name  | email             | salary | department
---|-------|-------------------|--------|------------
1  | Amit  | amit@gmail.com    | 50000  | IT
2  | Rahul | rahul@gmail.com   | 70000  | HR
3  | Ravi  | ravi@gmail.com    | 60000  | IT
4  | Aman  | amit@gmail.com    | 50000  | IT
5  | Neha  | neha@gmail.com    | 90000  | HR
6  | Raj   | raj@gmail.com     | 80000  | Sales
```

---
# CodeTribe — MERN Stack Machine Round

# Section 3 — Database (SQL)

---

# Q7. Duplicate Email Addresses

## Question

Given the following SQL table:

Users

- Id
- Name
- Email

Write:

1. A query to find duplicate email addresses.
2. A query to remove duplicates while keeping the oldest record.

---

# 1. Find Duplicate Email Addresses

## Explanation

Hume find karna hai ki kaunse email database me ek se zyada
baar exist kar rahe hain.

Iske liye:

- GROUP BY → same emails ko group karega
- COUNT(*) → batayega email kitni baar aaya
- HAVING → sirf duplicate emails show karega

## Query

    SELECT email, COUNT(*) AS count
    FROM Users
    GROUP BY email
    HAVING COUNT(*) > 1;

## Example Data

    Id    Name       Email
    1     Ritesh     ritesh@gmail.com
    2     Rahul      rahul@gmail.com
    3     Aman       aman@gmail.com
    4     Ritesh2    ritesh@gmail.com
    5     Rahul2     rahul@gmail.com

Result:

    Email               Count
    ritesh@gmail.com       2
    rahul@gmail.com        2

## Simple Explanation

GROUP BY email:

    ritesh@gmail.com → 2 records
    rahul@gmail.com  → 2 records

HAVING COUNT(*) > 1:

    Sirf woh emails dikhao jo 1 se zyada baar aaye hain.

---

# 2. Remove Duplicates But Keep Oldest Record

## Explanation

Oldest record ko hum smallest `Id` maan rahe hain.

Example:

    Id    Name       Email
    1     Ritesh     ritesh@gmail.com
    4     Ritesh2    ritesh@gmail.com

Id 1 oldest hai.

Hume:

    Id 1 → Keep
    Id 4 → Delete

karna hai.

## Query

    DELETE u1
    FROM Users u1
    INNER JOIN Users u2
        ON u1.email = u2.email
        AND u1.id > u2.id;

## Explanation

Yahan:

    u1.email = u2.email

ka matlab hai same email wale records find karo.

Aur:

    u1.id > u2.id

ka matlab hai jis record ki ID badi hai,
use duplicate maan lo.

Example:

    Id 1 → Oldest → Keep
    Id 4 → Newer  → Delete

---

# Q7 — Important Note

Delete query production database par directly run nahi karni chahiye.

Pehle SELECT se verify karna better hai.

## Check Before Delete

    SELECT u1.*
    FROM Users u1
    INNER JOIN Users u2
        ON u1.email = u2.email
        AND u1.id > u2.id;

Agar result correct hai, tab DELETE query run karenge.

---

# Q7 — Final Machine Round Answer

To find duplicate emails, I will use GROUP BY with HAVING COUNT(*) > 1.

To remove duplicates while keeping the oldest record, I will compare
records with the same email and delete the record having the larger ID,
assuming the smaller ID represents the older record.

---
