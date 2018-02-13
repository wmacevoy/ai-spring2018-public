/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author wmacevoy
 */
public class Parameters {

    public static Builder make() {
        return new Builder();
    }

    public static Builder make(Parameters start) {
        return new Builder(start);
    }

    public static class Builder {

        private Parameters values;

        Builder() {
            values = new Parameters();
        }

        Builder(Parameters _values) {
            values = new Parameters(_values);
        }

        Builder set(String name, boolean value) {
            values.setBoolean(name, value);
            return this;
        }

        Builder set(String name, long value) {
            values.setLong(name, value);
            return this;
        }

        Builder set(String name, String value) {
            values.setString(name, value);
            return this;
        }

        Builder set(String name, Parameters value) {
            values.setParameters(name, value);
            return this;
        }
        
        Parameters parameters() {
            return values;
        }
    }
    
    public static interface BooleanExpression {

        boolean eval();
    }

    public static interface LongExpression {

        long eval();
    }

    public static interface DoubleExpression {

        double eval();
    }

    public static interface StringExpression {

        String eval();
    }

    public static interface ParametersExpression {

        Parameters eval();
    }
    private HashMap<String, Object> values = new HashMap<String, Object>();

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
        return value == null ? def : value;
    }

    public boolean getBoolean(String name, BooleanExpression ex) {
        Boolean value = (Boolean) values.get(name);
        return value == null ? ex.eval() : value;
    }

    private void setLong(String name, long value) {
        values.put(name, Long.valueOf(value));
    }

    public long getLong(String name) {
        return (Long) values.get(name);
    }

    public long getLong(String name, long def) {
        Long value = (Long) values.get(name);
        return value == null ? def : value;
    }

    public long getLong(String name, LongExpression ex) {
        Long value = (Long) values.get(name);
        return value == null ? ex.eval() : value;
    }

    private void setDouble(String name, double value) {
        values.put(name, Double.valueOf(value));
    }

    public double getDouble(String name) {
        return (Double) values.get(name);
    }

    public double getDouble(String name, double def) {
        Double value = (Double) values.get(name);
        return value == null ? def : value;
    }

    public double getDouble(String name, DoubleExpression ex) {
        Double value = (Double) values.get(name);
        return value == null ? ex.eval() : value;
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
        return (value == null) ? def : value;
    }

    public String getString(String name, StringExpression ex) {
        String value = (String) values.get(name);
        return (value == null) ? ex.eval() : value;
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
            sb.append(name);
            sb.append("=");
            if (value instanceof String) {
                sb.append("'");
                sb.append(value);
                sb.append("'");
            } else if (value instanceof Double) {
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMinimumFractionDigits(1);
                sb.append(nf.format((double) value));
            } else {
                sb.append(value);
            }
        }
        sb.append("}");
        return sb.toString();
    }

}
