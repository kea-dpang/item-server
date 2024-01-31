package kea.dpang.item.dto;

import kea.dpang.item.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewPersonalListDto {

    private Long reviewId;
    private Long reviewerId;
    private LocalDateTime createdTime;
    private Long itemId;
    private String content;
    private Double rating;

    public ReviewPersonalListDto(Review review) {
        this.reviewId = review.getReviewId();
        this.reviewerId = review.getReviewerId();
        this.createdTime = review.getCreatedTime();
        this.itemId = review.getItemId();
        this.content = review.getContent();
        this.rating = review.getRating();
    }


}