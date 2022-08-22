package io.github.saltyseadoggo.common.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.function.Supplier;

//This class is used for knives that should have an enchantment by default, matching TF's ironwood and steeleaf items.

public class PreenchantedKnifeItem extends KnifeItem {

    Supplier<Enchantment> enchant;
    int level;

    public PreenchantedKnifeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Supplier<Enchantment> enchant, int level, Properties properties) {
        super (tier, attackDamageIn, attackSpeedIn, properties);
        this.enchant = enchant;
        this.level = level;
    }

    //Code copied from https://github.com/TeamTwilight/twilightforest/blob/1.19.x/src/main/java/twilightforest/item/IronwoodSwordItem.java
    //to apply a default enchantment, like all of the ironwood and steeleaf items have.
    //I modified it to take an enchantment and level for said enchantment from the constructor.
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        if (this.allowedIn(tab)) {
            ItemStack istack = new ItemStack(this);
            istack.enchant(enchant.get(), level);
            list.add(istack);
        }

    }
}
