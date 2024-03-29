package kea.dpang.item.feign;

import kea.dpang.item.base.SuccessResponse;
import kea.dpang.item.feign.dto.UserDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-server")
public interface UserServiceFeignClient {

    // 리뷰 작성자 이름을 받아오기 위한 API
    @GetMapping("/api/users/{userId}")
    ResponseEntity<SuccessResponse<UserDetailDto>> getReviewer(@PathVariable Long userId);
}
