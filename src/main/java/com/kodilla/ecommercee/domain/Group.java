package com.kodilla.ecommercee.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT_GROUPS")
public class Group {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "GROUP_ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @OneToMany(targetEntity = Product.class, mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    public Group(String name) {
        this.name = name;
        this.products = new ArrayList<Product>();
    }

    public Group(Long id, String name) {
        this.id = id;
        this.name = name;
        this.products = new ArrayList<Product>();
    }
}
