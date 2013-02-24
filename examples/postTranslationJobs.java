package examples;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.enums.Tier;
import com.gengo.client.exceptions.GengoException;
import com.gengo.client.payloads.TranslationJob;
import com.gengo.client.payloads.Payloads;

public class postTranslationJobs
{

    public postTranslationJobs() throws GengoException
    {
        GengoClient Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
        List<TranslationJob> jobList = new ArrayList<TranslationJob>();
        jobList.add(new TranslationJob("short story title", "This is a short story.", "en", "es", Tier.STANDARD));
        jobList.add(new TranslationJob("short story body", "There once was a man from Nantucket.", "en", "es", Tier.STANDARD));
        JSONObject response = Gengo.postTranslationJobs(jobList, true);
    }

}
