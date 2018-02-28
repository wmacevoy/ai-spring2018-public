/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coloradomesa.cs.ai;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wmacevoy
 */
public class Parameters implements Comparable<Parameters> {

    public static final Parameters NONE = new Parameters();

    public static Builder make() {
        return new Builder();
    }

    public static Builder make(Parameters start) {
        return new Builder(start);
    }

    public static Builder make(String json) {
        Parse parse = new Parse();
        Builder builder = new Builder();
        builder.parameters = parse.parse(json);
        return builder;
    }

    @Override
    public boolean equals(Object to) {
        if (to instanceof Parameters) {
            return compareTo((Parameters) to) == 0;
        } else {
            return getClass().getCanonicalName().compareTo(to.getClass().getCanonicalName()) == 0;
        }
    }

    @Override
    public int compareTo(Parameters to) {
        if (to == null) {
            return 1;
        }
        if (!to.values.keySet().containsAll(values.keySet())) {
            return 1;
        }
        if (!values.keySet().containsAll(to.values.keySet())) {
            return -1;
        }
        for (Map.Entry<String, Object> entries : values.entrySet()) {
            String name = entries.getKey();
            Object value = entries.getValue();
            Object toValue = to.values.get(name);
            Class valueType = value.getClass();
            Class toValueType = toValue.getClass();
            if (valueType.equals(toValueType)) {
                int cmp = ((Comparable) value).compareTo(toValue);
                if (cmp < 0) {
                    return -1;
                }
                if (cmp > 0) {
                    return 1;
                }
                continue;
            } else {
                int cmp = valueType.getCanonicalName().compareTo(toValueType.getCanonicalName());
                if (cmp < 0) {
                    return -1;
                }
                if (cmp > 0) {
                    return 1;
                }
                continue;
            }
        }
        return 0;
    }

    public static class Builder {

        private Parameters parameters;

        public Builder() {
            parameters = new Parameters();
        }

        public Builder(Parameters _values) {
            parameters = new Parameters(_values);
        }

        public Builder set(String name, boolean value) {
            parameters.setBoolean(name, value);
            return this;
        }

