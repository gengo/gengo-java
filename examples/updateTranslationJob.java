package examples;

import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.enums.Rating;
import com.gengo.client.enums.RejectReason;
import com.gengo.client.exceptions.GengoException;

public class updateTranslationJob
{

    GengoClient Gengo;

    public updateTranslationJob() throws GengoException
    {
        Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
        approveTranslationJob();
        rejectTranslationJob();
        reviseTranslationJob();
    }

    private void approveTranslationJob() throws GengoException
    {
        JSONObject response = Gengo.approveTranslationJob(
                42,
                Rating.FOUR_STARS,
                "Thanks, great job!",
                "Give this translator a medal..",
                false
                );
    }

    private void rejectTranslationJob() throws GengoException
    {
        JSONObject response = Gengo.rejectTranslationJob(
                42,
                RejectReason.OTHER,
                "A really good reason",
                "captcha text",
                true
                );
    }

    private void reviseTranslationJob() throws GengoException
    {
        JSONObject response = Gengo.reviseTranslationJob(42, "Please fix this and that.");
    }

}
