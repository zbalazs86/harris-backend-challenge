package hu.harris.backendchallenge.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MachineDto {

	@JsonIgnore
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private String id;
	
	private Integer version;
	
	private String name;
	
	private String createdAt;
	
	private String updatedAt;

	@JsonIgnore
    public Date getCreatedAtConverted() throws ParseException {
    	if(createdAt == null) return null;
    	
    	return dateFormat.parse(createdAt);
    }
    
    public void setCreatedAt(Date date) {
    	if(date != null) {    		
    		this.createdAt = dateFormat.format(date);
    	}
    }
    
    @JsonIgnore
    public Date getUpdatedAtConverted() throws ParseException {
    	if(updatedAt == null) return null;
    	
    	return dateFormat.parse(updatedAt);
    }
    
    public void setUpdatedAt(Date date) {
    	if(date != null) {    		
    		this.updatedAt = dateFormat.format(date);
    	}
    }
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
