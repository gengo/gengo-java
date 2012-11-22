package examples;

import java.util.Arrays;

import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.exceptions.GengoException;

public class getTranslationJob_id
{

    public getTranslationJob_id() throws GengoException
    {
        GengoClient Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
        JSONObject response = Gengo.getTranslationJobs(Arrays.asList(new Integer[]{42, 79}));
    }

}
