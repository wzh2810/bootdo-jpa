package com.bootdo_jpa.common.utils.bean;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

	/**
     * <p>Populate the JavaBeans properties of the specified bean, based on
     * the specified name/value pairs.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean JavaBean whose properties are being populated
     * @param properties Map keyed by property name, with the
     *  corresponding (String or String[]) value(s) to be set
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @see BeanUtilsBean#populate
     */
    @SuppressWarnings("rawtypes")
    public static void populate(Object bean, Map properties)
        throws IllegalAccessException, InvocationTargetException {
        
        BeanUtilsBean.getInstance().populate(bean, properties);
    }
    
}
