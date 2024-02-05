package kea.dpang.item.dto.item;

import lombok.Data;

import java.util.List;

@Data
public class DeleteItemRequestDto {
    private List<Long> itemIds;
}
