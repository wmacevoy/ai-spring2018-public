/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradientdescent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Warren MacEvoy
 *
 * Use reflection to create a minimization problem from a class, public double
 * values, arrays and get/set are parameters, and a public get with no set is
 * the value. Ex: class Ex extends ReflectedRealMin { public double x; public
 * double y; public double [] a = new double[2]; private time; public double
 * getTime() { return time; } public void setTime(double value) { time=value; }
 * private double _money; public double money() { return _money; } public void
 * money(double value) { _money=value; } public double cost() { return
 * x+y+a[0]+a[1]+getTime()+money(); } } This will be a minimization problem with
 * 6 parameters "x","y","a[0]","a[1]","time","money" and minimization target of
 * "cost". Note the indexes are not necessarily in declared order, but will be
 * consistent across all instances of Ex.
 *
 */
public class ReflectedRealMin extends ReflectedRealParameters implements RealMin {

    Method getValueMethod;

    public ReflectedRealMin() {
        this(null);
    }

    public ReflectedRealMin(Object _object) {
        super(_object);
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

        ArrayList<Method> mins = new ArrayList<Method>();
        for (String name : gets.keySet()) {
            if (name.startsWith("get")) {
                String Root = name.substring(3);
                Method setter = sets.get("set" + Root);
                if (setter == null) {
                    Method getter = gets.get("get" + Root);
                    mins.add(getter);
                }
            } else {
                String root = name;
                Method setter = sets.get(name);
                if (setter == null) {
                    Method getter = gets.get(name);
                    mins.add(getter);
                }
            }
        }

        getValueMethod = null;
        for (Method method : mins) {
            if (method.getName().equals("getValue")) {
                getValueMethod = method;
                break;
            }
        }
        if (getValueMethod == null) {
            if (mins.size() == 0) {
                throw new IllegalArgumentException("no min target method found");
            } else if (mins.size() == 1) {
                getValueMethod = mins.get(0);
            } else {
                String names = "";
                for (int i = 0; i < mins.size(); ++i) {
                    if (i > 0) {
                        names = names + ",";
                    }
                    names = names + mins.get(i).getName();
                }
                throw new IllegalArgumentException("ambiguous, change one of " + names + " to getValue");
            }
        }
    }

    @Override
    public double getValue() {
        try {
            return (double) getValueMethod.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new IllegalStateException();
        }
    }

    @Override
    public RealMin copy() {
        Class clazz = object.getClass();
        RealMin ans = null;
        Constructor cons = null;
        try {
            try {
                cons = clazz.getDeclaredConstructor(clazz);
                ans = (RealMin) cons.newInstance(this);
            } catch (NoSuchMethodException ex) {
                try {
                    cons = clazz.getDeclaredConstructor();
                    ans = (RealMin) cons.newInstance();
                } catch (NoSuchMethodException | SecurityException ex1) {
                    throw new IllegalStateException(ex1);
                }
                int n = ans.getRealParameterSize();
                for (int i = 0; i < n; ++i) {
                    ans.setRealParameterValue(i, getRealParameterValue(i));
                }
            }
        } catch (SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException(ex);
        }
        return ans;
    }
}
