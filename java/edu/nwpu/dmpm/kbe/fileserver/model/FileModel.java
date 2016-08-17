package edu.nwpu.dmpm.kbe.fileserver.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;


public class FileModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3533301031409698575L;
	private String fileId;// 文件id
	private String fileName;// 文件名称
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String suffix;// 后缀名
	private String path;// 存储路径（默认D:/dmpmfiles/文件类型）
	private MultipartFile file;
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
