package toufoumaster.trommelfactory.mixin;

import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityTrommel;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryTrommel;
import net.minecraft.core.entity.monster.MobSlime;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.core.block.entity.TileEntityTrommel.ResultEntry;
import turniplabs.halplibe.helper.RecipeBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static toufoumaster.trommelfactory.TrommelFactory.MOD_ID;

@Mixin(value = TileEntityTrommel.class, remap = false)
public class TileEntityTrommelMixin {

	@Shadow
	private ItemStack[] itemStacks;

	@Final
	@Shadow
	private Random random;

	@Unique
	private List<ItemStack> getItemResult(ItemStack slotItem) {
		List<ItemStack> list = new ArrayList<>();
		for(RecipeEntryTrommel recipe : Registries.RECIPES.getAllTrommelRecipes()) {
			if ((recipe.getInput()).matches(slotItem)) {
				ItemStack resultStack = ((WeightedRandomLootObject) ((WeightedRandomBag<?>) recipe.getOutput()).getRandom()).getItemStack();

				for (ItemStack stack : RecipeBuilder.getItemGroup(MOD_ID, "clay_mixtures")) {
					if (stack.itemID == slotItem.itemID) {
						if (random.nextInt(10) == 0) {
							list.add(Items.CLAY.getDefaultStack());
						}
						break;
					}
				}
				for (ItemStack stack : RecipeBuilder.getItemGroup(MOD_ID, "sand_mixtures")) {
					if (stack.itemID == slotItem.itemID) {
						if (random.nextInt(10) == 0) {
							list.add(Blocks.SAND.getDefaultStack());
						}
						break;
					}
				}
				if (resultStack == null) continue;
				if (resultStack.stackSize == 0) continue;
				list.add(resultStack);
			}
		}
		return list;
	}

	@Inject(method = "simulateTrommelDrops(ILnet/minecraft/core/item/ItemStack;)V", at=@At("HEAD"), cancellable = true)
	private void simulateTrommelDrops(int passes, ItemStack blockToTest, CallbackInfo ci) {
		HashMap<Object, ResultEntry> trommelDropMap = new HashMap<>();
		System.out.println();
		System.out.println("Generating simulation of Trommel loot for block: " + blockToTest.getItem().namespaceID);

		for (ItemStack itemStack : this.getItemResult(blockToTest)) {
			for (int i = 0; i < passes; ++i) {
				if (itemStack != null) {
					Item item = itemStack.getItem();
					if (!trommelDropMap.containsKey(item.namespaceID)) {
						trommelDropMap.put(item.namespaceID, new ResultEntry(item));
					}

					( trommelDropMap.get(item.namespaceID)).addStack(itemStack);
				} else {
					if (!trommelDropMap.containsKey("NULL")) {
						trommelDropMap.put("NULL", new ResultEntry(null));
					}

					(trommelDropMap.get("NULL")).addStack(null);
				}
			}
		}

		System.out.printf("Trommel loot sampling %s (%.2f stacks) passes:%n", passes, (float)passes / 64.0F);

		for(Item item : Item.itemsList) {
			if (item != null) {
				ResultEntry entry = trommelDropMap.get(item.namespaceID);
				if (entry != null) {
					System.out.println(entry);
				}
			}
		}

		if (trommelDropMap.get("NULL") != null) {
			System.out.println(trommelDropMap.get("NULL"));
		}
		ci.cancel();
	}

