package com.example.academics.model;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 60)
    private String title;

    public Product setPrice(String price) {
        this.price = price;
        return this;
    }

    @Column
    private String price;

    public String getCategory() {
        return category;
    }

    @Column
    private String category;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    @Column
    private String description;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Lob
    private byte[] photo;
    private LocalDateTime createdDate;
    @ManyToOne
    private User createdBy;

    public Long getId() {
        return id;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Product setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Product setContent(String content) {
        this.content = content;
        return this;
    }

    public byte[] getPhoto() {
        return photo;
    }

    @Transactional    public Product setPhoto(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Product setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Product setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product blog = (Product) o;
        return Objects.equals(id, blog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}

