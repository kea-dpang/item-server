package kea.dpang.item.dto.Item;

import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemCreateDto {

    private Long sellerId;
    private String itemName;
    private Category category;
    private String subCategory;
    private int itemPrice;
    private int stockQuantity;
    private String itemImage;
    private List<String> images;
}
