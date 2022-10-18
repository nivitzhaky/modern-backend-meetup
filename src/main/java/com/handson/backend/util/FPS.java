package com.handson.backend.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.backend.model.Pagination;
import com.handson.backend.model.PaginationAndList;
import com.handson.backend.model.SortDirection;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FPS {
    private List<FPSField> select = new ArrayList<>();
    private List<String> from = new ArrayList<>();
    private List<String> joins = new ArrayList<>();
    private List<FPSCondition> conditions = new ArrayList<>();
    private String sortField;
    private SortDirection sortDirection;
    private Integer page;
    private Integer count;
    private Class itemClass;


    TimeZone utcTz = TimeZone.getTimeZone("UTC");
    SimpleDateFormat isoDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
    public FPS() {
        super();
        isoDf.setTimeZone(utcTz);
    }

    public PaginationAndList exec(EntityManager em, ObjectMapper om) throws JsonProcessingException {
        Query qrySelect = em.createNativeQuery(getSelectSql(), itemClass);
        Query qryCount = em.createNativeQuery(getCountSql());
        conditions.forEach(x-> {
            if (x.getValue() != null) {
                qrySelect.setParameter(x.getParameterName(), x.getValue());
                qryCount.setParameter(x.getParameterName(), x.getValue());
            }
        });
        List rows =  qrySelect.getResultList();
        BigInteger total = (BigInteger) qryCount.getSingleResult();
        return PaginationAndList.of(Pagination.of(page, (total.intValue() / count) + 1, total.intValue()) ,rows);
    }

    private String getSelectSql() {
        String fieldsSql = " select " + select.stream().map(f -> f.getField() + " " + f.getAlias()) .collect(Collectors.joining(","));
        String fromSql = getFromSql();
        String whereSql = getWhereSql();
        String orderSql = "";
        if (sortField != null) {
            orderSql = " order by " + sortField + (SortDirection.desc.equals(sortDirection) ? " desc " : "");
        }
        String limitSql = "";
        if (page != null || count != null) {
            if (count != null) limitSql += " limit " + count;
            if (page != null) limitSql += " offset " + (page - 1) * count;
        }
        return fieldsSql + fromSql + whereSql + orderSql + limitSql;
    }

    private String getWhereSql() {
        String whereSql = " where " + joins.stream().collect(Collectors.joining(" and "));
        if (joins.size() > 0) whereSql += " and ";
        whereSql += conditions.stream().filter(x -> x.getValue() != null).map(y -> y.getCondition()).collect(Collectors.joining(" and "));
        if ("where".equals(whereSql.trim())) whereSql = "";
        return whereSql;
    }

    private String getFromSql() {
        return " from " + from.stream().collect(Collectors.joining(","));
    }

    private String getCountSql() {
        String fieldsSql = " select count(*)";
        String fromSql = getFromSql();
        String whereSql = getWhereSql();
        return fieldsSql + fromSql + whereSql ;
    }

    private boolean isaBoolean(Object o) {
        return o instanceof Boolean;
    }

    private boolean isDate(Object o) {
        return o instanceof Date  || o instanceof java.sql.Date || o instanceof Timestamp;
    }

    private boolean isNumeric(Object o) {
        return o instanceof Integer || o instanceof Long || o instanceof Double || o instanceof BigInteger
                || o instanceof BigDecimal || o instanceof Float;
    }

    public List<FPSField> getSelect() {
        return select;
    }

    public List<String> getFrom() {
        return from;
    }

    public List<String> getJoins() {
        return joins;
    }

    public List<FPSCondition> getConditions() {
        return conditions;
    }

    public String getSortField() {
        return sortField;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getCount() {
        return count;
    }


    public static final class FPSBuilder {
        private List<FPSField> select = new ArrayList<>();
        private List<String> from = new ArrayList<>();
        private List<String> joins = new ArrayList<>();
        private List<FPSCondition> conditions = new ArrayList<>();
        private String sortField;
        private SortDirection sortDirection;
        private Integer page;
        private Integer count;
        private Class itemClass;

        private FPSBuilder() {
        }

        public static FPSBuilder aFPS() {
            return new FPSBuilder();
        }

        public FPSBuilder select(List<FPSField> select) {
            this.select = select;
            return this;
        }

        public FPSBuilder from(List<String> from) {
            this.from = from;
            return this;
        }

        public FPSBuilder joins(List<String> joins) {
            this.joins = joins;
            return this;
        }

        public FPSBuilder conditions(List<FPSCondition> conditions) {
            this.conditions = conditions;
            return this;
        }

        public FPSBuilder sortField(String sortField) {
            this.sortField = sortField;
            return this;
        }

        public FPSBuilder sortDirection(SortDirection sortDirection) {
            this.sortDirection = sortDirection;
            return this;
        }

        public FPSBuilder page(Integer page) {
            this.page = page;
            return this;
        }

        public FPSBuilder count(Integer count) {
            this.count = count;
            return this;
        }

        public FPSBuilder itemClass(Class itemClass) {
            this.itemClass = itemClass;
            return this;
        }


        public FPS build() {
            FPS fPS = new FPS();
            fPS.conditions = this.conditions;
            fPS.page = this.page;
            fPS.itemClass = this.itemClass;
            fPS.count = this.count;
            fPS.sortDirection = this.sortDirection;
            fPS.select = this.select;
            fPS.from = this.from;
            fPS.joins = this.joins;
            fPS.sortField = this.sortField;
            return fPS;
        }
    }
}
