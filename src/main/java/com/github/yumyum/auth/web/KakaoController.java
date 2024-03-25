//package menu.yumyum.yumyum.auth.web;
//
//import jakarta.mail.Message;
//import jakarta.servlet.http.HttpServletRequest;
//import menu.yumyum.yumyum.auth.service.AuthService;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//import org.springframework.http.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.client.RestTemplate;
//
//@Controller
//@RequestMapping("/api/kakao")
//public class KakaoController {
//
//    private final AuthService authService;
//
//    @GetMapping(value = "/callback")
//    public ResponseEntity<Message> KakaoLogin(@RequestParam("code") String code , HttpServletRequest request){
//        Message msg = new Message();
//        RestTemplate rt = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type","authorization_code");
//        params.add("client_id", kakao_rest);
//        params.add("redirect_uri",SERVER_URL + "/auth/kakao/callback");
//        params.add("code",code);
//
//        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(params,httpHeaders);
//
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        // 토큰값 Json 형식으로 가져오기위해 생성
//        JSONObject jo = new JSONObject(response.getBody());
//
//        // 토큰결과값
//        log.debug("kakao token result = {} " , response);
//
//        RestTemplate rt2 = new RestTemplate();
//        HttpHeaders headers2 = new HttpHeaders();
//
//        headers2.add("Authorization", "Bearer "+ jo.get("access_token"));
//        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String,String >> kakaoProfileRequest2= new HttpEntity<>(headers2);
//
//        ResponseEntity<String> response2 = rt2.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoProfileRequest2,
//                String.class
//        );
//
//        // 토큰을 사용하여 사용자 정보 추출
//        JSONObject jo2 = new JSONObject(response2.getBody());
//        log.debug("###### kakao login = {}", jo2);
//
//        return new ResponseEntity<>(msg, HttpStatus.OK);
//    }
//}
