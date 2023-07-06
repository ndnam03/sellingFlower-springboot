package com.vnstart.library.model;

import lombok.*;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "image", "phone_number"}))
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    @Size(min = 3,max = 15,message = "First name should have 3-15 characters ")
    private String firstName;
    @Size(min = 3,max = 15,message = "Lats name should have 3-15 characters ")
    private String lastName;

    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    private String password;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private String image;


    @OneToOne(mappedBy = "customer")
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "customer")
    List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name =  "customers_roles",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

}
