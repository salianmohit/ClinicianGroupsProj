package com.eg.clinician.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eg.clinician.entity.ClinicianGroups;
import com.eg.clinician.facade.ClinicianGroupsFacade;

/**
 * @author Mohit.Salian
 *
 */
@RestController
@RequestMapping("/api/v1")
public class ClinicianGroupsController {

	@Autowired
	private ClinicianGroupsFacade clinicianGroupsFacade;

	/**
	 * Create a group or clinicians
	 * 
	 * @param as the GroupDetails
	 * 
	 *
	 */
	@PostMapping("/create")
	public ResponseEntity<String> createGroup(@RequestBody ClinicianGroups groupData) {

		clinicianGroupsFacade.createGroup(groupData);

		return ResponseEntity.ok("Group Successfully Created");

	}

	/**
	 * Fetches all the group or clinicians details
	 * 
	 *
	 */
	@GetMapping("/getAllGroups")
	public ResponseEntity<List<ClinicianGroups>> fetchAllGroups() {

		List<ClinicianGroups> groupData = clinicianGroupsFacade.fetchAllGroups();
		return ResponseEntity.ok(groupData);

	}

	/**
	 * Updates the group or Clinician's details
	 * 
	 * @param the group id and the GroupDetails
	 *
	 */
	@PutMapping("/editGroup")
	public ResponseEntity<ClinicianGroups> editGroup(@RequestParam Long id, @RequestBody ClinicianGroups groupData) {

		ClinicianGroups groupDataUpdated = clinicianGroupsFacade.editGroup(id, groupData);
		return ResponseEntity.ok(groupDataUpdated);

	}

	/**
	 * Delete the group or Clinician's node
	 * 
	 * @param the group id
	 *
	 */
	@DeleteMapping("/deleteGroup")
	public ResponseEntity<String> deleteGroup(@RequestParam Long id) {

		clinicianGroupsFacade.removeGrouporClinicians(id);
		return ResponseEntity.ok("Group Successfully Deleted");

	}

	/**
	 * Fetches the an group or Clinician's node details
	 * 
	 * @param the group id
	 *
	 */
	@GetMapping("/fetchGroupById")
	public ResponseEntity<Optional<ClinicianGroups>> fetchSingleGroup(@RequestParam Long id) {

		Optional<ClinicianGroups> groupData = clinicianGroupsFacade.fetchSingleGroup(id);
		return ResponseEntity.ok(groupData);
	}

}
