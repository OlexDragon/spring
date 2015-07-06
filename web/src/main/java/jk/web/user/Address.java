package jk.web.user;


public class Address {

	public enum AddressType{

		HOME,
		WORK;
	}

	private AddressType addressType;

	//thymeleaf fields
	private String address;
	private String addressError;

	private String city;
	private String cityError;

	private String regionName;
	private String region;

	private String countryCode;
	private String countryCodeError;

	private String postalCode;
	private String postalCodeError;

	private String mapPath;
	private String regionCodeError;

	private boolean showAddress;

	private boolean editAddress;

	public Address() { }

	public Address(AddressType addressType) {
		this.addressType = addressType;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public Address setAddressType(AddressType addressType) {
		this.addressType = addressType;
		return this;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String regionCode) {
		this.region = regionCode;
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
		return "Address [addressType=" + addressType + ", address=" + address + ", addressError=" + addressError + ", city=" + city + ", cityError=" + cityError + ", regionName="
				+ regionName + ", regionCode=" + region + ", countryCode=" + countryCode + ", countryCodeError=" + countryCodeError + ", postalCode=" + postalCode
				+ ", postalCodeError=" + postalCodeError + ", mapPath=" + mapPath + ", regionCodeError=" + regionCodeError + ", showAddress="
				+ showAddress + ", editAddress=" + editAddress + "]";
	}
}
