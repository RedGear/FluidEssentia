package redgear.fluidessentia.block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import redgear.fluidessentia.core.FluidEssentia;
import redgear.fluidessentia.fluid.FluidAspect;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;

public class TileEssentiaToFluid extends TileAbstractEssentiaInterface {

	public TileEssentiaToFluid() {
		super(10);
	}

	@Override
	protected boolean doPreWork() {
		if (tank.getSpace() >= fluidRate) {
			IEssentiaTransport jar = findJar();

			if (jar != null) {
				Aspect jarAspect = jar.getEssentiaType(ForgeDirection.UP);
				int amount = jar.getEssentiaAmount(ForgeDirection.UP);

				if (jarAspect == null || amount < 1)
					return false;

				Fluid fluid = FluidAspect.getFluid(jarAspect);

				//Get the total amount of fluid that can be taken (tank takes into account the fluid type so we don't have to worry).
				int canTake = tank.fill(new FluidStack(fluid, amount * fluidRate), false);
				//translate that back into essentia and take it.
				int taken = jar.takeEssentia(jarAspect, canTake / fluidRate, ForgeDirection.UP);
				//turn that back into fluid and add it.
				tank.fill(new FluidStack(fluid, taken * fluidRate), true);

				//true if some fluid moved.
				return taken > 0;
			}

		}

		return false;
	}

	@Override
	protected int checkWork() {
		return 0;
	}

	@Override
	protected boolean doWork() {
		return false;
	}

	@Override
	protected boolean tryUseEnergy(int energy) {
		return true;
	}

	@Override
	protected boolean doPostWork() {
		return false;
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return getDirection() == face;
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return isConnectable(face);
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return false;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {

	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return getAspect();
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return 64;
	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return 0;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
		if (isConnectable(face)) {
			Fluid fluid = FluidAspect.getFluid(aspect);

			//get amount of fluid we could take by converting to fluid, fake filling
			int canTake = tank.fill(new FluidStack(fluid, amount * fluidRate), false);
			//translate back to essentia
			int taken = canTake / fluidRate;
			//actually fill the amount we can accept
			tank.fill(new FluidStack(fluid, taken * fluidRate), true);
			
			FluidEssentia.inst.logDebug("Fluid: ", fluid, " amount: ", amount, " canTake: ", canTake, " taken: ", taken);
			
			//return essentia amount taken.
			return taken;
		}
		else
			return 0;
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return getAspect();
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return tank.getFluidAmount() / fluidRate;
	}

	@Override
	public int getMinimumSuction() {
		return 0;
	}

	@Override
	public boolean renderExtendedTube() {
		return false;
	}
}
