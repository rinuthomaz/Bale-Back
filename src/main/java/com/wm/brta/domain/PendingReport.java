package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name ="Pendingreport")

public class PendingReport {
	
	
	@Id
	@Column(name = "balepickupassignmentid")
	private Integer balePickupAssignmentId;
	
	@Column(name = "driver")
	private Integer driver;
	
	@Column(name = "supplierid")
	private Integer supplierId;

	@Column(name = "weeknumber")
	private Integer weekNumber;
	
	@Column(name = "day")
	private Integer day;
	
	@Column(name = "divertdate")
	private Date divertDate;
	
	@Column(name = "diverteduserid")
	private Integer divertedUserId;
	
	@Column(name = "buycustomersiteid")
	private Integer buyCustomerSiteId;
	
	@Column(name = "sellcustomersiteid")
	private Integer sellCustomerSiteId;
	
	@Column(name = "buycustomersitename")
	private String buyCustomerSiteName;
	
	@Column(name = "buycustomerlocationid")
	private Integer buyCustomerLocationId;
	
	@Column(name = "buycustomerhousenumber")
	private String buyCustomerHouseNumber;
	
	@Column(name = "buycustomeraddress1")
	private String buyCustomerAddress1;
	
	@Column(name = "buycustomeraddress2")
	private String buyCustomerAddress2;
	
	@Column(name = "buycustomeraddress3")
	private String buyCustomerAddress3;
	
	@Column(name = "buycustomeraddress4")
	private String buyCustomerAddress4;
	
	@Column(name = "buycustomeraddress5")
	private String buyCustomerAddress5;
	
	@Column(name = "buycustomerpostcode")
	private String buyCustomerPostCode;
	
	@Column(name = "buycustomertelno")
	private String buyCustomerTelNo;
	
	@Column(name = "buycustomerid")
	private Integer buyCustomerId;
	
	@Column(name = "buycustomername")
	private String buyCustomerName;
	
	@Column(name = "buycustomeralternativesearchreference")
	private String buyCustomerAlternativeSearchReference;
	
	@Column(name = "tripdriverfirstname")
	private String tripDriverFirstName;
	
	@Column(name = "tripdriverlastname")
	private String tripDriverLastName;
	
	@Column(name = "tripdriveremail")
	private String tripDriverEmail;
	
	@Column(name = "tripdrivermobile")
	private String tripDriverMobile;
	
	@Column(name = "diverttripdriverfirstname")
	private String divertTripDriverFirstName;
	
	@Column(name = "diverttripdriverlastname")
	private String divertTripDriverLastName;
	
	@Column(name = "diverttripdriveremail")
	private String divertTripDriverEmail;
	
	@Column(name = "diverttripdrivermobile")
	private String divertTripDriverMobile;
	
	public String getDivertTripDriverEmail() {
		return divertTripDriverEmail;
	}

	public void setDivertTripDriverEmail(String divertTripDriverEmail) {
		this.divertTripDriverEmail = divertTripDriverEmail;
	}

	public String getDivertTripDriverMobile() {
		return divertTripDriverMobile;
	}

	public void setDivertTripDriverMobile(String divertTripDriverMobile) {
		this.divertTripDriverMobile = divertTripDriverMobile;
	}

	public String getDivertTripDriverFirstName() {
		return divertTripDriverFirstName;
	}

	public void setDivertTripDriverFirstName(String divertTripDriverFirstName) {
		this.divertTripDriverFirstName = divertTripDriverFirstName;
	}

	public String getDivertTripDriverLastName() {
		return divertTripDriverLastName;
	}

	public void setDivertTripDriverLastName(String divertTripDriverLastName) {
		this.divertTripDriverLastName = divertTripDriverLastName;
	}

	@Transient
	private String pickupDate;

