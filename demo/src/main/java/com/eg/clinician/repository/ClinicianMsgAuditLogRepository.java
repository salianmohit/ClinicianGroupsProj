/**
 * 
 */
package com.eg.clinician.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eg.clinician.entity.ClinicianMsgAuditLog;

/**
 * @author Mohit.Salian
 *
 *         Repository class for getting the save inbuilt methods of
 *         JPARepository for Message persistence in DB
 */
public interface ClinicianMsgAuditLogRepository extends JpaRepository<ClinicianMsgAuditLog, Long> {

}
