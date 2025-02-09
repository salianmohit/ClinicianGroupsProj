/**
 * 
 */
package com.eg.clinician.jms.consumer;

import org.springframework.jms.annotation.JmsListener;

/**
 * @author Mohit.Salian
 *
 *         Mainly Listener for consuming messages from queue
 */
public class ClinicianJmsConsumer {

	@JmsListener(destination = "eg-CreateGrp-msgQueue")
	public void receiveCreateGrpMessage(String message) {

		System.out.println("Create Group message consumed" + message);
	}

	@JmsListener(destination = "eg-EditGrp-msgQueue")
	public void receiveEditGrpMessage(String message) {

		System.out.println("Edit Group message consumed" + message);

	}

	@JmsListener(destination = "eg-DeleteGrp-msgQueue")
	public void receiveDeleteGrpMessage(String message) {

		System.out.println("Delete Group message consumed" + message);

	}
}
