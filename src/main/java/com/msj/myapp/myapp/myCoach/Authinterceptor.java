package com.msj.myapp.myapp.myCoach;



import com.msj.myapp.myapp.myCoach.MyCoachutil.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
//WebMvcCOnfig에 인터셉터 추가 해줘야됨

@Component
public class Authinterceptor implements HandlerInterceptor {
//    중간에 가로채셔 토큰있는지 확인하는 어노테이션을 생성

//   토큰 검증 및 subjact/claim(토큰 내부의 데이터)를 객체화
    @Autowired
JWT jwt;

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//
////      1. 요청을 처리할 컨트롤 메서드에 @Auth 어노테이션이 있는지 확인
//        //            HTTP요청을 처리하는 메서드인지 확인
//        if (handler instanceof HandlerMethod){
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Method method = handlerMethod.getMethod();
////            @Autho 어노테이션이 있는지 확인
//           Auth auth = method.getAnnotation(Auth.class);
////           Auth 어노테이션이 없으면 토큰 관련 처리를 별도로 하지않음
//           if (auth == null) {
//            return true;
//           }
////              2. 인증 토큰 읽어오기
////           @Auth 어노테이션이 있는 경우
////            Request Header에 authorization 헤더에 토큰 조회
//            String token = request.getHeader("Authorization");
////            인증 토큰이 없으면
//           if (token ==null || token.isEmpty()){
////               401:Unauthorized (미인가이지만,미인증이라는 의미로 사용)
////               인증 토큰이 없음
//               response.setStatus(401);
//               return false;  //return false = 다음 구문으로 안 넘어감
//           }
////      인증 토큰 있으면
////      3. 인증토큰 및 페이로드(subject/cloim)데이터 객체화하기
//            AuthProfile profile = jwt               //Bearer한칸 띄어쓰기 해야함
//                    .validateToken(token.replace("Bearer ",""));
//            if (profile == null){
//                //401:Unauthorized
////              인증 토큰이 잘못됨(시그니처,페이로드,알고리즘...)
//                response.setStatus(401);
//            }
//
//
////      4. 요청 속성(attribute)에 프로필 객체 추가하기
//            request.setAttribute("authProfile",profile);
//            return true;
//        }
////
//        return true;
//    }
}
