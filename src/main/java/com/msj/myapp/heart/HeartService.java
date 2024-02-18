package com.msj.myapp.heart;

import com.msj.myapp.program.Program;
import com.msj.myapp.program.ProgramRepository;
import com.msj.myapp.user.User;
import com.msj.myapp.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final ProgramRepository programRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;


    @Transactional
    public ResponseEntity insert(HeartRequestDTO heartRequestDTO) {
        Optional<User> user = userRepository.findById(heartRequestDTO.getUserId());
        Optional<Program> program = programRepository.findById(heartRequestDTO.getProgramId());
        if (!user.isPresent() || !program.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            Heart heart = Heart.builder()
                    .user(user.get())
                    .program(program.get())
                    .build();
            heartRepository.save(heart);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @Transactional
    public void delete(HeartRequestDTO heartRequestDTO) {

    }
}
