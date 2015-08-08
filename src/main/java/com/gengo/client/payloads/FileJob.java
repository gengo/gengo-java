package com.gengo.client.payloads;

import org.json.JSONException;
import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.enums.Tier;
import com.gengo.client.exceptions.GengoException;

/**
 * A file job payload
 */
public class FileJob extends Payload
{
    /** Maximum comment length in bytes. */
    public static final int MAX_COMMENT_LENGTH = 1024;

    public static final String FLAG_TRUE = GengoClient.MYGENGO_TRUE;

    /* Required fields */
    private String slug;
    private String fileKey;
    private String sourceLanguageCode;
    private String targetLanguageCode;
    private Tier tier;

    /* Optional fields */
    private boolean forceNewTranslation;
    private String comment;
    private boolean usePreferredTranslators;
    private String callbackUrl;
    private boolean autoApprove;
    private String customData;
    private String tone;
    private String purpose;
    
    private String identifier;

    /**
     * Create a file job with a slug string.
     * @param slug short string to summarize this job
     * @param fileKey the key for the file in the file map passed to determineTranslationCost
     * @param sourceLanguageCode source language code
     * @param targetLanguageCode target language code
     * @param tier translation tier
     */
    public FileJob(String slug, String fileKey, String sourceLanguageCode, String targetLanguageCode, Tier tier)
    {
        this.slug = slug;
        this.fileKey = fileKey;
        this.sourceLanguageCode = sourceLanguageCode;
        this.targetLanguageCode = targetLanguageCode;
        this.tier = tier;
    }

    /**
     * Create a translation job with no slug string.
     * @param fileKey the key for the file in the file map passed to determineTranslationCost
     * @param sourceLanguageCode source language code
     * @param targetLanguageCode target language code
     * @param tier translation tier
     */
    public FileJob(String fileKey, String sourceLanguageCode, String targetLanguageCode, Tier tier)
    {
        this("0", fileKey, sourceLanguageCode, targetLanguageCode, tier);
    }


    public String getSlug()
    {
        return slug;
    }
    public void setSlug(String slug)
    {
        this.slug = slug;
    }
    public String getSourceLanguageCode()
    {
        return sourceLanguageCode;
    }
    public void setSourceLanguageCode(String sourceLanguageCode)
    {
        this.sourceLanguageCode = sourceLanguageCode;
    }
    public String getTargetLanguageCode()
    {
        return targetLanguageCode;
    }
    public void setTargetLanguageCode(String targetLanguageCode)
    {
        this.targetLanguageCode = targetLanguageCode;
    }
    public Tier getTier()
    {
        return tier;
    }
    public void setTier(Tier tier)
    {
        this.tier = tier;
    }
    public boolean isForceNewTranslation()
    {
        return forceNewTranslation;
    }
    public void setForceNewTranslation(boolean forceNewTranslation)
    {
        this.forceNewTranslation = forceNewTranslation;
    }
    public String getComment()
    {
        return comment;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    public boolean isUsePreferredTranslators()
    {
        return usePreferredTranslators;
    }
    public void setUsePreferredTranslators(boolean usePreferredTranslators)
    {
        this.usePreferredTranslators = usePreferredTranslators;
    }
    public String getCallbackUrl()
    {
        return callbackUrl;
    }
    public void setCallbackUrl(String callbackUrl)
    {
        this.callbackUrl = callbackUrl;
    }
    public boolean isAutoApprove()
    {
        return autoApprove;
    }
    public void setAutoApprove(boolean autoApprove)
    {
        this.autoApprove = autoApprove;
    }
    public String getCustomData()
    {
        return customData;
    }
    public void setCustomData(String data) throws GengoException
    {
        if (data.length() > MAX_COMMENT_LENGTH)
        {
            throw new GengoException("Maximum custom data is " + MAX_COMMENT_LENGTH + " bytes");
        }
        customData = new String(data);
    }
    public String getTone() {
    	return this.tone;
    }
    public void setTone(String tone) {
    	this.tone = tone;
    }
    public String getPurpose() {
    	return this.purpose;
    }
    public void setPurpose(String purpose) {
    	this.purpose = purpose;
    }
    public String getIdentifier() {
    	return identifier;
    }

    public void setIdentifier(String identifier) {
    	this.identifier = identifier;
    }

	/** Utility method */
    private boolean isNullOrEmpty(String str)
    {
        return (null == str || str.length() == 0);
    }

	/**
	 * Create a JSONObject representing this job
	 * @return the JSONObject created
	 * @throws GengoException
	 */
	public JSONObject toJSONObject() throws GengoException
	{
		if (isNullOrEmpty(this.fileKey)) throw new GengoException("Invalid file job - fileKey is required.");
		if (isNullOrEmpty(this.sourceLanguageCode)) throw new GengoException("Invalid translation job - sourceLanguageCode is required.");
		if (isNullOrEmpty(this.targetLanguageCode)) throw new GengoException("Invalid translation job - targetLanguageCode is required.");

		JSONObject job = new JSONObject();
		try
		{
			job.put("file_key", this.fileKey);
			job.put("type", "file");
			job.put("lc_src", this.sourceLanguageCode);
			job.put("lc_tgt", this.targetLanguageCode);
			job.put("tier", this.tier.toString().toLowerCase());
			job.put("slug", this.slug);

			if (this.forceNewTranslation)
			{
				job.put("force", FLAG_TRUE);
			}
			if (!isNullOrEmpty(comment))
			{
				job.put("comment", this.comment);
			}
			if (this.usePreferredTranslators)
			{
				job.put("use_preferred", FLAG_TRUE);
			}
			if (!isNullOrEmpty(this.callbackUrl))
			{
				job.put("callback_url", this.callbackUrl);
			}
			if (this.autoApprove)
			{
				job.put("auto_approve", FLAG_TRUE);
			}
			if (null != this.customData && this.customData.length() > 0)
			{
				job.put("custom_data", this.customData);
			}
			if (!this.isNullOrEmpty(this.tone)) {
				job.put("tone", this.tone);
			}
			if (!this.isNullOrEmpty(this.purpose)) {
				job.put("purpose", this.purpose);
			}
			if (!isNullOrEmpty(this.identifier))
			{
				job.put("identifier", this.identifier);
			}
		}
		catch (JSONException e)
		{
			throw new GengoException("Could not create JSONObject for FileJob", e);
		}
		return job;
	}
}
