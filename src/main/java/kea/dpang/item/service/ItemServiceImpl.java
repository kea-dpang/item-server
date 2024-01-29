package kea.dpang.item.service;

import kea.dpang.item.dto.*;
import kea.dpang.item.entity.Item;
import kea.dpang.item.exception.ItemNotFoundException;
import kea.dpang.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private static final String ITEM_VIEW_COUNT_KEY = "item:viewCount";
    private final StringRedisTemplate redisTemplate;

    // 상품 등록
    @Override
    @Transactional
    public void createItem(ItemCreateDto dto) {
        Item item = Item.from(dto);
        new ItemResponseDto(itemRepository.save(item));
        log.info("새로운 상품 등록 완료. 상품 ID: {}", item.getItemId());
    }

    // 상품 조회
    @Override
    @Transactional(readOnly = true)
    public ItemResponseDto getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .map(ItemResponseDto::new)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    // 상품 리스트 조회(프론트)
    @Override
    public List<ItemSimpleFrontendDto> getItemListForFrontend(Pageable pageable) {
        Page<Item> items = itemRepository.findAll(pageable);
        return items.stream()
                .map(ItemSimpleFrontendDto::new)
                .collect(Collectors.toList());
    }

    // 상품 리스트 조회(백엔드)
    public List<ItemSimpleBackendDto> getItemListForBackend() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(ItemSimpleBackendDto::new)
                .collect(Collectors.toList());
    }

    // 인기 상품 조회
    @Override
    @Transactional(readOnly = true)
    public List<PopularItemDto> getPopularItems() {
        // Redis에서 조회수를 기준으로 인기 상품 ID와 점수를 가져옴.
        Set<ZSetOperations.TypedTuple<String>> items = redisTemplate.opsForZSet()
                .reverseRangeWithScores(ITEM_VIEW_COUNT_KEY, 0, -1);

        // 가져온 데이터를 PopularItemDto 리스트로 변환.
        return items.stream().map(item -> {
            Long itemId = Long.valueOf(item.getValue());
            Double score = item.getScore();
            String itemName = "Item " + itemId;

            return new PopularItemDto(itemId, itemName, score);
        }).toList();
    }

    // 상품 수정
    @Override
    @Transactional
    public ItemResponseDto updateItem(Long itemId, ItemUpdateDto dto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        item.updateInformation(dto);
        return new ItemResponseDto(itemRepository.save(item));
    }

    // 상품 삭제
    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        itemRepository.delete(item);
    }

    // 재고 수량 조회
    @Override
    @Transactional
    public int getStockQuantity(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        return item.getStockQuantity();
    }

    // 재고 수량 증가
    @Override
    @Transactional
    public void increaseStock(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        item.increaseStock(quantity);
    }

    // 재고 수량 감소
    @Override
    @Transactional
    public void decreaseStock(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        item.decreaseStock(quantity);
    }
}