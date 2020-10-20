---
layout: post_layout
title:  "开放安全认证体系－kerberos"
date:   2015-01-10 11:00:03
categories: 安全认证
published: true
type: others
excerpt_separator: "###"
---



### 认证的基本原理

<div class="mermaid">
sequenceDiagram
participant Client
participant Server
Client->>Server:request
Note over Client,Server: (1) Client Identity <br/> (2) Client Identity [Encrypted with KServer-Client]
</div>
客户端请求服务端认证自己(确认自己的身份),客户将自己的用户信息以及使用自己的私钥加密的密文字符串发送给服务端，服务端持有每一个用户的私钥，收到客户端的信息后使用对称加密算法解密客户端发送的第二部分信息，解密出客户端的用户信息对比收到的用户信息，如果一致那么这个客户端就认证成功

### 引入Key Distribution: KServer-Client从何而来

秘钥在网络中传输的原则  

* 永久的密钥(Long-term Key)不能再网络中长久的传输(对于上面的例子KServer-Client不应该是用户的密码)  

* 使用具有有效时间的密钥(Short-term Key)来加密传输在网络中传输的数据 	

为了生成这个Short-term Key 引入一个叫做 Key Distribution Center 的东东(KDC)来生成Short-term key 这里重新取名叫做 SServer-Client. 注意到现在这个地方一共有三个角色 KDC、Client、Server KDC维护这Client 和Server的Long-term Key.

<div class="mermaid">
sequenceDiagram
Client->>KDC: request
Note over Client,KDC: I want get access to server
KDC->>Client: response
Note over Client,KDC: SServer-Client[Encrypted with Client master key] <br/> SServer-Client+Client Info[Encrypted with Server master Key]
</div>
KDC的相应包括两部分:使用Client的Master Key（即用户的密码）加密的SServer-Client和使用Server的Master Key 加密的SServer-Client以及用户信息。(SServer-Client即是Client和Server之间具有一定有效时间的Short-term key，也可以叫做Client和Server之间的会话密钥)
拿到了会话密钥之后Client和Server之间就可以使用对称加密算法进行互相的身份认证了。

### Server认证Client

<div class="mermaid">
sequenceDiagram
Client->>Server: request
Note over Client,Server: Session Ticket and Authenticator
Server->>Client: response
Note over Client,Server: Timestamp[Encrypted with SServer-Client]
</div>

Session Ticket 就是上面的SServer-Client+Client Info[Encrypted with Server master Key]，下面说一下Authenticator的生成，上面的步骤中client从KDC获取到SServer-Client[Encrypted with Client master key] 然后使用自己的master key 解密从而获取到SServer-Client 然后使用SServer-Client 去加密Client Info + Timestamp来得到 Authenticator，Server收到了 Session Ticket and Authenticator 后使用自己的 master key 解密Session Ticket 获取到 SServer-Client，然后使用获取到的SServer-Client 来解密 Authenticator，将Session Ticket中的 Client Info 和 Authenticator里面的 Client Info 对比来达到认证的作用。

Q:如果client 的Session Ticket 和Authentiator被人恶意截取了怎么办？ 

A:注意上面的Authenticator里面有一个Client的Timestamp，Server会通过如下的步骤判断这个请求是不是合法的
 - 将Authenticator中的Timestamp同自己的时间比较如果超出了一个可接受的时间范围则认为这不是一个合法的请求(会存在Client和Server的时钟不同步的问题)
 - Server记录每一个client上次的合法请求的timestamp，只有当前的Authentiator中的Timestamp大于Server记录的Timestamp才继续进行server的下面的认证

同时Server可以使用返回Timestamp[Encrypted with SServer-Client] 来达到Client认证Server的目的


### KDC 认证 Client

在上面的Server 认证Client的过程中我们并没有看到Client向提供自己的Master Key(密码)，而是使用的KDC分配的一个Short-term Key ，显然这样对Client来说是安全的，那么KDC也不会平白无故的就给Client提供key,Client需要session key那么就需要KDC认证自己
在这里我们把能获得上面步骤的Session Ticket 的客户端凭证叫做 “Ticket Granting Ticket” （简称TGT）
<div class="mermaid">
sequenceDiagram
Client->>KDC: request
Note over Client,KDC: client info[Encrypted with client Master key]
KDC->>Client: response 
Note over Client,KDC: SKDC-Client[Encrypted with client Master key] <br/> TGT(SKDC-Client+Client Info)[Encrypted with KDC Master key]
</div>

client info 一般是Client的用户名和客户端的Timestamp，同时将用户名明文传给KDC,KDC根据用户名在DB中查询用户的密码(一般是经过散列存储的)，然后根据查询的用户密码来解密client传递过来的加密字符串如果能够成功解密的话，则Client认证成功。
SKDC-Client 是client和KDC之间的Short-term key 
这个时候流程又来到这里
<div class="mermaid">
sequenceDiagram
Client->>KDC: request
Note over Client,KDC: Authenticator[Encrypted with SKDC-Client] <br/> TGT
KDC->>Client: response
Note over Client,KDC: SServer-Client[Encrypted with Client master key] <br/> SServer-Client+Client Info[Encrypted with Server master Key]
</div>

KDC通过解密TGT获取到SKDC-Client 然后使用SKDC-Client解密Authenticator ，比较前后的client info来判断认证是否通过。

Q:为什么获取SServer-Client需要两步？ 
 
A:SKDC-Client 是没有包含应用的信息的（应用信息放在Authenticator里面的）,故SKDC-Client可以在有效期内向SKDC请求多个Server的SServer-Client,而且SKDC-Client 是一个Short-term key.


### kerberos的工作过程

下面的交互图可以简单的描述kerberso系统的整个工作过程:

<div class="mermaid">
sequenceDiagram
client->>KDC:认证
KDC->>client: 认证通过返回(client-KDC)会话密钥
client->>KDC: 授权
KDC->>client:授权通过返回(client-server)会话密钥
client->>server:访问服务
</div>

解释一下上图中的名词：
 - client:发起用户认证的客户端(web 移动端)
 - server:提供提供服务的业务服务器
 - KDC:提供认证和授权的服务器(同时存储用户的秘钥，为接入的业务服务分配密钥等)

