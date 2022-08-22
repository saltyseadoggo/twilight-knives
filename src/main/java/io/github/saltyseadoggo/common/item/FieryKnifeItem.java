package io.github.saltyseadoggo.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;

public class FieryKnifeItem extends KnifeItem {
    public FieryKnifeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties properties) {
        super (tier, attackDamageIn, attackSpeedIn, properties);
    }

    //Copypasted code from https://github.com/TeamTwilight/twilightforest/blob/1.19.x/src/main/java/twilightforest/item/FierySwordItem.java
    //to make the knife unable to obtain Fire Aspect from the enchanting table and enchantment books.
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment != Enchantments.FIRE_ASPECT && super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return !EnchantmentHelper.getEnchantments(book).containsKey(Enchantments.FIRE_ASPECT) && super.isBookEnchantable(stack, book);
    }

    //Copypasted code from https://github.com/TeamTwilight/twilightforest/blob/1.19.x/src/main/java/twilightforest/item/FierySwordItem.java
    //to set attacked entities on fire
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (result && !target.getLevel().isClientSide() && !target.fireImmune()) {
            target.setSecondsOnFire(15);
        } else {
            for (int var1 = 0; var1 < 20; ++var1) {
                double px = target.getX() + target.getLevel().getRandom().nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                double py = target.getY() + target.getLevel().getRandom().nextFloat() * target.getBbHeight();
                double pz = target.getZ() + target.getLevel().getRandom().nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                target.getLevel().addParticle(ParticleTypes.FLAME, px, py, pz, 0.02, 0.02, 0.02);
            }
        }

        return result;
    }

    //Code copied from https://github.com/TeamTwilight/twilightforest/blob/1.18.x/src/main/java/twilightforest/item/KnightmetalSwordItem.java
    //to display a tooltip telling the player that the knife sets entities on fire.
    //I added a second tooltip line to explain the cooking effect it has on cut foods.
    //Text code changed in 1.19. When I port this to 1.19, reference the class in the link in the 1.19 branch for how to fix the error that will appear here.
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, world, list, flags);
        list.add(new TranslatableComponent(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
        list.add(new TranslatableComponent(getDescriptionId() + ".tooltip2").withStyle(ChatFormatting.GRAY));
    }
}
