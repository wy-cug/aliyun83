# aliyun83
**阿里云云效83行代码比赛代码**

# 第二题

## 通关指引
### 赛题说明
阿里云发布的智能编码插件（Cosy）https://developer.aliyun.com/tool/cosy ，它为开发者提供了代码智能补全以及代码示例搜索，
其中代码示例搜索能够让开发者在遇到不熟悉的API时，帮助开发者快速找到对应的API代码示例，当开发者在Cosy中输入API关键词时，
Cosy会提供API关键词的自动补全提示。现在阿里云OSS中存储了多个几万到几百万大小不等的Java API数据文件，
请你设计一个数据结构及算法，能够根据Java API名称前缀快速检索出对应的Java API名称列表。
你可以在已有的代码框架中完成TODO部分，或修改代码框架。


#### 评分机制

评分系统会随机选择一个小规模数据集和一个大规模数据集，针对每个数据集对参赛者编写的函数从准确性、性能、内存消耗维度进行评估。
* **运行结果** ：评分系统会随机生成一批前缀字符串输入到参赛者的函数中，并评估参赛者的结果是否准确
* **性能开销** ：函数计算耗时越短，性能维度分数越高。（注意：小数据集需在2分钟内完成计算，大数据集需在3分钟内完成计算）
* **内存消耗** ：函数计算内存消耗越低，内存消耗维度分数越高。（注意：默认最大内存为3GB）

#### 注意事项

* 代码框架中的 `com.aliyun.code83.round2.CodePrefixSearchRequest` 类名以及 `handleRequest` 方法名不能修改，否则会导致评分失败
* 参赛者自己引入的maven包是无效的，`pom.xml` 文件无法被修改，评分系统会将参赛者的 `pom.xml` 文件替换为默认文件

#### 提示：

OSS数据访问请参照以下文档：
* OSSClient初始化帮助文档：https://help.aliyun.com/document_detail/32010.html
* OSS文件流式下载帮助文档：https://help.aliyun.com/document_detail/84823.html

算法实现：

* 评测数据集较大，请考虑比双层for循环更优的方法，否则很可能无法通过

### 数据格式示例

#### 1. 数据集格式
文件每一行记录一条API，API名称包含a-z、A-Z、0-9、下划线字符
```
BufferedReader
BufferedInputStream
String
StringBuilder
……
```
#### 2. 输入JSON数据格式
```
{
    "prefixs":["","", ……],  // 字符串前缀
    "oss_endpoint": "", // OSS访问endpoint,
    "oss_key": "",  // OSS数据集访问地址
    "bucket": "",  // OSS Bucket
    "access_key": "", // OSS 访问AK
    "access_secret": "" // OSS 访问SK
}
```
输入数据示例：
```
{
    "prefixs":[
        "Buffer",
        "Str"
    ],
    "oss_endpoint":"*****",
    "oss_key":"*****",
    "bucket":"*****",
    "access_key":"*****",
    "access_secret":"*****"
}
```
#### 3. 输出JSON数据格式
```
{ "<prefix1>": ["", ……], "<prefix2>": ["", "", ……], …… }
```
输出数据示例：
```
{
   "Buffer":["BufferedReader", "BufferedInputStream"]，
   "Str":["String", "StringBuilder"]，
}
```

### 调试

`src/test/resources/test_input.json` 文件中存储有测试数据，可通过以下方法调试：

打开 `src/test/java/com/aliyun/code83/round2/test/CodePrefixSearchRequestTest.java` 单元测试文件，
去除 `testHandleRequest` 方法上的 `@Ignore` 注解，然后点击"Debug Test"进行调试。

### 提交至评分系统


# 第三题
恭喜你来到第三关「重塑人生」！

你的任务：
找到埋藏的bug；控制中枢的重启需要你帮助完成贸易站的系统升级，让我们可以添加新的商品，以保证在灾害结束前供应给需要的人们。

简单介绍一下我们的系统：
* 商品（Item）都有一个销售剩余天数（SellIn），表示该商品必须在该值所代表的天数内销售出去。
* 所有商品都有一个Value值，代表商品的价值。
* 每过一天，所有商品的SellIn值和Value值都减1。
* 一旦过了销售剩余天数，价值就以双倍的速度下滑。
* 陈年老酒（Aged Wine）是一种特殊的商品，放得越久，价值反而越高。而且过了销售剩余天数后价值会双倍上涨。
* 商品的价值永远不会小于0，也永远不会超过50。
* 魔法锤（Sulfuras）是一种传奇商品，其销售剩余天数和品质值都不会变化。
* 演出票（Show Ticket）越接近演出日，价值反而上升。在演出前10天，价值每天上升2点；演出前5天，价值每天上升3点。但一旦过了演出日，价值就马上变成0。
* 最近因为灾害，我们采购了特效药（Cure）， 特效药的贬值速度是普通物品的两倍，这更加需要尽快升级我们的系统。