        public Builder def(String name, boolean value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Builder set(String name, byte value) {
            parameters.setByte(name, value);
            return this;
        }

        public Builder def(String name, byte value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Builder set(String name, short value) {
            parameters.setShort(name, value);
            return this;
        }

        public Builder def(String name, short value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Builder set(String name, int value) {
            parameters.setInt(name, value);
            return this;
        }

        public Builder def(String name, int value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Builder set(String name, long value) {
            parameters.setLong(name, value);
            return this;
        }

        public Builder def(String name, long value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Builder set(String name, float value) {
            parameters.setFloat(name, value);
            return this;
        }

        public Builder def(String name, float value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Builder set(String name, double value) {
            parameters.setDouble(name, value);
            return this;
        }

        public Builder def(String name, double value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Builder set(String name, BigInteger value) {
            parameters.setBigInteger(name, value);
            return this;
        }

        public Builder def(String name, BigInteger value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Builder set(String name, BigDecimal value) {
            parameters.setBigDecimal(name, value);
            return this;
        }

        public Builder def(String name, BigDecimal value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Builder set(String name, String value) {
            parameters.setString(name, value);
            return this;
        }

        public Builder def(String name, String value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Builder set(String name, Parameters value) {
            parameters.setParameters(name, value);
            return this;
        }

        public Builder def(String name, Parameters value) {
            if (!parameters.defined(name)) {
                set(name, value);
            }
            return this;
        }

        public Parameters parameters() {
            Parameters _values = parameters;
            parameters = null;
            return _values;
        }
    }

    public static interface BooleanExpression {

        boolean eval();
    }

    public static interface ByteExpression {

        byte eval();
    }

    public static interface ShortExpression {

        short eval();
    }

    public static interface IntExpression {

        int eval();
    }

    public static interface LongExpression {

        long eval();
    }

    public static interface FloatExpression {

        float eval();
    }

    public static interface DoubleExpression {

        double eval();
    }

    public static interface BigIntegerExpression {

        BigInteger eval();
    }

    public static interface BigDecimalExpression {

        BigDecimal eval();
    }

    public static interface StringExpression {

        String eval();
    }

    public static interface ParametersExpression {

        Parameters eval();
    }
    private TreeMap<String, Object> values = new TreeMap<String, Object>();

    public Parameters() {
    }

    public Parameters(Parameters copy) {
        for (Entry<String, Object> nameValue : copy.values.entrySet()) {
            String name = nameValue.getKey();
            Object value = nameValue.getValue();
            if (value instanceof Parameters) {
                value = new Parameters((Parameters) value);
            }
            values.put(name, value);
        }
    }

    public boolean defined(String name) {
        return values.containsKey(name);
    }

    public Parameters copy() {
        return new Parameters(this);
    }

    private void setBoolean(String name, boolean value) {
        values.put(name, Boolean.valueOf(value));
    }

    public boolean getBoolean(String name) {
        return (Boolean) values.get(name);
    }

    public boolean getBoolean(String name, boolean def) {
        Boolean value = (Boolean) values.get(name);
        return value != null ? value : def;
    }

    public boolean getBoolean(String name, BooleanExpression ex) {
        Boolean value = (Boolean) values.get(name);
        return value != null ? value : ex.eval();
    }

    public byte getByte(String name) {
        return ((Number) values.get(name)).byteValue();
    }

    private void setByte(String name, byte value) {
        values.put(name, Byte.valueOf(value));
    }

    public byte getByte(String name, byte def) {
        Number value = (Number) values.get(name);
        return value != null ? value.byteValue() : def;
    }

    public byte getByte(String name, ByteExpression ex) {
        Number value = (Number) values.get(name);
        return value != null ? value.byteValue() : ex.eval();
    }

    private void setShort(String name, short value) {
        if (Byte.MIN_VALUE <= value && value <= Byte.MAX_VALUE) {
            setByte(name, (byte) value);
        } else {
            values.put(name, Short.valueOf(value));
        }
    }

    public short getShort(String name) {
        return ((Number) values.get(name)).shortValue();
    }

    public short getShort(String name, short def) {
        Number value = (Number) values.get(name);
        return value != null ? value.shortValue() : def;
    }

    public short getShort(String name, ShortExpression ex) {
        Number value = (Number) values.get(name);
        return value != null ? value.shortValue() : ex.eval();
    }

    private void setInt(String name, int value) {
        if (Short.MIN_VALUE <= value && value <= Short.MAX_VALUE) {
            setShort(name, (short) value);
        } else {
            values.put(name, Integer.valueOf(value));
        }
    }

    public int getInt(String name) {
        return ((Number) values.get(name)).intValue();
    }

    public int getInt(String name, int def) {
        Number value = (Number) values.get(name);
        return value != null ? value.intValue() : def;
    }

    public int getInt(String name, IntExpression ex) {
        Number value = (Number) values.get(name);
        return value != null ? value.intValue() : ex.eval();
    }

    private void setLong(String name, long value) {
        if (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE) {
            setInt(name, (int) value);
        } else {

            values.put(name, Long.valueOf(value));
        }
    }

    public long getLong(String name) {
        return ((Number) values.get(name)).longValue();
    }

    public long getLong(String name, long def) {
        Number value = (Number) values.get(name);
        return value != null ? value.longValue() : def;
    }

    public long getLong(String name, LongExpression ex) {
        Number value = (Number) values.get(name);
        return value != null ? value.longValue() : ex.eval();
    }

    private void setFloat(String name, float value) {
        long lvalue = (long) value;
        if (lvalue == value) {
            setLong(name, lvalue);
        } else {
            values.put(name, Float.valueOf(value));
        }
    }

    public float getFloat(String name) {
        return ((Number) values.get(name)).floatValue();
    }

    public float getFloat(String name, float def) {
        Number value = (Number) values.get(name);
        return value != null ? value.floatValue() : def;
    }

    public float getFloat(String name, FloatExpression ex) {
        Number value = (Number) values.get(name);
        return value != null ? value.floatValue() : ex.eval();
    }

    private void setDouble(String name, double value) {
        long lvalue = (long) value;
        if (lvalue == value) {
            setLong(name, lvalue);
        } else {
            float fvalue = (float) value;
            if (fvalue == value) {
                setFloat(name, fvalue);
            } else {
                values.put(name, Double.valueOf(value));
            }
        }
    }

    public double getDouble(String name) {
        return ((Number) values.get(name)).doubleValue();
    }

    public double getDouble(String name, double def) {
        Number value = (Number) values.get(name);
        return value != null ? value.doubleValue() : def;
    }

    public double getDouble(String name, DoubleExpression ex) {
        Number value = (Number) values.get(name);
        return value != null ? value.doubleValue() : ex.eval();
    }

    private void setBigInteger(String name, BigInteger value) {
        if (value.bitLength() <= 63) {
            setLong(name, value.longValue());
        } else {
            values.put(name, value);
        }
    }

    private static BigInteger toBigInteger(Object value) {
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toBigInteger();
        }
        if (value instanceof Double || value instanceof Float) {
            return BigDecimal.valueOf(((Number) value).doubleValue()).toBigInteger();
        }
        return BigInteger.valueOf(((Number) value).longValue());

    }

    public BigInteger getBigInteger(String name) {
        return toBigInteger(values.get(name));
    }

    public BigInteger getBigInteger(String name, BigInteger def) {
        Object value = values.get(name);
        return value != null ? toBigInteger(value) : def;
    }

    public BigInteger getBigInteger(String name, BigIntegerExpression ex) {
        Object value = values.get(name);
        return value != null ? toBigInteger(value) : ex.eval();
    }

    private static BigDecimal toBigDecimal(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }
        if (value instanceof Double || value instanceof Float) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        }
        return BigDecimal.valueOf(((Number) value).longValue());
    }

    private void setBigDecimal(String name, BigDecimal value) {
        if (value.signum() == 0 || value.scale() <= 0 || value.stripTrailingZeros().scale() <= 0) {
            setBigInteger(name, value.toBigIntegerExact());
        } else {
            double dvalue = value.doubleValue();
            if (value.equals(BigDecimal.valueOf(dvalue))) {
                setDouble(name, dvalue);
            } else {
                values.put(name, value);
            }
        }
    }

    public BigDecimal getBigDecimal(String name) {
        return toBigDecimal(values.get(name));
    }

    public BigDecimal getBigDecimal(String name, BigDecimal def) {
        Object value = values.get(name);
        return value != null ? toBigDecimal(value) : def;
    }

    public BigDecimal getBigDecimal(String name, BigDecimalExpression ex) {
        Object value = values.get(name);
        return value != null ? toBigDecimal(value) : ex.eval();
    }

    private void setString(String name, String value) {
        values.put(name, value);
    }

    public String getString(String name) {
        String value = (String) values.get(name);
        if (value == null) {
            throw new NullPointerException();
        }
        return value;
    }

    public String getString(String name, String def) {
        String value = (String) values.get(name);
        return (value != null) ? value : def;
    }

    public String getString(String name, StringExpression ex) {
        String value = (String) values.get(name);
        return (value != null) ? value : ex.eval();
    }

    private void setParameters(String name, Parameters value) {
        values.put(name, value);
    }

    public Parameters getParameters(String name) {
        Parameters value = (Parameters) values.get(name);
        if (value == null) {
            throw new NullPointerException();
        }
        return value;
    }

    public Parameters getParameters(String name, Parameters def) {
        Parameters value = (Parameters) values.get(name);
        return (value == null) ? def : value;
    }

    public Parameters getParameters(String name, ParametersExpression ex) {
        Parameters value = (Parameters) values.get(name);
        return (value == null) ? ex.eval() : value;
    }

    private static void quote(StringBuilder sb, String string) {
        if (string == null || string.length() == 0) {
            sb.append("\"\"");
        }

        char c = 0;
        int i;
        int len = string.length();
        String t;

        sb.append('"');
        for (i = 0; i < len; i += 1) {
            c = string.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u" + t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        sb.append("{");
        for (Entry<String, Object> nameValue : values.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            String name = nameValue.getKey();
            Object value = nameValue.getValue();
            quote(sb, name);
            sb.append(":");
            if (value instanceof String) {
                quote(sb, (String) value);
            } else {
                sb.append(value);
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public int size() {
        return values.size();
    }

    public boolean modify(Object object, Field field) {
        try {
            field.setAccessible(true);
            String name = field.getName();
            Class clazz = field.getType();
            if (clazz.equals(Parameters.class) && name.equals("parameters")) {
                field.set(object, this);
                return true;
            }
            Object value = values.get(name);
            if (value == null) {
                return false;
            }
            if (clazz.equals(boolean.class)) {
                field.setBoolean(object, getBoolean(name));
                return true;
            }
            if (clazz.equals(Boolean.class)) {
                field.set(object, getBoolean(name));
                return true;
            }
            if (clazz.equals(byte.class)) {
                field.setByte(object, getByte(name));
                return true;
            }
            if (clazz.equals(Byte.class)) {
                field.set(object, getByte(name));
                return true;
            }
            if (clazz.equals(short.class)) {
                field.setShort(object, getShort(name));
                return true;
            }
            if (clazz.equals(Short.class)) {
                field.set(object, getShort(name));
                return true;
            }
            if (clazz.equals(int.class)) {
                field.setInt(object, getInt(name));
                return true;
            }
            if (clazz.equals(Integer.class)) {
                field.set(object, getInt(name));
                return true;
            }
            if (clazz.equals(long.class)) {
                field.setLong(object, getLong(name));
                return true;
            }
            if (clazz.equals(Long.class)) {
                field.set(object, getLong(name));
                return true;
            }
            if (clazz.equals(float.class)) {
                field.setFloat(object, getFloat(name));
                return true;
            }
            if (clazz.equals(Float.class)) {
                field.set(object, getFloat(name));
                return true;
            }
            if (clazz.equals(double.class)) {
                field.setDouble(object, getDouble(name));
                return true;
            }
            if (clazz.equals(Double.class)) {
                field.set(object, getDouble(name));
                return true;
            }
            if (clazz.equals(BigInteger.class)) {
                field.set(object, getBigInteger(name));
                return true;
            }
            if (clazz.equals(BigDecimal.class)) {
                field.set(object, getBigDecimal(name));
                return true;
            }
            if (clazz.equals(String.class)) {
                field.set(object, getString(name));
                return true;
            }
            if (clazz.equals(Parameters.class)) {
                field.set(object, value);
                return true;
            }

            if (value instanceof Parameters) {
                Object fieldValue = field.get(object);
                if (fieldValue == null) {
                    fieldValue = create((Parameters) value, clazz);
                    field.set(object, fieldValue);
                } else {
                    ((Parameters) value).modify(fieldValue);
                }
                return true;
            }

        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    boolean modify(Object object, Method get, Method set, String name) {
        try {
            get.setAccessible(true);
            set.setAccessible(true);
            Class clazz = get.getReturnType();
            if (clazz.equals(Parameters.class) && name.equals("parameters")) {
                set.invoke(object, this);
                return true;
            }
            Object value = values.get(name);
            if (value == null) {
                return false;
            }
            if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
                set.invoke(object, getBoolean(name));
                return true;
            }
            if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
                set.invoke(object, getByte(name));
                return true;
            }
            if (clazz.equals(short.class) || clazz.equals(Short.class)) {
                set.invoke(object, getShort(name));
                return true;
            }
            if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
                set.invoke(object, getInt(name));
                return true;
            }
            if (clazz.equals(long.class) || clazz.equals(Long.class)) {
                set.invoke(object, getLong(name));
                return true;
            }
            if (clazz.equals(float.class) || clazz.equals(Float.class)) {
                set.invoke(object, getFloat(name));
                return true;
            }
            if (clazz.equals(double.class) || clazz.equals(Double.class)) {
                set.invoke(object, getDouble(name));
                return true;
            }
            if (clazz.equals(BigInteger.class)) {
                set.invoke(object, getBigInteger(name));
                return true;
            }
            if (clazz.equals(BigDecimal.class)) {
                set.invoke(object, getBigDecimal(name));
                return true;
            }
            if (clazz.equals(String.class)) {
                set.invoke(object, getString(name));
                return true;
            }
            if (clazz.equals(Parameters.class)) {
                set.invoke(object, value);
                return true;
            }
            if (value instanceof Parameters) {
                Object fieldValue = get.invoke(object);
                if (fieldValue == null) {
                    fieldValue = create((Parameters) value, clazz);
                    set.invoke(object, fieldValue);
                } else {
                    ((Parameters) value).modify(fieldValue);
                }
                return true;
            }

        } catch (InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    private static boolean isPublic(Field ao) {
        return Modifier.isPublic(ao.getModifiers());
    }

    private static boolean isPublic(Method ao) {
        return Modifier.isPublic(ao.getModifiers());
    }

    public void modify(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (isPublic(field)) {
                modify(object, field);
            }
        }

        Method[] methods = object.getClass().getDeclaredMethods();
        HashMap<String, Method> gets = new HashMap<String, Method>();
        HashMap<String, Method> sets = new HashMap<String, Method>();

        for (Method method : methods) {
            if (isPublic(method)
                    && method.getParameterCount() == 0) {
                gets.put(method.getName(), method);
            }
            if (isPublic(method)
                    && method.getParameterCount() == 1) {
                sets.put(method.getName(), method);
            }
        }

        for (Map.Entry<String, Method> entry : gets.entrySet()) {
            String name = entry.getKey();
            Method get = entry.getValue();
            if (name.startsWith("get")) {
                String Root = name.substring(3);
                Method set = sets.get("set" + Root);
                if (set != null && set.getParameterTypes()[0].equals(get.getReturnType())) {
                    String root = Root.substring(0, 1).toLowerCase() + Root.substring(1);
                    modify(object, get, set, root);
                }
            } else {
                String root = name;
                Method set = sets.get(name);
                if (set != null && set.getParameterTypes()[0].equals(get.getReturnType())) {
                    modify(object, get, set, root);
                }
            }
        }

    }

    static Object create(Parameters parameters) {
        return create(parameters, null);
    }

    static Object create(Parameters parameters, Class<?> defBaseClass) {
        Class<?> clazz = null;
        try {
            String className = parameters.getString("class", (String) null);
            if (className != null) {
                clazz = Class.forName(className);
            } else {
                clazz = defBaseClass;
            }
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        if (clazz == null) {
            throw new RuntimeException("could not determine class type");
        }
        if (defBaseClass != null && !defBaseClass.isAssignableFrom(clazz)) {
            throw new RuntimeException("" + clazz + " is not a subclass of " + defBaseClass);
        }
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(Parameters.class);
            constructor.setAccessible(true);
            Object object = constructor.newInstance(parameters);
            return object;
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object object = constructor.newInstance();
                parameters.modify(object);
                try {
                    Method init = clazz.getDeclaredMethod("init");
                    init.setAccessible(true);
                    init.invoke(object);
                } catch (NoSuchMethodException ex3) {
                }

                return object;
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex2) {
                throw new RuntimeException(ex2);
            }
        }

    }

    private static class Parse {

        CharSequence cs;
        int at;

        boolean end() {
            return at >= cs.length();
        }

        int cp() {
            return !end() ? Character.codePointAt(cs, at) : -1;
        }

        void next() {
            if (!end()) {
                at += Character.charCount(cp());
            }
        }

        boolean white() {
            return Character.isWhitespace(cp());
        }

        boolean skipWhite() {
            while (white()) {
                next();
            }
            return true;
        }

        boolean match(int m) {
            int c = cp();
            if (c == m) {
                at += Character.charCount(c);
                return true;
            } else {
                return false;
            }
        }

        boolean wsMatch(int m) {
            int cursor = at;
            if (skipWhite() && match(m) && skipWhite()) {
                return true;
            } else {
                at = cursor;
                return false;
            }
        }

        boolean parseBigDecimal(BigDecimal[] bdRef) {
            StringBuilder sb = new StringBuilder();
            int cursor = at;
            for (;;) {
                int c = cp();
                if (c == '_') {
                    next();
                    continue;
                }
                if (isNum(c)) {
                    sb.appendCodePoint(c);
                    next();
                    continue;
                }
                break;
            }

            try {
                bdRef[0] = new BigDecimal(sb.toString());
            } catch (NumberFormatException ex) {
                at = cursor;
                return false;
            }
            return true;
        }

        boolean isHex(int c) {
            return (('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F'));
        }

        boolean isNum(int c) {
            return ('0' <= c && c <= '9')
                    || c == '.' || c == '+' || c == '-' || c == 'e' || c == 'E';
        }

        boolean parseString(StringBuilder sb) {
            int cursor = at;
            if (!match('"')) {
                at = cursor;
                return false;
            }
            for (;;) {
                int c = cp();
                next();
                if (c == -1) {
                    at = cursor;
                    return false;
                }
                if (c == '"') {
                    return true;
                }
                if (c != '\\') {
                    sb.appendCodePoint(c);
                } else {
                    int ec = cp();
                    next();
                    switch (ec) {
                        case 'b':
                            sb.appendCodePoint('\b');
                            break;
                        case 't':
                            sb.appendCodePoint('\t');
                            break;
                        case 'n':
                            sb.appendCodePoint('\n');
                            break;
                        case 'f':
                            sb.appendCodePoint('\f');
                            break;
                        case 'r':
                            sb.appendCodePoint('\r');
                            break;
                        case 'u':
                            int begin = at;
                            while (at < begin + 4 && isHex(cp())) {
                                next();
                            }
                            if (at > begin) {
                                int uc = Integer.parseInt(cs.subSequence(begin, at).toString(), 16);
                                sb.appendCodePoint(uc);
                            } else {
                                at = cursor;
                                return false;
                            }
                            break;
                        default:
                            sb.appendCodePoint(ec);
                            break;
                    }
                }
            }
        }

        boolean parseName(StringBuilder sb) {
            int cursor = at;
            int begin = sb.length();
            if (cp() == ('"')) {
                if (parseString(sb)) {
                    if (sb.length() > begin) {
                        return true;
                    }
                }
            } else {
                for (;;) {
                    int c = cp();
                    if (Character.isWhitespace(c)
                            || c == ':' || c == '{' || c == '}' || c == ',' || c == '"') {
                        break;
                    }
                    sb.appendCodePoint(c);
                    at += Character.charCount(c);
                }
                if (sb.length() > begin) {
                    return true;
                }
            }
            at = cursor;
            return false;
        }

        boolean parseValue(Parameters p, String name) {
            int cursor = at;
            skipWhite();
            int c = cp();
            if (c == '{') {
                Parameters q = new Parameters();
                if (parseParameters(q)) {
                    p.setParameters(name, q);
                    return true;
                } else {
                    at = cursor;
                    return false;
                }
            } else if (c == '"') {
                StringBuilder sb = new StringBuilder();
                if (parseString(sb)) {
                    p.setString(name, sb.toString());
                    return true;
                } else {
                    at = cursor;
                    return false;
                }
            } else if (c == 't' || c == 'T' || c == 'f' || c == 'F') {
                StringBuilder sb = new StringBuilder();
                if (parseName(sb)) {
                    String ans = sb.toString().toLowerCase();
                    if (ans.equals("true")) {
                        p.setBoolean(name, true);
                        return true;
                    } else if (ans.equals("false")) {
                        p.setBoolean(name, false);
                        return true;
                    }
                }
                at = cursor;
                return false;

            } else {
                BigDecimal[] bdRef = new BigDecimal[1];
                if (parseBigDecimal(bdRef)) {
                    p.setBigDecimal(name, bdRef[0]);
                    return true;
                } else {
                    at = cursor;
                    return false;
                }
            }
        }

        boolean parseParameters(Parameters p) {
            int cursor = at;
            if (!wsMatch('{')) {
                at = cursor;
                return false;
            }
            for (;;) {
                cursor = at;
                wsMatch(',');
                StringBuilder name = new StringBuilder();
                if (!parseName(name)) {
                    return wsMatch('}');
                }
                if (!wsMatch(':') && !wsMatch('=')) {
                    at = cursor;
                    return false;
                }
                if (!parseValue(p, name.toString())) {
                    at = cursor;
                    return false;
                }
            }
        }

        Parameters parse(CharSequence _cs) {
            Parameters p = new Parameters();
            cs = _cs;
            at = 0;
            if (parseParameters(p) && end()) {
                return p;
            } else {
                return null;
            }
        }
    }

}
