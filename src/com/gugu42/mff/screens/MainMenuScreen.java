package com.gugu42.mff.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.gugu42.mff.GameClass;

public class MainMenuScreen implements Screen {

	public GameClass game;
	public OrthographicCamera camera;
	Stage stage;
	Button play, quit;
	
	public MainMenuScreen(GameClass game) {
		this.game = game;
		stage = new Stage();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Bienvenue dans le jeu fait par Gugu42 pour le concours MFF ", 100, 150);
		game.font.draw(game.batch, "Clickez pour commencer !", 100, 100);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new LevelTestScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		
	}

}
