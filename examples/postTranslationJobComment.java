package examples;

import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.exceptions.GengoException;


public class postTranslationJobComment
{

    public postTranslationJobComment() throws GengoException
    {
        GengoClient Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
        JSONObject response = Gengo.postTranslationJobComment(42, "This is a comment.");
    }

}
