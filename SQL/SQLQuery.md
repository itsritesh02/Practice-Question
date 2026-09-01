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

# Q1. Duplicate Records Find Aur Remove Karo

## Question

Write a SQL query to find duplicate email addresses and remove duplicate records while keeping the oldest record.

### Part 1 — Find Duplicate Emails

```sql
SELECT email, COUNT(*) AS count
FROM Employees
GROUP BY email
HAVING COUNT(*) > 1;
```

### Explanation — Hindi

Pehle hum `email` ko `GROUP BY` karenge.

`COUNT(*)` se pata chalega ki har email kitni baar aa rahi hai.

```sql
HAVING COUNT(*) > 1
```

sirf duplicate emails ko return karega.

### Interview Answer

> "Main email ko GROUP BY karunga, COUNT se frequency calculate karunga aur HAVING COUNT(*) > 1 se duplicate emails identify karunga."

---

## Part 2 — Duplicate Remove Karna

Assume:

```text
Smaller ID = Older Record
```

Query:

```sql
DELETE e1
FROM Employees e1
JOIN Employees e2
    ON e1.email = e2.email
    AND e1.id > e2.id;
```

### Explanation

Same email wale records ko compare karenge.

```sql
e1.email = e2.email
```

same email check karta hai.

```sql
e1.id > e2.id
```

bade ID wale record ko identify karta hai.

Isliye:

```text
Small ID → Oldest → KEEP
Large ID → Duplicate → DELETE
```

### Production mein

DELETE se pehle SELECT karke verify karna chahiye:

```sql
SELECT e1.*
FROM Employees e1
JOIN Employees e2
    ON e1.email = e2.email
    AND e1.id > e2.id;
```

---

# Q2. Second Highest Salary Nikalo

## Question

Write a SQL query to find the second highest salary from the `Employees` table.

### Method 1 — DISTINCT + LIMIT

```sql
SELECT DISTINCT salary
FROM Employees
ORDER BY salary DESC
LIMIT 1 OFFSET 1;
```

### Explanation

```text
ORDER BY salary DESC
```

salary ko highest se lowest order mein sort karega.

```text
LIMIT 1 OFFSET 1
```

first highest salary ko skip karke second salary return karega.

`DISTINCT` duplicate salaries ko remove karta hai.

---

## Method 2 — Subquery

```sql
SELECT MAX(salary) AS second_highest
FROM Employees
WHERE salary < (
    SELECT MAX(salary)
    FROM Employees
);
```

### Interview Answer

> "Main pehle maximum salary find karunga aur usse chhoti salaries mein se maximum salary select karunga. Isse second highest distinct salary mil jayegi."

---

# Q3. Nth Highest Salary Nikalo

## Question

Write a SQL query to find the Nth highest salary.

Example:

```text
N = 3
```

means third highest salary.

### MySQL — LIMIT / OFFSET

```sql
SELECT DISTINCT salary
FROM Employees
ORDER BY salary DESC
LIMIT 1 OFFSET 2;
```

General formula:

```text
OFFSET = N - 1
```

For example:

```text
2nd highest → OFFSET 1
3rd highest → OFFSET 2
4th highest → OFFSET 3
Nth highest → OFFSET N-1
```

---

## Using DENSE_RANK()

```sql
SELECT salary
FROM (
    SELECT
        salary,
        DENSE_RANK() OVER (ORDER BY salary DESC) AS rank_no
    FROM Employees
) AS temp
WHERE rank_no = 3;
```

Yahan:

```sql
DENSE_RANK()
```

salary ko ranking deta hai.

Example:

```text
Salary | Rank
-------|-----
90000  | 1
80000  | 2
70000  | 3
70000  | 3
60000  | 4
```

### Interview Answer

> "Nth highest salary ke liye main ORDER BY aur LIMIT/OFFSET use kar sakta hoon. Agar ranking based solution chahiye to DENSE_RANK() use karunga."

---

# Q4. Top 5 Highest Salary Nikalo

## Question

Write a SQL query to find the top 5 highest salaries.

```sql
SELECT DISTINCT salary
FROM Employees
ORDER BY salary DESC
LIMIT 5;
```

### Explanation

```sql
ORDER BY salary DESC
```

highest salary ko top par rakhega.

```sql
LIMIT 5
```

top 5 salaries return karega.

### Agar complete employee records chahiye:

```sql
SELECT *
FROM Employees
ORDER BY salary DESC
LIMIT 5;
```

### Interview Answer

