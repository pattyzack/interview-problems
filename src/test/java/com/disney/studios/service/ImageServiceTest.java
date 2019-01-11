package com.disney.studios.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.disney.studios.dataaccess.entity.ImageEntity;
import com.disney.studios.dataaccess.repository.ImageEntityRepository;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceTest {

    @Mock
    private ImageEntityRepository repository;

    @InjectMocks
    @Spy
    private ImageService service;

    private Page<ImageEntity> page = null;

    @Before
    public void setUp() throws Exception {
        List<ImageEntity> list = new ArrayList();
        ImageEntity entity1 = new ImageEntity(0l, "pug", "description", "filepath", "uri", "url", 0l, "source");
        ImageEntity entity2 = new ImageEntity(1l, "pug", "description", "filepath", "uri", "url", 0l, "source");
        ImageEntity entity3 = new ImageEntity(1l, "boxer", "description", "filepath", "uri", "url", 0l, "soruce");
        list.add(entity1);
        list.add(entity2);
        list.add(entity3);
        Pageable pageable = PageRequest.of(0, 10);
        page = new PageImpl<>(list, pageable, list.size());

        doReturn(page).when(repository).findAll(any(Pageable.class));
        doReturn(page).when(repository).findAllByBreedIgnoreCase(any(), any(Pageable.class));
        doReturn(entity1).when(repository).saveAndFlush(any());
        doReturn(Optional.of(entity1)).when(repository).findById(any());

    }

    @Test
    public void getAllImages() {
        Page<ImageEntity> page = service.getAllImages(null, PageRequest.of(0, 10));
        assertNotNull(page);
        assertTrue(page.getTotalElements() > 0);
    }

    @Test
    public void getAllImagesWithSearch() {
        Page<ImageEntity> page = service.getAllImages("breed:boxer", PageRequest.of(0, 10));
        assertNotNull(page);
        assertTrue(page.getTotalElements() > 0);
    }

    @Test
    public void getImageById() {
        Optional<ImageEntity> entity = service.getImageById(1l);
        assertTrue(entity.isPresent());
    }

    @Test
    public void incrementImageVote() {
        ImageEntity entity = service.incrementImageVote(1l);
        assertNotNull(entity);
    }

    @Test
    public void decrementImageVote() {
        ImageEntity entity = service.incrementImageVote(1l);
        assertNotNull(entity);
    }
}