### 注意：
1. 后台会运行若干单测来验证程序的正确性，请不要修改提示了不允许修改的类或者方法的签名，以保证测试的正常运行。例如以下提示：     
// Please don't modify the signature of this method.   
// Please don't modify the class name.  

2. 如果程序存在编译错误，各项得分将直接判定为0分。

# 第四题
## 致命真相
当你推开第四扇门，眼前一片灰蒙蒙的。不远处有三个光团，你的直觉告诉你，这三个光团与你密切相关。但此刻，它们被灰雾重重包裹着，完全无法触达。

"这里面有你想要知道的一切，但揭开它之前，还有最后一层防护措施，这对于你一个优秀的程序猿来说，应该不在话下。“

### 赛题说明
这是一个服务器软件，基于 Spring WebFlux 实现，通过 HTTP 建立 WebSocket 连接之后进行数据传输，使用 Spring Security 进行身份验证。

**你的任务：**
获取光团数据异常，请快速找到问题并修复他们。
记住，你只有4个小时的时间。4小时后，空间将会崩塌。

**Tips:**
快快打开你的背包，那就是引领你通关的秘籍(°▽°)/ ——来自万圣节鬼鬼的提醒

#### 提示
##### 1. 你可以从单测开始
我们已经准备好了单测小糖果，先 run 一下吧。
你可以打开 terminal 执行 `mvn test` 或在目录树直接 `Run Tests in 'com.aliyun.code83.round4'`。

**注意:**
不要更改故障检测用例和数据，不要试图绕过故障检测工具。

##### 2. 启动 Server（服务器软件） 和 Client（光团存储介质）
服务器软件中存在多个 bug，导致无法读取或读取内容出错，尝试修复 bug 读取光团内容。

- 打开 Round4Application.java 文件，点击 `Run Round4Application.main()` 启动服务。
- 打开 terminal 执行 `./round4` 启动存储介质，观察输出逐一修复 bug

**注意:**
为防止真相泄漏，光团存储介质探测到任何干扰读取数据的行为，都会启动自毁程序！
不信？！那你试试 debug (´ｰ∀ｰ`) —— by 万圣节鬼鬼

##### 3. 撞墙推墙的推荐
程序猿和程序媛们，抓耳挠腮了有没有！来来来，看神器来了๑乛◡乛๑
光团存储介质原厂家「阿里云-代码平台」官方提供应用观测器，在不干扰光团存储介质的情况下就可观测通信过程。

【应用观测器】使用方法：
-   打开 Round4Application.java 文件，点击 `Edit Round4Application.main()` 编辑配置。
-   在配置页，设置VM options为`-agentpath:/tmp/cdbg_java_agent.so`（请勿修改或移动文件）
-   点击 `Run Round4Application.main()` 启动服务。
-   在编辑器中想要调试的位置，右键选择`应用观测器-添加日志点`或者`应用观测器-添加快照(虚拟断点)`等方式进行调试，其中快照（虚拟断点）会在设置位置触发时，返回触发时的变量和调用堆栈情况，但并不会阻塞进程运行。
-   更详细的说明，可以参考`https://thoughts.aliyun.com/share/617e1a5b727f03001a11a099#617e185d6ae8e92c050ba88b`
-   **注意:**
目前应用观测器和debug模式不能同时运行，使用应用观测器时请勿通过`Debug Round4Application.main()`启动服务

##### 4. 提交打分

提交打分过程如下：
- 打开 Round4Application.java 文件，点击 `Run Round4Application.main()` 启动服务。
- 打开 Terminal 执行 `./round4 --submit` 提交打分，过程预计 10s

如果你回答正确所有答案，并且总得分达到60分，那么你将揭开光团，面对最后这致命的真相。其中答案占10分，修复问题占90分。

2021年10月31日18点之后，空间将要崩塌，灰雾也将散去，再次提交即可揭开光团。


**注意:**
18点比赛结束，真相结局水晶开放给所有选手，除在 WebIDE 内，也可在18点后刷新官网页面领取真相。真相不计入线索。

如有问题，请加钉钉群：34336891
