package com.dmitrij.viable.ndurasov.ui;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dmitrij.viable.ndurasov.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

import static com.dmitrij.viable.ndurasov.ui.FullCardFragment.NUM_OF_POINTS;


public class MapsFragment extends Fragment {

    private static final String TAG = "maps_fragment";
    private ItemViewModel viewModel;
    private FloorViewModel floorViewModel;
    private GoogleMap mMap;

    private GroundOverlayOptions options1, options2, options3;

    private int currentActivePoint = 0;

    private Marker[] points = new Marker[NUM_OF_POINTS+1];

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_maps)));
            Log.d("gmaps", success+"");


            LatLng[] pointsLng = new LatLng[NUM_OF_POINTS+1];
            pointsLng[0] = new LatLng(55.7538865, 37.6485381);
            pointsLng[1] = new LatLng(55.75386205, 37.64869775);
            pointsLng[2] = new LatLng(55.75415959, 37.648743353);
            pointsLng[3] = new LatLng(55.754117332, 37.648492567);
            pointsLng[4] = new LatLng(55.754041674, 37.648444958);
            pointsLng[5] = new LatLng(55.754051673, 37.648377902);
            pointsLng[6] = new LatLng(55.753995071, 37.648225016);
            pointsLng[7] = new LatLng(55.753962052, 37.648335993);

            pointsLng[8] = new LatLng(55.753900167, 37.648305818);
            pointsLng[9] = new LatLng(55.753822432, 37.648269273);
            pointsLng[10] = new LatLng(55.75400054, 37.648585774);
            pointsLng[11] = new LatLng(55.754031, 37.648581);

            pointsLng[12] = new LatLng(55.754151, 37.648320);
            pointsLng[13] = new LatLng(55.754049, 37.648272);
            pointsLng[14] = new LatLng(55.753962, 37.648230);
            pointsLng[15] = new LatLng(55.753831, 37.648163);
            pointsLng[16] = new LatLng(55.753806, 37.6483426);
            pointsLng[17] = new LatLng(55.753786, 37.648463);
            pointsLng[18] = new LatLng(55.753825, 37.648135);

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    Log.d(TAG,"Map clicked [" + point.latitude + ", " + point.longitude + "]");
                }
            });

            for (int i=0; i < NUM_OF_POINTS+1; i++){
                if (pointsLng[i] == null){
                    pointsLng[i] = new LatLng(55.754074, 37.648268);
                }

                points[i] = mMap.addMarker(
                        new MarkerOptions()
                                .position(pointsLng[i])
                                .anchor(0.5f, 0.5f)
                                .title(i + ". " + getResources().getStringArray(R.array.points_short_titles)[i])
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.poi)));
                points[i].setTag(i);
            }

            //mMap.moveCamera(CameraUpdateFactory.newLatLng(hall));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Integer clickCount = (Integer) marker.getTag();

                    if (clickCount != null) {
                        clickCount = clickCount + 1;

                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.poi));

                        try {
                            Bundle bundle = new Bundle();
                            bundle.putString("param1", (marker.getTag()+""));
                            Log.d(TAG, marker.getTag()+"");
                            if (Integer.parseInt(marker.getTag()+"")>=0 &&
                                    Integer.parseInt(marker.getTag()+"")<NUM_OF_POINTS+1
                            ){
                                viewModel.select(marker.getTag()+"");
                                points[Integer.parseInt(marker.getTag()+"")].setIcon(
                                        BitmapDescriptorFactory.fromResource(R.drawable.poi_active)
                                );
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return false;
                }
            });

            viewModel.getSelectedItem().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    try {
                        points[currentActivePoint].setIcon(
                                BitmapDescriptorFactory.fromResource(R.drawable.poi)
                        );
                        points[Integer.parseInt(s)].setIcon(
                                BitmapDescriptorFactory.fromResource(R.drawable.poi_active)
                        );
                        currentActivePoint = Integer.parseInt(s);

                        // hiding marker title
                        //points[currentActivePoint].hideInfoWindow();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });

            options1 = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.map1))
                    .anchor(0, 0.1f)
                    .position(new LatLng(55.754460, 37.649282), 117f)
                    .transparency(0.05f)
                    .bearing(105f)
                    .clickable(false);
            GroundOverlay overlay1 = mMap.addGroundOverlay(options1);
            options2 = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.map2))
                    .anchor(0, 0.1f)
                    .position(new LatLng(55.754460, 37.649282), 117f)
                    .transparency(0.05f)
                    .bearing(105f)
                    .clickable(false);
            GroundOverlay overlay2 = mMap.addGroundOverlay(options2);
            options3 = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.map3))
                    .anchor(0, 0.1f)
                    .position(new LatLng(55.754460, 37.649282), 117f)
                    .transparency(0.05f)
                    .bearing(105f)
                    .clickable(false);
            GroundOverlay overlay3 = mMap.addGroundOverlay(options3);


            if (floorViewModel!=null){
                floorViewModel.getSelectedItem().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        int step  = Integer.parseInt(s);
                        switch (step){
                            case 1:
                                overlay1.setVisible(true);
                                overlay2.setVisible(false);
                                overlay3.setVisible(false);
                                for (int i=0; i < 11; i++){
                                    points[i].setVisible(true);
                                }
                                for (int i=11; i < NUM_OF_POINTS; i++){
                                    points[i].setVisible(false);
                                }
                                points[NUM_OF_POINTS].setVisible(false);
                                break;
                            case 2:
                                overlay1.setVisible(false);
                                overlay2.setVisible(true);
                                overlay3.setVisible(false);
                                for (int i=0; i < 11; i++){
                                    points[i].setVisible(false);
                                }
                                for (int i=11; i < NUM_OF_POINTS; i++){
                                    points[i].setVisible(true);
                                }
                                points[NUM_OF_POINTS].setVisible(false);
                                break;
                            case 3:
                                overlay1.setVisible(false);
                                overlay2.setVisible(false);
                                overlay3.setVisible(true);
                                for (int i=0; i < 11; i++){
                                    points[i].setVisible(false);
                                }
                                for (int i=11; i < NUM_OF_POINTS; i++){
                                    points[i].setVisible(false);
                                }
                                points[NUM_OF_POINTS].setVisible(true);
                                break;
                            default:

                        }
                    }
                });
            }

            // Zoom and moving options restrictions
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder(mMap.getCameraPosition()).bearing(105f)
                    .target(new LatLng(55.754071, 37.6484553))
                    .zoom(20.3f)
                    .build()));

            mMap.setMaxZoomPreference(23f);
            mMap.setMinZoomPreference(20.3f);
            LatLngBounds adelaideBounds = new LatLngBounds(
                    new LatLng(55.753831, 37.648123), // SW bounds
                    new LatLng(55.754168, 37.648741)  // NE bounds
            );
            mMap.setLatLngBoundsForCameraTarget(adelaideBounds);
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
            Log.d("gsonon", item);
            if (!item.equals("")){
                if (rootView.getVisibility() == View.INVISIBLE){
                    Navigation.findNavController(rootView).navigate(R.id.action_nav_card_self);
                }
            } else {
                rootView.setVisibility(View.INVISIBLE);
            }
        });

        floorViewModel = new ViewModelProvider(requireActivity()).get(FloorViewModel.class);

    }

}