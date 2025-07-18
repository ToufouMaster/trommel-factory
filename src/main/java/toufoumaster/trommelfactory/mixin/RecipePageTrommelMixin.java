package toufoumaster.trommelfactory.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.gui.TooltipElement;
import net.minecraft.client.gui.guidebook.SlotGuidebook;
import net.minecraft.client.gui.guidebook.trommeling.RecipePageTrommel;
import net.minecraft.client.option.enums.DescriptionPromptEnum;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.TextureManager;
import net.minecraft.core.WeightedRandomBag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryTrommel;
import net.minecraft.core.lang.I18n;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = RecipePageTrommel.class, remap = false)
public class RecipePageTrommelMixin {

	@Final
	@Shadow
	private ItemElement itemElement;

	@Shadow
	public List<SlotGuidebook> slots;

	@Final
	@Shadow
	private static Minecraft mc;

	@Final
	@Shadow
	private TooltipElement tooltipElement;

	@Inject(method = "renderForeground(Lnet/minecraft/client/render/TextureManager;Lnet/minecraft/client/render/Font;IIIIF)V", at=@At("HEAD"), cancellable = true)
	protected void renderForeground(TextureManager re, Font fr, int x, int y, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
		RecipePageTrommel recipePage = (RecipePageTrommel)(Object)this;
		List<?> recipes = ((RecipePageInterface)this).getRecipes();

		if (recipes.isEmpty()) {
			recipePage.drawStringCenteredNoShadow(fr, I18n.getInstance().translateKey("guidebook.section.search.error.no_recipes"), x + 79, y + 110, -8355712);
		}

		SlotGuidebook mouseOverSlot = null;

		for(SlotGuidebook slot : this.slots) {
			recipePage.drawSlot(x + slot.x - 1, y + slot.y - 1, -1);
			boolean dontrender = false;

			if (slot.recipe instanceof RecipeEntryTrommel && slot.item != null && slot.isOutput) {
				WeightedRandomBag<WeightedRandomLootObject> loot = ((RecipeEntryTrommel)slot.recipe).getOutput();
				int index = slot.index;
				if (slot.recipeAmount > 8) {
					index = slot.index + 9 * slot.recipeIndex;
				}

				WeightedRandomLootObject lootObject = loot.getEntries().get(index);
				if (lootObject.isRandomYield()) {
					if (lootObject.getMinYield() == 0 && lootObject.getMaxYield() == 0) {
						dontrender = true;
					}
				} else {
					if (lootObject.getFixedYield() == 0) {
						dontrender = true;
					}
				}
			}

			if (recipePage.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
				mouseOverSlot = slot;
			}
			if (!dontrender) {
				this.itemElement.render(slot.getItemStack(), x + slot.x, y + slot.y, mouseOverSlot == slot, slot);
			} else {
				this.itemElement.render(null, x + slot.x, y + slot.y, mouseOverSlot == slot, slot);
			}
		}
		ci.cancel();
	}

	@Inject(method = "renderOverlay(Lnet/minecraft/client/render/TextureManager;Lnet/minecraft/client/render/Font;IIIIF)V", at=@At("HEAD"), cancellable = true)
	protected void renderOverlay(TextureManager re, Font fr, int x, int y, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
		RecipePageTrommel recipePage = (RecipePageTrommel)(Object)this;
		SlotGuidebook mouseOverSlot = null;

		for(SlotGuidebook slot : this.slots) {
			boolean dontrender = false;

			if (slot.recipe instanceof RecipeEntryTrommel && slot.item != null && slot.isOutput) {
				WeightedRandomBag<WeightedRandomLootObject> loot = ((RecipeEntryTrommel)slot.recipe).getOutput();
				int index = slot.index;
				if (slot.recipeAmount > 8) {
					index = slot.index + 9 * slot.recipeIndex;
				}

				WeightedRandomLootObject lootObject = loot.getEntries().get(index);
				if (lootObject.isRandomYield()) {
					if (lootObject.getMinYield() == 0 && lootObject.getMaxYield() == 0) {
						dontrender = true;
					}
				} else {
					if (lootObject.getFixedYield() == 0) {
						dontrender = true;
					}
				}
			}

			if (!dontrender) {
				if (recipePage.getIsMouseOverSlot(slot, x, y, mouseX, mouseY)) {
					mouseOverSlot = slot;
				}

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				if (mouseOverSlot != null && mouseOverSlot.hasItem()) {
					boolean showDescription = DescriptionPromptEnum.showDescription(mc);
					String str = tooltipElement.getTooltipText(mouseOverSlot.getItemStack(), showDescription, mouseOverSlot);
					if (!str.isEmpty()) {
						tooltipElement.render(str, mouseX, mouseY, 8, -8);
					}
				}
			}
		}
		ci.cancel();
	}
}
