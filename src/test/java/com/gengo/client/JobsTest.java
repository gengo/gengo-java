package com.gengo.client;

import java.lang.System;
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
    public void testPostJobs() throws GengoException, JSONException {

        GengoClient Gengo = new GengoClient(this.public_key, this.private_key, true);
        List<TranslationJob> jobList = new ArrayList<TranslationJob>();
        jobList.add(new TranslationJob("java client test", "This is a short story.","en","ja",Tier.STANDARD));
        JSONObject response = Gengo.postTranslationJobs(jobList, true);
        Assert.assertEquals(response.get("opstat").toString(), "ok");
        Assert.assertTrue(response.has("response"));
    }
}
