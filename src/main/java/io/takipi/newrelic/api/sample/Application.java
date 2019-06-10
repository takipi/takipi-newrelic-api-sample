package io.takipi.newrelic.api.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;

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
    
    @RequestMapping("/uncaught")
    @ResponseBody
    String uncaught() {
      return "uncaught" + 1/0;
    }
}
