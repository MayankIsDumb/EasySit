package bl4ckscor3.mod.sit;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LayEntity extends Entity {
	public LayEntity(EntityType<? extends LayEntity> type, Level level) {
		super(type, level);
	}

	public LayEntity(Level level) {
		super(Sit.LAY_ENTITY_TYPE, level);
		noPhysics = true;
	}

	@Override
	public Vec3 getPassengerRidingPosition(Entity passenger) {
		return position().add(0.0, 0.0, 0.0);
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
		if (passenger instanceof Player player) {
			Vec3 resetPosition = SitUtil.getPreviousLayPosition(player, this);

			if (resetPosition != null) {
				discard();
				return resetPosition;
			}
		}

		discard();
		return super.getDismountLocationForPassenger(passenger);
	}

	@Override
	public void remove(RemovalReason reason) {
		super.remove(reason);
		SitUtil.removeLayEntity(level(), this);
	}

	@Override
	protected void defineSynchedData(Builder builder) {}

	//? if >=1.21.6 {
	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput nbt) {}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput nbt) {}
	//?} else {
	/*@Override
	public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag nbt) {}

	@Override
	public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag nbt) {}
	*///?}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
		return new ClientboundAddEntityPacket(this, serverEntity);
	}

	//? if >=1.21.6 {
	@Override
	public boolean hurtServer(net.minecraft.server.level.ServerLevel level, net.minecraft.world.damagesource.DamageSource source, float amount) {
		return false;
	}
	//?} else {
	/*@Override
	public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
		return false;
	}
	*///?}

	@Override
	public boolean shouldRender(double x, double y, double z) {
		return false;
	}
}
