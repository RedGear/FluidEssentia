package redgear.fluidessentia.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;

public class EssentiaTank extends AdvFluidTank{

	/**
	 * Creates a new AdvFluidTank with the given capacity.
	 * 
	 * @param capacity Capacity of this tank in mb.
	 */
	public EssentiaTank(int capacity) {
		super(capacity);
	}

	/**
	 * Creates a new AdvFluidTank with the gives capacity and FluidStack.
	 * 
	 * @param fluid Fluid to fill the new tank with.
	 * @param amount Amount of fluid to fill.
	 * @param capacity Capacity of this tank in mb.
	 */
	public EssentiaTank(Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
	}

	/**
	 * Creates a new AdvFluidTank with the gives capacity and FluidStack.
	 * 
	 * @param stack FluidStack to fill the new tank with.
	 * @param capacity Capacity of this tank in mb.
	 */
	public EssentiaTank(FluidStack stack, int capacity) {
		super(stack, capacity);
	}
	
	
	
	/**
	 * @param fluidId Fluid ID to try to add
	 * @return True if this tank could accept this type of fluid through the
	 * fillWithMap() method
	 */
	@Override
	public boolean canAccept(int fluidId) {
		//This one shouldn't be called since canFillWithMap is also being overridden, but just to be safe ...
		return FluidRegistry.getFluid(fluidId) instanceof FluidAspect;
	}

	/**
	 * @param other FluidStack to try to add
	 * @return True if other could be added to this tank with the
	 * fillWithMap()
	 * method.
	 */
	@Override
	public boolean canFillWithMap(FluidStack other, boolean fully) {
		return other == null || other.getFluid() instanceof FluidAspect && canFill(other, fully);
	}

}
