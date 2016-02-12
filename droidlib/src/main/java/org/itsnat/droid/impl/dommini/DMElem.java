package org.itsnat.droid.impl.dommini;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jmarranz on 10/02/2016.
 */
public class DMElem extends DMNode
{
    protected String name;
    protected LinkedList<DMAttr> attributeList; // Serán anecdóticos (lo normal es ninguno y quizás uno o dos)
    protected ArrayList<DMNode> childList;

    public DMElem(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public List<DMAttr> getDMAttrList()
    {
        return attributeList;
    }

    public void addDMAttr(DMAttr attr)
    {
        if (attributeList == null) this.attributeList = new LinkedList<DMAttr>();
        attributeList.add(attr);
    }

    public List<DMNode> getChildDMNodeList()
    {
        return childList;
    }

    public void addChildDMNode(DMNode node)
    {
        if (childList == null) this.childList = new ArrayList<DMNode>();
        childList.add(node);
    }
}
