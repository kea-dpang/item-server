package kea.dpang.item.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reviews")
public class Review {
    // 리뷰 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    // 상품 ID
    @Column(nullable = false)
    private Long itemId; // 간단한 예제를 위해 직접 연결

    // 리뷰 내용
    @Column(nullable = false)
    private String content;

    // 평점
    @Column(nullable = false)
    private Double rating;

}
