/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steepestdescent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author wmacevoy
 */
public class ReflectedRealParameters implements RealParameters {

    public static boolean isPublic(Field ao) {
        return Modifier.isPublic(ao.getModifiers());
    }

    public static boolean isPublic(Method ao) {
        return Modifier.isPublic(ao.getModifiers());
    }

    public static boolean isDouble(Class clazz) {
        return clazz.equals(double.class);
    }

    Object object;
    ArrayList< RealParameterFly> accessors = new ArrayList< RealParameterFly>();
    HashMap< String, Integer> indexes = new HashMap< String, Integer>();

    ReflectedRealParameters() { this(null); }
    
    ReflectedRealParameters(Object _object) {
        object = _object != null ? _object : this;
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!isPublic(field)) {
                continue;
            }
            Class clazz = field.getType();
            if (isDouble(clazz)) {
                accessors.add(new FieldParameterFly(field));
            }
            if (clazz.equals(double[].class)) {
                double[] a = null;
                try {
                    a = (double[]) field.get(object);
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
                if (a != null) {
                    for (int i = 0; i < a.length; ++i) {
                        accessors.add(new ArrayParameterFly(field, i));
                    }
                }
            }
        }

        Method[] methods = object.getClass().getDeclaredMethods();
        HashMap<String, Method> gets = new HashMap<String, Method>();
        HashMap<String, Method> sets = new HashMap<String, Method>();

        for (Method method : methods) {
            if (isPublic(method)
                    && method.getParameterCount() == 0
                    && isDouble(method.getReturnType())) {
                gets.put(method.getName(), method);
            }
            if (isPublic(method)
                    && method.getParameterCount() == 1
                    && isDouble(method.getParameterTypes()[0])) {
                sets.put(method.getName(), method);
            }
        }

        for (String name : gets.keySet()) {
            if (name.startsWith("get")) {
                String Root = name.substring(3);
                Method setter = sets.get("set" + Root);
                if (setter != null) {
                    Method getter = gets.get("get" + Root);
                    String root = Root.substring(0, 1).toLowerCase() + Root.substring(1);
                    accessors.add(new MethodParameterFly(root, getter, setter));
                }
            } else {
                String root = name;
                Method setter = sets.get(name);
                if (setter != null) {
                    Method getter = gets.get(name);
                    accessors.add(new MethodParameterFly(root, getter, setter));
                }
            }
        }
        
        for (int i=0; i<accessors.size(); ++i) {
            indexes.put(accessors.get(i).name(), i);
        }
    }

    @Override
    public int getRealParameterSize() {
        return accessors.size();
    }

    @Override
    public String getRealParameterName(int index) {
        return accessors.get(index).name();
    }

    @Override
    public int getRealParameterIndex(String name) {
        return indexes.get(name);
    }

    @Override
    public double getRealParameterValue(int index) {
        return accessors.get(index).get(object);
    }

    @Override
    public void setRealParameterValue(int index, double value) {
        accessors.get(index).set(object, value);
    }
}
