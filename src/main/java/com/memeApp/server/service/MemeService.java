package com.memeApp.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memeApp.server.config.JwtService;
import com.memeApp.server.dto.request.GetMemesRequest;
import com.memeApp.server.dto.request.UploadMemeRequest;
import com.memeApp.server.dto.response.GetMemesResponse;
import com.memeApp.server.dto.response.UploadMemeResponse;
import com.memeApp.server.model.meme.Meme;
import com.memeApp.server.model.meme.MemeRepository;
import com.memeApp.server.dto.response.MemeResponse;
import com.memeApp.server.model.meme.likes.MemeLikesRepository;
import com.memeApp.server.model.memeTag.MemeTag;
import com.memeApp.server.model.memeTag.MemeTagRepository;
import com.memeApp.server.model.tag.Tag;
import com.memeApp.server.model.tag.TagRepository;
import com.memeApp.server.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemeService{

    @Value("${memesFolder}")
    private String FOLDER_PATH;
    private final MemeRepository memeRepository;
    private final UserRepository userRepository;
    private final MemeTagRepository memeTagRepository;
    private final MemeLikesRepository memeLikesRepository;
    private final TagRepository tagRepository;
    private final JwtService jwtService;



    public UploadMemeResponse upload(UploadMemeRequest request,  String token) throws IOException {
        String contentType = Files.probeContentType(Path.of(request.getImage().getOriginalFilename()));
        if(!(contentType.equals("image/gif") || contentType.equals("image/png"))){
            return null;
        }

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

            ObjectMapper mapper = new ObjectMapper();
            String[] tagsArray = mapper.readValue(request.getTags(), String[].class);
            List<String> tagsList = Arrays.asList(tagsArray);
            List<Tag> memeTags = new ArrayList<>();
            for (String s : tagsList) {
                if (tagRepository.findByName(s).isEmpty()) {
                    Tag tag = new Tag();
                    tag.setName(s);
                    memeTags.add(tagRepository.save(tag));
                } else {
                    memeTags.add(tagRepository.findTagByName(s));
                }
            }
            Meme meme = new Meme();
            meme.setFile_path(newName);
            meme.setContent_type(contentType);
            meme.setTitle(request.getTitle());
            meme.setUser_id(user.getId());
            meme = memeRepository.save(meme);
            for(int i = 0; i < memeTags.size(); i++){
                MemeTag memeTag = new MemeTag();
                memeTag.setMeme_id(meme.getId());
                memeTag.setTag_id(memeTags.get(i).getId());
                memeTagRepository.save(memeTag);
            }
            return new UploadMemeResponse(meme);
        }
        return null;
    }
    public ByteArrayResource downloadMeme(String fileName) throws IOException {
        return new ByteArrayResource(Files.readAllBytes(Paths.get(FOLDER_PATH + fileName )));
    }
    public GetMemesResponse getMemes(GetMemesRequest request){
        List<MemeResponse> memesWithTags = new ArrayList<>();
        List<Meme> memes;
        if (request.getLastMeme_id() == 0){
            request.setLastMeme_id(memeRepository.getMaxId());
        }
        if(request.getTag_id() == 0){
            memes = memeRepository.getNextMemes(request.getLastMeme_id(), request.getCount());
        }
        else{
            memes = memeRepository.getNextMemes(request.getLastMeme_id(), request.getCount(), request.getTag_id());
        }
        if(memes != null){
            for (Meme meme : memes) {
                Integer value = 0;
                if(request.getUser_id() != 0){
                    value = memeLikesRepository.getValue(request.getUser_id(), meme.getId());
                }
                if(value == null){
                    value = 0;
                }
                List<Object[]> tags = memeTagRepository.getMemeTagsByMemeId(meme.getId());
                List<Tag> tagsList= new ArrayList<>();
                for (Object[] object : tags) {
                    tagsList.add(new Tag((int)object[0], (String)object[1]));
                }
                memesWithTags.add(MemeResponse.builder()
                        .id(meme.getId())
                        .file_path(meme.getFile_path())
                        .content_type(meme.getContent_type())
                        .title(meme.getTitle())
                        .add_timestamp(meme.getAdd_timestamp())
                        .total_likes(meme.getTotal_likes())
                        .author_id(meme.getUser_id())
                        .author_nickname(userRepository.findUserById(meme.getUser_id()).getNickname())
                        .reactionValue(value)
                        .tags(tagsList)
                        .build());
            }
        }
        return new GetMemesResponse(memesWithTags);
    }
}

