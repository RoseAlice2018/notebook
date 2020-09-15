## leetcode 5 最长回文子串

- 题目

  ```
  给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
  
  示例 1：
  
  输入: "babad"
  输出: "bab"
  注意: "aba" 也是一个有效答案。
  示例 2：
  
  输入: "cbbd"
  输出: "bb"
  ```

- 思路1： 动态规划

  - 假设字符串大小大于2，对于一个子串而言，如果它是回文串，则如果i-1，j+1相等，【i-1，j+1】就会是回文字符串，否则不是。

  - 我们可以得到状态转移方程p(i,j)=p(i+1,j-1)^(si==sj)

  - p(i,j)表示第i到j个字母组成的串是否为回文串 是为true 否则为false

  - 考虑边界情况 字符串只有一个字母 则一定是回文串，如果是两个字符，则S [i]==S[i+1]为回文，否则为false。

  - 综上可以写出代码

  - ```
    class Solution {
    public:
        string longestPalindrome(string s) {
            int size=s.size();
            string res;
            vector<vector<int>> dp(n,vector<int>(n));
            for(int l=0;l<n;l++)
            {
            	for(int i=0;i+l<n;i++)
            	{
            		int j=i+l;
            		if(l==0)// 第一种边界
            		{
            			dp[i][j]=1;
            		}else if(l==1)//第二种边界
            		{
            			dp[i][j]=(s[i]==s[j]);
            		}else{
            			dp[i][j]=(dp[i+1][j-1]&&(s[i]==s[j]));
            		}
            		if(dp[i][j]&&l+1>res.size())//l+1==j-i+1
            		{
            			res=s.substr(i,l+1);
            		}
            		
            	}
            }
            return res;
        }
    };
    ```

- 思路一.2：中心扩展算法

  - 仔细观察方法一中的状态转移方程：找出其中的状态转移链：p(i,j)<-p(i+1,j-1)<-p(i+2,j-2)<----某一边界情况，可以发现，所有的状态在转移的时候的可能性都是唯一的。

  - 我们通过每一个边界情况，进行扩展，直到无法扩展为止。此时回文串的长度就为此回文中心下的最长回文串长度。

  - ```
    class Solution {
    public:
    	pair<int,int> expandAroundCenter(const string& s,int left,int right)
    	{
    		while(left>=0&&right<s.size()&&s[left]==s[right])
    		{
    			--left;
    			++right;
    		}
    		return {left+1,right-1};
    	}
        string longestPalindrome(string s) {
           int start=0,end=0;
           for(int i=0;i<s.size();i++)
           {
           		auto [left1,right1]=expandAroundCenter(s,i,i);
           		auto [left2,right2]=expandAroundCenter(s,i,i+1);
           		if(right1-left1>end-start)
           		{
           			start=left1;
           			end=right1;
           		}
           		if(right2-left2>end-start)
           		{
           			start=left2;
           			end=right2;
           		}
           }
           return s.substr(start,end-start+1);
        }
    };
    ```

- Manacher 算法

