package dfutils.eventhandler;

import dfutils.utils.MessageUtils;

class ClickItemEvent {
    
    //This method is called whenever the player clicks an item inside a container.
    static void onClickItemEvent(dfutils.customevents.ClickItemEvent event) {
        MessageUtils.infoMessage("Clicked item.");
    }
}
