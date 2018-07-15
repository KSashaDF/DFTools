package dfutils.codetools.codeprinting;

public enum PrintSubState {
    NULL,
    MOVEMENT_WAIT,
    ACTION_WAIT,
    ACTION_WAIT_FINISH,
    EVENT_WAIT,
    EVENT_WAIT_FINISH
}
