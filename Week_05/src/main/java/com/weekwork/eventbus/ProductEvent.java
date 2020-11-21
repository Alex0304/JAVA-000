package com.weekwork.eventbus;

import com.weekwork.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ProductEvent {

    private Product product;
}
