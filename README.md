This mod allows you to change wandering trader spawn frequency. When you first join a world or start a server after adding this mod, a configuration file is created that allows you to edit the wandering trader spawn delay and the wandering trader spawn timer.

## Config Options
### Wandering Trader Spawn Delay
`wandering.trader.spawn.delay` defaults to 24000. (20 minutes)
### Wandering Trader Spawn Timer
`wandering.trader.spawn.timer` defaults to 1200 (1 minutes).
## How it works
every game tick, the spawn timer decrements. when it reaches 0, it resets to `wandering.trader.spawn.timer`, and subtracts that value from the spawn delay. it then checks whether spawn delay is 0 or less, and if it is, it attempts a wandering trader spawn and resets back to `wandering.trader.spawn.delay`.
