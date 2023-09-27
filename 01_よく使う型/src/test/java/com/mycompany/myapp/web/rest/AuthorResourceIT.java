package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Author;
import com.mycompany.myapp.repository.AuthorRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AuthorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuthorResourceIT {

    private static final String DEFAULT_COL_A = "AAAAAAAAAA";
    private static final String UPDATED_COL_A = "BBBBBBBBBB";

    private static final Long DEFAULT_COL_B = 0L;
    private static final Long UPDATED_COL_B = 1L;

    private static final Double DEFAULT_COL_C = 0D;
    private static final Double UPDATED_COL_C = 1D;

    private static final LocalDate DEFAULT_COL_D = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COL_D = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_COL_E = false;
    private static final Boolean UPDATED_COL_E = true;

    private static final String DEFAULT_COL_F = "AAAAAAAAAA";
    private static final String UPDATED_COL_F = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/authors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthorMockMvc;

    private Author author;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Author createEntity(EntityManager em) {
        Author author = new Author()
            .colA(DEFAULT_COL_A)
            .colB(DEFAULT_COL_B)
            .colC(DEFAULT_COL_C)
            .colD(DEFAULT_COL_D)
            .colE(DEFAULT_COL_E)
            .colF(DEFAULT_COL_F);
        return author;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Author createUpdatedEntity(EntityManager em) {
        Author author = new Author()
            .colA(UPDATED_COL_A)
            .colB(UPDATED_COL_B)
            .colC(UPDATED_COL_C)
            .colD(UPDATED_COL_D)
            .colE(UPDATED_COL_E)
            .colF(UPDATED_COL_F);
        return author;
    }

    @BeforeEach
    public void initTest() {
        author = createEntity(em);
    }

    @Test
    @Transactional
    void createAuthor() throws Exception {
        int databaseSizeBeforeCreate = authorRepository.findAll().size();
        // Create the Author
        restAuthorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isCreated());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeCreate + 1);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getColA()).isEqualTo(DEFAULT_COL_A);
        assertThat(testAuthor.getColB()).isEqualTo(DEFAULT_COL_B);
        assertThat(testAuthor.getColC()).isEqualTo(DEFAULT_COL_C);
        assertThat(testAuthor.getColD()).isEqualTo(DEFAULT_COL_D);
        assertThat(testAuthor.getColE()).isEqualTo(DEFAULT_COL_E);
        assertThat(testAuthor.getColF()).isEqualTo(DEFAULT_COL_F);
    }

    @Test
    @Transactional
    void createAuthorWithExistingId() throws Exception {
        // Create the Author with an existing ID
        author.setId(1L);

        int databaseSizeBeforeCreate = authorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkColAIsRequired() throws Exception {
        int databaseSizeBeforeTest = authorRepository.findAll().size();
        // set the field null
        author.setColA(null);

        // Create the Author, which fails.

        restAuthorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isBadRequest());

        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkColBIsRequired() throws Exception {
        int databaseSizeBeforeTest = authorRepository.findAll().size();
        // set the field null
        author.setColB(null);

        // Create the Author, which fails.

        restAuthorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isBadRequest());

        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkColCIsRequired() throws Exception {
        int databaseSizeBeforeTest = authorRepository.findAll().size();
        // set the field null
        author.setColC(null);

        // Create the Author, which fails.

        restAuthorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isBadRequest());

        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuthors() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList
        restAuthorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(author.getId().intValue())))
            .andExpect(jsonPath("$.[*].colA").value(hasItem(DEFAULT_COL_A)))
            .andExpect(jsonPath("$.[*].colB").value(hasItem(DEFAULT_COL_B.intValue())))
            .andExpect(jsonPath("$.[*].colC").value(hasItem(DEFAULT_COL_C.doubleValue())))
            .andExpect(jsonPath("$.[*].colD").value(hasItem(DEFAULT_COL_D.toString())))
            .andExpect(jsonPath("$.[*].colE").value(hasItem(DEFAULT_COL_E.booleanValue())))
            .andExpect(jsonPath("$.[*].colF").value(hasItem(DEFAULT_COL_F.toString())));
    }

    @Test
    @Transactional
    void getAuthor() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get the author
        restAuthorMockMvc
            .perform(get(ENTITY_API_URL_ID, author.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(author.getId().intValue()))
            .andExpect(jsonPath("$.colA").value(DEFAULT_COL_A))
            .andExpect(jsonPath("$.colB").value(DEFAULT_COL_B.intValue()))
            .andExpect(jsonPath("$.colC").value(DEFAULT_COL_C.doubleValue()))
            .andExpect(jsonPath("$.colD").value(DEFAULT_COL_D.toString()))
            .andExpect(jsonPath("$.colE").value(DEFAULT_COL_E.booleanValue()))
            .andExpect(jsonPath("$.colF").value(DEFAULT_COL_F.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAuthor() throws Exception {
        // Get the author
        restAuthorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuthor() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author
        Author updatedAuthor = authorRepository.findById(author.getId()).get();
        // Disconnect from session so that the updates on updatedAuthor are not directly saved in db
        em.detach(updatedAuthor);
        updatedAuthor
            .colA(UPDATED_COL_A)
            .colB(UPDATED_COL_B)
            .colC(UPDATED_COL_C)
            .colD(UPDATED_COL_D)
            .colE(UPDATED_COL_E)
            .colF(UPDATED_COL_F);

        restAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAuthor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAuthor))
            )
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getColA()).isEqualTo(UPDATED_COL_A);
        assertThat(testAuthor.getColB()).isEqualTo(UPDATED_COL_B);
        assertThat(testAuthor.getColC()).isEqualTo(UPDATED_COL_C);
        assertThat(testAuthor.getColD()).isEqualTo(UPDATED_COL_D);
        assertThat(testAuthor.getColE()).isEqualTo(UPDATED_COL_E);
        assertThat(testAuthor.getColF()).isEqualTo(UPDATED_COL_F);
    }

    @Test
    @Transactional
    void putNonExistingAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, author.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(author))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(author))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthorWithPatch() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author using partial update
        Author partialUpdatedAuthor = new Author();
        partialUpdatedAuthor.setId(author.getId());

        partialUpdatedAuthor.colB(UPDATED_COL_B);

        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthor))
            )
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getColA()).isEqualTo(DEFAULT_COL_A);
        assertThat(testAuthor.getColB()).isEqualTo(UPDATED_COL_B);
        assertThat(testAuthor.getColC()).isEqualTo(DEFAULT_COL_C);
        assertThat(testAuthor.getColD()).isEqualTo(DEFAULT_COL_D);
        assertThat(testAuthor.getColE()).isEqualTo(DEFAULT_COL_E);
        assertThat(testAuthor.getColF()).isEqualTo(DEFAULT_COL_F);
    }

    @Test
    @Transactional
    void fullUpdateAuthorWithPatch() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author using partial update
        Author partialUpdatedAuthor = new Author();
        partialUpdatedAuthor.setId(author.getId());

        partialUpdatedAuthor
            .colA(UPDATED_COL_A)
            .colB(UPDATED_COL_B)
            .colC(UPDATED_COL_C)
            .colD(UPDATED_COL_D)
            .colE(UPDATED_COL_E)
            .colF(UPDATED_COL_F);

        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthor))
            )
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getColA()).isEqualTo(UPDATED_COL_A);
        assertThat(testAuthor.getColB()).isEqualTo(UPDATED_COL_B);
        assertThat(testAuthor.getColC()).isEqualTo(UPDATED_COL_C);
        assertThat(testAuthor.getColD()).isEqualTo(UPDATED_COL_D);
        assertThat(testAuthor.getColE()).isEqualTo(UPDATED_COL_E);
        assertThat(testAuthor.getColF()).isEqualTo(UPDATED_COL_F);
    }

    @Test
    @Transactional
    void patchNonExistingAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, author.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(author))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(author))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthor() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeDelete = authorRepository.findAll().size();

        // Delete the author
        restAuthorMockMvc
            .perform(delete(ENTITY_API_URL_ID, author.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
