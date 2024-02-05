package kea.dpang.item.dto.review;

import kea.dpang.item.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private Long reviewerId;
    private LocalDateTime createdTime;
    private Long itemId;
    private String content;
    private Double rating;

    public ReviewDto(Review review) {
        this.reviewId = review.getId();
        this.reviewerId = review.getReviewerId();
        this.createdTime = review.getCreatedTime();
        this.itemId = review.getItemId().getItemId();
        this.content = review.getContent();
        this.rating = review.getRating();
    }
}

