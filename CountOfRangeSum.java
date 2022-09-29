// LeetCode 327. Count of Range Sum
// Given an integer array nums and two integers lower and upper, return the number 
// of range sums that lie in [lower, upper] inclusive. Range sum S(i, j) is defined as 
// the sum of the elements in nums between indices i and j inclusive, where i <= j.
// Example 1:
// Input: nums = [-2,5,-1], lower = -2, upper = 2
// Output: 3
// Explanation: The three ranges are: [0,0], [2,2], and [0,2] and their respective sums are: -2, -1, 2.
// Example 2:
// Input: nums = [0], lower = 0, upper = 0
// Output: 1

import java.util.*;

class CountOfRangeSum {
    public static int countRangeSum(int[] nums, int lower, int upper) {
        int n = nums.length, ans = 0;
        long[] pre = new long[n+1];
        for (int i = 0; i < n; i++){
            pre[i+1] = nums[i] + pre[i];
        }
        Arrays.sort(pre);
        int[] bit = new int[pre.length+2];
        long sum = 0;
        for (int i = 0; i < n; i++){
            update(bit, bs(sum, pre), 1);
            sum += nums[i];
            ans += sum(bit, bs(sum-lower, pre)) - sum(bit, bs(sum-upper-1, pre));
        }
        return ans;
    }

    private static int bs(long sum, long[] pre){ // return the index of first number bigger than sum
        int lo = 0, hi = pre.length;
        while(lo < hi){
            int mid = (lo+hi) >> 1;
            if (pre[mid] > sum){
                hi = mid;
            }else{
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static void update(int[] bit, int idx, int inc){
        for (++idx; idx < bit.length; idx += idx & -idx){
            bit[idx] += inc;
        }
    }

    private static int sum(int[] bit, int idx){
        int ans = 0;
        for (++idx; idx > 0; idx -= idx & -idx){
            ans += bit[idx];
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int[] arr = new int[size];
        for(int i = 0; i < size; i++) {
            arr[i] = sc.nextInt();
        }
        int lower = sc.nextInt();
        int upper = sc.nextInt(); 
        int result = countRangeSum(arr, lower, upper);
        System.out.println(result);
        sc.close();
    }
}