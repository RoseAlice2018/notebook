##  剑指 Offer 03. 数组中重复的数字

- 题目：在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。

- 思路一：很好想。用一个集合来来记录已经出现过的数字，如果有数字第二次出现，直接返回即可。

- 代码：

  ```
  class Solution {
  public:
      int findRepeatNumber(vector<int>& nums) {
          unordered_set<int> dic;
          for(int i=0;i<nums.size();i++)
          {
              if(dic.count(nums[i]))
                  return nums[i];
              dic.insert(nums[i]);
          }
          return 0;
      }
  };
  ```

- 思路二：充分利用所有数字都在0~n-1的条件，如果数组中的数字没有重复，那么数字i应该是在下标i的位置，所以思路是重头扫描，**遇到下标为i的数字如果不是i的话，（假设为m),那么我们就拿与下标m的数字交换。在交换过程中，如果有重复的数字发生，那么终止返回**。

- 代码：

- ```
  class Solution {
  public:
      int findRepeatNumber(vector<int>& nums) {
          int temp;
          for(int i=0;i<nums.size();i++)
          {
              while(nums[i]!=i){
                  if(nums[i]==nums[nums[i]])
                  {
                      return nums[i];
                  }
                  temp=nums[i];
                  nums[i]=nums[temp];
                  nums[temp]=temp;
              }
          }
          return 0;
      }
  };
  ```

  

