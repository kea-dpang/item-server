package kea.dpang.item.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Item {
    // 상품 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    // 상품명
    @Column(nullable = false)
    private String itemName;

    // 상품 분류 카테고리
    @Column(nullable = false)
    private String category;

    // 상품 분류 서브카테고리
    @Column(nullable = false)
    private String subCategory;

    // 브랜드명
    @Column(nullable = false)
    private String brand;

    // 상품 원가
    @Column(nullable = false)
    private Long itemPrice;

    // 평점
    private Double rating;

    // 리뷰 리스트
    @ElementCollection
    private List<String> reviews;

    // 리뷰 개수
    private Long numberReview;

    // 할인율
    private Long discountRate;

    // 할인가
    private Long discountPrice;

    // 이벤트가
    private Long eventPrice;

    // 판매처
    private String vendor;

    // 태그
    private String tags;

    // 최소 재고 수량
    private String minStock;

    // 최대 재고 수량
    private String maxStock;

    // 상품 상세정보
    @Column(length = 1000)
    private String description;

    // 상품 사진
    private String itemImage;

    // 상품 썸네일 사진
    private String thumbnailImage;

    // 위시리스트
    private Boolean wishlistCheck;

    // 이미지 리스트
    @ElementCollection
    private List<String> images;

    public void updateInformation(String itemName, String category, String subCategory, Long itemPrice, Long discountPrice, Long eventPrice, String minStock, String maxStock) {
        this.itemName = itemName;
        this.category = category;
        this.subCategory = subCategory;
        this.itemPrice = itemPrice;
        this.discountPrice = this.discountPrice;
        this.eventPrice = this.eventPrice;
        this.vendor = vendor;
        this.tags = tags;
        this.minStock = this.minStock;
        this.maxStock = this.maxStock;
        this.itemImage = itemImage;
        this.images = images;
    }
}

