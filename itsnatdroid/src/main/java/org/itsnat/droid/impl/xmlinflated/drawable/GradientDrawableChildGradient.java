package org.itsnat.droid.impl.xmlinflated.drawable;

import org.itsnat.droid.impl.xmlinflater.PercFloatImpl;

/**
 * Created by jmarranz on 27/11/14.
 */
public class GradientDrawableChildGradient extends ElementDrawableChildNormal
{
    protected Float angle;
    protected PercFloatImpl centerX;
    protected PercFloatImpl centerY;
    protected Integer centerColor;
    protected Integer endColor;
    protected PercFloatImpl gradientRadius;
    protected Integer startColor;
    protected Integer type;  // GradientTypeUtil.LINEAR_GRADIENT, RADIAL_GRADIENT, SWEEP_GRADIENT
    protected Boolean useLevel;


    public GradientDrawableChildGradient(ElementDrawableChildBase parentElementDrawable)
    {
        super(parentElementDrawable);
    }

    public Float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public PercFloatImpl getCenterX() {
        return centerX;
    }

    public void setCenterX(PercFloatImpl centerX) {
        this.centerX = centerX;
    }

    public PercFloatImpl getCenterY() {
        return centerY;
    }

    public void setCenterY(PercFloatImpl centerY) {
        this.centerY = centerY;
    }

    public Integer getCenterColor() {
        return centerColor;
    }

    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
    }

    public Integer getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public PercFloatImpl getGradientRadius() {
        return gradientRadius;
    }

    public void setGradientRadius(PercFloatImpl gradientRadius) {
        this.gradientRadius = gradientRadius;
    }

    public Integer getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Boolean getUseLevel() {
        return useLevel;
    }

    public void setUseLevel(boolean useLevel) {
        this.useLevel = useLevel;
    }
}
