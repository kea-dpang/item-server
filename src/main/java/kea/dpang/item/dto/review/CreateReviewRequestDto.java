package kea.dpang.item.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateReviewRequestDto {
    private Long reviewerId;
    private Long itemId;
    private String content;
    private Double rating;
}
