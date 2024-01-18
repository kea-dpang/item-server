package kea.dpang.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateItemDto {

    private String itemName;
    private String category;
    private String subCategory;
    private Long itemPrice;
    private Long discountPrice;
    private Long eventPrice;
    private String vendor;
    private String tags;
    private String brand;
    private String minStock;
    private String maxStock;
    private String itemImage;
    private List<String> images;
}
