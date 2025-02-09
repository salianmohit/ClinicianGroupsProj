/**
 * 
 */
package com.eg.clinician.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eg.clinician.entity.ClinicianGroups;

/**
 * @author Mohit.Salian
 * 
 *         Repository class for getting the inbuilt methods of JPARepository
 *         (CRUD operations)
 *
 */
public interface ClinicianGroupsRepository extends JpaRepository<ClinicianGroups, Long> {

}
