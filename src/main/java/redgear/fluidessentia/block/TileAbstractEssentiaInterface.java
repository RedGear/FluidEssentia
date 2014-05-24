package redgear.fluidessentia.block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.api.tile.IFacedTile;
import redgear.core.api.util.FacedTileHelper;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;
import redgear.core.world.WorldLocation;
import redgear.fluidessentia.fluid.EssentiaTank;
import redgear.fluidessentia.fluid.FluidAspect;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;

public abstract class TileAbstractEssentiaInterface extends TileEntityTank implements IEssentiaTransport, IFacedTile {

	public static final int fluidRate = 125; //1 essentia = 125 mb
	ForgeDirection face;
	AdvFluidTank tank;

	public TileAbstractEssentiaInterface(int idleRate) {
		super(idleRate);
		tank = new EssentiaTank(1000);
		tank.addFluidMap(-1, TransferRule.BOTH);
		addTank(tank);
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

	@Override
	public int getDirectionId() {
		return face.ordinal();
	}

	@Override
	public ForgeDirection getDirection() {
		return face;
	}

	@Override
	public boolean setDirection(int id) {
		if (id >= 0 && id < 6) {
			face = ForgeDirection.getOrientation(id);
			return true;
		} else
			return false;
	}

	@Override
	public boolean setDirection(ForgeDirection side) {
		face = side;
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		face = FacedTileHelper.facePlayer(entity);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setByte("face", (byte) face.ordinal());
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		face = ForgeDirection.getOrientation(tag.getByte("face"));
	}
}
