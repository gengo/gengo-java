package examples;

import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.enums.Tier;
import com.gengo.client.exceptions.GengoException;
import com.gengo.client.payloads.TranslationJob;

public class postTranslationJob
{

    public postTranslationJob() throws GengoException
    {
        GengoClient Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
        TranslationJob job = new TranslationJob("stub", "Quick brown fox", "en", "es", Tier.STANDARD);
        job.setAutoApprove(false);
        job.setCustomData("custom data");
        JSONObject response = Gengo.postTranslationJob(job);
    }

}
