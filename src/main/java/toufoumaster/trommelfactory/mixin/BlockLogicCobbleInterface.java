package toufoumaster.trommelfactory.mixin;


import net.minecraft.core.block.BlockLogicCobble;
import net.minecraft.core.item.IItemConvertible;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Supplier;

@Mixin(value = BlockLogicCobble.class, remap = false)
public interface BlockLogicCobbleInterface {

	@Accessor
	void setCrushDrop(@Nullable Supplier<? extends IItemConvertible> value);

}
