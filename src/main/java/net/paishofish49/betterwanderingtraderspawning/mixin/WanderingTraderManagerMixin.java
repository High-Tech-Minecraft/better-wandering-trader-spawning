package net.paishofish49.betterwanderingtraderspawning.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.level.ServerWorldProperties;
import net.paishofish49.betterwanderingtraderspawning.BetterWanderingTraderSpawning;
import net.paishofish49.betterwanderingtraderspawning.MyConfigProvider;
import net.paishofish49.betterwanderingtraderspawning.SimpleConfig;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static java.lang.Math.min;

@Mixin(WanderingTraderManager.class)
public class WanderingTraderManagerMixin {
    @Unique private int freshSpawnDelay = 24000;
    @Unique private int freshSpawnTimer = 1200;
    @Shadow private final Random random = Random.create();
    @Final @Shadow private ServerWorldProperties properties;
    @Shadow private int spawnTimer;
    @Shadow private int spawnDelay;
    @Shadow private int spawnChance;
    @Shadow private boolean trySpawn(ServerWorld world) {return false;};

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInitialize(ServerWorldProperties properties, CallbackInfo ci) {
        SimpleConfig config = SimpleConfig.of(BetterWanderingTraderSpawning.MOD_ID + "config").provider(new MyConfigProvider()).request();
        freshSpawnDelay = config.getOrDefault("wandering.trader.spawn.delay", 24000);
        freshSpawnTimer = config.getOrDefault("wandering.trader.spawn.timer", 1200);
        spawnDelay = min(spawnDelay, freshSpawnDelay);
        spawnTimer = min(spawnTimer, freshSpawnTimer);
    }

    /**
     * @author PaiShoFish49
     * @reason this method has a lot of hardcoded values and it seems like a pain to try and tiptoe around them elegantly.
     * hell ill just re-write it yk. also it seems like a pretty small, inconsequential method.
     */
    @Overwrite
    public void spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (world.getGameRules().getBoolean(GameRules.DO_TRADER_SPAWNING)) {
            if (--this.spawnTimer <= 0) {
                this.spawnTimer = freshSpawnTimer;
                this.spawnDelay -= freshSpawnTimer;
                this.properties.setWanderingTraderSpawnDelay(this.spawnDelay);
                if (this.spawnDelay <= 0) {
                    this.spawnDelay = freshSpawnDelay;
                    if (world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
                        int i = this.spawnChance;
                        this.spawnChance = MathHelper.clamp(this.spawnChance + 25, 25, 75);
                        this.properties.setWanderingTraderSpawnChance(this.spawnChance);
                        if (this.random.nextInt(100) <= i) {
                            if (this.trySpawn(world)) {
                                this.spawnChance = 25;
                            }

                        }
                    }
                }
            }
        }
    }
}
