package Like;

import com.msj.myapp.Post.entity.Post;
import com.msj.myapp.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Primary
public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByUserAndPost(User user, Post post);
}
