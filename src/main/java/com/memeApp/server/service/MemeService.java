package com.memeApp.server.service;

import com.memeApp.server.config.JwtService;
import com.memeApp.server.dto.request.GetMemesRequest;
import com.memeApp.server.dto.request.UploadMemeRequest;
import com.memeApp.server.dto.response.UploadMemeResponse;
import com.memeApp.server.model.meme.Meme;
import com.memeApp.server.model.meme.MemeRepository;
import com.memeApp.server.model.tag.Tag;
import com.memeApp.server.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
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

    private final MemeRepository memeRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final String FOLDER_PATH = "C:\\Users\\kalan\\OneDrive\\Pulpit\\Engineering project\\Memes\\";


    public UploadMemeResponse upload(UploadMemeRequest request,  String token) throws IOException {
        var user = userRepository.findById(request.getUser_id()).orElseThrow();

        if(jwtService.isTokenValid(token, user)){
            String path = FOLDER_PATH + request.getImage().getOriginalFilename();
            int fileCounter = 0;
            File f = new File(path);
            String originalName = FilenameUtils.getBaseName(path);
            while(f.exists()){
                fileCounter++;
                path = FOLDER_PATH + originalName + "(" + fileCounter + ")." + FilenameUtils.getExtension(path);
                f = new File(path);
            }
            request.getImage().transferTo(f);

            Meme meme = new Meme();
            meme.setFile_path(path);
            meme.setTitle(request.getTitle());
            meme.setUser_id(user.getId());
            meme = memeRepository.save(meme);
            return new UploadMemeResponse(meme);
        }
        return null;

    }
    public ByteArrayResource downloadMeme(String fileName) throws IOException {
        return new ByteArrayResource(Files.readAllBytes(Paths.get(
                "C:\\Users\\kalan\\OneDrive\\Pulpit\\Engineering project\\Memes\\" + fileName )));
    }
    public List<Map<Meme, List<Tag>>> getMemes(GetMemesRequest request){
        if (request.getLastMeme_id() == 0){
            request.setLastMeme_id(memeRepository.getMaxId());
        }
        if(request.getTag_id() == 0){

        }
        return null;
    }
}

