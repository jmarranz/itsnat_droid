package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.XMLDOM;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMValues extends XMLDOM
{
    public final static String TYPE_ANIM = "anim";
    public final static String TYPE_ANIMATOR = "animator";
    public final static String TYPE_BOOL = "bool";
    public final static String TYPE_COLOR = "color";
    public final static String TYPE_DIMEN = "dimen";
    public final static String TYPE_DRAWABLE = "drawable";
    public final static String TYPE_FLOAT = "float";
    public final static String TYPE_ID = "id";
    public final static String TYPE_ID_PLUS = "+id";
    public final static String TYPE_INTEGER = "integer";
    public final static String TYPE_INTEGER_ARRAY = "integer-array";
    public final static String TYPE_LAYOUT = "layout";
    public final static String TYPE_STRING = "string";
    public final static String TYPE_STRING_ARRAY = "string-array";
    public final static String TYPE_ARRAY = "array"; // http://developer.android.com/guide/topics/resources/more-resources.html#TypedArray

    private static final Set<String> resourceTypeValues = new HashSet<String>();

    static
    {
        resourceTypeValues.add("style");

        resourceTypeValues.add(TYPE_ANIM);
        resourceTypeValues.add(TYPE_ANIMATOR);
        resourceTypeValues.add(TYPE_BOOL);
        resourceTypeValues.add(TYPE_COLOR);
        resourceTypeValues.add(TYPE_DIMEN);
        resourceTypeValues.add(TYPE_DRAWABLE);
        resourceTypeValues.add(TYPE_FLOAT);
        resourceTypeValues.add(TYPE_ID);
        resourceTypeValues.add(TYPE_ID_PLUS);
        resourceTypeValues.add(TYPE_INTEGER);
        resourceTypeValues.add(TYPE_INTEGER_ARRAY);
        resourceTypeValues.add(TYPE_LAYOUT);
        resourceTypeValues.add(TYPE_STRING);
        resourceTypeValues.add(TYPE_STRING_ARRAY);
        resourceTypeValues.add(TYPE_ARRAY);
    }


    public XMLDOMValues()
    {
    }

    public static boolean isResourceTypeValues(String resourceType)
    {
        return resourceTypeValues.contains(resourceType);
    }
}

/*

Aparte de <declare-styleable> hay tipos no soportados

<fraction>  http://stackoverflow.com/questions/11734470/how-does-one-use-resources-getfraction
<plurals>   http://blog.vogella.com/2011/11/22/plurals-in-android/

Ej. de enum:
    <declare-styleable name="PieChart">
        <attr name="showText" format="boolean" />
        <attr name="labelPosition" format="enum">
            <enum name="left" value="0"/>
            <enum name="right" value="1"/>
        </attr>
    </declare-styleable>

    <attr name="layout_scroll_height" format="integer">
        <enum name="scroll_to_top" value="-1"/>
    </attr>

        Ej. reference:
    <declare-styleable name="ButtonBarContainerTheme">
        <attr name="buttonBarStyle" format="reference" />
        <attr name="buttonBarButtonStyle" format="reference" />
    </declare-styleable>

        Ej. flag:

    <attr name="myflags">
        <flag name="one" value="1" />
        <flag name="two" value="2" />
        <flag name="four" value="4" />
        <flag name="eight" value="8" />
    </attr>



        Ej flags y enum:

<!-- declare myenum attribute -->
<attr name="myenum">
    <enum name="zero" value="0" />
    <enum name="one" value="1" />
    <enum name="two" value="2" />
    <enum name="three" value="3" />
</attr>

<!-- declare myflags attribute -->
<attr name="myflags">
    <flag name="one" value="1" />
    <flag name="two" value="2" />
    <flag name="four" value="4" />
    <flag name="eight" value="8" />
</attr>

<!-- declare our custom widget to be styleable by these attributes -->
<declare-styleable name="com.example.MyWidget">
    <attr name="myenum" />
    <attr name="myflags" />
</declare-styleable>

        In res/layout/mylayout.xml we can now do

<com.example.MyWidget
        myenum="two"
        myflags="one|two"
        ... />

*/
