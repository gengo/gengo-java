package examples;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.gengo.client.GengoClient;
import com.gengo.client.exceptions.*;
import com.gengo.client.payloads.Approval;
import com.gengo.client.payloads.Rejection;
import com.gengo.client.payloads.TranslationJob;
import com.gengo.client.enums.ApiServiceLevel;
import com.gengo.client.enums.JobStatus;
import com.gengo.client.enums.Rating;
import com.gengo.client.enums.RejectReason;
import com.gengo.client.enums.Tier;

public class TestClient {
    private static String API_KEY_PUBLIC  = ApiKeys.PUBLIC_KEY;
    private static String API_KEY_PRIVATE = ApiKeys.PRIVATE_KEY;

    private GengoClient gc;
    private String sessionHash;

    /**
     * 
     * @param baseUrl Set one of GengoConstants.BASE_URL_*.
     */
    public TestClient(ApiServiceLevel level) {
        switch(level) {
            case STANDARD:
                this.gc = new GengoClient(API_KEY_PUBLIC, API_KEY_PRIVATE);
                break;
            case SANDBOX:
                this.gc = new GengoClient(API_KEY_PUBLIC, API_KEY_PRIVATE, true);
                break;
            default:
                throw new IllegalArgumentException("level is missing.");
        }
        this.sessionHash = new Random().toString();
    }

