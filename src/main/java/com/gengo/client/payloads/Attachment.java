package com.gengo.client.payloads;

import org.json.JSONObject;
import org.json.JSONException;

import com.gengo.client.exceptions.GengoException;


public class Attachment extends Payload
{
    private JSONObject data;

    private static final String URL = "url";
    private static final String FILENAME = "filename";
    private static final String MIMETYPE = "mime_type";

    /**
     * @param url full URL to a resource i.e. http://example.com/image.jpg
     * @param filename name of the file i.e. image.jpg
     * @param mimeType mime Type of the resource i.e. image/jpg
     * @throws GengoException
     */
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

    /**
     * @param url full URL to a resource i.e. http://example.com/image.jpg
     * @return void
     * @throws GengoException
     */
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

    /**
     * @return String
     * @throws GengoException
     */
    public String getURL()
        throws GengoException
    {
        try {
            return this.data.getString(Attachment.URL);
        }
        catch (JSONException e) {
            throw new GengoException("Error retrieving attachment url.", e);
        }
    }

    /**
     * @param filename name of the file i.e. image.jpg
     * @return void
     * @throws GengoException
     */
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

    /**
     * @throws GengoException
     * @return String
     */
    public String getFilename()
        throws GengoException
    {
        try {
            return this.data.getString(Attachment.FILENAME);
        }
        catch (JSONException e) {
            throw new GengoException("Error retrieving attachment filename.", e);
        }
    }

    /**
     * @param mimeType mime Type of the resource i.e. image/jpg
     * @return void
     * @throws GengoException
     */
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

    /**
     * @return String
     * @throws GengoException
     */
    public String getMimeType()
        throws GengoException
    {
        try {
            return this.data.getString(Attachment.MIMETYPE);
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
