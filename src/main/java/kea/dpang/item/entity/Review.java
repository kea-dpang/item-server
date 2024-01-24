package kea.dpang.item.entity;

import jakarta.persistence.*;
import kea.dpang.item.base.BaseEntity;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {
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
