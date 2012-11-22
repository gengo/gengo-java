package com.gengo.client.payloads;

import org.json.JSONException;
import org.json.JSONObject;

import com.gengo.client.enums.Tier;
import com.gengo.client.exceptions.GengoException;

/**
 * Abstract base class for update payloads
 */
public abstract class JobUpdate extends Payload
{
    private int jobId = 0;
    
    private String lcSrc;
    private String lcTgt;
    private String bodySrc;
    private Tier tier;

    /**
     * Create a job update payload identified by job ID
     * @param jobId
     */
    public JobUpdate(int jobId)
    {
        this.jobId = jobId;
    }
    
    /**
     * Create a job update payload identified by source and target languages, text body and tier
     * @param lcSrc
     * @param lcTgt
     * @param bodySrc
     * @param tier
     */
    public JobUpdate(String lcSrc, String lcTgt, String bodySrc, Tier tier)
    {
        this.jobId = 0;
        this.lcSrc = lcSrc;
        this.lcTgt = lcTgt;
        this.bodySrc = bodySrc;
        this.tier = tier;
    }
    
    /**
     * Determine whether this update identifies job by job ID or not
     * @return true if the job is identified by job ID, false if indentified by (lcSrc, lcTgt, bodySrc, tier)
     */
    public boolean isIdentifiedByJobId()
    {
        return 0 != jobId;
    }
    
    /**
     * Create a JSONObject representing this update
     * @return the JSONObject created
     * @throws GengoException
     */
    public JSONObject toJSONObject() throws GengoException
    {
        JSONObject u = new JSONObject();
        try
        {
            if (0 != jobId)
            {
                u.put("job_id", jobId);
            }
            else
            {
                u.put("body_src", bodySrc);
                u.put("lc_src", lcSrc);
                u.put("lc_tgt", lcTgt);
                u.put("tier", tier);
            }
        }
        catch (JSONException e)
        {
            throw new GengoException("Could not create JSONObject", e);
        }
        return u;
    }

}
