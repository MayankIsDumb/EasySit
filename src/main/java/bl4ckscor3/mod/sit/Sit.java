package bl4ckscor3.mod.sit;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?} else {
/*import net.minecraft.resources.ResourceLocation;
*///?}
//? if >=1.21.6 {
import net.minecraft.world.entity.EntitySpawnReason;
//?}
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.AABB;

public class Sit implements ModInitializer {
	//@formatter:off
	//? if >=1.21.11 {
	public static final EntityType<SitEntity> SIT_ENTITY_TYPE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			Identifier.fromNamespaceAndPath("sit", "entity_sit"),
			EntityType.Builder.<SitEntity>of(SitEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("sit", "entity_sit")))
	);

	public static final EntityType<LayEntity> LAY_ENTITY_TYPE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			Identifier.fromNamespaceAndPath("sit", "entity_lay"),
			EntityType.Builder.<LayEntity>of(LayEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("sit", "entity_lay")))
	);
	//?} else if >=1.21.6 {
	/*public static final EntityType<SitEntity> SIT_ENTITY_TYPE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath("sit", "entity_sit"),
			EntityType.Builder.<SitEntity>of(SitEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("sit", "entity_sit")))
	);

	public static final EntityType<LayEntity> LAY_ENTITY_TYPE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath("sit", "entity_lay"),
			EntityType.Builder.<LayEntity>of(LayEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("sit", "entity_lay")))
	);
	*///?} else {
	/*public static final EntityType<SitEntity> SIT_ENTITY_TYPE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath("sit", "entity_sit"),
			EntityType.Builder.<SitEntity>of(SitEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build("entity_sit")
	);

	public static final EntityType<LayEntity> LAY_ENTITY_TYPE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath("sit", "entity_lay"),
			EntityType.Builder.<LayEntity>of(LayEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build("entity_lay")
	);
	*///?}
	//@formatter:on

	@Override
	public void onInitialize() {
		AutoConfig.register(SitConfig.class, JanksonConfigSerializer::new);

		// /sit command - sit on the block you're standing on
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("sit").executes(context -> {
				ServerPlayer player = context.getSource().getPlayerOrException();

				if (SitUtil.isPlayerSitting(player))
					return 0;

				//check block at player's feet first (catches slabs/stairs), then the one below
				BlockPos feetPos = player.blockPosition();
				BlockState feetState = player.level().getBlockState(feetPos);
				BlockPos pos = isHalfBlock(feetState) ? feetPos : feetPos.below();
				BlockState state = player.level().getBlockState(pos);
				double yOffset = isHalfBlock(state) ? 0.5D : 1.0D;

				//? if >=26.1 {
				/*if (pos.getY() + yOffset >= player.level().getMinY() + player.level().getHeight())
					return 0;
				*///?}

				//? if >=1.21.6 {
				SitEntity sit = SIT_ENTITY_TYPE.create(player.level(), EntitySpawnReason.SPAWN_ITEM_USE);
				sit.absSnapTo(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
				//?} else {
				/*SitEntity sit = SIT_ENTITY_TYPE.create(player.level());
				sit.setPos(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
				*///?}

				if (SitUtil.addSitEntity(player.level(), pos, sit, player.position())) {
					player.level().addFreshEntity(sit);
					player.startRiding(sit);
					return Command.SINGLE_SUCCESS;
				}

				return 0;
			}));
		});

		// /lay command - lay down on the block you're standing on
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("lay").executes(context -> {
				ServerPlayer player = context.getSource().getPlayerOrException();

				if (SitUtil.isPlayerSitting(player) || SitUtil.isPlayerLaying(player))
					return 0;

				BlockPos feetPos = player.blockPosition();
				BlockState feetState = player.level().getBlockState(feetPos);
				BlockPos pos = isHalfBlock(feetState) ? feetPos : feetPos.below();
				BlockState state = player.level().getBlockState(pos);
				double yOffset = isHalfBlock(state) ? 0.55D : 1.05D;

				//? if >=26.1 {
				/*if (pos.getY() + yOffset >= player.level().getMinY() + player.level().getHeight())
					return 0;
				*///?}

				//? if >=1.21.6 {
				LayEntity lay = LAY_ENTITY_TYPE.create(player.level(), EntitySpawnReason.SPAWN_ITEM_USE);
				lay.absSnapTo(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
				//?} else {
				/*LayEntity lay = LAY_ENTITY_TYPE.create(player.level());
				lay.setPos(pos.getX() + 0.5D, pos.getY() + yOffset, pos.getZ() + 0.5D);
				*///?}

				if (SitUtil.addLayEntity(player.level(), pos, lay, player.position())) {
					player.level().addFreshEntity(lay);
					player.startRiding(lay);
					return Command.SINGLE_SUCCESS;
				}

				return 0;
			}));
		});

		//right-click sit handling - works on any block
		UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
			if (level.isClientSide() || !level.mayInteract(player, hitResult.getBlockPos()) || player.isShiftKeyDown() || SitUtil.isPlayerSitting(player) || SitUtil.isPlayerLaying(player) || hitResult.getDirection() != Direction.UP)
				return InteractionResult.PASS;

			BlockPos hitPos = hitResult.getBlockPos();
			BlockState s = level.getBlockState(hitPos);
			Block b = s.getBlock();

			if (!isPlayerInRange(player, hitPos) || SitUtil.isOccupied(level, hitPos) || !player.getItemInHand(hand).isEmpty())
				return InteractionResult.PASS;

			//only allow sitting on stairs and slabs
			if (!(b instanceof StairBlock) && !(b instanceof SlabBlock))
				return InteractionResult.PASS;

			//only allow bottom-half stairs and slabs
			if (b instanceof SlabBlock && s.getProperties().contains(SlabBlock.TYPE) && s.getValue(SlabBlock.TYPE) != SlabType.BOTTOM)
				return InteractionResult.PASS;
			if (b instanceof StairBlock && s.getProperties().contains(StairBlock.HALF) && s.getValue(StairBlock.HALF) != Half.BOTTOM)
				return InteractionResult.PASS;

			double yOffset = isHalfBlock(s) ? 0.5D : 1.0D;

			//? if >=26.1 {
			/*if (hitPos.getY() + yOffset >= level.getMinY() + level.getHeight())
				return InteractionResult.PASS;
			*///?}

			//? if >=1.21.6 {
			SitEntity sit = SIT_ENTITY_TYPE.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
			sit.absSnapTo(hitPos.getX() + 0.5D, hitPos.getY() + yOffset, hitPos.getZ() + 0.5D);
			//?} else {
			/*SitEntity sit = SIT_ENTITY_TYPE.create(level);
			sit.setPos(hitPos.getX() + 0.5D, hitPos.getY() + yOffset, hitPos.getZ() + 0.5D);
			*///?}

			if (SitUtil.addSitEntity(level, hitPos, sit, player.position())) {
				level.addFreshEntity(sit);
				player.startRiding(sit);
				return InteractionResult.SUCCESS;
			}

			return InteractionResult.PASS;
		});
		PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
			if (!level.isClientSide()) {
				SitEntity sitEntity = SitUtil.getSitEntity(level, pos);

				if (sitEntity != null) {
					SitUtil.removeSitEntity(level, pos);
					sitEntity.ejectPassengers();
					sitEntity.discard();
				}

				LayEntity layEntity = SitUtil.getLayEntity(level, pos);

				if (layEntity != null) {
					SitUtil.removeLayEntity(level, pos);
					layEntity.ejectPassengers();
					layEntity.discard();
				}
			}
		});
	}

	/**
	 * Returns whether the block is a half-block (bottom slab or bottom stair)
	 */
	private static boolean isHalfBlock(BlockState state) {
		Block block = state.getBlock();

		if (block instanceof SlabBlock && state.getProperties().contains(SlabBlock.TYPE) && state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM)
			return true;
		if (block instanceof StairBlock && state.getProperties().contains(StairBlock.HALF) && state.getValue(StairBlock.HALF) == Half.BOTTOM)
			return true;

		return false;
	}

	/**
	 * Returns whether the player is close enough to the block to be able to sit on it
	 *
	 * @param player The player
	 * @param pos The position of the block to sit on
	 * @return true if the player is close enough, false otherwise
	 */
	private static boolean isPlayerInRange(Player player, BlockPos pos) {
		BlockPos playerPos = player.blockPosition();
		int blockReachDistance = AutoConfig.getConfigHolder(SitConfig.class).getConfig().blockReachDistance;

		if (blockReachDistance == 0) //player has to stand on top of the block
			return playerPos.getY() - pos.getY() <= 1 && playerPos.getX() - pos.getX() == 0 && playerPos.getZ() - pos.getZ() == 0;

		AABB range = new AABB(pos.getX() + blockReachDistance, pos.getY() + blockReachDistance, pos.getZ() + blockReachDistance, pos.getX() - blockReachDistance, pos.getY() - blockReachDistance, pos.getZ() - blockReachDistance);

		return range.minX <= playerPos.getX() && range.minY <= playerPos.getY() && range.minZ <= playerPos.getZ() && range.maxX >= playerPos.getX() && range.maxY >= playerPos.getY() && range.maxZ >= playerPos.getZ();
	}
}
