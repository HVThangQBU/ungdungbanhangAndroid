package com.example.appbanhangadmin.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.appbanhang.R;
import com.example.appbanhang.databinding.ActivityThongTinBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ThongTinActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityThongTinBinding binding;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        binding = ActivityThongTinBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );
        toolbar = findViewById( R.id.toolbarthongtin );
        ActionBar();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
    }

    private void ActionBar() {
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
  //      mMap.setMyLocationEnabled( false );
        // Add a marker in Sydney and move the camera
        LatLng QBU = new LatLng( 17.482293, 106.608024 );
        mMap.addMarker( new MarkerOptions().position( QBU ).title( "Trường Đại Học Quảng Bình" ).snippet( "Nguyễn Văn Linh, Đồng Phú, Đồng Hới, Quảng Bình, Vietnam" ).icon( BitmapDescriptorFactory.defaultMarker() ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLng( QBU ) );
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL  );
        CameraPosition cameraPosition = new CameraPosition.Builder().target( QBU ).zoom( 90 ).build();
        mMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );

    }
}