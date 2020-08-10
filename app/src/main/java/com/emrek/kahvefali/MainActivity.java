package com.emrek.kahvefali;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.emrek.kahvefali.ui.DownloadData;
import com.emrek.kahvefali.ui.main.BounceInterpolator;
import com.emrek.kahvefali.ui.main.Fortunes;
import com.emrek.kahvefali.ui.main.GetFortunes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.text.Line;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.emrek.kahvefali.ui.main.SectionsPagerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<GetFortunes> otherArrayList, userArrayList;
    CoordinatorLayout coordinatorLayout;
    DownloadData downloadData;


    TextView pictured;
    View prob_view;
    GridLayout gridLayout;


    public static String result = "", searchFortune = "", deviceID = "";
    public static boolean isClick = false;

    public static SharedPreferences sharedPreferences;

    Dialog dialog;

    FloatingActionButton fab1, fab2;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;

    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;
    RoundedImageView bottomImage;
    TextView bottomHeader, bottomText, galleryText, cameraText, result1, result2, result3, result4, result5;
    View bottomView;
    Uri imageURI;

    byte[] fileInBytes;

    public static ConnectivityManager connectivityManager;


    LottieAnimationView lottieAnimationView;
    public static DatabaseReference userDatabaseRef, otherDatabaseRef;
    private StorageReference mStorageRef;
    Fortunes fortunes;

    String[] fortune_array = {"Lampshade\n" + "A job that will be created with an idea that will come to mind at an unexpected moment will yield positive results. Earnings will be obtained.", "Monument\n" +
            "It means getting a good career. It means doing business with the government and making big profits, or benefiting a community.", "Pain\n" +
            "Seeing geometric angles gives information about the profession of the person who drinks coffee.", "Island\n" +
            "The money that will come from a place you never expected indicates the property.", "Vow\n" +
            "It means entering a good job. It is also interpreted as a devoted but unfulfilled votive.", "Man\n" +
            "If the person who reads your fortune is a woman, this figure can be interpreted as the man in her life. If the person who reads your fortune is a male, this figure may represent a man or friend who can guide when consulted. In both cases, the interpretation should be expanded based on the posture, what it carries, and other items around it.", "Network\n" +
            "Most commentators think that this is a trap sign. There are people around the person who want to fool him. It is useful to be careful. If this is a fishing net, it suggests that you will make money with the sweat of your forehead, that you have to be careful because of your worries and trickery. Networking the sea is a lucrative job.", "Tree\n" +
            "Since the tree is the ornament and dress of the soil, its interpretation means that the costume is improved. Or he has to pay more attention to the costume. In particular, he needs to give more importance to his jewelry.", "Woodpecker\n" +
            "It is interpreted that there will be good days in the future. For some commentators, it is explained in the sense that one of your relatives will be damaged due to the disclosure of some information that should remain confidential due to their chattering.", "Lament\n" +
            "Seeing someone lamented is interpreted as bad news.", "Mouth\n" +
            "A closed mouth discusses with the relatives, the female lip indicates a happy draw, and a small mouth indicates a fertile gain.", "Cry\n" +
            "It means that you will get rid of the remorse created by your mistakes.", "Chef\n" +
            "You will get happiness as a result of a job you have done or are interested in recently.", "Handset\n" +
            "It is a sign that you will get good news.", "Wooden house\n" +
            "A beautiful marriage marks a respectable and well-established family life.", "Octopus\n" +
            "These days you will get into some mixed jobs, be careful.", "Family\n" +
            "You will receive good news.", "Stream\n" +
            "It is an increase in fertility because of the melting of rain, snow and glaciers. If it goes right in the middle of the coffee cup, the abundance and abundance that will come will be quite a while. If there is a line to the bottom of the cup, then there will be a temporary abundance and abundance.", "Vulture\n" +
            "Indicates a danger that will result in material loss. A thief can enter the house.", "Scorpion\n" +
            "It is poison and danger that comes to mind immediately. When interpreting this, it is necessary to interpret the opposite as much as possible. So as good luck and beauty. If the scorpion appears in the household, the symbol of the fortune tells that it is necessary to get along with the household.", "Regiment\n" +
            "Soldier regiment; represents a relative or job in the military. Wedding procession; is a sign that the family will expand, it is spacious and peaceful. Funeral rites; the pain is interpreted in the news.", "Realm\n" +
            "Seeing the mosque minaret, dome, or flag on the flag; is a sign of peace and comfort in life.", "Tool\n" +
            "Since people are the biggest help they use in daily life, their interpretation also means getting help or helping. Here it is necessary to interpret together with the previous form of fortune telling.", "Flame\n" +
            "You will have a great and passionate love.", "Received\n" +
            "To see wound-like darkness on the forehead; to see a shame and annoyance in the forehead; Whether he will have a son or grandson, black forehead and deceit, bright forehead; good luck and a help.", "Six\n" +
            "You will receive a new friendship or news that you haven't seen in a long time. Through your friends, your aspirations and desires will come true. Marriage.", "Gold\n" +
            "It is a sign of health and safety. According to another comment; abundance, getting rid of your troubles and troubles; is a sign that your requests will be met soon. Gold bracelet; it is a long-term job or a profession to be gained. There are also those who interpret it as a loaded heritage.", "Altıparmak\n" +
            "It is a sign that it will be a difficult situation and the order will be disrupted.", "Wedding ring\n" +
            "You will open up with the person you are with.", "Operation\n" +
            "Seeing someone undergoing surgery; it means that a great support will come to the surgeon.", "Bulb\n" +
            "Your troubles will end, you will have happy and joyful days.", "Key\n" +
            "Your wishes and desires will be accepted. It may be moving, buying or renting a house.", "Tell\n" +
            "Seeing someone telling you something; it is a sign that the opportunities are not evaluated because time passes with empty delusions.", "Mom\n" +
            "Seeing a mother with her child in her arms; is a safe and peaceful environment. In case of mutual dialogue, it is interpreted that an expected news will be positive. A figure embraced by a mother with a small child is a sign of a happy news and positive developments.", "Antenna\n" +
            "It is a sign that feelings will intensify and perceptions will open.", "Antique\n" +
            "Seeing things that look like antiques; Although the comments that vary according to the type and shape of the item are varied, it is the harbinger of deep-rooted events.", "Epaulette\n" +
            "It indicates the promotion of his / her relatives or the progress of his business.", "Car\n" +
            "Displacement. Moving from one place to another. It also symbolizes job change.", "Aryan\n" +
            "It symbolizes diligence. You get rid of the troubles. You will get the reward of your work. In addition, a comment means that much work should be done in the summer.", "Back\n" +
            "Someone looking back; it refers to separation, resentment or evil. The brightness of the person facing back is a sign of innovation. It means a new job, home or property. It means that trouble will go away.", "Rigging\n" +
            "The unexpected surprise is the herald of a gift.", "Pear\n" +
            "Your financial expectation will result in a positive outcome. Luck is on your side.", "Harp\n" +
            "Love, happiness.", "Land\n" +
            "It indicates that the troubles will be over and the business life will improve.", "Plus sign\n" +
            "You will get very sad news. This may be the death news of a relative, encounter with a serious illness, anxiety and concern.", "Baton\n" +
            "It is the symbol of leadership. He's just a tough leader. It also means that it is very authoritarian.", "Chef\n" +
            "It indicates that the time spent collecting the products and the works done recently has approached metaphorically as the time of obedience is approaching.", "Soldier\n" +
            "A tough struggle awaits the person. He has to work hard and fight well.", "Lion\n" +
            "Force is a symbol of pride, generosity and hunting.", "Lion's Head\n" +
            "It is interpreted that you can get what you want. Murat is. Realization of aspirations and desires means remote guests, a new friend.", "Vine Tree\n" +
            "It is a sign of a deep-rooted relationship with a loved one, to do permanent works.", "Padlock\n" +
            "Hard to overcome is a sign of obstacles.", "Horse\n" +
            "It is the most beautiful form of nobility and elegance. The horse, which seems to be running on the falda, expresses that the person will do a good stampede if he is single, if he is married, he will be very well appreciated.", "Horseshoe\n" +
            "An auspicious event or an efficient job is waiting for you", "Fire\n" +
            "It is an alarm. And it is also warning. Indicates that the road made or traveled is incorrect. It is a warning that danger can be encountered at any moment.", "Jump\n" +
            "Jump from one place to another; change of social status, jump down; the complete reversal of the financial situation, jumping from the mountain or the wall; interpreted as comfort.", "Equestrian\n" +
            "The beginning of new friendship, you will receive news from a friend you have not seen for a long time.", "Hawk\n" +
            "It symbolizes a rich life. Keeping hawk in your hand; It symbolizes overcoming difficulties and achieving the difficult one.", "Hunting\n" +
            "You have neglected your works so much, you should now meet.", "Hunting Trap\n" +
            "It is a sign of being deceived by someone.", "Hunter\n" +
            "You will soon find a favor, you will finally reach what you expect.", "Chandelier\n" +
            "It is a sign of success. Refreshment icons.", "Handful\n" +
            "You have to get away from the negative habits and friends.", "Moon\n" +
            "It points to brightness. Half a month is a sign that it is more time to get comfortable.", "Sunflower\n" +
            "When it opens, it symbolizes rejoicing.", "Foot\n" +
            "In a bad situation, it is interpreted that it will meet someone in need, and if it is to be helped, it should not be done for the sake of a benefit, with the expectation of a response.", "Footprint\n" +
            "You will have troubles arising from your friends or work. Be careful.", "Shoe\n" +
            "It means that the guest will come if he / she exits inside the house, and that he / she will go if he / she leaves the house.", "Bear\n" +
            "Since there are 42 females like dogs, if the wish is made, it is a sign that a great wish will be fulfilled on the 42nd day at the end of the 41st day.", "Mirror\n" +
            "It is a sign that you are living in the dream world, getting away from the truth and it is necessary to reckon.", "Quince\n" +
            "It is the harbinger of distress caused by sadness.", "Read on\n" +
            "It means building a new home to build a house. The smoking chimney means it will have a happy marriage. If there is no smoke from the chimney, it indicates a marriage that may be a little ahead.", "Leg\n" +
            "You may suffer loss of property during this time.", "Almond\n" +
            "This is the right time for every new breakthrough you think about yourself.", "Garden\n" +
            "It is a sign of safety and a peaceful life.", "Garden Beli\n" +
            "To be hardworking; usually followed by the prize money.", "Garden door\n" +
            "It's a problem that will be resolved in a pleasant way.", "Gob\n" +
            "It is interpreted as a symbol of love. It is interpreted as a sign that there will be excessive attachment and therefore lead to an untimely break from the rest of life.", "Honeycomb\n" +
            "It is a sign that efforts will not be wasted, especially in business life, that they will be rewarded.", "Whale\n" +
            "It is a great fortune. It could be a national lottery or a beautiful marriage. It is expressed as the biggest bird of fortune because it is the biggest fish living in the sea.", "The fish\n" +
            "Anything is money that will enter the household. It is destiny. Evaluation is made according to its size.", "Fish man\n" +
            "An attempt will be made for a good and good job, but it requires very good research. The result will be very good.", "Fish Net\n" +
            "The fishing net indicates that you will earn money with the sweat of your forehead, that you need to be careful because of your anxieties, and deceit. Netting in the sea means getting a lucrative business.", "Balloon\n" +
            "As the name suggests, it is unfounded news. If it is next to any symbol, it means that the news about that symbol is unfounded.", "ax\n" +
            "There is a chance that you are out of business, after a short trouble things will be fine.", "Sledgehammer\n" +
            "It is a sign of a very troublesome and physically heavy work.", "Call\n" +
            "It is a sign that you will relax with a news you will receive.", "Bathroom\n" +
            "It is a sign of disappointment, one should be careful.", "Dam\n" +
            "It means that money that has been saved for a long time for bad days will be spent unnecessarily.", "Baraka\n" +
            "It is a sign that you will eventually encounter a bad situation with a waste of money.", "Glass\n" +
            "The empty glass is expected to result in a job done. If the glass is full, the result is clear in a very short time.", "Barricade\n" +
            "There are obstacles to the work you want to do.", "Head\n" +
            "You will have a lot of money, boy on the way.", "Spike\n" +
            "Abundance, peace and abundance await you.", "Step\n" +
            "Your wishes will come true step by step, no worries.", "Hood\n" +
            "It symbolizes the need to be cautious. There is no need to worry too much, because events that require action are predictable events.", "Walking stick\n" +
            "It means that he will be a gentle and loving leader and also a much loved leader. It means a very high future and a healthy person.", "Swamp\n" +
            "It is a sign of financial difficulties resulting from arbitrary actions.", "Setting sun\n" +
            "Some of your requests will come true in a short time.", "Suitcase\n" +
            "You will make serious decisions, try to make the right decision.", "Owl\n" +
            "It will come in the near future. But they will be able to meet this problem easily.", "Flag\n" +
            "News from the official office. Starting public office. Doing a job with the court. Only this symbol is in good direction. It is absolutely not sad. More precisely, it symbolizes a good news coming from here.", "Baby\n" +
            "If he is married, he will have a child very soon. If he is single, he will have a baby with a niece or a loved one. It also symbolizes baby innocence.", "Barber\n" +
            "It means being related to someone in high rank.", "Five\n" +
            "Rumors, empty conversations.", "Five Points\n" +
            "The news you expect from a distant place is finally coming.", "Pentagon\n" +
            "You will not return from the decisions you have made about your business and you will be happy.",
            "Cradle\n" +
                    "Not having the job he wants to do. It wobbles like a cradle. One should not try to do something like partnership. Because that month only needs to be put on hold.",
            "Pepper\n" +
                    "It indicates that there are lying, deceitful and dishonest people around and that they should be avoided.",
            "Brain\n" +
                    "Symbolizes the logic and the mind.",
            "Bracelet\n" +
                    "It is a sign of marriage. If he is married, it indicates that he will have a boy.",
            "Building\n" +
                    "When you see a building in fortune telling, the person who turns off the fortune will buy any immovable. The darkness and lightness of the building shows its value, if it is dark, it means that if it is very precious, very light and without grounds, a real estate with very low financial value will be bought.",
            "One\n" +
                    "to be loved.",
            "Intersecting Straight Lines\n" +
                    "A good life, wealth awaits you.",
            "Bicycle\n" +
                    "Slow news. This news is a slow news that should be received in advance, and is now a bit out of date.",
            "Knife\n" +
                    "A long-standing job will come to an end.",
            "Whiskers\n" +
                    "It is a sign of acquiring property.",
            "Insect\n" +
                    "All sorts of troubles and sorrows. Distress can be interpreted according to the size and size of the insect. More precisely, it is very important that the simulated shape is inside and outside the household.",
            "bull\n" +
                    "It depicts the potency and the height of the sex power. Its sexuality is very high. Even if it occurs in men or women, it is necessary to pay attention to that person. It is also a symbol of wealth.",
            "Pack\n" +
                    "It means an unexpected guest coming from a distant place.",
            "Bead\n" +
                    "It is a small but permanent sign of happiness.",
            "Borazan\n" +
                    "It is a sign of dealing with a bad event.",
            "Horn\n" +
                    "Sometimes you unwittingly say your opinion about others; watch out for people prone to accept these as gossip.",
            "Wheat\n" +
                    "You will have a lot of money in financial terms.",
            "Bouquet\n" +
                    "Someone you will meet new can tempt your mind and amaze you.",
            "Bulldog Dog\n" +
                    "Know the value of the friends around you.",
            "Cloud\n" +
                    "You will shed tears, and then you will get relief.",
            "Zodiac sign\n" +
                    "Being in a fortress; If there is a seriously ill, it indicates that he will die or worry about the situation.",
            "Nose\n" +
                    "You will get help from someone with a career in your area.",
            "A Big Circle\n" +
                    "There are events that will cause you trouble, even for a short time.",
            "A Big Square\n" +
                    "Opportunities to own a property are coming towards you.",
            "A Big Point\n" +
                    "You will receive good news.",
            "A refrigerator\n" +
                    "The cold coming around. If it is in the household, it means that a very cold air blows or blows in the house, and if it is outside the home, it means a cold air related to business life."
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == 1 && resultCode == RESULT_OK && data.getData() != null) {
                lottieAnimationView.setVisibility(View.VISIBLE);
                imageURI = data.getData();
                FirebaseVisionImage image;
                try {
                    image = FirebaseVisionImage.fromFilePath(getApplicationContext(), data.getData());
                    FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
                            .getOnDeviceImageLabeler();


                    labeler.processImage(image)
                            .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                                @Override
                                public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                                    result = "";

                                    for (FirebaseVisionImageLabel label : labels) {
                                        String text = label.getText();
                                        String entityId = label.getEntityId();
                                        float confidence = label.getConfidence();
                                        int t = (int) (confidence * 10000);
                                        double d = (double) t / 100;
                                        result += text + "\n" + d + "%,";
                                        fortuneCheck(text);
                                    }
                                    if (searchFortune != null && searchFortune != "") {
                                        bottomView.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.view_background));
                                        imageUploadToFirebase();
                                        isClick = true;

                                    } else {
                                        bottomView.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.view_sorry_background));
                                        bottomImage.setImageResource(R.drawable.sorry);
                                        bottomHeader.setText("There were no results");
                                        bottomText.setText("Sorry, no results match the picture!");
                                    }

                                    lottieAnimationView.setVisibility(View.INVISIBLE);

                                    bottomSheetDialog.show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        final FloatingActionButton fab = findViewById(R.id.fab);

        downloadData = new DownloadData();
        downloadData.execute();
        tekrarla();

        otherArrayList = new ArrayList<>();
        userArrayList = new ArrayList<>();



        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        coordinatorLayout = findViewById(R.id.main_coordinator);
        sharedPreferences = this.getSharedPreferences("com.emrek.kahvefali", Context.MODE_APPEND);

        dialog = new Dialog(this);
        if (!sharedPreferences.getBoolean("state", false)) {
            showDialog();
        }

        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        galleryText = findViewById(R.id.gallery);
        cameraText = findViewById(R.id.camera);

        bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_layout, (LinearLayout) findViewById(R.id.bottomSheetContainer));
        bottomHeader = bottomSheetView.findViewById(R.id.bottom_header);
        bottomImage = bottomSheetView.findViewById(R.id.bottom_image);
        bottomText = bottomSheetView.findViewById(R.id.bottom_text);
        bottomView = bottomSheetView.findViewById(R.id.bottomView);


        result1 = bottomSheetView.findViewById(R.id.result1);
        result2 = bottomSheetView.findViewById(R.id.result2);
        result3 = bottomSheetView.findViewById(R.id.result3);
        result4 = bottomSheetView.findViewById(R.id.result4);
        result5 = bottomSheetView.findViewById(R.id.result5);

        pictured = bottomSheetView.findViewById(R.id.pictured);
        prob_view = bottomSheetView.findViewById(R.id.prob_view);
        gridLayout = bottomSheetView.findViewById(R.id.gridlayout);

        bottomSheetDialog.setContentView(bottomSheetView);


        setAnimation(bottomView);
        setAnimation(galleryText);
        setAnimation(cameraText);


        lottieAnimationView = findViewById(R.id.lottie);
        deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        mStorageRef = FirebaseStorage.getInstance().getReference("pictures");
        userDatabaseRef = FirebaseDatabase.getInstance().getReference("userDeviceID/" + deviceID);
        otherDatabaseRef = FirebaseDatabase.getInstance().getReference("otherFortune");


        fortunes = new Fortunes();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen)
                    fab.startAnimation(rotateBackward);
                else
                    fab.startAnimation(rotateForward);

                animateFab();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnection()) {
                    choosePicture();
                } else
                    Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();

                animateFab();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInternetConnection()) {

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 2);
                    } else {
                        searchFortune = "";
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(camera, 1);
                    }
                } else
                    Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_SHORT).show();
                // Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();

                animateFab();
            }
        });


        downloadFortunes();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(61, 70, 120));
        }


    }

    synchronized private void fortuneCheck(String getText) {

        System.out.println(getText.toLowerCase());
        for (int i = 0; i < fortune_array.length; i++) {

            String[] words = fortune_array[i].split("\n");
            for (int j = 0; j < words.length; j++) {
                if (getText.toLowerCase().matches(words[j].toLowerCase())) {
                    searchFortune += fortune_array[i] + " ";
                    System.out.println("search fortune : " + searchFortune);
                    break;
                }
            }


        }
    }

    public void openResult(View view) {
        bottomSheetDialog.show();
    }

    private void lowQualityImage() {

        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageURI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 15, baos);
            fileInBytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void imageUploadToFirebase() {
        final String[] words = searchFortune.split("\n");
        bottomImage.setImageURI(imageURI);
        bottomText.setText(words[1]);
        bottomHeader.setText(words[0]);

        setProbability();

        final StorageReference riversRef = mStorageRef.child(imageURI.getPath());
        lowQualityImage();

        riversRef.putBytes(fileInBytes)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onSuccess(Uri uri) {
                                DateFormat df = null;
                                String date = "";
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                    df = new SimpleDateFormat("d.MM.yyyy, HH:mm");
                                    date = df.format(Calendar.getInstance().getTime());
                                }
                                fortunes.setUser(sharedPreferences.getString("user", "unknown"));
                                fortunes.setFortune_time(date);
                                fortunes.setFortuneImageUrl(uri.toString());
                                fortunes.setFortune_header(words[0]);
                                fortunes.setFortune_text(words[1]);
                                userDatabaseRef.push().setValue(fortunes);
                                otherDatabaseRef.push().setValue(fortunes);
                                downloadFortunes();

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    public static void downloadFortunes() {

        otherArrayList.clear();
        userArrayList.clear();

        otherDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    GetFortunes fort = ds.getValue(GetFortunes.class);
                    otherArrayList.add(new GetFortunes(fort.getFortune_header(), fort.getFortune_text(), fort.getFortuneImageUrl(), fort.getFortune_time(), fort.getUser()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        userDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    GetFortunes fort = ds.getValue(GetFortunes.class);
                    userArrayList.add(new GetFortunes(fort.getFortune_header(), fort.getFortune_text(), fort.getFortuneImageUrl(), fort.getFortune_time(), fort.getUser()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void animateFab() {
        if (isOpen) {
            galleryText.startAnimation(fabClose);
            cameraText.startAnimation(fabClose);
            fab1.startAnimation(fabClose);
            fab2.startAnimation(fabClose);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isOpen = false;
        } else {
            galleryText.startAnimation(fabOpen);
            cameraText.startAnimation(fabOpen);
            fab1.startAnimation(fabOpen);
            fab2.startAnimation(fabOpen);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isOpen = true;

        }
    }

    private void setAnimation(View view) {
        AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();
    }

    public static boolean isInternetConnection() {

        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    private void showDialog() {
        final EditText name, surname;
        final Button okey;
        LinearLayout popup;
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        BounceInterpolator bounceInterpolator = new BounceInterpolator(20, 0.2);
        animation.setInterpolator(bounceInterpolator);

        dialog.setContentView(R.layout.custom_popup_menu);
        popup = dialog.findViewById(R.id.popupLayout);
        name = dialog.findViewById(R.id.name);
        surname = dialog.findViewById(R.id.surname);
        okey = dialog.findViewById(R.id.button_ok);
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putBoolean("state", true).apply();
                sharedPreferences.edit().putString("user", name.getText().toString().toUpperCase() + " " + surname.getText().toString().toUpperCase()).apply();
                dialog.dismiss();
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0 && surname.getText().toString() != null && surname.getText().toString() != "") {
                    okey.setEnabled(true);
                    okey.setBackground(getResources().getDrawable(R.drawable.button_background));
                } else {
                    okey.setEnabled(false);
                    okey.setBackground(getResources().getDrawable(R.drawable.button_disabled_background));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0 && name.getText().toString() != "" && name.getText().toString() != null) {
                    okey.setEnabled(true);
                    okey.setBackground(getResources().getDrawable(R.drawable.button_background));
                } else {
                    okey.setEnabled(false);
                    okey.setBackground(getResources().getDrawable(R.drawable.button_disabled_background));
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        setAnimation(popup);
        dialog.show();
        popup.startAnimation(animation);
    }

    private void choosePicture() {
        searchFortune = "";
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "select"), 1);
    }

    public void tekrarla() {

        final Handler handler = new Handler();
        Timer timer;


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                        } catch (Exception e) {
                        }


                    }
                });
            }
        };

        timer = new Timer();

        timer.schedule(timerTask, 1000, 1000);

    }

    private void setProbability() {

        pictured.setVisibility(View.VISIBLE);
        prob_view.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);

        String[] dizi1 = result.split(",");
        String[] dizi2;
        dizi2 = dizi1[0].split("\n");
        result1.setText(dizi2[0] + "\n" + dizi2[1]);
        dizi2 = dizi1[1].split("\n");
        result2.setText(dizi2[0] + "\n" + dizi2[1]);
        dizi2 = dizi1[2].split("\n");
        result3.setText(dizi2[0] + "\n" + dizi2[1]);
        dizi2 = dizi1[3].split("\n");
        result4.setText(dizi2[0] + "\n" + dizi2[1]);
        dizi2 = dizi1[4].split("\n");
        result5.setText(dizi2[0] + "\n" + dizi2[1]);

    }


}

