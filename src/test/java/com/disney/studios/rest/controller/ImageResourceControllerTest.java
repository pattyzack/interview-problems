package com.disney.studios.rest.controller;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.disney.studios.dataaccess.entity.ImageEntity;
import com.disney.studios.service.ImageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class ImageResourceControllerTest {

    @Mock
    private ImageService service;

    @InjectMocks
    private ImageResourceController controller;

    private MockMvc mockMvc;

    private Page<ImageEntity> page = null;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();

        List<ImageEntity> list = new ArrayList();
        ImageEntity entity1 = new ImageEntity(0l, "pug", "description", "filepath", "uri", "url", 0l, "source");
        ImageEntity entity2 = new ImageEntity(1l, "pug", "description", "filepath", "uri", "url", 0l, "source");
        ImageEntity entity3 = new ImageEntity(1l, "boxer", "description", "filepath", "uri", "url", 0l, "source");

        list.add(entity1);
        list.add(entity2);
        list.add(entity3);
        Pageable pageable = PageRequest.of(0, 10);
        page = new PageImpl<>(list, pageable, list.size());

        doReturn(page).when(service).getAllImages(any(), any());
        doReturn(Optional.of(entity1)).when(service).getImageById(any());
        doReturn(entity1).when(service).incrementImageVote(any());
        doReturn(entity1).when(service).decrementImageVote(any());

    }

    @Test
    public void getAllImages() throws Exception{
        UriComponents uri = UriComponentsBuilder.fromUriString("/api/v1/images").buildAndExpand();
        MvcResult result = mockMvc.perform(get(uri.toUri())).andExpect(status().isOk()).andReturn();
        assertNotNull(result);
        assertNotNull(result.getResponse().getContentAsString());
        List<Resource> list = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ImageEntity>>() { });
        assertEquals(list.size(), 3);
    }

    @Test
    public void getAllImagesSearchByBreed() throws Exception{
        UriComponents uri = UriComponentsBuilder.fromUriString("/api/v1/images").buildAndExpand();
        MvcResult result = mockMvc.perform(get(uri.toUri()).param("search", "breed:pug")).andExpect(status().isOk()).andReturn();
        assertNotNull(result);
        assertNotNull(result.getResponse().getContentAsString());
        List<Resource> list = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ImageEntity>>() { });
        assertEquals(list.size(), 3);
    }

    //@Test
    public void getAllImagesSortByBreedDesc() throws Exception{
        UriComponents uri = UriComponentsBuilder.fromUriString("/api/v1/images").buildAndExpand();
        MvcResult result = mockMvc.perform(get(uri.toUri())).andExpect(status().isOk()).andReturn();
        assertNotNull(result);
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void getImageById() throws Exception {
        UriComponents uri = UriComponentsBuilder.fromUriString("/api/v1/images/1").buildAndExpand();
        MvcResult result = mockMvc.perform(get(uri.toUri())).andExpect(status().isOk()).andReturn();
        assertNotNull(result);
        assertNotNull(result.getResponse().getContentAsString());
    }
    
    @Test
    public void addImageVote() throws Exception {
        UriComponents uri = UriComponentsBuilder.fromUriString("/api/v1/images/1/votes").buildAndExpand();
        MvcResult result = mockMvc.perform(post(uri.toUri())).andExpect(status().isOk()).andReturn();
        assertNotNull(result);
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void removeImageVote() throws Exception{
        UriComponents uri = UriComponentsBuilder.fromUriString("/api/v1/images/1/votes").buildAndExpand();
        MvcResult result = mockMvc.perform(delete(uri.toUri())).andExpect(status().isOk()).andReturn();
        assertNotNull(result);
        assertNotNull(result.getResponse().getContentAsString());
    }
}