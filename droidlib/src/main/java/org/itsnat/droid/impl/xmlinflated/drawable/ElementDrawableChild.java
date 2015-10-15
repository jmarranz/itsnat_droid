package org.itsnat.droid.impl.xmlinflated.drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public abstract class ElementDrawableChild extends ElementDrawable
{
    protected ElementDrawable parentElementDrawable;

    protected ElementDrawableChild(ElementDrawable parentElementDrawable)
    {
        this.parentElementDrawable = parentElementDrawable;
    }

    public ElementDrawable getParentElementDrawable()
    {
        return parentElementDrawable;
    }

    public void setParentElementDrawable(ElementDrawable parentElement)
    {
        this.parentElementDrawable = parentElement;
    }

    public ElementDrawableRoot getElementDrawableRoot()
    {
        return parentElementDrawable.getElementDrawableRoot();
    }


}
