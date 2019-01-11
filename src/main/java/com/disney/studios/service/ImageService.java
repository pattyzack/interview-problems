/*
 * Copyright (c) 2019. copyright text here
 */

package com.disney.studios.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.disney.studios.dataaccess.entity.ImageEntity;
import com.disney.studios.dataaccess.repository.AttributesRepository;
import com.disney.studios.dataaccess.repository.ImageEntityRepository;
import com.disney.studios.dataaccess.repository.SearchCriteria;
import com.disney.studios.rest.exception.ImageResourceNotFoundException;

@Service
public class ImageService {

    @Autowired
    private ImageEntityRepository imageEntityRepository;

    @Autowired
    private AttributesRepository attributesRepository;

    public Page<ImageEntity> getAllImages(String search, Pageable pageable) {
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            String key = null;
            String val = null;
            while (matcher.find()) {
                SearchCriteria searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
                if (searchCriteria.getKey().equalsIgnoreCase("breed")) {
                    return imageEntityRepository.findAllByBreedIgnoreCase(searchCriteria.getValue(), pageable);
                } else {
                    // TODO votes >, <, =
                    // TODO not supported
                }
            }
        }
        return imageEntityRepository.findAll(pageable);

    }

    public Optional<ImageEntity> getImageById(Long id) {
        return imageEntityRepository.findById(id);
    }

    public ImageEntity incrementImageVote(Long id) {
        Optional<ImageEntity> entity = imageEntityRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ImageResourceNotFoundException();
        }
        ImageEntity entityToBeUpdated = new ImageEntity(entity.get());
        entityToBeUpdated.setVotes(entityToBeUpdated.getVotes() + 1);
        return imageEntityRepository.saveAndFlush(entityToBeUpdated);
    }

    public ImageEntity decrementImageVote(Long id) {
        Optional<ImageEntity> entity = imageEntityRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ImageResourceNotFoundException();
        }
        ImageEntity entityToBeUpdated = new ImageEntity(entity.get());

        if (entityToBeUpdated.getVotes() != 0) {
            entityToBeUpdated.setVotes(entityToBeUpdated.getVotes() - 1);
            return imageEntityRepository.saveAndFlush(entityToBeUpdated);
        } else {
            return entityToBeUpdated;
        }
    }
}