> "Main salary ko descending order mein sort karke LIMIT 5 use karunga."

---

# Q5. Employees Ki Total Count Nikalo

## Question

Write a SQL query to find the total number of employees.

```sql
SELECT COUNT(*) AS total_employees
FROM Employees;
```

### Explanation

`COUNT(*)` table ke total rows count karta hai.

Example output:

```text
total_employees
---------------
6
```

### Interview Answer

> "Total employees count karne ke liye main COUNT(*) use karunga."

---

# Q6. Department-Wise Employee Count Nikalo

## Question

Write a SQL query to find the number of employees in each department.

```sql
SELECT
    department,
    COUNT(*) AS employee_count
FROM Employees
GROUP BY department;
```

### Example Output

```text
department | employee_count
-----------|---------------
IT         | 3
HR         | 2
Sales      | 1
```

### Explanation

```sql
GROUP BY department
```

same department ke employees ko ek group mein rakhta hai.

```sql
COUNT(*)
```

har department ke employees count karta hai.

### Interview Answer

> "Department-wise count ke liye department ko GROUP BY karunga aur COUNT(*) se har department ke employees count karunga."

---

# Q7. Department-Wise Highest Salary Nikalo

## Question

Write a SQL query to find the highest salary in each department.

```sql
SELECT
    department,
    MAX(salary) AS highest_salary
FROM Employees
GROUP BY department;
```

### Example Output

```text
department | highest_salary
-----------|---------------
IT         | 60000
HR         | 90000
Sales      | 80000
```

### Explanation

`GROUP BY department` har department ka group banata hai.

`MAX(salary)` har group ki highest salary return karta hai.

### Interview Answer

> "Department-wise highest salary ke liye main department ko GROUP BY karunga aur MAX(salary) use karunga."

---

## Agar Complete Employee Record Chahiye

Sirf salary nahi, balki employee ka naam bhi chahiye:

```sql
SELECT e.*
FROM Employees e
JOIN (
    SELECT
        department,
        MAX(salary) AS max_salary
    FROM Employees
    GROUP BY department
) d
ON e.department = d.department
AND e.salary = d.max_salary;
```

Ye har department ke highest-paid employee ko return karega.

---

# Q8. Average Salary Se Zyada Salary Wale Employees Nikalo

## Question

Write a SQL query to find employees whose salary is greater than the average salary.

```sql
SELECT *
FROM Employees
WHERE salary > (
    SELECT AVG(salary)
    FROM Employees
);
```

### Explanation

Inner query:

```sql
SELECT AVG(salary)
FROM Employees;
```

average salary calculate karegi.

Outer query:

```sql
WHERE salary > (...)
```

average se zyada salary wale employees return karegi.

### Flow

```text
Employees
    ↓
AVG(salary)
    ↓
Average Salary
    ↓
Compare Each Employee
    ↓
salary > average
    ↓
Result
```

### Interview Answer

> "Main subquery ke through pehle average salary calculate karunga. Uske baad outer query mein un employees ko filter karunga jinki salary average se greater hai."

---

# Q9. NULL Values Handle Karo

## Question

Write SQL queries to find and handle NULL values in the `email` column.

---

## Find NULL Emails

```sql
SELECT *
FROM Employees
WHERE email IS NULL;
```

### Important

NULL ke saath:

```sql
email = NULL
```

use nahi karna chahiye.

Correct:

```sql
email IS NULL
```

---

## Find Non-NULL Emails

```sql
SELECT *
FROM Employees
WHERE email IS NOT NULL;
```

---

## Replace NULL With Default Value

```sql
SELECT
    name,
    COALESCE(email, 'No Email') AS email
FROM Employees;
```

`COALESCE()` first non-NULL value return karta hai.

Example:

```text
email = NULL
```

output:

```text
No Email
```

---

## Update NULL Values

Agar NULL emails ko default value se update karna ho:

```sql
UPDATE Employees
SET email = 'not-provided@example.com'
WHERE email IS NULL;
```

### Interview Answer

> "NULL check karne ke liye main IS NULL ya IS NOT NULL use karunga. NULL ko display level par handle karna ho to COALESCE() use kar sakta hoon."

---

# Q10. Duplicate Emails Ko Future Mein Prevent Kaise Karoge?

## Question

Duplicate emails ko database mein future mein insert hone se kaise prevent karoge?

### Solution

`UNIQUE` constraint use karenge.

```sql
ALTER TABLE Employees
ADD CONSTRAINT unique_email UNIQUE (email);
```

