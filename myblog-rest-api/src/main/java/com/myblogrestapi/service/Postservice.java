package com.myblogrestapi.service;


import com.myblogrestapi.payload.PostDto;
import com.myblogrestapi.payload.PostResponse;

import java.util.List;

public interface Postservice {
    public PostDto createpost(PostDto postDto);
    public PostResponse getallpostto(int pageNo, int pageSize, String sortBy, String  sortDir);

   public PostDto getpostbyid(long id);

    public PostDto updatepostbyid(PostDto postDto,long id);

    public void deleteidbypost(long id);
}
