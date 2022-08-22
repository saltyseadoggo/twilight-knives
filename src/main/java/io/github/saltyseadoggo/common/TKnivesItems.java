package io.github.saltyseadoggo.common;

import io.github.saltyseadoggo.TwilightKnives;
import io.github.saltyseadoggo.common.item.FieryKnifeItem;
import io.github.saltyseadoggo.common.item.IceKnifeItem;
import io.github.saltyseadoggo.common.item.KnightmetalKnifeItem;
import io.github.saltyseadoggo.common.item.PreenchantedKnifeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.init.TFItems;
import twilightforest.util.TwilightItemTier;
import vectorwing.farmersdelight.common.registry.ModEnchantments;

import java.util.function.Supplier;

    //Registers our knives.
    //The following class in Delightful was referenced to create this: https://github.com/brdle/Delightful/blob/1.18.2/src/main/java/net/brdle/delightful/common/item/DelightfulItems.java

public class TKnivesItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TwilightKnives.MODID);

    //Farmer's Delight knives' damage and attack speed referenced from the Delightful class linked above.
    //As a reminder, the damage is added to the base damage of the tool tiers.
    public static final float KNIFE_DAMAGE = 0.5F;
    public static final float KNIFE_ATK_SPEED = -2.0F;
    public static final CreativeModeTab TAB = TFItems.creativeTab;

    //Preenchanted knives (ironwood & steeleaf)
    //"Why does the ironwood one use `() -> Enchantments.UNBREAKING` instead of just `Enchantments.UNBREAKING`?"
    //To fix a crash related to trying to use Farmer's Delight's Backstabbing enchantment before it was registered,
    //I had to make `registerPreenchantedKnife` use `Supplier<Enchantment>` instead of Enchantment.
    //`() ->` turns the `Enchantments.UNBREAKING` into a supplier.
    //See `registerPreenchantedKnife` for more info.
    public static final RegistryObject<Item> IRONWOOD_KNIFE = registerPreenchantedKnife
            ("ironwood_knife", TwilightItemTier.IRONWOOD, () -> Enchantments.UNBREAKING, 2);
    public static final RegistryObject<Item> STEELEAF_KNIFE = registerPreenchantedKnife
            ("steeleaf_knife", TwilightItemTier.STEELEAF, ModEnchantments.BACKSTABBING, 2);

    //Knives with their own classes to mimic special functionalities of TF tools made of the same materials
    public static final RegistryObject<Item> KNIGHTMETAL_KNIFE = registerKnightmetalKnife
            ("knightmetal_knife", TwilightItemTier.KNIGHTMETAL);
    public static final RegistryObject<Item> FIERY_KNIFE = registerFieryKnife
            ("fiery_knife", TwilightItemTier.FIERY);
    public static final RegistryObject<Item> ICE_KNIFE = registerIceKnife
            ("ice_knife", TwilightItemTier.ICE);



    //"Why does this method take `Supplier<Enchantment>` instead of just `Enchantment`?"
    //While the vanilla `Enchantments` class stores `Enchantment`s in its fields, which can be directly used in the knife class to apply enchantments,
    //Farmer's Delight's equivalent, `ModEnchantments`, stores `RegistryObject<Enchantment>` in its fields instead.
    //As we can see here: https://docs.minecraftforge.net/en/latest/concepts/registries/#referencing-registered-objects
    //we have to use .get() to turn a `RegistryObject<Enchantment>` into an `Enchantment` that can be applied to the knives.
    //I originally had this method take `Enchantment` and used `.get()` in the steeleaf knife line above, but that crashed the game.
    //This is because Farmer's Delight's enchantments aren't registered when this class registers items. See https://stackoverflow.com/a/73164590
    //Referencing that stack overflow post, I had this method and the `PreenchantedKnifeItem` class take `RegistryObject`s,
    //and had the `PreenchantedKnifeItem` class run `.get()` right when the enchantment is about to be applied.
    //Apparently, that's late enough for Farmer's Delight's enchantments to have registered, because the crash stops, and the enchantment is applied.~
    //Also, we can say `Supplier<Enchantment>` here instead of `RegistryObject<Enchantment>` because `RegistryObject` implements `Supplier`.
    public static RegistryObject<Item> registerPreenchantedKnife(String name, Tier tier, Supplier<Enchantment> enchant, int level) {
        return registerItem(name, () -> new PreenchantedKnifeItem(tier, KNIFE_DAMAGE, KNIFE_ATK_SPEED, enchant, level,
                (new Item.Properties()).tab(TAB)));
    }

    public static RegistryObject<Item> registerFieryKnife(String name, Tier tier) {
        return registerItem(name, () -> new FieryKnifeItem(tier, KNIFE_DAMAGE, KNIFE_ATK_SPEED,
                (new Item.Properties()).tab(TAB).fireResistant().rarity(Rarity.UNCOMMON)));
    }

    public static RegistryObject<Item> registerKnightmetalKnife(String name, Tier tier) {
        return registerItem(name, () -> new KnightmetalKnifeItem(tier, KNIFE_DAMAGE, KNIFE_ATK_SPEED,
                (new Item.Properties()).tab(TAB)));
    }

    public static RegistryObject<Item> registerIceKnife(String name, Tier tier) {
        return registerItem(name, () -> new IceKnifeItem(tier, KNIFE_DAMAGE, KNIFE_ATK_SPEED,
                (new Item.Properties()).tab(TAB)));
    }

    public static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

    public static void create(IEventBus bus) {
        ITEMS.register(bus);
    }
}
