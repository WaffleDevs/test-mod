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

public class ClearWeather extends Item {

    private static final Logger LOGGER = LogUtils.getLogger();

    public ClearWeather(Properties pProperties) {
        super(pProperties);
    }
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        LOGGER.info("Right clicker");
        if(pLevel.isRaining() || pLevel.isThundering()){
            if (!pLevel.isClientSide()){
                Objects.requireNonNull(pLevel.getServer()).overworld().setWeatherParameters(0, ServerLevel.RAIN_DELAY.sample(Objects.requireNonNull(pLevel.getServer()).overworld().getRandom()), false, false);

            }
            itemstack.hurtAndBreak(1, pPlayer, EquipmentSlot.MAINHAND);
            pPlayer.setItemInHand(pUsedHand, itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }
}
