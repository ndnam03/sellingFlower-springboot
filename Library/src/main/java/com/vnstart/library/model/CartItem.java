package com.vnstart.library.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_detail_id")
    private Long id;
    private Integer quantity;
    private Double totalPrice;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")
    private ShoppingCart cart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
}
