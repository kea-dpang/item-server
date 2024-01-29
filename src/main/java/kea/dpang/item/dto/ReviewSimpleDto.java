package kea.dpang.item.dto;

import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewSimpleDto {
    private Long reviewerId;
    private Long itemId;
    private String content;
    private Double rating;

    public ReviewSimpleDto(Review review) {
        this.itemId = review.getItemId();
        this.itemId = review.getItemId();
        this.itemId = review.getItemId();
        this.itemId = review.getItemId();
    }
}
