# Patient Appointment Portal – User Stories

## Admin User Stories

1. **User Management**
   - **Story:** As an admin, I want to create, update, and delete user accounts so that I can control who has access to the system.
   - **Priority:** High
   - **Story Points:** 5
   - **Acceptance Criteria:**
     - Admin can create, update, and delete users.
     - User receives email confirmation upon creation.
     - Deleted users cannot log in.

2. **Role Management**
   - **Story:** As an admin, I want to assign roles to users (Admin, Doctor, Patient) so that each user has appropriate permissions.
   - **Priority:** High
   - **Story Points:** 3
   - **Acceptance Criteria:**
     - Admin can assign and update roles.
     - Users can only access features permitted by their role.
     - Changes are logged in system logs.

3. **System Monitoring**
   - **Story:** As an admin, I want to view system logs and activity reports so that I can monitor usage and detect issues.
   - **Priority:** Medium
   - **Story Points:** 5
   - **Acceptance Criteria:**
     - Admin can view logs of login, logout, and user actions.
     - Reports can be exported in CSV or PDF format.
     - System alerts for suspicious activity are displayed.

4. **Appointment Oversight**
   - **Story:** As an admin, I want to see all scheduled appointments so that I can resolve conflicts or manage scheduling issues.
   - **Priority:** High
   - **Story Points:** 3
   - **Acceptance Criteria:**
     - Admin can view all appointments for all doctors.
     - Conflicts (double-bookings) are highlighted.
     - Admin can reschedule or cancel appointments if needed.

5. **Audit & Security Control**
   - **Story:** As an admin, I want audit logs of all changes so that I can ensure compliance and security.
   - **Priority:** Medium
   - **Story Points:** 3
   - **Acceptance Criteria:**
     - Every critical action (user creation, deletion, role changes) is logged.
     - Logs include timestamp and admin username.
     - Admin can search/filter logs by date, user, or action.

## Patient User Stories

1. **Account Management**
   - **Story:** As a patient, I want to create and manage my account so that I can access the portal securely.
   - **Priority:** High
   - **Story Points:** 3
   - **Acceptance Criteria:**
     - Patients can register and update profile information.
     - Password reset is available via email.
     - Account is secured with encryption and authentication.

2. **Appointment Booking**
   - **Story:** As a patient, I want to view available time slots and book appointments online so that I don’t need to call the clinic.
   - **Priority:** High
   - **Story Points:** 5
   - **Acceptance Criteria:**
     - Available time slots are displayed by doctor.
     - Double bookings are prevented.
     - Confirmation email or portal notification is sent.

3. **Appointment Management**
   - **Story:** As a patient, I want to reschedule or cancel appointments so that I can adjust to changes in my availability.
   - **Priority:** Medium
   - **Story Points:** 3
   - **Acceptance Criteria:**
     - Patient can cancel or reschedule appointments.
     - Doctor receives notification of changes.
     - System prevents rescheduling outside of available slots.

4. **Notifications**
   - **Story:** As a patient, I want to receive reminders for upcoming appointments so that I don’t miss them.
   - **Priority:** Medium
   - **Story Points:** 2
   - **Acceptance Criteria:**
     - Email, SMS, or push notifications are sent 24 hours before appointment.
     - Notification settings can be customized by patient.

5. **View Medical History**
   - **Story:** As a patient, I want to access my past appointment details so that I can track my medical history.
   - **Priority:** Medium
   - **Story Points:** 3
   - **Acceptance Criteria:**
     - Patient can view previous visits, doctor notes, and prescriptions.
     - History can be exported in PDF format.

## Doctor User Stories

1. **Profile Management**
   - **Story:** As a doctor, I want to update my profile and availability so that patients can see when I’m free.
   - **Priority:** High
   - **Story Points:** 3
   - **Acceptance Criteria:**
     - Doctor can update profile info and working hours.
     - Updates are reflected in patient booking view.

2. **Appointment Management**
   - **Story:** As a doctor, I want to view and confirm appointments so that I can plan my schedule efficiently.
   - **Priority:** High
   - **Story Points:** 3
   - **Acceptance Criteria:**
     - Doctor can see upcoming appointments.
     - Can confirm, cancel, or mark appointments as completed.

3. **Patient Interaction**
   - **Story:** As a doctor, I want to see patient details for upcoming appointments so that I can prepare for consultations.
   - **Priority:** High
   - **Story Points:** 3
   - **Acceptance Criteria:**
     - Doctor can view patient medical history and notes before appointment.
     - Only authorized access to patient data is allowed.

4. **Notifications**
   - **Story:** As a doctor, I want to receive notifications for new bookings or cancellations so that I stay informed.
   - **Priority:** Medium
   - **Story Points:** 2
   - **Acceptance Criteria:**
     - Notifications sent via email, SMS, or portal.
     - Notification settings are configurable.

5. **Availability Management**
   - **Story:** As a doctor, I want to block off unavailable times so that patients cannot book during those periods.
   - **Priority:** Medium
   - **Story Points:** 2
   - **Acceptance Criteria:**
     - Doctor can block time slots for personal use or emergencies.
     - Patients cannot book blocked times.

## Optional Enhancements

1. **Telemedicine Sessions**
   - **Story:** As a patient, I want to join telemedicine sessions via the portal so that I can consult remotely.
   - **Priority:** Medium
   - **Story Points:** 8
   - **Acceptance Criteria:**
     - Video call works on desktop and mobile.
     - Doctors can start/end sessions.
     - Session duration and notes are logged.

2. **Calendar Integration**
   - **Story:** As a doctor, I want the portal to sync with Google/Outlook calendar so that my availability is always up-to-date.
   - **Priority:** Medium
   - **Story Points:** 5
   - **Acceptance Criteria:**
     - Sync occurs automatically.
     - Conflicting times are flagged.

3. **Feedback/Rating System**
   - **Story:** As a patient, I want to rate my appointments so that I can provide feedback on the service.
   - **Priority:** Low
   - **Story Points:** 3
   - **Acceptance Criteria:**
     - Ratings are collected per appointment.
     - Doctors can view feedback anonymously.
