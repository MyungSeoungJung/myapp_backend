package com.msj.myapp.Post;

import com.msj.myapp.auth.Auth;
import com.msj.myapp.auth.AuthProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
    @Autowired
    PostRepository repo;



    @GetMapping(value = "/getPost")
    public List<Post> getPostList() {
        List<Post> list = repo.findAll(Sort.by("no").ascending());
        return list;
    }


    @Auth
    @PostMapping (value = "/addPost")
    public ResponseEntity<Map<String, Object>> addPost(@RequestBody Post post, @RequestAttribute AuthProfile authProfile) {
        System.out.println(post);
        System.out.println(authProfile);

        if (post.getTitle() == null || post.getContent() == null || post.getTitle().isEmpty() || post.getContent().isEmpty()) {
//            Map<String, Object> result = ;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        post.setUserName(authProfile.getName());
        post.setCreatedTime(new Date().getTime());

        post.setCreatorId(authProfile.getId());  //유저id들어감
        Post savedPost = repo.save(post);


        //생성된 객체가 존재하면 null값이 아닐 때
        if (savedPost != null) {
            Map<String, Object> res = new HashMap<>();
            res.put("data", savedPost);
            res.put("message", "created");

            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }


        return ResponseEntity.ok().build();
    }


    @Auth
    @GetMapping (value = "/myPost")
    public List<Post> getPostList( @RequestAttribute AuthProfile authProfile) {
        List<Post> list = repo.findByCreatorId(authProfile.getId());
        return list;
    }

    @Auth
    @DeleteMapping(value = "/deletePost")
    public ResponseEntity removePost(@RequestParam long no, @RequestAttribute AuthProfile authProfile) {
        System.out.println(no);

        Optional<Post> post = repo.findByNo(no);


        if (!post.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (post.get().getNo() != no) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        repo.deleteById(no);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
