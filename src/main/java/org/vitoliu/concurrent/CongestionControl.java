package org.vitoliu.concurrent;


import java.util.concurrent.locks.AbstractQueuedSynchronizer;


/**
 * 拥塞控制，通过继承实现具体的缩放逻辑
 * 本地AQS控制
 * 	   请求 -->
 * 	     -1 阻塞 <=0    循环CAS本地控制
 * 		释放
 * 		 +2   			循环CAS本地控制
 * 		超时
 * 	     -1				循环CAS本地控制
 * @author yukun.liu
 * @since 26 十一月 2018
 */
public class CongestionControl {

	private final BaseSync sync;

	protected int windows;

	public CongestionControl(int windows) {
		sync = new NoFairSync(windows);
	}

	public CongestionControl(int initialCongestionWindow, boolean fair) {
		sync = fair ? new FairSync(initialCongestionWindow) : new NoFairSync(initialCongestionWindow);
	}

	public void acquire() throws InterruptedException {
		sync.acquireSharedInterruptibly(1);
	}

	protected int increase(int next) {
		return next + 1 + 1;
	}

	protected int reduce(int next) {
		return next - 1;
	}

	abstract class BaseSync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = 1192457210091910933L;

		BaseSync(int windows) {
			setState(windows);
		}

		final int getWindows() {
			return getState();
		}

		final int noFairTryAcquireShared(int acquires) {
			for (; ; ) {
				int available = getState();
				int remaining = getNext(acquires, available);
				if (remaining < 0 || compareAndSetState(available, remaining)) {
					return remaining;
				}
			}
		}

		@Override
		protected boolean tryRelease(int release) {
			for (; ; ) {
				int current = getState();
				int next = current;
				for (int i = 0; i < release; i++) {
					next = increase(next);
				}
				//出现overflow的情况
				if (next < current) {
					throw new Error("Congestion Window count exceeded!");
				}
				if (compareAndSetState(current, next)) {
					return true;
				}
			}
		}

		final void reduceWindows(int reductions) {
			for (; ; ) {
				int current = getState();
				int next = getNext(reductions, current);
				//出现underflow的情况
				if (next > current) {
					throw new Error("Congestion Window underflow!");
				}
				if (compareAndSetState(current, next)) {
					return;
				}
			}
		}

		final int drainWindows() {
			for (; ; ) {
				int current = getState();
				if (current == 0 || compareAndSetState(current, 0)) {
					return current;
				}
			}
		}

		private int getNext(int reductions, int current) {
			int next = current;
			for (int i = 0; i < reductions; i++) {
				next = reduce(next);
			}
			return next;
		}
	}

	final class NoFairSync extends BaseSync {
		private static final long serialVersionUID = -2694183684443567898L;

		NoFairSync(int windows) {
			super(windows);
		}

		@Override
		protected int tryAcquireShared(int acquires) {
			return noFairTryAcquireShared(acquires);
		}
	}

	final class FairSync extends BaseSync {
		private static final long serialVersionUID = 2014338818796000944L;

		FairSync(int windows) {
			super(windows);
		}

		@Override
		protected int tryAcquireShared(int acquires) {
			for (;;){
				if (hasQueuedPredecessors()){
					return -1;
				}
				int available = getState();
				int remaining = available;
				for (int i = 0; i < acquires; i++) {
					remaining = reduce(remaining);
				}
				if (remaining < 0 || compareAndSetState(available, remaining)) {
					return remaining;
				}
			}
		}
	}
}
