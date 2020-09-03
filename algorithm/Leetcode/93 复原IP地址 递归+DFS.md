##  93 复原IP地址 递归+DFS
**题目** ：给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。有效的 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。例如："0.1.2.201" 和 "192.168.1.1" 是 有效的 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效的 IP 地址。

** 思路 **：由于我们需要找出所有可能复原出的 IP 地址，因此可以考虑使用递归的方法，对所有可能的字符串分隔方式进行搜索，并筛选出满足要求的作为答案。

设题目中给出的字符串为 ss。我们用递归函数 dfs (segId,segStart)表示我们正在从 s[segStart] 的位置开始，搜索 IP 地址中的第 segId 段，其中segId∈{0,1,2,3}。由于 IP 地址的每一段必须是 [0, 255][0,255] 中的整数，因此我们从 segStart 开始，从小到大依次枚举当前这一段 IP 地址的结束位置 segEnd。如果满足要求，就递归地进行下一段搜索，调用递归函数 dfs(segId+1,segEnd+1)。

特别地，由于 IP 地址的每一段不能有前导零，因此如果s[segStart] 等于字符 00，那么 IP 地址的第segId 段只能为 00，需要作为特殊情况进行考虑。

在递归搜索的过程中，如果我们已经得到了全部的 4 段 IP 地址（即 segId=4），并且遍历完了整个字符串（即 segStart=∣s∣，其中 ∣s∣ 表示字符串 s 的长度），那么就复原出了一种满足题目要求的 IP 地址，我们将其加入答案。在其它的时刻，如果提前遍历完了整个字符串，那么我们需要结束搜索，回溯到上一步。

```
class Solution {
private:
    static constexpr int SEG_COUNT = 4;

private:
    vector<string> ans;
    vector<int> segments;

public:
    void dfs(const string& s, int segId, int segStart) {
        // 如果找到了 4 段 IP 地址并且遍历完了字符串，那么就是一种答案
        if (segId == SEG_COUNT) {
            if (segStart == s.size()) {
                string ipAddr;
                for (int i = 0; i < SEG_COUNT; ++i) {
                    ipAddr += to_string(segments[i]);
                    if (i != SEG_COUNT - 1) {
                        ipAddr += ".";
                    }
                }
                ans.push_back(move(ipAddr));
            }
            return;
        }

        // 如果还没有找到 4 段 IP 地址就已经遍历完了字符串，那么提前回溯
        if (segStart == s.size()) {
            return;
        }

        // 由于不能有前导零，如果当前数字为 0，那么这一段 IP 地址只能为 0
        if (s[segStart] == '0') {
            segments[segId] = 0;
            dfs(s, segId + 1, segStart + 1);
        }

        // 一般情况，枚举每一种可能性并递归
        int addr = 0;
        for (int segEnd = segStart; segEnd < s.size(); ++segEnd) {
            addr = addr * 10 + (s[segEnd] - '0');
            if (addr > 0 && addr <= 0xFF) {
                segments[segId] = addr;
                dfs(s, segId + 1, segEnd + 1);
            } else {
                break;
            }
        }
    }

    vector<string> restoreIpAddresses(string s) {
        segments.resize(SEG_COUNT);
        dfs(s, 0, 0);
        return ans;
    }
};


```

