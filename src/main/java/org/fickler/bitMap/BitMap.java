package org.fickler.bitMap;

/**
 * bitMap 位图，用于表示一组布尔值（0/1）。
 * 非常适合用来管理大量布尔状态的数据，因为它在内存占用中非常小，管理 100万个布尔值仅需 125KB。
 * 计算过程：1.一个布尔值仅有 0 或 1 状态，再计算机中使用 1bit 即可存储。
 *         2.100万个布尔值，需要 1000000 * 1bit = 1000000bit
 *         3.8bit = 1byte，所以 1000000bit = 1000000 / 8 = 125000byte
 *         4.1024byte = 1kb，所以 125000byte = 125000 / 1024 = 122.07kb
 *           近似可看做 1000byte = 1kb，所以 125000byte = 125kb
 * 使用 boolean 数组存储：一个 boolean 占用 1byte，所以 100万个 boolean 占用 1000000byte = 1000kb = 1mb
 * 【boolean 具体占用多少字节，不确定，通常的一种说法是，boolean 占用 4 哥字节，再 boolean 数组中占用 1 哥字节】
 *
 * Java 中提供了现成的 BitSet 类，实现与下面的自己实现类似，不同的地方是 BitSet 使用的事 long 数组，而不是 byte 数组，但是消耗的空间是相同的，因为都是使用 bit 来存储布尔值（0/1）。
 *
 * @author guojuhui@jbinfo.cn
 * @since 2025/3/27
 */
public class BitMap {

    private byte[] bits;
    private int size;

    public BitMap(int size) {
        this.size = size;
        // +7 是为了向上取整，例如 9 / 8 = 1，但是 9 / 8 + 7 = 2
        this.bits = new byte[(size + 7) / 8];
    }

    // 设置某一位为1
    public void set(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("超出范围");
        }
        // k / 8 得到 byte 数组的下标
        // k % 8 得到在 byte 数组中的位置
        bits[index / 8] |= (1 << (index % 8));
    }

    // 设置某一位为0
    public void clear(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("超出范围");
        }
        bits[index / 8] &= ~(1 << (index % 8));
    }

    // 获取某一位的值
    public boolean get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("超出范围");
        }
        return (bits[index / 8] & (1 << (index % 8))) != 0;
    }

    // 获取位图大小（比特位数量）
    public int size() {
        return size;
    }

    public static void main(String[] args) {

        BitMap bitMap = new BitMap(16); // byte[2]

        bitMap.set(3);  // bits[ 3 / 8 = 0] |= 1 << (3 % 8) = 00001000
        bitMap.set(7);  // bits[ 7 / 8 = 0] |= 1 << (7 % 8) = 10000000 => 10001000

        System.out.println(bitMap.get(3)); // true
        System.out.println(bitMap.get(7)); // true
        System.out.println(bitMap.get(4));

        // 10001000
        // 11110111
        //=10000000
        bitMap.clear(3);    // bits[ 3 / 8 = 0] &= ~(1 << (3 % 8)) = 11110111
        System.out.println(bitMap.get(3)); // false

    }

}
