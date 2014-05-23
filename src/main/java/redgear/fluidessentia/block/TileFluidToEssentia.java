package redgear.fluidessentia.block;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;

public class TileFluidToEssentia extends TileAbstractEssentiaInterface {

	public TileFluidToEssentia() {
		super(10);

	}

	@Override
	protected boolean doPreWork() {
		if (tank.getFluidAmount() >= fluidRate) {
			IEssentiaTransport jar = findJar();

			if (jar != null) {
				Aspect fluidAspect = getAspect();
				//get amount of aspects we could give. 
				int amount = tank.getFluidAmount() / fluidRate;
				//Paranoid double check, should never happen.
				if (fluidAspect == null || amount < 1)
					return false;
				//Get amount the jar can actually take.
				int taken = jar.addEssentia(fluidAspect, amount, ForgeDirection.UP);
				//Drain the amount of fluid the jar took.
				tank.drain(taken * fluidRate, true);

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
		return false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return getDirection() == face;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {

	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return null;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return 0;
	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		if (aspect == getAspect() && face == getDirection()) {
			//How much fluid they want
			int want = amount * fluidRate;
			//fake drain that amount of fluid to see how much we could give
			FluidStack canGiveStack = tank.drain(want, false);
			//turn that fluid stack back into the amount of Essentia we could give.
			int canGive = canGiveStack == null ? 0 : canGiveStack.amount / fluidRate;

			//and turn that BACK into fluid to drain it for real. The reason for the extra steps is to deal with possible fluid fractions.
			tank.drain(canGive * fluidRate, true);
			//return the essentia amount we calculated.
			return canGive;
		}
		return 0;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
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
