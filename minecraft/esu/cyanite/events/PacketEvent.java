package esu.cyanite.events;

import net.minecraft.network.Packet;

public class PacketEvent{
    private Packet<?> packet;

    public PacketEvent() {
        this.packet = packet;
    }


    public Packet<?> getPacket() {
        return packet;
    }


    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }




}