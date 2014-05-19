package redgear.fluidessentia.fluid;

import net.minecraftforge.fluids.Fluid;
import thaumcraft.api.aspects.Aspect;

public class FluidAspect extends Fluid {

	public final Aspect aspect;

	public FluidAspect(Aspect aspect) {
		super("fluid" + aspect.getName());
		this.aspect = aspect;
	}

	@Override
	public int getColor() {
		return aspect.getColor();
	}
}
