package com.gengo.client;

import java.lang.System;

import com.gengo.client.GengoClient;
import com.gengo.client.exceptions.GengoException;

import org.json.JSONObject;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

    private String public_key = System.getenv("GENGO_PUBKEY");
    private String private_key = System.getenv("GENGO_PRIVKEY");

    @Test
    public void testGetAccountBalance() throws GengoException, JSONException {

        GengoClient Gengo = new GengoClient(this.public_key, this.private_key, true);
        JSONObject response = Gengo.getAccountBalance();
        Assert.assertEquals(response.get("opstat").toString(), "ok");
        Assert.assertTrue(response.has("response"));
    }

    @Test
    public void testGetAccountStats() throws GengoException, JSONException {

        GengoClient Gengo = new GengoClient(this.public_key, this.private_key, true);
        JSONObject response = Gengo.getAccountStats();
        Assert.assertEquals(response.get("opstat").toString(), "ok");
        Assert.assertTrue(response.has("response"));
    }
}
