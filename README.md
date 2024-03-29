# The Matrix

## Plan

This mod is still in development, and the plan is as follows:

### Dimensions

1. Zion
   - [x] Define Zion Dimension Type
   - [x] Define noise function to generate terrain
   - [x] Define biomes (Zion cave)

2. Virtual World
   - [ ] Define Virtual World Dimension Type
   - [ ] Define noise function to generate terrain
   - [ ] Define biomes (city)

3. Robot World
   - [x] Define Robot World Dimension Type
   - [x] Define noise function to generate terrain
   - [ ] Define biomes (robot world)

4. End Virtual World
   - [ ] Define End Virtual World Dimension Type
   - [ ] Define noise function to generate terrain (floating islands)
   - [ ] Define biomes (city in the sky)

### Biomes

- [x] Zion
   - [x] Zion cave
      - [x] Define Zion cave biome
      - [x] Creatures generation
- [ ] Virtual World
   - [ ] Virtual city
- [ ] Robot World

### Entities

1. Zion
   - [x] Armored Personnel Unit (APU) (rideable)
      - [x] Define APU entity: see [ArmoredPersonnelUnitEntity.java](src/main/java/me/jaffe2718/the_matrix/element/entity/vehicle/ArmoredPersonnelUnitEntity.java)
      - [x] Model & Textures: see [ArmoredPersonnelUnitModel.java](src/main/java/me/jaffe2718/the_matrix/client/model/entity/ArmoredPersonnelUnitModel.java)
      - [x] Animations
      - [x] AI Goals
      - [x] Sound events
         - [x] Ambient
         - [x] Hurt
         - [x] Death
      - [x] Create APU like iron golem: see [EngineCoreBlock.java](src/main/java/me/jaffe2718/the_matrix/element/block/EngineCoreBlock.java)
      - [x] Translations
   - [ ] Digger Robot (boss)
     - [x] Model & Textures
     - [x] Animations
     - [x] AI Goals
     - [ ] Sound events
         - [ ] Ambient
         - [ ] Hurt
         - [ ] Death
         - [ ] Attack
     - [x] Loot table
         - [x] Machine parts
         - [x] CPU
     - [x] Spawn conditions
   - [ ] Machine Gun (rideable, no living entity like boat)
      - [x] Define Machine Gun entity: see [MachineGunEntity.java](src/main/java/me/jaffe2718/the_matrix/element/entity/vehicle/MachineGunEntity.java)
      - [x] Model & Textures: see [MachineGunModel.java](src/main/java/me/jaffe2718/the_matrix/client/model/entity/MachineGunModel.java)
      - [x] Animations
      - [x] AI Goals
      - [ ] Sound events
         - [x] Ambient
         - [ ] Hurt
         - [ ] Death
      - [x] Translations
   - [x] Spaceship (rideable) see [SpaceshipEntity.java](src/main/java/me/jaffe2718/the_matrix/element/entity/vehicle/SpaceshipEntity.java)
      - [x] Define Spaceship entity
      - [x] Model & Textures: see [SpaceshipModel.java](src/main/java/me/jaffe2718/the_matrix/client/model/entity/SpaceshipModel.java)
      - [x] Create Spaceship : see [EngineCoreBlock.java](src/main/java/me/jaffe2718/the_matrix/element/block/EngineCoreBlock.java)
      - [x] Animations
      - [x] AI Goals
      - [x] Sound events
         - [x] Ambient
         - [x] Hurt
         - [x] Death
      - [x] Translations
   - [x] Zion People
       - [x] Define Zion People entity: see [ZionPeopleEntity.java](src/main/java/me/jaffe2718/the_matrix/element/entity/mob/ZionPeopleEntity.java)
       - [x] Model & Textures: see [ZionPeopleModel.java](src/main/java/me/jaffe2718/the_matrix/client/model/entity/ZionPeopleModel.java)
          - [x] Jobs (0 -> random)
             - [x] 1 -> apu_pilot
             - [x] 2 -> carpenter
             - [x] 3 -> farm_breeder
             - [x] 4 -> farmer
             - [x] 5 -> grocer
             - [x] 6 -> infantry
             - [x] 7 -> machinist
             - [x] 8 -> miner
             - [x] 9 -> rifleman
       - [x] Animations
       - [x] AI Goals
       - [x] Sound events
          - [x] Hurt
          - [x] Death
          - [x] Attack
       - [x] Trading
       - [x] Spawn conditions
