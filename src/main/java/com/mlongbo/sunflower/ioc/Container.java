package com.mlongbo.sunflower.ioc;

import org.apache.commons.beanutils.PropertyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Container
{
    private static final String CONF_NAME = "components.properties";
    private Map<String, Object> components;
    private Properties conf;

    public Container()
            throws IOException
    {
        InputStream stream = getClass().getResourceAsStream("/components.properties");

        this.conf = new Properties();
        this.conf.load(stream);
    }

    public Container(Properties conf)
    {
        this.conf = conf;
    }

    public Container(String confURI)
            throws IOException
    {
        InputStream stream = new FileInputStream(new File(confURI));

        this.conf = new Properties();
        this.conf.load(stream);
    }

    public void init()
            throws Exception
    {
        this.components = new HashMap();

        for (Map.Entry entry : this.conf.entrySet()) {
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();

            processComponent(key, value);
        }

        for (Map.Entry entry : this.conf.entrySet()) {
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();

            processDependency(key, value);
        }
    }

    private void processDependency(String key, String value)
            throws NoSuchMethodException, InvocationTargetException, Exception
    {
        String[] parts = key.split("\\.");

        if (parts.length > 1) {
            Object component = this.components.get(parts[0]);
            Object reference = this.components.get(value);
            try
            {
                PropertyUtils.setProperty(component, parts[1], reference);
            } catch (IllegalAccessException e) {
                throw new Exception("cant not find bean: " + value, e);
            }
        }
    }

    private void processComponent(String key, String value)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException, InstantiationException
    {
        String[] parts = key.split("\\.");

        if (parts.length == 1) {
            Object component = Class.forName(value).newInstance();
            this.components.put(parts[0], component);
        }
    }

    public Object getComponent(String id)
    {
        return this.components.get(id);
    }
}