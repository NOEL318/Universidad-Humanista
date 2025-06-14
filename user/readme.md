| Field                            | Type    | Description                                                               |
| -------------------------------- | ------- | ------------------------------------------------------------------------- |
| `disability`                     | Boolean | Whether the user has a registered disability.                             |
| `disability_type`                | String  | Description of the disability type.                                       |
| `chronic_conditions`             | String  | List or description of chronic diseases or conditions (e.g., diabetes).   |
| `mental_health_conditions`       | String  | Description of any mental health-related conditions.                      |
| `blood_type`                     | String  | Blood type (e.g., A+, O-, AB-).                                           |
| `allergies`                      | String  | Known allergies (e.g., nuts, pollen, penicillin).                         |
| `emergency_contact_name`         | String  | Full name of the emergency contact.                                       |
| `emergency_contact_relationship` | String  | Relationship with the user (e.g., mother, friend, guardian).              |
| `emergency_contact_phone`        | String  | Phone number to call in case of emergency.                                |
| `has_health_insurance`           | Boolean | Indicates whether the user has active health insurance.                   |
| `insurance_provider`             | String  | Name of the insurance provider.                                           |
| `insurance_policy_number`        | String  | Policy or membership number.                                              |
| `insurance_valid_until`          | String  | Expiration date of the insurance coverage.                                |
| `medications`                    | String  | Medications currently taken by the user.                                  |
| `mobility_aid`                   | Boolean | Whether the user needs assistive devices for mobility (e.g., wheelchair). |
| `vision_problems`                | Boolean | Whether the user has vision issues or needs corrective lenses.            |
| `hearing_problems`               | Boolean | Whether the user has hearing impairments or needs assistive devices.      |

```java


// Medical Registry
private Boolean disability;
private String disability_type;
private String chronic_conditions;
private String mental_health_conditions;

private String blood_type;
private String allergies;
private String emergency_contact_name;
private String emergency_contact_relationship;

private String emergency_contact_phone;
private Boolean has_health_insurance;
private String insurance_provider;
private String insurance_policy_number;
private String insurance_valid_until;
private String medications;

private Boolean mobility_aid;         // Needs something to move
private Boolean vision_problems;      // Needs glasses or has any issue to see
private Boolean hearing_problems;     // Needs a special device to hear correctly

// Medical Service History
private String medical_ref;
```
