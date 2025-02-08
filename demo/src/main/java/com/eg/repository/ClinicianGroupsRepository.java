/**
 * 
 */
package com.eg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eg.entity.ClinicianGroups;

/**
 * @author Mohit.Salian
 * 
 *         Repository class for getting the inbuilt methods of JPARepository
 *         (CRUD operations)
 *
 */
public interface ClinicianGroupsRepository extends JpaRepository<ClinicianGroups, Long> {

}
