package cn.jxust_04_reflect;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/*
 * 反射
 * 很多时候配置文件中只有类的字符串，这时不能去new，所以要用到反射
 * http://blog.csdn.net/liujiahan629629/article/details/18013523
 */
public class MyReflect {
	public String className = null;
	@SuppressWarnings("rawtypes")
	public Class personClass = null;
	/**
	 * 反射Person类
	 * @throws Exception 
	 */
	@Before
	public void init() throws Exception {
		className = "cn.jxust_04_reflect.Person";
		personClass = Class.forName(className);
	}
	/**
	 *获取某个class文件对象
	 */
	@Test
	public void getClassName() throws Exception {
		System.out.println(personClass);
	}
	/**
	 *获取某个class文件对象的另一种方式
	 */
	@Test
	public void getClassName2() throws Exception {
		System.out.println(Person.class);
	}
	/**
	 *创建一个class文件表示的实例对象，底层会调用空参数的构造方法
	 *person类中一定要有无参构造方法，不然会报错
	 */
	@Test
	public void getNewInstance() throws Exception {
		System.out.println(personClass.newInstance());
	}
	/**
	 *获取非私有的构造函数
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getPublicConstructor() throws Exception {
		/*getConstructor 获取公共构造器  getDeclaredConstructor获取声明的所有构造器，包括公有、私有的
		 * getConstructors、getDeclaredConstructors 返回所有的构造器
		 * getDeclaredMethods()获取所有的方法
		 * 获取所有的属性  Field[] fs = c.getDeclaredFields();  for(Field field:fs)遍历
		 */
		Constructor  constructor  = personClass.getConstructor(Long.class,String.class);//参数为构造器中的参数的class类型
		Person person = (Person)constructor.newInstance(100L,"zhangsan");
		System.out.println(person.getId());
		System.out.println(person.getName());
		Constructor constructor2 = personClass.getConstructor(Long.class);
		Person p=(Person)constructor2.newInstance(500L);
		System.out.println(p.getId()+" "+p.getName());
	}
	/**
	 *获得私有的构造函数
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getPrivateConstructor() throws Exception {
		Constructor con = personClass.getDeclaredConstructor(String.class);
//		Constructor con = personClass.getConstructor(String.class);
		con.setAccessible(true);//强制取消Java的权限检测
		Person person2 = (Person)con.newInstance("zhangsan");
		System.out.println("**"+person2.getName());
	}
	/**
	 *访问非私有的成员变量
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getNotPrivateField() throws Exception {
		Constructor  constructor  = personClass.getConstructor(Long.class,String.class);
		Object obj = constructor.newInstance(100L,"zhangsan");
		
		Field field = personClass.getField("name");//根据属性名去获得属性
		field.set(obj, "lisi");//为某一对象的该属性赋值
		System.out.println(field.get(obj));//获取某一对象的该属性值
	}
	/**
	 *访问私有的成员变量
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getPrivateField() throws Exception {
		Constructor  constructor  = personClass.getConstructor(Long.class);
		Object obj = constructor.newInstance(100L);
		
		Field field2 = personClass.getDeclaredField("id");
		field2.setAccessible(true);//强制取消Java的权限检测
		field2.set(obj,10000L);
		System.out.println(field2.get(obj));
	}
	/**
	 * 获取所有属性
	 */
	@Test
	public void getAllFields(){
		Field[] fields = personClass.getDeclaredFields();
		for(Field f:fields){//f.getModifiers()为int类型，记得用Modifiertostring
			System.out.println(f);//直接输出可以
			System.out.println("属性名："+f.getName()+" 权限："+Modifier.toString(f.getModifiers())+" 类型："+f.getType().getSimpleName());
		}
	}
	
	/**
	 *获取非私有的成员函数
	 */
	@SuppressWarnings({ "unchecked" })
	@Test
	public void getNotPrivateMethod() throws Exception {
		System.out.println(personClass.getMethod("toString"));
		Object obj = personClass.newInstance();//获取空参的构造函数
		Object o = new Object();
		Method toStringMethod = personClass.getMethod("toString");
		Object object = toStringMethod.invoke(obj);//用某一对象调用该方法,并返回该对象
//		Object object1 = toStringMethod.invoke(o，1);//用某一对象调用该方法 invoke方法中的对象必须是要反射的类的对象
		System.out.println(object);
	}
	/**
	 *获取私有的成员函数
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getPrivateMethod() throws Exception {
		Object obj = personClass.newInstance();//获取空参的构造函数
		Method method = personClass.getDeclaredMethod("getSomeThing");
		method.setAccessible(true);
		Object value = method.invoke(obj);
		System.out.println(value);

	}
	
	/**
	 * 获取所有方法
	 */
	@Test
	public void getAllMethods(){
		Method[] methods = personClass.getDeclaredMethods();
		for(Method m:methods){//f.getModifiers()为int类型，记得用Modifiertostring
			System.out.println(m);//直接输出可以  
			//" 参数类型"+m.getParameterTypes()......  m.getGenericReturnType()返回包含泛型参数信息
			System.out.println("方法名："+m.getName()+" 权限："+Modifier.toString(m.getModifiers())+" 参数个数："+m.getParameterCount()+" 返回值类型"+m.getReturnType());
		}
	}
	
	
	/**
	 *其他方法
	 */
	@Test
	public void otherMethod() throws Exception {
		//当前加载这个class文件的那个类加载器对象
		System.out.println(personClass.getClassLoader());
		//获取某个类实现的所有接口
		Class[] interfaces = personClass.getInterfaces();
		for (Class class1 : interfaces) {
			System.out.println(class1);
		}
		//反射当前这个类的直接父类
		System.out.println(personClass.getGenericSuperclass());//包含泛型参数信息  getSuperclass()不包含泛型参数信息
		/**
		 * getResourceAsStream这个方法可以获取到一个输入流，这个输入流会关联到name所表示的那个文件上。
		 */
		//path 不以’/'开头时默认是从此类所在的包下取资源，以’/'开头则是从ClassPath根下（src）获取。其只是通过path构造一个绝对路径，最终还是由ClassLoader获取资源。
		System.out.println(personClass.getResourceAsStream("/log4j.properties"));
		System.out.println(new BufferedReader(new InputStreamReader(personClass.getResourceAsStream("/log4j.properties"))).readLine());
		System.out.println(personClass.getResourceAsStream("log4j.properties"));
		
		//判断当前的Class对象表示是否是数组
		System.out.println(personClass.isArray());
		System.out.println(new String[3].getClass().isArray());
		
		//判断当前的Class对象表示是否是枚举类
		System.out.println(personClass.isEnum());
		System.out.println(Class.forName("cn.itcast_04_reflect.City").isEnum());
		
		//判断当前的Class对象表示是否是接口
		System.out.println(personClass.isInterface());
		System.out.println(Class.forName("cn.itcast_04_reflect.TestInterface").isInterface());
		
		
	}

}
