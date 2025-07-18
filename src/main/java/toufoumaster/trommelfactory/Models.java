package toufoumaster.trommelfactory;

import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import static toufoumaster.trommelfactory.Blocks.*;
import static toufoumaster.trommelfactory.TrommelFactory.MOD_ID;

public class Models implements ModelEntrypoint {

	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {
		ModelHelper.setBlockModel(BASALT_GRAVEL, () -> new BlockModelStandard<>(BASALT_GRAVEL)
			.setAllTextures(0, MOD_ID+":block/basalt_gravel"));
		ModelHelper.setBlockModel(LIMESTONE_GRAVEL, () -> new BlockModelStandard<>(LIMESTONE_GRAVEL)
			.setAllTextures(0, MOD_ID+":block/limestone_gravel"));
		ModelHelper.setBlockModel(GRANITE_GRAVEL, () -> new BlockModelStandard<>(GRANITE_GRAVEL)
			.setAllTextures(0, MOD_ID+":block/granite_gravel"));

		ModelHelper.setBlockModel(FLINT_CLAY_MIXTURE, () -> new BlockModelStandard<>(FLINT_CLAY_MIXTURE)
			.setAllTextures(0, MOD_ID+":block/stone_clay_mixture"));
		ModelHelper.setBlockModel(OLIVINE_CLAY_MIXTURE, () -> new BlockModelStandard<>(OLIVINE_CLAY_MIXTURE)
			.setAllTextures(0, MOD_ID+":block/basalt_clay_mixture"));
		ModelHelper.setBlockModel(QUARTZ_CLAY_MIXTURE, () -> new BlockModelStandard<>(QUARTZ_CLAY_MIXTURE)
			.setAllTextures(0, MOD_ID+":block/granite_clay_mixture"));

		ModelHelper.setBlockModel(FLINT_SAND_MIXTURE, () -> new BlockModelStandard<>(FLINT_SAND_MIXTURE)
			.setAllTextures(0, MOD_ID+":block/stone_sand_mixture"));
		ModelHelper.setBlockModel(OLIVINE_SAND_MIXTURE, () -> new BlockModelStandard<>(OLIVINE_SAND_MIXTURE)
			.setAllTextures(0, MOD_ID+":block/basalt_sand_mixture"));
		ModelHelper.setBlockModel(QUARTZ_SAND_MIXTURE, () -> new BlockModelStandard<>(QUARTZ_SAND_MIXTURE)
			.setAllTextures(0, MOD_ID+":block/granite_sand_mixture"));
	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {

	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
