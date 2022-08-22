package io.github.saltyseadoggo.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;

public class KnightmetalKnifeItem extends KnifeItem {
    public KnightmetalKnifeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties properties) {
        super (tier, attackDamageIn, attackSpeedIn, properties);
    }

    //Code copied from https://github.com/TeamTwilight/twilightforest/blob/1.18.x/src/main/java/twilightforest/item/KnightmetalSwordItem.java
    //to display a tooltip telling the player that the knife deals bonus damage to unarmored entities
    //Text code changed in 1.19. When I port this to 1.19, reference the class in the link in the 1.19 branch for how to fix the error that will appear here.
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, world, list, flags);
        list.add(new TranslatableComponent(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
    }
}
