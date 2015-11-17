package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.attr.OrientationUtil;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodSingleName;

/**
 * El único motivo de hacer esta clase es porque el valor por defecto del orientation de LinearLayout es "vertical" en RadioGroup
 * y no horizontal, el método setOrientation es el de LinearLayout pero es llamado desde RadioGroup
 * Llamamos a getParentClassDescViewBased() en el constructor porque setOrientation está en LinearLayout (clase base de RadioGroup)
 *
 * Created by jmarranz on 6/10/14.
 */
public class AttrDescView_widget_RadioGroup_orientation extends AttrDescReflecMethodSingleName<Integer,ClassDescViewBased,View,AttrLayoutContext>
{
    @SuppressWarnings("unchecked")
    public AttrDescView_widget_RadioGroup_orientation(ClassDescViewBased parent)
    {
        super(parent,"orientation",int.class, OrientationUtil.valueMap,"vertical");

        // Redefinimos el atributo method para cambiar el Class contenedor a LinearLayout pues setOrientation está ahí no en RadioGroup
        this.method = new MethodContainer(parent.getParentClassDescViewBased().getDeclaredClass(),
                            method.getMethodName(),method.getParamClasses());
    }
}
