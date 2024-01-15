package kea.dpang.item.service;

import kea.dpang.item.dto.ItemDetailDto;
import kea.dpang.item.dto.ItemThumbnailDto;
import kea.dpang.item.dto.UpdateItemDto;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.Review;
import kea.dpang.item.exception.ItemNotFoundException;
import kea.dpang.item.repository.ItemRepository;
import kea.dpang.item.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public ItemDetailDto getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .map(ItemDetailDto::new)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    @Override
    @Transactional
    public UpdateItemDto updateItem(Long itemId, UpdateItemDto dto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        // 사용자 정보 업데이트
        item.updateInformation(dto.getItemName(), dto.getCategory(), dto.getSubCategory(), dto.getItemPrice(), dto.getDiscountPrice(), dto.getEventPrice(), dto.getMinStock(), dto.getMaxStock());
        return new ItemDetailDto(itemRepository.save(item));
    }


    public ItemThumbnailDto convertToItemThumbnailDTO(Item item) {
        List<Review> reviews = reviewRepository.findByItemId(item.getItemId());

        return new ItemThumbnailDto(
                item.getItemId(),
                item.getItemName(),
                item.getBrand(),
                item.getItemPrice(),
                item.getDiscountPrice(),
                item.getWishlistCheck(),
                item.getItemImage(),
                item.getNumberReview()
        );
    }

}

