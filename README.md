# The Matrix

## Plan

### Dimensions

1. Zion
   - [x] Define Zion Dimension Type
   - [ ] Define noise function to generate terrain
   - [ ] Define biomes

2. Virtual World
   - [ ] Define Virtual World Dimension Type
   - [ ] Define noise function to generate terrain
   - [ ] Define biomes

3. Robot World
   - [x] Define Robot World Dimension Type
   - [x] Define noise function to generate terrain
   - [ ] Define biomes

4. End Virtual World
   - [ ] Define End Virtual World Dimension Type
   - [ ] Define noise function to generate terrain
   - [ ] Define biomes

### Biomes

- [ ] Zion
   - [ ] Zion cave
- [ ] Virtual World
   - [ ] Virtual city
- [ ] Robot World

### Entities

1. Zion
   - [ ] Armored Personnel Unit (APU) Mecha (rideable)
      - [x] Define APU Mecha entity
      - [x] Model & Textures
      - [x] Animations
      - [ ] AI Goals
      - [ ] Sound events
         - [x] Ambient
         - [x] Hurt
         - [ ] Death
      - [ ] Loot table
         - [ ] Machine parts
         - [ ] CPU
      - [ ] Spawn conditions
      - [x] Translations
   - [ ] Machine Gun (rideable, no living entity like boat)
   - [ ] Space Warship (rideable)
   - [ ] Zion People
       - [ ] Define Zion People entity
       - [ ] Model & Textures
       - [ ] Animations
       - [ ] AI Goals
       - [ ] Sound events
          - [ ] Ambient
          - [ ] Hurt
          - [ ] Death
          - [ ] Attack
       - [ ] Trading
2. Virtual World
   - [ ] Agent
      - [x] Define Agent entity
      - [x] Animations
      - [x] AI Goals
      - [x] Loot table
         - [x] Mobile phone
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
      - [x] Define Robot Sentinel entity
      - [x] Model & Textures
      - [x] Animations
      - [ ] AI Goals
         - [x] basic define
         - [ ] advanced define
      - [x] Sound events
         - [x] Ambient
         - [x] Hurt
         - [x] Death
      - [x] Loot table
          - [x] Machine parts
          - [x] CPU
      - [ ] Spawn conditions


### Items

1. Virtual World
   - [ ] Mobile Phone (to tp to Zion by calling)
      - [x] Define telephone item
      - [ ] Realise function to tp to Zion
   - [x] Armors
      - [x] V Mask
         - [x] Ability to attack agents
         - [x] Tooltip
      - [x] Hacker Cloak
         - [x] Fly
         - [x] Tooltip
      - [x] Hacker Pants
         - [x] Complete defense the attacks from agents
         - [x] Tooltip
      - [x] Hacker Shoes
         - [x] Complete defense fall damage
         - [x] Tooltip

2. Zion
   - [ ] Transmitter (to tp to Virtual World)
      - [ ] Define transmitter item
      - [ ] Define Block of transmitters
      - [ ] Realise function to tp to Virtual World

3. Common
   - [ ] CPUs (to make the space warship)
      - [x] Define CPU item
      - [ ] Realise function to make the space warship
   - [x] Machines parts (get it by killing robots, to make many things like space warship, APU mecha, etc)
   - [ ] Energy batteries (to power the space warship)
      - [ ] Define energy battery item
      - [ ] Realise function to power the space warship
   - [ ] Electromagnetic guns (use Energy batteries to shoot)
      - [x] Define electromagnetic gun item
      - [x] Animations & Models & Textures
      - [ ] Realise function to shoot
   - [ ] Electromagnetic hand grenades (to kill robots)
   - [ ] Raduim Ore (to make energy batteries, etc)
   - [ ] Raduim Ingot (to make energy batteries)
   - [ ] Raduim Block (to make energy batteries)

### Particles
   - [ ] Zion
      - [ ] APU machine gun shoot
      - [ ] Electromagnetic gun shoot
   - [ ] Virtual World
      - [ ] Zero One