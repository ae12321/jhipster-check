package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AuthorInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AuthorInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorInfoRepository extends JpaRepository<AuthorInfo, Long> {}
