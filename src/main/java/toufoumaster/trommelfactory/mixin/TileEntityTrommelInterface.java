package toufoumaster.trommelfactory.mixin;

import net.minecraft.core.block.entity.TileEntityTrommel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = TileEntityTrommel.class, remap = false)
public interface TileEntityTrommelInterface {

	@Invoker("canProduce")
	boolean invokeCanProduce(int slot);
}
