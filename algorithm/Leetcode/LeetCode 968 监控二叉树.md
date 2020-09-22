## LeetCode 968 监控二叉树

- 题目：给定一个二叉树，我们在树的节点上安装摄像头。节点上的每个摄影头都可以监视**其父对象、自身及其直接子对象。**计算监控树的所有节点所需的最小摄像头数量。

- 示例：

  ```
  输入：[0,0,null,0,0]
  输出：1
  解释：如图所示，一台摄像头足以监控所有节点。
  
  输入：[0,0,null,0,null,0,null,null,0]
  输出：2
  解释：需要至少两个摄像头来监视树的所有节点。 上图显示了摄像头放置的有效位置之一。
  ```

- 分析：

  - 假设当前结点为root，左右孩子结点为left，right，根据题意有以下几种情况
    - root放置了结点，此时root监控了left，right。
    - root并未放置结点，left放置了结点，监控了父亲
    - root并未放置结点，right放置了结点，监控了父亲
    - root，left，right均未放置结点，root父亲监控了root
  - 由以上四种情况发现，发现需要维护以下几种状态
    - a：*root* 必须放置摄像头的情况下，覆盖整棵树需要的摄像头数目。
    - b：覆盖整棵树需要的摄像头数目，无论 root* 是否放置摄像头。
    - c：覆盖两棵子树需要的摄像头数目，无论节点 *root* 本身是否被监控到。
  - a=lc+rc+1
  - *b*=min(*a*,min(*la*+*rb*,*ra*+*lb*))
  - 对于 c 而言，要保证两棵子树被完全覆盖，要么root 处放置一个摄像头，需要的摄像头数目为 a；要么 root 处不放置摄像头，此时两棵子树分别保证自己被覆盖，需要的摄像头数目为 lb +rb 。

- 代码：

- ```
  struct Status {
      int a, b, c;
  };
  
  class Solution {
  public:
      Status dfs(TreeNode* root) {
          if (!root) {
              return {INT_MAX / 2, 0, 0};
          }
          auto [la, lb, lc] = dfs(root->left);
          auto [ra, rb, rc] = dfs(root->right);
          int a = lc + rc + 1;
          int b = min(a, min(la + rb, ra + lb));
          int c = min(a, lb + rb);
          return {a, b, c};
      }
  
      int minCameraCover(TreeNode* root) {
          auto [a, b, c] = dfs(root);
          return b;
      }
  };
  ```

  

