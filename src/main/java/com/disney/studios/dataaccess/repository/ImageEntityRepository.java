/*
 * Copyright (c) 2019. copyright text here
 */

package com.disney.studios.dataaccess.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.disney.studios.dataaccess.entity.ImageEntity;

@Repository
public interface ImageEntityRepository extends PagingAndSortingRepository<ImageEntity, Long>, JpaRepository<ImageEntity, Long>, JpaSpecificationExecutor<ImageEntity> {

    public Page<ImageEntity> findAllByBreedIgnoreCase(String breed, Pageable pageable);

}
