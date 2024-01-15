package kea.dpang.item.repository;

import kea.dpang.item.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 특정 상품에 대한 리뷰 목록을 가져오는 메소드
    List<Review> findByItemId(Long itemId);
}
