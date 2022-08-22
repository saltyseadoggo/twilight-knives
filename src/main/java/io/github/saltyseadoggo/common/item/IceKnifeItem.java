package io.github.saltyseadoggo.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFMobEffects;
import twilightforest.init.TFParticleType;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;

public class IceKnifeItem extends KnifeItem {
    public IceKnifeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties properties) {
        super (tier, attackDamageIn, attackSpeedIn, properties);
    }

    //Code copied from https://github.com/TeamTwilight/twilightforest/blob/1.18.x/src/main/java/twilightforest/item/IceSwordItem.java
    //to apply the ice sword's chill effect to targets. Redundant casts were removed.
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (result) {
            target.addEffect(new MobEffectInstance(TFMobEffects.FROSTY.get(), 200, 2));

            for(int i = 0; i < 20; ++i) {
                ((ServerLevel)target.level).sendParticles(TFParticleType.SNOW.get(), target.getX(), target.getY() + (double)(target.getBbHeight() * 0.5F), target.getZ(), 1, (double)target.getBbWidth() * 0.5D, (double)target.getBbHeight() * 0.5D, (double)target.getBbWidth() * 0.5D, 0.0D);
            }
        }

        return result;
    }

    //Code copied from https://github.com/TeamTwilight/twilightforest/blob/1.19.x/src/main/java/twilightforest/item/KnightmetalSwordItem.java
    //to display a tooltip telling the player that the knife sets entities on fire.
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, world, list, flags);
        list.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
    }
}
