package examples;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.enums.Tier;
import com.gengo.client.exceptions.GengoException;
import com.gengo.client.payloads.Attachment;
import com.gengo.client.payloads.TranslationJob;

/* Basic runnable example */
public class GettingStarted {

    private GengoClient Gengo;

    public GettingStarted() throws GengoException
    {
        Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
    }

    public void test() throws GengoException, JSONException
    {
        // retrieve account balance
        JSONObject response = Gengo.getAccountBalance();
        System.out.println(response);

        // prepare jobs payload
        List<TranslationJob> jobList = new ArrayList<TranslationJob>();
        TranslationJob job = new TranslationJob("short story title", "This is a short story.","en","es",Tier.STANDARD);
        job.addAttachment(this.getAttachment());
        jobList.add(job);
        jobList.add(new TranslationJob("short story body", "There once was a man from Nantucket.","en","es",Tier.STANDARD));

        // send request
        response = Gengo.postTranslationJobs(jobList, true);
        System.out.println(response.toString(2));
    }

    private Attachment getAttachment() throws GengoException
    {
        return new Attachment("http://example.com/filename.jpeg",
                              "filename.jpeg", "image/jpeg");
    }

    public static void main(String [] args) throws GengoException, JSONException
    {
        GettingStarted gs = new GettingStarted();
        gs.test();
    }
}
