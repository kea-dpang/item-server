package kea.dpang.item.dto;

import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.SubCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemManageListDto {

    private Long itemId;
    private String itemImage;
    private String itemName;
    private Category category;
    private SubCategory subCategory;
    private int stockQuantity;

    public ItemManageListDto(Item item) {
        this.itemId = item.getItemId();
        this.itemImage = item.getItemImage();
        this.itemName = item.getItemName();
        this.category = item.getCategory();
        this.subCategory = item.getSubCategory();
        this.stockQuantity = item.getStockQuantity();

    }
}
