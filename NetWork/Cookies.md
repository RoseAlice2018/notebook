## Cookies

### 什么是Cookies？

Cookies are small files which are stored on a user's computer. They are designed to hold a modest amount of data specific to a particular client and website, and can be accessed either by the web server or the client computer. This allows the server to deliver a page tailored to a particular user, or the page itself can contain some script which is aware of the data in the cookie and so is able to carry information from one visit to the website (or related site) to the next.

### 为什么用Cookies？

由于HTTP是无状态的协议，所以对于两个请求，它无法知道这是两个不同的主机发出的请求还是同一个主机发出的请求。这简化了服务器器的设计，但很多时候服务器希望能够识别用户。Cookies就是为了解决这一问题而提出的。

### Cookies实现过程？

#### Cookies样例

1. 客户端发送HTTP请求报文。
2. 服务端响应报文，在数据库中为用户创建ID，在返回的响应报文里插入Set-cookie。
3. 浏览器在管理的cookies文件中添加一行，该行包括服务器的主机名和Set-cookie。
4. 客户端再次发送HTTP请求报文，首部包含cookieID。
5. 服务端识别ID，记录活动。



