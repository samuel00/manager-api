package org.learn.log;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/filme")
public class FilmeController {

	private static final Logger logger = LoggerFactory.getLogger(FilmeController.class);

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public String salvarFilme(@RequestParam(value = "titulo") String titulo,
			@RequestParam(value = "ano") String ano,
			@RequestParam(value = "genero") String genero,
			HttpServletRequest request) {

		JSONObject json = new JSONObject();
		
		json.put("evento", "create");
		json.put("http_response", 201);

		return json.toString();
	}

	@RequestMapping(value = "/id", method = RequestMethod.GET)
	public String getFilme(@RequestParam(value = "id", required = false) String id, HttpServletRequest request) {

		JSONObject json = new JSONObject();
		
		json.put("titulo", "Mestrado: a Batalha Final");
		json.put("ano", 2016);
		json.put("genero", "Drama");

		return json.toString();
	}
}
