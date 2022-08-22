package io.github.saltyseadoggo.common;

import io.github.saltyseadoggo.TwilightKnives;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TwilightKnives.MODID)
public class ToolEvents {

        //Copypasted & adjusted code from https://github.com/TeamTwilight/twilightforest/blob/1.19.x/src/main/java/twilightforest/events/ToolEvents.java
        //to deal bonus damage to unarmored entities if they are hit by a Knightmetal Knife
    private static final int KNIGHTMETAL_BONUS_DAMAGE = 2;

    @SubscribeEvent
    public static void onKnightmetalToolDamage(LivingHurtEvent event) {
        LivingEntity target = (LivingEntity) event.getEntity();

        if (!target.getLevel().isClientSide() && event.getSource().getDirectEntity() instanceof LivingEntity living) {
            ItemStack weapon = living.getMainHandItem();

            if (!weapon.isEmpty()) {
                if (target.getArmorValue() == 0 && weapon.is(TKnivesItems.KNIGHTMETAL_KNIFE.get())) {
                    event.setAmount(event.getAmount() + KNIGHTMETAL_BONUS_DAMAGE);
                    // enchantment attack sparkles
                    ((ServerLevel) target.getLevel()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
                }
            }
        }
    }
}
