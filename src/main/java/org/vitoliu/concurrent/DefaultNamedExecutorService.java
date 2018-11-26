package org.vitoliu.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 做一些业务操作，如添加trace，监控等。
 *
 * @author yukun.liu
 * @since 26 十一月 2018
 */
public class DefaultNamedExecutorService implements NamedExecutorService {
	@Override
	public String getName() {
		return null;
	}

	@Override
	public ExecutorService getDelegate() {
		return null;
	}

	@Override
	public void shutdown() {

	}

	@Override
	public List<Runnable> shutdownNow() {
		return null;
	}

	@Override
	public boolean isShutdown() {
		return false;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return null;
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		return null;
	}

	@Override
	public Future<?> submit(Runnable task) {
		return null;
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return null;
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
		return null;
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return null;
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return null;
	}

	@Override
	public void execute(Runnable command) {

	}
}
