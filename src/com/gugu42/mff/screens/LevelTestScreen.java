package com.gugu42.mff.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gugu42.mff.GameClass;
import com.gugu42.mff.entities.Player;

public class LevelTestScreen implements Screen {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private GameClass game;

	private Player player;

	private TextureAtlas playerAtlas;

	public LevelTestScreen(GameClass game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(player.getX() + player.getWidth() / 2, 145, 0);

		camera.update();
		renderer.setView(camera);

		renderer.render();

		renderer.getSpriteBatch().begin();
		player.draw(renderer.getSpriteBatch());
		game.font.draw(renderer.getSpriteBatch(),
				"Player Y : " + player.getY(), 100, 300);
		renderer.getSpriteBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 2.5f;
		camera.viewportHeight = height / 2.5f;
		camera.update();
	}

	@Override
	public void show() {
		map = new TmxMapLoader().load("res/levels/testmap.tmx");

		renderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera(1024, 780);

		playerAtlas = new TextureAtlas("res/char/player.pack");
		
		Animation stillR, stillL, left, right, jumpR, jumpL;
		
		stillR = new Animation(1 / 1f, playerAtlas.findRegions("stillR"));
		stillL = new Animation(1 / 1f, playerAtlas.findRegions("stillL"));
		left = new Animation(1 / 8f, playerAtlas.findRegions("left"));
		right = new Animation(1 / 8f, playerAtlas.findRegions("right"));
		jumpR = new Animation(1 / 1f, playerAtlas.findRegions("jumpR"));
		jumpL = new Animation(1 / 1f, playerAtlas.findRegions("jumpL"));
		stillR.setPlayMode(Animation.LOOP);
		stillL.setPlayMode(Animation.LOOP);
		left.setPlayMode(Animation.LOOP);
		right.setPlayMode(Animation.LOOP);
		jumpR.setPlayMode(Animation.LOOP);
		jumpL.setPlayMode(Animation.LOOP);
		
		player = new Player(stillR, stillL, left, right, jumpR, jumpL,
				(TiledMapTileLayer) map.getLayers().get(1));
		
		player.setPosition(11 * player.getCollisionLayer().getTileWidth(),
				4 * player.getCollisionLayer().getTileHeight());

		Gdx.input.setInputProcessor(player);
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
		map.dispose();
		renderer.dispose();
		player.getTexture().dispose();
		playerAtlas.dispose();
	}

}