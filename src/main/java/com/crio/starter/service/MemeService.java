package com.crio.starter.service;

import lombok.RequiredArgsConstructor;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.exchange.MemeIdResponseDTO;
import com.crio.starter.exchange.MemeResponseDTO;
import com.crio.starter.repository.MemeRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemeService {
    
    @Autowired
    private final MemeRepository memeRepository;
     
    public MemeIdResponseDTO createMeme(MemeEntity memeEntity) throws Exception{
        if (memeEntity.hasEmptyFields()) {
            throw new Exception("Fields cannot be null or empty");
        }
        
        Optional<MemeEntity> duplicateMeme = memeRepository.findByNameAndUrlAndCaption(
            memeEntity.getName(), memeEntity.getUrl(), memeEntity.getCaption()
        );

        if (duplicateMeme.isPresent()) {
            throw new Exception("Duplicate meme exists");
        }
        MemeEntity savedMeme = memeRepository.save(memeEntity);
        MemeIdResponseDTO memeResponseDTO = new MemeIdResponseDTO();
        memeResponseDTO.setId(savedMeme.getId());
        return memeResponseDTO;
    }

    public MemeResponseDTO getMemeById(String id) {
        Optional<MemeEntity> memeEntityOptional = memeRepository.findById(id);

        if (!memeEntityOptional.isPresent()) {
            // handle not found case
            return null;
        }

        MemeEntity memeEntity = memeEntityOptional.get();
        MemeResponseDTO memeResponseDTO = new MemeResponseDTO();
        memeResponseDTO.setId(memeEntity.getId());
        memeResponseDTO.setName(memeEntity.getName());
        memeResponseDTO.setUrl(memeEntity.getUrl());
        memeResponseDTO.setCaption(memeEntity.getCaption());
        return memeResponseDTO;
    }
    
    public List<MemeResponseDTO> getLatestMemes() {
        Pageable pageable = PageRequest.of(0, 100);
        List<MemeEntity> memeEntities = memeRepository.findAllByOrderByIdDesc(pageable);
        return memeEntities.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private MemeResponseDTO convertToDto(MemeEntity memeEntity) {
        MemeResponseDTO memeResponseDTO = new MemeResponseDTO();
        memeResponseDTO.setId(memeEntity.getId());
        memeResponseDTO.setName(memeEntity.getName());
        memeResponseDTO.setUrl(memeEntity.getUrl());
        memeResponseDTO.setCaption(memeEntity.getCaption());
        return memeResponseDTO;
    }
    
}
