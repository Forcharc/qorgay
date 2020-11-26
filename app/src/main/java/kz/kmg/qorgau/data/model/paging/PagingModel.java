package kz.kmg.qorgau.data.model.paging;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kz.kmg.qorgau.data.model.observations.WorkObservationModel;

public class PagingModel<T>{

	@SerializedName("TotalCount")
	private int totalCount;

	@SerializedName("PageNum")
	private int pageNum;

	@SerializedName("PageCount")
	private int pageCount;

	@SerializedName("RowMin")
	private int rowMin;

	@SerializedName("List")
	private List<WorkObservationModel> list;

	@SerializedName("RowMax")
	private int rowMax;

	public int getTotalCount(){
		return totalCount;
	}

	public int getPageNum(){
		return pageNum;
	}

	public int getPageCount(){
		return pageCount;
	}

	public int getRowMin(){
		return rowMin;
	}

	public List<WorkObservationModel> getList(){
		return list;
	}

	public int getRowMax(){
		return rowMax;
	}
}