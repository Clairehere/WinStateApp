package com.example.wilder.winstateapp;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentContainer;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.joaquimley.faboptions.FabOptions;
import com.telenav.expandablepager.ExpandablePager;
import com.telenav.expandablepager.listener.OnSliderStateChangeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    UserSingleton recieveArticle = UserSingleton.getInstance();
    Location mLastLocation = null;
    Location mLocationUser;
    FusedLocationProviderClient mFusedLocationClient;
    boolean mIsWaitingForGoogleMap = false;
    ArrayList<VideoModel> mEvent = new ArrayList<>();
    ArrayList<Marker> mEventMarker = new ArrayList<>();
    ArrayList<String> mVideoTest = new ArrayList<>();
    private GoogleMap mMap;



    // Liste
    int duration = 200;
    boolean two = false;
    private List<Book> list = Arrays.asList(
            new Book("Crime and Punishment").setAuthor("Fyodor Dostoyevsky").setUrl("http://ecx.images-amazon.com/images/I/51M%2BDuxvjmL._SX311_BO1,204,203,200_.jpg").setDescription("Crime and Punishment (Russian: Преступлéние и наказáние, tr. Prestupleniye i nakazaniye; IPA: [prʲɪstʊˈplʲenʲɪɪ ɪ nəkɐˈzanʲɪɪ]) is a novel by the Russian author Fyodor Dostoyevsky. It was first published in the literary journal The Russian Messenger in twelve monthly installments during 1866. It was later published in a single volume. It is the second of Dostoyevsky's full-length novels following his return from 10 years of exile in Siberia. Crime and Punishment is considered the first great novel of his \"mature\" period of writing."),
            new Book("The Brothers Karamazov").setAuthor("Fyodor Dostoyevsky").setUrl("http://ecx.images-amazon.com/images/I/51FIyYKsCXL._SX333_BO1,204,203,200_.jpg").setDescription("The Brothers Karamazov (Russian: Бра́тья Карама́зовы, Brat'ya Karamazovy, pronounced [ˈbratʲjə kərɐˈmazəvɨ]), also translated as The Karamazov Brothers, is the final novel by the Russian author Fyodor Dostoyevsky. Dostoyevsky spent nearly two years writing The Brothers Karamazov, which was published as a serial in The Russian Messenger and completed in November 1880. The author died less than four months after its publication."),
            new Book("Demons").setAuthor("Fyodor Dostoyevsky").setUrl("http://ecx.images-amazon.com/images/I/41Q-p2N1neL._SX326_BO1,204,203,200_.jpg").setDescription("Demons (Russian: Бесы, Bésy) is an anti-nihilistic novel by Fyodor Dostoyevsky, first published in the journal The Russian Messenger in 1871-2. It is the third of the four great novels written by Dostoyevsky after his return from Siberian exile, the others being Crime and Punishment (1866), The Idiot (1869) and The Brothers Karamazov (1880). Demons is a social and political satire, a psychological drama, and large scale tragedy. Joyce Carol Oates has described it as \"Dostoevsky's most confused and violent novel, and his most satisfyingly 'tragic' work.\""),
            new Book("Catch-22").setAuthor("Joseph Heller").setUrl("http://ecx.images-amazon.com/images/I/51kqbC3YKvL._SX322_BO1,204,203,200_.jpg").setDescription("Catch-22 is a satirical novel by the American author Joseph Heller. He began writing it in 1953; the novel was first published in 1961. It is frequently cited as one of the greatest literary works of the twentieth century. It uses a distinctive non-chronological third-person omniscient narration, describing events from the points of view of different characters. The separate storylines are out of sequence so that the timeline develops along with the plot."),
            new Book("Animal Farm").setAuthor("George Orwell").setUrl("http://ecx.images-amazon.com/images/I/51EjU6rQjsL._SX318_BO1,204,203,200_.jpg").setDescription("Animal Farm is an allegorical and dystopian novella by George Orwell, first published in England on 17 August 1945. According to Orwell, the book reflects events leading up to the Russian Revolution of 1917 and then on into the Stalinist era of the Soviet Union. Orwell, a democratic socialist, was a critic of Joseph Stalin and hostile to Moscow-directed Stalinism, an attitude that was critically shaped by his experiences during the Spanish Civil War."),
            new Book("To Kill a Mockingbird").setAuthor("Harper Lee").setUrl("http://ecx.images-amazon.com/images/I/51grMGCKivL._SX307_BO1,204,203,200_.jpg").setDescription("To Kill a Mockingbird is a novel by Harper Lee published in 1960. It was immediately successful, winning the Pulitzer Prize, and has become a classic of modern American literature. The plot and characters are loosely based on the author's observations of her family and neighbors, as well as on an event that occurred near her hometown in 1936, when she was 10 years old."),
            new Book("Fahrenheit 451").setAuthor("Ray Bradbury").setUrl("http://ecx.images-amazon.com/images/I/41Cx8mY2UNL._SX324_BO1,204,203,200_.jpg").setDescription("Fahrenheit 451 is a dystopian novel by Ray Bradbury published in 1953. It is regarded as one of his best works. The novel presents a future American society where books are outlawed and \"firemen\" burn any that are found. The title refers to the temperature that Bradbury asserted to be the autoignition temperature of paper. (In reality, scientists place the autoignition temperature of paper anywhere from high 440 degrees Fahrenheit to some 30 degrees hotter, depending on the study and type of paper.)")
    );
    private ExpandablePager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        askLocationPermission();

        FloatingActionButton fabAddArticle = findViewById(R.id.fab_add_article);
        fabAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, AddArticleActivity.class);
                startActivity(intent);
            }
        });

        final List<Book> myList = new ArrayList<>(list);

        //use PageAdapter
        final BookAdapter adapter = new BookAdapter(myList);
        //or FragmentAdapter
        //final BookFragmentAdapter adapter = new BookFragmentAdapter(getSupportFragmentManager(), myList);
        //or FragmentStateAdapter
        //final BookFragmentStateAdapter adapter = new BookFragmentStateAdapter(getSupportFragmentManager(), myList);

        pager = (ExpandablePager) findViewById(R.id.container);
        //pager.setAnimationDuration(duration);
        //pager.setCollapsedHeight((int) getResources().getDimension(R.dimen.header_height));
        //pager.setMode(ExpandablePager.MODE_REGULAR);
        pager.setAdapter(adapter);
        pager.setOnSliderStateChangeListener(new OnSliderStateChangeListener() {

            @Override
            public void onStateChanged(View page, int index, int state) {
               // toggleContent(page, state, duration);
            }

            @Override
            public void onPageChanged(View page, int index, int state) {
               // toggleContent(page, state, 0);
            }
        });

    }


    /**
     * Permissions
     **/

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // personne a déjà refusé

            } else {

                // on ne lui a pas encore posé la question
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        6996);

                // demande les droits à l'utilisateur
            }
        } else {
            // on a déjà le droit !
            getLocation();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 6996: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // il a accepté, charger la position de la personne

                    getLocation();

                } else {

                    Toast.makeText(this, "Vous avez refusé la geolocalisation", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void moveCameraOnUser(Location location) {

        if (mMap == null) {
            mIsWaitingForGoogleMap = true;
            mLastLocation = location;
        } else if (location != null) {

            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(userLocation, 15);

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
            mMap.animateCamera(yourLocation);
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.6014536, 1.4421452000000272), 10));
        }
    }

    private void getLocation() {

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // mettre à jour la position de l'utilisateur
                moveCameraOnUser(location);
                mLocationUser = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Si l'utilisateur à permis l'utilisation de la localisation
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            moveCameraOnUser(location);
                        }
                    });

            // Si l'utilisateur n'a pas désactivé la localisation du téléphone
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                // demande la position de l'utilisateur
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5, locationListener);
            } else {
                Toast.makeText(this, "Geoloc Off", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final MapStyleOptions mapFilter = MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.map_style);
        googleMap.setMapStyle(mapFilter);

        if (mIsWaitingForGoogleMap) {
            moveCameraOnUser(mLastLocation);
        }

        Resources ressource = getApplicationContext().getResources();
        final int widthDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, ressource.getDisplayMetrics());
        final int heightDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, ressource.getDisplayMetrics());

        BitmapDescriptor iconWinNews = BitmapDescriptorFactory.fromResource(R.drawable.winnews);
        BitmapDescriptor iconWinNewsJaune = BitmapDescriptorFactory.fromResource(R.drawable.winnews_jaune);

        mEvent.add(new VideoModel("Le sport rend heureux, c’est mesuré !",
                "Une étude portant sur plus de 500 000 personnes publiée dans leJournal of\n" +"Happiness Studies, a découvert quela pratique d’une activité physique\n" +
                "durant seulement 10 minutes au courant de la semaine peut améliorer\n" + "considérablement les chances de se sentir heureux.",
                "www.youtube.com", "www.google.com", 43.5911392, 1.4434542999999849,0));

        mEvent.add(recieveArticle.getVideoModelsList().get(0));

        for (int i = 0; i < mEvent.size(); i++) {

            Marker eventMark = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mEvent.get(i).getLatitude(), mEvent.get(i).getLongitude()))
                    .title(mEvent.get(i).getTitle())
                    .snippet(mEvent.get(i).getDescription())
                    .icon((BitmapDescriptorFactory.fromBitmap(resizeBitmap("winnews", widthDp, heightDp)))));

            mEventMarker.add(eventMark);
        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                    Location eventLocation = new Location(marker.getTitle());
                    moveCameraOnUser(eventLocation);
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                //Pop up News
                AlertDialog.Builder popup = new AlertDialog.Builder(MapsActivity.this);
                LayoutInflater inflater = (LayoutInflater) MapsActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewpop = inflater.inflate(R.layout.model_layout_article, null);
                popup.setView(viewpop);
                final AlertDialog dialog = popup.create();


                VideoView videoArticle = viewpop.findViewById(R.id.article_video);
                TextView titreArticle = viewpop.findViewById(R.id.article_titlre);
                TextView resumeArticle = viewpop.findViewById(R.id.article_resume);
                TextView lienArticle = viewpop.findViewById(R.id.article_lien_web);

                String markerIdProv = marker.getId();
                String markerID[] = markerIdProv.split("m");
                int markerId = Integer.parseInt(markerID[1]);

                videoArticle.setVideoURI(Uri.parse(mEvent.get(markerId).getLinkVideo()));

                videoArticle.setMediaController(new MediaController(MapsActivity.this));
                videoArticle.requestFocus();
                videoArticle.start();

                titreArticle.setText(mEvent.get(markerId).getTitle());
                resumeArticle.setText(mEvent.get(markerId).getDescription());
                lienArticle.setText(mEvent.get(markerId).getLinkArticle());

                dialog.show();
            marker.setIcon((BitmapDescriptorFactory.fromBitmap(resizeBitmap("winnews_jaune", widthDp, heightDp))));

            }
        });

    }

    public Bitmap resizeBitmap(String drawableName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(drawableName, "drawable", getPackageName()));
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }



}

