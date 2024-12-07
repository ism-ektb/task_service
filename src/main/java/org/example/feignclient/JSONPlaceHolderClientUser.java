package org.example.feignclient;
import jakarta.validation.constraints.Positive;
import org.example.dto.external.UserOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userClient", url = "http://51.250.41.207:8081/users")
public interface JSONPlaceHolderClientUser {
    @GetMapping("/{id}")
    UserOutDto getUser(@RequestHeader @Positive Long userId,
                       @RequestHeader String password,
                       @PathVariable long id);
}