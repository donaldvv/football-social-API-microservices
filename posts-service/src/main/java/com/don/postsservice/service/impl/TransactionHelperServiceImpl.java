package com.don.postsservice.service.impl;

import com.don.postsservice.model.Post;
import com.don.postsservice.repository.PostRepository;
import com.don.postsservice.service.TransactionHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionHelperServiceImpl /*implements TransactionHelperService */{

    private final PostRepository postRepository;

    /*@Override*/
    @Transactional
    public <T> T withTransaction(Supplier<T> supplier) {
        return supplier.get();
    }

    @Transactional
    public void withTransaction(Runnable runnable) {
        runnable.run();
    }

    @Transactional(readOnly = true)
    public <T> T withTransactionReadOnly(Supplier<T> supplier) {
        return supplier.get();
    }

    @Transactional(readOnly = true)
    public void withTransactionReadOnly(Runnable runnable) {
        runnable.run();
    }
}
