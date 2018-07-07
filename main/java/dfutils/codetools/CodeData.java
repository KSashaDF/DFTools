package dfutils.codetools;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.LogManager;
public class CodeData {
    
    public static NBTTagCompound codeReferenceData;
    
    public CodeData() {

        //Alright, I know this is EXTREMELY lazy coding, but lets be honest, this works fine, so why not? :P
        try {
            codeReferenceData = JsonToNBT.getTagFromJson("{\n" +
                    "    \"PLAYER_ACTION\": {\n" +
                    "\n" +
                    "        \"CodeTarget\": {\n" +
                    "            \"Selection\": \"§aCurrent Selection\",\n" +
                    "            \"Default\": \"§aDefault Player\",\n" +
                    "            \"Random\": \"§eRandom Player\",\n" +
                    "            \"All\": \"§bAll Players\",\n" +
                    "            \"Killer\": \"§cKiller\",\n" +
                    "            \"Damager\": \"§cDamager\",\n" +
                    "            \"Shooter\": \"§eShooter\",\n" +
                    "            \"Victim\": \"§9Victim\"\n" +
                    "        },\n" +
                    "\n" +
                    "        \"GiveItems\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§6Give Items\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetItems\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§5Set Items\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetArmor\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§6Set Armor\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetOffHand\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§cSet Item In Off Hand\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RemoveItem\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§eRemove Items\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ClearInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§eClear Inventory\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ShowInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§eShow Inventory Menu\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"CloseInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§aClose All Inventory Menus\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ExpandInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§bExpand Inventory Menu\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SaveInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§aSave Current Inventory\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"LoadInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§eLoad Saved Inventory\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetSlot\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§9Set Hotbar Slot\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"GiveRngItem\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§6Give Random Item\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"SendMessage\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§eSend Message\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SendDialogue\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§bSend Dialogue\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SendHover\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§dSend Message w/ Hover\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ClearChat\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§cClear Chat\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PlaySound\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§9Play Sound Effect\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PlaySoundSeq\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§bPlay Sound Sequence\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"StopSound\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§cStop Sound Effects\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PlayEffect\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§9Play Particle Effect\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SendTitle\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§eSend Title\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetChatTag\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§bSet Chat Tag\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"BossBar\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§dSet Bossbar\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ClearBars\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§5Remove Bossbar\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ActionBar\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§eSend Action Bar Message\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ChatColor\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCommunication\",\n" +
                    "                \"§bSet Chat Color\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"Teleport\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§3Movement\",\n" +
                    "                \"§5Teleport\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RngTeleport\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§3Movement\",\n" +
                    "                \"§5Randomized Teleport\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"TpSequence\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§3Movement\",\n" +
                    "                \"§dTeleport Sequence\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"LaunchUp\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§3Movement\",\n" +
                    "                \"§bLaunch Up/Down\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"LaunchFwd\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§3Movement\",\n" +
                    "                \"§bLaunch Forward/Backward\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"LaunchToward\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§3Movement\",\n" +
                    "                \"§bLaunch Toward/Away From Location\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RideEntity\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§3Movement\",\n" +
                    "                \"§aRide Entity\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "        \"Damage\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§cDamage\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Heal\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§dHeal\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"GiveEffect\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§aGive Potion Effect\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ClearEffects\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§bClear All Potion Effects\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RemoveEffect\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§cRemove Potion Effect\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetXPLvl\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§5Set XP Level\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetXPProg\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§2Set XP Progress\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetFoodLevel\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§6Set Food Level\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetSaturation\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§eSet Saturation Level\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetMaxHealth\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§dSet Maximum Health\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetAtkSpeed\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§cSet Attack Speed\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetFire\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§6Set On Fire\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"FlightSpeed\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§eSet Flight Speed\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"WalkSpeed\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aStatistics\",\n" +
                    "                \"§bSet Walk Speed\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"LaunchProj\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§4Projectiles\",\n" +
                    "                \"§bLaunch Projectile\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RmArrows\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§4Projectiles\",\n" +
                    "                \"§6Remove Arrows In Body\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"MobDisguise\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eDisguises\",\n" +
                    "                \"§2Disguise As Mob\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PlayerDisguise\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eDisguises\",\n" +
                    "                \"§bDisguise As Player\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"BlockDisguise\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eDisguises\",\n" +
                    "                \"§eDisguise As Block\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Undisguise\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eDisguises\",\n" +
                    "                \"§4Undisguise\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"HideDisguise\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eDisguises\",\n" +
                    "                \"§cHide Own Disguise\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"EnablePVP\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§aEnable PVP\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"DisablePVP\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§cDisable PVP\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetTime\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§eSet Time\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"EnableFlight\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§aEnable Flight\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"DisableFlight\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§cDisable Flight\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"AllowDrops\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§aAllow Item Dropping\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"DisableDrops\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§cDisable Item Dropping\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"KeepInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§aKeep Items on Death\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NoKeepInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§cLose Items on Death\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"WeatherClear\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§aSet Weather: Clear\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"WeatherDown\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§aSet Weather: Downfall\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ProjColl\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§2Enable Projectile Collisions\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NoProjColl\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§6Disable Projectile Collisions\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NatRegen\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§dEnable Natural Regeneration\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NoNatRegen\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§6Disable Natural Regeneration\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"EnableBlocks\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§aAllow Placing/Breaking Blocks\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"DisableBlocks\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§cDisallow Placing/Breaking Blocks\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"DeathDrops\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§cEnable Death Drops\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NoDeathDrops\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§3Disable Death Drops\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"GmAdventure\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§eSet to Adventure Mode\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"GmSurvival\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§cSet to Survival Mode\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"Rollback\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§5Other\",\n" +
                    "                \"§eRoll Back Block Changes\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Respawn\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§5Other\",\n" +
                    "                \"§aRespawn\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Kick\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§5Other\",\n" +
                    "                \"§cKick\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RewardCredits\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§5Other\",\n" +
                    "                \"§aReward with DiamondFire Credits\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "    },\n" +
                    "\n" +
                    "    \"GAME_ACTION\": {\n" +
                    "        \"SpawnMob\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dEntity Spawning\",\n" +
                    "                \"§aSpawn Mob\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SpawnItem\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dEntity Spawning\",\n" +
                    "                \"§6Spawn Item\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Firework\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dEntity Spawning\",\n" +
                    "                \"§bLaunch Firework\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SpawnTNT\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dEntity Spawning\",\n" +
                    "                \"§4Spawn Primed TNT\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SpawnVehicle\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dEntity Spawning\",\n" +
                    "                \"§9Spawn Vehicle\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SpawnExpOrb\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dEntity Spawning\",\n" +
                    "                \"§eSpawn XP Orb\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"Wait\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCode Utility\",\n" +
                    "                \"§9Wait\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"StartLoop\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCode Utility\",\n" +
                    "                \"§aStart Loop\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"StopLoop\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCode Utility\",\n" +
                    "                \"§cStop Loop\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"CancelEvent\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cCode Utility\",\n" +
                    "                \"§eCancel Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"SetBlock\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aBlock Manipulation\",\n" +
                    "                \"§cSet Block/Region\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"BreakBlock\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aBlock Manipulation\",\n" +
                    "                \"§6Break Block\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"CopyBlocks\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aBlock Manipulation\",\n" +
                    "                \"§eCopy Blocks\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"FillHolder\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aBlock Manipulation\",\n" +
                    "                \"§6Fill Holder\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"EmptyHolder\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aBlock Manipulation\",\n" +
                    "                \"§3Empty Holder\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ChangeSign\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aBlock Manipulation\",\n" +
                    "                \"§6Change Sign\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"BlockDropsOn\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§aEnable Block Drops\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"BlockDropsOff\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSettings\",\n" +
                    "                \"§cDisable Block Drops\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"ShowSidebar\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bScoreboard Manipulation\",\n" +
                    "                \"§6Show Scoreboard Sidebar\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"HideSidebar\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bScoreboard Manipulation\",\n" +
                    "                \"§cHide Scoreboard Sidebar\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetScObj\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bScoreboard Manipulation\",\n" +
                    "                \"§bSet Scoreboard Objective Name\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetScore\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bScoreboard Manipulation\",\n" +
                    "                \"§6Set Scoreboard Score\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RemoveScore\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bScoreboard Manipulation\",\n" +
                    "                \"§cRemove Scoreboard Score\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ClearScBoard\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bScoreboard Manipulation\",\n" +
                    "                \"§cClear Scoreboard\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"CreateHologram\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§3Holograms\",\n" +
                    "                \"§bCreate Hologram\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RemoveHologram\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§3Holograms\",\n" +
                    "                \"§cRemove Hologram\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "\n" +
                    "\n" +
                    "        \"Explosion\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§cCreate Explosion\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"FireworkEffect\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§bPlay Firework Explosion\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Particle FX\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§6Play Particle Effect\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PFX Line\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§aCreate Particle Line\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PFX Ray\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§aCreate Particle Ray\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PFX Path\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§aCreate Particle Path\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PFX Circle\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§aCreate Particle Circle\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PFX Sphere\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§bCreate Particle Sphere\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PFX Cluster\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§bCreate Particle Cluster\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PFX Spiral\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§bCreate Particle Spiral\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PFX Line [A]\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§6Create Animated Particle Line\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PFX Circle [A]\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§6Create Animated Particle Circle\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PFX Spiral [A]\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSpecial Effects\",\n" +
                    "                \"§6Create Animated Particle Spiral\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "    },\n" +
                    "\n" +
                    "    \"ENTITY_ACTION\": {\n" +
                    "\n" +
                    "        \"CodeTarget\": {\n" +
                    "            \"Selection\": \"§aCurrent Selection\",\n" +
                    "            \"Default\": \"§cDefault Entity\",\n" +
                    "            \"Killer\": \"§cKiller\",\n" +
                    "            \"Victim\": \"§9Victim\",\n" +
                    "            \"Damager\": \"§cDamager\",\n" +
                    "            \"Shooter\": \"§eShooter\",\n" +
                    "            \"Projectile\": \"§bProjectile\",\n" +
                    "            \"Name\": \"§aMob with name in chest\",\n" +
                    "            \"EntityName\": \"§2Entity with name in chest\",\n" +
                    "            \"Random\": \"§eRandom Mob\",\n" +
                    "            \"EntityRdm\": \"§6Random Entity\",\n" +
                    "            \"All\": \"§bAll Mobs\",\n" +
                    "            \"AllEntities\": \"§9All Entities\",\n" +
                    "            \"LastSpawned\": \"§5The last mob spawned\"\n" +
                    "        },\n" +
                    "\n" +
                    "        \"SetArmor\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Set Items\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"LaunchProj\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bLaunch Projectile\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Teleport\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§5Teleport\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"TpSequence\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dTeleport Sequence\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Remove\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cRemove\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetName\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSet Custom Name\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"HideName\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Hide Name Tag\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ShowName\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eShow Name Tag\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Damage\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cDamage\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Heal\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dHeal\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"GiveEffect\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aGive Potion Effect\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RemoveEffect\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cRemove Potion Effect\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"LaunchUp\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bLaunch Up/Down\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"LaunchFwd\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bLaunch Forward/Backward\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"LaunchToward\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bLaunch Toward/Away From Location\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"MobDisguise\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§2Disguise As Mob\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PlayerDisguise\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bDisguise As Player\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"BlockDisguise\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eDisguise As Block\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Undisguise\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§4Undisguise\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"DropItems\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aEnable Death Drops\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NoDrops\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cDisable Death Drops\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetMaxHealth\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSet Max Health\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ProjColl\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§2Enable Projectile Collisions\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NoProjColl\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Disable Projectile Collisions\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"EnableAI\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aEnable AI\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NoAI\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cDisable AI\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetFire\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Set On Fire\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetAge/Size\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§2Set Age / Size\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Silence\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§4Silence\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Unsilence\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Unsilence\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NoGravity\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cDisable Gravity\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Gravity\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aEnable Gravity\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetTarget\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSet Target\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RideEntity\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aRide Entity\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"MoveTo\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Move To Location\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "    },\n" +
                    "\n" +
                    "    \"SET_VARIABLE\": {\n" +
                    "\n" +
                    "        \"=\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Set (=)\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"+\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Set to Sum (+)\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"-\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Set to Difference (-)\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"*\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Set to Product (*)\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"/\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Set to Quotient (÷)\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"%\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Set to Remainder (%)\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"+=\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Increment (+=)\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"-=\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Decrement (-=)\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ParseInt\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Parse Number\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Random\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Set to Random Number\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"CmbText\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eCombine Text\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RmText\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eRemove Text\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"TrimText\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eTrim Text\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"TextLength\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eGet Text Length\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RandomObj\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cSet to Random Object\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetX\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSet X-Coordinate\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetY\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSet Y-Coordinate\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetZ\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSet Z-Coordinate\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetPitch\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSet Pitch\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetYaw\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSet Yaw\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetCoords\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSet to Coordinates\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ParseX\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aParse X-Coordinate\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ParseY\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aParse Y-Coordinate\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ParseZ\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aParse Z-Coordinate\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ParsePitch\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aParse Pitch\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ParseYaw\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aParse Yaw\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Distance\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSet to Distance\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "\n" +
                    "    \"SELECT_OBJECT\": {\n" +
                    "\n" +
                    "        \"DefaultPlayer\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSelect Default Player\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"DefaultEntity\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cSelect Default Entity\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RandomPlayer\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSelect Random Player\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RandomMob\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSelect Random Mob\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RandomEntity\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSelect Random Entity\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"AllPlayers\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bSelect All Players\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"AllMobs\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bSelect All Mobs\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"AllEntities\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bSelect All Entities\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"LastMob\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSelect Last-Spawned Mob\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Killer\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cSelect Killer\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Damager\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cSelect Damager\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Shooter\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSelect Shooter\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Projectile\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bSelect Projectile\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Victim\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§9Select Victim\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PlayerName\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSelect Player by Name\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"MobName\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSelect Mobs by Name\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"EntityName\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aSelect Entities by Name\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PlayersCond\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSelect Players by Condition\"\n" +
                    "            ],\n" +
                    "\n" +
                    "            \"IsSneaking\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§5Is Sneaking\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsHolding\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Holding Item\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsHoldingMain\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Holding Item in Main Hand\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsHoldingOff\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Holding Item in Off Hand\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"HasItem\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eHas Item\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"HasAllItems\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eHas All Items\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsLookingAt\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aIs Looking At Block\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"StandingOn\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aIs Standing On Block\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsNear\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§bIs Near Location\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsWearing\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Wearing Item\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"NameEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aName Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsBlocking\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§2Is Blocking\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"CmdEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eCommand Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"CmdArgEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eCommand Argument Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"ItemEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§bItem Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"BlockEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Block Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"SlotEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§9Hotbar Slot Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsGliding\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aIs Gliding\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsSprinting\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eIs Sprinting\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsFlying\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eIs Flying\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsSwimming\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§9Is Swimming\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"CursorItem\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aCursor Item Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "\n" +
                    "\n" +
                    "            \"=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable =\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"!=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable !=\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \">\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber >\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \">=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber ≥\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"<\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber <\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"<=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber ≤\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"InRange\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable Is Within Range\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"EqIgnoreCase\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§eText Equals (Not Case-Sensitive)\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"Contains\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Players by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§eText Contains\"\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"MobsCond\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSelect Mobs by Condition\"\n" +
                    "            ],\n" +
                    "\n" +
                    "            \"IsType\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§5Is Type\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"NameEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§aName Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"StandingOn\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§aIs Standing On Block\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsNear\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§bIs Near Location\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsMob\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§2Is Mob\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsProj\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§9Is Projectile\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable =\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"!=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable !=\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \">\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber >\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \">=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber ≥\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"<\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber <\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"<=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber ≤\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"InRange\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable Is Within Range\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"EqIgnoreCase\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§eText Equals (Not Case-Sensitive)\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"Contains\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Mobs by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§eText Contains\"\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"EntitiesCond\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dSelect Entities by Condition\"\n" +
                    "            ],\n" +
                    "\n" +
                    "            \"IsType\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§5Is Type\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"NameEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§aName Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"StandingOn\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§aIs Standing On Block\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsNear\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§bIs Near Location\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsMob\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§2Is Mob\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsProj\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§9Is Projectile\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable =\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"!=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable !=\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \">\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber >\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \">=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber ≥\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"<\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber <\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"<=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber ≤\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"InRange\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable Is Within Range\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"EqIgnoreCase\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§eText Equals (Not Case-Sensitive)\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"Contains\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§dSelect Entities by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§eText Contains\"\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"RandomSelected\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Filter Selection Randomly\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"FilterSelect\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eFilter Selection by Condition\"\n" +
                    "            ],\n" +
                    "\n" +
                    "            \"IsSneaking\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§5Is Sneaking\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsHolding\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Holding Item\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsHoldingMain\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Holding Item in Main Hand\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsHoldingOff\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Holding Item in Off Hand\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"HasItem\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eHas Item\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"HasAllItems\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eHas All Items\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsLookingAt\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aIs Looking At Block\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsWearing\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Wearing Item\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsBlocking\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§2Is Blocking\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"CmdEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eCommand Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"CmdArgEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eCommand Argument Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"ItemEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§bItem Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"BlockEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Block Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"SlotEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§9Hotbar Slot Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsGliding\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aIs Gliding\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsSprinting\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eIs Sprinting\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsFlying\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eIs Flying\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsSwimming\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§9Is Swimming\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"CursorItem\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aCursor Item Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "\n" +
                    "\n" +
                    "            \"=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable =\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"!=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable !=\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \">\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber >\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \">=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber ≥\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"<\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber <\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"<=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber ≤\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"InRange\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable Is Within Range\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"EqIgnoreCase\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§eText Equals (Not Case-Sensitive)\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"Contains\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§eText Contains\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "\n" +
                    "\n" +
                    "            \"IsType\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§5Is Type\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsMob\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§2Is Mob\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsProj\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§eFilter Selection by Condition\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§9Is Projectile\"\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"None\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cSelect Nothing\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "\n" +
                    "    \"CONTROL\": {\n" +
                    "        \"StopRepeat\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dStop Repeating\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Skip\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSkip\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Return\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aReturn\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"End\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cEnd Sequence\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "\n" +
                    "    \"IF_PLAYER\": {\n" +
                    "\n" +
                    "        \"CodeTarget\": {\n" +
                    "            \"Selection\": \"§aCurrent Selection\",\n" +
                    "            \"Default\": \"§Default Player\",\n" +
                    "            \"Random\": \"§eRandom Player\",\n" +
                    "            \"All\": \"§bAll Players\",\n" +
                    "            \"Killer\": \"§cKiller\",\n" +
                    "            \"Damager\": \"§cDamager\",\n" +
                    "            \"Shooter\": \"§eShooter\",\n" +
                    "            \"Victim\": \"§9Victim\",\n" +
                    "        },\n" +
                    "\n" +
                    "        \"IsSneaking\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§5Is Sneaking\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsHolding\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Is Holding Item\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsHoldingMain\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Is Holding Item in Main Hand\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsHoldingOff\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Is Holding Item in Off Hand\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"HasItem\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eHas Item\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"HasAllItems\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eHas All Items\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsLookingAt\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aIs Looking At Block\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"StandingOn\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aIs Standing On Block\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsNear\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bIs Near Location\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsWearing\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Is Wearing Item\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NameEquals\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aName Equals\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsBlocking\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§2Is Blocking\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"CmdEquals\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eCommand Equals\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"CmdArgEquals\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eCommand Argument Equals\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ItemEquals\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bItem Equals\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"BlockEquals\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Block Equals\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SlotEquals\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§9Hotbar Slot Equals\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsGliding\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aIs Gliding\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsSprinting\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eIs Sprinting\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsFlying\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eIs Flying\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsSwimming\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§9Is Swimming\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"CursorItem\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aCursor Item Equals\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "\n" +
                    "    \"IF_GAME\": {\n" +
                    "\n" +
                    "        \"BlockEq\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bBlock Equals\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ContainerHas\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aContainer Has Item\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ContainerHasAll\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dContainer Has All Items\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SignHasTxt\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eSign Containes Text\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "\n" +
                    "    \"IF_ENTITY\": {\n" +
                    "\n" +
                    "        \"IsType\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§5Is Type\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"NameEquals\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aName Equals\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"StandingOn\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aIs Standing On Block\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsNear\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bIs Near Location\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsMob\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§2Is Mob\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"IsProj\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§9Is Projectile\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "\n" +
                    "    \"IF_VARIABLE\": {\n" +
                    "\n" +
                    "        \"=\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bVariable =\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"!=\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bVariable !=\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \">\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bNumber >\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \">=\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bNumber ≥\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"<\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bNumber <\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"<=\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bNumber ≤\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"InRange\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bVariable Is Within Range\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"EqIgnoreCase\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eText Equals (Not Case-Sensitive)\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Contains\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§eText Contains\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "\n" +
                    "    \"REPEAT\": {\n" +
                    "\n" +
                    "        \"N Times\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aRepeat Multiple Times\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Forever\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cRepeat Forever\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"WhileCond\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Repeat While Condition is True\"\n" +
                    "            ],\n" +
                    "\n" +
                    "            \"IsSneaking\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§5Is Sneaking\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsHolding\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Holding Item\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsHoldingMain\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Holding Item in Main Hand\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsHoldingOff\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Holding Item in Off Hand\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"HasItem\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eHas Item\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"HasAllItems\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eHas All Items\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsLookingAt\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aIs Looking At Block\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsWearing\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Is Wearing Item\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsBlocking\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§2Is Blocking\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"CmdEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eCommand Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"CmdArgEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eCommand Argument Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"ItemEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§bItem Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"BlockEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§6Block Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"SlotEquals\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§9Hotbar Slot Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsGliding\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aIs Gliding\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsSprinting\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eIs Sprinting\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsFlying\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§eIs Flying\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsSwimming\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§9Is Swimming\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"CursorItem\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Player\",\n" +
                    "                    \"§aCursor Item Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "\n" +
                    "\n" +
                    "            \"=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable =\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"!=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable !=\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \">\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber >\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \">=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber ≥\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"<\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber <\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"<=\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bNumber ≤\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"InRange\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§bVariable Is Within Range\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"EqIgnoreCase\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§eText Equals (Not Case-Sensitive)\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"Contains\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§6If Variable\",\n" +
                    "                    \"§eText Contains\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "\n" +
                    "\n" +
                    "            \"IsType\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§5Is Type\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsMob\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§2Is Mob\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"IsProj\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§2If Entity\",\n" +
                    "                    \"§9Is Projectile\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "\n" +
                    "\n" +
                    "            \"BlockEq\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§cIf Game\",\n" +
                    "                    \"§bBlock Equals\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"ContainerHas\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§cIf Game\",\n" +
                    "                    \"§aContainer Has Item\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"ContainerHasAll\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§cIf Game\",\n" +
                    "                    \"§dContainer Has All Items\"\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"SignHasTxt\": {\n" +
                    "                \"path\": [\n" +
                    "                    \"§6Repeat While Condition is True\",\n" +
                    "                    \"§cIf Game\",\n" +
                    "                    \"§eSign Containes Text\"\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        }\n" +
                    "    },\n" +
                    "\n" +
                    "    \"PLAYER_EVENT\": {\n" +
                    "        \"Join\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aPlayer Join Game Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Quit\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cPlayer Quit Game Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Right Click\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bPlayer Right Click Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Left Click\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§3Player Left Click Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Respawn\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§2Player Respawn Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Kill Player\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cPlayer Kill Player Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Death\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cPlayer Death Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Sneak\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§5Player Sneak Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Unsneak\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§5Player Unsneak Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PlayerDmgPlayer\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Player Damage Player Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ProjDmgPlayer\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bProjectile Damage Player Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"PlayerTakeDmg\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cPlayer Take Damage Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"KillMob\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§5Player Kill Mob Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"MobKillPlayer\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Mob Kill Player Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"DamageMob\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cPlayer Damage Mob Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"MobDmgPlayer\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cMob Damage Player Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ProjHit\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cPlayer Projectile Hit Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Command\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§ePlayer Command Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ClickItem\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aPlayer Click Item Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ClickOwnInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aPlayer Click Item In Own Inventory Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Click Entity\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dPlayer Right Click Entity Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Place Block\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bPlayer Place Block Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Break Block\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Player Break Block Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Pickup Item\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dPlayer Pickup Item Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Drop Item\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§2Player Drop Item Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Consume\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§ePlayer Consume Item Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Swap Hands\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§2Player Swap Hands Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Change Slot\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aPlayer Change Slot Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Sprint\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dPlayer Sprint Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"StopSprint\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§dPlayer Stop Sprinting Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Walk\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bPlayer Walk Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"FallDamage\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cPlayer Take Fall Damage Event\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "\n" +
                    "    \"ENTITY_EVENT\": {\n" +
                    "\n" +
                    "        \"MobDmgMob\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cMob Damage Mob Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ProjDmgMob\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§bProjectile Damage Mob Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"MobKillMob\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§4Mob Kill Mob Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ProjKillMob\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§9Projectile Kill Mob Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"MobDmg\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cMob Take Damage Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"MobDeath\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§4Mob Death Event\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    }\n" +
                    "}");
        } catch (NBTException exception) {
            LogManager.getLogger().error("Hmm, it appears the mod author has screwed up the code reference data format. :/");
        }
    }
}
