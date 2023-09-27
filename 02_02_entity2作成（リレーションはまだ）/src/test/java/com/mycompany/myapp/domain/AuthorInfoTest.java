package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthorInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthorInfo.class);
        AuthorInfo authorInfo1 = new AuthorInfo();
        authorInfo1.setId(1L);
        AuthorInfo authorInfo2 = new AuthorInfo();
        authorInfo2.setId(authorInfo1.getId());
        assertThat(authorInfo1).isEqualTo(authorInfo2);
        authorInfo2.setId(2L);
        assertThat(authorInfo1).isNotEqualTo(authorInfo2);
        authorInfo1.setId(null);
        assertThat(authorInfo1).isNotEqualTo(authorInfo2);
    }
}
