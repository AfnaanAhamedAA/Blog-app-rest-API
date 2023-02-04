package com.myblogrestapi.serviceimpl;

import com.myblogrestapi.entity.Comment;
import com.myblogrestapi.entity.Post;
import com.myblogrestapi.exception.ResoueceNotFoundException;
import com.myblogrestapi.payload.CommentDto;
import com.myblogrestapi.repository.CommentRepository;
import com.myblogrestapi.repository.PostRepository;
import com.myblogrestapi.service.Commentservice;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Commentserviceimpl implements Commentservice {

    private PostRepository postrepo;
    private CommentRepository commentrepo;



    public Commentserviceimpl(PostRepository postrepo, CommentRepository commentrepo) {
        this.postrepo = postrepo;
        this.commentrepo = commentrepo;

    }

    @Override
    public CommentDto createcomment(long postId, CommentDto commentDto) {
        Post post = postrepo.findById(postId).orElseThrow(() -> new ResoueceNotFoundException("post", "id", postId));
        Comment comment = maptoEntity(commentDto);
        comment.setPost(post);
        Comment save = commentrepo.save(comment);
        CommentDto commentDto1 = maptoDto(save);
        return commentDto1;
    }

    @Override
    public List<CommentDto> getComment(long postId, CommentDto commentDto) {
        List<Comment> comments = commentrepo.findByPost_id(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> maptoDto(comment)).collect(Collectors.toList());
        return  commentDtos;
    }

    @Override
    public CommentDto updatecomment( long postId,long commentId, CommentDto commentDto) {
        Post post = postrepo.findById(postId).orElseThrow(() -> new ResoueceNotFoundException("post", "id", postId));
        Comment comment = commentrepo.findById(commentId).orElseThrow(() -> new ResoueceNotFoundException("comment", "id", commentId));
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment save1 = commentrepo.save(comment);
        CommentDto commentDtos = maptoDto(save1);
        return commentDtos;
    }

    @Override
    public CommentDto getcommentbyId(long postId, long commentId) {
        Post post = postrepo.findById(postId).orElseThrow(() -> new ResoueceNotFoundException("post", "id", postId));
        Comment comment = commentrepo.findById(commentId).orElseThrow(() -> new ResoueceNotFoundException("comment", "id", commentId));
        CommentDto commentDto = maptoDto(comment);
        return commentDto;
    }

    @Override
    public void deleteCommentbyId(long postId, long commentId) {
        Post post = postrepo.findById(postId).orElseThrow(() -> new ResoueceNotFoundException("post", "id", postId));
        Comment comment = commentrepo.findById(commentId).orElseThrow(() -> new ResoueceNotFoundException("comment", "id", commentId));
        commentrepo.delete(comment);
    }


    public Comment maptoEntity(CommentDto commentDto){
       // Comment comment =  mapper.map(commentDto,Comment.class);
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
         return comment;
    }

    public CommentDto maptoDto(Comment comment){
       // CommentDto commentDto = mapper.map(comment,CommentDto.class);
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        commentDto.setName(comment.getName());
        return commentDto;
    }
}
