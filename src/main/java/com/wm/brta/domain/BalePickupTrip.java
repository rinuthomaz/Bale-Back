package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="[balepickuptrip]")
public class BalePickupTrip 
{
	
	
	private Integer tripId;
	private BalePickupAssignment balePickupAssignment;
	private CustomerSite buyCustomerSite;
	private Material material;
	private Integer buyLegacyVendorId;
	private Integer CompanyoutletId;
	private Integer balesPicked;
	private Integer balesRemaining;
	private Date pickupDate;
	private Float averageBaleWeight;
	private Float pickedBaleTotalWeight;
	private CustomerSite sellCustomerSite;
	private Integer sellLegacyVendorId;
	private Integer BOL;
	private boolean incident;
	private IncidentType incidentType;
	private String Comments;
	private Date createDate;
	private Date updatedAt;
	private boolean enabled;
	private Supplier supplier;
	private Integer weekNumber;
	private Integer day;
	private User user;
	private String loadTripId;
	private String childLoadTripId;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tripid",nullable=false,unique=true)
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	
	@JoinColumn(name="balepickupassignmentid",nullable = false)
	public BalePickupAssignment getBalePickupAssignment() {
		return balePickupAssignment;
	}
	public void setBalePickupAssignment(BalePickupAssignment balePickupAssignment) {
		this.balePickupAssignment = balePickupAssignment;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="buycustomersiteid",nullable=false)
	public CustomerSite getBuyCustomerSite() {
		return buyCustomerSite;
	}
	public void setBuyCustomerSite(CustomerSite buyCustomerSite) {
		this.buyCustomerSite = buyCustomerSite;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="materialid",nullable=false)
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	@Column(name="buylegacyvendorid",nullable =false)
	public Integer getBuyLegacyVendorId() {
		return buyLegacyVendorId;
	}
	public void setBuyLegacyVendorId(Integer buyLegacyVendorId) {
		this.buyLegacyVendorId = buyLegacyVendorId;
	}
	@Column(name="companyoutletid",nullable =false)
	public Integer getCompanyoutletId() {
		return CompanyoutletId;
	}
	public void setCompanyoutletId(Integer companyoutletId) {
		CompanyoutletId = companyoutletId;
	}
	@Column(name="balespicked",nullable =false)
	public Integer getBalesPicked() {
		return balesPicked;
	}
	public void setBalesPicked(Integer balesPicked) {
		this.balesPicked = balesPicked;
	}
	@Column(name="balesremaining",nullable =false)
	public Integer getBalesRemaining() {
		return balesRemaining;
	}
	public void setBalesRemaining(Integer balesRemaining) {
		
		this.balesRemaining = balesRemaining;
	}
	
	@Column(name="pickupdate",nullable =false)
	public Date getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	@Column(name="avgbaleweight",nullable =false)
	public Float getAverageBaleWeight() {
		return averageBaleWeight;
	}
	public void setAverageBaleWeight(Float averageBaleWeight) {
		this.averageBaleWeight = averageBaleWeight;
	}
	@Column(name="pickedbaletotalweight",nullable =false)
	public Float getPickedBaleTotalWeight() {
		return pickedBaleTotalWeight;
	}
	public void setPickedBaleTotalWeight(Float pickedBaleTotalWeight) {
		this.pickedBaleTotalWeight = pickedBaleTotalWeight;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sellcustomersiteid",nullable=false)
	public CustomerSite getSellCustomerSite() {
		return sellCustomerSite;
	}
	public void setSellCustomerSite(CustomerSite sellCustomerSite) {
		this.sellCustomerSite = sellCustomerSite;
	}
	@Column(name="selllegacyvendorid",nullable =false)
	public Integer getSellLegacyVendorId() {
		return sellLegacyVendorId;
	}
	public void setSellLegacyVendorId(Integer sellLegacyVendorId) {
		this.sellLegacyVendorId = sellLegacyVendorId;
	}
	@Column(name="bol",nullable =false)
	public Integer getBOL() {
		return BOL;
	}
	public void setBOL(Integer bOL) {
		BOL = bOL;
	}
	@Column(name="incident",nullable =false)
	public boolean isIncident() {
		return incident;
	}
	public void setIncident(boolean incident) {
		this.incident = incident;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="incidenttype",nullable = false)
	public IncidentType getIncidentType() {
		return incidentType;
	}
	public void setIncidentType(IncidentType incidentType) {
		this.incidentType = incidentType;
	}
	@Column(name="comments",nullable =false)
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	@Column(name="createdate",nullable =false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="updatedat",nullable =false)
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name="enabled",nullable =false)
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="supplierid",nullable = false)
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	@Column(name="weeknumber",nullable =false)
	public Integer getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}
	@Column(name="day",nullable =false)
	public Integer getDay() {
		return day;
	}
	
	public void setDay(Integer day) {
		this.day = day;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid",nullable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name="loadtripid")
	public String getLoadTripId() {
		return loadTripId;
	}
	public void setLoadTripId(String loadTripId) {
		this.loadTripId = loadTripId;
	}
	@Column(name="childloadtripid")
	public String getChildLoadTripId() {
		return childLoadTripId;
	}
	public void setChildLoadTripId(String childLoadTripId) {
		this.childLoadTripId = childLoadTripId;
	}
	@Override
	public String toString() {
		return "BalePickupTrip [tripId=" + tripId + ", balePickupAssignment="
				+ balePickupAssignment + ", buyCustomerSite=" + buyCustomerSite
				+ ", material=" + material + ", buyLegacyVendorId="
				+ buyLegacyVendorId + ", CompanyoutletId=" + CompanyoutletId
				+ ", balesPicked=" + balesPicked + ", balesRemaining="
				+ balesRemaining + ", pickupDate=" + pickupDate
				+ ", averageBaleWeight=" + averageBaleWeight
				+ ", pickedBaleTotalWeight=" + pickedBaleTotalWeight
				+ ", sellCustomerSite=" + sellCustomerSite
				+ ", sellLegacyVendorId=" + sellLegacyVendorId + ", BOL=" + BOL
				+ ", incident=" + incident + ", incidentType=" + incidentType
				+ ", Comments=" + Comments + ", createDate=" + createDate
				+ ", updatedAt=" + updatedAt + ", enabled=" + enabled
				+ ", supplier=" + supplier + ", weekNumber=" + weekNumber
				+ ", day=" + day + ", user=" + user + ", loadTripId="
				+ loadTripId + ", childLoadTripId=" + childLoadTripId + "]";
	}
	
	
	
	
	
	

}
