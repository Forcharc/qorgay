package kz.kmg.qorgau.data.model.user;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProfileModel{

	@SerializedName("UserName")
	private String userName;

	@SerializedName("Email")
	private String email;

	@SerializedName("Photo")
	private List<Object> photo;

	@SerializedName("Gender")
	private int gender;

	@SerializedName("City")
	private Object city;

	@SerializedName("Name")
	private String name;

	@SerializedName("Skype")
	private String skype;

	@SerializedName("CityId")
	private Object cityId;

	@SerializedName("FullName")
	private String fullName;

	@SerializedName("PhoneNumberSecond")
	private Object phoneNumberSecond;

	@SerializedName("GroupsList")
	private Object groupsList;

	@SerializedName("PhoneNumber")
	private String phoneNumber;

	@SerializedName("PhoneNumberThird")
	private Object phoneNumberThird;

	@SerializedName("Id")
	private int id;

	@SerializedName("Patronymic")
	private String patronymic;

	@SerializedName("Surname")
	private String surname;

	@SerializedName("BirthDate")
	private String birthDate;

	public String getUserName(){
		return userName;
	}

	public String getEmail(){
		return email;
	}

	public List<Object> getPhoto(){
		return photo;
	}

	public int getGender(){
		return gender;
	}

	public Object getCity(){
		return city;
	}

	public String getName(){
		return name;
	}

	public String getSkype(){
		return skype;
	}

	public Object getCityId(){
		return cityId;
	}

	public String getFullName(){
		return fullName;
	}

	public Object getPhoneNumberSecond(){
		return phoneNumberSecond;
	}

	public Object getGroupsList(){
		return groupsList;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public Object getPhoneNumberThird(){
		return phoneNumberThird;
	}

	public int getId(){
		return id;
	}

	public String getPatronymic(){
		return patronymic;
	}

	public String getSurname(){
		return surname;
	}

	public String getBirthDate(){
		return birthDate;
	}

	public Long getBirthDateLong() {
		if (birthDate != null) {
			return Long.parseLong(birthDate.substring(6, birthDate.length() - 2));
		} else {
			return null;
		}
	}
}