package org.itsnat.droid.impl.stdalone;

import android.animation.Animator;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatResourcesImpl;
import org.itsnat.droid.impl.dom.ResourceDesc;
import org.itsnat.droid.impl.dom.ResourceDescLocal;
import org.itsnat.droid.impl.dom.ResourceDescRemote;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.xmlinflater.layout.stdalone.XMLInflaterLayoutStandalone;

/**
 * Created by jmarranz on 12/04/2016.
 */
public class ItsNatResourcesStandaloneImpl extends ItsNatResourcesImpl
{
    protected final XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone;
    protected final XMLDOMParserContext xmlDOMParserContext;

    public ItsNatResourcesStandaloneImpl(XMLInflaterLayoutStandalone xmlInflaterLayoutStandalone)
    {
        super(xmlInflaterLayoutStandalone.getInflatedLayoutStandaloneImpl().getItsNatDroidImpl().getXMLDOMRegistry(),
              xmlInflaterLayoutStandalone.getXMLInflaterContext(),
              xmlInflaterLayoutStandalone.getInflatedLayoutStandaloneImpl().getItsNatDroidImpl().getXMLInflaterRegistry());

        // En este caso PageImpl es null

        this.xmlInflaterLayoutStandalone = xmlInflaterLayoutStandalone;

        Context ctx = xmlInflaterLayoutStandalone.getContext();
        this.xmlDOMParserContext = new XMLDOMParserContext(xmlDOMRegistry,ctx);
    }

    public Context getContext()
    {
        return xmlInflaterLayoutStandalone.getContext();
    }

    private ResourceDesc checkRemote(ResourceDesc resourceDesc)
    {
        if (resourceDesc instanceof ResourceDescRemote)
            throw new ItsNatDroidException("Remote resource is not allowed in this context, use assets or intern files instead");
        return resourceDesc; // Puede ser ResourceDescLocal o "compiled"
    }

    @Override
    protected ResourceDesc prepare(String resourceDescValue,ResourceDesc resourceDesc)
    {
        if (resourceDesc == null) resourceDesc = ResourceDesc.create(resourceDescValue);
        resourceDesc = checkRemote(resourceDesc);
        if (resourceDesc instanceof ResourceDescLocal) // Puede ser "compiled" también
        {
            XMLDOMParser.prepareResourceDescLocalToLoadResource((ResourceDescLocal) resourceDesc, xmlDOMParserContext);
        }
        return resourceDesc;
    }

}
