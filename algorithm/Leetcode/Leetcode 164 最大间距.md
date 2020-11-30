## Leetcode 164 最大间距

- 题目：https://leetcode-cn.com/problems/maximum-gap/

- 解法一：基于基数排序

  ```
  class Solution {
  public:
      int maximumGap(vector<int>& nums) {
          int n = nums.size();
          if (n < 2) {
              return 0;
          }
          //基数排序
          int exp = 1;
          vector<int> buf(n);
          int maxVal = *max_element(nums.begin(), nums.end());
  
          while (maxVal >= exp) {
              vector<int> cnt(10);
              for (int i = 0; i < n; i++) {
                  int digit = (nums[i] / exp) % 10;
                  cnt[digit]++;
              }
              for (int i = 1; i < 10; i++) {
                  cnt[i] += cnt[i - 1];
              }
              for (int i = n - 1; i >= 0; i--) {
                  int digit = (nums[i] / exp) % 10;
                  buf[cnt[digit] - 1] = nums[i];
                  cnt[digit]--;
              }
              copy(buf.begin(), buf.end(), nums.begin());
              exp *= 10;
          }
  		//寻找最大间隔值
          int ret = 0;
          for (int i = 1; i < n; i++) {
              ret = max(ret, nums[i] - nums[i - 1]);
          }
          return ret;
      }
  };
  
  作者：LeetCode-Solution
  链接：https://leetcode-cn.com/problems/maximum-gap/solution/zui-da-jian-ju-by-leetcode-solution/
  来源：力扣（LeetCode）
  著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
  ```

  

- 解法二：基于桶的算法

  ```
  class Solution {
  public:
      int maximumGap(vector<int>& nums) {
          int n = nums.size();
          if (n < 2) {
              return 0;
          }
          int minVal = *min_element(nums.begin(), nums.end());
          int maxVal = *max_element(nums.begin(), nums.end());
          int d = max(1, (maxVal - minVal) / (n - 1));
          int bucketSize = (maxVal - minVal) / d + 1;
  
          vector<pair<int, int>> bucket(bucketSize, {-1, -1});  // 存储 (桶内最小值，桶内最大值) 对，(-1, -1) 表示该桶是空的
          for (int i = 0; i < n; i++) {
              int idx = (nums[i] - minVal) / d;
              if (bucket[idx].first == -1) {
                  bucket[idx].first = bucket[idx].second = nums[i];
              } else {
                  bucket[idx].first = min(bucket[idx].first, nums[i]);
                  bucket[idx].second = max(bucket[idx].second, nums[i]);
              }
          }
  
          int ret = 0;
          int prev = -1;
          for (int i = 0; i < bucketSize; i++) {
              if (bucket[i].first == -1) continue;
              if (prev != -1) {
                  ret = max(ret, bucket[i].first - bucket[prev].second);
              }
              prev = i;
          }
          return ret;
      }
  };
  
  作者：LeetCode-Solution
  链接：https://leetcode-cn.com/problems/maximum-gap/solution/zui-da-jian-ju-by-leetcode-solution/
  来源：力扣（LeetCode）
  著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
  ```

  

