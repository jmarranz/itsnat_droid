package org.itsnat.droid.impl.xmlinflater.animinterp;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescInterpolatorMgr extends ClassDescMgr<ClassDescInterpolatorBased>
{
    public ClassDescInterpolatorMgr(XMLInflaterRegistry parent)
    {
        super(parent);
        initClassDesc();
    }

    @Override
    public ClassDescInterpolatorBased get(String resourceTypeName)
    {
        return classes.get(resourceTypeName);
    }


    @Override
    protected void initClassDesc()
    {

        //ClassDescAnimationSet set = new ClassDescAnimationSet(this,animation);
        //addClassDesc(set);


    }

    /*
301            if (name.equals("linearInterpolator")) {
302                interpolator = new LinearInterpolator(c, attrs);
303            } else if (name.equals("accelerateInterpolator")) {
304                interpolator = new AccelerateInterpolator(c, attrs);
305            } else if (name.equals("decelerateInterpolator")) {
306                interpolator = new DecelerateInterpolator(c, attrs);
307            }  else if (name.equals("accelerateDecelerateInterpolator")) {
308                interpolator = new AccelerateDecelerateInterpolator(c, attrs);
309            }  else if (name.equals("cycleInterpolator")) {
310                interpolator = new CycleInterpolator(c, attrs);
311            } else if (name.equals("anticipateInterpolator")) {
312                interpolator = new AnticipateInterpolator(c, attrs);
313            } else if (name.equals("overshootInterpolator")) {
314                interpolator = new OvershootInterpolator(c, attrs);
315            } else if (name.equals("anticipateOvershootInterpolator")) {
316                interpolator = new AnticipateOvershootInterpolator(c, attrs);
317            } else if (name.equals("bounceInterpolator")) {
318                interpolator = new BounceInterpolator(c, attrs);
319            } else {
320                throw new RuntimeException("Unknown interpolator name: " + parser.getName());
321            }

     */
}
