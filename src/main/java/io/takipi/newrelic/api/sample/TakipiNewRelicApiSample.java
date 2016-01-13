package io.takipi.newrelic.api.sample;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.takipi.sdk.v1.api.Takipi;
import com.takipi.sdk.v1.api.core.events.TakipiEvent;
import com.takipi.sdk.v1.api.core.events.TakipiEventFireOptions;
import com.takipi.sdk.v1.api.core.events.TakipiEventResult;

/**
 * This class wraps NewRelic handleError mechanism to generate tiny urls to events and adds them as additional context 
 * to NewRelic events. 
 * 
 * @author chenharel <chen.harel@takipi.com>
 *
 */
public class TakipiNewRelicApiSample {
	private static final String TAKIPI_TINY_URL_PREFIX = "http://tkp.to/";
	
	private static Takipi takipi;
	private static TakipiEvent event;
	
	static {
		takipi = Takipi.create("Takipi-NewRelic-Error");
		event = takipi.events().createEvent("noticeError");
	}
	
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
			
			TakipiEventFireOptions eventFireOptions = buildEventFireOptions("Testing New Relic events3");
			TakipiEventResult takipiEventResult = event.fire(eventFireOptions);
			
			String takipiLink = generateTakipiLink(takipiEventResult);
			
			if (takipiLink != null) {
				params.put("Takipi snapshot", takipiLink);
			}
			
			NewRelic.noticeError("My test error", params);
			
			System.out.println("NewRelic event fired. Takipi link is " + takipiLink);
		}
	}
	
	private static String generateTakipiLink(TakipiEventResult takipiEventResult) {
		if (takipiEventResult.hasSnapshot()) {
			String id = takipiEventResult.getSnapshotId();
			return TAKIPI_TINY_URL_PREFIX + id;
		}
		
		return null;
	}
	
	private static TakipiEventFireOptions buildEventFireOptions(String msg) {
		return TakipiEventFireOptions.newBuilder()
				.withMessage(msg)
				.withForceSnapshot(true) // In production this should be false (default) as the Takipi agent should be allowed to throttle/skip events
				.build();
	}
}
