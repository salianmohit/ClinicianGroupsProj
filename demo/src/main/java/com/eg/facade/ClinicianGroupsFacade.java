/**
 * 
 */
package com.eg.facade;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.eg.entity.ClinicianGroups;
import com.eg.jms.sender.ClinicianJmsSender;
import com.eg.service.ClinicianGroupsService;

/**
 * @author Mohit.Salian
 * @param <E>
 *
 */
public class ClinicianGroupsFacade {

	@Autowired
	private ClinicianGroupsService clinicianGroupsService;
	
	@Autowired
	private ClinicianJmsSender clinicianJmsSender;
	
	private Date date;

	public ClinicianGroups createGroup(ClinicianGroups groupData) {
		
		//Tracking the Operation in DB for CREATE

		clinicianJmsSender.sendCreateGrpMessage("CREATE_GROUP-"+date);

		return clinicianGroupsService.createGroupOrClinicians(groupData);
	}

	public List<ClinicianGroups> fetchAllGroups() {

		return clinicianGroupsService.fetchAllGroups();
	}

	public ClinicianGroups editGroup(Long id, ClinicianGroups groupData) {

		//Tracking the Operation in DB for EDIT

		clinicianJmsSender.sendEditGrpMessage("EDIT_GROUP-"+groupData.getGroupId()+"-"+groupData.getParentId()+"-"+date);

		return clinicianGroupsService.editGroupOrClinicians(id, groupData);
	}

	public String removeGrouporClinicians(Long id) {

		try {
			Optional<ClinicianGroups> groupDataFromDb = clinicianGroupsService.fetchGroupOrCliniciansData(id);

			if (groupDataFromDb.isPresent()) {

				ClinicianGroups groupData = groupDataFromDb.get();

				// Deleting the Parent group along with its child Id
				if (Objects.isNull(groupData.getParentId())) {

					// Deleting first the child groups or nodes.
					if (org.springframework.util.CollectionUtils.isEmpty(groupData.getChildIds())) {
						groupData.getChildIds().forEach(e -> clinicianGroupsService.deleteGroupOrClinicians(e));

						// Deleting the parent group
						clinicianGroupsService.deleteGroupOrClinicians(groupData.getGroupId());
					}

				} else {

					// If only the child group needs to be deleted and post that updating the parent
					// with updated child details

					// For the child which doesn't have sub child
					if (org.springframework.util.CollectionUtils.isEmpty(groupData.getChildIds())) {

						removeChildWithoutSubChild(groupData.getGroupId());

					} else {

						// For the child which has sub child
						if (!Objects.isNull(groupData.getParentId())) {

							// Removing the child node and the sub child node
							removeParentAndChild(groupData);

							// For updating the updated child for the parent id

							List<ClinicianGroups> allGroupsData = clinicianGroupsService.fetchAllGroups();

							Map<Long, List<Long>> parentIdForUpdate = new HashMap<Long, List<Long>>();
							// Filter the parent group for which the child exist
							allGroupsData.forEach(e -> {

								if (e.getChildIds().contains(groupData.getGroupId())) {
									parentIdForUpdate.put(e.getGroupId(),
											e.getChildIds().stream()
													.filter(a -> Objects.equals(groupData.getGroupId(), a))
													.collect(Collectors.toList()));
								}
							});
							// Update the updated child for the parent id
							clinicianGroupsService.updateChildinParent(parentIdForUpdate);

						}

					}
				}
				//Tracking the Operation in DB for DELETE
				clinicianJmsSender.sendDeleteGrpMessage("DELETE_GROUP-"+groupDataFromDb.get().getGroupId()+"-"+groupDataFromDb.get().getParentId()+"-"+date);

				return "Removed the node or group successfully";

			} else {

				return "Unable to delete the node or group as it doesn't exist in DB";

			}
		} catch (Exception e) {

			return "Unable to delete the node or group due to Exception :" + e.getMessage();
		}
	}

	public Optional<ClinicianGroups> fetchSingleGroup(Long id) {

		return clinicianGroupsService.fetchGroupOrCliniciansData(id);
	}

	public String removeChildWithoutSubChild(Long id) {

		// Remove the child row first
		clinicianGroupsService.deleteGroupOrClinicians(id);
		// Fetch all the groups where the group was present as a child for removing its
		// details from child id column
		List<ClinicianGroups> allGroupsData = clinicianGroupsService.fetchAllGroups();

		Map<Long, List<Long>> parentIdForUpdate = new HashMap<Long, List<Long>>();
		// Filter the parent group for which the child exist
		allGroupsData.forEach(e -> {

			if (e.getChildIds().contains(id)) {
				parentIdForUpdate.put(e.getGroupId(),
						e.getChildIds().stream().filter(a -> Objects.equals(id, a)).collect(Collectors.toList()));
			}
		});
		// Update the updated child if for the parent id
		clinicianGroupsService.updateChildinParent(parentIdForUpdate);

		return "Success";
	}

	public String removeParentAndChild(ClinicianGroups groupDataClinician) {

		// Deleting first the child groups or nodes.
		if (org.springframework.util.CollectionUtils.isEmpty(groupDataClinician.getChildIds())) {
			groupDataClinician.getChildIds().forEach(e -> clinicianGroupsService.deleteGroupOrClinicians(e));

			// Deleting the parent group
			clinicianGroupsService.deleteGroupOrClinicians(groupDataClinician.getGroupId());

		}
		return "Success";
	}
}
