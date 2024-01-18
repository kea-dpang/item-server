package kea.dpang.item.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "reviews")
public class Review {
    // 리뷰 ID
    @Id
    @Column(name = "item_review_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    // 리뷰 작성자 ID
    @Column(name = "reviewer_id", nullable = false)
    private Long reviewerId;

    // 상품 ID
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    // 리뷰 내용
    @Column(name = "review_content", nullable = false)
    private String content;

    // 평점
    @Column(name = "rating", nullable = false)
    private Double rating;
}
