package examples;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.enums.Tier;
import com.gengo.client.exceptions.GengoException;
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
        JSONObject response = Gengo.getAccountBalance();
        System.out.println(response);
        GengoClient Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
        List<TranslationJob> jobList = new ArrayList<TranslationJob>();
        jobList.add(new TranslationJob("short story title", "This is a short story.","en","es",Tier.STANDARD));
        jobList.add(new TranslationJob("short story body", "There once was a man from Nantucket.","en","es",Tier.STANDARD));
        response = Gengo.postTranslationJobs(jobList, true);
        System.out.println(response.toString(2));
	}

	public static void main(String [] args) throws GengoException, JSONException
	{
		GettingStarted gs = new GettingStarted();
		gs.test();
	}

}
