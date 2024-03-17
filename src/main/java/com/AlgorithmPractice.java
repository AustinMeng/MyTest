package com;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AlgorithmPractice {
  public int divide(int dividend, int divisor) {
    if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
    int negative = 2;
    if (dividend > 0) {
      negative--;
      dividend = -dividend;
    }

    if (divisor > 0) {
      negative--;
      divisor = -divisor;
    }

    int result = 0;
    while(dividend < divisor) {
      int value = divisor;
      int quotient = 1;
      while(value >= 0xC0000000 && dividend < value + value) {
        quotient += quotient;
        value += value;
      }

      result += quotient;
      dividend -= value;
    }
    return negative == 1 ? -result : result;
  }

  public String addBinary(String a, String b) {
    StringBuilder result = new StringBuilder();
    int i = a.length() - 1;
    int j = b.length() - 1;
    int carry = 0;
    while (i >= 0 || j >= 0) {
      int digitA = i >= 0 ? a.charAt(i) - '0' : 0;
      int digitB = j >= 0 ? b.charAt(j) - '0' : 0;
      int sum = digitA + digitB + carry;
      carry = sum >= 2 ? 1 : 0;
      sum = sum >= 2 ? sum - 2 : 0;// sum = sum > 2 : 1 : 0
      result.append(sum);
    }
    return result.reverse().toString();
  }

  public int[] countBits(int num) {
    int[] result = new int[num + 1];
    for (int i = 0; i <= num; i++) {
      int j = i;
      while (j != 0) {
        result[i]++;
        j = j & (j - 1);
      }
    }
    return result;
  }

  public int singleNumber(int[] nums) {
    int[] bitSums = new int[32];
    for (int num : nums) {
      for (int i = 0; i < 32; i++) {
        bitSums[i] += (num >> (31 - i)) & 1;
      }
    }

    int result = 0;
    for (int i = 0; i < 32; i++) {
      result = (result << 1) + bitSums[i] % 3;
    }

    return result;
  }

  public int maxProduct(String[] words) {
    boolean[][] flags = new boolean[words.length][26];
    for (int i = 0; i < words.length; i++) {
      for (char c : words[i].toCharArray()) {
        flags[i][c-'a'] = true;
      }
    }

    int result = 0;
    for (int i = 0; i < words.length; i++) {
      for (int j = i + 1; j < words.length; j++) {
        int k = 0;
        for (; k < 26; k++) {
          if (flags[i][k] && flags[j][k]) {
            break;
          }
        }
        if (k == 26) {
          int prod = words[i].length() * words[j].length();
          result = Math.max(result, prod);
        }
      }
    }
    return result;
  }

  public int maxProduct1(String[] words) {
    int[] flags = new int[words.length];
    for (int i = 0; i < words.length; i++) {
      for (char c : words[i].toCharArray()) {
        flags[i] |= 1 << (c - 'a');
      }
    }

    int result = 0;
    for (int i = 0; i < words.length; i++) {
      for (int j = i + 1; j < words.length; j++) {
        if ((flags[i] & flags[j]) == 0) {
          int prod = words[i].length() * words[j].length();
          result = Math.max(result, prod);
        }
      }
    }
    return result;
  }

  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new LinkedList<>();
    if (nums.length >= 3) {
      Arrays.sort(nums);

      int i = 0;
      while (i < nums.length - 2) {
        twoSum(nums, i, result);
        int temp = nums[i];
        while (i < nums.length && nums[i] == temp) {
          ++i;
        }
      }
    }
    return result;
  }

  private void twoSum(int[] nums, int i, List<List<Integer>> result) {
    int j = i + 1;
    int k = nums.length - 1;
    while (j < k) {
      if (nums[i] + nums[j] + nums[k] == 0) {
        result.add(Arrays.asList(nums[i], nums[j], nums[k]));
        int temp = nums[j];
        while (j < k && nums[j] == temp) {
          j++;
        }
      } else if (nums[i] + nums[j] + nums[k] < 0) {
        j++;
      } else {
        k--;
      }
    }
  }

  public int minSubArrayLen(int k, int[] nums) {
    int left = 0;
    int sum = 0;
    int minLength = Integer.MAX_VALUE;
    for (int right = 0; right < nums.length; right++) {
      sum += nums[right];
      while (left <= right && sum >= k) {
        minLength = Math.min(minLength, right - left + 1);
        sum -= nums[left++];
      }
    }
    return minLength == Integer.MAX_VALUE ? 0 : minLength;
  }

  public int numSubarrayProductLessThanK(int[] nums, int k) {
    long product = 1;
    int left = 0;
    int count = 0;
    for (int right = 0; right < nums.length; right++) {
      product *= nums[right];
      while (left <= right && product >= k) {
        product /= nums[left++];
      }

      count += right >= left ? right - left + 1 : 0;
    }
    return count;
  }

  public int subArraySum(int[] nums, int k) {
    Map<Integer, Integer> sumToCount = new HashMap<>();
    sumToCount.put(0, 1);

    int sum = 0;
    int count = 0;
    for (int num : nums) {
      sum += num;
      count += sumToCount.getOrDefault(sum - k, 0);
      sumToCount.put(sum, sumToCount.getOrDefault(sum, 0) + 1);
    }
    return count;
  }

  public int findMaxLength(int[] nums) {
    Map<Integer, Integer> sumToIndex = new HashMap<>();
    sumToIndex.put(0, -1);
    int sum = 0;
    int maxLength = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i] == 0 ? -1 : 1;
      if (sumToIndex.containsKey(sum)) {
        maxLength = Math.max(maxLength, i - sumToIndex.get(sum));
      } else {
        sumToIndex.put(sum, i);
      }
    }
    return maxLength;
  }

  public int pivotIndex(int[] nums) {
    int total = 0;
    for (int num : nums) {
      total += num;
    }

    int sum = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      if (sum - nums[i] == total - sum) {
        return i;
      }
    }
    return -1;
  }

  public boolean checkInclusion(String s1, String s2) {
    if (s2.length() < s1.length()) return false;
    int[] counts = new int[26];
    for (int i = 0; i < s1.length(); i++) {
      counts[s1.charAt(i) - 'a']++;
      counts[s2.charAt(i) - 'a']--;
    }
    if (areAllZero(counts)) return true;

    for (int i = s1.length(); i < s2.length(); i++) {
      counts[s2.charAt(i) - 'a']--;
      counts[s2.charAt(i - s1.length()) - 'a']++;
      if (areAllZero(counts)) return true;
    }
    return false;
  }

  private boolean areAllZero(int[] counts) {
    for (int count : counts) {
      if (count != 0) return false;
    }
    return true;
  }

  public int lengthOfLongestSubString(String s) {
    if (s.length() == 0) {
      return 0;
    }

    int[] counts = new int[256];//record the number of each char between i(exclusive) and j
    int i = 0;
    int j = 0 ;//deal with first char
    int longest = 1;
    for (; i < s.length(); i++) {
      counts[s.charAt(i)]++;
      while (hasGreaterThan1(counts)) {//check if there is more than 1 duplicate char
        counts[s.charAt(j)]--;
        j++;
      }
      longest = Math.max(i - j + 1, longest);
    }
    return longest;
  }

  private boolean hasGreaterThan1(int[] counts) {
    for (int count : counts) {
      if (count > 1) {
        return true;
      }
    }
    return false;
  }


  public String minWindow(String s, String t) {
    Map<Character, Integer> charToCount = new HashMap<>();
    for (char ch : t.toCharArray()) {
      charToCount.put(ch, charToCount.getOrDefault(ch, 0) + 1);
    }

    int count = charToCount.size();
    int start = 0, end = 0, minStart = 0, minEnd = 0;
    int minLength = Integer.MAX_VALUE;
    while (end < s.length() || count == 0 && end == s.length()) {
      if (count > 0) {
        char endCh = s.charAt(end);
        if (charToCount.containsKey(endCh)) {
          charToCount.put(endCh, charToCount.get(endCh) - 1);
          if (charToCount.get(endCh) == 0) {
            count--;
          }
        }
        end++;
      } else {
        if (end - start < minLength) {
          minLength = end - start;
          minStart = start;
          minEnd = end;
        }
        char startCh = s.charAt(start);
        if (charToCount.containsKey(startCh)) {
          charToCount.put(startCh, charToCount.get(startCh) + 1);
          if (charToCount.get(startCh) == 1) {
            count++;
          }
        }
        start++;
      }
    }
    return minLength < Integer.MAX_VALUE ? s.substring(minStart, minEnd) : "";
  }

  public int countSubStrings(String s) {
    if (s == null || s.length() == 0) return 0;

    int count = 0;
    for (int i = 0; i < s.length(); i++) {
      count += countPalindrome(s, i, i);
      count += countPalindrome(s, i, i + 1);
    }
    return count;
  }

  private int countPalindrome(String s, int start, int end) {
    int count = 0;
    while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
      count++;
      start--;
      end++;
    }
    return count;
  }

  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;

    ListNode front = head, back = dummy;
    for (int i = 0; i < n; i++) {
      front = front.next;
    }

    while (front != null) {
      front = front.next;
      back = back.next;
    }

    back.next = back.next.next;
    return dummy.next;
  }

  public static void main(String[] args) {
    int i = Integer.MAX_VALUE;
    System.out.println(i << 1);
    //System.out.println(i <<< 1);

    AlgorithmPractice algo = new AlgorithmPractice();
    System.out.println(algo.lengthOfLongestSubString("babcca"));
  }

  public ListNode detectCycle(ListNode head) {
    ListNode inLoop = getNodeInloop(head);
    if (inLoop == null) return null;

    int loopCount = 1;
    for (ListNode n = inLoop; n.next != inLoop; n = n.next) {
      loopCount++;
    }

    ListNode fast = head;
    for (int i = 0; i < loopCount; i++) {
      fast = fast.next;
    }

    ListNode slow = head;
    while (fast != slow) {
      fast = fast.next;
      slow = slow. next;
    }
    return slow;
  }
  private ListNode getNodeInloop(ListNode head) {
    if (head == null || head.next == null) {
      return null;
    }

    ListNode slow = head.next;
    ListNode fast = slow.next;
    while (slow != null && fast != null) {
      if (slow == fast) {
        return slow;
      }
      slow = slow.next;
      fast = fast.next;
      if (fast.next != null) {
        fast = fast.next;
      }
    }
    return null;
  }
}

class ListNode {
  ListNode next;
  int val;
  ListNode(int val) {
    this.val = val;
  }
}
