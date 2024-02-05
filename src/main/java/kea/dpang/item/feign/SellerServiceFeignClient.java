package kea.dpang.item.feign;

import kea.dpang.item.base.SuccessResponse;
import kea.dpang.item.feign.dto.SellerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "seller-server")
public interface SellerServiceFeignClient {

    // 판매처 이름을 받아오기 위한 API
    @GetMapping("/api/seller/{id}")
    ResponseEntity<SuccessResponse<SellerDto>> getSeller(@PathVariable Long id);
}
