package com.qmth.wuda.teaching.util;

import java.text.ParseException;

/**
 * SnowFlake算法修改版生成递增分布式ID<br/>
 *
 * @author luoshi
 */
public class UidGenerator {

    // 起始时间戳，2020-06-01 00:00:00
    private final static long START_TIMESTAMP = 1590940800000L;

    // 序列号占用的位数
    private final static long SEQUENCE_BIT = 12;

    // 数据中心编号占用的位数
    private final static long DATACENTER_BIT = 3;

    // 机器编号占用的位数
    private final static long MACHINE_BIT = 7;

    // 最大数据中心编号
    public final static long MAX_DATACENTER_ID = ~(-1L << DATACENTER_BIT);

    // 最大机器编号
    public final static long MAX_MACHINE_ID = ~(-1L << MACHINE_BIT);

    // 最大序列号
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;

    private final static long DATACENTER_LEFT = MACHINE_LEFT + MACHINE_BIT;

    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;

    // 机器编号
    private long machineId;

    // 序列号
    private long sequence = 0L;

    // 上一次时间戳
    private long lastTimestamp = -1L;

    public UidGenerator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(
                    "datacenterId can't be greater than " + MAX_DATACENTER_ID + " or less than 0");
        }
        if (machineId > MAX_MACHINE_ID || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than " + MAX_MACHINE_ID + " or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个UID
     *
     * @return
     */
    public synchronized long next() {
        long current = System.currentTimeMillis();
        // 不能在程序运行过程中回拨时钟，否则报异常需要等待时钟赶上后重试
        if (current < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate uid");
        }

        if (current == lastTimestamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列号已经用完，等待延迟到下一毫秒
            if (sequence == 0L) {
                current = waitNextMillisecond();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimestamp = current;

        return (current - START_TIMESTAMP) << TIMESTMP_LEFT // 时间戳部分
                | (datacenterId << DATACENTER_LEFT) //数据中心部分
                | (machineId << MACHINE_LEFT) // 机器部分
                | sequence; // 序列号部分
    }

    private long waitNextMillisecond() {
        long time = System.currentTimeMillis();
        while (time <= lastTimestamp) {
            time = System.currentTimeMillis();
        }
        return time;
    }

    private static String toFullBinaryString(long num) {
        // 规定输出的long型最多有64位（00 00000000 00000000 00000000 00000000 00000000）
        final int size = 64;
        char[] chs = new char[size];
        for (int i = 0; i < size; i++) {
            /**
             * 目的：获取第i位的二进制数 解析： ((num >> i) & 1)： 因为1的二进制的特殊性（0000
             * 0001），在进行“&”运算时，只有末位会被保留下来（比如，末位为“1”，运算
             * 后为1；末位为0，运算后为0），其他位置全部被置为0。
             * 这样当需要将一个整型转换成二进制表示时，需要取出每个位置的二进制数值（1或者0），那么就可以右移该位置
             * 数，与“1”进行“&”运算。 比如：取出3（0000 0011）的第2位（也就是1），则右移1位--》得到“0000
             * 0001”，然后与1（“0000 0001”） 进行“&”运算，得到“1”。以此类推即可得到每个位置为二进制数 “((num >>
             * i) & 1) + '0'”： 字符‘0’的“ASCLL”码对应的数值为48，比如"(char)
             * (49)"那么计算字符的方法就是：49-48 = 1，所以对应的 字符就是'1',所以“((num >> i) & 1) +
             * '0'”这段代码的意思就是，取出“num”二进制时对应的每个位置数值（只能 为“0”或“1”，不过此时还是二进制表示法：0
             * 0000 0000 ； 1 0000 0001）。之后，与字符'0'对应的数值“48”进行 加运算，结果只能为（48 或者
             * 49），再经过（char）强转后（“char”强转就是根据传入的数值，与“48”进行比较，
             * 比如“49”，多1，那么对应的字符就是“1”），就变成了字符,“0”或者“1”，就取出了特定位置的二进制数值
             *
             */
            chs[size - 1 - i] = (char) (((num >> i) & 1) + '0');
        }
        return new String(chs);
    }

    public static void main(String[] args) throws ParseException {
        //        UidGenerator[] uids = new UidGenerator[15];
        //        for (int i = 0; i < 15; i++) {
        //            uids[i] = new UidGenerator(0, i);
        //        }
        //        for (int i = 0; i < 100; i++) {
        //            System.out.println("index:" + i);
        //            for (UidGenerator uid : uids) {
        //                long id = uid.next();
        //                System.out.println(id + ":" + toFullBinaryString(id));
        //            }
        //        }
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println(format.parse("2020-06-01 00:00:00").getTime());
    }

}
