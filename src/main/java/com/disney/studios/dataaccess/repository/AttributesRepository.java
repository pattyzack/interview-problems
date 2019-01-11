/*
 * Copyright (c) 2019. copyright text here
 */

package com.disney.studios.dataaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.disney.studios.dataaccess.entity.AttributeEntity;

@Repository
public interface AttributesRepository extends JpaRepository<AttributeEntity, Long> {

}