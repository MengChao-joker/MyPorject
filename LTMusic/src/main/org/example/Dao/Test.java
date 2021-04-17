package org.example.Dao;

import java.util.List;

public class Test {
    static class Nums{
        List<Integer> nums;
    }

    public static void main(String[] args) {
        Nums nums = new Nums();
        for (int i : nums.nums) {
            System.out.println(i);
        }
        System.out.println("遍历结束");
    }
}
