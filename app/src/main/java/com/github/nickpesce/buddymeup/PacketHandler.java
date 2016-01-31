package com.github.nickpesce.buddymeup;

import packets.Packet;

public interface PacketHandler {
    void handlePacket(Packet p);
}
