package com.gengo.client;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gengo.client.enums.HttpMethod;
import com.gengo.client.enums.Rating;
import com.gengo.client.enums.RejectReason;
import com.gengo.client.exceptions.GengoException;
import com.gengo.client.payloads.Approval;
import com.gengo.client.payloads.FileJob;
import com.gengo.client.payloads.JobUpdate;
import com.gengo.client.payloads.Payload;
import com.gengo.client.payloads.Rejection;
import com.gengo.client.payloads.Revision;
import com.gengo.client.payloads.TranslationJob;
import com.gengo.client.payloads.Payloads;

/**
 * A Java client for the Gengo.com translation API.
 * This client depends on the JSON in Java library available at:
 *   http://json.org/java/
 */
public class GengoClient extends JsonHttpApi
{
    private static final String STANDARD_BASE_URL = "http://api.gengo.com/v2/";
    private static final String SANDBOX_BASE_URL = "http://api.sandbox.gengo.com/v2/";

    /** Strings used to represent TRUE and FALSE in requests */
    public static final String MYGENGO_TRUE = "1";
    public static final String MYGENGO_FALSE = "0";

    private String baseUrl = STANDARD_BASE_URL;

    /**
     * Initialize the client.
     * @param publicKey your Gengo.com public API key
     * @param privateKey your Gengo.com private API key
     */
    public GengoClient(String publicKey, String privateKey)
    {
        this(publicKey, privateKey, false);
    }

    /**
     * Initialize the client with the option to use the sandbox.
     * @param publicKey your Gengo.com public API key
     * @param privateKey your Gengo.com private API key
     * @param useSandbox true to use the sandbox, false to use the live service
     */
    public GengoClient(String publicKey, String privateKey, boolean useSandbox)
    {
        super(publicKey, privateKey);
        setUseSandbox(useSandbox);
    }

    /**
     * @return true iff the client is using the sandbox
     */
    public boolean getUseSandbox()
    {
        return SANDBOX_BASE_URL.equals(baseUrl);
    }

    /**
     * Set the client to use the sandbox or the live service.
     * @param use true iff the client should use the sandbox
     */
    public void setUseSandbox(boolean use)
    {
        baseUrl = use ? SANDBOX_BASE_URL : STANDARD_BASE_URL;
    }

