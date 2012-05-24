package pl.net.bluesoft.util.lang;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Classes {
    private static final Logger logger = Logger.getLogger(Classes.class.getName());

    public static List<Field> getDeclaredFields(Class clazz) {
        List<Field> fields = new ArrayList<Field>();
        while (clazz != Object.class && clazz != null) {
            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers())) {
                    fields.add(f);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public static Field findDeclaredField(Class clazz, String fieldName) {
        for (Field field : getDeclaredFields(clazz)) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    public static <A extends Annotation> List<Field> getFieldsWithAnnotation(Class clazz, Class<A> annotation) {
        List<Field> fields = new ArrayList<Field>();
        while (clazz != Object.class && clazz != null) {
            for (Field f : clazz.getDeclaredFields()) {
                if (f.isAnnotationPresent(annotation)) {
                    fields.add(f);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public static <A extends Annotation> A getClassAnnotation(Class clazz, Class<A> annotation) {
        while (clazz != Object.class && clazz != null) {
            if (clazz.isAnnotationPresent(annotation)) {
                return (A) clazz.getAnnotation(annotation);
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public static void setFieldValue(Object instance, Field field, Object value) throws Exception {
        PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(instance, field.getName());
        Method m = pd != null ? PropertyUtils.getWriteMethod(pd) : null;
        if (m == null) {
            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }
            field.set(instance, value);
            if (!accessible) {
                field.setAccessible(false);
            }
        }
        else {
            m.invoke(instance, value);
        }
    }

    public static void setFieldValue(Object instance, String fieldName, Object value) throws Exception {
        PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(instance, fieldName);
        Method m = pd != null ? PropertyUtils.getWriteMethod(pd) : null;
        if (m == null) {
            Field field = findDeclaredField(instance.getClass(), fieldName);
            if (field == null) {
                throw new RuntimeException("Cannot find field named " + fieldName);
            }
            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }
            field.set(instance, value);
            if (!accessible) {
                field.setAccessible(false);
            }
        }
        else {
            m.invoke(instance, value);
        }
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        }
        catch (Exception ex) {
            throw new RuntimeException("Unable to invoke method", ex);
        }
    }

    public static Properties loadProperties(Class clazz, String path) {
        Properties p = new Properties();
        try {
            p.load(clazz.getResourceAsStream(path));
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return p;
    }

	public static <T> T newInstance(Class<T> clazz) {
		try {
			Constructor constr = clazz.getConstructor();
			return (T)constr.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setProperty(Object object, String property, Object value) {
		if (property == null) {
			return;
		}
		try {
			PropertyUtils.setProperty(object, property, value);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getProperty(Object object, String property) {
		if (property == null) {
			return null;
		}
		try {
			return PropertyUtils.getProperty(object, property);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void copyProperties(Object dest, Object src) {
        try {
            PropertyUtils.copyProperties(dest, src);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyProperties(Object dest, Object src, String[] skippedProperties) {
        Map<String, Object> oldValues = new HashMap<String, Object>();
        if (skippedProperties != null) {
            for (String property : skippedProperties) {
                oldValues.put(property, getProperty(dest, property));
            }
        }

		Classes.copyProperties(dest, src);

        if (skippedProperties != null) {
            for (String property : skippedProperties) {
                setProperty(dest, property, oldValues.get(property));
            }
        }
    }
}
