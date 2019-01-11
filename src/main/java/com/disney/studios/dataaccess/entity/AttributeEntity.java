/*
 * Copyright (c) 2019. copyright text here
 */

package com.disney.studios.dataaccess.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "attributes")
public class AttributeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String key;
    private String val;

    public AttributeEntity() {
    }

    public AttributeEntity(Long id, String key, String val) {
        this.id = id;
        this.key = key;
        this.val = val;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "AttributeEntity{" +
            "id=" + id +
            ", key='" + key + '\'' +
            ", val='" + val + '\'' +
            '}';
    }
}
