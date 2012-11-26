package com.gengo.client;

import java.lang.Integer;
import java.lang.System;
import java.lang.Thread;
import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.List;

import com.gengo.client.GengoClient;
import com.gengo.client.exceptions.GengoException;
import com.gengo.client.payloads.TranslationJob;
import com.gengo.client.enums.Tier;

import org.json.JSONObject;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

public class JobsTest {

    private String public_key = System.getenv("GENGO_PUBKEY");
    private String private_key = System.getenv("GENGO_PRIVKEY");

    @Test
    public void testPostJobsText() throws GengoException, JSONException, InterruptedException {
        // POST a text job
        GengoClient Gengo = new GengoClient(this.public_key, this.private_key, true);
        List<TranslationJob> jobList = new ArrayList<TranslationJob>();
        TranslationJob job = new TranslationJob("java client test", "This is a short story.", "en", "ja", Tier.STANDARD);
        job.setForceNewTranslation(true);
        jobList.add(job);
        JSONObject postResp = Gengo.postTranslationJobs(jobList, true);
        // Make assertions on POST response
        Assert.assertEquals(postResp.getString("opstat"), "ok");
        Assert.assertTrue(postResp.has("response"));
        JSONObject postResponse = postResp.getJSONObject("response");
        Assert.assertEquals(postResponse.get("job_count"), 1);
        Assert.assertTrue(postResponse.has("credits_used"));
        Assert.assertTrue(postResponse.has("order_id"));
        String orderId = postResponse.getString("order_id");

        // GET order
        Thread.sleep(3000);
        JSONObject getOrderResp = Gengo.getOrderJobs(Integer.parseInt(orderId));
        // Make assertions on GET response
        Assert.assertEquals(getOrderResp.getString("opstat"), "ok");
        Assert.assertTrue(getOrderResp.has("response"));
        JSONObject getOrderResponse = getOrderResp.getJSONObject("response");
        String jobId = getOrderResponse.getJSONObject("order").getJSONArray("jobs_available").getString(0);

        // GET job
        JSONObject getJobResp = Gengo.getTranslationJob(Integer.parseInt(jobId));
        Assert.assertEquals(getJobResp.getString("opstat"), "ok");
        Assert.assertTrue(getJobResp.has("response"));
        JSONObject getJobResponse = getJobResp.getJSONObject("response");
        String bodySrc = getJobResponse.getJSONObject("job").getString("body_src");
        Assert.assertEquals(bodySrc, "This is a short story.");
    }
}
