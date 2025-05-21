package com.example.jumprandom;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class JumpRandomPlugin extends JavaPlugin implements Listener {

    private final Set<Player> jumpingPlayers = new HashSet<>();
    private final Random random = new Random();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("JumpRandom 플러그인이 활성화되었습니다!");
    }

    @EventHandler
    public void onPlayerJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Y축으로 이동하고 있고 바닥에서 떨어질 때만 감지
        if (player.isOnGround()) {
            jumpingPlayers.remove(player);
        } else if (!jumpingPlayers.contains(player)) {
            // 새로 점프한 상태로 간주
            jumpingPlayers.add(player);

            double jumpPower = 0.5 + (2.5 * random.nextDouble()); // 0.5 ~ 3.0
            Vector velocity = player.getVelocity();
            velocity.setY(jumpPower);
            player.setVelocity(velocity);

            player.sendMessage("§a랜덤 점프! 높이: " + String.format("%.2f", jumpPower));
        }
    }
}