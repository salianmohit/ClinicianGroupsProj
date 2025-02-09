/**
 * 
 */
package com.eg.clinician.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eg.clinician.entity.ClinicianGroups;
import com.eg.clinician.entity.ClinicianMsgAuditLog;
import com.eg.clinician.repository.ClinicianGroupsRepository;
import com.eg.clinician.repository.ClinicianMsgAuditLogRepository;

/**
 * @author Mohit.Salian
 *
 *         Calling the JPA repository CRUD operations method for creating a
 *         group, updating and removing
 */
@Service
public class ClinicianGroupsService {

	@Autowired
	ClinicianGroupsRepository cliniciangroupsrepo;

	@Autowired
	ClinicianMsgAuditLogRepository clinicianMsgrepo;

	/**
	 *
	 * Creating a group or clinician's
	 *
	 */
	public ClinicianGroups createGroupOrClinicians(ClinicianGroups group) {

		return cliniciangroupsrepo.save(group);
	}

	/**
	 *
	 * Fetching the group or node id details
	 *
	 */
	public Optional<ClinicianGroups> fetchGroupOrCliniciansData(Long id) {

		return cliniciangroupsrepo.findById(id);
	}

	/**
	 *
	 * Updating a group or clinician's
	 *
	 */
	public ClinicianGroups editGroupOrClinicians(Long id, ClinicianGroups group) {

		Optional<ClinicianGroups> fetchedGrouporCliniciansData = cliniciangroupsrepo.findById(id);

		if (!fetchedGrouporCliniciansData.isEmpty()) {

			ClinicianGroups grpCliniciandata = fetchedGrouporCliniciansData.get();

			grpCliniciandata.setGroupName(group.getGroupName());
			grpCliniciandata.setParentId(group.getParentId());
			grpCliniciandata.setChildIds(group.getChildIds());

			return cliniciangroupsrepo.save(grpCliniciandata);
		} else

			throw new RuntimeException("Group not found");

	}

	/**
	 *
	 * Remove a group or clinician's
	 *
	 */
	public void deleteGroupOrClinicians(Long id) {

		cliniciangroupsrepo.deleteById(id);

	}

	/**
	 *
	 * Fetches all the group details
	 *
	 */
	public List<ClinicianGroups> fetchAllGroups() {

		List<ClinicianGroups> fetchedGrouporCliniciansData = new ArrayList<>();

		fetchedGrouporCliniciansData = cliniciangroupsrepo.findAll();

		if (!org.springframework.util.CollectionUtils.isEmpty(fetchedGrouporCliniciansData)) {

			return fetchedGrouporCliniciansData;

		} else

			return fetchedGrouporCliniciansData;

	}

	/**
	 *
	 * Updating the childId details in the parent post removal of child
	 *
	 */
	public void updateChildinParent(Map<Long, List<Long>> parentIdForUpdate) {

		parentIdForUpdate.forEach((key, val) -> {

			Optional<ClinicianGroups> fetchedGrouporCliniciansData = cliniciangroupsrepo.findById(key);

			if (!fetchedGrouporCliniciansData.isEmpty()) {

				ClinicianGroups grpCliniciandata = fetchedGrouporCliniciansData.get();

				grpCliniciandata.setChildIds(val);

				cliniciangroupsrepo.save(grpCliniciandata);
			}

		});

	}

	/**
	 *
	 * Saving Messages to Db
	 *
	 */
	public String saveSentMessages(String message) {

		ClinicianMsgAuditLog clinicianMsgAuditLog = new ClinicianMsgAuditLog();

		clinicianMsgAuditLog.setMessage(message);
		clinicianMsgAuditLog.setDate(LocalDate.now());
		clinicianMsgrepo.save(clinicianMsgAuditLog);
		return "success";
	}

}
