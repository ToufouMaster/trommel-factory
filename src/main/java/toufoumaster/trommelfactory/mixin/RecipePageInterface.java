package toufoumaster.trommelfactory.mixin;

import net.minecraft.client.gui.guidebook.RecipePage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;


@Mixin(value = RecipePage.class, remap = false)
public interface RecipePageInterface {

		@Accessor
		List<?> getRecipes();
}
