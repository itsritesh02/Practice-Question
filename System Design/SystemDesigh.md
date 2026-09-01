# Q11. Provider-to-Facility Matching System

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

I would design a matching system that finds the most suitable
hospital/facility for a provider.

The system will collect provider information, facility information,
availability, preferences and previous work history.

Then it will filter and rank facilities according to predefined
business rules.

---

# 1. Break Down the Problem

I would divide the system into the following parts:

    Provider
       ↓
    Specialty
       ↓
    Previous Work History
       ↓
    Availability
       ↓
    Distance
       ↓
    Provider Preferences
       ↓
    Facility Preferences
       ↓
    Priority Rules
       ↓
    Matching Score
       ↓
    Best Facilities

---

# 2. Provider

A Provider can be a doctor or healthcare professional.

The Provider information can contain:

- Name
- Specialty
- Experience
- Location
- Availability
- Preferences
- Previous work history

### Example

    Provider:

    Name: Dr. Ritesh
    Specialty: Cardiology
    Experience: 5 years
    Location: Chandigarh

---

# 3. Facility

A Facility represents a hospital or healthcare center.

The Facility information can contain:

- Name
- Location
- Supported specialties
- Available positions
- Working hours
- Facility preferences

### Example

    Facility:

    Name: City Hospital
    Location: Chandigarh
    Specialties:
    - Cardiology
    - Neurology

---

# 4. Previous Work History

Previous work history tells us where the provider has
worked before.

### Example

    Provider: Dr. Ritesh

    Previous Facilities:
    - City Hospital
    - Apollo Hospital

    Previous Specialty:
    Cardiology

### Why is it useful?

If a provider has already worked in a similar facility,
the system can give that facility a higher matching score.

---

# 5. Distance

Distance between provider and facility should be considered.

### Example

    Provider
       |
       |---- 5 km ----> Facility A
       |
       |---- 20 km ---> Facility B
       |
       |---- 50 km ---> Facility C

If all other factors are similar:

    Facility A
        ↓
    Higher Score

because it is closer.

For location-based searching, I would use geospatial queries
and a location index such as MongoDB's `2dsphere` index.

---

# 6. Availability

The system must check whether the provider is available
when the facility needs them.

### Example

    Provider Availability:

    Monday:
    9 AM - 5 PM

    Facility Requirement:

    Monday:
    10 AM - 4 PM

This is a valid match because the provider is available
during the required time.

If:

    Facility Requirement:
    6 PM - 10 PM

then the provider is not available and the match should
be rejected or given a very low score.

---

# 7. Facility Preferences

A facility may have specific requirements.

### Example

    Facility A:

    Preferred Specialty:
    Cardiology

    Minimum Experience:
    3 years

If the provider has:

    Specialty: Cardiology
    Experience: 5 years

then the provider matches the facility preference.

---

# 8. Provider Preferences

The provider may also have preferences.

### Example

    Provider Preferences:

    Preferred Location:
    Chandigarh

    Maximum Distance:
    30 km

    Preferred Facility Type:
    Private Hospital

If a facility is:

    10 km away
    Private Hospital

then it is a good match.

But if the facility is:

    80 km away

then it may be rejected because it exceeds the provider's
maximum distance preference.

---

# 9. Priority Rules

Some requirements are more important than others.

I would define clear priority rules.

### Example Priority

    1. Specialty Match
    2. Availability
    3. Required Experience
    4. Provider/Facility Preferences
    5. Distance
    6. Previous Work History

Some rules can be mandatory.

For example:

    Specialty does not match
            ↓
        Reject Match

    Provider unavailable
            ↓
        Reject Match

After mandatory requirements pass, the remaining factors
can be used for ranking.

---

# 10. Matching Score

For suitable providers and facilities, I would calculate
a matching score.

### Example

    Specialty Match       = 30 points
    Availability          = 25 points
    Experience            = 15 points
    Distance              = 10 points
    Provider Preference   = 10 points
    Facility Preference   = 10 points

    Total                 = 100 points

---

# Example Matching

Suppose we have three facilities.

### Facility A

    Specialty Match     = 30
    Availability        = 25
    Experience          = 15
    Distance            = 10
    Provider Preference = 10
    Facility Preference = 10

    Total = 100

### Facility B

    Specialty Match     = 30
    Availability        = 25
    Experience          = 15
    Distance            = 5
    Provider Preference = 5
    Facility Preference = 5

    Total = 85

### Facility C

    Specialty Match     = 30
    Availability        = 20
    Experience          = 10
    Distance            = 5
    Provider Preference = 5
    Facility Preference = 5

    Total = 75

### Final Ranking

    1. Facility A → 100
    2. Facility B → 85
    3. Facility C → 75

Facility A becomes the best match.

---

# 11. Database Structure

Since I am using the MERN stack, I would use MongoDB.

I would create collections such as:

    providers
    facilities
    availability
    workHistory
    preferences
    matches

---

# Provider Collection

Example:

    {
        _id: "P101",
        name: "Dr. Ritesh",
        specialty: "Cardiology",
        experience: 5,
        location: {
            lat: 30.7333,
            lng: 76.7794
        }
    }

---

