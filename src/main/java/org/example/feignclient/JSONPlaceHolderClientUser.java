package org.example.feignclient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "userClient", url = "http://51.250.41.207:8081")
public interface JSONPlaceHolderClientUser {

}