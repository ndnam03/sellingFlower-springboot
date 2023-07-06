package com.vnstart.library.dto;

import com.vnstart.library.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data @NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double costPrice;
    private Double SalePrice;
    private Integer currentQuantity;
    private Category category;
    private String image;
    private Boolean activated;
    private Boolean deleted;
}