	public String getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}

	public Integer getBalePickupAssignmentId() {
		return balePickupAssignmentId;
	}

	public void setBalePickupAssignmentId(Integer balePickupAssignmentId) {
		this.balePickupAssignmentId = balePickupAssignmentId;
	}

	public Integer getDriver() {
		return driver;
	}

	public void setDriver(Integer driver) {
		this.driver = driver;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Date getDivertDate() {
		return divertDate;
	}

	public void setDivertDate(Date divertDate) {
		this.divertDate = divertDate;
	}

	public Integer getDivertedUserId() {
		return divertedUserId;
	}

	public void setDivertedUserId(Integer divertedUserId) {
		this.divertedUserId = divertedUserId;
	}

	public Integer getBuyCustomerSiteId() {
		return buyCustomerSiteId;
	}

	public void setBuyCustomerSiteId(Integer buyCustomerSiteId) {
		this.buyCustomerSiteId = buyCustomerSiteId;
	}

	public Integer getSellCustomerSiteId() {
		return sellCustomerSiteId;
	}

	public void setSellCustomerSiteId(Integer sellCustomerSiteId) {
		this.sellCustomerSiteId = sellCustomerSiteId;
	}

	public String getBuyCustomerSiteName() {
		return buyCustomerSiteName;
	}

	public void setBuyCustomerSiteName(String buyCustomerSiteName) {
		this.buyCustomerSiteName = buyCustomerSiteName;
	}

	public Integer getBuyCustomerLocationId() {
		return buyCustomerLocationId;
	}

	public void setBuyCustomerLocationId(Integer buyCustomerLocationId) {
		this.buyCustomerLocationId = buyCustomerLocationId;
	}

	public String getBuyCustomerHouseNumber() {
		return buyCustomerHouseNumber;
	}

	public void setBuyCustomerHouseNumber(String buyCustomerHouseNumber) {
		this.buyCustomerHouseNumber = buyCustomerHouseNumber;
	}

	public String getBuyCustomerAddress1() {
		return buyCustomerAddress1;
	}

	public void setBuyCustomerAddress1(String buyCustomerAddress1) {
		this.buyCustomerAddress1 = buyCustomerAddress1;
	}

	public String getBuyCustomerAddress2() {
		return buyCustomerAddress2;
	}

	public void setBuyCustomerAddress2(String buyCustomerAddress2) {
		this.buyCustomerAddress2 = buyCustomerAddress2;
	}

	public String getBuyCustomerAddress3() {
		return buyCustomerAddress3;
	}

	public void setBuyCustomerAddress3(String buyCustomerAddress3) {
		this.buyCustomerAddress3 = buyCustomerAddress3;
	}

	public String getBuyCustomerAddress4() {
		return buyCustomerAddress4;
	}

	public void setBuyCustomerAddress4(String buyCustomerAddress4) {
		this.buyCustomerAddress4 = buyCustomerAddress4;
	}

	public String getBuyCustomerAddress5() {
		return buyCustomerAddress5;
	}

	public void setBuyCustomerAddress5(String buyCustomerAddress5) {
		this.buyCustomerAddress5 = buyCustomerAddress5;
	}

	public String getBuyCustomerPostCode() {
		return buyCustomerPostCode;
	}

	public void setBuyCustomerPostCode(String buyCustomerPostCode) {
		this.buyCustomerPostCode = buyCustomerPostCode;
	}

	public String getBuyCustomerTelNo() {
		return buyCustomerTelNo;
	}

	public void setBuyCustomerTelNo(String buyCustomerTelNo) {
		this.buyCustomerTelNo = buyCustomerTelNo;
	}

	public Integer getBuyCustomerId() {
		return buyCustomerId;
	}

	public void setBuyCustomerId(Integer buyCustomerId) {
		this.buyCustomerId = buyCustomerId;
	}

	public String getBuyCustomerName() {
		return buyCustomerName;
	}

	public void setBuyCustomerName(String buyCustomerName) {
		this.buyCustomerName = buyCustomerName;
	}

	public String getBuyCustomerAlternativeSearchReference() {
		return buyCustomerAlternativeSearchReference;
	}

	public void setBuyCustomerAlternativeSearchReference(
			String buyCustomerAlternativeSearchReference) {
		this.buyCustomerAlternativeSearchReference = buyCustomerAlternativeSearchReference;
	}

	public String getTripDriverFirstName() {
		return tripDriverFirstName;
	}

	public void setTripDriverFirstName(String tripDriverFirstName) {
		this.tripDriverFirstName = tripDriverFirstName;
	}

	public String getTripDriverLastName() {
		return tripDriverLastName;
	}

	public void setTripDriverLastName(String tripDriverLastName) {
		this.tripDriverLastName = tripDriverLastName;
	}

	public String getTripDriverEmail() {
		return tripDriverEmail;
	}

	public void setTripDriverEmail(String tripDriverEmail) {
		this.tripDriverEmail = tripDriverEmail;
	}

	public String getTripDriverMobile() {
		return tripDriverMobile;
	}

	public void setTripDriverMobile(String tripDriverMobile) {
		this.tripDriverMobile = tripDriverMobile;
	}

	@Override
	public String toString() {
		return "PendingReport [balePickupAssignmentId="
				+ balePickupAssignmentId + ", driver=" + driver
				+ ", supplierId=" + supplierId + ", weekNumber=" + weekNumber
				+ ", day=" + day + ", divertDate=" + divertDate
				+ ", divertedUserId=" + divertedUserId + ", buyCustomerSiteId="
				+ buyCustomerSiteId + ", sellCustomerSiteId="
				+ sellCustomerSiteId + ", buyCustomerSiteName="
				+ buyCustomerSiteName + ", buyCustomerLocationId="
				+ buyCustomerLocationId + ", buyCustomerHouseNumber="
				+ buyCustomerHouseNumber + ", buyCustomerAddress1="
				+ buyCustomerAddress1 + ", buyCustomerAddress2="
				+ buyCustomerAddress2 + ", buyCustomerAddress3="
				+ buyCustomerAddress3 + ", buyCustomerAddress4="
				+ buyCustomerAddress4 + ", buyCustomerAddress5="
				+ buyCustomerAddress5 + ", buyCustomerPostCode="
				+ buyCustomerPostCode + ", buyCustomerTelNo="
				+ buyCustomerTelNo + ", buyCustomerId=" + buyCustomerId
				+ ", buyCustomerName=" + buyCustomerName
				+ ", buyCustomerAlternativeSearchReference="
				+ buyCustomerAlternativeSearchReference
				+ ", tripDriverFirstName=" + tripDriverFirstName
				+ ", tripDriverLastName=" + tripDriverLastName
				+ ", tripDriverEmail=" + tripDriverEmail
				+",diverTripDriverFirstName=" + divertTripDriverFirstName
				+",divertTripDriverLastName=" + divertTripDriverLastName
				+",divertTripDriverEmail=" + divertTripDriverEmail
				+",divertTripDriverMobile=" + divertTripDriverMobile
				+ ", tripDriverMobile=" + tripDriverMobile + ", pickupDate="
				+ pickupDate + "]";
	}

	
	
}
