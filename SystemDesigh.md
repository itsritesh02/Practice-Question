
# Q11. Provider-to-Facility Matching System for Hospitals

## Question

Design a Provider-to-Facility Matching System for hospitals.

The system should consider:

- Provider specialty
- Previous work history
- Distance
- Availability
- Facility preferences
- Provider preferences
- Priority rules

You are not expected to write code.

Explain:

- How you would break down the problem
- What database structure you would use
- Which APIs you would create
- Which parts AI could help generate
- Which parts require human decision-making

---

# Answer

## 1. Problem Understanding

Humein ek aisa system design karna hai jo hospital/facility ke liye **best matching healthcare provider** find kare.

System ko provider aur facility dono ki requirements consider karni hongi.

Example:

```text
Hospital Requirement
        +
Provider Information
        +
Availability
        +
Distance
        +
Preferences
        +
Priority Rules
        ↓
Matching System
        ↓
Best Providers
```

System ka goal sirf nearest provider find karna nahi hai.

Humein multiple factors ko combine karke **best possible match** find karna hai.

---

# 2. Main Entities

Main system ko mainly in entities mein divide karunga:

```text
Provider
Facility
Availability
Work History
Preferences
Match
Assignment
Priority Rules
```

---

# 3. Provider

Provider healthcare professional ko represent karega.

Example:

```text
Provider
- id
- name
- specialty
- experience
- location
- status
```

Example:

```json
{
    "id": "P101",
    "name": "Provider A",
    "specialty": "Cardiology",
    "experience": 7,
    "location": {
        "lat": 30.70,
        "lng": 76.80
    },
    "status": "active"
}
```

---

# 4. Facility

Facility hospital ya healthcare center ko represent karegi.

Fields:

```text
Facility
- id
- name
- location
- requiredSpecialty
- preferences
- priority
```

Example:

```json
{
    "id": "F101",
    "name": "City Hospital",
    "requiredSpecialty": "Cardiology",
    "location": {
        "lat": 30.72,
        "lng": 76.81
    }
}
```

---

# 5. Provider Specialty

Provider ki specialty matching ka important factor hai.

Example:

```text
Cardiology
Neurology
Orthopedics
Dermatology
Pediatrics
```

Agar facility ko cardiologist chahiye to system ko cardiology specialty wale providers ko priority deni chahiye.

---

# 6. Previous Work History

Provider ka previous work history bhi matching mein important hoga.

Example:

```text
Provider A

Specialty:
Cardiology

Previous Hospitals:
Hospital A
Hospital B
Hospital C

Experience:
7 years
```

System check kar sakta hai:

```text
Previous experience
+
Relevant specialty
+
Facility type experience
```

---

# 7. Availability

Provider available hai ya nahi, ye sabse important checks mein se ek hai.

Example:

```text
Provider A
Monday → Available
Tuesday → Available
Wednesday → Not Available
```

Agar facility ko Wednesday ko provider chahiye, to Provider A match nahi hoga.

Availability ko database mein date/time ke saath store karna chahiye.

---

# 8. Distance

Provider aur facility ke beech distance calculate karna hoga.

Example:

```text
Provider A → 5 km
Provider B → 15 km
Provider C → 30 km
```

Agar baaki factors same hain, to nearer provider ko higher score diya ja sakta hai.

MongoDB mein geospatial queries ke liye:

```text
2dsphere index
```

use kiya ja sakta hai.

---

# 9. Facility Preferences

Facility apni preferences define kar sakti hai.

Example:

```text
Preferred Specialty
Minimum Experience
Preferred Provider Type
Maximum Distance
Preferred Shift
```

Example:

```text
Facility Requirement:

Specialty:
Cardiology

Minimum Experience:
5 years

Maximum Distance:
20 km
```

---

# 10. Provider Preferences

Provider ki preferences bhi consider karni hongi.

Example:

```text
Preferred Location
Preferred Shift
Maximum Travel Distance
Preferred Facility Type
Available Days
```

Example:

```text
Provider Preference:

Maximum Distance:
15 km

Preferred Shift:
Morning

Preferred Facility:
Hospital
```

---

# 11. Priority Rules

Sabhi factors equally important nahi hote.

Priority rules define karenge ki kaunsa factor zyada important hai.

Example:

```text
Specialty Match       → 40%
Availability           → 25%
Distance               → 15%
Experience             → 10%
Preferences            → 10%
```

Total:

```text
40 + 25 + 15 + 10 + 10 = 100%
```

---

# 12. Matching Score

Main ek scoring system design karunga.

Example:

```text
Match Score =
Specialty Score
+
Availability Score
+
Distance Score
+
Experience Score
+
Preference Score
```

Example:

```text
Provider A

Specialty       = 40/40
Availability    = 25/25
Distance        = 12/15
Experience      = 8/10
Preferences     = 10/10

Total Score = 95/100
```

Provider A ko high priority match milega.

---

# 13. Hard Rules vs Soft Rules

