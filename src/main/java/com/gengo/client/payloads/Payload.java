package com.gengo.client.payloads;

import org.json.JSONObject;

import com.gengo.client.exceptions.GengoException;

public abstract class Payload
{

    /**
     * Create a JSONObject representing this payload object
     * @return the JSONObject created
     * @throws GengoException
     */
    public abstract JSONObject toJSONObject() throws GengoException;
    
}
