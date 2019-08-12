package com.afeiluo.algorithm;

/**
 * @author ben
 * @date 2019/8/12
 * 最长回回文子串
 * https://leetcode-cn.com/problems/longest-palindromic-substring/description/
 */
public class Palindromic {
    //回文开始的位置
    private int index;

    //回文的长度
    private int len;

    public static void main(String[] args) {
        Palindromic palindromic = new Palindromic();
        System.out.println(palindromic.longestPalindrome("abdexedfg"));
    }

    public String longestPalindrome(String s) {
        if (s.length() < 2)
            return s;
        for (int i = 0; i < s.length() - 1; i++) {
            PalindromeHelper(s, i, i);
            PalindromeHelper(s, i, i + 1);
        }
        return s.substring(index, index + len);
    }

    public void PalindromeHelper(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        //之前的回文的长度小于当前的长度
        if (len < r - l - 1) {
            //起始位置left+1(while里面出来left多减了1)
            index = l + 1;
            //新的长度
            len = r - l - 1;
        }
    }
}

/**
 * 最长回文子序列， 最长回文子序列和最长回文子串的区别是，子串是字符串中连续的一个序列，而子序列是字符串中保持相对位置的字符序列，例如，"bbbb"可以是字符串"bbbab"的子序列但不是子串。
 * 动态规划： dp[i][j] = dp[i+1][j-1] + 2 if s.charAt(i) == s.charAt(j) otherwise, dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1])
 * https://blog.csdn.net/geekmanong/article/details/51056375
 */
class PalindromicSubSeq {
    public static void main(String[] args) {
        PalindromicSubSeq palindromicSubSeq = new PalindromicSubSeq();
        String  str= "aqsrdftgufdisa";
        // asdfgfdsa
        //System.out.println(palindromicSubSeq.longestPalindromeSubseq(str));
        System.out.println(palindromicSubSeq.longestPalindromeSubseqRecursion(str, 0 , str.length()-1));
    }

    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        int[][] dp = new int[len][len];
        //从最后的位置向前
        for (int i = len - 1; i >= 0; i--) {
            dp[i][i] = 1;
            //从当前位置向后
            for (int j = i + 1; j < len; j++) {
                if (s.charAt(i) == s.charAt(j))
                    //两边的字符相等的话，str[i...j] = str[i+1...j-1] + 2
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                else
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
        //str[0...len-1]的最长回文子序列的长度
        return dp[0][len - 1];
    }

    public int longestPalindromeSubseqRecursion(String s, int l, int r) {
        if (l == r) {
            return 1;
        }
        if (l > r) {
            return 0;
        }
        if (s.charAt(l) == s.charAt(r)) {
            return longestPalindromeSubseqRecursion(s, l + 1, r - 1) + 2;
        } else {
            return Math.max(longestPalindromeSubseqRecursion(s, l, r - 1), longestPalindromeSubseqRecursion(s, l + 1, r));
        }
    }
}
