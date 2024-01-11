package kea.dpang.item.entity;

import jakarta.persistence.*;
import java.util.List;

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

    // 브랜드명
    @Column(nullable = false)
    private String brand;

    // 상품 원가
    @Column(nullable = false)
    private Double itemPrice;

    // 평점
    private Double rating;

    // 리뷰 리스트
    @ElementCollection
    private List<String> reviews;

    // 할인율
    private Double discountRate;

    // 할인가
    private Double discountPrice;

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
}

