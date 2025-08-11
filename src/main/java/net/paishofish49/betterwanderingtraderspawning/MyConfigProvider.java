package net.paishofish49.betterwanderingtraderspawning;

public class MyConfigProvider implements SimpleConfig.DefaultConfig {
    @Override
    public String get(String namespace) {
        return "wandering.trader.spawn.delay=24000\nwandering.trader.spawn.timer=1200";
    }
}
