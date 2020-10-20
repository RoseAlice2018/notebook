## KMP算法

### 简介

- 今天某个学妹问了一道KMP算法匹配的题，我在博客里略找了一下）结果发现没有，然而我印象中好像明明写过，正好也记得不太清楚了，那就索性再写一遍吧。希望能比上次写的更好一点。
- 简单回顾一下，一般来讲KMP引入的题目：有一个主串S，和一个子串T，判定字串T是否在主串中出现，如果出现的话返回出现的下标，没有出现的话则返回-1.

字符串匹配算法本身是非常重要且运用广泛的，比如在一篇报道中，你希望很快的检索看是否有你自己的名字，一个表单，你想快速查看你想要知道的记录，这都需要高效的字符串匹配算法。

### 朴素的BF算法

我们先从朴素的BF（Brute-Force）算法讲起：

- 人如其名，BF是暴力算法，也是一开始最容易让人想到的算法：既然想要看主串里是否存在子串，只要进行逐字母的匹配即可，posS用以标记主串，posT用以标记字串，如果当前两串所指向的字母不相同，则将posS进行后移一位，再进行比对。
- 代码如下：

```
 //这里考虑的未必全面，只是简单的做一个描述，理解意思即可
 int PosS=0,PosT=0;

 	for(int i=0;i<T.size();i++)
 	{
 		if(S[PosS+i]!=T[i])
 		{
 			PosS++;//后退一位
 			i=-1;//从头再来
 		}
 		if(i==T.size()-1)
 		{
 			True;
 		}
 	}
 
 False;
```

- 但是显而易见，这样的效率实在是有些慢，时间复杂度达到了o（nm）。

### BF的改进思路

1. 我们比较两个字符串是否相等时只能采取逐字符比较的方法，每次比较m次，是不可以避免的。所以我们的优化方向在于减少比较的次数。
2. **算法优化要充分利用已知信息**。我们观察一次比较可以得到的信息：
   1. 第K+1个字符，两串不相等。
   2. 前k个字符串，两串相等。
3. 我们利用了第一个信息，但对第二个信息利用的却不充分。
4. 第二个信息体现了，主串的某一个字串等于模式串的某一个前缀。
5. **充分利用第二个信息，跳过不可能成功的字符串匹配** 。

#### 引入Next数组

![](C:\Users\15052\Desktop\博客\KMP\next数组.png)

![next2](C:\Users\15052\Desktop\博客\KMP\next2.png)

1. **显而易见，移动后的前缀要与移动前的后缀相等**。
2. next数组就是为此而设置（有关next数组的信息，再次不多赘述）。
3. 因此：KMP算法分为两步，一是求出Next数组，二是利用Next数组求解。

### KMP代码如下（C++）

```
#include<iostream>
#include<vector>
using namespace std;
//next 数组生成
vector<int> next(string t)
{
    vector<int> next(t.size());
    next[0]=-1;
    next[1]= 0;
    int j=2;
    while(j<t.size())
    {
        if(next[j-1]!=0&&t[next[j-1]]==t[j-1])
        {
            next[j]=next[j-1]+1;
        }
        else if(t[0]==t[j-1])
        {
            next[j]=1;
        }
        else
        {
            next[j]=0;
        }
        j++;
    }
    return next;
}
int KMP(string s,string t,vector<int>& next_)
{
    //get next
    next_=next(t);
    int index=0;
    int s_size=s.size();
    int t_size=t.size();
    if(s_size<t_size)
    {
        //  此时主串小于子串 不可能出现匹配
        return -1;
    }
    int i=0,j=0;
    while (i<s_size&&j<t_size)
    {
        if(j==-1||s[i]==t[j])
        {
            i++;
            j++;
        }
        else
        {
            j=next_[j];
        }
    }
    if(j>=t_size)
    {
        index=i-j;
        return index;
    }
    else
    {
        return -1;
    }
    
}
int main()
{
    string s,t;
    cin>>s>>t;
    vector<int> next;
    int res=KMP(s,t,next);
    for(int i=0;i<t.size();i++)
        cout<<next[i]<<" ";
    cout<<endl;
    cout<<res;
}

```