2. Virtual World
   - [ ] Agent
      - [x] Define Agent entity: see [AgentEntity.java](src/main/java/me/jaffe2718/the_matrix/element/entity/mob/AgentEntity.java)
      - [x] Animations
      - [x] AI Goals
      - [x] Loot table
         - [x] Mobile phone: see [ItemRegistry.java](src/main/java/me/jaffe2718/the_matrix/unit/ItemRegistry.java)
         - [x] Coins
      - [ ] Spawn conditions
      - [x] Translations
   - [ ] Virtual People
      - [ ] Define Virtual People entity
      - [ ] Model & Textures
      - [ ] Animations
      - [ ] AI Goals
      - [ ] Sound events
         - [ ] Ambient
         - [ ] Hurt
         - [ ] Death
         - [ ] Attack
      - [ ] Trading
3. Robot World
   - [ ] Robots
      - [ ] The Matrix (boss)
      - [ ] Other robots
4. End Virtual World
   - [ ] Agent Smith (boss)
5. Common
   - [ ] Robot Sentinel (Zion & Robot World, Octopus)
      - [x] Define Robot Sentinel entity: see [RobotSentinelEntity.java](src/main/java/me/jaffe2718/the_matrix/element/entity/mob/RobotSentinelEntity.java)
      - [x] Model & Textures: see [RobotSentinelModel.java](src/main/java/me/jaffe2718/the_matrix/client/model/entity/RobotSentinelModel.java)
      - [x] Animations
      - [x] AI Goals
      - [x] Sound events
         - [x] Ambient
         - [x] Hurt
         - [x] Death
      - [x] Loot table
          - [x] Machine parts
          - [x] CPU
      - [ ] Spawn conditions
          - [ ] Robot World
          - [x] Zion


### Items

1. Virtual World
   - [x] Mobile Phone (to tp to Zion by calling)
      - [x] Define telephone item
      - [x] Realise functions
         - [x] `tp` to Zion
         - [x] clear player's inventory (cannot take anything leaving Virtual World except **coins**)
         - [x] record player's inventory (next time `tp` to Virtual World, the inventory will be restored, but **coins** will be taken away by the player)
   - [x] Armors
      - [x] V Mask: see [VMaskItem.java](src/main/java/me/jaffe2718/the_matrix/element/item/VMaskItem.java)
         - [x] Ability to attack agents
         - [x] Tooltip
      - [x] Hacker Cloak: see [HackerCloakItem.java](src/main/java/me/jaffe2718/the_matrix/element/item/HackerCloakItem.java)
         - [x] Fly
         - [x] Tooltip
      - [x] Hacker Pants: see [HackerPantsItem.java](src/main/java/me/jaffe2718/the_matrix/element/item/HackerPantsItem.java)
         - [x] Complete defense the attacks from agents
         - [x] Tooltip
      - [x] Hacker Boots: see [HackerBootsItem.java](src/main/java/me/jaffe2718/the_matrix/element/item/HackerBootsItem.java)
         - [x] Complete defense fall damage
         - [x] Tooltip

2. Zion
   - [x] Teleporter (to tp to Virtual World)
      - [x] Define Teleporter item
      - [x] Define Block of Teleporters
      - [x] Realise function to tp to Virtual World
   
3. Common
   - [x] CPUs
      - [x] Define CPU item
      - [x] To recipe Laptop
   - [x] Laptop (to earn coins)
      - [x] Define laptop item
      - [x] Realise function to earn coins by playing game 2048
   - [x] Machines parts (get it by killing robots, to make many things like spaceship, APU, etc.)
   - [x] Mechanical Armors
   - [x] Mining Drill (to mine)
   - [x] Batteries (to power the electromagnetic guns)
      - [x] Define battery item
      - [x] Realise function to power the electromagnetic guns
   - [x] Electromagnetic guns (use batteries to shoot)
      - [x] Define electromagnetic gun item
      - [x] Animations & Models & Textures
      - [x] Realise function to shoot
   - [x] Promethium Ore (to make energy batteries, etc)
      - [x] Define [deepslate] promethium ore block
      - [x] Define promethium ore item
      - [x] Spawn conditions
   - [x] Promethium Ingot (to make energy batteries)
   - [x] Promethium Block (to make energy batteries)

### Particles
   - [x] Zion
      - [x] APU machine gun shoot
      - [x] Electromagnetic gun shoot
   - [ ] Virtual World
      - [ ] Zero One