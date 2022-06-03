package com.don.postsservice.service.impl;

import com.don.postsservice.model.Post;
import com.don.postsservice.service.TransactionHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionHelperServiceImpl implements TransactionHelperService {
/*
    private final PostRepository postRepository;


    @Override
    @Transactional
    public Post savePost(Post post) {
        post.setUser(loggedUser.getLoggedUser());
        return postRepository.save(post);
    }*/
}
