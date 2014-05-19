package redgear.fluidessentia.core;

import redgear.core.fluids.FluidUtil;
import redgear.core.mod.ModUtils;
import redgear.fluidessentia.fluid.FluidAspect;
import thaumcraft.api.aspects.Aspect;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "redgear_fluidessentia", name = "Fluid Essentia", version = "@ModVersion@", dependencies = "required-after:redgear_core;required-after:Thaumcraft")
public class FluidEssentia extends ModUtils {

	@Instance("redgear_fluidessentia")
	public static ModUtils inst;
	private static final String texture = "essentia";
	
	
	@Override
	protected void PreInit(FMLPreInitializationEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void Init(FMLInitializationEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void PostInit(FMLPostInitializationEvent event) {
		for(Aspect asp : Aspect.aspects.values())
			FluidUtil.createFluid(new FluidAspect(asp), texture);
	}

	//Don't touch these!

	@Override
	@EventHandler
	public void PreInitialization(FMLPreInitializationEvent event) {
		super.PreInitialization(event);
	}

	@Override
	@EventHandler
	public void Initialization(FMLInitializationEvent event) {
		super.Initialization(event);
	}

	@Override
	@EventHandler
	public void PostInitialization(FMLPostInitializationEvent event) {
		super.PostInitialization(event);
	}

}
