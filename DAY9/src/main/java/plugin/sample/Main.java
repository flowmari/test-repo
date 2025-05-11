package plugin.sample;

import java.math.BigInteger;
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

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
    count++;

    Player player = e.getPlayer();
    World world = player.getWorld();

    // 見やすくカラフルな色リスト
    List<Color> colorList = List.of(Color.YELLOW, Color.LIME, Color.AQUA, Color.PURPLE);

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
                  .withColor(color)           // 主な色
                  .withFade(Color.WHITE)      // 消える時に白くフェード
                  .with(Type.BALL_LARGE)
                  .withFlicker()              // 点滅効果
                  .withTrail()                // 軌跡を追加
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
}
