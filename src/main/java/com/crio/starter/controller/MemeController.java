package com.crio.starter.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.exchange.MemeIdResponseDTO;
import com.crio.starter.exchange.MemeResponseDTO;
import com.crio.starter.service.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memes")
public class MemeController {

    @Autowired
    private final MemeService memeService;

    @PostMapping
    public ResponseEntity<MemeIdResponseDTO> createMeme(@RequestBody MemeEntity memeEntity){
        try {
            MemeIdResponseDTO memeResponseDTO = memeService.createMeme(memeEntity);
            return new ResponseEntity<>(memeResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            if(e.getMessage().equals("Fields cannot be null or empty")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemeResponseDTO> getMemeById(@PathVariable String id) {
        MemeResponseDTO memeResponseDTO = memeService.getMemeById(id);

        if (memeResponseDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(memeResponseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MemeResponseDTO>> getLatestMemes() {
        List<MemeResponseDTO> memeResponseDTOs = memeService.getLatestMemes();
        return new ResponseEntity<>(memeResponseDTOs, HttpStatus.OK);
    }
}
