package com.gengo.client;

import java.lang.Integer;
import java.lang.System;
import java.lang.Thread;
import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.gengo.client.GengoClient;
import com.gengo.client.exceptions.GengoException;
import com.gengo.client.payloads.TranslationJob;
import com.gengo.client.payloads.FileJob;
import com.gengo.client.enums.Tier;
import com.gengo.client.payloads.Payloads;

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
        TranslationJob job2 = new TranslationJob("java client test 2", "This is another short story.", "en", "ja", Tier.STANDARD);
        job.setPosition("1");
        job2.setPosition("2");
        job.setForceNewTranslation(true);
        job2.setForceNewTranslation(true);
        jobList.add(job);
        jobList.add(job2);
        JSONObject postResp = Gengo.postTranslationJobs(jobList, true);
        // Make assertions on POST response
        Assert.assertEquals(postResp.getString("opstat"), "ok");
        Assert.assertTrue(postResp.has("response"));
        JSONObject postResponse = postResp.getJSONObject("response");
        Assert.assertEquals(postResponse.get("job_count"), 2);
        Assert.assertTrue(postResponse.has("credits_used"));
        Assert.assertTrue(postResponse.has("order_id"));
        String orderId = postResponse.getString("order_id");

        // GET order
        // We have to sleep to give jobs processor some time to process the jobs,
        // then the IDs should be in jobs_available
        Thread.sleep(10000);
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

    @Test
    public void testPostJobsFiles() throws GengoException, JSONException, InterruptedException {
        GengoClient Gengo = new GengoClient(this.public_key, this.private_key, true);
        Map<String, String> filePaths = new HashMap<String, String>();
        FileJob job1 = new FileJob("someslug", "file_job_1", "en", "ja", Tier.STANDARD);
        FileJob job2 = new FileJob("someslug2", "file_job_2", "en", "ja", Tier.STANDARD);
        filePaths.put("file_job_1", "examples/testfiles/test_file1.txt");
        filePaths.put("file_job_2", "examples/testfiles/test_file2.txt");
        List<FileJob> jobList = new ArrayList<FileJob>();
        jobList.add(job1);
        jobList.add(job2);
        JSONObject rsp = Gengo.determineTranslationCostFiles(jobList, filePaths);
        Assert.assertEquals(rsp.getString("opstat"), "ok");
        Assert.assertTrue(rsp.has("response"));
    }

    @Test(expected=GengoException.class)
    public void testPostTranslationJobs() throws GengoException, JSONException, InterruptedException {
        GengoClient Gengo = new GengoClient(this.public_key, this.private_key, true);
        // there is no Afrikaans source language, so we expect this to raise an exception
        TranslationJob job = new TranslationJob("label_test", "'n Bietjie afrikaanse teks", "af", "es", Tier.STANDARD);
        List<TranslationJob> jobList = new ArrayList<TranslationJob>();
        jobList.add(job);
        JSONObject response = Gengo.postTranslationJobs(jobList, false);
    }
}
