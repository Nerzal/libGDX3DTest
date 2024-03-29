package com.noobygames.nerzal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class LoadingAScene implements ApplicationListener {
	public PerspectiveCamera cam;
	public CameraInputController camController;
	public ModelBatch modelBatch;
	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	public Environment environment;
	public boolean loading;

	public Array<ModelInstance> blocks = new Array<ModelInstance>();
	public Array<ModelInstance> invaders = new Array<ModelInstance>();
	public ModelInstance ship;
	public ModelInstance space;

	@Override
	public void create() {
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f,
				0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f,
				-0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(0f, 7f, 10f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		assets = new AssetManager();
		assets.load("models/ship.g3db", Model.class);
		assets.load("models/block.g3db", Model.class);
		assets.load("models/invader.g3db", Model.class);
		assets.load("models/spacesphere.g3db", Model.class);
		loading = true;
	}

	private void doneLoading() {
		ship = new ModelInstance(assets.get("models/ship.g3db", Model.class));
		ship.transform.setToRotation(Vector3.Y, 180).trn(0, 0, 6f);
		instances.add(ship);

		Model blockModel = assets.get("models/block.g3db", Model.class);
		for (float x = -5f; x <= 10f; x += 2f) {
			ModelInstance block = new ModelInstance(blockModel);
			block.transform.setToTranslation(x, 0, 3f);
			instances.add(block);
			blocks.add(block);
		}

		Model invaderModel = assets.get("models/invader.g3db", Model.class);
		for (float x = -5f; x <= 10f; x += 2f) {
			for (float y = -5f; y <= 10f; y += 0.7f) {
				for (float z = -50f; z <= 0f; z += 2f) {
					ModelInstance invader = new ModelInstance(invaderModel);
					invader.transform.setToTranslation(x, y, z);
					instances.add(invader);
					invaders.add(invader);
				}
			}
		}

		space = new ModelInstance(assets.get("models/spacesphere.g3db",
				Model.class));


		loading = false;
	}

	@Override
	public void render() {
		if (loading && assets.update())
			doneLoading();
		camController.update();
		
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(instances, environment);
		if (space != null)
			modelBatch.render(space);
		modelBatch.end();
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		instances.clear();
		assets.dispose();
	}

	@Override
	public void resume() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}
}