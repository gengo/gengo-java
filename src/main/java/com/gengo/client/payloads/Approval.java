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
    private Rating rating;
    
    public Approval(int jobId,
            String feedbackTranslator, String feedbackGengo, boolean isFeedbackPublic, Rating rating)
    {
        super(jobId);
        init(feedbackTranslator, feedbackGengo, isFeedbackPublic, rating);        
    }
    
    public Approval(String lc_src, String lc_tgt, String body_src, Tier tier,
            String feedbackTranslator, String feedbackGengo, boolean isFeedbackPublic, Rating rating)
    {
        super(lc_src, lc_tgt, body_src, tier);
        init(feedbackTranslator, feedbackGengo, isFeedbackPublic, rating);
    }

    private void init(String feedbackTranslator, String feedbackGengo, boolean isFeedbackPublic, Rating rating)
    {
        this.feedbackTranslator = feedbackTranslator;
        this.feedbackGengo = feedbackGengo;
        this.isFeedbackPublic = isFeedbackPublic;
        this.rating = rating;
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
            id.put("for_gengo", feedbackGengo);
            id.put("public", isFeedbackPublic ? GengoClient.MYGENGO_TRUE : GengoClient.MYGENGO_FALSE);
            id.put("rating", rating.toString());
            return id;
        }
        catch (JSONException e)
        {
            throw new GengoException("Could not create JSONObject", e);
        }
    }
}
