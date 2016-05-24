###Clairvoyant
	Clairvoyant意愈为千里眼，寄望其能成为一个优秀的校验框架。
	
	本框架旨在利用注解对Android开发中的输入校验进行简化，框架处于研究期，不是十分完善，慎用。

[TOC]

###使用步骤

####1、导入Jar包

####2、实例化Clairvoyant对象
```java
public Clairvoyant(Context context, ClairvoyantListener listener)
//clairvoyant = new Clairvoyant(this, this);
```
第一个参数传入`EditText`所在的上下文，第二个对象传入校验出错的监听器，监听器的定义如下

```java
public interface ClairvoyantListener {

    public void inputError(String msg);

}
```

####3、对EditText进行注解
```Java
NotNull(msg = "请输入密码",seq=1,group={0,1})
EditText editPassword;
```

####4、触发校验
```java
clairvoyant.look(); //默认分组
```

###常用注解
####非空`@NotNull`

	大部分的输入判断输入是否为空，进而进行空输入的提示，而非空注解正好解决了这一冗余的过程。
	
```Java
@NotNull(msg = "请输入密码")
EditText editPassword;
```

当`editPassword`没有输入任何内容时，会抛出`ClairvoyantException`

####手机号码 `@Mobile`
```java
@Mobile
EditText editMobile;
```

####正则表达式 `@Pattern`

```java
@Pattern(msg = "请填写验证码", seq = 4, pattern = "[1-9]{4}")
@Bind(R.id.edit_verify)
EditText editVerify;
```

####关于顺序 `seq`
所有注解都可以指定顺序，从大到小排列，`seq`值大的校验有限进行，如下
```java
@NotNull(msg = "请输入密码", seq = 6)
EditText editPassword;
```

####关于分组 `group`
很多时候，同个界面内，校验是需要分组的，如注册页面存在两种触发校验的情况，如点击`获取验证码`按钮、点击`注册`按钮,

`获取验证码`按钮 -》 校验手机号码

`注册`按钮-》校验手机号码、密码、短信验证码等

所以部分情况下，我们需要提供对分组的支持，于是就有了`group`字段，且同个`EditText`可以归入多个分组，使用方式如下：
```java
@Mobile(seq = 5, group = {0, 1})
EditText editMobile;

@NotNull(msg = "请输入密码", seq = 6, group = {0})
EditText editPassword;
```
添加分组后，当触发
```java
clairvoyant.look(0);
```
的时候，会对手机号码、密码都做校验，而触发
```java
clairvoyant.look(1);
```
的时候，只会对手机号码做校验
