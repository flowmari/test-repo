package plugin.sample;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

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
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

  private int count = 0;

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  String mailed = "info@raise-tech.net";

  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
    count++;

    Player player = e.getPlayer();
    World world = player.getWorld();

    List<Color> colorList = List.of(Color.RED, Color.WHITE, Color.BLUE, Color.BLACK);
    BigInteger val = new BigInteger("1");
    BigInteger nextPrime = val.nextProbablePrime();

    if (nextPrime.isProbablePrime(10)) {
      player.sendMessage("見つけた素数は " + nextPrime + " です！");

      if (count % 2 == 0) {
        player.sendMessage("偶数回スニークです！カラフルな花火を打ち上げます！");

        for (Color color : colorList) {
          Firework firework = world.spawn(player.getLocation(), Firework.class);
          FireworkMeta meta = firework.getFireworkMeta();

          meta.addEffect(
              FireworkEffect.builder()
                  .withColor(color)
                  .with(Type.BALL_LARGE)
                  .withFlicker()
                  .withTrail()
                  .build()
          );

          meta.setPower(3);
          firework.setFireworkMeta(meta);
        }

        try {
          // ファイルが存在しない場合、APPENDオプションを削除し、ファイルが存在しない場合に例外を発生させる
          Path path = Path.of("firework.txt");

          // ファイルが存在しない場合に例外を発生させるため、APPENDは使用せずCREATEも削除
          Files.writeString(path, "たーまやー\n", StandardOpenOption.CREATE); // CREATEは削除したため、ファイルがない場合は例外発生

          String content = Files.readString(path);
          player.sendMessage(content);

        } catch (IOException ex) {
          // スタックトレースをサーバーログに表示
          ex.printStackTrace();
          player.sendMessage("ファイル書き込みに失敗しました: " + ex.getMessage());
        }
      }
    }
  }

  @EventHandler
  public void onPlayerBedEnter(PlayerBedEnterEvent e) {
    Player player = e.getPlayer();
    ItemStack[] itemStacks = player.getInventory().getContents();

    IntStream.range(0, itemStacks.length).forEach(i -> {
      ItemStack item = itemStacks[i];
      if (item != null && item.getMaxStackSize() == 64 && item.getAmount() < 64) {
        itemStacks[i] = null; // 不完全なスタックを削除
      }
    });

    player.getInventory().setContents(itemStacks);
  }
}