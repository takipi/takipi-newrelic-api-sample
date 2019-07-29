package io.takipi.newrelic.api.sample;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import hello.Quote;

@Controller
@EnableAutoConfiguration
@SpringBootApplication
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class); 
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "<html><body>"
        		+ "<a href=\"/fire\">SDK Event</a><br/>"
            + "<a href=\"/uncaught\">Uncaught</a><br/>"
        		+ "</body></html>";
    }
    
    @RequestMapping("/fire")
    @ResponseBody
    String fire() {
      new TakipiNewRelicApiSample.NewRelicApiWrapper().fire();
      return "fire";
    }
    

    @RequestMapping("/slow")
    @ResponseBody
    String slow() {
    	long l = new Random().nextLong() % 10000 + 100;
    	
    	try {
			Thread.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
    	
    	return "slept for " + l + "ms" + ". quote: " + quote;
    }
    
    @RequestMapping("/uncaught")
    @ResponseBody
    String uncaught() {
      return "uncaught" + 1/0;
    }
}
