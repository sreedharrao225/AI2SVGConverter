package com.dev.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aioobe.cloudconvert.CloudConvertService;
import org.aioobe.cloudconvert.ConvertProcess;
import org.aioobe.cloudconvert.ProcessStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CloudConverterController {



	@GetMapping("/")
	public String mainWithParam(
			@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {

		model.addAttribute("message", name);

		return "index"; //view
	}

	private String getFileStoragepath() {
		String path="";
		path=System.getenv("FILE_STORAGE_PATH");
		if(path==null || path.isEmpty()) {
			path=File.separator+"tmp"+File.separator;
		}
		return path;

	}

	private String getApiKey() {
		String key="";
		key=System.getenv("API_KEY");
		if(key==null || key.isEmpty()) {
			key="s6JJeAlUSJ4iB9HyWG87vkImxGhFCQjQWAdlje7Ak58itkSZtOOdZhObdrDEYJsm";
		}
		return key;

	}

	private File getOutputFile(MultipartFile file) {
		File fileToSave = null;
		try {
			String path="";
			path=System.getenv("FILE_STORAGE_OUTPUT_PATH");
			if(path==null || path.isEmpty()) {
				path=File.separator+"tmp"+File.separator;
			}
			path=path+file.getOriginalFilename().toLowerCase().replace(".ai", "");
			System.out.println("@@@file name is "+path);
			fileToSave=new File(path+"_output.svg");
			fileToSave.createNewFile();
		}catch(Exception e) {
			System.out.println("Error while loadig file "+e.getMessage());
		}
		return fileToSave;
	}
	
	private File saveFile(MultipartFile file) {
		File fileToSave = null;
		try {
			String url = getFileStoragepath()+file.getOriginalFilename();
			fileToSave = new File(url);
			fileToSave.createNewFile();
			FileOutputStream fos = new FileOutputStream(fileToSave); 
			fos.write(file.getBytes());
			fos.close();
		}catch(Exception e) {
			System.out.println("Error while loadig file "+e.getMessage());
		}
		return fileToSave;
	}

	@PostMapping("/convert") 
	@ResponseStatus(value=HttpStatus.OK)
    @ResponseBody
	public  void convert( @RequestParam("file") MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws Exception {

		try {

			CloudConvertService service = new CloudConvertService(getApiKey()) ;

			System.out.println("starting process...");
			ConvertProcess process = service.startProcess("ai", "svg");

			File  inpFile=saveFile(file);
			inpFile.createNewFile();
			System.out.println("starting conversation wtih file ..."+ inpFile.getAbsolutePath());
			process.startConversion(inpFile);//.createNewFile();

			// Wait for result
			ProcessStatus status;
			waitLoop: while (true) {
				status = process.getStatus();
				System.out.println("waiting on the convertion ...convertion.. "+status.step);
				switch (status.step) {
				case FINISHED: break waitLoop;
				case ERROR: throw new RuntimeException(status.message);
				}

				// Be gentle
				Thread.sleep(200);
			}
			System.out.println("Done processing the file ... "+status.message);
			File outputFile=getOutputFile(file);
			System.out.println("writing to the file..");
			service.download(status.output.url,outputFile);

			System.out.println("cleaning up ..");
			process.delete();
			inpFile.delete();

			if (outputFile.exists()) {
				String mimeType = URLConnection.guessContentTypeFromName(outputFile.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				System.out.println("get filename ::"+outputFile.getName());
				response.setHeader("Content-Disposition", "attachment; filename=\"" + outputFile.getName());
		        response.getOutputStream().write(Files.readAllBytes(Paths.get(outputFile.getAbsolutePath())));
		        response.flushBuffer();
				
			}
		}catch(Exception e) {
			System.out.println("###Error processing request..");
			e.printStackTrace();
			throw e;
		}
	}

}