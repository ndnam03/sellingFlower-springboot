package com.vnstart.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private Date orderDate;
    private Date deliveryDate;
    private Double totalPrice;
    private Double shippingFee;
    private String orderStatus;
    private String notes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    private List<OrderDetail> orderDetailList;

    public List<Product> getProducts() {
        return orderDetailList.stream().map(OrderDetail::getProduct).collect(Collectors.toList());
    }

}
