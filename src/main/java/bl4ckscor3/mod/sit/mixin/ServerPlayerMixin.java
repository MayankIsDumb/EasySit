//? if >=26.1 {
/*package bl4ckscor3.mod.sit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bl4ckscor3.mod.sit.LayEntity;
import bl4ckscor3.mod.sit.SitEntity;
import net.minecraft.server.level.ServerPlayer;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin
{
	@Inject(method = "sendBuildLimitMessage", at = @At("HEAD"), cancellable = true)
	private void sit$suppressBuildLimitMessage(boolean isTooHigh, int height, CallbackInfo ci)
	{
		ServerPlayer player = (ServerPlayer)(Object)this;

		if (player.getVehicle() instanceof SitEntity || player.getVehicle() instanceof LayEntity)
			ci.cancel();
	}
}
*///?}
