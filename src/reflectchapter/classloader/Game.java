package reflectchapter.classloader;

public class Game {
	static String getNextPlayer() {
		return null;
	}
	
	void reportScore(String str){
		System.out.println(str);
	}
	
	public static void main(String[] args) {
		String name;
		while((name = getNextPlayer()) != null){
			try {
				PlayerLoader loader = new PlayerLoader();
				Class<? extends Player> classOf =
						loader.loadClass(name).asSubclass(Player.class);
				Player player = classOf.newInstance();
				Game game = new Game();
				player.play(game);
				game.reportScore(name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
