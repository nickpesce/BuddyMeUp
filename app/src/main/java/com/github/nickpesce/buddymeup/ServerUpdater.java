package com.github.nickpesce.buddymeup;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import packets.LocationPacket;
import packets.Packet;

public class ServerUpdater extends Service implements PacketHandler {
    String name, id;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        this.name=intent.getStringExtra("Name");
        this.id =intent.getStringExtra("Id");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {

                    new LocationPacket("", -1, id, name, 1, 1);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public ServerUpdater() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void handlePacket(Packet p) {

    }
}
