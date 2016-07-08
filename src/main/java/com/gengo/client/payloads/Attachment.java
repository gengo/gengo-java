package com.gengo.client.payloads;

import org.json.JSONObject;
import org.json.JSONException;

import com.gengo.client.exceptions.GengoException;


public class Attachment extends Payload
{
    protected JSONObject data;

    protected static final String URL = "url";
    protected static final String FILENAME = "filename";
    protected static final String MIMETYPE = "mime_type";

    public Attachment(String url, String filename, String mimeType)
        throws GengoException
    {
        try {
            this.data = new JSONObject();
            this.data.put(Attachment.URL, url);
            this.data.put(Attachment.FILENAME, filename);
            this.data.put(Attachment.MIMETYPE, mimeType);
        }
        catch (JSONException e) {
            throw new GengoException("Failed to create attachment.", e);
        }
    }

    public Attachment()
    {
        this.data = new JSONObject();
    }

    public void setURL(String url)
        throws GengoException
    {
        try {
            this.data.put(Attachment.URL, url);
        }
        catch (JSONException e) {
            throw new GengoException("Failed to set attachment url.", e);
        }
    }

    public String getURL()
        throws GengoException
    {
        try {
            return (String)this.data.get(Attachment.URL);
        }
        catch (JSONException e) {
            throw new GengoException("Error retrieving attachment url.", e);
        }
    }

    public void setFilename(String filename)
        throws GengoException
    {
        try {
            this.data.put(Attachment.FILENAME, filename);
        }
        catch (JSONException e) {
            throw new GengoException("Failed to set attachment filename.", e);
        }
    }

    public String getFilename()
        throws GengoException
    {
        try {
            return (String)this.data.get(Attachment.FILENAME);
        }
        catch (JSONException e) {
            throw new GengoException("Error retrieving attachment filename.", e);
        }
    }

    public void setMimeType(String mimeType)
        throws GengoException
    {
        try {
            this.data.put(Attachment.MIMETYPE, mimeType);
        }
        catch (JSONException e) {
            throw new GengoException("Failed to set attachment mime type.", e);
        }
    }

    public String getMimeType()
        throws GengoException
    {
        try {
            return (String)this.data.get(Attachment.MIMETYPE);
        }
        catch (JSONException e) {
            throw new GengoException("Error retrieving attachment mime type.", e);
        }
    }

    /**
     * @return the JSONObject created
     * @throws GengoException
     */
    public JSONObject toJSONObject() throws GengoException
    {
        return this.data;
    }
}
