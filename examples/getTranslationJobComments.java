package examples;

import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.exceptions.GengoException;

public class getTranslationJobComments
{

    public getTranslationJobComments() throws GengoException
    {
        GengoClient Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
        JSONObject response = Gengo.getTranslationJobComments(42);
    }

}
