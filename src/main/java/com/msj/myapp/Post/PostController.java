package com.msj.myapp.Post;

import com.msj.myapp.Post.entity.Post;
import com.msj.myapp.Post.request.PostModifyRequest;
import com.msj.myapp.auth.Auth;
import com.msj.myapp.auth.AuthProfile;
import com.msj.myapp.user.User;
import com.msj.myapp.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "커뮤니티 관리 처리")
@RestController
@RequestMapping(value = "/posts")
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Operation(summary = "게시물 띄우기")
    @GetMapping(value = "/getPost")
    public List<Post> getPostList() {
        List<Post> list = postRepository.findAll(Sort.by("no").ascending());
        return list;
    }

    @Operation(summary = "게시물 작성", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @PostMapping (value = "/addPost")
    public ResponseEntity<String>addPost(@RequestBody Post post, @RequestAttribute AuthProfile authProfile) {
        if (post.getTitle() == null || post.getContent() == null || post.getTitle().isEmpty() || post.getContent().isEmpty()) { // 요청 보내는 게시물 내용 체크
            return ResponseEntity.badRequest().body("Title and content are required.");
        }
        Optional<User> user = userRepository.findById(authProfile.getId());
        if(user.isPresent()){
            post.setUserName(user.get().getName());
            post.setCreatedTime(new Date().getTime());
            post.setCreatorId(user.get().getId());
            postRepository.save(post);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @Operation(summary = "내가 작성한 게시물", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping (value = "/myPost")
    public List<Post> getPostList( @RequestAttribute AuthProfile authProfile) {
        List<Post> list = postRepository.findByCreatorId(authProfile.getId());
        return list;
    }

    @Operation(summary = "게시물 삭제", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @DeleteMapping(value = "/deletePost")
    public ResponseEntity removePost(@RequestParam long no, @RequestAttribute AuthProfile authProfile) {
        Optional<Post> post = postRepository.findByNo(no);
        if (!post.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (post.get().getNo() != no) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        postRepository.deleteById(no);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "게시물 수정", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @PutMapping(value = "/modifyPost")
    public ResponseEntity modifyPost(@RequestParam long no, @RequestBody PostModifyRequest post, @RequestAttribute AuthProfile authProfile) {
        Optional<Post> findedPost = postRepository.findById(no);
        if (!findedPost.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Post toModifyPost = findedPost.get();
        if (post.getTitle() != null && !post.getTitle().isEmpty()) {
            toModifyPost.setTitle(post.getTitle());
        }
        if (post.getContent() != null && !post.getContent().isEmpty()) {
            toModifyPost.setContent(post.getContent());
        }
        postRepository.save(toModifyPost);
        return ResponseEntity.ok().build();
    }
}