Ye system design ka important part hai.

## Hard Rules

Hard rules mandatory conditions hongi.

Example:

```text
Wrong Specialty
        ↓
Reject

Not Available
        ↓
Reject

Inactive Provider
        ↓
Reject
```

Agar provider required specialty ka nahi hai to usko sirf score dekar match nahi karna chahiye.

---

## Soft Rules

Soft rules ranking ke liye use hongi.

Example:

```text
Closer Distance
More Experience
Preferred Facility
Preferred Shift
```

Ye provider ko reject nahi karte, bas ranking improve/reduce karte hain.

---

# 14. Database Structure

MERN application hone ki wajah se MongoDB use kar sakte hain.

Main collections kuch is type ki rakhunga:

```text
providers
facilities
availability
work_history
preferences
matches
assignments
priority_rules
```

---

# 15. Provider Collection

Example:

```javascript
{
    _id,
    name,
    specialty,
    experience,
    location,
    status
}
```

---

# 16. Facility Collection

Example:

```javascript
{
    _id,
    name,
    location,
    requiredSpecialties,
    preferences,
    priority
}
```

---

# 17. Availability Collection

Example:

```javascript
{
    _id,
    providerId,
    date,
    startTime,
    endTime,
    status
}
```

---

# 18. Work History Collection

Example:

```javascript
{
    _id,
    providerId,
    facilityId,
    specialty,
    startDate,
    endDate
}
```

---

# 19. Preferences Collection

Example:

```javascript
{
    _id,
    providerId,
    preferredDistance,
    preferredShift,
    preferredFacilityTypes
}
```

Facility preferences ko bhi separate collection mein store kiya ja sakta hai agar requirements complex hon.

---

# 20. Match Collection

Matching results store karne ke liye:

```javascript
{
    _id,
    providerId,
    facilityId,
    score,
    reasons,
    status,
    createdAt
}
```

Example:

```json
{
    "providerId": "P101",
    "facilityId": "F101",
    "score": 95,
    "reasons": [
        "Specialty matched",
        "Provider available",
        "Near facility",
        "Experience requirement satisfied"
    ]
}
```

---

# 21. APIs

Main REST APIs create karunga.

## Provider APIs

```text
GET    /api/providers
GET    /api/providers/:id
POST   /api/providers
PUT    /api/providers/:id
DELETE /api/providers/:id
```

---

## Facility APIs

```text
GET    /api/facilities
GET    /api/facilities/:id
POST   /api/facilities
PUT    /api/facilities/:id
DELETE /api/facilities/:id
```

---

## Availability APIs

```text
GET    /api/providers/:id/availability
POST   /api/providers/:id/availability
PUT    /api/providers/:id/availability/:availabilityId
```

---

## Matching API

Main matching API:

```text
POST /api/matches
```

Ya:

```text
GET /api/facilities/:id/matches
```

Ye facility ke liye best matching providers return karega.

---

# 22. Matching API Flow

Example:

```text
Facility Request
       ↓
Validate Requirements
       ↓
Find Providers
       ↓
Filter Hard Rules
       ↓
Check Availability
       ↓
Calculate Distance
       ↓
Check Experience
       ↓
Check Preferences
       ↓
Calculate Score
       ↓
Sort by Score
       ↓
Return Best Matches
```

---

# 23. Example

Facility:

```text
Specialty: Cardiology
Minimum Experience: 5 years
Maximum Distance: 20 km
Date: 10 September
```

Providers:

```text
Provider A
Cardiology
7 years
5 km
Available

Provider B
Cardiology
10 years
18 km
Available

Provider C
Neurology
8 years
3 km
Available
```

Result:

```text
Provider A → Match
Provider B → Match
Provider C → Reject
```

Reason:

```text
Provider C has wrong specialty.
```

---

# 24. AI Can Help With

AI development mein helpful ho sakta hai.

AI help kar sakta hai:

```text
Boilerplate API Code
MongoDB Queries
Aggregation Pipelines
Validation Schemas
Unit Test Cases
API Documentation
Code Explanation
Error Handling Suggestions
Test Data Generation
```

Example:

```text
"Generate a MongoDB query to find available
cardiologists within a specific distance."
```

AI initial query generate kar sakta hai.

Lekin developer ko query verify karni hogi.

---

# 25. AI Should NOT Make Final Decisions

Healthcare matching system mein AI ko blindly final decision-maker nahi banana chahiye.

AI suggestions de sakta hai, lekin final business rules deterministic hone chahiye.

Example:

```text
Required Specialty
Availability
License / Eligibility
Priority Rules
```

in jaise critical conditions ko clearly defined rules se validate karna chahiye.

---

# 26. Human Decision-Making

Human developer/architect ko decide karna hoga:

```text
Business Rules
Database Architecture
API Design
Security
Authorization
Matching Algorithm
Priority Rules
Data Validation
Privacy
Performance
Failure Handling
```

---

