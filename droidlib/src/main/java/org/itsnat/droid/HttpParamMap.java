package org.itsnat.droid;

/**
 * Created by Jose on 15/12/2015.
 */
public interface HttpParamMap
{
    Object getParameter(String name);

    HttpParamMap setParameter(String name, Object value);

    //HttpParamMap copy();

    boolean removeParameter(String name);

    long getLongParameter(String name, long defaultValue);

    HttpParamMap setLongParameter(String name, long value);

    int getIntParameter(String name, int defaultValue);

    HttpParamMap setIntParameter(String name, int value);

    double getDoubleParameter(String name, double defaultValue);

    HttpParamMap setDoubleParameter(String name, double value);

    boolean getBooleanParameter(String name, boolean defaultValue);

    HttpParamMap setBooleanParameter(String name, boolean value);

    boolean isParameterTrue(String name);

    boolean isParameterFalse(String name);

}
