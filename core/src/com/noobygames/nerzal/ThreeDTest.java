package com.noobygames.nerzal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class ThreeDTest implements ApplicationListener {
	public PerspectiveCamera cam;
	public ModelBatch modelBatch;
	public CameraInputController camController;
	public Model model;
	public ModelInstance instance;
	private Environment environment;

	@Override
	public void create() {
		//createGreenBox();
		loadShipModel();
	}
	
	private void createGreenBox(){
		environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
		
		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);
		
		modelBatch = new ModelBatch();
		ModelBuilder mb = new ModelBuilder();
		model = mb.createBox(5f, 5f, 5f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				Usage.Position | Usage.Normal);
		instance = new ModelInstance(model);
	}

	private void loadShipModel(){
		 modelBatch = new ModelBatch();
	        environment = new Environment();
	        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
	        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	         
	        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        cam.position.set(1f, 1f, 1f);
	        cam.lookAt(0,0,0);
	        cam.near = 1f;
	        cam.far = 300f;
	        cam.update();
	 
	        ModelLoader loader = new ObjLoader();
	        model = loader.loadModel(Gdx.files.internal("ship/ship.obj"));
	        instance = new ModelInstance(model);
	 
	        camController = new CameraInputController(cam);
	        Gdx.input.setInputProcessor(camController);
	}
	
	public void renderGreenBox(){
		modelBatch.begin(cam);
        modelBatch.render(instance, environment);
        modelBatch.end();
	}
	
	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		 
        renderGreenBox();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		model.dispose();

	}
}
