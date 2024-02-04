package kea.dpang.item.service;

import kea.dpang.item.dto.Item.ItemCreateDto;
import kea.dpang.item.dto.Item.ItemResponseDto;
import kea.dpang.item.dto.Item.ItemUpdateDto;
import kea.dpang.item.dto.Stock.StockManageDto;
import kea.dpang.item.entity.Item;
import kea.dpang.item.exception.ItemNotFoundException;
import kea.dpang.item.feign.SellerServiceFeignClient;
import kea.dpang.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final SellerServiceFeignClient sellerServiceFeignClient;
    private static final String ITEM_VIEW_COUNT_KEY = "item:viewCount";
    private final StringRedisTemplate redisTemplate;

    // 상품 등록
    @Override
    @Transactional
    public void createItem(ItemCreateDto dto) {
        Item item = Item.from(dto);
        itemRepository.save(item);
    }

    // 상품 상세 정보 조회
    @Override
    @Transactional(readOnly = true)
    public ItemResponseDto getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        String sellerName = sellerServiceFeignClient.getSeller(item.getSellerId()).getBody().getData();
        return item.toItemResponseDto(sellerName);
    }

    // 상품 수정
    @Override
    @Transactional
    public void updateItem(Long itemId, ItemUpdateDto dto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        item.updateInformation(dto);
    }

    // 상품 삭제
    @Override
    @Transactional
    public void deleteItem(List<Long> itemIds) {
        for (Long itemId : itemIds) {

            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ItemNotFoundException(itemId));
            itemRepository.delete(item);
        }

    }

    // 재고 수량 증감
    @Override
    @Transactional
    public StockManageDto changeStock(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        item.changeStock(quantity);
        return StockManageDto.builder()
                .stockQuantity(item.getStockQuantity())
                .itemId(item.getItemId())
                .build();
    }

}