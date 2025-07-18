package toufoumaster.trommelfactory;

import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import toufoumaster.trommelfactory.mixin.BlockLogicCobbleInterface;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.BlockInitEntrypoint;

import static toufoumaster.trommelfactory.TrommelFactory.MOD_ID;
import static net.minecraft.core.block.Blocks.*;

public class Blocks implements BlockInitEntrypoint {

	private static int startingID = Config.CFG.getInt("IDs.startingBlockID");

	public static Block<?> BASALT_GRAVEL;
	public static Block<?> LIMESTONE_GRAVEL;
	public static Block<?> GRANITE_GRAVEL;

	public static Block<?> FLINT_CLAY_MIXTURE;
	public static Block<?> OLIVINE_CLAY_MIXTURE;
	public static Block<?> QUARTZ_CLAY_MIXTURE;

	public static Block<?> FLINT_SAND_MIXTURE;
	public static Block<?> OLIVINE_SAND_MIXTURE;
	public static Block<?> QUARTZ_SAND_MIXTURE;

	@Override
	public void afterBlockInit() {
		((BlockLogicCobbleInterface)COBBLE_BASALT.getLogic()).setCrushDrop(() -> BASALT_GRAVEL);
		((BlockLogicCobbleInterface)COBBLE_LIMESTONE.getLogic()).setCrushDrop(() -> LIMESTONE_GRAVEL);
		((BlockLogicCobbleInterface)COBBLE_GRANITE.getLogic()).setCrushDrop(() -> GRANITE_GRAVEL);

		BlockBuilder gravelBuilder = new BlockBuilder(MOD_ID)
			.setHardness(GRAVEL.getHardness())
			.setResistance(GRAVEL.blastResistance)
			.setBlockSound(BlockSounds.GRAVEL)
			.addTags(BlockTags.MINEABLE_BY_SHOVEL);

		BASALT_GRAVEL = gravelBuilder.build("basalt_gravel", "basalt_gravel", startingID++, b -> new BlockLogicGravel(b));
		LIMESTONE_GRAVEL = gravelBuilder.build("limestone_gravel", "limestone_gravel", startingID++, b -> new BlockLogicGravel(b));
		GRANITE_GRAVEL = gravelBuilder.build("granite_gravel", "granite_gravel", startingID++, b -> new BlockLogicGravel(b));

		BlockBuilder clayBuilder = new BlockBuilder(MOD_ID)
			.setHardness(BLOCK_CLAY.getHardness())
			.setResistance(BLOCK_CLAY.blastResistance)
			.setBlockSound(BlockSounds.GRAVEL)
			.addTags(BlockTags.MINEABLE_BY_SHOVEL);

		FLINT_CLAY_MIXTURE = clayBuilder.build("flint_clay_mixture", "flint_clay_mixture", startingID++, b -> new BlockLogic(b, Material.clay));
		OLIVINE_CLAY_MIXTURE = clayBuilder.build("olivine_clay_mixture", "olivine_clay_mixture", startingID++, b -> new BlockLogic(b, Material.clay));
		QUARTZ_CLAY_MIXTURE = clayBuilder.build("quartz_clay_mixture", "quartz_clay_mixture", startingID++, b -> new BlockLogic(b, Material.clay));

		BlockBuilder sandBuilder = new BlockBuilder(MOD_ID)
			.setHardness(SAND.getHardness())
			.setResistance(SAND.blastResistance)
			.setBlockSound(BlockSounds.SAND)
			.addTags(BlockTags.MINEABLE_BY_SHOVEL);

		FLINT_SAND_MIXTURE = sandBuilder.build("flint_sand_mixture", "flint_sand_mixture", startingID++, b -> new BlockLogicSand(b));
		OLIVINE_SAND_MIXTURE = sandBuilder.build("olivine_sand_mixture", "olivine_sand_mixture", startingID++, b -> new BlockLogicSand(b));
		QUARTZ_SAND_MIXTURE = sandBuilder.build("quartz_sand_mixture", "quartz_sand_mixture", startingID++, b -> new BlockLogicSand(b));

	}

}
