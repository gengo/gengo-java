package com.gengo.client.enums;

/**
 * Job status used in GET method.
 * Key words not to be supported in filter are commented out.
 */
public enum JobStatus {
	//QUEUED,
	AVAILABLE,
	PENDING,
	REVIEWABLE,
	APPROVED,
	//REVISING,
	REJECTED,
	CANCELED
	//,HOLD
}