    /**
     * Set a custom base URL. For development testing purposes only.
     * @param baseUrl a custom API base URL
     */
    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    /**
     * Get account statistics.
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getAccountStats() throws GengoException
    {
        String url = baseUrl + "account/stats";
        return call(url, HttpMethod.GET);
    }

    /**
     * Get account balance.
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getAccountBalance() throws GengoException
    {
        String url = baseUrl + "account/balance";
        return call(url, HttpMethod.GET);
    }

    /**
     * Submit a job for translation.
     * @param job a job payload object
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject postTranslationJob(TranslationJob job)
            throws GengoException
    {
        try
        {
            String url = baseUrl + "translate/job";
            JSONObject data = new JSONObject();
            data.put("job", job.toJSONObject());
            return call(url, HttpMethod.POST, data);
        } catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Submit multiple jobs for translation.
     * @param jobs TranslationJob payload objects
     * @param processAsGroup true iff the jobs should be processed as a group
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject postTranslationJobs(List<TranslationJob> jobs, boolean processAsGroup)
    throws GengoException
    {
        try
        {
            String url = baseUrl + "translate/jobs";
            JSONObject data = new JSONObject();
            /* We can safely cast our list of jobs into a list of the payload base type */
            @SuppressWarnings({ "rawtypes", "unchecked" })
            List<Payload> p = (List)jobs; 
            data.put("jobs", (new Payloads(p)).toJSONArray());
            data.put("as_group", processAsGroup ? MYGENGO_TRUE : MYGENGO_FALSE);
            JSONObject rsp = call(url, HttpMethod.POST, data);
            return rsp;
        }
        catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Request revisions for a job.
     * @param id The job ID
     * @param comments Comments for the translator
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject reviseTranslationJob(int id, String comments)
            throws GengoException
    {
        try
        {
            String url = baseUrl + "translate/job/" + id;
            JSONObject data = new JSONObject();
            data.put("action", "revise");
            data.put("comment", comments);
            return call(url, HttpMethod.PUT, data);
        } catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Approve a translation.
     * @param id The job ID
     * @param rating A rating for the translation
     * @param commentsForTranslator Comments for the translator
     * @param commentsForGengo Comments for Gengo
     * @param feedbackIsPublic true iff the feedback can be shared publicly
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject approveTranslationJob(int id, Rating rating,
            String commentsForTranslator, String commentsForGengo,
            boolean feedbackIsPublic) throws GengoException
    {
        try
        {
            String url = baseUrl + "translate/job/" + id;
            JSONObject data = new JSONObject();
            data.put("action", "approve");
            data.put("for_translator", commentsForTranslator);
            data.put("for_gengo", commentsForGengo);
            data.put("public", feedbackIsPublic ? MYGENGO_TRUE : MYGENGO_FALSE);
            data.put("rating", rating.toString());
            return call(url, HttpMethod.PUT, data);
        } catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Reject a translation.
     * @param id the job ID
     * @param reason reason for rejection
     * @param comments comments for Gengo
     * @param captcha the captcha image text
     * @param requeue true iff the job should be passed on to another translator
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject rejectTranslationJob(int id, RejectReason reason,
            String comments, String captcha, boolean requeue)
            throws GengoException
    {
        try
        {
            String url = baseUrl + "translate/job/" + id;
            JSONObject data = new JSONObject();
            data.put("action", "reject");
            data.put("reason", reason.toString().toLowerCase());
            data.put("comment", comments);
            data.put("captcha", captcha);
            data.put("follow_up", requeue ? "requeue" : "cancel");
            return call(url, HttpMethod.PUT, data);
        } catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Get a translation job
     * @param id the job id
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getTranslationJob(int id) throws GengoException
    {
        String url = baseUrl + "translate/job/" + id;
        return call(url, HttpMethod.GET);
    }

    /**
     * Get all translation jobs
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getTranslationJobs() throws GengoException
    {
        String url = baseUrl + "translate/jobs/";
        return call(url, HttpMethod.GET);
    }

    /**
     * Get selected translation jobs
     * @param ids a list of job ids to retrieve
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getTranslationJobs(List<Integer> ids) throws GengoException
    {
        String url = baseUrl + "translate/jobs/";
        url += join(ids, ",");
        return call(url, HttpMethod.GET);
    }

    /**
     * Get translation jobs which were previously submitted as a group
     * @param groupId The group job number for these jobs.
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getGroupJobs(int groupId) throws GengoException
    {
        String url = baseUrl + "translate/jobs/group/";
        url += groupId;
        return call(url, HttpMethod.GET);
    }

    /**
     * Post a comment for a translation job
     * @param id the ID of the job to comment on
     * @param comment the comment
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject postTranslationJobComment(int id, String comment)
            throws GengoException
    {
        try
        {
            String url = baseUrl + "translate/job/" + id + "/comment";
            JSONObject data = new JSONObject();
            data.put("body", comment);
            return call(url, HttpMethod.POST, data);
        }
        catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Get comments for a translation job
     * @param id the job ID
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getTranslationJobComments(int id) throws GengoException
    {
        String url = baseUrl + "translate/job/" + id + "/comments/";
        return call(url, HttpMethod.GET);
    }

    /**
     * Get feedback for a translation job
     * @param id the job ID
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getTranslationJobFeedback(int id) throws GengoException
    {
        String url = baseUrl + "translate/job/" + id + "/feedback";
        return call(url, HttpMethod.GET);
    }

    /**
     * Get all revisions for a translation job
     * @param id the job ID
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getTranslationJobRevisions(int id) throws GengoException
    {
        String url = baseUrl + "translate/job/" + id + "/revisions";
        return call(url, HttpMethod.GET);
    }

    /**
     * Get a specific revision for a translation job
     * @param id the job ID
     * @param revisionId the ID of the revision to retrieve
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getTranslationJobRevision(int id, int revisionId)
            throws GengoException
    {
        String url = baseUrl + "translate/job/" + id + "/revision/"
                + revisionId;
        return call(url, HttpMethod.GET);
    }

    /**
     * Get the preview image for a translated job
     * @param id the job ID
     * @return the image from the server
     * @throws GengoException
     */
    public BufferedImage getTranslationJobPreviewImage(int id) throws GengoException
    {
        String url = baseUrl + "translate/job/" + id + "/preview";
        return getImage(url);
    }

    /**
     * Cancel a translation job. It can only be deleted if it has not been started by a translator.
     * @param id the job ID
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject deleteTranslationJob(int id) throws GengoException
    {
        String url = baseUrl + "translate/job/" + id;
        return call(url, HttpMethod.DELETE);
    }

    /**
     * Cancel translation jobs. They can only be deleted if they have not been started by a translator.
     * @param ids a list of job IDs to delete
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject deleteTranslationJobs(List<Integer> ids) throws GengoException
    {
        try
        {
            String url = baseUrl + "translate/jobs/";
            JSONObject data = new JSONObject();
            data.put("job_ids", ids);
            return call(url, HttpMethod.DELETE, data);
        }
        catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Get a list of supported languages and their language codes.
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getServiceLanguages() throws GengoException
    {
        String url = baseUrl + "translate/service/languages";
        return call(url, HttpMethod.GET);
    }

    /**
     * Get a list of supported language pairs, tiers, and credit prices.
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getServiceLanguagePairs() throws GengoException
    {
        String url = baseUrl + "translate/service/language_pairs";
        return call(url, HttpMethod.GET);
    }

    /**
     * Get a list of supported language pairs, tiers and credit prices for a specific source language.
     * @param sourceLanguageCode the language code for the source language
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getServiceLanguagePairs(String sourceLanguageCode) throws GengoException
    {
        try
        {
            String url = baseUrl + "translate/service/language_pairs";
            JSONObject data = new JSONObject();
            data.put("lc_src", sourceLanguageCode);
            return call(url, HttpMethod.GET, data);
        }
        catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Get a quote for translation jobs.
     * @param jobs Translation job objects to be quoted for
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject determineTranslationCost(Payloads jobs) throws GengoException
    {
        try
        {
            String url = baseUrl + "translate/service/quote/";
            JSONObject data = new JSONObject();
            data.put("jobs", jobs.toJSONArray());
            return call(url, HttpMethod.POST, data);
        } catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Update translation jobs
     * @param action the update action to apply
     * @param approvals the job payload objects
     * @return the response from the server
     * @throws GengoException
     */
    private JSONObject updateTranslationJobs(String action, List<JobUpdate> updates) throws GengoException
    {
        try
        {
            String url = baseUrl + "translate/jobs";
            JSONObject data = new JSONObject();
            data.put("action", action);
            String fieldName = null;
            for (JobUpdate u : updates)
            {
                String g = u.isIdentifiedByJobId() ? "job_ids" : "jobs";
                if (null != fieldName && !fieldName.equals(g))
                {
                    throw new GengoException("All updates in list must be identified in the same way: either by job_id, or by (lc_src, lc_tgt, body_src, tier)");
                }
                else
                {
                    fieldName = g;
                }
            }
            /* We can safely cast into a list of the payload base type */
            @SuppressWarnings({ "rawtypes", "unchecked" })
            List<Payload> p = (List)updates;
            data.put(fieldName, (new Payloads(p)).toJSONArray());
            return call(url, HttpMethod.PUT, data);
        }
        catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Revise translations.
     * @param revisions Revision payload objects
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject reviseTranslationJobs(List<Revision> revisions) throws GengoException
    {
        /* We can safely cast our list of revisions into a list of the payload base type */
        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<JobUpdate> p = (List)revisions;
        return updateTranslationJobs("revise", p);
    }

    /**
     * Reject translations.
     * @param rejections Rejection payload objects
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject rejectTranslationJobs(List<Rejection> rejections) throws GengoException
    {
        /* We can safely cast our list of rejections into a list of the payload base type */
        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<JobUpdate> p = (List)rejections;
        return updateTranslationJobs("reject", p);
    }

    /**
     * Approve translations.
     * @param approvals Approval payload objects
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject approveTranslationJobs(List<Approval> approvals) throws GengoException
    {
        /* We can safely cast our list of approvals into a list of the payload base type */
        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<JobUpdate> p = (List)approvals;
        return updateTranslationJobs("approve", p);
    }

    /**
     * Get translation jobs which were previously submitted together by their order id.
     *
     * @param orderId
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject getOrderJobs(int orderId) throws GengoException
    {
        String url = baseUrl + "translate/order/";
        url += orderId;
        return call(url, HttpMethod.GET);
    }

    /**
     * Get a quote for file jobs.
     * @param jobs Translation job objects to be quoted
     * @param filePaths map of file keys to filesystem paths
     * @return the response from the server
     * @throws GengoException
     */
    public JSONObject determineTranslationCostFiles(List<FileJob> jobs, Map<String, String> filePaths) throws GengoException
    {
        try
        {
            JSONObject theJobs = new JSONObject();

            for (int i = 0; i < jobs.size(); i++) {
                theJobs.put(String.format("job_%s", i), jobs.get(i).toJSONObject());
            }
            String url = baseUrl + "translate/service/quote/file";
            JSONObject data = new JSONObject();
            data.put("jobs", theJobs);

            return httpPostFileUpload(url, data, filePaths);
        } catch (JSONException x)
        {
            throw new GengoException(x.getMessage(), x);
        }
    }

    /**
     * Utility function.
     */
    private String join(Iterable<? extends Object> pColl, String separator)
    {
        Iterator<? extends Object> oIter;
        if (pColl == null || (!(oIter = pColl.iterator()).hasNext()))
        {
            return "";
        }
        StringBuffer oBuilder = new StringBuffer(String.valueOf(oIter.next()));
        while (oIter.hasNext())
        {
            oBuilder.append(separator).append(oIter.next());
        }
        return oBuilder.toString();
    }


}
