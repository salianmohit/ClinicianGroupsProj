# ClinicianGroupsProj
JMS queueing demo

1)Ran the application from IDE, you can also run from commandline with below commandline

mvn spring-boot:run


2)Bring up the queue , download the Apache ActiveMq from below URL , 

https://activemq.apache.org/components/classic/download/

unzip then go to bin directory and run the below command from command line

activemq start

3)Queues can be viewed or monitored in the below URL once the Messaging service is up.

 http://127.0.0.1:8161/
 
4) Assumptions

Frontend we would be sending the parent id when the child is created.

Table clinician_groups , columns - GroupId(primary key)
                                   GroupName (not null)
								   Parent Id
								   Child Ids (List)
								   
Table clinician_msg_audit_log, columns - auditId (primary key)
                                         message
										 date
                                       