package bl4ckscor3.mod.sit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bl4ckscor3.mod.sit.LayEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

@Mixin(Player.class)
public abstract class PlayerPoseMixin {
	@Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
	private void sit$forceLayPose(CallbackInfo ci) {
		Player self = (Player) (Object) this;

		if (self.getVehicle() instanceof LayEntity) {
			self.setPose(Pose.SLEEPING);
			ci.cancel();
		}
	}
}
