## leetcode22 括号生成

- 题目：

  - 数字 *n* 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 **有效的** 括号组合。

  - ```
    输入：n = 3
    输出：[
           "((()))",
           "(()())",
           "(())()",
           "()(())",
           "()()()"
         ]
    ```

- 思路一：暴力

- 生成所有的2^2n个‘ （ ’ 和 ')'字符构成的序列，然后我们检查每一个是否有效即可。

- 递归生成每一个序列，长度为n的序列就是在n-1后加一个‘（’或‘）’

- 检查每一个序列是否有效，设置一个变量balance，balance表示左括号减去右括号的数量。如果balance的值小于0，或者结束时balance的值不为0，那么该序列是无效的，否则有效。

- ```
  class solution{
  	bool valid(const string& str)
  	{
  		int balance=0;
  		for(char c:str)
  		{
  			if(c=='(')
  				++balance;
  			else{
  				--balance;
  			}
  			if(balance<0)
  				return false;
  		}
  		return balance==0;
  	}
  	void generate_all(string& current,int n,vector<string>& result )
  	{
  		if(n==current.size())
  		{
  			if(valid(current))
  				result.push_back(current);
  		}
  		current+='(';
  		generate_all(current,n,result);
  		current.pop_back();
  		current+=')';
  		generate_all(current,n,result);
  		current.pop_back();
  	}
  	public:
      vector<string> generateParenthesis(int n) {
          vector<string> result;
          string current;
          generate_all(current, n * 2, result);
          return result;
      }
  }
  ```

- 思路二：回溯法

- 改进：我们只在序列仍然保持有效时才添加‘（’ or ‘）’

- ```
  class Solution {
      void backtrack(vector<string>& ans, string& cur, int open, int close, int n) {
          if (cur.size() == n * 2) {
              ans.push_back(cur);
              return;
          }
          if (open < n) {
              cur.push_back('(');
              backtrack(ans, cur, open + 1, close, n);
              cur.pop_back();
          }
          if (close < open) {
              cur.push_back(')');
              backtrack(ans, cur, open, close + 1, n);
              cur.pop_back();
          }
      }
  public:
      vector<string> generateParenthesis(int n) {
          vector<string> result;
          string current;
          backtrack(result, current, 0, 0, n);
          return result;
      }
  };
  ```

  

