package com.yc.model;

public class Pbook {
    
    private String pbId;

    
    private String typeId;

    
    private String ownerName;

    
    private String contactName;

    
    private String pbTel;

    
    private Short pbFlag;

    
    private String createTime;

    
    private String creator;

    
    private String modifyTime;

    
    private String modifyor;

    
    private String description;

    
    private String coId;

    
    private String pbLongitude;

    
    private String pbLatitude;

    
    private String pbAddr;

    
    private String pbDescription;

    
    private String typeName;
    
    private String seq;
    
    public String getPbId() {
        return pbId;
    }

    
    public void setPbId(String pbId) {
        this.pbId = pbId == null ? null : pbId.trim();
    }

    
    public String getTypeId() {
        return typeId;
    }

    
    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    
    public String getOwnerName() {
        return ownerName;
    }

    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    
    public String getContactName() {
        return contactName;
    }

    
    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    
    public String getPbTel() {
        return pbTel;
    }

    
    public void setPbTel(String pbTel) {
        this.pbTel = pbTel == null ? null : pbTel.trim();
    }

    
    public Short getPbFlag() {
        return pbFlag;
    }

    
    public void setPbFlag(Short pbFlag) {
        this.pbFlag = pbFlag;
    }

    
    public String getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    
    public String getCreator() {
        return creator;
    }

    
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    
    public String getModifyTime() {
        return modifyTime;
    }

    
    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime == null ? null : modifyTime.trim();
    }

    
    public String getModifyor() {
        return modifyor;
    }

    
    public void setModifyor(String modifyor) {
        this.modifyor = modifyor == null ? null : modifyor.trim();
    }

    
    public String getDescription() {
        return description;
    }

    
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    
    public String getCoId() {
        return coId;
    }

    
    public void setCoId(String coId) {
        this.coId = coId == null ? null : coId.trim();
    }

    
    public String getPbLongitude() {
        return pbLongitude;
    }

    
    public void setPbLongitude(String pbLongitude) {
        this.pbLongitude = pbLongitude == null ? null : pbLongitude.trim();
    }

    
    public String getPbLatitude() {
        return pbLatitude;
    }

    
    public void setPbLatitude(String pbLatitude) {
        this.pbLatitude = pbLatitude == null ? null : pbLatitude.trim();
    }

    
    public String getPbAddr() {
        return pbAddr;
    }

    
    public void setPbAddr(String pbAddr) {
        this.pbAddr = pbAddr == null ? null : pbAddr.trim();
    }

    
    public String getPbDescription() {
        return pbDescription;
    }

    
    public void setPbDescription(String pbDescription) {
        this.pbDescription = pbDescription == null ? null : pbDescription.trim();
    }


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getSeq() {
		return seq;
	}


	public void setSeq(String seq) {
		this.seq = seq;
	}
}