package com.gugu42.mff;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gugu42.mff.screens.MainMenuScreen;

public class GameClass extends Game implements ApplicationListener {

	public SpriteBatch batch;
	public BitmapFont font;
	
	@Override
	public void create() {		
		setScreen(new MainMenuScreen(this));
		batch = new SpriteBatch();
		font = new BitmapFont();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
