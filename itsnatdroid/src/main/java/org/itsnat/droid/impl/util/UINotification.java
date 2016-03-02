package org.itsnat.droid.impl.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by jmarranz on 30/06/14.
 */
public class UINotification
{
    public static void alert(String title, Object value, Context ctx)
    {
        String text = value != null ? value.toString() : "null";

        new AlertDialog.Builder(ctx).setTitle(title).setMessage(text)
        .setCancelable(false)
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public static void toast(Object value,int duration, Context ctx)
    {
        String text = value != null ? value.toString() : "null";
        Toast.makeText(ctx, text, duration).show();
    }
}

