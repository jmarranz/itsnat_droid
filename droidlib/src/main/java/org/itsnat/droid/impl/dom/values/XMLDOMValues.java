package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.XMLDOM;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMValues extends XMLDOM
{
    public final static String TYPE_BOOL = "bool";
    public final static String TYPE_COLOR = "color";
    public final static String TYPE_DIMEN = "dimen";
    public final static String TYPE_STRING = "string";
    public final static String TYPE_STRING_ARRAY = "string-array";
    public final static String TYPE_INTEGER_ARRAY = "integer-array";
    public final static String TYPE_ARRAY = "array"; // http://developer.android.com/guide/topics/resources/more-resources.html#TypedArray

    public XMLDOMValues()
    {
    }
}
