package com.handson.backend.model;


import java.util.Objects;

public class Pagination {
    private Integer page;
    private Integer ofPage;
    private Integer count;

    public Pagination() {
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getOfPage() {
        return this.ofPage;
    }

    public Integer getCount() {
        return this.count;
    }

    public static Pagination of(Integer page, Integer ofPage, Integer count) {
        Pagination res = new Pagination();
        res.page = page;
        res.ofPage = ofPage;
        res.count = count;
        return res;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Pagination that = (Pagination)o;
            return Objects.equals(this.page, that.page) && Objects.equals(this.ofPage, that.ofPage) && Objects.equals(this.count, that.count);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.page, this.ofPage, this.count});
    }

    public String toString() {
        return "Pagination{page=" + this.page + ", ofPage=" + this.ofPage + ", count=" + this.count + "}";
    }
}
