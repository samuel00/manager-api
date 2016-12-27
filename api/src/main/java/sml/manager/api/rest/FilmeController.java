package sml.manager.api.rest;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sml.manager.api.core.entidade.FilmeDTO;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/filme")
public class FilmeController {

	private static final Logger logger = LoggerFactory.getLogger(FilmeController.class);

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String salvarFilme(@RequestBody @Validated FilmeDTO filme,
			HttpServletRequest request)  throws ParseException {

		JSONObject json = new JSONObject();
		
		json.put("evento", "create");
		json.put("http_response", 201);

		HttpHeaders headers = new HttpHeaders();
        headers.setConnection("");
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