Ab same email dobara insert nahi ho sakti.

Example:

```sql
INSERT INTO Employees
(id, name, email, salary, department)
VALUES
(7, 'Rohit', 'amit@gmail.com', 60000, 'IT');
```

Agar:

```text
amit@gmail.com
```

already exist karta hai, database duplicate insert ko reject karega.

---

## CREATE TABLE Mein Direct UNIQUE

Agar table create karte time hi constraint lagana ho:

```sql
CREATE TABLE Employees (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    salary INT,
    department VARCHAR(100)
);
```

### Interview Answer

> "Future mein duplicate emails prevent karne ke liye main email column par UNIQUE constraint lagaunga. Isse database level par duplicate email insert nahi ho payegi."

---

# ⭐ Important: Application + Database Validation

MERN application mein frontend/backend validation useful hai, lekin database level par `UNIQUE` constraint important hai.

```text
Frontend Validation
       ↓
Backend Validation
       ↓
Database UNIQUE Constraint
```

Database constraint final protection provide karta hai.

---

# 🔥 Quick Revision

## 1. Duplicate Email

```sql
SELECT email, COUNT(*)
FROM Employees
GROUP BY email
HAVING COUNT(*) > 1;
```

---

## 2. Second Highest Salary

```sql
SELECT DISTINCT salary
FROM Employees
ORDER BY salary DESC
LIMIT 1 OFFSET 1;
```

---

## 3. Nth Highest Salary

```sql
SELECT DISTINCT salary
FROM Employees
ORDER BY salary DESC
LIMIT 1 OFFSET N-1;
```

---

## 4. Top 5 Salary

```sql
SELECT DISTINCT salary
FROM Employees
ORDER BY salary DESC
LIMIT 5;
```

---

## 5. Total Employees

```sql
SELECT COUNT(*)
FROM Employees;
```

---

## 6. Department-Wise Count

```sql
SELECT department, COUNT(*)
FROM Employees
GROUP BY department;
```

---

## 7. Department-Wise Highest Salary

```sql
SELECT department, MAX(salary)
FROM Employees
GROUP BY department;
```

---

## 8. Salary > Average

```sql
SELECT *
FROM Employees
WHERE salary > (
    SELECT AVG(salary)
    FROM Employees
);
```

---

## 9. NULL Check

```sql
SELECT *
FROM Employees
WHERE email IS NULL;
```

---

## 10. Prevent Duplicate Email

```sql
ALTER TABLE Employees
ADD CONSTRAINT unique_email UNIQUE (email);
```

---

# 🧠 Interview Formula

```text
Duplicate
→ GROUP BY + COUNT + HAVING

Second Highest
→ ORDER BY DESC + OFFSET
→ OR MAX + Subquery

Nth Highest
→ LIMIT/OFFSET
→ OR DENSE_RANK()

Top N
→ ORDER BY DESC + LIMIT

Total Count
→ COUNT(*)

Group Count
→ GROUP BY + COUNT

Highest
→ GROUP BY + MAX

Greater Than Average
→ AVG + Subquery

NULL
→ IS NULL / IS NOT NULL / COALESCE

Prevent Duplicate
→ UNIQUE Constraint
```

# 🎯 Most Important Concepts From These 10 Questions

```text
SELECT
WHERE
GROUP BY
HAVING
ORDER BY
LIMIT
OFFSET
DISTINCT
COUNT()
MAX()
AVG()
COALESCE()
Subquery
Self JOIN
DENSE_RANK()
UNIQUE
NULL
```

# ⭐ Interview Tip

Question ko dekhte hi identify karo:

```text
Duplicate?
→ GROUP BY + HAVING

Highest?
→ MAX()

Average?
→ AVG()

Count?
→ COUNT()

Department-wise?
→ GROUP BY department

Top N?
→ ORDER BY DESC + LIMIT

Nth Highest?
→ ORDER BY + OFFSET
→ DENSE_RANK()

NULL?
→ IS NULL / COALESCE()

Future Duplicate Prevention?
→ UNIQUE
```

# ✅ SQL Interview Set — Q1 to Q10 Complete

Ye 10 questions SQL ke basic + practical interview concepts cover karte hain:

1. Duplicate records find & remove
2. Second highest salary
3. Nth highest salary
4. Top 5 highest salary
5. Total employee count
6. Department-wise employee count
7. Department-wise highest salary
8. Salary greater than average
9. NULL handling
10. Duplicate email prevention