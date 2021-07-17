package com.cos.photogramstart.web.api;

import com.cos.photogramstart.configuration.auth.PrincipalDetails;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@PathVariable long toUserId, @AuthenticationPrincipal PrincipalDetails principal) {
        long fromUser = principal.getUser().getId();
        subscribeService.subscribe(fromUser, toUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "successfully subscribed", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/unsubscribe/{toUserId}")
    public ResponseEntity<?> unSubscribe(@PathVariable long toUserId, @AuthenticationPrincipal PrincipalDetails principal) {
        long fromUser = principal.getUser().getId();
        subscribeService.unSubscribe(fromUser, toUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "successfully unsubscribed", null), HttpStatus.OK);
    }
}
