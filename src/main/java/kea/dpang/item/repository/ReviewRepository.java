package kea.dpang.item.repository;

import kea.dpang.item.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByReviewerId(Long reviewerId, Pageable pageable);
    Page<Review> findByItemIdItemId(Long itemId, Pageable pageable);
}
