/*
 * Copyright (c) 2019. copyright text here
 */

package com.disney.studios.dataaccess.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // TODO could be generalized for images by addting attributes/tags.

    private String breed;
    private String description;
    private String filename;
    private String uri;
    private String url;
    private String imageType; // jpg, jpeg, png, etc.
    private Long votes; // number of votes
    private String source;

    @Lob  // blob
    @ApiModelProperty(required = false)
    private byte[] image;

    @OneToMany(fetch= FetchType.EAGER, cascade =  CascadeType.ALL, targetEntity = AttributeEntity.class)  // TODO FetchType.LAZY
    private Set<AttributeEntity> attributes;  // Doesn't work with a List

    public ImageEntity() {
    }

    public ImageEntity(Long id, String breed, String description, String filename, String uri, String url, long votes, String source) {
        this.id = id;
        this.breed = breed;
        this.description = description;
        this.filename = filename;
        this.uri = uri;
        this.url = url;
        this.votes = votes;
        this.source = source;
    }

    public ImageEntity(ImageEntity entity) {
        this.id = entity.getId();
        this.breed = entity.getBreed();
        this.description = entity.getDescription();
        this.filename = entity.getFilename();
        this.uri = entity.getUri();
        this.url = entity.getUrl();
        this.votes = entity.getVotes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Set<AttributeEntity> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
            "id=" + id +
            ", breed='" + breed + '\'' +
            ", description='" + description + '\'' +
            ", filename='" + filename + '\'' +
            ", uri='" + uri + '\'' +
            ", url='" + url + '\'' +
            ", imageType='" + imageType + '\'' +
            ", votes=" + votes +
            ", source='" + source + '\'' +
           // ", image=" + Arrays.toString(image) +
            ", attributes=" + attributes +
            '}';
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ImageEntity that = (ImageEntity) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(breed, that.breed) &&
            Objects.equals(description, that.description) &&
            Objects.equals(filename, that.filename) &&
            Objects.equals(uri, that.uri) &&
            Objects.equals(url, that.url) &&
            Objects.equals(imageType, that.imageType) &&
            Objects.equals(votes, that.votes) &&
            Objects.equals(source, that.source) &&
            Arrays.equals(image, that.image) &&
            Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, breed, description, filename, uri, url, imageType, votes, source, attributes);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
    */

}



