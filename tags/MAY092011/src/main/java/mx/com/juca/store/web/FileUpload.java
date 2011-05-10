package mx.com.juca.store.web;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Juan Carlos Cruz
 * @since Mar 23, 2011
 */
public class FileUpload {
	private MultipartFile multipartFile;

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileUpload [");
		builder.append("multipartFile=").append(multipartFile);
		builder.append("]");
		return builder.toString();
	}

}
