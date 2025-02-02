package net.id.paradiselost.mixin.server;

import net.id.paradiselost.blocks.ParadiseLostBlocks;
import net.id.paradiselost.world.dimension.ParadiseLostBiomes;
import net.id.paradiselost.world.dimension.ParadiseLostDimension;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.FlatLevelGeneratorPreset;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorLayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

/**
 * Responsible for adding the Paradise Lost superflat preset.
 */
@Mixin(targets = "net/minecraft/world/gen/FlatLevelGeneratorPresets$Registrar")
public abstract class FlatLevelGeneratorPresets$RegistrarMixin {
    @Shadow protected abstract RegistryEntry<FlatLevelGeneratorPreset> createAndRegister(RegistryKey<FlatLevelGeneratorPreset> registryKey, ItemConvertible icon, RegistryKey<Biome> biome, Set<RegistryKey<StructureSet>> structureSets, boolean hasFeatures, boolean hasLakes, FlatChunkGeneratorLayer... layers);
    
    @Inject(
        method = "initAndGetDefault",
        at = @At(
            value = "FIELD",
            opcode = Opcodes.GETSTATIC,
            target = "Lnet/minecraft/world/gen/FlatLevelGeneratorPresets;THE_VOID:Lnet/minecraft/util/registry/RegistryKey;"
        )
    )
    private void initAndGetDefault(CallbackInfoReturnable<RegistryEntry<FlatLevelGeneratorPreset>> cir) {
        createAndRegister(
            ParadiseLostDimension.SUPERFLAT_PRESET,
            ParadiseLostBlocks.GRASS_BLOCK,
            ParadiseLostBiomes.HIGHLANDS_PLAINS_KEY,
            Set.of(StructureSetKeys.VILLAGES),
            false,
            false,
            new FlatChunkGeneratorLayer(1, ParadiseLostBlocks.GRASS_BLOCK),
            new FlatChunkGeneratorLayer(2, ParadiseLostBlocks.DIRT),
            new FlatChunkGeneratorLayer(1, Blocks.BEDROCK)
        );
    }
}
