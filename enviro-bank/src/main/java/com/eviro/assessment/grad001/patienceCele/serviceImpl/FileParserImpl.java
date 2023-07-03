package com.eviro.assessment.grad001.patienceCele.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.eviro.assessment.grad001.patienceCele.entity.AccountProfile;
import com.eviro.assessment.grad001.patienceCele.repository.AccountProfileRepo;
import com.eviro.assessment.grad001.patienceCele.service.FileParser;

@Service
public class FileParserImpl implements FileParser {

	private AccountProfileRepo repository;

	public FileParserImpl(AccountProfileRepo repository) {
		this.repository = repository;
	}

	private AccountProfile profile;

	private String line = "";
	private String format;
	File imageFile = null;

	@Override
	public void parseCSV(File csvFile) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(csvFile));
			int row = 0;

			while ((line = reader.readLine()) != null) {

				String[] data = line.split(",");
				if (data[0].equals("name")) {
					System.out.println("I am going to the new line");
					continue;
				}
				profile = new AccountProfile();
				profile.setName(data[0]);
				profile.setSurname(data[1]);

//				Supplying image format in it's correct form (.jpeg etc) CSV file =image/jpeg
				format = "." + data[2].substring(6);
				String imageData = data[3];
				System.out.println(profile.getName() + " " + profile.getSurname() + " " + format);
				if (!imageData.equals("")) {
					if (format.equals("")) {
						System.out.println("Unable to save image supplied, image format not found");
					}
					imageFile = convertCSVDataToImage(imageData);
//					
					profile.setHttpImageLink(createImageLink(imageFile));
					System.out.println("The link is " + profile.getHttpImageLink());
				}

//				Saving information to the database
				repository.save(profile);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@Override
	public File convertCSVDataToImage(String base64ImageData) {
		File file = null;

		byte[] image = Base64.getDecoder().decode(base64ImageData);
		try {
			String savePath = "src/main/resources/templates/images/" + profile.getName() + profile.getSurname()
					+ format;
			file = new File(savePath);
			FileOutputStream stream = new FileOutputStream(file);
			stream.write(image);
			stream.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return file;
	}

	@Override
	public URI createImageLink(File fileImage) {

		return fileImage.toURI();
	}

	public FileSystemResource getFileResource(String name, String surname) throws AccountNotFoundException {

//findByNameAndSurname methods checks records on the database, if the profile has a name and surname that matches
//the given arguements, it will return that profile. The method returns an optional of type AccountProfile. If the profile doesn't exist, it will throw an AccountNotFoundException		
		profile = repository.findByNameAndSurname(name, surname)
				.orElseThrow(() -> new AccountNotFoundException("No such profile"));
		URI uri = profile.getHttpImageLink();

		String resourceLocator = uri.getPath();
		System.out.println("Resource locator's  value is " + resourceLocator);

		FileSystemResource newResource = new FileSystemResource(resourceLocator);
		System.out.println("\n \n New resource:" + newResource);

		return newResource;

	}

}
