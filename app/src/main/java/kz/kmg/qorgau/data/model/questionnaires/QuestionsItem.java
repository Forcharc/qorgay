package kz.kmg.qorgau.data.model.questionnaires;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class QuestionsItem{

	@SerializedName("Type")
	private Integer type;

	@SerializedName("AnswerVariants")
	private List<AnswerVariantsItem> answerVariants;

	@SerializedName("Text")
	private String text;

	@SerializedName("Id")
	private Integer id;

	public void setSelectedAnswer(Integer selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	@SerializedName("SelectedAnswer")
	private Integer selectedAnswer;

	public Integer getType(){
		return type;
	}

	public List<AnswerVariantsItem> getAnswerVariants(){
		return answerVariants;
	}

	public String getText(){
		return text;
	}

	public Integer getId(){
		return id;
	}

	public Integer getSelectedAnswer(){
		return selectedAnswer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof QuestionsItem)) return false;
		QuestionsItem that = (QuestionsItem) o;
		return type == that.type &&
				id == that.id &&
				selectedAnswer == that.selectedAnswer &&
				Objects.equals(answerVariants, that.answerVariants) &&
				Objects.equals(text, that.text);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, answerVariants, text, id, selectedAnswer);
	}
}