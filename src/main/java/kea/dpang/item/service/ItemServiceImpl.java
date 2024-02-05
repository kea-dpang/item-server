package kea.dpang.item.service;

import kea.dpang.item.dto.item.*;
import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.SubCategory;
import kea.dpang.item.exception.ItemNotFoundException;
import kea.dpang.item.feign.SellerServiceFeignClient;
import kea.dpang.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final SellerServiceFeignClient sellerServiceFeignClient;
    private static final String ITEM_VIEW_COUNT_KEY = "item:viewCount";
    private final StringRedisTemplate redisTemplate;

    // 상품 등록
    @Override
    @Transactional
    public void createItem(CreateItemRequestDto dto) {
        log.info("ItemCreateDto로부터 새로운 아이템 생성을 시작합니다 : {}", dto);

        try {
            Item item = dto.toItem();
            itemRepository.save(item);
            log.info("성공적으로 아이템이 생성되었습니다. 생성된 아이템의 ID는 : {}", item.getId());

        } catch (Exception e) {
            log.error("ItemCreateDto로부터 아이템 생성에 실패하였습니다. DTO 정보 : {}", dto, e);
            throw e;
        }
    }

    @Override
    public List<ItemDto> getItemList(List<Long> itemIds) {
        log.info("item ID 리스트로부터 아이템 리스트 조회를 시작합니다 : {}", itemIds);
        return itemRepository.findAllById(itemIds)
                .stream()
                .map(ItemDto::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto getItemInfo(Long itemId) {
        log.info("item ID로부터 아이템 조회를 시작합니다 : {}", itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        log.info("아이템 조회가 성공적으로 완료되었습니다. 조회된 아이템의 ID는 : {}", item.getId());

        return new ItemDto(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PopularItemDto> getPopularItems() {
        // Redis에서 조회수를 기반으로 인기 상품 ID와 점수를 가져옴
        Set<ZSetOperations.TypedTuple<String>> items = redisTemplate.opsForZSet()
                .reverseRangeWithScores(ITEM_VIEW_COUNT_KEY, 0, -1);

        // 가져온 데이터를 PopularItemDto 리스트로 변환
        return items.stream().map(item -> {
            Long itemId = Long.valueOf(item.getValue());
            Double score = item.getScore();
            String itemName = "Item " + itemId;

            return new PopularItemDto(itemId, itemName, score);
        }).toList();
    }

    @Override
    public void incrementViewCount(Long itemId) {
        redisTemplate.opsForZSet().incrementScore(ITEM_VIEW_COUNT_KEY, String.valueOf(itemId), 1);
    }

    // 상품 상세 정보 조회
    @Override
    @Transactional(readOnly = true)
    public ItemDetailDto getItemDetailInfo(Long itemId) {
        log.info("item ID로부터 아이템 조회를 시작합니다 : {}", itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        log.info("아이템 조회가 성공적으로 완료되었습니다. 조회된 아이템의 ID는 : {}", item.getId());

        log.info("판매자 이름 조회를 시작합니다 : {}", item.getSellerId());

        String sellerName = Objects.requireNonNull(sellerServiceFeignClient.getSeller(item.getSellerId()).getBody())
                .getData()
                .getName();

        log.info("판매자 이름 조회가 성공적으로 완료되었습니다. 조회된 판매자 이름은 : {}", sellerName);

        return new ItemDetailDto(item, sellerName);
    }

    @Override
    public Page<ItemDetailDto> getItemList(Category category, SubCategory subCategory, Double minPrice, Double maxPrice, String keyword, Long sellerId, Pageable pageable) {
        log.info("상품 리스트 조회를 시작합니다 : 카테고리 = {}, 서브카테고리 = {}, 판매자ID = {}, 최소가격 = {}, 최대가격 = {}, 키워드 = {}, 페이지 요청 정보 = {}", category, subCategory, sellerId, minPrice, maxPrice, keyword, pageable);
        Page<Item> items = itemRepository.filterItems(category, subCategory, sellerId, minPrice, maxPrice, keyword, pageable);

        log.info("판매자 이름 조회를 시작합니다 : 판매자ID = {}", sellerId);
        String sellerName = Objects.requireNonNull(sellerServiceFeignClient.getSeller(sellerId).getBody())
                .getData()
                .getName();

        log.info("상품 리스트를 ItemResponseDto로 변환합니다.");
        return items.map(item -> new ItemDetailDto(item, sellerName));
    }


    // 상품 수정
    @Override
    @Transactional
    public void updateItem(Long itemId, UpdateItemRequestDto dto) {
        log.info("item ID로부터 아이템 수정을 시작합니다 : {}", itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        log.info("아이템 조회가 성공적으로 완료되었습니다. 조회된 아이템의 ID는 : {}", item.getId());

        log.info("ItemUpdateDto로부터 아이템 수정을 시작합니다 : {}", dto);
        item.update(dto);
    }

    // 상품 삭제
    @Override
    @Transactional
    public void deleteItem(List<Long> itemIds) {
        log.info("item ID 리스트로부터 아이템 삭제를 시작합니다 : {}", itemIds);

        for (Long itemId : itemIds) {
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ItemNotFoundException(itemId));

            log.info("아이템 삭제를 시작합니다 : {}", item.getId());
            itemRepository.delete(item);

            log.info("아이템 삭제가 성공적으로 완료되었습니다. 삭제된 아이템의 ID는 : {}", item.getId());
        }

    }

    // 재고 수량 증감
    @Override
    @Transactional
    public void changeStock(List<UpdateStockRequestDto> updateStockRequestDtos) {
        log.info("재고 수량 변경을 시작합니다.");

        for (UpdateStockRequestDto updateStockRequestDto : updateStockRequestDtos) {
            Long itemId = updateStockRequestDto.getItemId();
            int quantity = updateStockRequestDto.getQuantity();

            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ItemNotFoundException(itemId));
            log.info("아이템 조회가 성공적으로 완료되었습니다. 조회된 아이템의 ID는 : {}", item.getId());

            log.info("재고 수량 변경을 시작합니다 : {}", item.getId());

            if (quantity > 0) {
                item.increaseStock(quantity);
            } else if (quantity < 0) {
                item.decreaseStock(-quantity);
            }

            log.info("재고 수량 변경이 성공적으로 완료되었습니다. 변경된 재고 수량은 : {}", item.getStockQuantity());
        }

        log.info("모든 아이템의 재고 수량 변경이 완료되었습니다.");
    }


}