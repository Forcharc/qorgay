package kz.kmg.qorgau.data.model.questionnaires;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AnswerVariantsItem{

	@SerializedName("Text")
	private String text;

	@SerializedName("IsSelected")
	private boolean isSelected;

	@SerializedName("Id")
	private Integer id;

	public String getText(){
		return text;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public boolean isIsSelected(){
		return isSelected;
	}

	public Integer getId(){
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AnswerVariantsItem)) return false;
		AnswerVariantsItem that = (AnswerVariantsItem) o;
		return isSelected == that.isSelected &&
				id == that.id &&
				Objects.equals(text, that.text);
	}

	@Override
	public int hashCode() {
		return Objects.hash(text, isSelected, id);
	}
}