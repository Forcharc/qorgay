package kz.kmg.qorgau.data.model;

import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchableIdModel implements Searchable {

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

    public int getId() {
        return id;
    }
}
