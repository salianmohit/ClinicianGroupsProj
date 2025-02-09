/**
 * 
 */
package com.eg.clinician.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eg.clinician.entity.ClinicianMsgAuditLog;

/**
 * @author Mohit.Salian
 *
 *         Repository class for getting the save inbuilt methods of
 *         JPARepository for Message persistence in DB
 */
@Repository
public interface ClinicianMsgAuditLogRepository extends JpaRepository<ClinicianMsgAuditLog, Long> {

}
