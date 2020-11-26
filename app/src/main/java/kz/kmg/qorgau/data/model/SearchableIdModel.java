package kz.kmg.qorgau.data.model;

import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchableIdModel implements SearchableId {

    private String title;
    private int id;

    public SearchableIdModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getId() {
        return id;
    }
}
