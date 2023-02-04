package com.myblogrestapi.service;


import com.myblogrestapi.payload.CommentDto;

import java.util.List;

public interface Commentservice {

    public CommentDto createcomment(long postId,CommentDto commentDto);

    public List<CommentDto> getComment(long postId, CommentDto commentDto);

    public CommentDto updatecomment( long postId,long commentId, CommentDto commentDto);

     public CommentDto getcommentbyId(long postId, long commentId);

   public void deleteCommentbyId(long postId, long commentId);
}
