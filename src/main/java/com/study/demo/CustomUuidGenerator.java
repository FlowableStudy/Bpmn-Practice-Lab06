package com.study.demo;

import org.flowable.common.engine.impl.cfg.IdGenerator;

import java.util.UUID;

/**
 * 自定义的uuid生成策略
 */
public class CustomUuidGenerator implements IdGenerator {
    public String getNextId() {
        return "custom:" + UUID.randomUUID().toString();
    }
}
