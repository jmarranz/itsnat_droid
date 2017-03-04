package org.itsnat.droid.impl.stdalone;

import android.content.Context;

import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.ItsNatDoc;
import org.itsnat.droid.ItsNatDroid;
import org.itsnat.droid.UserData;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.util.UniqueIdGenerator;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutStandaloneImpl;

/**
 * Created by jmarranz on 28/02/2017.
 */

public class InflatedLayoutImpl implements InflatedLayout
{
    protected ItsNatDroidImpl itsNatDroid;
    protected ItsNatDocStandaloneImpl itsNatDoc;
    protected InflatedXMLLayoutStandaloneImpl inflatedXMLLayoutStandalone;
    //protected Interpreter interp = new Interpreter(); // Global
    protected UniqueIdGenerator idGenerator = new UniqueIdGenerator();
    protected UserData userData;

    public InflatedLayoutImpl(ItsNatDroidImpl itsNatDroid,InflatedXMLLayoutStandaloneImpl inflatedXMLLayoutStandalone)
    {
        this.itsNatDroid = itsNatDroid;
        this.inflatedXMLLayoutStandalone = inflatedXMLLayoutStandalone;

//        String uniqueIdForInterpreter = idGenerator.generateId("i"); // i = interpreter
//        this.interp = new Interpreter(new StringReader(""), System.out, System.err, false, new NameSpace(interp.getNameSpace(), uniqueIdForInterpreter)); // El StringReader está copiado del código fuente de beanshell2 https://code.google.com/p/beanshell2/source/browse/branches/v2.1/src/bsh/Interpreter.java

        // Definimos pronto el itsNatDoc para que los layout include tengan algún soporte de scripting de ItsNatDoc por ejemplo toast, eval, alert etc antes de inflarlos
        this.itsNatDoc = new ItsNatDocStandaloneImpl(this); // ¿¿¿¿¿Casi el último para que  InflatedLayoutImpl esté ya bien creado antes de inicializar  ItsNatDocImpl, el xmlInflaterLayoutPage siguiente necesita acceder a ItsNatDocImpl desde PageImpl?????

    }
    public ItsNatDocStandaloneImpl getItsNatDocImpl()
    {
        return itsNatDoc;
    }

    public ItsNatDoc getItsNatDoc()
    {
        return itsNatDoc;
    }

    @Override
    public Context getContext()
    {
        return inflatedXMLLayoutStandalone.getContext();
    }

    @Override
    public ItsNatDroid getItsNatDroid()
    {
        return itsNatDroid;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return itsNatDroid;
    }

    @Override
    public UserData getUserData()
    {
        return userData;
    }

    public InflatedXMLLayoutStandaloneImpl getInflatedXMLLayoutStandaloneImpl()
    {
        return inflatedXMLLayoutStandalone;
    }


}