    /**
     * Test account functionalities.
     */
    public void testAccount(Boolean verbose) {
        verbose = verbose == null ? true : verbose;
        System.out.println("[testAccount] start@" + this.sessionHash);
        JSONObject response = null;
        // Stats(GET)
        System.out.println("Account::Stats(GET)");
        try {
            response = this.gc.getAccountStats();
            System.out.print("[OK] ");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG] ");
            System.out.println(x.getMessage());
        }
        // Balance(GET)
        System.out.println("Account::Balance(GET)");
        try {
            response = this.gc.getAccountBalance();
            System.out.print("[OK] ");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG] ");
            System.out.println(x.getMessage());
        }
        // Preferred translators(GET)
        System.out.println("Account::PreferredTranslators(GET)");
        try {
            response = this.gc.getAccountPreferredTranslators();
            System.out.print("[OK] ");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG] ");
            System.out.println(x.getMessage());
        }
        System.out.println("[testAccount] end@" + this.sessionHash);
    }

    /**
     * Test service functionalities.
     */
    public void testService(Boolean verbose) {
        verbose = verbose == null ? true : verbose;
        System.out.println("[testService] start@" + this.sessionHash);
        JSONObject response;
        // Language pairs(GET)
        System.out.println("Service::Language pairs(GET)");
        try {
            response = this.gc.getServiceLanguagePairs();
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        // Languages(GET)
        System.out.println("Service::Languages(GET)");
        try {
            response = this.gc.getServiceLanguages();
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        // Quote(POST)
        System.out.println("Service::Quote(POST)");
        try {
            List<TranslationJob> qt_jobs = new ArrayList<TranslationJob>();
            qt_jobs.add(new TranslationJob("Test job for quoting.(1) (" + this.sessionHash + ")", "Test.",
                    "en", "ja", Tier.STANDARD));
            qt_jobs.add(new TranslationJob("Test job for quoting.(2) (" + this.sessionHash + ")", "Test.",
                    "en", "ja", Tier.PRO));
            response = this.gc.determineTranslationCostText(qt_jobs);
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        System.out.println("[testService] end@" + this.sessionHash);
    }

    /**
     * Test jobs functionalities.
     */
    public void testJobs(Boolean verbose) {
        verbose = verbose == null ? true : verbose;
        System.out.println("[testJobs] start@" + this.sessionHash);
        JSONObject response = null;
        List<TranslationJob> pll;
        // Jobs(POST) single
        try {
            System.out.println("Jobs::(POST) single");
            pll = Arrays.asList(
                new TranslationJob("[Single] en2ja::" + this.sessionHash, "Test.",
                        "en", "ja", Tier.STANDARD)
                );
            response = this.gc.postTranslationJobs(pll, false);
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        // Jobs(POST) multiple
        try {
            System.out.println("Jobs::(POST) multiple");
            pll = Arrays.asList(
                    new TranslationJob("[Multiple] en2ja(1)::" + this.sessionHash, "Test. (1)",
                            "en", "ja", Tier.STANDARD),
                    new TranslationJob("[Multiple] en2ja(2)::" + this.sessionHash, "Test. (2)",
                            "en", "ja", Tier.STANDARD)
                    );
            response = this.gc.postTranslationJobs(pll, true);
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        // Jobs(GET)
        try {
            System.out.println("Jobs::(GET)");
            response = this.gc.getTranslationJobs();
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        // Jobs(GET) by id
        Integer[] jobs_get = null;
        try {
            jobs_get = new Integer[(int) Math.floor(response.getJSONArray("response").length() / 2)];
            for (int i = 0; i < (int) Math.floor(response.getJSONArray("response").length() / 2); i++) {
                jobs_get[i] = response.getJSONArray("response").getJSONObject(i).getInt("job_id");
            }
        } catch (JSONException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
        }
        try {
            System.out.println("Jobs::(GET) by id");
            response = this.gc.getTranslationJobs(Arrays.asList(jobs_get));
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        // Jobs(GET) by status and limit 5
        try {
            System.out.println("Jobs::(GET) by status and limit 5");
            response = this.gc.getTranslationJobs(Arrays.asList(jobs_get), JobStatus.AVAILABLE, null, 5);
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        System.out.println("[testJobs] end@" + this.sessionHash);
    }

    /**
     * Test job functionalities.
     */
    public void testJob(Boolean verbose) {
        verbose = verbose == null ? true : verbose;
        System.out.println("[testJob] start@" + this.sessionHash);
        JSONObject response = null;
        // Preparation
        int job_id = -1;
        String job_status = null;
        try {
            response = this.gc.getTranslationJobs();
            job_id = response.getJSONArray("response").getJSONObject(0).getInt("job_id");
        } catch (JSONException e) {
            System.out.println("[Skipped] Preparation failed. " + x.getMessage());
        } catch (GengoException e) {
            System.out.println("[Skipped] Preparation failed. " + x.getMessage());
        }
        // Job(GET)
        System.out.println("Job::(GET)");
        if (job_id != -1) {
            try {
                response = this.gc.getTranslationJob(job_id);
                job_status = response.getJSONObject("response").getString("status");
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (JSONException e) {
                System.out.print("[Skipped]");
                System.out.println(x.getMessage());
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped] No job.");
        }
        // Job(PUT) revise
        System.out.println("Job::(PUT) revise");
        if (job_id != -1 && job_status == "reviewable") {
            try {
                response = this.gc.reviseTranslationJob(job_id, "Test revise via API.");
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped] No jobs can be modified.");
        }
        // Job(PUT) approve
        System.out.println("Job::(PUT) approve");
        if (job_id != -1 && job_status == "reviewable") {
            try {
                response = this.gc.approveTranslationJob(job_id, Rating.THREE_STARS, Rating.FOUR_STARS, Rating.TWO_STARS,
                        "Test comment for translator via API.", 
                        "Test comment for Gengo.", false);
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped] No jobs can be modified.");
        }
        // Job(PUT) reject
        System.out.println("Job::(PUT) reject");
        if (job_id != -1 && job_status == "reviewable") {
            try {
                response = this.gc.rejectTranslationJob(job_id, RejectReason.OTHER,
                        "Test reject reason via API.", "", false);
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped] No jobs can be modified.");
        }
        // Job(PUT) archive <~ API not in service
        // Job::Revisions(GET)
        System.out.println("Job Revisions::(GET)");
        if (job_id != -1) {
            try {
                response = this.gc.getTranslationJobRevisions(job_id);
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped]");
        }
        // Job::Revision(GET)
        System.out.println("Job Revision::(GET)");
        if (job_id != -1) {
            try {
                response = this.gc.getTranslationJobRevision(job_id,
                        response.getJSONObject("response").getJSONArray("revisions").getJSONObject(0).getInt("rev_id"));
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (JSONException e) {
                System.out.print("[Skipped]");
                System.out.println(x.getMessage());
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped] No jobs can be modified.");
        }
        // Job::Feedback(GET)
        System.out.println("Job Feedback::(GET)");
        if (job_id != -1) {
            try {
                response = this.gc.getTranslationJobFeedback(job_id);
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped]");
        }
        // Job::Comment(POST)
        System.out.println("Job Comment::(POST)");
        if (job_id != -1) {
            try {
                response = this.gc.postTranslationJobComment(job_id, "Test comment via API.");
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped]");
        }
        // Job::Comments(GET)
        System.out.println("Job Comments::(GET)");
        if (job_id != -1) {
            try {
                response = this.gc.getTranslationJobComments(job_id);
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped]");
        }
        // Job(DELETE)
        System.out.println("Job::(DELETE)");
        if (job_id != -1) {
            try {
                response = this.gc.deleteTranslationJob(job_id);
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped]");
        }
        System.out.println("[testJob] end@" + this.sessionHash);
    }

    /**
     * Test order functionalities.
     * @param order_id prematured Order ID is expected
     */
    public void testOrder(Boolean verbose, int order_id)
    {
        verbose = verbose == null ? true : verbose;
        System.out.println("[testOrder] start@" + this.sessionHash);
        JSONObject response = null;
        // Order::(GET)
        System.out.println("Order::(GET)");
        try {
            response = this.gc.getOrderJobs(order_id);
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        // Order:: Comment(POST)
        System.out.println("Order:: Comment(POST)");
        try {
            response = this.gc.postOrderComment(order_id, "Test order comment via API.");
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        // Order:: Comments(GET)
        System.out.println("Order:: Comments(GET)");
        try {
            response = this.gc.getOrderComments(order_id);
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        // Order::(DELETE)
        System.out.println("Order::(DELETE)");
        try {
            response = this.gc.deleteTranslationOrder(order_id);
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        System.out.println("[testOrder] end@" + this.sessionHash);
    }

    /**
     * Test glossary functionalities.
     */
    public void testGlossary(Boolean verbose)
    {
        verbose = verbose == null ? true : verbose;
        System.out.println("[testGlossary] start@" + this.sessionHash);
        JSONObject response = null;
        int glossary_id = -1;
        // Glossaries (GET)
        System.out.println("Glossaries::(GET)");
        try {
            response = this.gc.getGlossaryList();
            glossary_id = response.getJSONArray("response").getJSONObject(0).getInt("id");
            System.out.print("[OK]");
            System.out.println(verbose ? response.toString() : "");
        } catch (JSONException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        } catch (GengoException e) {
            System.out.print("[NG]");
            System.out.println(x.getMessage());
        }
        // Glossary (GET)
        System.out.println("Glossary::(GET)");
        if (glossary_id != -1) {
            try {
                response = this.gc.getGlossary(glossary_id);
                System.out.print("[OK]");
                System.out.println(verbose ? response.toString() : "");
            } catch (GengoException e) {
                System.out.print("[NG]");
                System.out.println(x.getMessage());
            }
        } else {
            System.out.println("[Skipped]");
        }
        System.out.println("[testGlossary] end@" + this.sessionHash);
    }

    public static void main(String[] args) throws GengoException {
        TestClient tc = new TestClient(ApiServiceLevel.STANDARD);
        //JSONObject response;
        tc.testAccount(true);
        //tc.testService(false);
        //tc.testJobs(true);
        //[begin] Jobs revised test.
        //HashMap<Integer, String> jobs_revision = new HashMap<Integer, String>();
        //jobs_revision.put(17782185, "revision comment");
        //response = tc.gc.reviseTranslationJobs(jobs_revision);
        //[end] Jobs revised test.
        //[begin] Jobs approved test.
        //List<Approval> jobs_approval = new ArrayList<Approval>();
        //jobs_approval.add(new Approval(17784225, "API test feedback.", "API test feedback.", false, Rating.FOUR_STARS, Rating.THREE_STARS, Rating.TWO_STARS));
        //JSONObject response = tc.gc.approveTranslationJobs(jobs_approval);
        //[end] Jobs approved test.
        //[begin] Jobs archived test.
        //JSONObject response = tc.gc.archiveTranslationJobs(Arrays.asList(17784225));
        //[end] Jobs archived test.
        //[begin] Jobs rejected test.
        //List<Rejection> jobs_reject = new ArrayList<Rejection>();
        //jobs_reject.add(new Rejection(17784226 , RejectReason.OTHER, "API reject test.", "BLTZ", false));
        //try {
        // response = tc.gc.rejectTranslationJobs(jobs_reject);
        // System.out.println(response.toString());
        //} catch (GengoException e) {
        // System.out.println(x.getMessage());
        //}
        //[end] Jobs rejected test.
        //[begin] Job revised test.
        //response = tc.gc.reviseTranslationJob(17784224, "API reviseing test.");
        //[end] Job revised test.
        //[begin] Job reject test.
        //response = tc.gc.rejectTranslationJob(17784224, RejectReason.QUALITY, "API rejection test.", "RKPX", false);
        //[end] Job reject test.
        //[begin] Job approved test.
        //response = tc.gc.approveTranslationJob(17784224, Rating.THREE_STARS, Rating.FIVE_STARS, Rating.ONE_STAR, "Test comment for translator.", "Test comment for gengo.", false);
        //[end] Job approved test.
        //tc.testJob(false);
        // Order functionalities cannot be tested automatically because API method to enumerate orders is missing. 
        //tc.testOrder(false, 1699675);
        //response = tc.gc.deleteTranslationOrder(1699685);
        //tc.testGlossary(true);
        //System.out.println(response.toString());
    }
}
