package com.github.nickpesce.buddymeup;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import packets.AlertPacket;
import packets.FriendUpdatePacket;
import packets.LocationPacket;
import packets.LoginPacket;
import packets.Packet;
import packets.PairRequestPacket;
import packets.PairResponsePacket;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PacketHandler, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Profile profile;
    private Networking networking;
    private LinearLayout buddyLayout;
    private final static double MAX_DISTANCE = 100/5280.0;
    //   private Intent locationService;
    private boolean trackingLocation;
    private Location mLastLocation;
    private Button bAlert;
    private LocationRequest mLocationRequest;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        bAlert = (Button)findViewById(R.id.bAlert);
        bAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networking.send(new AlertPacket("", -1, profile.id, profile.name, mLastLocation.getLatitude(), mLastLocation.getLongitude(), profile.id));
            }
        });

        buddyLayout = (LinearLayout) findViewById(R.id.llBuddies);
        networking = new Networking(this);

        profile = new Profile(this);
        Intent intent = getIntent();
        profile.name = intent.getStringExtra("Name");
        profile.id = intent.getStringExtra("Phone");
//        locationService = new Intent(this, ServerUpdater.class);
//        locationService.putExtra("Name", profile.name);
//        locationService.putExtra("Id", profile.id);

        //TODO Get Profile info:
        //- Friends temporarily hardcoded into profile
        //- Settings

        LinearLayout scrollLayout = (LinearLayout) findViewById(R.id.llScroll_Friends);
        final int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());

        for (final Friend f : profile.getFriends().values()) {
            final ImageView icon = f.getNewIcon(true);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!profile.getBuddies().containsKey(f.id) && !profile.getFriends().get(f.id).requested) {
                        f.requested = true;
                        networking.send(new PairRequestPacket("", -1, profile.id, profile.name, f.id));
                        //TODO visual requested
                    }
                }
            });

            scrollLayout.addView(icon, radius, radius + radius / 3);
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    networking.send(new LocationPacket(InetAddress.getLocalHost(), 6969, "478t8yrq4ywhgu", "Nick", "Georgetown"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        networking.send(new LoginPacket("", -1, profile.id, profile.name));

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        createLocationRequest();
        networking.close();
    }

    public void checkDistance(final String id) {
        Friend f = profile.getBuddies().get(id);
        if (f.distance > MAX_DISTANCE)
        {
            alert(id);
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(MainActivity.this, "You are separated from  " + profile.getBuddies().get(id).name + "!", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void alert(final String id) {
        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(10000);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_buddies) {

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_options) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_rate) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void startTracking() {
//        trackingLocation = true;
//        startService(locationService);
//    }
//
//    public void stopTracking() {
//        trackingLocation = false;
//        stopService(locationService);
//    }

    private void connectToTether(String id) {
        if (!trackingLocation)
            track();
        //startTracking();
        final Friend friend = profile.getFriends().get(id);
        profile.getBuddies().put(id, friend);
        profile.getFriends().get(id).requested = false;
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                buddyLayout.addView(friend.getNewIcon(false));
            }
        });
    }

    public void updateUI() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                buddyLayout.removeAllViews();
                for (Friend f : profile.getBuddies().values()) {
                    buddyLayout.addView(f.getNewIcon(false));
                }
            }
        });
    }


    private void track() {
        trackingLocation = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
//                LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 69);
//                    return;
//                }
                while (true) {
                    networking.send(new LocationPacket("", -1, profile.id, profile.name, mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 69: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//                    System.exit(0);
//                }
//                return;
//            }
//        }
//    }

    @Override
    public void handlePacket(Packet p) {
        switch (p.getPacketType()) {
            case Packet.FRIEND_UPDATE_PACKET:
                FriendUpdatePacket fup = (FriendUpdatePacket) p;
                for (FriendUpdatePacket.FriendLocation loc : fup.getFriends()) {
                    Friend bud = profile.getBuddies().get(loc.getFriendId());
                    bud.setDistance(loc.getDistance());
                    checkDistance(bud.id);
                    bud.setLocation(loc.getFriendLatitude(), loc.getFriendLongitude());
                }
                updateUI();
                break;
            case Packet.LOCATION_PACKET:
                if(p instanceof AlertPacket)
                {
                    final String id = ((AlertPacket) p).getfriendId();
                    alert(id);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, profile.getBuddies().get(id).name + " needs you!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
            case Packet.PAIR_RESPONSE_PACKET:
                PairResponsePacket pres = (PairResponsePacket) p;
                final Friend friend = profile.getFriends().get(pres.getFriendId());
                if (pres.response() == true) {
                    connectToTether(pres.getFriendId());
                } else
                    friend.requested = false;
                break;
            case Packet.PAIR_REQUEST_PACKET:
                final PairRequestPacket preq = (PairRequestPacket) p;
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(profile.getFriends().get(preq.getFriendId()) + " would like to buddy up with you. Okay?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //YES
                                networking.send(new PairResponsePacket("", -1, profile.id, profile.name, preq.getFriendId(), true));
                                connectToTether(preq.getFriendId());

                            }
                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //NO
                                        networking.send(new PairResponsePacket("", -1, profile.id, profile.name, preq.getFriendId(), false));
                                    }
                                }).show();
                    }
                });
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (client != null) {
            client.connect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(client);
        if (mLastLocation != null) {

            //Toast.makeText(this, "Latitude:" + mLastLocation.getLatitude() + ", Longitude:" + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();

        }
        startLocationUpdates();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (client != null) {
            client.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //Toast.makeText(this, "Latitude:" + mLastLocation.getLatitude()+", Longitude:"+mLastLocation.getLongitude(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}
