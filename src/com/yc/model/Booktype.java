package com.yc.model;

import java.util.List;

public class Booktype {
    
    private String ptId;

    
    private String ptName;

    
    private Short ptFlag;

    
    private String ptSequence;

    private List<Pbook> books;
    
    public String getPtId() {
        return ptId;
    }

    
    public void setPtId(String ptId) {
        this.ptId = ptId == null ? null : ptId.trim();
    }

    
    public String getPtName() {
        return ptName;
    }

    
    public void setPtName(String ptName) {
        this.ptName = ptName == null ? null : ptName.trim();
    }

    
    public Short getPtFlag() {
        return ptFlag;
    }

    
    public void setPtFlag(Short ptFlag) {
        this.ptFlag = ptFlag;
    }

    
    public String getPtSequence() {
        return ptSequence;
    }

    
    public void setPtSequence(String ptSequence) {
        this.ptSequence = ptSequence == null ? null : ptSequence.trim();
    }


	public List<Pbook> getBooks() {
		return books;
	}


	public void setBooks(List<Pbook> books) {
		this.books = books;
	}
}