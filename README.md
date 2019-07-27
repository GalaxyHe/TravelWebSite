# TravelWebSite

基于HTML(Ajax、JQuery)+JDBC Template+Servlet

项目缘由：之前一直在使用SSM框架，包括学期末两次课程设计，都是使用的SSM框架完成的。想着假期做个原生mvc模式的项目。

## 项目架构：

采用通用的面向对象三层设计架构，**表现层** 、**业务层** 、**持久层**。

- 表现层：采用MVC模式
  1. 视图：使用HTML，配合Ajax以及JQuery。由于该项目是本着开放给互联网用户的目的，所以并没有使用jsp作为表现层。选择通用的html作为视图展示。
  2. 控制：使用原生的servlet作为控制器
  3. 模型：基于数据库表结构设计的实体类
- 业务层：一个实体类一个接口，一次提交一个业务方法。业务方法的参数来自表现层。
- 持久层：使用到Redis数据库以及MySQL。操作数据库，执行CRUD的任务交给了spring框架提供的JDBC Template

## 项目截图：

![image](https://github.com/GalaxyHe/TravelWebSite/blob/master/images/1564225380798.png)

![image](https://github.com/GalaxyHe/TravelWebSite/blob/master/images/1564225423579.png)

![image](https://github.com/GalaxyHe/TravelWebSite/blob/master/images/1564225454912.png)

![image](https://github.com/GalaxyHe/TravelWebSite/blob/master/images/1564225493895.png)

![image](https://github.com/GalaxyHe/TravelWebSite/blob/master/images/1564225517595.png)

![image](https://github.com/GalaxyHe/TravelWebSite/blob/master/images/1564226420099.png)




**能力有限，如有不足请及时指出！**

（ps:如果需要使用，请将resources文件夹中的两个配置文件 设为自己本机所使用的）
