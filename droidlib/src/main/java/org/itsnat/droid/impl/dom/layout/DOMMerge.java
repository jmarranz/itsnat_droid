package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.DOMElement;

import java.util.LinkedList;

/**
 * Created by jmarranz on 27/10/14.
 */
public class DOMMerge extends DOMElement
{
    public DOMMerge(String name, DOMView parentElement)
    {
        super(name,parentElement);
    }

    public static void resolveFirstChildWhenIsMerge(LinkedList<DOMElement> childList)
    {
        if (childList == null || childList.size() != 1) return;

        DOMElement firstElem = childList.getFirst();
        if (firstElem instanceof DOMMerge)
        {
            childList.removeFirst();
            LinkedList<DOMElement> mergeChildList = firstElem.getChildDOMElementList();
            if (mergeChildList != null)
            {
                childList.addAll(mergeChildList);
            }
        }
    }
}
