package com.gengo.client.payloads;

import org.json.JSONException;
import org.json.JSONObject;

import com.gengo.client.enums.Tier;
import com.gengo.client.exceptions.GengoException;

/**
 * Job revision payload
 */
public class Revision extends JobUpdate
{
    private String comment;
    
    public Revision(int jobId, String comment)
    {
        super(jobId);
        init(comment);        
    }
    
    public Revision(String lc_src, String lc_tgt, String body_src, Tier tier, String comment)
    {
        super(lc_src, lc_tgt, body_src, tier);
        init(comment);
    }

    private void init(String comment)
    {
        this.comment = comment;
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
            id.put("comment", comment);
            return id;
        }
        catch (JSONException e)
        {
            throw new GengoException("Could not create JSONObject", e);
        }
    }
}
