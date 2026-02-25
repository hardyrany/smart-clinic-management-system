package com.smartclinic.repository;

import com.smartclinic.model.entity.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Patient} entity operations.
 * Provides CRUD operations and custom query methods for patient data access.
 * 
 * @author SmartClinic Team
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

    /**
     * Retrieves a patient by their email address.
     * Email addresses are unique in the system, so this returns at most one result.
     *
     * @param email the email address of the patient (must not be null)
     * @return an {@link Optional} containing the patient if found, or empty if not found
     * @throws IllegalArgumentException if email is null
     */
    Optional<Patient> findByEmail(String email);

    /**
     * Retrieves a patient by their phone number.
     * Phone numbers are unique in the system, so this returns at most one result.
     *
     * @param phone the phone number of the patient (must not be null)
     * @return an {@link Optional} containing the patient if found, or empty if not found
     * @throws IllegalArgumentException if phone is null
     */
    Optional<Patient> findByPhone(String phone);

    /**
     * Retrieves a patient by either their email address OR phone number.
     * Useful for login scenarios where the user can provide either identifier.
     * Note: If both email and phone exist but belong to different patients,
     * the result will be the first match found by MongoDB.
     *
     * @param email the email address to search for (can be null)
     * @param phone the phone number to search for (can be null)
     * @return an {@link Optional} containing the patient if found by either criteria,
     *         or empty if no match found
     * @apiNote At least one parameter must be non-null for meaningful results
     */
    Optional<Patient> findByEmailOrPhone(String email, String phone);

    /**
     * Checks if a patient exists with the given email address.
     *
     * @param email the email address to check (must not be null)
     * @return true if a patient with the email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a patient exists with the given phone number.
     *
     * @param phone the phone number to check (must not be null)
     * @return true if a patient with the phone exists, false otherwise
     */
    boolean existsByPhone(String phone);

    /**
     * Retrieves all patients with the specified last name.
     *
     * @param lastName the last name to search for (must not be null)
     * @return a list of patients with the given last name (may be empty)
     */
    List<Patient> findByLastName(String lastName);

    /**
     * Retrieves all patients with the specified last name, ignoring case.
     * Provides case-insensitive search for better user experience.
     *
     * @param lastName the last name to search for (case-insensitive, must not be null)
     * @return a list of patients with the given last name (may be empty)
     */
    List<Patient> findByLastNameIgnoreCase(String lastName);

    /**
     * Retrieves all patients of the specified age.
     *
     * @param age the exact age to search for
     * @return a list of patients with the given age (may be empty)
     */
    List<Patient> findByAge(int age);

    /**
     * Retrieves all patients residing in the specified city.
     *
     * @param city the city name to search for (must not be null)
     * @return a list of patients from the given city (may be empty)
     */
    List<Patient> findByCity(String city);

    /**
     * Retrieves all patients with the specified last name, ordered by first name.
     * Useful for alphabetical listings and reports.
     *
     * @param lastName the last name to search for (must not be null)
     * @return a list of patients sorted by first name ascending
     */
    List<Patient> findByLastNameOrderByFirstNameAsc(String lastName);
}