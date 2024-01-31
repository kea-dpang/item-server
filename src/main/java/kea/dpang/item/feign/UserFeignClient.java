package kea.dpang.item.feign;

import kea.dpang.item.base.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-server")
public interface UserFeignClient {

    // 리뷰 작성자 이름을 받아오기 위한 API
    @GetMapping("/api/users/reviewer/{reviewerId}")
    ResponseEntity<SuccessResponse<String>> getReviewer (@PathVariable Long reviewerId);
}
