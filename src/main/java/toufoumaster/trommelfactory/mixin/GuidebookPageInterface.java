package toufoumaster.trommelfactory.mixin;

import net.minecraft.client.gui.guidebook.GuidebookPage;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GuidebookPage.class, remap = false)
public interface GuidebookPageInterface {
}
