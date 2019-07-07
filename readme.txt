ssm框架手动配置环境

1，用mybatis逆向工程建立好bean和entity包

2.因为要集成，所以mybatis的的主配置文件mybatis-config不用配置了，
建立一个spring默认配置文件 applicationContext.xml
1>先自动扫包
<!--  	自动扫描包 -->
	<context:component-scan base-package="com.woniu"></context:component-scan>
2>取代mybatis的的主配置文件，
	配置德鲁伊连接池去连接数据库
	<!--  	让spring管理数据库连接，让德鲁伊连接池配置sql -->
	<bean id="ds" class="com.alibaba.druid.pool.DruidDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		<property name="url" value="jdbc:mysql:///test"></property>
		<property name="username" value="root"></property>
		<property name="password" value="123465"></property>
	</bean>
	
	配置sqlSessionFactoryBean，ref德鲁伊连接池
	<bean id="sf" class="org.mybatis.spring.SqlSessionFactoryBean">  
		<property name="dataSource" ref="ds"></property>
		<property name="typeAliasesPackage" value="com.woniu.entity"></property>
	</bean>
	
	配置指定子配置文件的路径
	<!-- 
		指定子配置文件的位置，工厂就会自动根据找到的子配置，来动态生成对应接口的实现类，并让入容器 
		比如，在com.woniu.dao下找到了UserMapper.xml文件，就会自动创建一个类， 去实现UserMapper接口！
		并且把该UserMapper的实现类的对象，存入容器中，这样在@Autowired UserMapper mapper;就能注入成功了！
	-->
	<bean id="mapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.woniu.dao" />
	</bean>
3>配置声明式事务管理器DatasourceTransactionManager
	<!-- 一个坑，id必须为小写，这样启用时可以不写，因为默认是transactionManager这个名字-->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="ds"></property>
	</bean>
	
	启用:
	<!-- id是其他名字，必须要写括号里的配置-->
	<tx:annotation-driven (transaction-manager="同bean id")/>	
	
3.最后可以建一个service接口和实现类 测试环境
接口
package com.woniu.service;
import com.woniu.entity.User;
public interface IUserService {
	void save(User user);
}
实现类
package com.woniu.service.impl;
@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper mapper;
	
	@Override
	public void save(User user) {
		mapper.insertSelective(user);
	}
}
测试包
package com.woniu.test;
public class AppTest {
@Test
public void testName() throws Exception {
	ApplicationContext ax = //
			new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	IUserService service = (IUserService) ax.getBean("userServiceImpl");
//	System.out.println(service.getClass());
	User u = new User();
	u.setName("ergoulalala");
	u.setPassword("123456");
	service.save(u);
}
}

..........................


4.集成spring-mvc
1导入jar包
2建一个spring配置xml文件
配置基本的三大件
先扫web包
	<context:component-scan base-package="com.woniu.web.controller"></context:component-scan>
映射器和适配器走默认的
	
	<mvc:annotation-driven></mvc:annotation-driven>	
试图解析器如下	
	<!-- 视图解析器 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/"></property>
		<property name="suffix" value=".jsp"></property>
	</bean>
3.在web下建一个controller测试下
@Controller
@RequestMapping("users")
public class UserController {
	@Autowired
	private IUserService service;
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public void findOne() {
	System.out.println("UserController.findOne()"+service);
	}
}
页面访问localhost:端口号/项目名+映射名
localhost/0706SSMDemo/users
测试完成，可以输出

************************
基本环境搭成

