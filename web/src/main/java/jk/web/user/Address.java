package jk.web.user;

import java.util.List;

import jk.web.user.entities.RegionEntity;

public class Address {

	private static List<RegionEntity> regions;

	private String title;
	private boolean showAddress;
	private String buttonName;
	private boolean editAddress;

	//thymeleaf fields
	private String address;
	private String addressError;

	private String city;
	private String cityError;

	private String regionName;
	private String regionCode;

	private String countryCode;
	private String countryCodeError;

	private String postalCode;
	private String postalCodeError;

	private String mapPath;
	private String regionCodeError;

	public static List<RegionEntity> getRegions() {
		return regions;
	}

	public static void setRegions(List<RegionEntity> regions) {
		Address.regions = regions;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isShowAddress() {
		return showAddress;
	}

	public void setShowAddress(boolean showAddress) {
		this.showAddress = showAddress;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public boolean isEditAddress() {
		return editAddress;
	}

	public void setEditAddress(boolean editAddress) {
		this.editAddress = editAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressError() {
		return addressError;
	}

	public void setAddressError(String addressError) {
		this.addressError = addressError;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityError() {
		return cityError;
	}

	public void setCityError(String cityError) {
		this.cityError = cityError;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionCodeError() {
		return regionCodeError;
	}

	public void setRegionCodeError(String regionCodeError) {
		this.regionCodeError = regionCodeError;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCodeError() {
		return countryCodeError;
	}

	public void setCountryCodeError(String countryCodeError) {
		this.countryCodeError = countryCodeError;
	}

	public String getPostalCodeError() {
		return postalCodeError;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setPostalCodeError(String postalCodeError) {
		this.postalCodeError = postalCodeError;
	}

	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

	@Override
	public String toString() {
		return "Address [title=" + title + ", showAddress=" + showAddress + ", buttonName=" + buttonName + ", editAddress=" + editAddress + ", address=" + address
				+ ", addressError=" + addressError + ", city=" + city + ", cityError=" + cityError + ", regionName=" + regionName + ", regionCode=" + regionCode + ", countryCode="
				+ countryCode + ", countryCodeError=" + countryCodeError + ", postalCode=" + postalCode + ", postalCodeError=" + postalCodeError + ", mapPath=" + mapPath
				+ ", regionCodeError=" + regionCodeError + "]";
	}
}
