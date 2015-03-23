package ch.webk;

import ch.webk.enums.Action;

public interface IUIListener {

    abstract void sendUIAction(final Action action);
}
