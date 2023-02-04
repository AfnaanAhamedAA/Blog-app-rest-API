package com.myblogrestapi.controller;

import com.myblogrestapi.payload.CommentDto;
import com.myblogrestapi.service.Commentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class Commentcontroller {

    @Autowired
    private Commentservice commentService;

    public Commentcontroller(Commentservice commentservice) {
        this.commentService = commentservice;
    }


    @PostMapping("/{postid}/comments")
    public ResponseEntity<CommentDto> createComments(@Valid @PathVariable("postid") long postId,@Valid @RequestBody CommentDto commentDto){
        CommentDto createcomments = commentService.createcomment(postId, commentDto);
        return new ResponseEntity<>(createcomments,HttpStatus.CREATED);
    }

    @GetMapping("/{post_id}/comments")
    public List<CommentDto> getCommentById(@PathVariable("post_id") long postId, CommentDto commentDto){
        List<CommentDto> comment = commentService.getComment(postId, commentDto);
        return comment;
    }

    @GetMapping("/{post_id}/comments/{commentid}")
    public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable("post_id") long postId, @PathVariable("commentid") long commentId){
        CommentDto commentDto = commentService.getcommentbyId(postId, commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @PutMapping("/{postid}/comments/{commentid}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postid") long postId, @PathVariable("commentid") long commentId, @RequestBody CommentDto commentDto){
        CommentDto updatecomment = commentService.updatecomment(postId,commentId, commentDto);
        return new ResponseEntity<>(updatecomment,HttpStatus.OK);
    }

    @DeleteMapping("/{postid}/comments/{commentid}")
    public  ResponseEntity<String> deleteComment(@PathVariable("postid") long postId, @PathVariable("commentid") long commentId){
        commentService.deleteCommentbyId(postId,commentId);
        return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
    }


}
