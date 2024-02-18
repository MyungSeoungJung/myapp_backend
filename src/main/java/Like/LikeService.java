package Like;

import com.msj.myapp.Post.PostRepository;
import com.msj.myapp.Post.entity.Post;
import com.msj.myapp.user.User;
import com.msj.myapp.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void insert(LikeRequestDTO likeRequestDTO) throws Exception {
        User user = userRepository.findById(likeRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("멤버 ID를 찾을 수 없습니다: " + likeRequestDTO.getUserId()));
        Post post = postRepository.findById(likeRequestDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("게시물 ID를 찾을 수 없습니다: " + likeRequestDTO.getPostId()));

        // 이미 좋아요되어있으면 예외 발생
        if (likeRepository.findByUserAndPost(user, post).isPresent()){
            throw new Exception();
        }

        Like like = Like.builder()
                .post(post)
                .user(user)
                .build();

        likeRepository.save(like);
    }

    @Transactional
    public void delete(LikeRequestDTO likeRequestDTO) {
        User user = userRepository.findById(likeRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("멤버 ID를 찾을 수 없습니다: " + likeRequestDTO.getUserId()));

        Post post = postRepository.findById(likeRequestDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("게시물 ID를 찾을 수 없습니다: " + likeRequestDTO.getPostId()));

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new RuntimeException("좋아요 ID를 찾을 수 없습니다"));

        likeRepository.delete(like);
    }
}
