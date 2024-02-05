package kea.dpang.item.dto.item;

import lombok.Data;

import java.util.List;

@Data
public class GetItemListRequestDto {
    private List<Long> itemIds;
}
