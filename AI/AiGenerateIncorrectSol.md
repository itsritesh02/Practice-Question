
==================================================
# Q10. Real Situation Where AI Generated an Incorrect Solution
==================================================

## Question

Tell us about a real situation where AI generated an incorrect solution.

Explain:

- What the problem was
- Why the AI solution was incorrect
- How you identified the issue
- How you fixed it

If you don't have professional experience, you may describe
a personal project.

---

# Answer

## Problem

In one of my MERN projects, I had a problem with a MongoDB query.
I asked AI to help optimize the query.

The AI suggested changing the query and adding an index.

---

## Why AI Solution Was Incorrect

The suggested query was syntactically correct, but it did not
match the actual business requirement.

The query was returning data correctly for simple cases but
gave incorrect results for some edge cases.

Also, the suggested index was not useful for the actual query pattern.

---

## How I Identified the Issue

I did not directly use the AI solution.

I tested it with different inputs and compared the result with
the expected result.

I also checked:

    explain("executionStats")

and reviewed the actual MongoDB query and indexes.

This helped me identify that the AI's assumption about the
query/filtering logic was incorrect.

---

## How I Fixed It

I changed the query according to the actual business requirement.

Then I:

1. Tested normal cases
2. Tested edge cases
3. Checked the query result
4. Checked index usage
5. Used explain()
6. Compared performance before and after

After testing, I used the corrected solution instead of
blindly accepting the AI-generated code.

---

# Q10 — Final Machine Round Answer

In my MERN project, I once used AI to solve a MongoDB query problem.
The AI-generated solution looked correct, but it did not handle
some edge cases according to the actual requirement.

I identified the issue by testing different inputs and comparing
the results with the expected output.

I then checked the actual query and database logic, corrected
the query, tested edge cases, and used `explain()` to verify
performance.

This taught me that AI is useful for development, but its output
must always be verified with actual code, requirements and tests.


==================================================
# Section 5 — System Design & Engineering Thinking
==================================================

# Q11. Provider-to-Facility Matching System

## Question

Design a Provider-to-Facility Matching System for hospitals.

The system should consider:

- Provider specialty
- Previous work history
- Distance
- Availability
- Facility preferences

---

# Answer

I would design the system using a matching service.

The main goal is to find the best hospital/facility for a
provider based on multiple factors.

---

# Step 1 — Main Entities

I would create the following main entities:

    Provider
    Facility
    WorkHistory
    Availability
    FacilityPreference
    Match

---

# Step 2 — Provider

Provider stores information about the doctor/provider.

Example:

    {
        _id: "P101",
        name: "Dr. Ritesh",
        specialty: "Cardiology",
        location: {
            lat: 30.7333,
            lng: 76.7794
        }
    }

Important fields:

- Name
- Specialty
- Location
- Experience
- Availability

---

# Step 3 — Facility

Facility stores hospital information.

Example:

    {
        _id: "F101",
        name: "City Hospital",
        location: {
            lat: 30.7415,
            lng: 76.7681
        },
        specialties: [
            "Cardiology",
            "Neurology"
        ]
    }

Important fields:

- Name
- Location
- Supported specialties
- Capacity
- Preferences

---

# Step 4 — Previous Work History

We need to know where the provider has worked before.

Example:

    {
        providerId: "P101",
        facilityId: "F050",
        specialty: "Cardiology",
        startDate: "2024-01-01",
        endDate: "2025-01-01"
    }

This can help determine whether the provider has
experience with a particular facility or type of facility.

---

# Step 5 — Availability

We need to check whether the provider is available.

Example:

    {
        providerId: "P101",
        date: "2026-09-10",
        startTime: "09:00",
        endTime: "17:00",
        available: true
    }

A facility should not be matched if the provider
is unavailable.

---

# Step 6 — Facility Preferences

Facilities may have preferences.

Example:

    {
        facilityId: "F101",
        preferredSpecialties: [
            "Cardiology"
        ],
        minimumExperience: 3
    }

The matching system will consider these preferences.

---

# Step 7 — Matching Logic

I would apply filters first.

Example:

    Provider Specialty
            ↓
    Facility Supports Specialty?
            ↓
        Availability
            ↓
        Distance
            ↓
      Work History
            ↓
    Facility Preferences
            ↓
       Final Ranking

---

# Step 8 — Distance

Distance is an important factor.

For example:

    Provider → 5 km → Facility A
    Provider → 20 km → Facility B
    Provider → 50 km → Facility C

If other factors are similar, Facility A should get
a higher score because it is closer.

MongoDB geospatial queries can be used for location-based
search.

Example:

    $near

or:

    $geoNear

---

# Step 9 — Matching Score

I would calculate a score for every suitable facility.

Example:

    Specialty Match     = 30 points
    Work History        = 20 points
    Distance            = 20 points
    Availability        = 20 points
    Facility Preference = 10 points

Total:

    100 points

Example result:

    Facility A → 90
    Facility B → 75
    Facility C → 60

Therefore:

    Facility A = Best Match

---

# Step 10 — API Design

Example APIs:

    GET /api/providers/:id

Get provider details.

    GET /api/facilities

Get facilities.

    GET /api/providers/:id/matches

Get matching facilities for a provider.

    POST /api/matches

Create/save a match.

---

# Step 11 — Backend Flow

The backend can follow this flow:

    Client
      ↓
    Express API
      ↓
    Matching Controller
      ↓
    Matching Service
      ↓
    MongoDB
      ↓
    Calculate Score
      ↓
    Sort by Score
      ↓
    Return Best Matches

---

# Step 12 — Indexing

I would create indexes for frequently searched fields.

Example:

    db.providers.createIndex({
        specialty: 1
    });

For location-based searches, I would use a geospatial index.

Example:

    db.providers.createIndex({
        location: "2dsphere"
    });

For availability:

    db.availability.createIndex({
        providerId: 1,
        date: 1
    });

---

# Step 13 — Scalability

If the number of providers and facilities becomes very large,
I would optimize queries and indexes first.

For high traffic, I could use:

- Redis caching
- Background jobs
- Message queues
- Database optimization
- Horizontal scaling

If the database becomes extremely large, sharding can also
be considered.

---

# Q11 — Final Machine Round Answer

I would design the system around Provider, Facility, WorkHistory,
Availability and FacilityPreference entities.

First, I would filter facilities based on provider specialty
and availability.

Then I would calculate distance using geospatial queries and
consider previous work history and facility preferences.

Finally, I would calculate a matching score for each facility,
sort the results by score and return the best matches.

For scalability, I would use proper MongoDB indexes, including
a `2dsphere` index for location-based searches, and use caching
or background processing if traffic increases.

---

# QUICK REVISION

## Q9 — AI MongoDB Query

    Check Correctness
          ↓
    explain()
          ↓
    executionStats
          ↓
    Check Indexes
          ↓
    Check Aggregation
          ↓
    Production Data Test
          ↓
    Optimize
          ↓
    Accept / Reject


## Q10 — AI Incorrect Solution

    AI Solution
         ↓
    Test
         ↓
    Find Issue
         ↓
    Check Requirement
         ↓
    Fix
         ↓
    Test Again


## Q11 — Matching System

    Provider
       ↓
    Specialty
       ↓
    Availability
       ↓
    Distance
       ↓
    Work History
       ↓
    Facility Preference
       ↓
    Matching Score
       ↓
    Best Facility