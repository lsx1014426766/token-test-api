package com.balala.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BeanCopyUtil {

	static final Logger logger =  LoggerFactory.getLogger(BeanCopyUtil.class);

	/**
	 * 两个不同对象之间数据的复制,仅限于当前类申明变量
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void beanCopy(Object source, Object target){
		Field sourceField[] = source.getClass().getDeclaredFields();
		Field targetField[] = target.getClass().getDeclaredFields();
		for (Field tf : targetField) {
			tf.setAccessible(true);
			for (Field sf : sourceField) {
				sf.setAccessible(true);
				String tfType = tf.getType().getName();
				String sfType = sf.getType().getName();
				String tfname=tf.getName().toLowerCase();
				String sfname=sf.getName().toLowerCase();

				if("serialVersionUID".equals(tf.getName())){
					continue;
				}
				if (tfname.equals(sfname) && tfType.equals(sfType)) {
					try {
						tf.set(target, sf.get(source));
					} catch (Exception e) {
						e.printStackTrace();
					} 
					break;
				}
			}
		}
	}
	
	/**
	 * 两个不同对象之间数据的复制,包含当前类变量及超类中变量
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void beanSuperCopy(Object source, Object target){
		if (source != null && target != null) {
			try {
				List<Field> sourceField = getDeclaredFields(source.getClass());
				List<Field> targetField = getDeclaredFields(target.getClass());
				for (Field tf : targetField) {
					tf.setAccessible(true);
					for (Field sf : sourceField) {
						sf.setAccessible(true);
						String tfType = tf.getType().getName();
						String sfType = sf.getType().getName();
						String tfname=tf.getName().toLowerCase();
						String sfname=sf.getName().toLowerCase();
						if (tfname.equals(sfname) && tfType.equals(sfType)) {
							try {
								tf.set(target, sf.get(source));
							} catch (Exception e) {
								e.printStackTrace();
							} 
							break;
						}
					}
				}
			} catch (SecurityException e) {
//				e.printStackTrace();
				logger.error( "Bean Copy Error", e);
			}
		}
	}
	
	public static List<Field> getDeclaredFields(Class<?> clazz) {
		Field[] cur_fields = null;
		List<Field> sup_fields = null, re = null;
		
		Class<?> clz = clazz.getSuperclass();
		if (clz != null && clz != Object.class) {
			sup_fields = getDeclaredFields(clz);
		}
		
		cur_fields = clazz.getDeclaredFields();
		re = new ArrayList<>(Arrays.asList(cur_fields));
		
		for (Iterator<Field> it = re.iterator(); it.hasNext();) {
			Field field = it.next();
			//提出final字段不予拷贝
			if ((field.getModifiers() & Modifier.FINAL) != 0) {
				it.remove();
			}
		}
		
		if (sup_fields != null && !sup_fields.isEmpty()) {
			re.addAll(sup_fields);
		}
		
		return re;
	}
	
}
