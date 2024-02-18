package Like;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class LikeRequestDTO {
    private Long UserId;
    private Long postId;

    public LikeRequestDTO(Long userId, Long postId){
        this.UserId = userId;
        this.postId = postId;
    }
}
