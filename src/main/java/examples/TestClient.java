package examples;

import org.json.JSONObject;

import java.lang.IllegalArgumentException;
import com.gengo.client.GengoClient;
import com.gengo.client.exceptions.*;
import com.gengo.client.enums.ApiServiceLevel;

public class TestClient {
	private static String API_KEY_PUBLIC  = "^U88gnGj4ofZsDz00{NA6B=-3^~AJDW_2jfnO32tSEfIzCq36eI53GYoWenQ~QSc";
	private static String API_KEY_PRIVATE = "ryPJN}Y|~]-(9GU5NIjim-D4B9{aPUDXP09GQ}xok3ZESsIiadeC7G2[V58[vhtP";
	
	private GengoClient gc;
	
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
			case STAGING:
				this.gc = new GengoClient(API_KEY_PUBLIC, API_KEY_PRIVATE, false, true);
				break;
			default:
				throw new IllegalArgumentException("level is missing.");
		}
	}
	
	public static void main(String[] args) throws GengoException {
		TestClient tc = new TestClient(ApiServiceLevel.STAGING);
		JSONObject data = null;
		try {
			data = tc.gc.getAccountBalance();
		} catch (GengoException e) {
			System.out.println(e.toString());
		}
		if (data != null) {
			System.out.println(data.toString());
		}
	}
}
