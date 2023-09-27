package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Author.
 */
@Entity
@Table(name = "author")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 0, max = 20)
    @Column(name = "col_a", length = 20, nullable = false, unique = true)
    private String colA;

    @NotNull
    @Min(value = 0L)
    @Max(value = 100L)
    @Column(name = "col_b", nullable = false, unique = true)
    private Long colB;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "col_c", nullable = false, unique = true)
    private Double colC;

    @Column(name = "col_d")
    private LocalDate colD;

    @Column(name = "col_e")
    private Boolean colE;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "col_f")
    private String colF;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Author id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColA() {
        return this.colA;
    }

    public Author colA(String colA) {
        this.setColA(colA);
        return this;
    }

    public void setColA(String colA) {
        this.colA = colA;
    }

    public Long getColB() {
        return this.colB;
    }

    public Author colB(Long colB) {
        this.setColB(colB);
        return this;
    }

    public void setColB(Long colB) {
        this.colB = colB;
    }

    public Double getColC() {
        return this.colC;
    }

    public Author colC(Double colC) {
        this.setColC(colC);
        return this;
    }

    public void setColC(Double colC) {
        this.colC = colC;
    }

    public LocalDate getColD() {
        return this.colD;
    }

    public Author colD(LocalDate colD) {
        this.setColD(colD);
        return this;
    }

    public void setColD(LocalDate colD) {
        this.colD = colD;
    }

    public Boolean getColE() {
        return this.colE;
    }

    public Author colE(Boolean colE) {
        this.setColE(colE);
        return this;
    }

    public void setColE(Boolean colE) {
        this.colE = colE;
    }

    public String getColF() {
        return this.colF;
    }

    public Author colF(String colF) {
        this.setColF(colF);
        return this;
    }

    public void setColF(String colF) {
        this.colF = colF;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Author)) {
            return false;
        }
        return id != null && id.equals(((Author) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Author{" +
            "id=" + getId() +
            ", colA='" + getColA() + "'" +
            ", colB=" + getColB() +
            ", colC=" + getColC() +
            ", colD='" + getColD() + "'" +
            ", colE='" + getColE() + "'" +
            ", colF='" + getColF() + "'" +
            "}";
    }
}