# Facility Collection

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

---

# Availability Collection

Example:

    {
        providerId: "P101",
        date: "2026-09-10",
        startTime: "09:00",
        endTime: "17:00",
        available: true
    }

---

# Work History Collection

Example:

    {
        providerId: "P101",
        facilityId: "F050",
        specialty: "Cardiology",
        startDate: "2024-01-01",
        endDate: "2025-01-01"
    }

---

# Preferences Collection

This can store both provider and facility preferences.

### Example

    Provider:

    {
        providerId: "P101",
        preferredLocation: "Chandigarh",
        maxDistance: 30,
        preferredFacilityType: "Private"
    }

---

# Matches Collection

This collection can store generated matches.

### Example

    {
        providerId: "P101",
        facilityId: "F101",
        score: 90,
        status: "recommended"
    }

---

# 12. APIs

I would create APIs such as:

## Provider APIs

    GET /api/providers/:id

Get provider details.

    POST /api/providers

Create a provider.

    PUT /api/providers/:id

Update provider information.

---

## Facility APIs

    GET /api/facilities

Get facilities.

    GET /api/facilities/:id

Get a specific facility.

    POST /api/facilities

Create a facility.

---

## Availability APIs

    GET /api/providers/:id/availability

Get provider availability.

    POST /api/providers/:id/availability

Add provider availability.

---

## Matching API

    GET /api/providers/:id/matches

Get the best facility matches for a provider.

### Example Response

    {
        "providerId": "P101",
        "matches": [
            {
                "facilityId": "F101",
                "name": "City Hospital",
                "score": 90
            },
            {
                "facilityId": "F102",
                "name": "General Hospital",
                "score": 80
            }
        ]
    }

---

# 13. Overall System Flow

The complete system would work like this:

    Provider Request
          ↓
    Get Provider Details
          ↓
    Check Specialty
          ↓
    Check Availability
          ↓
    Find Nearby Facilities
          ↓
    Check Facility Preferences
          ↓
    Check Provider Preferences
          ↓
    Check Work History
          ↓
    Apply Priority Rules
          ↓
    Calculate Score
          ↓
    Rank Facilities
          ↓
    Return Best Matches

---

# 14. Which Parts AI Could Help Generate?

AI can help with repetitive development tasks.

For example:

### AI can help generate:

- MongoDB schemas
- Mongoose models
- CRUD APIs
- Express routes
- Controller boilerplate
- Validation code
- API documentation
- Test cases
- Basic matching logic
- Sample data
- Unit test structure

### Example

I can ask AI:

    "Create a Mongoose schema for Provider with
     specialty, experience, location and availability."

AI can generate the initial schema.

But I would review and modify it according to the
actual project requirements.

---

# 15. Which Parts Require Human Decision-Making?

Business rules should not be blindly decided by AI.

Human decisions are required for:

- Which criteria are mandatory
- Priority of each factor
- Matching score weights
- Healthcare business rules
- Privacy and security requirements
- What should happen when no match is available
- Whether a provider should be automatically rejected
- Final approval of recommendations

### Example

AI may suggest:

    Distance = 20%
    Experience = 20%

But the business may decide:

    Specialty = Mandatory
    Availability = Mandatory
    Distance = 30%
    Experience = 20%

Therefore, the final rules should be decided by
business/stakeholders and verified by engineers.

---

# 16. Scalability

If the system grows to millions of providers and facilities,
I would optimize it using:

- Proper MongoDB indexes
- Geospatial indexes
- Pagination
- Caching
- Background jobs
- API optimization

For example:

    providers
        ↓
    2dsphere index
        ↓
    Find nearby providers/facilities

For frequently requested matches:

    Client
      ↓
    API
      ↓
    Redis Cache
      ↓
    MongoDB

If the database becomes extremely large, horizontal scaling
and sharding can be considered.

---

# Q11 — Final Machine Round Answer

I would divide the system into Provider, Facility, Availability,
Work History and Preferences.

First, I would filter facilities using mandatory rules such as
specialty and availability.

Then I would consider distance, previous work history, provider
preferences and facility preferences.

After that, I would apply priority rules and calculate a matching
score to rank the facilities.

I would use MongoDB collections for providers, facilities,
availability, work history, preferences and matches.

I would create APIs for providers, facilities, availability and
matching.

AI could help generate schemas, CRUD APIs, boilerplate code and
test cases, but important business decisions such as priority
rules, matching weights, healthcare rules, privacy and final
approval should be handled and verified by humans.

---

# Q11 — QUICK REVISION

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
    Provider Preference
        ↓
    Facility Preference
        ↓
    Priority Rules
        ↓
    Matching Score
        ↓
    Ranking
        ↓
    Best Facility

---

# Most Important Points to Remember

Provider → Who needs a facility?

Facility → Hospital where provider can work.

Specialty → Does the provider's specialization match?

Availability → Is the provider available?

Distance → How far is the facility?

Work History → Where has the provider worked before?

Provider Preference → What does the provider want?

Facility Preference → What does the hospital want?

Priority Rules → Which requirements are more important?

Matching Score → Convert all factors into a ranking.

AI → Helps generate code, but humans decide business rules.