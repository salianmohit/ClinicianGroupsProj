/**
 * 
 */
/**
 * @author Mohit.Salian
 *
 */
package com.eg.clinician.entity;

import java.util.List;

import jakarta.persistence.*;

/**
 * @author Mohit.Salian
 *
 *         Entity class for creation of Table , primary key and respective
 *         columns in the tables.
 */
@Entity
@Table
public class ClinicianGroups {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupId;

	@Column(nullable = false)
	private String groupName;

	@Column
	private Long parentId;

	@Column
	private List<Long> childIds;

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the childIds
	 */
	public List<Long> getChildIds() {
		return childIds;
	}

	/**
	 * @param childIds the childIds to set
	 */
	public void setChildIds(List<Long> childIds) {
		this.childIds = childIds;
	}

}
