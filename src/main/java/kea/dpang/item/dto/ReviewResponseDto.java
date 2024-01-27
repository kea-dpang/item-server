package kea.dpang.item.dto;

import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private Long reviewerId;
    private Long itemId;
    private String content;
    private Double rating;

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.reviewerId = review.getReviewerId();
        this.itemId = review.getItemId();
        this.content = review.getContent();
        this.rating = review.getRating();
    }
}

