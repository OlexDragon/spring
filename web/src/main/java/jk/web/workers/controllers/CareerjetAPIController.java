package jk.web.workers.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import com.careerjet.webservices.api.Client;

@Controller
@RequestMapping("/careerjet")
public class CareerjetAPIController {

	private final Logger logger = LogManager.getLogger();

	@Autowired
	private LocaleResolver localeResolver;

	@RequestMapping
	public ResponseEntity<byte[]> search(
									@RequestParam(value = "keywords") String keywords,
									@RequestParam(value = "location", required=false) String location,
									HttpServletRequest request){

		logger.entry(keywords, location);
		Client c = new Client(localeResolver.resolveLocale(request).toString());
		  Map<String, String> args = new HashMap<String, String>();
		   args.put("keywords", keywords);

		   if(location!=null)
			   args.put("location", location);

		   JSONObject results = (JSONObject) c.search(args);

		   String type = (String)results.get("type");
		   switch(type){
		   case "JOBS":
			   JSONArray jobs = (JSONArray) results.get("jobs");
			   logger.trace("\n\t{}", jobs);
			   break;
		   case "LOCATIONS":
		       JSONArray solvelocations = (JSONArray) results.get("solveLocations");
			   logger.trace("\n\t{}", solvelocations);
			   break;
		   case "ERROR":
			   logger.error("\n\t Error: {}", results.get("ERROR"));
			   break;
		   default:
			   logger.trace("\n\t Type: {}", type);
		   }

		return null;
	}
}
