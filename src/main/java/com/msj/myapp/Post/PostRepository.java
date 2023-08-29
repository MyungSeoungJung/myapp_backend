package com.msj.myapp.Post;

import com.msj.myapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCreatorId(long creatorId);

    Optional<Post> findByNo(long no);



}
