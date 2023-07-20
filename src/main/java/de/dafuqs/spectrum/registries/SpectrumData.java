package de.dafuqs.spectrum.registries;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.minecraft.registry.*;

public class SpectrumData implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(SpectrumDynamicRegistryProvider::new);
	}
	
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, SpectrumWorldgen::bootstrapPlacedFeatures);
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, SpectrumWorldgen::bootstrapConfiguredFeatures);
	}
}