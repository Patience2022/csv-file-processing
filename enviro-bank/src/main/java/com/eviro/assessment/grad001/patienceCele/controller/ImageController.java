package com.eviro.assessment.grad001.patienceCele.controller;

import java.io.File;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eviro.assessment.grad001.patienceCele.service.FileParser;

@RestController
@RequestMapping("v1/api/image")
public class ImageController {

//	Injecting FileParser interface so i can use it's methods.
//	I opted to inject the interface instead of implementation class so i can achieve loose coupling
	private FileParser fileParser;

	public ImageController(FileParser fileParser) {
		this.fileParser = fileParser;
	}

	@GetMapping("/{name}/{surname}")
	public FileSystemResource getHttpImageLink(@PathVariable String name, @PathVariable String surname)
			throws AccountNotFoundException {

		return fileParser.getFileResource(name, surname);

	}

//Below method is used to save pictures on file, create links and save information to the database.
//It is triggered by sending a post request with request mapping attribute. 	
	@PostMapping
	public ResponseEntity<String> saveAccountProfile() {
		File file = new File("src/main/resources/templates/1672815113084-GraduateDev_AssessmentCsv_Ref003.csv");
		fileParser.parseCSV(file);
		return ResponseEntity.ok("Account Saved successfully");
	}
}
