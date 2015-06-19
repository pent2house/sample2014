package com.ns.spring.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rma_hdr")
public class RMA_HDR {

	@Id
	private ObjectId id;
	
	private String rma_num;
	
	private String rma_hdr_sts_cd;
	
	public int getTimestamp() {
		return id.getTimestamp();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getRma_num() {
		return rma_num;
	}

	public void setRma_num(String rma_num) {
		this.rma_num = rma_num;
	}

	public String getRma_hdr_sts_cd() {
		return rma_hdr_sts_cd;
	}

	public void setRma_hdr_sts_cd(String rma_hdr_sts_cd) {
		this.rma_hdr_sts_cd = rma_hdr_sts_cd;
	}	
}
