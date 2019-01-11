/*
 * Copyright (c) 2019. copyright text here
 */

package com.disney.studios.rest.controller;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.disney.studios.dataaccess.entity.ImageEntity;
import com.disney.studios.rest.exception.ImageResourceNotFoundException;
import com.disney.studios.service.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@Api(value = "ImageResource")
@RequestMapping(value = {"/api/v1/images"})
public class ImageResourceController {

    //http://localhost:8080/api/v1/images?sort=breed,desc
    //http://localhost:8080/api/v1/images?search=breed:labrador
    //http://localhost:8080/api/v1/images?size=100
    //http://localhost:8080/api/v1/images?page=0

    @Autowired
    private ImageService imageService;

    private static final Logger LOG = LoggerFactory.getLogger(ImageResourceController.class);

    @GetMapping()
    @ApiOperation(value = "Get list of all images")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "The request was successful")})
    public ResponseEntity<List<ImageEntity>> getAllImages(

    @RequestParam(required = false, value = "search") String search,
        Pageable pageRequest ) {

        System.out.println("search: " + search + " pageRequest: sort: " + pageRequest.getSort() + " pageSize: " + pageRequest.getPageSize() + " pageNumber:" + pageRequest.getPageNumber());

        Page<ImageEntity> entities = imageService.getAllImages(search, pageRequest);
        List<ImageEntity> list = new ArrayList<>(entities.getContent());

        return ResponseEntity.ok(list);

        /*
        List<Resource<ImageEntity>> resources = list.stream()
            .map(cur -> {
                Resource<ImageEntity> resource = new Resource<>(cur);
                URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(cur.getId()).toUri();
                resource.add(new Link(location.toString(), "self"));
                resource.add(new Link(location.toString() + "/images", "images"));
                return resource;
            })
            .collect(Collectors.toList());
            */

        // TODO abondoned as forget hateoas Resource has an id which will conflict with the entity id.  Tackle later.  Need proper links for next page, prev page, self, etc.
        // PagedResourcesAssembler assembler) {
        // public ResponseEntity<PagedResources<ImageEntity>> getAllImages(
        //public ResponseEntity<List<Resource<ImageEntity>>> getAllImages(
        //Page<ImageEntity> resources = new PageImpl(list, entities.getPageable(), entities.getTotalElements());
        //return new ResponseEntity<>(assembler.toResource(resources), HttpStatus.OK);

    }

    @GetMapping()
    @RequestMapping(value = "/{id}")
    @ApiOperation(value = "Get an image")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "The request was successful")})
    public ResponseEntity<ImageEntity> getImageById(
        @PathVariable(required = true, value = "id") Long id) {
        Optional<ImageEntity> entity = imageService.getImageById(id);
        if (entity.isPresent()) {
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        } else {
            LOG.warn("image resource does not exist. Id: {} ", id);
            throw new ImageResourceNotFoundException();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/votes")
    @ApiOperation(value = "Add a vote to an image")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "The request was successful")})
    public ResponseEntity<ImageEntity> addImageVote(
        @PathVariable(required = true, value = "id") Long id) {
        ImageEntity savedEntity = imageService.incrementImageVote(id);
        return new ResponseEntity<>(savedEntity, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}/votes")
    @ApiOperation(value = "Remove a vote from an image")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "The request was successful")})
    public ResponseEntity<ImageEntity> removeImageVote(
        // TODO theoritically this should return 204 - NO CONTENT
        @PathVariable(required = true, value = "id") Long id) {
        ImageEntity savedEntity = imageService.decrementImageVote(id);
        return new ResponseEntity<>(savedEntity, HttpStatus.OK);
    }

    // TODO PUT/PATCH
    // TODO DELETE

}

