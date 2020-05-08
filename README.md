# Apache Shiro 对于 Freemarker 自定义标签

[Apache Shiro](http://shiro.apache.org/) 自带了一些 [JSP标签](http://shiro.apache.org/jsp-tag-library.html)，用于做一些事情，比如只显示匿名用户、登录用户等内容。我正在使用 [Freemarker](http://freemarker.sourceforge.net/)，不想只为`Shiro`而依赖`JSP`，所以我重写了`Freemarker`的标签。

### 安装

* jar 方式引入

下载 [shiro-freemarker-tags.jar](https://github.com/xla145/shiro-freemarker-tags/blob/1.0.0/dist/shiro-freemarker-tags-0.1-SNAPSHOT.jar) 文件，并在项目中引入jar

![](https://github.com/xla145/shiro-freemarker-tags/blob/1.0.0/img/20200508165957.png)

* maven方式

  ```
  <dependency>
      <groupId>com.xula</groupId>
      <artifactId>shiro-freemarker-tags</artifactId>
      <version>1.0.1</version>
  </dependency>
  ```
  
  由于上传的是个人私服，使用者需要在`pom` 文件下添加以下配置	
  
  ```
  <repositories>
      <repository>
          <id>rdc-releases</id>
          <url>https://repo.rdc.aliyun.com/repository/120621-release-HldziT/</url>
          <name>rdc-releases</name>
      </repository>
  </repositories>
  ```

## 使用

声明一个名为 `shiro `的共享变量，并将其分配给`ShiroTags`类的一个实例。

    cfg.setSharedVariable("shiro", new ShiroTags());

在`Freemarker` 模板中使用标签

    <@shiro.guest>Hello guest!</@shiro.guest>

## 注意

如果在使用过程中出现 bug，请提交 issue
