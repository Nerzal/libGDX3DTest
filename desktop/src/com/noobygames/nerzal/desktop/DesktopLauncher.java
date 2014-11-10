package com.noobygames.nerzal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.noobygames.nerzal.CreatingAShader;
import com.noobygames.nerzal.LoadingAScene;
import com.noobygames.nerzal.ThreeDTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "GDX 3D Test";
		config.width = 800;
		config.height = 640;
		
		new LwjglApplication(new LoadingAScene(), config);
	}
}
