package org.itsnat.droid.impl.xmlinflater.menu;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildGroup;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildMenu;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildRoot;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildSubMenu;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescMenuMgr extends ClassDescMgr<ClassDescResourceBased>
{
    public ClassDescMenuMgr(XMLInflaterRegistry parent)
    {
        super(parent);
        initClassDesc();
    }

    @Override
    public ClassDescResourceBased get(String classOrDOMElemName)
    {
        return classes.get(classOrDOMElemName);
    }


    @Override
    protected void initClassDesc()
    {
        ClassDescElementMenuChildRoot rootMenu = new ClassDescElementMenuChildRoot(this);
        addClassDesc(rootMenu); // Se registra como "builtin-menu"

        ClassDescElementMenuChildMenu menu = new ClassDescElementMenuChildMenu(this);
        //addClassDesc(menu); // <menu> es siempre SubMenu excepto en el caso root <menu>

        ClassDescElementMenuChildSubMenu subMenu = new ClassDescElementMenuChildSubMenu(this,menu);
        addClassDesc(subMenu); // <menu> es siempre SubMenu excepto en el caso root <menu>

        ClassDescElementMenuChildGroup group = new ClassDescElementMenuChildGroup(this);
        addClassDesc(group);

        ClassDescElementMenuChildMenuItem menuItem = new ClassDescElementMenuChildMenuItem(this);
        addClassDesc(menuItem);
    }
}
