package net.paishofish49.betterwanderingtraderspawning;

public class MyConfigProvider implements SimpleConfig.DefaultConfig {
    @Override
    public String get(String namespace) {
        return """
                # defaults to 24000 (20 irl minutes)
                wandering.trader.spawn.delay=24000
                
                # defaults to 1200 (1 irl minute)
                wandering.trader.spawn.timer=1200""";
    }
}
