package com.gugu42.mff.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

	/**
	 * The movement velocity
	 */
	private Vector2 velocity = new Vector2();

	private String blockedKey = "blocked";

	/**
	 * Speed of the player
	 */
	private float speed = 60 * 2, gravity = 60f * 1.8f, increment, animationTime = 0;

	private Animation stillR, stillL, left, right, jumpR, jumpL;
	
	private TiledMapTileLayer collisionLayer;

	private boolean canJump;

	private boolean rightKeyReleased;

	public Player(Animation stillR, Animation stillL, Animation left, Animation right, Animation jumpR, Animation jumpL, TiledMapTileLayer collisionLayer) {
		super(stillR.getKeyFrame(0));
		this.stillR = stillR;
		this.stillL = stillL;
		this.left = left;
		this.right = right;
		this.jumpR = jumpR;
		this.jumpL = jumpL;
		this.collisionLayer = collisionLayer;
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	public void update(float delta) {

		animationTime += delta;
		velocity.y -= gravity * delta;

		// clamp velocity
		if (velocity.y > speed)
			velocity.y = speed;
		else if (velocity.y < -speed)
			velocity.y = -speed;

		// Old position
		float oldX = getX(), oldY = getY();

		boolean collisionX = false, collisionY = false;
		// Move on X
		setX(getX() + velocity.x * delta);

		increment = collisionLayer.getTileWidth();
		increment = getWidth() < increment ? getWidth() / 2 : increment / 2;

		if (velocity.x < 0) //Left
			collisionX = collidesLeft();
		else if (velocity.x > 0)//Right
			collisionX = collidesRight();


		if (collisionX) {
			setX(oldX);
			velocity.x = 0;
		}


		setY(getY() + velocity.y * delta * 5f);

		increment = collisionLayer.getTileHeight();
		increment = getHeight() < increment ? getHeight() / 2 : increment / 2;

		if (velocity.y < 0) 
			canJump = collisionY = collidesBottom();
		else if (velocity.y > 0)
			collisionY = collidesTop();

		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
		//update animation
		
		setRegion(velocity.x < 0 && velocity.y == 0 ? left.getKeyFrame(animationTime) : velocity.x > 0 && velocity.y == 0 ? right.getKeyFrame(animationTime) : velocity.x < 0 ? jumpL.getKeyFrame(animationTime) : velocity.x > 0 ? jumpR.getKeyFrame(animationTime) : getStillFrame());
	}

	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}

	
	private TextureRegion getStillFrame(){
		if(rightKeyReleased){
			return stillR.getKeyFrame(animationTime);
		} else {
			return stillL.getKeyFrame(animationTime);
		}
	}
	
	public boolean collidesRight() {
		for(float step = 0; step <= getHeight(); step += increment)
			if(isCellBlocked(getX() + getWidth(), getY() + step))
				return true;
		return false;
	}

	public boolean collidesLeft() {
		for(float step = 0; step <= getHeight(); step += increment)
			if(isCellBlocked(getX(), getY() + step))
				return true;
		return false;
	}

	public boolean collidesTop() {
		for(float step = 0; step <= getWidth(); step += increment)
			if(isCellBlocked(getX() + step, getY() + getHeight()))
				return true;
		return false;

	}

	public boolean collidesBottom() {
		for(float step = 0; step <= getWidth(); step += increment)
			if(isCellBlocked(getX() + step, getY()))
				return true;
		return false;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.Z:
			if (canJump) {
				velocity.y = speed / 1.8f;
				canJump = false;
			}
			break;
		case Keys.Q:
			velocity.x = -speed;
			animationTime = 0;
			break;
		case Keys.D:
			velocity.x = speed;
			animationTime = 0;
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.Z:
			break;
		case Keys.Q:
			velocity.x = 0;
			animationTime = 0;
			rightKeyReleased = false;
			break;
		case Keys.D:
			velocity.x = 0;
			animationTime = 0;
			rightKeyReleased = true;
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
