package Like;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//@Slf4j
@RestController
@RequiredArgsConstructor  //final 객체 생성자 자동 생성 어노테이션

@RequestMapping("/Like")
public class LikeController {
    private final LikeService likeService;

//    @PostMapping
//    public ResponseResult<?> insert(@RequestBody @Valid LikeRequestDTO likeRequestDTO){
//    likeService.insert(likeRequestDTO);
//    return success(null);
//    }
}
