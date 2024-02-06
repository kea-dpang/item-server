package kea.dpang.item.dto.item;

import jakarta.persistence.Convert;
import kea.dpang.item.converter.StringToSubCategoryConverter;
import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.Image;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateItemRequestDto {
    private Long sellerId;
    private String itemName;
    private Category category;
    @Convert(converter = StringToSubCategoryConverter.class)
    private SubCategory subCategory = SubCategory.NONE;;
    private int itemPrice;
    private int stockQuantity;
    private String itemImage;
    private List<Image> images;

    public Item toItem() {
        return Item.builder()
                .id(null)
                .name(this.getItemName())
                .sellerId(this.getSellerId())
                .price(this.getItemPrice())
                .category(this.getCategory())
                .subCategory(this.getSubCategory())
                .averageRating(0.0f) // 초기 평점을 0으로 설정
                .reviews(new ArrayList<>()) // 초기 리뷰 리스트를 빈 리스트로 설정
                .discountRate(0) // 초기 할인율을 0으로 설정
                .discountPrice(0) // 초기 할인가를 0으로 설정
                .stockQuantity(this.getStockQuantity())
                .description("") // 초기 상품 설명을 빈 문자열로 설정
                .thumbnailImage(this.getItemImage())
                .informationImages(this.getImages())
                .build();
    }

}
