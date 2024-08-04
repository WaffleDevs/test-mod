package com.waffledevs.rainmaker.item;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Objects;

public class SummonStorm extends Item {

    private static final Logger LOGGER = LogUtils.getLogger();

    public SummonStorm(Properties pProperties) {
        super(pProperties);
    }
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        LOGGER.info("Is Raining: {}", pLevel.isRaining());
        LOGGER.info("LD Is Raining: {}", pLevel.getLevelData().isRaining());
        LOGGER.info("RainLevel: {}", pLevel.rainLevel);
        LOGGER.info("GetRainLevel: {}", pLevel.getRainLevel(1.0F));
        LOGGER.info("Is Thundering: {}", pLevel.isThundering());
        LOGGER.info("ThunderLevel: {}", pLevel.thunderLevel);
        LOGGER.info("GetThunderLevel: {}", pLevel.getThunderLevel(1.0f));
        if(!pLevel.isThundering()){
            if (!pLevel.isClientSide()) {
                Objects.requireNonNull(pLevel.getServer()).overworld().setWeatherParameters(0, ServerLevel.THUNDER_DURATION.sample(Objects.requireNonNull(pLevel.getServer()).overworld().getRandom()), true, true);
            }
            itemstack.hurtAndBreak(1, pPlayer, EquipmentSlot.MAINHAND);
            pPlayer.setItemInHand(pUsedHand, itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }
}
