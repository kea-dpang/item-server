package kea.dpang.item.dto.Item;

import jakarta.persistence.Convert;
import kea.dpang.item.converter.StringToSubCategoryConverter;
import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemCreateDto {
    private Long sellerId;
    private String itemName;
    private Category category;
    @Convert(converter = StringToSubCategoryConverter.class)
    private SubCategory subCategory;
    private int itemPrice;
    private int stockQuantity;
    private String itemImage;
    private List<String> images;

    public Item toItem() {
        return Item.builder()
                .itemId(null)
                .itemName(this.getItemName())
                .sellerId(this.getSellerId())
                .itemPrice(this.getItemPrice())
                .category(this.getCategory())
                .subCategory(this.getSubCategory())
                .averageRating(0.0f) // 초기 평점을 0으로 설정
                .reviews(new ArrayList<>()) // 초기 리뷰 리스트를 빈 리스트로 설정
                .discountRate(0) // 초기 할인율을 0으로 설정
                .discountPrice(0) // 초기 할인가를 0으로 설정
                .stockQuantity(this.getStockQuantity())
                .description("") // 초기 상품 설명을 빈 문자열로 설정
                .itemImage(this.getItemImage())
                .images(this.getImages())
                .wishlistCheck(false) // 초기 위시리스트 체크를 false로 설정
                .build();
    }

}
