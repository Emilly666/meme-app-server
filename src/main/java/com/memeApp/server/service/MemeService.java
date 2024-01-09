package com.memeApp.server.service;

import com.memeApp.server.config.JwtService;
import com.memeApp.server.dto.request.GetMemesRequest;
import com.memeApp.server.dto.request.UploadMemeRequest;
import com.memeApp.server.dto.response.UploadMemeResponse;
import com.memeApp.server.model.meme.Meme;
import com.memeApp.server.model.meme.MemeRepository;
import com.memeApp.server.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemeService{

    @Value("${memesFolder}")
    private String FOLDER_PATH;
    private final MemeRepository memeRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;



    public UploadMemeResponse upload(UploadMemeRequest request,  String token) throws IOException {

        var user = userRepository.findByEmail(jwtService.extractUsername(token)).orElseThrow();

        if(jwtService.isTokenValid(token, user)){
            String newName = request.getImage().getOriginalFilename();
            int fileCounter = 0;
            File f = new File(FOLDER_PATH + request.getImage().getOriginalFilename());
            String originalName = FilenameUtils.getBaseName(request.getImage().getOriginalFilename());
            while(f.exists()){
                fileCounter++;
                newName =  originalName + "(" + fileCounter + ")." + FilenameUtils.getExtension(request.getImage().getOriginalFilename());
                f = new File(FOLDER_PATH + newName);
            }
            request.getImage().transferTo(f);

            Meme meme = new Meme();
            meme.setFile_path(newName);
            meme.setTitle(request.getTitle());
            meme.setUser_id(user.getId());
            meme = memeRepository.save(meme);
            return new UploadMemeResponse(meme);
        }
        return null;

    }
    public ByteArrayResource downloadMeme(String fileName) throws IOException {
        return new ByteArrayResource(Files.readAllBytes(Paths.get(FOLDER_PATH + fileName )));
    }
    public List<Meme> getMemes(GetMemesRequest request){
        List<Meme> memes = null;
        if (request.getLastMeme_id() == 0){
            request.setLastMeme_id(memeRepository.getMaxId());
        }
        if(request.getTag_id() == 0){
            memes = memeRepository.getNextMemes(request.getLastMeme_id(), request.getCount());
        }
        return memes;
    }
}

