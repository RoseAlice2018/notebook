## Leetcode 88 合并两个有序数组

- 题目：给你两个有序整数数组 *nums1* 和 *nums2*，请你将 *nums2* 合并到 *nums1* 中*，*使 *nums1* 成为一个有序数组。

- 说明：

  - 初始化 *nums1* 和 *nums2* 的元素数量分别为 *m* 和 *n* 。
  - 你可以假设 *nums1* 有足够的空间（空间大小大于或等于 *m + n*）来保存 *nums2* 中的元素。

- 示例：

  ```
  输入:
  nums1 = [1,2,3,0,0,0], m = 3
  nums2 = [2,5,6],       n = 3
  
  输出: [1,2,2,3,5,6]
  ```

- 思路一：暴力解法

- 合并和排序

- ```
  class Solution {
  public:
      void merge(vector<int>& nums1, int m, vector<int>& nums2, int n)
       {
          for(int i=0;i<n;i++)
          {
              nums1[m+i]=nums2[i];
          }
          sort(nums1.begin(),nums1.end());
      }
  };
  ```

- 思路二：双指针

- 一般而言，对于两个有序数组的排序而言，都可以考虑双指针技巧

- 利用了o（m）的额外空间

- ```
  class Solution {
  public:
      void merge(vector<int>& nums1, int m, vector<int>& nums2, int n)
       {
           vector<int> numscopy(nums1);
           int left=0;
           int right=0;
           int count=0;
           while(left<m&&right<n)
           {
               if(numscopy[left]>=nums2[right])
               {
                   nums1[count++]=nums2[right++];
               }
               else
               {
                   nums1[count++]=numscopy[left++];
               }
           }
           while(left<m)
           {
               nums1[count++]=numscopy[left++];
           }
           while(right<n)
           {
               nums1[count++]=nums2[right++];
           }
      }
  };
  ```

- 