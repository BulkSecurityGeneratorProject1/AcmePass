package com.acme.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaFileService {

	private final Logger log = LoggerFactory.getLogger(MediaFileService.class);

	private final static String MEDIA_FOLDER = new File(".").getAbsolutePath()+"/mediaResources/";
	private final static Path MEDIA_FOLDER_PATH = Paths.get(MEDIA_FOLDER).normalize();

	public List<File> getMediaFileList() {
		return Arrays.asList(new File(MEDIA_FOLDER).listFiles(file -> file.isFile() && !file.getName().startsWith(".")));
	}

	public File getMediaFile(String fileName) {
        Path filePath = Paths.get(MEDIA_FOLDER, fileName).normalize();

        if (!filePath.startsWith(MEDIA_FOLDER_PATH)) {
            log.warn("Invalid filename provided");
            return null;
        }

		File file = new File(MEDIA_FOLDER + fileName);

		if (file.exists() && file.canRead() && file.isFile()) {
			return file;
		}

		return null;
	}

	public void saveMediaFile(MultipartFile multiPartFile) throws IOException {
		File file = new File(MEDIA_FOLDER + multiPartFile.getOriginalFilename());

		multiPartFile.transferTo(file);
	}

	public boolean deleteMediaFile(String fileName) {
        Path filePath = Paths.get(MEDIA_FOLDER, fileName).normalize();
        if (!filePath.startsWith(MEDIA_FOLDER_PATH)) {
            log.warn("Invalid filename provided");
            return false;
        }

		File file = new File(MEDIA_FOLDER + fileName);

		return file.exists() && file.canWrite() && file.delete();
	}
}
