package redgear.fluidessentia.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import redgear.core.tile.TileEntityGeneric;
import redgear.core.world.WorldLocation;
import redgear.fluidessentia.fluid.FluidAspect;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;

public class EssentiaInterface extends TileEntityGeneric implements IFluidHandler {

	public static final int fluidRate = 125; //1 essentia = 125 mb

	private Aspect lock;
	private FluidStack buffer;

	private IEssentiaTransport findJar() {
		TileEntity tile = new WorldLocation(this).translate(getDirection(), 1).getTile();

		if (tile instanceof IEssentiaTransport)
			return (IEssentiaTransport) tile;
		else
			return null;
	}

	private boolean isEssentia(FluidStack fluid) {
		return fluid != null && fluid.getFluid() instanceof FluidAspect;
	}

	private Aspect getAspect(FluidStack fluid) {
		if (isEssentia(fluid))
			return ((FluidAspect) fluid.getFluid()).aspect;
		else
			return null;
	}

	/**
	 *
	 * @param jar
	 * @param input if true it finds input, if false it finds output.
	 * @return
	 */
	private ForgeDirection findWorkingSide(IEssentiaTransport jar, boolean input) {
		for (ForgeDirection side : ForgeDirection.values())
			if (input ? jar.canInputFrom(side) : jar.canOutputTo(side))
				return side;

		return null;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

		if (isEssentia(resource)) {
			IEssentiaTransport jar = findJar();

			if (jar != null) {
				Aspect inAsp = getAspect(resource);
				Aspect jarAsp = jar.getEssentiaType(getDirection().getOpposite());

				if ((lock == null || inAsp == lock) && (jarAsp == null || jarAsp == inAsp)) {
					ForgeDirection validSide = findWorkingSide(jar, true);

					if (validSide != null)
						if (buffer == null || buffer.amount < fluidRate) {
							int totalFluid = buffer == null ? 0 : buffer.amount + resource.amount;

							int aspects = totalFluid / fluidRate;

							if (aspects == 0) { // the total does not come to a whole aspect, so we take it all and store it in the buffer.
								if (buffer == null)
									buffer = resource.copy();
								else
									buffer.amount += resource.amount;

								lock = inAsp;
								return resource.amount;
							} else { //we have at least one whole aspect.
								int taken = jar.addEssentia(inAsp, aspects, validSide);

								if (taken > 0) {
									int fluidTaken = taken * fluidRate;

									if (buffer == null)
										return fluidTaken;
									else if (fluidTaken <= buffer.amount) {
										buffer.amount -= fluidTaken;
										return 0;
									}

									else {
										int bufferAmount = buffer.amount;
										buffer = null;
										lock = null;

										return fluidTaken - bufferAmount;
									}

								} else { //jar didn't take any but we have enough total to make at least one aspect. Take enough for one and leave the rest.
									int max = fluidRate - buffer.amount;
									buffer.amount += max;
									return max;
								}

							}

						} else //buffer is full
						{
							if (jar.addEssentia(lock, 1, validSide) == 1) { //try to empty full buffer
								buffer = null;// yay! Empty now.
								lock = null;
							}

							return 0; // but we won't take any more right now, even if the buffer is now empty.
						}

				}

			}
		}

		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		buffer.writeToNBT(tag);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		buffer = FluidStack.loadFluidStackFromNBT(tag);
		lock = getAspect(buffer);
	}
}
