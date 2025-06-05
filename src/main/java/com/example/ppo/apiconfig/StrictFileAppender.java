package com.example.ppo.apiconfig;

import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.status.ErrorStatus;

public class StrictFileAppender<E> extends FileAppender<E> {
    @Override
    public void start() {
        try {
            super.start();
        } catch (Exception e) {
            addStatus(new ErrorStatus("CRITICAL: Failed to start FileAppender", this, e));
            throw new IllegalStateException("Fatal log initialization error", e);
        }
    }

    @Override
    protected void append(E event) {
        try {
            super.append(event);
        } catch (Exception e) {
            addStatus(new ErrorStatus("CRITICAL: Failed to write log", this, e));
            throw new RuntimeException("Fatal log write error", e);
        }
    }
}