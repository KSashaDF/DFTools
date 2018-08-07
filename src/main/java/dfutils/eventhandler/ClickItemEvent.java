package dfutils.eventhandler;

import dfutils.utils.playerdata.PlayerStateHandler;

class ClickItemEvent {
    
    //This method is called whenever the player clicks an item inside a container.
    static void onClickItemEvent(dfutils.customevents.ClickItemEvent event) {
        if (PlayerStateHandler.isOnDiamondFire) {
            PlayerStateHandler.playerStateHandlerClickItemEvent(event);
        }
    }
}