	@Inject(method = "sieveItem(I)V", at=@At("HEAD"), cancellable = true)
	public void sieveItem(int slotIndex, CallbackInfo ci) {
		if (((TileEntityTrommelInterface)this).invokeCanProduce(slotIndex)) {
			List<ItemStack> itemResults = this.getItemResult(this.itemStacks[slotIndex]);
			--this.itemStacks[slotIndex].stackSize;
			if (this.itemStacks[slotIndex].stackSize <= 0) {
				this.itemStacks[slotIndex] = null;
			}

			TileEntityTrommel tileEntity = (TileEntityTrommel)(Object)this;

			World worldObj = tileEntity.worldObj;
			CarriedBlock carriedBlock = tileEntity.carriedBlock;

			for (ItemStack itemResult : itemResults) {
				if (itemResult.stackSize <= 0) {
					continue;
				}
				int xOffset = 0;
				int zOffset = 0;
				if (worldObj != null) {
					int meta = worldObj.getBlockMetadata(tileEntity.x, tileEntity.y, tileEntity.z) & 7;
					if (meta == 2) {
						xOffset = -1;
					} else if (meta == 5) {
						zOffset = -1;
					} else if (meta == 3) {
						xOffset = 1;
					} else if (meta == 4) {
						zOffset = 1;
					}
				}

				int adjacentId = worldObj != null ? worldObj.getBlockId(tileEntity.x + xOffset, tileEntity.y, tileEntity.z + zOffset) : 0;
				Container chest = null;
				if (Block.hasLogicClass(Blocks.blocksList[adjacentId], BlockLogicChest.class)) {
					assert worldObj != null;

					chest = BlockLogicChest.getInventory(worldObj, tileEntity.x + xOffset, tileEntity.y, tileEntity.z + zOffset);
				}

				if (chest != null) {
					if (addToChest(chest, itemResult)) {
						continue;
					}
				}

				if (itemResult.stackSize > 0) {
					if (worldObj != null) {
						worldObj.dropItem(tileEntity.x, tileEntity.y, tileEntity.z, itemResult);
					} else if (carriedBlock != null) {
						carriedBlock.world.dropItem(MathHelper.floor(carriedBlock.holder.x), MathHelper.floor(carriedBlock.holder.y), MathHelper.floor(carriedBlock.holder.z), itemResult);
					}
				}
			}

			if (random.nextInt(4000) == 0) {
				float f = 0.125F;
				float f1 = 0.125F;
				MobSlime mobSlime = new MobSlime(carriedBlock != null ? carriedBlock.world : worldObj);
				mobSlime.setSlimeSize(1);
				float f3 = 0.05F;
				mobSlime.xd = ((float)random.nextGaussian() * f3);
				mobSlime.yd = ((float)random.nextGaussian() * f3 + 0.2F);
				mobSlime.zd = ((float)random.nextGaussian() * f3);
				if (worldObj != null) {
					mobSlime.moveTo((double)tileEntity.x + (double)f, (double)tileEntity.y + (double)1.0F, (double)tileEntity.z + (double)f1, random.nextFloat() * 360.0F, 0.0F);
					worldObj.entityJoinedWorld(mobSlime);
				} else if (carriedBlock != null) {
					mobSlime.moveTo(carriedBlock.holder.x + (double)f, carriedBlock.holder.y + (double)carriedBlock.holder.heightOffset, carriedBlock.holder.z + (double)f1, random.nextFloat() * 360.0F, 0.0F);
					carriedBlock.world.entityJoinedWorld(mobSlime);
				}
			}

		}
		ci.cancel();
	}

	@Unique
	private boolean addToChest(Container chest, ItemStack itemResult) {
		for(int i = 0; i < chest.getContainerSize(); ++i) {
			ItemStack slot = chest.getItem(i);
			if (slot != null && slot.itemID == itemResult.itemID && slot.getMetadata() == itemResult.getMetadata()) {
				while(slot.stackSize + 1 <= slot.getMaxStackSize()) {
					++slot.stackSize;
					chest.setItem(i, slot);
					--itemResult.stackSize;
					if (itemResult.stackSize <= 0) {
						return true;
					}
				}
			}
		}

		if (itemResult.stackSize <= 0) {
			return true;
		}

		for(int i = 0; i < chest.getContainerSize(); ++i) {
			ItemStack slot = chest.getItem(i);
			if (slot == null) {
				chest.setItem(i, itemResult);
				return true;
			}
		}
		return false;
	}
}
