package com.noobygames.nerzal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.utils.JsonReader;

public class CreatingAShader implements ApplicationListener {
   public PerspectiveCamera cam;
   public CameraInputController camController;
   public Shader shader;
   public RenderContext renderContext;
   public Model model;
   public Environment environment;
   public Renderable renderable;
     
   @Override
   public void create () {
	   cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    cam.position.set(2f, 2f, 2f);
	    cam.lookAt(0,0,0);
	    cam.near = 1f;
	    cam.far = 300f;
	    cam.update();
	     
	    camController = new CameraInputController(cam);
	    Gdx.input.setInputProcessor(camController);
	 
	    ModelBuilder modelBuilder = new ModelBuilder();
	    model = modelBuilder.createSphere(2f, 2f, 2f, 20, 20,
	      new Material(),
	      Usage.Position | Usage.Normal| Usage.TextureCoordinates);
	 
	    NodePart blockPart = model.nodes.get(0).parts.get(0);
	      
	    renderable = new Renderable();
	    blockPart.setRenderable(renderable);
	    renderable.environment = null;
	    renderable.worldTransform.idt();
	    //renderable.primitiveType = GL20.GL_POINTS;
	    
	    renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
	    String vert = Gdx.files.internal("models/test.vertex.glsl").readString();
	    String frag = Gdx.files.internal("models/test.fragment.glsl").readString();
	    shader = new DefaultShader(renderable, new DefaultShader.Config(vert, frag));
	    shader.init();
   }
     
   @Override
   public void render () {
       camController.update();
         
       Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
 
       renderContext.begin();
       shader.begin(cam, renderContext);
       shader.render(renderable);
       shader.end();
       renderContext.end();
   }
     
   @Override
   public void dispose () {
       shader.dispose();
       model.dispose();
   }
  
    @Override public void resume () {}
    @Override public void resize (int width, int height) {}
    @Override public void pause () {}
}