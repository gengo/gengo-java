package com.gengo.client.exceptions;

@SuppressWarnings("serial")
/**
 * Thrown when an error response is received from the Gengo server
 */
public class ErrorResponseException extends GengoException
{
	public static final int AUTHENTICATION_FAILED = 1000;
	public static final int API_SIG_MISSING = 1100;
	public static final int API_KEY_MISSING = 1150;
	public static final int TIMESTAMP_MISSING = 1200;
	public static final int TIMESTAMP_NOT_NUMERIC = 1201;
	public static final int DATA_MISSING = 1250;
	public static final int DATA_INVALID = 1251;
	public static final int JOB_TYPE_MISSING = 1300;
	public static final int JOB_TYPE_UNSUPPORTED = 1301;
	public static final int BODY_SRC_MISSING = 1350;
	public static final int LC_SRC_MISSING = 1400;
	public static final int LC_TGT_MISSING = 1450;
	public static final int TIER_MISSING = 1500;
	public static final int LANGUAGE_SERVICE_UNSUPPORTED = 1551;
	public static final int CALLBACK_URL_INVALID = 1601;
	public static final int AUTO_APPROVE_INVALID = 1651;
	public static final int USE_PREFERRED_INVALID = 1701;
	public static final int CUSTOM_DATA_TOO_LARGE = 1751;
	public static final int JOBS_MISSING = 1800;
	public static final int AS_GROUP_INVALID = 1851;
	public static final int PROCESS_INVALID = 1901;
	public static final int JOBS_NOT_GROUPABLE = 1950;
	public static final int SINGLE_JOB_NOT_GROUPABLE = 1951;
	public static final int BODY_MISSING = 2000;
	public static final int JOB_ID_MISSING = 2050;
	public static final int JOB_ACCESS_UNAUTHORIZED = 2100;
	public static final int REVISION_ID_REQUIRED = 2150;
	public static final int REVISION_ACCESS_UNAUTHORIZED = 2200;
	public static final int JOB_NOT_REVIEWABLE = 2250;
	public static final int JOB_CANNOT_BE_PURCHASED = 2251;
	public static final int JOB_CANNOT_BE_CANCELLED = 2252;
	public static final int COMMENT_REQUIRED = 2300;
	public static final int REASON_REQUIRED = 2350;
	public static final int CAPTCHA_REQUIRED = 2400;
	public static final int CAPTCHA_INVALID = 2401;
	public static final int CAPTCHA_FOLLOWUP_INVALID = 2450;
	public static final int RATING_REQUIRED = 2500;
	public static final int SERVICES_API_ERROR_CODES_RATING_RESTRICTION = 2501;
	public static final int SERVICES_API_ERROR_CODES_RATING_RESTRICTION_2 = 2502;
	public static final int GROUP_ID_REQUIRED = 2550;
	public static final int GROUP_ACCESS_UNAUTHORIZED = 2600;
	public static final int JOB_STATUS_INVALID = 2650;
	public static final int CREDITS_INSUFFICIENT = 2700;
    public static final int UNAUTHORIZED_ORDER_ACCESS = 2750;


	private int code;

	public ErrorResponseException(String e, int code)
	{
		super(String.format("%d: %s", code, e));
		this.code = code;
	}

	/**
	 * @return the error code returned by the server
	 */
	public int getErrorCode()
	{
		return code;
	}

}
