package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.AuthorInfo;
import com.mycompany.myapp.repository.AuthorInfoRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AuthorInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuthorInfoResourceIT {

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/author-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuthorInfoRepository authorInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthorInfoMockMvc;

    private AuthorInfo authorInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthorInfo createEntity(EntityManager em) {
        AuthorInfo authorInfo = new AuthorInfo().age(DEFAULT_AGE).address(DEFAULT_ADDRESS);
        return authorInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthorInfo createUpdatedEntity(EntityManager em) {
        AuthorInfo authorInfo = new AuthorInfo().age(UPDATED_AGE).address(UPDATED_ADDRESS);
        return authorInfo;
    }

    @BeforeEach
    public void initTest() {
        authorInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createAuthorInfo() throws Exception {
        int databaseSizeBeforeCreate = authorInfoRepository.findAll().size();
        // Create the AuthorInfo
        restAuthorInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorInfo)))
            .andExpect(status().isCreated());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeCreate + 1);
        AuthorInfo testAuthorInfo = authorInfoList.get(authorInfoList.size() - 1);
        assertThat(testAuthorInfo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testAuthorInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void createAuthorInfoWithExistingId() throws Exception {
        // Create the AuthorInfo with an existing ID
        authorInfo.setId(1L);

        int databaseSizeBeforeCreate = authorInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorInfo)))
            .andExpect(status().isBadRequest());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAuthorInfos() throws Exception {
        // Initialize the database
        authorInfoRepository.saveAndFlush(authorInfo);

        // Get all the authorInfoList
        restAuthorInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authorInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    @Transactional
    void getAuthorInfo() throws Exception {
        // Initialize the database
        authorInfoRepository.saveAndFlush(authorInfo);

        // Get the authorInfo
        restAuthorInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, authorInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(authorInfo.getId().intValue()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    void getNonExistingAuthorInfo() throws Exception {
        // Get the authorInfo
        restAuthorInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuthorInfo() throws Exception {
        // Initialize the database
        authorInfoRepository.saveAndFlush(authorInfo);

        int databaseSizeBeforeUpdate = authorInfoRepository.findAll().size();

        // Update the authorInfo
        AuthorInfo updatedAuthorInfo = authorInfoRepository.findById(authorInfo.getId()).get();
        // Disconnect from session so that the updates on updatedAuthorInfo are not directly saved in db
        em.detach(updatedAuthorInfo);
        updatedAuthorInfo.age(UPDATED_AGE).address(UPDATED_ADDRESS);

        restAuthorInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAuthorInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAuthorInfo))
            )
            .andExpect(status().isOk());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeUpdate);
        AuthorInfo testAuthorInfo = authorInfoList.get(authorInfoList.size() - 1);
        assertThat(testAuthorInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testAuthorInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void putNonExistingAuthorInfo() throws Exception {
        int databaseSizeBeforeUpdate = authorInfoRepository.findAll().size();
        authorInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthorInfo() throws Exception {
        int databaseSizeBeforeUpdate = authorInfoRepository.findAll().size();
        authorInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthorInfo() throws Exception {
        int databaseSizeBeforeUpdate = authorInfoRepository.findAll().size();
        authorInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthorInfoWithPatch() throws Exception {
        // Initialize the database
        authorInfoRepository.saveAndFlush(authorInfo);

        int databaseSizeBeforeUpdate = authorInfoRepository.findAll().size();

        // Update the authorInfo using partial update
        AuthorInfo partialUpdatedAuthorInfo = new AuthorInfo();
        partialUpdatedAuthorInfo.setId(authorInfo.getId());

        partialUpdatedAuthorInfo.age(UPDATED_AGE);

        restAuthorInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthorInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthorInfo))
            )
            .andExpect(status().isOk());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeUpdate);
        AuthorInfo testAuthorInfo = authorInfoList.get(authorInfoList.size() - 1);
        assertThat(testAuthorInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testAuthorInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void fullUpdateAuthorInfoWithPatch() throws Exception {
        // Initialize the database
        authorInfoRepository.saveAndFlush(authorInfo);

        int databaseSizeBeforeUpdate = authorInfoRepository.findAll().size();

        // Update the authorInfo using partial update
        AuthorInfo partialUpdatedAuthorInfo = new AuthorInfo();
        partialUpdatedAuthorInfo.setId(authorInfo.getId());

        partialUpdatedAuthorInfo.age(UPDATED_AGE).address(UPDATED_ADDRESS);

        restAuthorInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthorInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthorInfo))
            )
            .andExpect(status().isOk());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeUpdate);
        AuthorInfo testAuthorInfo = authorInfoList.get(authorInfoList.size() - 1);
        assertThat(testAuthorInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testAuthorInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void patchNonExistingAuthorInfo() throws Exception {
        int databaseSizeBeforeUpdate = authorInfoRepository.findAll().size();
        authorInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authorInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthorInfo() throws Exception {
        int databaseSizeBeforeUpdate = authorInfoRepository.findAll().size();
        authorInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthorInfo() throws Exception {
        int databaseSizeBeforeUpdate = authorInfoRepository.findAll().size();
        authorInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(authorInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthorInfo in the database
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthorInfo() throws Exception {
        // Initialize the database
        authorInfoRepository.saveAndFlush(authorInfo);

        int databaseSizeBeforeDelete = authorInfoRepository.findAll().size();

        // Delete the authorInfo
        restAuthorInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, authorInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuthorInfo> authorInfoList = authorInfoRepository.findAll();
        assertThat(authorInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
