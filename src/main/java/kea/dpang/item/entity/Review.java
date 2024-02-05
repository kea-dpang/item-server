package kea.dpang.item.entity;

import jakarta.persistence.*;
import kea.dpang.item.base.BaseEntity;
import kea.dpang.item.dto.review.CreateReviewRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Long id;

    // 리뷰 작성자 ID
    @Column(name = "reviewer_id", nullable = false)
    private Long reviewerId;

    // 상품 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item itemId;

    // 리뷰 내용
    @Column(name = "review_content", nullable = false)
    private String content;

    // 평점
    @Column(name = "rating", nullable = false)
    private Double rating;

    public static Review from(CreateReviewRequestDto dto, Item item) {
        return Review.builder()
                .reviewerId(dto.getReviewerId())
                .itemId(item)
                .content(dto.getContent())
                .rating(dto.getRating())
                .build();
    }
}