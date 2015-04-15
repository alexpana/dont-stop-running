package org.vertexarmy.dsr.core.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.beust.jcommander.internal.Lists;
import com.google.common.base.Preconditions;
import org.vertexarmy.dsr.core.Log;
import org.vertexarmy.dsr.core.component.InputComponent;

import java.util.List;

/**
 * created by Alex
 * on 3/21/2015.
 */
public class InputSystem {
    private static final InputSystem INSTANCE = new InputSystem();

    private final Log log = Log.create();

    private final List<InputComponent> components = Lists.newArrayList();

    private InputSystem() {
    }

    public static InputSystem instance() {
        return INSTANCE;
    }

    public void addInputComponent(InputComponent component) {
        Preconditions.checkArgument(component != null, "Cannot add a null input component.");
        components.add(component);
    }

    public void removeInputComponent(InputComponent component) {
        components.remove(component);
    }

    public void initialize() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                for (InputComponent component : components) {
                    try {
                        if (component.getInputAdapter().keyDown(keycode)) {
                            return true;
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                for (InputComponent component : components) {
                    try {
                        if (component.getInputAdapter().keyUp(keycode)) {
                            return true;
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                for (InputComponent component : components) {
                    try {
                        if (component.getInputAdapter().keyTyped(character)) {
                            return true;
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                for (InputComponent component : components) {
                    try {
                        if (component.getInputAdapter().touchDown(screenX, screenY, pointer, button)) {
                            return true;
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                for (InputComponent component : components) {
                    try {
                        if (component.getInputAdapter().touchUp(screenX, screenY, pointer, button)) {
                            return true;
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                for (InputComponent component : components) {
                    try {
                        if (component.getInputAdapter().touchDragged(screenX, screenY, pointer)) {
                            return true;
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                for (InputComponent component : components) {
                    try {
                        if (component.getInputAdapter().mouseMoved(screenX, screenY)) {
                            return true;
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                for (InputComponent component : components) {
                    try {
                        if (component.getInputAdapter().scrolled(amount)) {
                            return true;
                        }
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
                return false;
            }
        });

        log.info("Initialized ok.");
    }
}
