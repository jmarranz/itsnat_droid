package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.View;
import android.view.ViewGroup;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.layout._IncludeFakeViewGroup_;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.ArrayList;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDesc_Include_layout extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDesc_Include_layout(ClassDescViewBased parent)
    {
        super(parent,"layout");
    }

    @Override
    public void setAttribute(final View view,final DOMAttr attr,final AttrLayoutContext attrCtx)
    {
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {

                _IncludeFakeViewGroup_ viewIncludeFake = (_IncludeFakeViewGroup_)view;

                ViewGroup viewParent = (ViewGroup)view.getParent();

                int indexOfInclude = viewParent.indexOfChild(viewIncludeFake);

                viewParent.removeViewAt(indexOfInclude); // Eliminamos el falso View auxiliar que substituye al <include> y que está recien insertado
                if (viewIncludeFake.getParent() != null) throw MiscUtil.internalError();

                XMLInflaterLayout xmlInflater = attrCtx.getXMLInflaterLayout();

                ArrayList<DOMAttr> includeAttribs = ((_IncludeFakeViewGroup_)view).getAttribs();

                //int countBefore = viewParent.getChildCount();
                View resView = getViewLayout(attr.getResourceDesc(), xmlInflater, viewParent, indexOfInclude, includeAttribs);
                if (resView != viewParent) throw new ItsNatDroidException("Internal Error"); // Es así, ten en cuenta que el layout incluido puede ser un <merge> con varios views, si viewParent es no nulo se devuelve viewParent, idem que en el inflado nativo

                // Test para ver que se ha insertado si fue al final (eliminar en el futuro):
                /*
                int countAfter = viewParent.getChildCount();
                View[] childList = new View[countAfter - countBefore];
                int j = 0;
                for(int i = countBefore; i < countAfter; i++)
                {
                    childList[j] = viewParent.getChildAt(i);
                    j++;
                }
                */
            }
        };
        PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        if (pendingViewPostCreateProcess != null)
            pendingViewPostCreateProcess.addPendingPostAddViewTask(task);
        else
            task.run();
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "@null", attrCtx);
    }

}
