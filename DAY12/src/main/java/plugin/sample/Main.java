package plugin.sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

  private int count = 0;
  private final Path fireworkMessagePath = Path.of("firework.txt");

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);

    // 初回のみ「たーまやー」ファイルを作成
    try {
      if (!Files.exists(fireworkMessagePath)) {
        Files.writeString(fireworkMessagePath, "たーまやー");
        getLogger().info("firework.txt を作成しました。");
      }
    } catch (IOException e) {
      getLogger().warning("firework.txt の作成に失敗しました: " + e.getMessage());
    }
  }

  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
    count++;

    // 偶数回目のみ実行
    if (count % 2 == 0) {
      Player player = e.getPlayer();
      World world = player.getWorld();

      List<Color> colorList = List.of(Color.YELLOW, Color.LIME, Color.AQUA, Color.PURPLE);
      for (Color color : colorList) {
        Firework firework = world.spawn(player.getLocation(), Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();

        meta.addEffect(
            FireworkEffect.builder()
                .withColor(color)
                .withFade(Color.WHITE)
                .with(Type.BALL_LARGE)
                .withFlicker()
                .withTrail()
                .build()
        );
        meta.setPower(1);
        firework.setFireworkMeta(meta);
      }

      try {
        String message = Files.readString(fireworkMessagePath);
        player.sendMessage(message);              // プレイヤーに表示
        getLogger().info("花火ログ: " + message);  // サーバーログに出力
      } catch (IOException ex) {
        getLogger().warning("firework.txt の読み込みに失敗しました: " + ex.getMessage());
      }
    }
  }
}