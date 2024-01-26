package kea.dpang.item.dto;

import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemUpdateDto {

    private String itemName;
    private Category category;
    private SubCategory subCategory;
    private int itemPrice;
    private int discountPrice;
    private int eventPrice;
    private int stockQuantity;
    private int minStock;
    private int maxStock;
    private String itemImage;
    private List<String> images;
}
