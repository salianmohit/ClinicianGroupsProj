/**
 * 
 */
package com.eg.clinician.jms.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.eg.clinician.service.ClinicianGroupsService;

/**
 * @author Mohit.Salian
 *
 *         Mainly Producer for sending messages to queue
 */
@Component
public class ClinicianJmsSender {

	@Autowired
	private JmsTemplate jmstemplate;

	@Autowired
	private ClinicianGroupsService clinicianGroupsService;

	public void sendCreateGrpMessage(String message) {

		jmstemplate.convertAndSend("eg-CreateGrp-msgQueue", message);

		clinicianGroupsService.saveSentMessages(message);

	}

	public void sendEditGrpMessage(String message) {

		jmstemplate.convertAndSend("eg-EditGrp-msgQueue", message);

		clinicianGroupsService.saveSentMessages(message);

	}

	public void sendDeleteGrpMessage(String message) {

		jmstemplate.convertAndSend("eg-DeleteGrp-msgQueue", message);

		clinicianGroupsService.saveSentMessages(message);

	}

}
