package com.zshs.messagecenter.util;

/**
 * 雪花算法工具类
 */
public class SnowflakeIdGenerator {

    // 起始的时间戳，可以设置一个合适的值
    private static final long START_TIMESTAMP = 1609459200000L; // 2021-01-01 00:00:00

    // 每一部分占用的位数
    private static final long SEQUENCE_BIT = 12; // 序列号占用的位数
    private static final long MACHINE_BIT = 5;   // 机器标识占用的位数
    private static final long DATACENTER_BIT = 5; // 数据中心占用的位数

    // 每一部分的最大值
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private static final long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);

    // 每一部分向左的位移
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  // 数据中心
    private long machineId;     // 机器标识
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上一次生成ID的时间戳

    /**
     * 构造函数
     *
     * @param datacenterId 数据中心ID
     * @param machineId    机器ID
     */
    public SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("数据中心ID不能大于" + MAX_DATACENTER_NUM + "或小于0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("机器ID不能大于" + MAX_MACHINE_NUM + "或小于0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 生成下一个ID
     *
     * @return 雪花算法生成的ID
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，此时应抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回退，拒绝生成ID");
        }

        // 如果当前时间与上一次ID生成的时间戳相同，则在序列号自增
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 如果序列号溢出，则等待下一个毫秒
            if (sequence == 0L) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 如果当前时间大于上一次ID生成的时间戳，则序列号重置为0
            sequence = 0L;
        }

        // 更新上一次ID生成的时间戳
        lastTimestamp = timestamp;

        // 通过位运算，将各部分ID组合成一个64位的ID
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT)
                | (datacenterId << DATACENTER_LEFT)
                | (machineId << MACHINE_LEFT)
                | sequence;
    }

    /**
     * 等待下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上一次ID生成的时间戳
     * @return 新的时间戳
     */
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(5, 3);

        for (int i = 0; i < 10; i++) {
            long id = idGenerator.nextId();
            System.out.println("生成的ID：" + id);
        }
    }
}
