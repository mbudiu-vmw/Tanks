package tanks.gui.screen;

import tanks.Crusade;
import tanks.Drawing;
import tanks.Game;
import tanks.gui.Button;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ScreenCrusades extends Screen
{
	public static final String crusadeDir = Game.directoryPath + "/crusades";

	int rows = 6;
	int yoffset = -150;
	int page = 0;

	Button quit = new Button(Drawing.drawing.interfaceSizeX / 2, Drawing.drawing.interfaceSizeY / 2 + 300, 350, 40, "Back", new Runnable()
	{
		@Override
		public void run() 
		{
			Game.screen = new ScreenPlay();
		}
	}
			);

	Button next = new Button(Drawing.drawing.interfaceSizeX / 2 + 190, Drawing.drawing.interfaceSizeY / 2 + 240, 350, 40, "Next page", new Runnable()
	{
		@Override
		public void run() 
		{
			page++;
		}
	}
			);

	Button previous = new Button(Drawing.drawing.interfaceSizeX / 2 - 190, Drawing.drawing.interfaceSizeY / 2 + 240, 350, 40, "Previous page", new Runnable()
	{
		@Override
		public void run() 
		{
			page--;
		}
	}
			);

	ArrayList<Button> buttons = new ArrayList<Button>();


	public ScreenCrusades()
	{
		if (!Files.exists(Paths.get(Game.homedir + crusadeDir)))
		{
			new File(Game.homedir + crusadeDir).mkdir();
		}

		ArrayList<Path> levels = new ArrayList<Path>();

		try
		{
			DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(Game.homedir + crusadeDir));

			for (Path p : ds) {
				if (p.toString().endsWith(".tanks"))
					levels.add(p);
			}

			ds.close();
		}
		catch (IOException e)
		{
			Game.exitToCrash(e);
		}
		
		buttons.add(new Button(0, 0, 350, 40, "Classic crusade", new Runnable()
		{
			@Override
			public void run() 
			{
				Scanner s = new Scanner(new InputStreamReader(getClass().getResourceAsStream("/classic_crusade.tanks")));
				ArrayList<String> al = new ArrayList<String>();
				
				while (s.hasNext())
				{
					al.add(s.nextLine());
				}
				
				s.close();
				
				Crusade.currentCrusade = new Crusade(al, "Classic Crusade");
				Crusade.crusadeMode = true;
				Crusade.currentCrusade.begin();
				Game.screen = new ScreenGame(Crusade.currentCrusade.getShop());
			}
		}
				));
		
		buttons.add(new Button(0, 0, 350, 40, "Wii crusade", new Runnable()
		{
			@Override
			public void run() 
			{
				Scanner s = new Scanner(new InputStreamReader(getClass().getResourceAsStream("/wii_crusade.tanks")));
				ArrayList<String> al = new ArrayList<String>();
				
				while (s.hasNext())
				{
					al.add(s.nextLine());
				}
				
				s.close();
				
				Crusade.currentCrusade = new Crusade(al, "Wii Crusade");
				Crusade.crusadeMode = true;
				Crusade.currentCrusade.begin();
				Game.screen = new ScreenGame(Crusade.currentCrusade.getShop());
			}
		}
				));
		
		for (Path l: levels)
		{
			String[] pathSections = l.toString().replaceAll("\\\\", "/").split("/");

			buttons.add(new Button(0, 0, 350, 40, pathSections[pathSections.length - 1].split("\\.")[0], new Runnable()
			{
				@Override
				public void run() 
				{
					Crusade.currentCrusade = new Crusade(l.toFile(), pathSections[pathSections.length - 1].split("\\.")[0]);
					Crusade.crusadeMode = true;
					Crusade.currentCrusade.begin();
					Game.screen = new ScreenGame(Crusade.currentCrusade.getShop());
				}
			}
					));

		}

		for (int i = 0; i < buttons.size(); i++)
		{
			int page = i / (rows * 3);
			int offset = 0;

			if (page * rows * 3 + rows < buttons.size())
				offset = -190;

			if (page * rows * 3 + rows * 2 < buttons.size())
				offset = -380;

			buttons.get(i).posY = Drawing.drawing.interfaceSizeY / 2 + yoffset + (i % rows) * 60;

			if (i / rows % 3 == 0)
				buttons.get(i).posX = Drawing.drawing.interfaceSizeX / 2 + offset;
			else if (i / rows % 3 == 1)
				buttons.get(i).posX = Drawing.drawing.interfaceSizeX / 2 + offset + 380;
			else
				buttons.get(i).posX = Drawing.drawing.interfaceSizeX / 2 + offset + 380 * 2;
		}


	}

	@Override
	public void update()
	{		
		for (int i = page * rows * 3; i < Math.min(page * rows * 3 + rows * 3, buttons.size()); i++)
		{
			buttons.get(i).update();
		}

		quit.update();
		//newLevel.update();

		if (page > 0)
			previous.update();

		if (buttons.size() > (1 + page) * rows * 3)
			next.update();
	}

	@Override
	public void draw()
	{
		this.drawDefaultBackground();

		for (int i = page * rows * 3; i < Math.min(page * rows * 3 + rows * 3, buttons.size()); i++)
		{
			buttons.get(i).draw();
		}

		quit.draw();
		//newLevel.draw(g);

		Drawing.drawing.drawInterfaceText(Drawing.drawing.sizeX / 2, Drawing.drawing.sizeY / 2 - 210, "Crusades");

		if (page > 0)
			previous.draw();

		if (buttons.size() > (1 + page) * rows * 3)
			next.draw();

	}

}
