package bl4ckscor3.mod.sit.mixin;

import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.6 {
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bl4ckscor3.mod.sit.LayEntity;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
//?} else {
/*import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bl4ckscor3.mod.sit.LayEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
*///?}

//? if >=1.21.6 {
@Mixin(HumanoidMobRenderer.class)
public class HumanoidRendererMixin {
	@Inject(method = "extractHumanoidRenderState", at = @At("TAIL"))
	private static void sit$hidePassengerForLay(LivingEntity entity, HumanoidRenderState state, float partialTick, ItemModelResolver itemModelResolver, CallbackInfo ci) {
		if (entity instanceof Player player && player.getVehicle() instanceof LayEntity) {
			state.isPassenger = false;
		}
	}
}
//?} else {
/*@Mixin(LivingEntityRenderer.class)
public class HumanoidRendererMixin {
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z", ordinal = 0, shift = At.Shift.AFTER))
	private void sit$hidePassengerForLay(LivingEntity entity, float yaw, float partialTick, com.mojang.blaze3d.vertex.PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource bufferSource, int packedLight, CallbackInfo ci) {
		if (entity instanceof Player player && player.getVehicle() instanceof LayEntity) {
			LivingEntityRenderer self = (LivingEntityRenderer) (Object) this;
			EntityModel model = self.getModel();
			model.riding = false;
		}
	}
}
*///?}
