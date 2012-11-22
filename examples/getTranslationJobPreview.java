package examples;

import java.awt.image.BufferedImage;

import com.gengo.client.GengoClient;
import com.gengo.client.exceptions.GengoException;

public class getTranslationJobPreview
{

    public getTranslationJobPreview() throws GengoException
    {
        GengoClient Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
        BufferedImage image = Gengo.getTranslationJobPreviewImage(42);
    }

}
