package redgear.fluidessentia.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;
import redgear.core.world.WorldLocation;
import redgear.fluidessentia.fluid.EssentiaTank;
import redgear.fluidessentia.fluid.FluidAspect;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;

public abstract class TileAbstractEssentiaInterface extends TileEntityTank implements IEssentiaTransport {

	public static final int fluidRate = 125; //1 essentia = 125 mb
	AdvFluidTank tank;

	public TileAbstractEssentiaInterface(int idleRate) {
		super(idleRate);
		tank = new EssentiaTank(1000);
		tank.addFluidMap(-1, TransferRule.BOTH);
		this.addTank(tank);
	}

	protected IEssentiaTransport findJar() {
		TileEntity tile = new WorldLocation(this).translate(getDirection(), 1).getTile();
		try {
			Class<?> jar = Class.forName("thaumcraft.common.tiles.TileJarFillable");
			if (tile instanceof IEssentiaTransport && tile.getClass().isAssignableFrom(jar))
				return (IEssentiaTransport) tile;
			else
				return null;

		} catch (ClassNotFoundException e) {
			return null;
		}

	}

	protected boolean isEssentia(FluidStack fluid) {
		return fluid != null && fluid.getFluid() instanceof FluidAspect;
	}

	protected Aspect getAspect() {
		return getAspect(tank.getFluid());
	}

	protected Aspect getAspect(FluidStack fluid) {
		if (isEssentia(fluid))
			return ((FluidAspect) fluid.getFluid()).aspect;
		else
			return null;
	}
}
