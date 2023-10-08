package com.buuz135.hotornot.object.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.Tier;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static com.buuz135.hotornot.HotOrNot.HOTORNOT_TAB;
import static com.buuz135.hotornot.HotOrNot.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public final class HONItems {

	private static ImmutableList<Item> allSimpleItems;

	public static ImmutableList<Item> getAllSimpleItems() {
		return allSimpleItems;
	}

	@SubscribeEvent
	public static void registerItem(final Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		final Builder<Item> simpleItems = ImmutableList.builder();

		simpleItems.add(register(registry, "wooden_tongs",
				new ItemHotHolder(Tier.TIER_0)
						.setMaxDamage(200)));

		simpleItems.add(register(registry, "mitts",
				new ItemHotHolder(Tier.TIER_II)
						.setMaxDamage(5_000)));

		for (final Metal metal : TFCRegistries.METALS.getValuesCollection()) {
			// Only make tongs for metals that make tools
			if (!metal.isToolMetal()) continue;

			simpleItems.add(register(registry, "metal/tongs/" + metal, new ItemMetalTongs(metal)));
			simpleItems.add(register(registry, "metal/tongs_head/" + metal, new ItemMetalTongsHead(metal)));
		}

		allSimpleItems = simpleItems.build();
	}

	private static <T extends Item> T register(final IForgeRegistry<Item> r, final String name, final T item) {
		item.setRegistryName(MOD_ID, name);
		item.setTranslationKey(MOD_ID + "." + name.replace('/', '.'));
		item.setCreativeTab(HOTORNOT_TAB);
		r.register(item);
		return item;
	}
}