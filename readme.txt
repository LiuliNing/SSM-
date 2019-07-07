ssm����ֶ����û���

1����mybatis���򹤳̽�����bean��entity��

2.��ΪҪ���ɣ�����mybatis�ĵ��������ļ�mybatis-config���������ˣ�
����һ��springĬ�������ļ� applicationContext.xml
1>���Զ�ɨ��
<!--  	�Զ�ɨ��� -->
	<context:component-scan base-package="com.woniu"></context:component-scan>
2>ȡ��mybatis�ĵ��������ļ���
	���õ�³�����ӳ�ȥ�������ݿ�
	<!--  	��spring�������ݿ����ӣ��õ�³�����ӳ�����sql -->
	<bean id="ds" class="com.alibaba.druid.pool.DruidDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		<property name="url" value="jdbc:mysql:///test"></property>
		<property name="username" value="root"></property>
		<property name="password" value="123465"></property>
	</bean>
	
	����sqlSessionFactoryBean��ref��³�����ӳ�
	<bean id="sf" class="org.mybatis.spring.SqlSessionFactoryBean">  
		<property name="dataSource" ref="ds"></property>
		<property name="typeAliasesPackage" value="com.woniu.entity"></property>
	</bean>
	
	����ָ���������ļ���·��
	<!-- 
		ָ���������ļ���λ�ã������ͻ��Զ������ҵ��������ã�����̬���ɶ�Ӧ�ӿڵ�ʵ���࣬���������� 
		���磬��com.woniu.dao���ҵ���UserMapper.xml�ļ����ͻ��Զ�����һ���࣬ ȥʵ��UserMapper�ӿڣ�
		���ҰѸ�UserMapper��ʵ����Ķ��󣬴��������У�������@Autowired UserMapper mapper;����ע��ɹ��ˣ�
	-->
	<bean id="mapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.woniu.dao" />
	</bean>
3>��������ʽ���������DatasourceTransactionManager
	<!-- һ���ӣ�id����ΪСд����������ʱ���Բ�д����ΪĬ����transactionManager�������-->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="ds"></property>
	</bean>
	
	����:
	<!-- id���������֣�����Ҫд�����������-->
	<tx:annotation-driven (transaction-manager="ͬbean id")/>	
	
3.�����Խ�һ��service�ӿں�ʵ���� ���Ի���
�ӿ�
package com.woniu.service;
import com.woniu.entity.User;
public interface IUserService {
	void save(User user);
}
ʵ����
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
���԰�
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


4.����spring-mvc
1����jar��
2��һ��spring����xml�ļ�
���û����������
��ɨweb��
	<context:component-scan base-package="com.woniu.web.controller"></context:component-scan>
ӳ��������������Ĭ�ϵ�
	
	<mvc:annotation-driven></mvc:annotation-driven>	
��ͼ����������	
	<!-- ��ͼ������ -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/"></property>
		<property name="suffix" value=".jsp"></property>
	</bean>
3.��web�½�һ��controller������
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
ҳ�����localhost:�˿ں�/��Ŀ��+ӳ����
localhost/0706SSMDemo/users
������ɣ��������

************************
�����������

