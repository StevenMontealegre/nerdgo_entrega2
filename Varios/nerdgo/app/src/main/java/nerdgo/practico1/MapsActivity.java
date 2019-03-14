package nerdgo.practico1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.text.DecimalFormat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 11;
    private GoogleMap mMap;
    private LocationManager manager;
    Circle saman, biblioteca, auditorios;
    Marker marcadorMiPosicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //ActivityCompat.requestPermissions(this, new String[]{
        //        Manifest.permission.ACCESS_FINE_LOCATION,
        //        Manifest.permission.ACCESS_COARSE_LOCATION
        // }, REQUEST_CODE);


        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (marcadorMiPosicion != null) {
                    //Borra mi posición antigua
                    marcadorMiPosicion.remove();

                }

                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.title("Este Soy yo");
                markerOptions.snippet("Debe decir la dirección en la que me encuentro");

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                markerOptions.position(latLng);

                //Con esto se puede poner un ícono pero no se le puede definir el tamaño
                //BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.current_position);

                marcadorMiPosicion = mMap.addMarker(markerOptions);

                //Cada 5 segundo muestra la posición actual
                Toast.makeText(getApplicationContext(), "Nueva posición >>>> LAT: " + latLng.latitude + "LONG: " + latLng.longitude, Toast.LENGTH_LONG).show();


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });


        CircleOptions opcionesCirculo1 = new CircleOptions().center(
                new LatLng(3.3426170, -76.5296914)).radius(
                29);

        auditorios = mMap.addCircle(opcionesCirculo1);
        auditorios.setStrokeWidth(10);
        auditorios.setStrokeColor(Color.RED);
//--------------------------------------------------------------------------------------
        CircleOptions opcionesCirculo2 = new CircleOptions().center(
                new LatLng(3.341745, -76.529961)).radius(
                24);

        biblioteca = mMap.addCircle(opcionesCirculo2);
        biblioteca.setStrokeWidth(10);
        biblioteca.setStrokeColor(Color.MAGENTA);
//--------------------------------------------------------------------------------------
        CircleOptions opcionesCirculo3 = new CircleOptions().center(
                new LatLng(3.341788, -76.530465)).radius(
                20);

        saman = mMap.addCircle(opcionesCirculo3);
        saman.setStrokeWidth(10);
        saman.setStrokeColor(Color.BLUE);


        // Add a marker in Sydney and move the camera
        LatLng colombia = new LatLng(3.3414959, -76.5295814);
        mMap.addMarker(new MarkerOptions().position(colombia).title("Marker in Cali-Colombia").icon(BitmapDescriptorFactory.fromResource(R.drawable.student1)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(colombia));
        float zoomLevel = 18.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colombia, zoomLevel));


    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radio de la tierra en  kilómetros
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }


    public boolean validarPosicion(LatLng coor) {
        boolean flat = false;
        LatLng l1 = saman.getCenter();
        double d1 = CalculationByDistance(coor, l1);
        if (d1 < saman.getRadius()) {
            flat = true;
        }
        LatLng l2 = biblioteca.getCenter();
        double d2 = CalculationByDistance(coor, l2);
        if (d2 < biblioteca.getRadius()) {
            flat = true;
        }
        LatLng l3 = auditorios.getCenter();
        double d3 = CalculationByDistance(coor, l3);
        if (d3 < biblioteca.getRadius()) {
            flat = true;
        }
        return flat;
    }

}
