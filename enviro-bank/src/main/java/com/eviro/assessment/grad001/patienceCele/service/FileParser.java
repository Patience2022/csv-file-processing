package com.eviro.assessment.grad001.patienceCele.service;

import java.io.File;
import java.net.URI;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.core.io.FileSystemResource;

public interface FileParser {

	void parseCSV(File csvFile);

	File convertCSVDataToImage(String base64ImageData);

	URI createImageLink(File fileImage);

	FileSystemResource getFileResource(String name, String surname) throws AccountNotFoundException;
}
