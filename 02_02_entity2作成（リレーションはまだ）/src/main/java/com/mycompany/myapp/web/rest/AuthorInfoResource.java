package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AuthorInfo;
import com.mycompany.myapp.repository.AuthorInfoRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.AuthorInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AuthorInfoResource {

    private final Logger log = LoggerFactory.getLogger(AuthorInfoResource.class);

    private static final String ENTITY_NAME = "authorInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthorInfoRepository authorInfoRepository;

    public AuthorInfoResource(AuthorInfoRepository authorInfoRepository) {
        this.authorInfoRepository = authorInfoRepository;
    }

    /**
     * {@code POST  /author-infos} : Create a new authorInfo.
     *
     * @param authorInfo the authorInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authorInfo, or with status {@code 400 (Bad Request)} if the authorInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/author-infos")
    public ResponseEntity<AuthorInfo> createAuthorInfo(@RequestBody AuthorInfo authorInfo) throws URISyntaxException {
        log.debug("REST request to save AuthorInfo : {}", authorInfo);
        if (authorInfo.getId() != null) {
            throw new BadRequestAlertException("A new authorInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthorInfo result = authorInfoRepository.save(authorInfo);
        return ResponseEntity
            .created(new URI("/api/author-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /author-infos/:id} : Updates an existing authorInfo.
     *
     * @param id the id of the authorInfo to save.
     * @param authorInfo the authorInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorInfo,
     * or with status {@code 400 (Bad Request)} if the authorInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authorInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/author-infos/{id}")
    public ResponseEntity<AuthorInfo> updateAuthorInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AuthorInfo authorInfo
    ) throws URISyntaxException {
        log.debug("REST request to update AuthorInfo : {}, {}", id, authorInfo);
        if (authorInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authorInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authorInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuthorInfo result = authorInfoRepository.save(authorInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, authorInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /author-infos/:id} : Partial updates given fields of an existing authorInfo, field will ignore if it is null
     *
     * @param id the id of the authorInfo to save.
     * @param authorInfo the authorInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorInfo,
     * or with status {@code 400 (Bad Request)} if the authorInfo is not valid,
     * or with status {@code 404 (Not Found)} if the authorInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the authorInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/author-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuthorInfo> partialUpdateAuthorInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AuthorInfo authorInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update AuthorInfo partially : {}, {}", id, authorInfo);
        if (authorInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authorInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authorInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuthorInfo> result = authorInfoRepository
            .findById(authorInfo.getId())
            .map(existingAuthorInfo -> {
                if (authorInfo.getAge() != null) {
                    existingAuthorInfo.setAge(authorInfo.getAge());
                }
                if (authorInfo.getAddress() != null) {
                    existingAuthorInfo.setAddress(authorInfo.getAddress());
                }

                return existingAuthorInfo;
            })
            .map(authorInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, authorInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /author-infos} : get all the authorInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authorInfos in body.
     */
    @GetMapping("/author-infos")
    public List<AuthorInfo> getAllAuthorInfos() {
        log.debug("REST request to get all AuthorInfos");
        return authorInfoRepository.findAll();
    }

    /**
     * {@code GET  /author-infos/:id} : get the "id" authorInfo.
     *
     * @param id the id of the authorInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authorInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/author-infos/{id}")
    public ResponseEntity<AuthorInfo> getAuthorInfo(@PathVariable Long id) {
        log.debug("REST request to get AuthorInfo : {}", id);
        Optional<AuthorInfo> authorInfo = authorInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(authorInfo);
    }

    /**
     * {@code DELETE  /author-infos/:id} : delete the "id" authorInfo.
     *
     * @param id the id of the authorInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/author-infos/{id}")
    public ResponseEntity<Void> deleteAuthorInfo(@PathVariable Long id) {
        log.debug("REST request to delete AuthorInfo : {}", id);
        authorInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
