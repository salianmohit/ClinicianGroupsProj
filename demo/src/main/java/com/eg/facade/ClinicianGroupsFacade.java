/**
 * 
 */
package com.eg.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;

import com.eg.entity.ClinicianGroups;
import com.eg.service.ClinicianGroupsService;

/**
 * @author Mohit.Salian
 * @param <E>
 *
 */
public class ClinicianGroupsFacade {

	@Autowired
	ClinicianGroupsService clinicianGroupsService;

	public String removeGrouporClinicians(Long id) {

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
										e.getChildIds().stream().filter(a -> Objects.equals(groupData.getGroupId(), a))
												.collect(Collectors.toList()));
							}
						});
						// Update the updated child for the parent id
						clinicianGroupsService.updateChildinParent(parentIdForUpdate);

					}

				}
			}

			return "Removed the node or group successfully";

		} else {

			return "Unable to delete the node or group as it doesn't exist in DB";

		}

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
