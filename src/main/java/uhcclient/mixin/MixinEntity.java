package uhcclient.mixin;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import uhcclient.UHCClientMod;

@Mixin(Entity.class)
public class MixinEntity {
    @ModifyVariable(
        method = "move(DDD)V",
        at = @At("HEAD"),
        ordinal = 0
    )
    private double moveDx(double dx) {
        final Entity entity = (Entity)(Object)this;
        if (entity == UHCClientMod.MINECRAFT.player) {
            if (UHCClientMod.spectatingPlayers.contains(((Player)entity).name)) {
                if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                    return dx * 4.5;
                }
                return dx * 2.5;
            }
            if (UHCClientMod.worldBorderInterpDir() == -1) return dx;
            final double worldBorder = UHCClientMod.getWorldBorder();
            if (entity.x > -worldBorder - 0.3 && entity.x + dx - 0.3 < -worldBorder) {
                dx = -worldBorder - entity.x + 0.3;
            } else if (entity.x < worldBorder + 0.3 && entity.x + dx + 0.3 > worldBorder) {
                dx = worldBorder - entity.x - 0.3;
            }
        }
        return dx;
    }

    @ModifyVariable(
        method = "move(DDD)V",
        at = @At("HEAD"),
        ordinal = 2
    )
    private double moveDz(double dz) {
        final Entity entity = (Entity)(Object)this;
        if (entity == UHCClientMod.MINECRAFT.player) {
            if (UHCClientMod.spectatingPlayers.contains(((Player)entity).name)) {
                if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                    return dz * 4.5;
                }
                return dz * 2.5;
            }
            if (UHCClientMod.worldBorderInterpDir() == -1) return dz;
            final double worldBorder = UHCClientMod.getWorldBorder();
            if (entity.z > -worldBorder - 0.3 && entity.z + dz - 0.3 < -worldBorder) {
                dz = -worldBorder - entity.z + 0.3;
            } else if (entity.z < worldBorder + 0.3 && entity.z + dz + 0.3 > worldBorder) {
                dz = worldBorder - entity.z - 0.3;
            }
        }
        return dz;
    }
}
