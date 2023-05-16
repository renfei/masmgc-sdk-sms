# 中国移动云MAS业务平台SDK

[![Latest Stable Version](https://img.shields.io/maven-central/v/net.renfei/masmgc-sdk-sms.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/net.renfei/masmgc-sdk-sms)
[![Coverage Status](https://coveralls.io/repos/github/renfei/masmgc-sdk-sms/badge.svg?branch=master)](https://coveralls.io/github/renfei/masmgc-sdk-sms?branch=master)
[![codebeat badge](https://codebeat.co/badges/96f3b052-b686-4201-b36e-5931caa68462)](https://codebeat.co/projects/github-com-renfei-masmgc-sdk-sms-master)

中国移动云MAS业务平台SDK精简版，我在使用官方提供的SDK包时，发现官方的SDK功能很多，也造成很臃肿，引入了很多第三方依赖，
甚至造成与我本身项目相互冲突，而我只用到发短信的功能而已，并不会查询上行短信的收件箱，所以我进行了精简阉割。

本仓库***非官方仓库***，中国移动官方文档与代码参见：

* [中国移动云MAS业务平台](https://mas.10086.cn)
* [中国移动云MAS平台用户操作手册](https://mas.10086.cn/login/down/uploadFile.ajax?fileName=1)
* [中国移动云MAS平台短信SDK接口文档](https://mas.10086.cn/login/down/uploadFile.ajax?fileName=3)
* [中国移动云MAS平台SDK接口案例](https://mas.10086.cn/login/down/uploadFile.ajax?fileName=4)

代码仓库镜像：

* [https://github.com/renfei/masmgc-sdk-sms](https://github.com/renfei/masmgc-sdk-sms)
* [https://gitee.com/rnf/masmgc-sdk-sms](https://gitee.com/rnf/masmgc-sdk-sms)
* [https://gitlab.com/renfei/masmgc-sdk-sms](https://gitlab.com/renfei/masmgc-sdk-sms)

## 说明

我对官方的包进行了阉割精简，只保留发送短信的接口，因为我不需要查询上行短信，其他功能请使用官方的包。

短信的发送已经使用了线程池异步执行，外部调用的时候无需再套线程池了。

代码是反编译 Class 文件得来的，```com.ejtone.mars```开头的包名，我也不知道是干啥的，可能是私有库？

## 安装

最新版本号可在中央仓库中查询: [https://central.sonatype.com/artifact/net.renfei/masmgc-sdk-sms](https://central.sonatype.com/artifact/net.renfei/masmgc-sdk-sms)

### Maven安装

```xml
<dependency>
    <groupId>net.renfei</groupId>
    <artifactId>masmgc-sdk-sms</artifactId>
    <version>1.0.3.0</version>
</dependency>
```

### Gradle安装

```gradle
dependencies{
	implementation group: 'net.renfei', name: 'masmgc-sdk-sms', version: '1.0.3.0'
}
```

### 手动安装

如果您不使用任何版本依赖管理软件，您希望手动下载```jar```包放入```lib```文件夹，您可以到以下位置下载，我们目前只提供```Java1.8```的编译版本：

* Maven 中央仓库：[https://central.sonatype.com/artifact/net.renfei/masmgc-sdk-sms](https://central.sonatype.com/artifact/net.renfei/masmgc-sdk-sms)
* GitHub 包制品：[https://github.com/renfei/masmgc-sdk-sms/packages/](https://github.com/renfei/masmgc-sdk-sms/packages/)
* GitLab 包制品：[https://gitlab.com/renfei/masmgc-sdk-sms/-/packages](https://gitlab.com/renfei/masmgc-sdk-sms/-/packages)

## 使用

### 实例化客户端

SDK客户端的构造方式,实例化客户端Client类后即可通过该实例调用短信发送等函数。

```java
Client client=Client.getInstance();
```

### 身份验证

企业必须通过身份验证才能进行短信发送,身份验证只需验证一次即可。

#### 函数声明

```java
public boolean login(String url,String userAccount,String password,String ecname)
```

#### 参数详解

| 参数名称        | 说明                              |
|-------------|---------------------------------|
| url         | 身份认证地址，请向中国移动集团获得短信发送平台数据URL信息。 |
| userAccount | 用户登录帐号                          |
| password    | 用户登录密码                          |
| ecname      | 用户企业名称                          |

#### 代码演示

```java
Boolean loginresult=client.login("http://www.chinamobile.com","chinamobile","chinamobilepassword","chinamobilename");
```

### 短信发送

短信发送函数,调用该函数会即时的下发短信，支持单发和群发。目前普通短信建议使用该函数进行下发。

#### 函数声明

```java
public int sendDSMS(String[]mobiles,String smsContent,String addSerial,int smsPriority,String sign,String msgGroup,boolean isMo)
```

#### 参数详解

| 参数名称        | 说明                                                                                                                               |
|-------------|----------------------------------------------------------------------------------------------------------------------------------|
| mobiles     | 手机号码数组，允许群发信息，该字符串数组中的每个字符串代表一个手机号码。群发短信单批最大号码数组为每批5000条。                                                                        |
| smsContent  | 发送短信内容                                                                                                                           |
| addSerial   | 扩展码，根据向移动公司申请的通道填写，如果申请的精确匹配通道，则填写空字符串("")，否则添加移动公司允许的扩展码                                                                        |
| smsPriority | 短信优先级，取值1-5，填其余值，系统默认选择1, 1最低，5最高                                                                                                |
| Sign        | 网关签名编码，必填，签名编码在中国移动集团开通帐号后分配，可以在云MAS网页端管理子系统-SMS接口管理功能中下载。                                                                       |
| msgGroup    | 发送数据批次号，32位世界上唯一编码，由字母和数字组成。用户可以采用自定义的数据批次产生算法，标定每次下发的数据的批号。 如果不填写该参数，SDK为满足发送服务器的管理需要，会自动生成一个批次号，但是客户端取状态报告时无法分辨短信的状态报告批次。 建议填写 |
| IsMo        | 是否需要上行，True代表需要；false代表不需要。目前云MAS平台默认推送上行                                                                                        |

#### 返回值

| 返回值 | 描述                                   |
|-----|--------------------------------------|
| 1   | 短信发送成功                               |
| 101 | 短信内容为空                               |
| 102 | 号码数组为空                               |
| 103 | 号码数组为空数组                             |
| 104 | 批次短信的号码中存在非法号码， SDK带有号码的验证处理。        |
| 105 | 未进行身份认证或认证失败，用户请确认输入的用户名，密码和企业名是否正确。 |
| 106 | 网关签名为空， 用户需要填写网关签名编号                 |
| 107 | 其它错误                                 |
| 108 | JMS异常， 需要联系移动集团维护人员                  |
| 109 | 批次短信号码中存在重复号码                        |

#### 代码演示

```java
int sendResult=client.sendDSMS("{'13871156000','13667263322'}","测试结果","123","1"，"NPHB12","2004563256421",true);
```

### 发送模板短信

模板短信发送，根据指定模板ID，传入模板所需参数发送短信，一次性短信必须该函数下发数据。

#### 函数声明

```java
public int sendTSMS(String[]mobiles,String tempID,String[]params,String addSerial,int smsPriority,String sign,String msgGroup)
```

#### 参数详解

| 参数名称        | 说明                                                                                                                               |
|-------------|----------------------------------------------------------------------------------------------------------------------------------|
| mobiles     | 手机号码数组，允许群发信息，该字符串数组中的每个字符串代表一个手机号码。群发短信单批最大号码数组为每批5000条。                                                                        |
| tempId      | 模版ID，模板由用户在中国移动集团提供的客户业务平台上，由客户自己增加短信模版的信息。                                                                                      |
| params      | 模版参数，字符串数组。 模板采用模板和参数合成的方式产生短信， 短信内容由发送服务器自动拼接。模板参数必须和模板中定义的动态填写的参数的个数一致。                                                        |
| addSerial   | 扩展码，根据向移动公司申请的通道填写，如果申请的精确匹配通道，则填写空字符串("")，否则添加移动公司允许的扩展码                                                                        |
| smsPriority | 短信优先级，取值1-5，填其余值，系统默认选择1 优先级1为最低，5为最高                                                                                            |
| sign        | 网关签名编码，必填，签名编码由企业在中国移动集团开通帐号分配                                                                                                   |
| msgGroup    | 发送数据批次号，32位世界上唯一编码，由字母和数字组成。用户可以采用自定义的数据批次产生算法，标定每次下发的数据的批号。 如果不填写该参数，SDK为满足发送服务器的管理需要，会自动生成一个批次号，但是客户端取状态报告时无法分辨短信的状态报告批次。 建议填写 |

#### 返回值

| 返回值 | 描述                                   |
|-----|--------------------------------------|
| 1   | 短信发送成功                               |
| 101 | 短信内容为空                               |
| 102 | 号码数组为空                               |
| 103 | 号码数组为空数组                             |
| 104 | 批次短信的号码中存在非法号码， SDK带有号码的验证处理。        |
| 105 | 未进行身份认证或认证失败，用户请确认输入的用户名，密码和企业名是否正确。 |
| 106 | 网关签名为空， 用户需要填写网关签名编号                 |
| 107 | 其它错误                                 |
| 108 | JMS异常， 需要联系移动集团维护人员                  |
| 109 | 批次短信号码中存在重复号码                        |

#### 代码演示

```java
int sendResult=client.sendSTMS("{‘13871156000','13667263322'}”, ”861”, "{‘变量1','变量2'}”,"123”,  "1”，"NPHB12”, "2004563256421”);
```
