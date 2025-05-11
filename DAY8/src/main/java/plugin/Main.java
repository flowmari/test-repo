package plugin.sample;

import

import java.beans.EventHandler;
import java.net.http.WebSocket.Listener;

public final class Main extends JavaPlugin implements Listener {

  ＠Override
  public void ionEnable(){
    Bukkit.getPluginMonager().registerEvents(listener.this, plugin this);

    int number = 10;
    if(number >=10){

    }
  }

  /**
   * プレイヤーがスニークを開始/終了する際に起動されるイベントハンドラ。
   *
   * ＠param e イベント
   */
  ＠EventHandler
  public void onPlayerToggleSneak(PlayerToggleSneakEvent e){
    //イベント発生時のプレイヤーやワールドなどの情報を変数に持つ。
    Player player = e.geetPlayer();
