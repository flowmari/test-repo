package plugin.sample;

import java.math.BigInteger; // 大きな整数を扱うためのクラス
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

  // プラグインが有効化（起動）されたときに一度だけ呼ばれる
  @Override
  public void onEnable() {
    // このクラスでイベントを受け取れるようにする
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  /**
   * プレイヤーがスニーク（しゃがむ）したときに呼び出されるイベント
   */
  @EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
    Player player = e.getPlayer();         // スニークしたプレイヤーを取得
    World world = player.getWorld();       // プレイヤーがいるワールドを取得

    // val を 1 に設定（1より大きい素数を探すため）
    BigInteger val = new BigInteger("1");

    // 1 より大きい「次の素数」を取得（ここでは 2 が返る）
    BigInteger nextPrime = val.nextProbablePrime();

    // その数が本当に素数かチェック（念のため）
    if (nextPrime.isProbablePrime(10)) {
      // プレイヤーに素数であることをチャットで伝える
      player.sendMessage("見つけた素数は " + nextPrime + " です！花火を打ち上げます！");

      // プレイヤーの位置に花火を出す
      Firework firework = world.spawn(player.getLocation(), Firework.class);
      FireworkMeta meta = firework.getFireworkMeta();

      // 花火のデザイン設定（青色、星型、キラキラ）
      meta.addEffect(
          FireworkEffect.builder()
              .withColor(Color.BLUE)
              .with(Type.STAR)
              .withFlicker()
              .build());
      meta.setPower(1); // 花火の高さ（0～2が有効）

      // 花火に設定を反映して打ち上げ
      firework.setFireworkMeta(meta);
    } else {
      // 万が一素数でなければメッセージだけ
      player.sendMessage(nextPrime + " は素数ではありません。花火は出ません。");
    }
  }
}