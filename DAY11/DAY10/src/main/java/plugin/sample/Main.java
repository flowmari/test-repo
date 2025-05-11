package plugin.sample;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

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

  // スニークしたときに素数と花火の処理を実行
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
                  .withFade(Color.WHITE)
                  .with(Type.BALL_LARGE)
                  .withFlicker()
                  .withTrail()
                  .build()
          );
          meta.setPower(1);
          firework.setFireworkMeta(meta);
        }
      } else {
        player.sendMessage("奇数回スニークなので花火は打ち上げません。");
      }
    } else {
      player.sendMessage(nextPrime + " は素数ではありません。花火は出ません。");
    }
  }

  // ベッドに入ると、スタック数がMAXじゃないアイテムを削除する
  @EventHandler
  public void onPlayerBedEnter(PlayerBedEnterEvent e) {
    Player player = e.getPlayer();
    ItemStack[] itemStacks = player.getInventory().getContents();

    // Stream API を使って、64未満のアイテムは削除（null にする）
    IntStream.range(0, itemStacks.length).forEach(i -> {
      ItemStack item = itemStacks[i];
      if (item != null && item.getMaxStackSize() == 64 && item.getAmount() < 64) {
        itemStacks[i] = null; // 消す
      }
    });

    player.getInventory().setContents(itemStacks);
  }
}
