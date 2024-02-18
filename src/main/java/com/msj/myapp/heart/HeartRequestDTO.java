package com.msj.myapp.heart;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class HeartRequestDTO {
    private Long UserId;
    private Long ProgramId;

    public HeartRequestDTO(Long userId, Long ProgramId){
        this.UserId = userId;
        this.ProgramId = ProgramId;
    }
}
