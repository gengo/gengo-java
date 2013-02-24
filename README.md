[![Build Status](https://secure.travis-ci.org/gengo/gengo-java.png?branch=master)](https://travis-ci.org/gengo/gengo-java)

Gengo Java Library (for the [Gengo API](http://gengo.com/api/))
======================================================================================================================================================
Translating your tools and products helps people all over the world access them; this is, of course, a somewhat tricky problem to solve.
**[Gengo](http://gengo.com/)** is a service that offers human-translation (which is often a higher quality than machine translation), and an API to
manage sending in work and watching jobs. This is a Java interface to make using the API simpler (some would say incredibly easy).


Building
------------------------------------------------------------------------------------------------------------------------------------------------------

### Ant
Simply run `ant` from the base directory after checking out. This will download dependencies, compile classes and package the library in
`lib/gengo.jar`. Add this jar file to your classpath to start working with the library.

### Maven
Run `mvn package` from the base directory. Package is built into the `target` directory.

### Eclipse
Quick start:
* Checkout code from github
* File menu > New Java Project
* Uncheck "Use default location"
* Set project layout to Use project folder as root for sources and class files
* Browse for location checked out code
* Hit next
* Hit finish
* Build!


Dependencies
------------------------------------------------------------------------------------------------------------------------------------------------------
The [JSON in Java](http://json.org/java/) package by Douglas Crockford.


Example Code
------------------------------------------------------------------------------------------------------------------------------------------------------

```java
import org.json.JSONException;
import org.json.JSONObject;

import com.gengo.client.GengoClient;
import com.gengo.client.enums.Tier;
import com.gengo.client.exceptions.GengoException;
import com.gengo.client.payloads.TranslationJob;

public class ShortExample
{
    private void ShortExample() throws GengoException, JSONException
    {
        GengoClient Gengo = new GengoClient(ApiKeys.PUBLIC_KEY, ApiKeys.PRIVATE_KEY, true);
        List<TranslationJob> jobList = new ArrayList<TranslationJob>();
        jobList.add(new TranslationJob("test_job_slug", "This is a short test job", "en", "es", Tier.STANDARD));
        JSONObject response = Gengo.postTranslationJobs(jobList, true);
    }
}
```


Question, Comments, Complaints, Praise?
------------------------------------------------------------------------------------------------------------------------------------------------------
If you have questions or comments and would like to reach us directly, please feel free to do so at the following outlets. We love hearing from
developers!

* Email: api [at] gengo dot com
* Twitter: [@gengoit](https://twitter.com/gengoit)
* IRC: [#gengo](irc://irc.freenode.net/gengo)

If you come across any issues, please file them on the [Github project issue tracker](https://github.com/gengo/gengo-java/issues). Thanks!


Documentation
------------------------------------------------------------------------------------------------------------------------------------------------------
Check out the full [Gengo API documentation](http://developers.gengo.com).


Credits & License
---------------------------------------------------------------------------------------------------------------------------
This library is based on the excellent C# interface by Saqib Shaikh

The library itself is licensed under a BSD-style license. See the enclosed LICENSE.txt file for more information.
