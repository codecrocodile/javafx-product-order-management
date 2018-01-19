/*
 * @(#)FXUtilities.java			30 Mar 2013
 *
 * Copyright (c) 2012-2013 Groovy Fly.
 * 3 Aillort place, East Mains, East Kilbride, Scotland.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Groovy 
 * Fly. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Groovy Fly.
 */
package com.groovyfly.controlcentre.client.sitemanagement.gae;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;

/**
 * General JavaFX utilities
 *
 * @author hendrikebbers
 *
 */
public class FXUtilities {
 
    /**
     * Simple helper class.
     *
     * @author hendrikebbers
     *
     */
    private static class ThrowableWrapper {
        Throwable t;
    }
 
    /**
     * Invokes a Runnable in JFX Thread and waits while it's finished. Like
     * SwingUtilities.invokeAndWait does for EDT.
     *
     * @param run
     *            The Runnable that has to be called on JFX thread.
     * @throws InterruptedException
     *             f the execution is interrupted.
     * @throws ExecutionException
     *             If a exception is occurred in the run method of the Runnable
     */
    public static void runAndWait(final Runnable run)
            throws InterruptedException, ExecutionException {
        if (Platform.isFxApplicationThread()) {
            try {
                run.run();
            } catch (Exception e) {
                throw new ExecutionException(e);
            }
        } else {
            final Lock lock = new ReentrantLock();
            final Condition condition = lock.newCondition();
            final ThrowableWrapper throwableWrapper = new ThrowableWrapper();
            lock.lock();
            try {
                Platform.runLater(new Runnable() {
 
                    @Override
                    public void run() {
                        lock.lock();
                        try {
                            run.run();
                        } catch (Throwable e) {
                            throwableWrapper.t = e;
                        } finally {
                            try {
                                condition.signal();
                            } finally {
                                lock.unlock();
                            }
                        }
                    }
                });
                condition.await();
                if (throwableWrapper.t != null) {
                    throw new ExecutionException(throwableWrapper.t);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}