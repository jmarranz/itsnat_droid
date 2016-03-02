package org.itsnat.droid.impl.xmlinflated.layout;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageNotItsNat;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedLayoutPageNotItsNatImpl extends InflatedLayoutPageImpl
{
    /**
     * El parámetro page es de tipo PageImpl y NO de tipo PageNotItsNatImpl que es lo que aparentemente debería ser, y es porque en layouts referenciados por ej con el uso de < include >
     * obtenemos un nuevo layout via getLayout() que no crea un nuevo ParentImpl por lo que reutilizamos el ParentImpl del layout padre que puede ser tipo ItsNat o NotItsNat
     * de ahí que usemos el tipo base PageImpl
     *
     * @param page
     * @param itsNatDroid
     * @param domLayout
     * @param ctx
     */
    public InflatedLayoutPageNotItsNatImpl(PageImpl page, ItsNatDroidImpl itsNatDroid, XMLDOMLayoutPageNotItsNat domLayout, Context ctx)
    {
        super(page,itsNatDroid, domLayout, ctx);
    }

    public XMLDOMLayoutPageNotItsNat getXMLDOMLayoutPageNotItsNat()
    {
        return (XMLDOMLayoutPageNotItsNat)xmlDOM;
    }

}
