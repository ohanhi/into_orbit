package com.ohanhi.into_orbit;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

/**
 * Created by ohan on 18.10.2014.
 */
public class GenericDialog extends Dialog {

    private Game game;

    public GenericDialog(Game game, String title, String text, String confirmText, String cancelText) {
        super(title, Const.SKIN);

        this.game = game;

        text(text);
        button(confirmText, true);
        button(cancelText, false);
    }

    public GenericDialog(Game game, String title, String text, String[] buttons) {
        super(title, Const.SKIN);

        this.game = game;
        text(text);
        for (int i = 0; i < buttons.length; i++) {
            String cur = buttons[i];
            button(cur, cur);
        }
    }

    @Override
    protected void result(Object object) {
        game.dialogInput(this.getTitle(), object.toString());
        this.remove();
    }
}
