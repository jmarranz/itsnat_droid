package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.inputmethod.EditorInfo;

import org.itsnat.droid.impl.util.MapSmart;

/**
 * Created by jmarranz on 28/07/14.
 */
public class ImeOptionsUtil
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create(16);
    static
    {
        nameValueMap.put("normal", 0x00000000);
        nameValueMap.put("actionUnspecified", EditorInfo.IME_NULL); // Es también 0 como "normal"
        nameValueMap.put("actionNone", EditorInfo.IME_ACTION_NONE);
        nameValueMap.put("actionGo", EditorInfo.IME_ACTION_GO);
        nameValueMap.put("actionSearch", EditorInfo.IME_ACTION_SEARCH);
        nameValueMap.put("actionSend", EditorInfo.IME_ACTION_SEND);
        nameValueMap.put("actionNext", EditorInfo.IME_ACTION_NEXT);
        nameValueMap.put("actionDone", EditorInfo.IME_ACTION_DONE);
        nameValueMap.put("actionPrevious", EditorInfo.IME_ACTION_PREVIOUS);
        nameValueMap.put("flagNoFullscreen", EditorInfo.IME_FLAG_NO_FULLSCREEN);
        nameValueMap.put("flagNavigatePrevious", EditorInfo.IME_FLAG_NAVIGATE_PREVIOUS);
        nameValueMap.put("flagNavigateNext", EditorInfo.IME_FLAG_NAVIGATE_NEXT);
        nameValueMap.put("flagNoExtractUi", EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        nameValueMap.put("flagNoAccessoryAction", EditorInfo.IME_FLAG_NO_ACCESSORY_ACTION);
        nameValueMap.put("flagNoEnterAction", EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        // shapeValueMap.put("flagForceAscii",EditorInfo.IME_FLAG_FORCE_ASCII);  // API level 16
    }
}
