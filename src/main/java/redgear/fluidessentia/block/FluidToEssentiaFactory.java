package redgear.fluidessentia.block;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;

public class FluidToEssentiaFactory implements ITileFactory{
	
	static{
		GameRegistry.registerTileEntity(TileFluidToEssentia.class, "TileFluidToEssentia");
	}

	@Override
	public TileEntity createTile() {
		return new TileFluidToEssentia();
	}

	@Override
	public boolean hasGui() {
		return false;
	}

	@Override
	public int guiId() {
		return 0;
	}

	@Override
	public Object createGui(InventoryPlayer inventoryPlayer, TileEntity tile) {
		return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		return null;
	}

}
