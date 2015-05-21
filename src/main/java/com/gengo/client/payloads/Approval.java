package com.gengo.client.payloads;

import org.json.JSONException;
import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.enums.Rating;
import com.gengo.client.enums.Tier;
import com.gengo.client.exceptions.GengoException;

/**
 * Job approval payload
 */
public class Approval extends JobUpdate
{
    private String feedbackTranslator;
    private String feedbackGengo;
    private boolean isFeedbackPublic;
    private Rating ratingTime;
    private Rating ratingQuality;
    private Rating ratingResponse;

    /**
     * @param jobId The job ID
     * @param feedbackForTranslator Feedback for the translator
     * @param feedbackForGengo Feedback for Gengo
     * @param feedbackIsPublic Whether the src/tgt text & feedback can be shared publicly
     * @param ratingTime Rating of the translation time/speed
     * @param ratingQuality Rating of the translation quality
     * @param ratingResponse Rating of the translator responsiveness
     */
    public Approval(int jobId, String feedbackTranslator, String feedbackGengo, boolean isFeedbackPublic,
            Rating ratingTime, Rating ratingQuality, Rating ratingResponse)
    {
        super(jobId);
        init(feedbackTranslator, feedbackGengo, isFeedbackPublic, ratingTime, ratingQuality, ratingResponse);
    }

    /**
     * @deprecated {@link Approval#Approval(int, String, String, boolean, Rating, Rating, Rating)}
     */
    public Approval(int jobId,
            String feedbackTranslator, String feedbackGengo, boolean isFeedbackPublic, Rating rating)
    {
        this(jobId, feedbackTranslator, feedbackGengo, isFeedbackPublic, rating, rating, rating);
    }

    /**
     * @deprecated {@link Approval#Approval(int, String, String, boolean, Rating, Rating, Rating)}
     */
    public Approval(String lc_src, String lc_tgt, String body_src, Tier tier,
            String feedbackTranslator, String feedbackGengo, boolean isFeedbackPublic, Rating rating)
    {
        super(lc_src, lc_tgt, body_src, tier);
        init(feedbackTranslator, feedbackGengo, isFeedbackPublic, rating, rating, rating);
    }

    private void init(String feedbackTranslator, String feedbackGengo, boolean isFeedbackPublic, Rating ratingTime,
            Rating ratingQuality, Rating ratingResponse)
    {
        this.feedbackTranslator = feedbackTranslator;
        this.feedbackGengo = feedbackGengo;
        this.isFeedbackPublic = isFeedbackPublic;
        this.ratingTime = ratingTime;
        this.ratingQuality = ratingQuality;
        this.ratingResponse = ratingResponse;
    }

    /**
     * Create a JSONObject representing this rejection
     * @return the JSONObject created
     * @throws GengoException
     */
    public JSONObject toJSONObject() throws GengoException
    {
        JSONObject id = super.toJSONObject();
        try
        {
            id.put("for_translator", feedbackTranslator);
            id.put("for_mygengo", feedbackGengo);
            id.put("public", isFeedbackPublic ? GengoClient.MYGENGO_TRUE : GengoClient.MYGENGO_FALSE);
            if (ratingTime != null) {
                id.put("rating_time", ratingTime.toString());
            }
            if (ratingQuality != null) {
                id.put("rating_quality", ratingQuality.toString());
            }
            if (ratingResponse != null) {
                id.put("rating_response", ratingResponse.toString());
            }
            return id;
        }
        catch (JSONException e)
        {
            throw new GengoException("Could not create JSONObject", e);
        }
    }
}
