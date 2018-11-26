package org.vitoliu.concurrent;

import java.util.concurrent.ExecutorService;

/**
 * 带标识的{@link ExecutorService}
 * @author yukun.liu
 * @since 26 十一月 2018
 */
public interface NamedExecutorService extends ExecutorService {

	String getName();

	ExecutorService getDelegate();
}
