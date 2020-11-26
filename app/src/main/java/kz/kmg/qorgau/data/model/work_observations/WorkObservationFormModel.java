package kz.kmg.qorgau.data.model.work_observations;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class WorkObservationFormModel implements Serializable {

	@SerializedName("Task")
	private String task;

	@SerializedName("DateObservation")
	private String dateObservation;

	@SerializedName("Date")
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@SerializedName("Time")
	private String time;

	@SerializedName("OrganizationDepartmentId")
	private Integer organizationDepartmentId;

	@SerializedName("Category5")
	private Integer category5;

	@SerializedName("Category4")
	private Integer category4;

	@SerializedName("Category3")
	private Integer category3;

	@SerializedName("Category2")
	private Integer category2;

	@SerializedName("Category1")
	private Integer category1;

	@SerializedName("Comment24")
	private String comment24;

	@SerializedName("Comment23")
	private String comment23;

	@SerializedName("Category9")
	private Integer category9;

	@SerializedName("Category8")
	private Integer category8;

	@SerializedName("Category7")
	private Integer category7;

	@SerializedName("Category6")
	private Integer category6;

	@SerializedName("PeopleCount")
	private Integer peopleCount;

	@SerializedName("Comment1")
	private String comment1;

	@SerializedName("PlaceId")
	private Integer placeId;

	@SerializedName("Comment2")
	private String comment2;

	@SerializedName("AuthorOrganizationName")
	private String authorOrganizationName;

	@SerializedName("Comment5")
	private String comment5;

	@SerializedName("Category10")
	private Integer category10;

	@SerializedName("Comment6")
	private String comment6;

	@SerializedName("Comment3")
	private String comment3;

	@SerializedName("Category12")
	private Integer category12;

	@SerializedName("Comment4")
	private String comment4;

	@SerializedName("Category11")
	private Integer category11;

	@SerializedName("Comment9")
	private String comment9;

	@SerializedName("Comment7")
	private String comment7;

	@SerializedName("Comment8")
	private String comment8;

	@SerializedName("AuthorOrganizationId")
	private Integer authorOrganizationId;

	@SerializedName("PlaceItemId")
	private Integer placeItemId;

	@SerializedName("Id")
	private Integer id;

	@SerializedName("AuthorFullname")
	private String authorFullname;

	@SerializedName("Category21")
	private Integer category21;

	@SerializedName("Category20")
	private Integer category20;

	@SerializedName("Category23")
	private Integer category23;

	@SerializedName("Category22")
	private Integer category22;

	@SerializedName("Comment11")
	private String comment11;

	@SerializedName("OrganizationId")
	private Integer organizationId;

	@SerializedName("Comment10")
	private String comment10;

	@SerializedName("Category14")
	private Integer category14;

	@SerializedName("Category13")
	private Integer category13;

	@SerializedName("Category16")
	private Integer category16;

	@SerializedName("Category15")
	private Integer category15;

	@SerializedName("Category18")
	private Integer category18;

	@SerializedName("Category17")
	private Integer category17;

	@SerializedName("Category19")
	private Integer category19;

	@SerializedName("Comment20")
	private String comment20;

	@SerializedName("Comment22")
	private String comment22;

	@SerializedName("Comment21")
	private String comment21;

	@SerializedName("AuthorOrganizationDepartmentId")
	private Integer authorOrganizationDepartmentId;

	@SerializedName("Comment17")
	private String comment17;

	@SerializedName("Comment16")
	private String comment16;

	@SerializedName("Comment19")
	private String comment19;

	@SerializedName("Comment18")
	private String comment18;

	@SerializedName("Comment13")
	private String comment13;

	@SerializedName("Comment12")
	private String comment12;

	@SerializedName("Comment15")
	private String comment15;

	@SerializedName("Comment14")
	private String comment14;

	@SerializedName("AuthorOrganizationDepartmentName")
	private String authorOrganizationDepartmentName;

	@SerializedName("Category24")
	private Integer category24;

	@SerializedName("AnswerCategories")
	private List<AnswerCategoriesItem> answerCategories;

	public String getTask(){
		return task;
	}

	public String getDateObservation(){
		return dateObservation;
	}

	public Integer getOrganizationDepartmentId(){
		return organizationDepartmentId;
	}

	public Integer getCategory5(){
		return category5;
	}

	public Integer getCategory4(){
		return category4;
	}

	public Integer getCategory3(){
		return category3;
	}

	public Integer getCategory2(){
		return category2;
	}

	public Integer getCategory1(){
		return category1;
	}

	public String getComment24(){
		return comment24;
	}

	public String getComment23(){
		return comment23;
	}

	public Integer getCategory9(){
		return category9;
	}

	public Integer getCategory8(){
		return category8;
	}

	public Integer getCategory7(){
		return category7;
	}

	public Integer getCategory6(){
		return category6;
	}

	public Integer getPeopleCount(){
		return peopleCount;
	}

	public String getComment1(){
		return comment1;
	}

	public Integer getPlaceId(){
		return placeId;
	}

	public String getComment2(){
		return comment2;
	}

	public String getAuthorOrganizationName(){
		return authorOrganizationName;
	}

	public String getComment5(){
		return comment5;
	}

	public Integer getCategory10(){
		return category10;
	}

	public String getComment6(){
		return comment6;
	}

	public String getComment3(){
		return comment3;
	}

	public Integer getCategory12(){
		return category12;
	}

	public String getComment4(){
		return comment4;
	}

	public Integer getCategory11(){
		return category11;
	}

	public String getComment9(){
		return comment9;
	}

	public String getComment7(){
		return comment7;
	}

	public String getComment8(){
		return comment8;
	}

	public Integer getAuthorOrganizationId(){
		return authorOrganizationId;
	}

	public Integer getPlaceItemId(){
		return placeItemId;
	}

	public Integer getId(){
		return id;
	}

	public String getAuthorFullname(){
		return authorFullname;
	}

	public Integer getCategory21(){
		return category21;
	}

	public Integer getCategory20(){
		return category20;
	}

	public Integer getCategory23(){
		return category23;
	}

	public Integer getCategory22(){
		return category22;
	}

	public String getComment11(){
		return comment11;
	}

	public Integer getOrganizationId(){
		return organizationId;
	}

	public String getComment10(){
		return comment10;
	}

	public Integer getCategory14(){
		return category14;
	}

	public Integer getCategory13(){
		return category13;
	}

	public Integer getCategory16(){
		return category16;
	}

	public Integer getCategory15(){
		return category15;
	}

	public Integer getCategory18(){
		return category18;
	}

	public Integer getCategory17(){
		return category17;
	}

	public Integer getCategory19(){
		return category19;
	}

	public String getComment20(){
		return comment20;
	}

	public String getComment22(){
		return comment22;
	}

	public String getComment21(){
		return comment21;
	}

	public Integer getAuthorOrganizationDepartmentId(){
		return authorOrganizationDepartmentId;
	}

	public String getComment17(){
		return comment17;
	}

	public String getComment16(){
		return comment16;
	}

	public String getComment19(){
		return comment19;
	}

	public String getComment18(){
		return comment18;
	}

	public String getComment13(){
		return comment13;
	}

	public String getComment12(){
		return comment12;
	}

	public String getComment15(){
		return comment15;
	}

	public String getComment14(){
		return comment14;
	}

	public String getAuthorOrganizationDepartmentName(){
		return authorOrganizationDepartmentName;
	}

	public Integer getCategory24(){
		return category24;
	}

	public List<AnswerCategoriesItem> getAnswerCategories(){
		return answerCategories;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public void setDateObservation(String dateObservation) {
		this.dateObservation = dateObservation;
	}

	public void setOrganizationDepartmentId(Integer organizationDepartmentId) {
		this.organizationDepartmentId = organizationDepartmentId;
	}

	public void setCategory5(Integer category5) {
		this.category5 = category5;
	}

	public void setCategory4(Integer category4) {
		this.category4 = category4;
	}

	public void setCategory3(Integer category3) {
		this.category3 = category3;
	}

	public void setCategory2(Integer category2) {
		this.category2 = category2;
	}

	public void setCategory1(Integer category1) {
		this.category1 = category1;
	}

	public void setComment24(String comment24) {
		this.comment24 = comment24;
	}

	public void setComment23(String comment23) {
		this.comment23 = comment23;
	}

	public void setCategory9(Integer category9) {
		this.category9 = category9;
	}

	public void setCategory8(Integer category8) {
		this.category8 = category8;
	}

	public void setCategory7(Integer category7) {
		this.category7 = category7;
	}

	public void setCategory6(Integer category6) {
		this.category6 = category6;
	}

	public void setPeopleCount(Integer peopleCount) {
		this.peopleCount = peopleCount;
	}

	public void setComment1(String comment1) {
		this.comment1 = comment1;
	}

	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}

	public void setComment2(String comment2) {
		this.comment2 = comment2;
	}

	public void setAuthorOrganizationName(String authorOrganizationName) {
		this.authorOrganizationName = authorOrganizationName;
	}

	public void setComment5(String comment5) {
		this.comment5 = comment5;
	}

	public void setCategory10(Integer category10) {
		this.category10 = category10;
	}

	public void setComment6(String comment6) {
		this.comment6 = comment6;
	}

	public void setComment3(String comment3) {
		this.comment3 = comment3;
	}

	public void setCategory12(Integer category12) {
		this.category12 = category12;
	}

	public void setComment4(String comment4) {
		this.comment4 = comment4;
	}

	public void setCategory11(Integer category11) {
		this.category11 = category11;
	}

	public void setComment9(String comment9) {
		this.comment9 = comment9;
	}

	public void setComment7(String comment7) {
		this.comment7 = comment7;
	}

	public void setComment8(String comment8) {
		this.comment8 = comment8;
	}

	public void setAuthorOrganizationId(Integer authorOrganizationId) {
		this.authorOrganizationId = authorOrganizationId;
	}

	public void setPlaceItemId(Integer placeItemId) {
		this.placeItemId = placeItemId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAuthorFullname(String authorFullname) {
		this.authorFullname = authorFullname;
	}

	public void setCategory21(Integer category21) {
		this.category21 = category21;
	}

	public void setCategory20(Integer category20) {
		this.category20 = category20;
	}

	public void setCategory23(Integer category23) {
		this.category23 = category23;
	}

	public void setCategory22(Integer category22) {
		this.category22 = category22;
	}

	public void setComment11(String comment11) {
		this.comment11 = comment11;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public void setComment10(String comment10) {
		this.comment10 = comment10;
	}

	public void setCategory14(Integer category14) {
		this.category14 = category14;
	}

	public void setCategory13(Integer category13) {
		this.category13 = category13;
	}

	public void setCategory16(Integer category16) {
		this.category16 = category16;
	}

	public void setCategory15(Integer category15) {
		this.category15 = category15;
	}

	public void setCategory18(Integer category18) {
		this.category18 = category18;
	}

	public void setCategory17(Integer category17) {
		this.category17 = category17;
	}

	public void setCategory19(Integer category19) {
		this.category19 = category19;
	}

	public void setComment20(String comment20) {
		this.comment20 = comment20;
	}

	public void setComment22(String comment22) {
		this.comment22 = comment22;
	}

	public void setComment21(String comment21) {
		this.comment21 = comment21;
	}

	public void setAuthorOrganizationDepartmentId(Integer authorOrganizationDepartmentId) {
		this.authorOrganizationDepartmentId = authorOrganizationDepartmentId;
	}

	public void setComment17(String comment17) {
		this.comment17 = comment17;
	}

	public void setComment16(String comment16) {
		this.comment16 = comment16;
	}

	public void setComment19(String comment19) {
		this.comment19 = comment19;
	}

	public void setComment18(String comment18) {
		this.comment18 = comment18;
	}

	public void setComment13(String comment13) {
		this.comment13 = comment13;
	}

	public void setComment12(String comment12) {
		this.comment12 = comment12;
	}

	public void setComment15(String comment15) {
		this.comment15 = comment15;
	}

	public void setComment14(String comment14) {
		this.comment14 = comment14;
	}

	public void setAuthorOrganizationDepartmentName(String authorOrganizationDepartmentName) {
		this.authorOrganizationDepartmentName = authorOrganizationDepartmentName;
	}

	public void setCategory24(Integer category24) {
		this.category24 = category24;
	}

	public void setAnswerCategories(List<AnswerCategoriesItem> answerCategories) {
		this.answerCategories = answerCategories;
	}
}