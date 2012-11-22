package examples;

import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.enums.Tier;
import com.gengo.client.exceptions.GengoException;
import com.gengo.client.payloads.TranslationJob;
import com.gengo.client.payloads.Payloads;

public class determineTranslationCost
{

    public determineTranslationCost(int id) throws GengoException
    {
        GengoClient Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
        Payloads jobList = new Payloads();
        jobList.add(new TranslationJob("very short job", "A very short job..","en","ja",Tier.STANDARD));
        jobList.add(new TranslationJob("longer job", "Still short but slightly longer job.","en","ja",Tier.STANDARD));
        JSONObject response = Gengo.determineTranslationCost(jobList);
    }

}
