package com.iemr.common.controller.swaasa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iemr.common.service.swaasa.SwaasaService;
import com.iemr.common.utils.response.OutputResponse;

@RequestMapping(value = "/swaasa")
@RestController
public class SwaasaController {

	@Autowired
	private SwaasaService swaasaService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Deprecated
	@CrossOrigin()
	@RequestMapping(value = "/validateCough", method = RequestMethod.POST, headers = "Authorization")
	public String getAdminLogin(@RequestParam("file") MultipartFile file) {
		OutputResponse output = new OutputResponse();
		try {

			Boolean res = swaasaService.verifyCough(file, null, null);
			if (res)
				output.setResponse("pass");
			else
				output.setResponse("fail");

			logger.info("get verify cough response: " + output);
		} catch (Exception e) {
			logger.error("sawassa failed with error " + e.getMessage(), e);
			output.setError(e);
		}
		return output.toString();
	}

	@CrossOrigin()
	@RequestMapping(value = "/startAssesment", method = RequestMethod.POST, headers = "Authorization")
	public String startAssesment(@RequestParam("file") MultipartFile file, @RequestParam("request") String request) {
		OutputResponse output = new OutputResponse();
		try {

			String res = swaasaService.initiateAssesment(request, file);
			output.setResponse(res);

			logger.info("start assessment cough response: " + output);
		} catch (Exception e) {
			logger.error("sawassa failed with error " + e.getMessage(), e);
			output.setError(e);
		}
		return output.toString();
	}

	@CrossOrigin()
	@RequestMapping(value = "/getAssesment/{assessmentId}", method = RequestMethod.GET, headers = "Authorization")
	public String getAssessment(@PathVariable("assessmentId") String assessmentId) {
		OutputResponse output = new OutputResponse();
		try {

			String res = swaasaService.getAssesment(assessmentId);
			output.setResponse(res);

			logger.info("get assessment cough response: " + output);
		} catch (Exception e) {
			logger.error("sawassa failed with error " + e.getMessage());
			output.setError(e);
		}
		return output.toString();
	}

	@CrossOrigin()
	@RequestMapping(value = "/getAssesmentDetails/{patientId}", method = RequestMethod.GET, headers = "Authorization")
	public String getAssessmentDetails(@PathVariable("patientId") Long patientId) {
		OutputResponse output = new OutputResponse();
		try {

			String res = swaasaService.getAssessmentDetails(patientId);
			output.setResponse(res);

			logger.info("get assessment details response: " + output);
		} catch (Exception e) {
			logger.error("get assessment details failed with error " + e.getMessage());
			output.setError(e);
		}
		return output.toString();
	}

}