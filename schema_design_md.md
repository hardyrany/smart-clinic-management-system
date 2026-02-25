# Smart Clinic Management System - Database Schema

## MySQL Database Design

### Tables

#### `users`
| Column        | Type          | Key          | Description                  |
|---------------|---------------|-------------|------------------------------|
| id            | INT           | PK          | Unique identifier for user   |
| username      | VARCHAR(50)   | UNIQUE      | Login username               |
| password      | VARCHAR(255)  |             | Hashed password              |
| role          | ENUM('ADMIN', 'DOCTOR', 'PATIENT') | | User role |
| created_at    | DATETIME      |             | Account creation timestamp   |
| updated_at    | DATETIME      |             | Last update timestamp        |

#### `doctors`
| Column        | Type          | Key          | Description                  |
|---------------|---------------|-------------|------------------------------|
| id            | INT           | PK          | Unique identifier            |
| user_id       | INT           | FK -> users(id) | Reference to user account |
| specialty     | VARCHAR(100)  |             | Doctor's medical specialty   |
| created_at    | DATETIME      |             | Record creation timestamp    |
| updated_at    | DATETIME      |             | Last update timestamp        |

#### `patients`
| Column        | Type          | Key          | Description                  |
|---------------|---------------|-------------|------------------------------|
| id            | INT           | PK          | Unique identifier            |
| user_id       | INT           | FK -> users(id) | Reference to user account |
| date_of_birth | DATE          |             | Patient's birth date         |
| contact_info  | VARCHAR(255)  |             | Phone/email/contact details  |
| created_at    | DATETIME      |             | Record creation timestamp    |
| updated_at    | DATETIME      |             | Last update timestamp        |

#### `appointments`
| Column        | Type          | Key          | Description                  |
|---------------|---------------|-------------|------------------------------|
| id            | INT           | PK          | Unique identifier            |
| doctor_id     | INT           | FK -> doctors(id) | Doctor assigned          |
| patient_id    | INT           | FK -> patients(id) | Patient booked           |
| appointment_time | DATETIME   |             | Scheduled date and time      |
| status        | ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') | | Appointment status |
| created_at    | DATETIME      |             | Record creation timestamp    |
| updated_at    | DATETIME      |             | Last update timestamp        |

#### `prescriptions`
| Column        | Type          | Key          | Description                  |
|---------------|---------------|-------------|------------------------------|
| id            | INT           | PK          | Unique identifier            |
| appointment_id| INT           | FK -> appointments(id) | Linked appointment      |
| medication    | VARCHAR(255)  |             | Prescribed medication        |
| dosage        | VARCHAR(100)  |             | Dosage instructions          |
| created_at    | DATETIME      |             | Record creation timestamp    |
| updated_at    | DATETIME      |             | Last update timestamp        |

### Relationships
- One `user` can be either a doctor or a patient (role determines type).
- One `doctor` can have many `appointments`.
- One `patient` can have many `appointments`.
- One `appointment` can have many `prescriptions`.

### Diagram
![Smart Clinic ER Diagram](./smartclinic_er_diagram.png)

