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
    private int eventPrice;
    private int stockQuantity;
    private String itemImage;
    private List<String> images;
}
