package io.takipi.newrelic.api.sample;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

/**
 * This class wraps NewRelic handleError mechanism to generate tiny urls to events and adds them as additional context 
 * to NewRelic events. 
 * 
 * @author chenharel <chen.harel@takipi.com>
 *
 */
public class TakipiNewRelicApiSample {
	private static final String TAKIPI_TINY_URL_PREFIX = "http://tkp.to/";
	
	public static void main(String[] args) throws Exception {
		System.out.println("Starting to fire events to NewRelic");

		for (int i = 0; i < 1000; i++) {
			new NewRelicApiWrapper().fire();
			Thread.sleep(2000);
		}
	}

	public static class NewRelicApiWrapper {
		@Trace
		public void fire() {
			Map<String, String> params = new HashMap<String, String>();
			params.put("prop1", "test1");
			
			NewRelic.noticeError("My test error", params);

			System.out.println("done fire");
		}
	}
}
