/**
 * 
 */
package com.eg.clinician.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eg.clinician.entity.ClinicianGroups;

/**
 * @author Mohit.Salian
 * 
 *         Repository class for getting the inbuilt methods of JPARepository
 *         (CRUD operations)
 *
 */
@Repository
public interface ClinicianGroupsRepository extends JpaRepository<ClinicianGroups, Long> {

}
