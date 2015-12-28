package org.itsnat.itsnatdroidtest.testact.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.itsnatdroidtest.R;

/**
 * Created by jmarranz on 20/06/14.
 */
public class CustomTextView extends CustomTextViewBase
{
    public CustomTextView(Context context)
    {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
}
