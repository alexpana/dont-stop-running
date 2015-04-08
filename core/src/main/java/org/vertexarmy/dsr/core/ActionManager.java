package org.vertexarmy.dsr.core;

import com.google.common.collect.Lists;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public final class ActionManager {
    private final static int HISTORY_SIZE = 1000;

    private final static ActionManager INSTANCE = new ActionManager();

    private final Deque<Action> undoActionStack = new ArrayDeque<>();

    private final Deque<Action> redoActionStack = new ArrayDeque<>();

    private ActionManager() {
    }

    public static ActionManager instance() {
        return INSTANCE;
    }

    public void runAction(Action action) {
        if (!action.isValid()) {
            return;
        }

        undoActionStack.add(action);
        action.doAction();

        redoActionStack.clear();

        ensureHistorySize();
    }

    public boolean undo() {
        if (!undoActionStack.isEmpty()) {
            Action lastAction = undoActionStack.removeLast();
            lastAction.undoAction();
            redoActionStack.addLast(lastAction);
            ensureHistorySize();

            return true;
        }

        return false;

    }

    public boolean redo() {
        if (!redoActionStack.isEmpty()) {
            Action lastUndoneAction = redoActionStack.removeLast();
            lastUndoneAction.doAction();
            undoActionStack.addLast(lastUndoneAction);
            ensureHistorySize();

            return true;
        }

        return false;

    }

    private void ensureHistorySize() {
        while (undoActionStack.size() > HISTORY_SIZE) {
            undoActionStack.removeFirst();
        }

        while (redoActionStack.size() > HISTORY_SIZE) {
            redoActionStack.removeFirst();
        }
    }

    public interface Action {
        void doAction();

        void undoAction();

        boolean isValid();
    }

    public static abstract class ActionAdapter implements Action {
        @Override
        public void doAction() {
        }

        @Override
        public void undoAction() {
        }

        @Override
        public boolean isValid() {
            return false;
        }
    }

    public static class ReverseAction implements Action {
        private final Action action;

        public ReverseAction(Action action) {
            this.action = action;
        }

        @Override
        public void doAction() {
            action.undoAction();
        }

        @Override
        public void undoAction() {
            action.doAction();
        }

        @Override
        public boolean isValid() {
            return action.isValid();
        }
    }

    public static class CompositeAction implements Action {

        private final List<Action> actionList;

        public CompositeAction(List<Action> actionList) {
            this.actionList = actionList;
        }

        @Override
        public void doAction() {
            for (Action action : actionList) {
                action.doAction();
            }
        }

        @Override
        public void undoAction() {
            for (Action action : Lists.reverse(actionList)) {
                action.undoAction();
            }
        }

        @Override
        public boolean isValid() {
            if (actionList.isEmpty()) {
                return false;
            }

            for (Action action : actionList) {
                if (action.isValid()) {
                    return true;
                }
            }

            return false;
        }
    }
}
