package org.itsnat.droid.impl.browser.serveritsnat;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import org.itsnat.droid.event.UserEvent;

/**
 * Métodos llamados por el servidor pero ninguno público para el usuario
 *
 * Created by jmarranz on 8/07/14.
 */
public interface ItsNatDocPublic
{
    public void downloadScript(String src);
}
