package redgear.fluidessentia.fluid;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fluids.Fluid;
import thaumcraft.api.aspects.Aspect;

public class FluidAspect extends Fluid {

	public final Aspect aspect;
	
	private static final Map<Aspect, Fluid> aspectMap = new HashMap<Aspect, Fluid>();

	public FluidAspect(Aspect aspect) {
		super("fluid" + aspect.getName());
		this.aspect = aspect;
		aspectMap.put(aspect, this);
	}

	@Override
	public int getColor() {
		return aspect.getColor();
	}
	
	public static Fluid getFluid(Aspect aspect){
		return aspectMap.get(aspect);
	}
}
