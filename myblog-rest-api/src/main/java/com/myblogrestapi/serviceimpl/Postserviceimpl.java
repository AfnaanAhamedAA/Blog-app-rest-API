package com.myblogrestapi.serviceimpl;

import com.myblogrestapi.entity.Post;
import com.myblogrestapi.exception.ResoueceNotFoundException;
import com.myblogrestapi.payload.PostDto;
import com.myblogrestapi.payload.PostResponse;
import com.myblogrestapi.repository.PostRepository;
import com.myblogrestapi.service.Postservice;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Postserviceimpl implements Postservice {

    private PostRepository postrepo;



    public Postserviceimpl(PostRepository postrepo) {
        this.postrepo = postrepo;

    }

    @Override
    public PostDto createpost(PostDto postDto) {

        Post post = maptoEntity(postDto);
        Post postEntity = postrepo.save(post);

        PostDto Dto = maptoDto(post);
        return Dto;
    }

    @Override
    public PostResponse getallpostto(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postrepo.findAll(pageable);
        List<Post> content = posts.getContent();
        List<PostDto> postdto = content.stream().map(post -> maptoDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(pageable.getPageNumber());
        postResponse.setPageSize(pageable.getPageSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getpostbyid(long id) {
        Post post = postrepo.findById(id).orElseThrow(() -> new ResoueceNotFoundException("post", "id", id));
        PostDto postDto = maptoDto(post);
        return postDto;
    }

    @Override
    public PostDto updatepostbyid(PostDto postDto,long id ) {
        Post post = postrepo.findById(id).orElseThrow(() -> new  ResoueceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post save = postrepo.save(post);
        PostDto postDtos = maptoDto(save);
        return postDtos;
    }

    @Override
    public void  deleteidbypost(long id) {
        postrepo.findById(id).orElseThrow(() -> new  ResoueceNotFoundException("post", "id", id));
        postrepo.deleteById(id) ;
    }

    public Post maptoEntity(PostDto postDto){
        //Post post = mapper.map(postDto,Post.class);
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    public PostDto maptoDto(Post post){
       // PostDto postDto = mapper.map(post,PostDto.class);
        PostDto Dto = new PostDto();
        Dto.setId(post.getId());
        Dto.setTitle(post.getTitle());
        Dto.setDescription(post.getDescription());
        Dto.setContent(post.getContent());
          return Dto;
    }



}
