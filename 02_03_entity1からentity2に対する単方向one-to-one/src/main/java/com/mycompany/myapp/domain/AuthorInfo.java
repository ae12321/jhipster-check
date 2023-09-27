package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A AuthorInfo.
 */
@Entity
@Table(name = "author_info")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuthorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address")
    private String address;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AuthorInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return this.age;
    }

    public AuthorInfo age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return this.address;
    }

    public AuthorInfo address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorInfo)) {
            return false;
        }
        return id != null && id.equals(((AuthorInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthorInfo{" +
            "id=" + getId() +
            ", age=" + getAge() +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
