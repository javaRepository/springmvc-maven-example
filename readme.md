#springmvc-maven-example------java示例项目
## 项目技术
     基于wap版Palmchat利用SpringMVC Maven SpringJDBC mysql
     spring-data-redis redis集群 freemarker log4j elk日志收集 jetty 测试用例等技术开发的示例项目

## 项目功能
- 登录注册等简单freemarker页面 和 返回json内容的接口
- 分别对页面和接口的异常处理，返回对应错误页面和错误json数据
- 文件的上传
- 数据保存和查询 在mysql和redis
- spring定时任务 spring aop权限拦截 spring拦截器
- 生成日志文件方便统计 日志收集

## maven运行使用介绍
```
clean compile com.jtool:codegen-builder-plugin:build -X #生成接口的使用文档
clean jetty:run  #jetty运行项目
clean package -Pafrika -Dmaven.test.skip=true #打包，把env目录下的afrika环境打war包
clean package -Psingapore -Dmaven.test.skip=true #-Dmaven.test.skip=true打包时跳过测试用例
```
