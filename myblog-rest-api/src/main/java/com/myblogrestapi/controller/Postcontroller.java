package com.myblogrestapi.controller;


import com.myblogrestapi.payload.PostDto;
import com.myblogrestapi.payload.PostResponse;
import com.myblogrestapi.service.Postservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class Postcontroller {

    private Postservice postservice;

    public Postcontroller(Postservice postservice) {
        this.postservice = postservice;
    }


    @PostMapping
    public ResponseEntity<PostDto> createpost(@Valid  @RequestBody PostDto postDto){
        ResponseEntity<PostDto> postdto = new ResponseEntity<>(postservice.createpost(postDto), HttpStatus.CREATED);
        return  postdto;
    }


    @GetMapping
    public PostResponse getallposts
            (@RequestParam(value = "pageNo" , defaultValue = "0", required = false) int pageNo, @RequestParam(value = "pageSizw" , defaultValue = "5" , required = false) int pageSize, @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy, @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
            {
                PostResponse posts = postservice.getallpostto(pageNo, pageSize, sortBy,sortDir);
                return posts;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getpostbyid(@PathVariable("id") long id){
        PostDto getpostbyid = postservice.getpostbyid(id);
        return ResponseEntity.ok(getpostbyid);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto>  updatepostbyId(@RequestBody PostDto postDto,@PathVariable("id") long id){
      PostDto updatepostbyid = postservice.updatepostbyid(postDto,id);
        return new ResponseEntity<>(updatepostbyid,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletepostbyid(@PathVariable("id") long id){
        postservice.deleteidbypost(id);
        return new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);
    }
}
