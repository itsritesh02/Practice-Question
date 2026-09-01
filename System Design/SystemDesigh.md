I would design a Provider-to-Facility Matching System that matches healthcare providers with hospitals based on multiple factors such as specialty, work history, distance, availability, and preferences.

1. Problem Breakdown

First, I would divide the system into four main parts:

- Provider management
- Facility management
- Matching engine
- Assignment and notification system

For every provider, I would store their specialty, experience, previous work history, location, availability, and preferences.

For every facility, I would store required specialties, location, working hours, facility preferences, and priority rules.

2. Data Structure

I would use MongoDB because the provider and facility data can have flexible fields.

Provider:
- providerId
- name
- specialty
- experience
- workHistory
- location
- availability
- preferences

Facility:
- facilityId
- name
- location
- requiredSpecialties
- availability
- preferences
- priority

Match:
- providerId
- facilityId
- matchingScore
- matchedFactors
- status

3. Matching Process

When a facility needs a provider, the system first filters providers based on basic requirements such as:

- Required specialty
- Availability
- Distance
- Experience

Then I would calculate a matching score.

For example:

Specialty match       → 30%
Work history          → 20%
Distance              → 15%
Availability          → 15%
Facility preference   → 10%
Provider preference   → 10%

The weights can be changed according to business requirements.

The providers with the highest scores would be shown first.

4. APIs

I would create REST APIs using Node.js and Express.

Examples:

POST /providers
GET /providers
PUT /providers/:id

POST /facilities
GET /facilities

POST /matches
GET /matches/:facilityId

POST /assignments

The matching API would receive a facility requirement, find suitable providers, calculate their scores, and return ranked matches.

5. Where AI Could Help

AI could help in areas where the data is unstructured.

For example, provider work history or resumes could be analyzed to identify relevant experience and skills.

AI could also help rank candidates based on historical matching patterns.

However, I would not let AI make the final decision automatically because this is a healthcare-related system.

The final matching decision should be based on clear business rules, constraints, and human approval.

6. Decision Making

I would use a rule-based scoring system for the final decision.

For example:

If specialty doesn't match:
    Provider is rejected.

If provider is unavailable:
    Provider is rejected.

If both match:
    Calculate the score based on distance,
    experience, preferences and priority.

Then:

Highest score
     ↓
Top match
     ↓
Hospital reviews
     ↓
Provider assigned

7. Priority Rules

Some rules should have higher priority than others.

For example:

Mandatory specialty match
        ↓
Provider availability
        ↓
Required experience
        ↓
Distance
        ↓
Preferences

This prevents a provider from being selected just because they are close when they don't have the required specialty.

Overall, I would keep the core matching logic deterministic and explainable, while using AI only to assist with unstructured data and recommendations.