package edu.vuum.mocca;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @class SimpleSemaphore
 * @brief This class provides a simple counting semaphore implementation using
 * Java a ReentrantLock and a ConditionObject. It must implement both
 * "Fair" and "NonFair" semaphore semantics, just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
    private ReentrantLock lock;

    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
    // TODO - you fill in here
    private Condition condition;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
    private volatile int availablePermits;

    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
        availablePermits = permits;
        lock = new ReentrantLock(fair);
        condition = lock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here.
        lock.lock();
        while (availablePermits == 0) {
            condition.await();
        }

        availablePermits--;
        lock.unlock();
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here.
        lock.lock();
        while (availablePermits == 0) {
            condition.awaitUninterruptibly();
        }

        availablePermits--;
        lock.unlock();
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here.
        lock.lock();

        availablePermits++;
        condition.signal();

        lock.unlock();
    }

    /**
     * Return the number of permits available.
     */
    public long availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
        return availablePermits;
    }
}
