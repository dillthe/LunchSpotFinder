package menu.yumyum.yumyum.redis.web;

import lombok.RequiredArgsConstructor;
import menu.yumyum.yumyum.redis.dto.RedisInviteReqDto;
import menu.yumyum.yumyum.redis.service.RedisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/invite")
    public ResponseEntity<Void> invite(@RequestBody RedisInviteReqDto dto) {
        redisService.inviteData(dto);
        return ResponseEntity.ok().build();
    }
}
