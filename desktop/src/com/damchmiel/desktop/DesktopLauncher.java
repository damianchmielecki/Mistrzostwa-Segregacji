package com.damchmiel.desktop;			//Rozmiar okna ; tytuł ; parametry zwiazane z wyswietlaniem grafiki robimy tu
																		//uruchamianie gry i konf biblioteki LibGDX
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.damchmiel.Segregacja;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.resizable = false;
		config.width = 1280;
		config.height = 1024;

		new LwjglApplication(new Segregacja(), config);
	}
}
