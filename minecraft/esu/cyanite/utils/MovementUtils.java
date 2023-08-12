package esu.cyanite.utils;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import java.util.List;

import esu.cyanite.events.EventMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public final class MovementUtils {
    private static double lastX = -999999.0;
    private static double lastZ = -999999.0;
    Minecraft mc = Minecraft.getMinecraft();
    public static float getSpeed() {
        return (float) getSpeed(Minecraft.thePlayer.motionX, Minecraft.thePlayer.motionZ);
    }

    public static double getSpeed(double motionX, double motionZ) {
        return Math.sqrt(motionX * motionX + motionZ * motionZ);
    }

    public static boolean isOnIce() {
        final EntityPlayerSP player = Minecraft.thePlayer;
        final Block blockUnder = Minecraft.theWorld.getBlockState(new BlockPos(player.posX, player.posY - 1.0, player.posZ)).getBlock();
        return blockUnder instanceof BlockIce || blockUnder instanceof BlockPackedIce;
    }

    public static boolean isBlockUnder() {
        if (Minecraft.thePlayer == null) return false;

        if (Minecraft.thePlayer.posY < 0.0) {
            return false;
        }
        for (int off = 0; off < (int)Minecraft.thePlayer.posY + 2; off += 2) {
            final AxisAlignedBB bb = Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, (double)(-off), 0.0);
            if (!Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static void accelerate() {
        accelerate(getSpeed());
    }

    public static void accelerate(final float speed) {
        if(!isMoving())
            return;

        final double yaw = getDirection();
        Minecraft.thePlayer.motionX += -Math.sin(yaw) * speed;
        Minecraft.thePlayer.motionZ += Math.cos(yaw) * speed;
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static boolean isMoving() {
        return Minecraft.thePlayer != null && (Minecraft.thePlayer.movementInput.moveForward != 0F || Minecraft.thePlayer.movementInput.moveStrafe != 0F);
    }

    public static boolean hasMotion() {
        return Minecraft.thePlayer.motionX != 0D && Minecraft.thePlayer.motionZ != 0D && Minecraft.thePlayer.motionY != 0D;
    }

    public static void strafe(final float speed) {
        if(!isMoving())
            return;

        final double yaw = getDirection();
        Minecraft.thePlayer.motionX = -Math.sin(yaw) * speed;
        Minecraft.thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public static void strafeCustom(final float speed, final float cYaw, final float strafe, final float forward) {
        if(!isMoving())
            return;

        final double yaw = getDirectionRotation(cYaw, strafe, forward);
        Minecraft.thePlayer.motionX = -Math.sin(yaw) * speed;
        Minecraft.thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public static void forward(final double length) {
        final double yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
        Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + (-Math.sin(yaw) * length), Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + (Math.cos(yaw) * length));
    }

    public static double getDirection() {
//        final TargetStrafe ts = LiquidBounce.moduleManager.getModule(TargetStrafe.class);
        return
//        		ts.getCanStrafe() ? ts.getMovingDir() :
                getDirectionRotation(Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.moveStrafing, Minecraft.thePlayer.moveForward);
    }

    public static float getRawDirection() {
        return getRawDirectionRotation(Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.moveStrafing, Minecraft.thePlayer.moveForward);
    }

    public static float getRawDirection(float yaw) {
        return getRawDirectionRotation(yaw, Minecraft.thePlayer.moveStrafing, Minecraft.thePlayer.moveForward);
    }

    public static double[] getXZDist(float speed, float cYaw) {
        double[] arr = new double[2];
        final double yaw = getDirectionRotation(cYaw, Minecraft.thePlayer.moveStrafing, Minecraft.thePlayer.moveForward);
        arr[0] = -Math.sin(yaw) * speed;
        arr[1] = Math.cos(yaw) * speed;
        return arr;
    }

    public static float getPredictionYaw(double x, double z) {
        if (Minecraft.thePlayer == null) {
            lastX = -999999.0;
            lastZ = -999999.0;
            return 0F;
        }

        if (lastX == -999999.0)
            lastX = Minecraft.thePlayer.prevPosX;

        if (lastZ == -999999.0)
            lastZ = Minecraft.thePlayer.prevPosZ;

        float returnValue = (float) (Math.atan2(z - lastZ, x - lastX) * 180F / Math.PI);

        lastX = x;
        lastZ = z;

        return returnValue;
    }

    public static double getDirectionRotation(float yaw, float pStrafe, float pForward) {
        float rotationYaw = yaw;

        if(pForward < 0F)
            rotationYaw += 180F;

        float forward = 1F;
        if(pForward < 0F)
            forward = -0.5F;
        else if(pForward > 0F)
            forward = 0.5F;

        if(pStrafe > 0F)
            rotationYaw -= 90F * forward;

        if(pStrafe < 0F)
            rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }

    public static float getRawDirectionRotation(float yaw, float pStrafe, float pForward) {
        float rotationYaw = yaw;

        if(pForward < 0F)
            rotationYaw += 180F;

        float forward = 1F;
        if(pForward < 0F)
            forward = -0.5F;
        else if(pForward > 0F)
            forward = 0.5F;

        if(pStrafe > 0F)
            rotationYaw -= 90F * forward;

        if(pStrafe < 0F)
            rotationYaw += 90F * forward;

        return rotationYaw;
    }

    public static float getScaffoldRotation(float yaw, float strafe) {
        float rotationYaw = yaw;

        rotationYaw += 180F;

        float forward = -0.5F;

        if(strafe < 0F)
            rotationYaw -= 90F * forward;

        if(strafe > 0F)
            rotationYaw += 90F * forward;

        return rotationYaw;
    }

    public static int getJumpEffect() {
        return Minecraft.thePlayer.isPotionActive(Potion.jump) ? Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1 : 0;
    }

    public static int getSpeedEffect() {
        return Minecraft.thePlayer.isPotionActive(Potion.moveSpeed) ? Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873D;
        if (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0D + 0.2D * (double)(Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }

        return baseSpeed;
    }

    public static double getBaseMoveSpeed(double customSpeed) {
        double baseSpeed = isOnIce() ? 0.258977700006 : customSpeed;
        if (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        return getJumpBoostModifier(baseJumpHeight, true);
    }

    public static double getJumpBoostModifier(double baseJumpHeight, boolean potionJump) {
        if (Minecraft.thePlayer.isPotionActive(Potion.jump) && potionJump) {
            int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseJumpHeight += ((float)(amplifier + 1) * 0.1f);
        }

        return baseJumpHeight;
    }

    public static void setMotion(EventMove event, double speed, double motion, boolean smoothStrafe) {
        double forward = Minecraft.thePlayer.movementInput.moveForward;
        double strafe = Minecraft.thePlayer.movementInput.moveStrafe;
        double yaw = Minecraft.thePlayer.rotationYaw;
        int direction = smoothStrafe ? 45 : 90;

        if ((forward == 0.0) && (strafe == 0.0)) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (forward > 0.0 ? -direction : direction);
                } else if (strafe < 0.0) {
                    yaw += (forward > 0.0 ? direction : -direction);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }

            double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            double sin = Math.sin(Math.toRadians(yaw + 90.0f));
            event.setX((forward * speed * cos + strafe * speed * sin) * motion);
            event.setZ((forward * speed * sin - strafe * speed * cos) * motion);
        }
    }
    public static void setMotion(double speed) {
        double forward = Minecraft.thePlayer.movementInput.moveForward;
        double strafe = Minecraft.thePlayer.movementInput.moveStrafe;
        double yaw = Minecraft.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (double)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (double)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double v9 = Math.sin(Math.toRadians(yaw));
            double v11 = Math.cos(Math.toRadians(yaw));
            double movespeed = speed;
            Minecraft.thePlayer.motionX = forward * movespeed * -v9 + strafe * movespeed * v11;
            Minecraft.thePlayer.motionZ = forward * movespeed * v11 - strafe * movespeed * -v9;
        }
    }
    public static void setMotion(double speed, boolean smoothStrafe) {
        double forward = Minecraft.thePlayer.movementInput.moveForward;
        double strafe = Minecraft.thePlayer.movementInput.moveStrafe;
        float yaw = Minecraft.thePlayer.rotationYaw;
        int direction = smoothStrafe ? 45 : 90;

        if (forward == 0.0 && strafe == 0.0) {
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -direction : direction);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? direction : -direction);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }

            Minecraft.thePlayer.motionX = forward * speed * (- Math.sin(Math.toRadians(yaw))) + strafe * speed * Math.cos(Math.toRadians(yaw));
            Minecraft.thePlayer.motionZ = forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * (- Math.sin(Math.toRadians(yaw)));
        }
    }

    public static void setSpeed(EventMove moveEvent, double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, Minecraft.thePlayer.rotationYaw, (double)Minecraft.thePlayer.movementInput.moveStrafe, (double)Minecraft.thePlayer.movementInput.moveForward);
    }

    public static void setSpeed(final EventMove moveEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;

        if (forward == 0.0 && strafe == 0.0) {
            moveEvent.setZ(0);
            moveEvent.setX(0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            if (strafe > 0.0D) {
                strafe = 1.0D;
            } else if (strafe < 0.0D) {
                strafe = -1.0D;
            }
            final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            final double sin = Math.sin(Math.toRadians(yaw + 90.0f));

            moveEvent.setX((forward * moveSpeed * cos + strafe * moveSpeed * sin));
            moveEvent.setZ((forward * moveSpeed * sin - strafe * moveSpeed * cos));
        }
    }

    public static boolean isOnGround(double height) {
        return !Minecraft.theWorld.getCollidingBoundingBoxes((Entity)Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }
    public static boolean isInLiquid() {
        if (Minecraft.thePlayer.isInWater()) {
            return true;
        }
        boolean bl = false;
        int n = (int)Minecraft.thePlayer.getEntityBoundingBox().minY;
        for (int i = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minX); i < MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().maxX) + 1; ++i) {
            for (int j = MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().minZ); j < MathHelper.floor_double((double)Minecraft.thePlayer.getEntityBoundingBox().maxZ) + 1; ++j) {
                Block block = Minecraft.theWorld.getBlockState(new BlockPos(i, n, j)).getBlock();
                if (block == null || block.getMaterial() == Material.air) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                bl = true;
            }
        }
        return bl;
    }
    public static double getJumpHeight(double d) {
        if (MovementUtils.isInLiquid()) {
            return 0.13499999955296516;
        }
        if (Minecraft.thePlayer.isPotionActive(Potion.jump)) {
            return d + (double)(((float)Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1.0f) * 0.1f);
        }
        return d;
    }


}