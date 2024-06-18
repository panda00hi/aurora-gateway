package com.aurora.gateway.common.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 雪花算法生成唯一ID
 */
public class SnowflakeIdWorker {

    // 自定义起始时间（单位：毫秒）
    private static final long EPOCH = 1582900800L;

    // 序列号位数
    private static final long SEQUENCE_BITS = 12L;

    // 工作机器ID位数
    private static final long WORKER_ID_BITS = 10L;

    // 数据中心ID位数
    private static final long DATA_CENTER_ID_BITS = 5L;

    // 最大的工作机器ID
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    // 最大的数据中心ID
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    // 最大的序列号
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    // 工作机器ID
    private final long workerId;

    // 数据中心ID
    private final long dataCenterId;

    // 序列号，使用 AtomicLong 实现线程安全
    private final AtomicLong sequence = new AtomicLong(0L);

    // 上一次生成 ID 的时间戳（单位：毫秒）
    private long lastTimestamp = -1L;

    public SnowflakeIdWorker(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 生成下一个唯一 ID
     *
     * @return 唯一 ID
     */
    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        // 如果当前时间小于上一次生成 ID 的时间戳，说明系统时钟回退了，这时抛出异常。
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - currentTimestamp));
        }

        // 如果在同一毫秒内，则对序列号进行自增，直到最大值，然后等待下一毫秒的到来。
        if (currentTimestamp == lastTimestamp) {
            sequence.set(sequence.incrementAndGet() & MAX_SEQUENCE);
            if (sequence.get() == 0) {
                currentTimestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence.set(0);
        }

        lastTimestamp = currentTimestamp;

        // 组装全局唯一 ID
        return ((currentTimestamp - EPOCH) << (WORKER_ID_BITS + DATA_CENTER_ID_BITS)) |
                (dataCenterId << WORKER_ID_BITS) |
                workerId |
                sequence.get();
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上一次生成 ID 的时间戳
     * @return 新的时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}