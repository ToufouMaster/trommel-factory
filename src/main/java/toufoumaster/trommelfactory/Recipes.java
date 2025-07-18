package toufoumaster.trommelfactory;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderTrommel;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static net.minecraft.core.block.Blocks.*;
import static net.minecraft.core.item.Items.*;
import static toufoumaster.trommelfactory.Blocks.*;
import static toufoumaster.trommelfactory.TrommelFactory.MOD_ID;

public class Recipes implements RecipeEntrypoint {
	@Override
	public void onRecipesReady() {
		RecipeBuilder.ModifyTrommel("minecraft", "gravel").deleteRecipe();
		RecipeBuilder.ModifyTrommel("minecraft", "sand").deleteRecipe();
		RecipeBuilder.ModifyTrommel("minecraft", "clay").deleteRecipe();
		RecipeBuilder.ModifyTrommel("minecraft", "dirt").deleteRecipe();

		// Mixture Crafts
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(new ItemStack(CLAY, 1))
			.addInput(new ItemStack(CLAY, 1))
			.addInput(new ItemStack(FLINT, 1))
			.addInput(new ItemStack(FLINT, 1))
			.create("flint_clay_mixture", FLINT_CLAY_MIXTURE.getDefaultStack());
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(new ItemStack(CLAY, 1))
			.addInput(new ItemStack(CLAY, 1))
			.addInput(new ItemStack(OLIVINE, 1))
			.addInput(new ItemStack(OLIVINE, 1))
			.create("olivine_clay_mixture", OLIVINE_CLAY_MIXTURE.getDefaultStack());
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(new ItemStack(CLAY, 1))
			.addInput(new ItemStack(CLAY, 1))
			.addInput(new ItemStack(QUARTZ, 1))
			.addInput(new ItemStack(QUARTZ, 1))
			.create("quartz_clay_mixture", QUARTZ_CLAY_MIXTURE.getDefaultStack());
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(new ItemStack(SAND, 1))
			.addInput(new ItemStack(SAND, 1))
			.addInput(new ItemStack(FLINT, 1))
			.addInput(new ItemStack(FLINT, 1))
			.create("flint_sand_mixture", FLINT_SAND_MIXTURE.getDefaultStack());
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(new ItemStack(SAND, 1))
			.addInput(new ItemStack(SAND, 1))
			.addInput(new ItemStack(OLIVINE, 1))
			.addInput(new ItemStack(OLIVINE, 1))
			.create("olivine_sand_mixture", OLIVINE_SAND_MIXTURE.getDefaultStack());
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(new ItemStack(SAND, 1))
			.addInput(new ItemStack(SAND, 1))
			.addInput(new ItemStack(QUARTZ, 1))
			.addInput(new ItemStack(QUARTZ, 1))
			.create("quartz_sand_mixture", QUARTZ_SAND_MIXTURE.getDefaultStack());


		// Gravel
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(GRAVEL.getDefaultStack())
			.addEntry(new WeightedRandomLootObject(FLINT.getDefaultStack(), 1), 1)
			.create("stone_to_flint");
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(BASALT_GRAVEL.getDefaultStack())
			.addEntry(new WeightedRandomLootObject(OLIVINE.getDefaultStack(), 1), 1)
			.create("basalt_to_olivine");
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(LIMESTONE_GRAVEL.getDefaultStack())
			.addEntry(new WeightedRandomLootObject(SAND.getDefaultStack(), 1), 1)
			.create("limestone_to_sand");
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(GRANITE_GRAVEL.getDefaultStack())
			.addEntry(new WeightedRandomLootObject(QUARTZ.getDefaultStack(), 1), 1)
			.create("granite_to_quartz");

		// Clay
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(FLINT_CLAY_MIXTURE.getDefaultStack())
			.addEntry(new WeightedRandomLootObject(ORE_RAW_IRON.getDefaultStack(), 1), 25)
			.addEntry(new WeightedRandomLootObject(new ItemStack(AMMO_PEBBLE, 1), 0), 75)
			.create("flint_to_iron");
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(OLIVINE_CLAY_MIXTURE.getDefaultStack())
			.addEntry(new WeightedRandomLootObject(ORE_RAW_GOLD.getDefaultStack(), 1), 10)
			.addEntry(new WeightedRandomLootObject(new ItemStack(AMMO_PEBBLE, 1), 0), 90)
			.create("olivine_to_gold");
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(QUARTZ_CLAY_MIXTURE.getDefaultStack())
			.addEntry(new WeightedRandomLootObject(DIAMOND.getDefaultStack(), 1), 1)
			.addEntry(new WeightedRandomLootObject(new ItemStack(AMMO_PEBBLE, 1), 0), 99)
			.create("quartz_to_diamond");

		// Sand
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(FLINT_SAND_MIXTURE.getDefaultStack())
			.addEntry(new WeightedRandomLootObject(new ItemStack(DYE, 1, 4), 1, 2), 5)
			.addEntry(new WeightedRandomLootObject(new ItemStack(AMMO_PEBBLE, 1), 0), 95)
			.create("flint_to_lapis");
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(OLIVINE_SAND_MIXTURE.getDefaultStack())
			.addEntry(new WeightedRandomLootObject(COAL.getDefaultStack(), 1, 2), 35)
			.addEntry(new WeightedRandomLootObject(new ItemStack(AMMO_PEBBLE, 1), 0), 65)
			.create("olivine_to_coal");
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(QUARTZ_SAND_MIXTURE.getDefaultStack())
			.addEntry(new WeightedRandomLootObject(DUST_REDSTONE.getDefaultStack(), 3, 6), 10)
			.addEntry(new WeightedRandomLootObject(new ItemStack(AMMO_PEBBLE, 1), 0), 90)
			.create("quartz_to_redstone");

		// Vanilla
		RecipeBuilderTrommel dirtBuilder = RecipeBuilder.Trommel(MOD_ID)
			.setInput("minecraft:dirt");
		int seedAmount = 0;
		for (ItemStack itemStack : RecipeBuilder.getItemGroup(MOD_ID, "trommel_seeds")) {
			dirtBuilder.addEntry(new WeightedRandomLootObject(itemStack, 0, 1), 1);
			seedAmount++;
		}
		dirtBuilder.addEntry(new WeightedRandomLootObject(AMMO_PEBBLE.getDefaultStack(), 0), seedAmount);
		dirtBuilder.create("dirt_to_seed");
	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
		RecipeBuilder.getRecipeNamespace(MOD_ID);

		RecipeBuilder.addItemsToGroup(MOD_ID, "clay_mixtures",
			FLINT_CLAY_MIXTURE,
			OLIVINE_CLAY_MIXTURE,
			QUARTZ_CLAY_MIXTURE
		);

		RecipeBuilder.addItemsToGroup(MOD_ID, "sand_mixtures",
			FLINT_SAND_MIXTURE,
			OLIVINE_SAND_MIXTURE,
			QUARTZ_SAND_MIXTURE
		);

		RecipeBuilder.addItemsToGroup(MOD_ID, "trommel_seeds",
			SEEDS_WHEAT,
			SEEDS_PUMPKIN
		);
	}
}