# 27. Security

Healthcare-related system hone ki wajah se security important hai.

Main implement karunga:

```text
Authentication
Authorization
Role-Based Access Control
Input Validation
API Security
Encryption
Audit Logs
Secure Database Access
```

Example roles:

```text
Admin
Hospital Manager
Provider
Staff
```

Har role ko sirf required permissions milengi.

---

# 28. Performance

Agar providers ki quantity bahut large ho jaye to har provider ko scan karke score karna expensive ho sakta hai.

Isliye:

```text
Indexes
Geospatial Index
Filtering
Pagination
Caching
Precomputed Data
Background Jobs
```

use kar sakte hain.

MongoDB location queries ke liye:

```text
2dsphere index
```

use karunga.

---

# 29. Scalability

Agar system initially:

```text
1,000 Providers
100 Facilities
```

handle karta hai aur future mein:

```text
1,000,000 Providers
10,000 Facilities
```

ho jaate hain, to architecture scalable hona chahiye.

Possible improvements:

```text
Load Balancer
Multiple API Servers
MongoDB Replica Set
Caching
Queue / Background Jobs
Monitoring
Horizontal Scaling
```

---

# 30. Logging and Monitoring

Production mein monitoring important hogi.

Track:

```text
API Response Time
Matching Time
Database Query Time
Error Rate
Failed Matches
Number of Matches
System Load
```

Isse performance problems identify karna easy hoga.

---

# 31. Final Architecture

High-level architecture:

```text
                Client
                  ↓
             React Frontend
                  ↓
             API / Backend
                  ↓
        Authentication Layer
                  ↓
          Matching Service
             ↓        ↓
       Rule Engine   Scoring
             ↓        ↓
               MongoDB
             ↓
       Providers / Facilities
       Availability / History
       Preferences / Matches
```

---

# 32. Important Design Principle

Matching system ko main do stages mein divide karunga:

## Stage 1 — Filtering

Hard requirements check:

```text
Correct Specialty?
Available?
Active?
Eligible?
Within allowed constraints?
```

Jo fail kare:

```text
Reject
```

---

## Stage 2 — Ranking

Remaining providers ko score:

```text
Distance
Experience
Preferences
Priority
```

ke basis par rank karunga.

Is approach se unnecessary processing reduce hogi.

---

# Interview Ready Answer — English

> "I would design the provider-to-facility matching system as a rule-based filtering and ranking system. First, I would identify the entities such as providers, facilities, availability, work history, preferences, matches and priority rules."

> "I would first apply hard rules such as specialty, availability and eligibility to eliminate invalid providers. Then I would calculate a matching score using soft factors such as distance, experience and provider or facility preferences."

> "For MongoDB, I would use separate collections for providers, facilities, availability, work history, preferences and matches. For location-based matching, I would use a 2dsphere geospatial index."

> "I would expose APIs for provider management, facility management, availability and matching. The matching API would filter candidates, calculate scores, sort them and return the best matches."

> "AI could help generate boilerplate APIs, MongoDB queries, aggregation pipelines, test cases and documentation. However, business rules, security, architecture and final matching decisions should remain under human control."

> "Finally, I would focus on security, scalability, monitoring and performance because this system may handle a large number of providers and facilities."

---

# Interview Ready Answer — Hindi + English

> "Main is system ko mainly do parts mein divide karunga — filtering aur ranking. Pehle hard rules apply karunga, jaise provider ki specialty, availability aur eligibility. Jo provider basic requirements satisfy nahi karta usko reject kar dunga."

> "Uske baad remaining providers ko distance, experience, provider preference aur facility preference ke basis par score dunga aur highest score wale providers ko top par rakhunga."

> "Database mein providers, facilities, availability, work history, preferences aur matches ke liye collections rakhunga. Location matching ke liye MongoDB ka 2dsphere geospatial index use karunga."

> "AI boilerplate code, MongoDB queries, aggregation pipelines aur test cases generate karne mein help kar sakta hai, lekin final business rules, architecture, security aur critical decisions human developer ko verify karne chahiye."

---

# Short Interview Answer

> "I would build the system using a two-step matching approach: first filter providers using hard requirements like specialty and availability, then rank the remaining providers using distance, experience and preferences. MongoDB would store providers, facilities, availability, work history and matches, with a 2dsphere index for location queries. APIs would handle providers, facilities, availability and matching. AI could help with boilerplate code, queries and tests, but the final business rules and critical decisions would be validated by humans."

---

# Quick Revision

```text
Provider
   +
Facility
   +
Specialty
   +
Availability
   +
Work History
   +
Distance
   +
Preferences
   +
Priority Rules
        ↓
Hard Filtering
        ↓
Scoring
        ↓
Ranking
        ↓
Best Match
```

# Golden Interview Line

> **"I would separate hard eligibility rules from soft ranking factors. First eliminate providers who cannot be matched, then rank the remaining providers based on distance, experience, availability and preferences."**