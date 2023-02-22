package com.example.be_food_order.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private Long status;
    private String code;
    private String day;
    private String month;
    private String price;
    private String buyer;
    private String phone;
}
