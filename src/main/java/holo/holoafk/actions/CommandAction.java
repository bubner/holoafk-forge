package holo.holoafk.actions;

import holo.holoafk.actions.threads.HubAction;
import holo.holoafk.actions.threads.KickAction;
import holo.holoafk.actions.threads.LimboAction;
import holo.holoafk.utils.Events;
import holo.holoafk.utils.FlagTrigger;
import holo.holoafk.utils.ModConfig;
import holo.holoafk.utils.Utils;

public class CommandAction {

    /**
     * Run a recovery action based on the given recovery event.
     *
     * @param config ModConfig instance.
     * @param recovery The recovery event to run.
     */
    public static void runRecovery(ModConfig config, FlagTrigger trigger, Events.RecoveryEvent recovery, boolean recurse) {
        if (Utils.isOnPrivateIsland() && Utils.isInSkyblock() && recurse) {
            // Further recovery not required
            Utils.sendMsg("Recovery completed. Sending notice.");
            trigger.setSuccess(true);
            new SendAlert(config, trigger, Events.AlertPriority.LOW).start();
            return;
        }
        switch (recovery) {
            case HUB_RECOVERY:
                HubAction hubAction = new HubAction(config, trigger);
                hubAction.start();
                break;
            case LIMBO_RECOVERY:
                LimboAction limboAction = new LimboAction(config, trigger);
                limboAction.start();
                break;
            case DISCONNECT_RECOVERY:
            case KICK_RECOVERY:
                KickAction kickAction = new KickAction(config, trigger);
                kickAction.start();
                break;
        }
    }

    public static void runRecovery(ModConfig config, FlagTrigger trigger, Events.RecoveryEvent recovery) {
        runRecovery(config, trigger, recovery, true);
    }
}
