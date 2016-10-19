package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescBitmapDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawable_BitmapDrawable_tileMode extends AttrDesc<ClassDescBitmapDrawable,BitmapDrawable,AttrDrawableContext>
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create( 4 );
    static
    {
        nameValueMap.put("disabled", -1);
        nameValueMap.put("clamp", 0);
        nameValueMap.put("repeat", 1);
        nameValueMap.put("mirror", 2);
    }

    public AttrDescDrawable_BitmapDrawable_tileMode(ClassDescBitmapDrawable parent, String name)
    {
        super(parent,name);
    }

    @Override
    public void setAttribute(BitmapDrawable target, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        int tileMode = AttrDesc.<Integer>parseSingleName(attr.getValue(), nameValueMap); // Valor concreto no puede ser un recurso

        Shader.TileMode tileModeObj;
        if (tileMode != -1)
        {
            switch (tileMode)
            {
                case 0:
                    tileModeObj = Shader.TileMode.CLAMP;
                    break;
                case 1:
                    tileModeObj = Shader.TileMode.REPEAT;
                    break;
                case 2:
                    tileModeObj = Shader.TileMode.MIRROR;
                    break;
                default: // Imposible este caso
                    tileModeObj = null;
                    break;
            }
        }
        else tileModeObj = null;

        String name = getName();
        if ("tileMode".equals(name))
            target.setTileModeXY(tileModeObj,tileModeObj);
        else if ("tileModeX".equals(name))
            target.setTileModeX(tileModeObj);
        else if ("tileModeY".equals(name))
            target.setTileModeY(tileModeObj);
    }

    @Override
    public void removeAttribute(BitmapDrawable target, AttrDrawableContext attrCtx)
    {
        setAttributeToRemove(target, "disabled",attrCtx);
    }
}
