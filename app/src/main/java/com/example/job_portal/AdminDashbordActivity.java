package com.example.job_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminDashbordActivity extends AppCompatActivity {



    Button ecoBtn;
    Button softBtn;
    Button statBtn;
    Button elecBtn;
    Button mechBtn;
    Button archBtn;
    Button assistBtn;
    Button journBtn;
    Button back;
    Button rate;

    Button offerEco, appEco;
    Button offerSoft, appSoft;
    Button offerStat, appStat;
    Button offerElect, appElect;
    Button offerMech, appMech;
    Button offerArch, appArch;
    Button offerAssist, appAssist;
    Button offerJour, appJour;

    DatabaseReference db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashbord);



        ecoBtn = findViewById(R.id.manageeconomics);
        softBtn = findViewById(R.id.managesoftware);
        statBtn = findViewById(R.id.managestat);
        elecBtn = findViewById(R.id.manageelect);
        mechBtn = findViewById(R.id.managemechanics);
        archBtn = findViewById(R.id.manageearchitecture);
        assistBtn = findViewById(R.id.manageassistant);
        journBtn = findViewById(R.id.manageejournal);
//        exit = findViewById(R.id.exitDash);
        back = findViewById(R.id.dashBACK);
        rate = findViewById(R.id.DASHRATE);

        offerEco = findViewById(R.id.offereconomics);
        appEco = findViewById(R.id.appeconomics);
        offerStat = findViewById(R.id.offerstat);
        appStat = findViewById(R.id.appstat);
        offerSoft = findViewById(R.id.offersoft);
        appSoft = findViewById(R.id.appsoft);
        offerElect = findViewById(R.id.offerelect);
        appElect = findViewById(R.id.appelect);
        offerMech = findViewById(R.id.offermechanics);
        appMech = findViewById(R.id.appmechanics);
        offerArch = findViewById(R.id.offerarchitec);
        appArch = findViewById(R.id.apparchitect);
        offerAssist = findViewById(R.id.offerassist);
        appAssist = findViewById(R.id.appassist);
        offerJour = findViewById(R.id.offerjournal);
        appJour = findViewById(R.id.appjournal);


        ArrayList<String> categories = new ArrayList<>();
        categories.add("Economics");
        categories.add("Statistics");
        categories.add("Electrical Engineering");
        categories.add("Mechanical Engineering");
        categories.add("Software Engineering");
        categories.add("Architecture");
        categories.add("Admin Assistance");
        categories.add("Journalism");

        db = FirebaseDatabase.getInstance().getReference();

        //EMPLOYEES
        DatabaseReference employees = db.child("Employees");
        //EMPLOYERS
        DatabaseReference employers = db.child("Employers");

        //APPLICATIONS IN THE APP
        DatabaseReference economicsPosts = db.child("POSTULATIONS/" + categories.get(0));
        DatabaseReference statisticsPosts = db.child("POSTULATIONS/" + categories.get(1));
        DatabaseReference electricalPosts = db.child("POSTULATIONS/" + categories.get(2));
        DatabaseReference mechanicalPosts = db.child("POSTULATIONS/" + categories.get(3));
        DatabaseReference softwarePosts = db.child("POSTULATIONS/" + categories.get(4));
        DatabaseReference architecturePosts = db.child("POSTULATIONS/" + categories.get(5));
        DatabaseReference assistantPosts = db.child("POSTULATIONS/" + categories.get(6));
        DatabaseReference journalismPosts = db.child("POSTULATIONS/" + categories.get(7));

        //OFFERS IN THE APP
        DatabaseReference economicsOffers = db.child("OFFERS/" + categories.get(0));
        DatabaseReference statisticsOffers = db.child("OFFERS/" + categories.get(1));
        DatabaseReference electricalOffers = db.child("OFFERS/" + categories.get(2));
        DatabaseReference mechanicalOffers = db.child("OFFERS/" + categories.get(3));
        DatabaseReference softwareOffers = db.child("OFFERS/" + categories.get(4));
        DatabaseReference architectureOffers = db.child("OFFERS/" + categories.get(5));
        DatabaseReference assistantOffers = db.child("OFFERS/" + categories.get(6));
        DatabaseReference journalismOffers = db.child("OFFERS/" + categories.get(7));



        employees.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        employers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //ECONOMIC APPLICATIONS AND OFFERS
        economicsPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appEco.setText(String.valueOf(snapshot.getChildrenCount()));

                appEco.setOnClickListener(view -> {
                    String categoy = "Economics";
                    Intent intent = new Intent(AdminDashbordActivity.this, PostulationsDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("CATEGORY",(Serializable)categoy);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        economicsOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> catInfo = new ArrayList<>();

                offerEco.setText(String.valueOf(snapshot.getChildrenCount()));
                catInfo.add("Economics Department");
                catInfo.add(appEco.getText().toString());
                catInfo.add(offerEco.getText().toString());
                ecoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminDashbordActivity.this, CategoryDetailsActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("CATINFORMATION",(Serializable)catInfo);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                    }
                });
                offerEco.setOnClickListener(view -> {
                    startActivity(new Intent(AdminDashbordActivity.this, EconomicsActivity.class));
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //SOFTWARE ENGINEERING APPLICATIONS AND OFFERS
        softwarePosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appSoft.setText(String.valueOf(snapshot.getChildrenCount()));
                appSoft.setOnClickListener(view -> {
                    String categoy = "Software Engineering";
                    Intent intent = new Intent(AdminDashbordActivity.this, PostulationsDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("CATEGORY",(Serializable)categoy);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        softwareOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                offerSoft.setText(String.valueOf(snapshot.getChildrenCount()));
                ArrayList<String> catInfo = new ArrayList<>();
                catInfo.add("Software Engineering Department");
                catInfo.add(appSoft.getText().toString());
                catInfo.add(offerSoft.getText().toString());
                softBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminDashbordActivity.this, CategoryDetailsActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("CATINFORMATION",(Serializable)catInfo);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                    }
                });
                offerSoft.setOnClickListener(view -> {
                    startActivity(new Intent(AdminDashbordActivity.this, SoftwareEngineeringActivity.class));
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //STATISTICS APPLICATIONS AND OFFERS
        statisticsPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appStat.setText(String.valueOf(snapshot.getChildrenCount()));
                appStat.setOnClickListener(view -> {
                    String categoy = "Statistics";
                    Intent intent = new Intent(AdminDashbordActivity.this, PostulationsDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("CATEGORY",(Serializable)categoy);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        statisticsOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offerStat.setText(String.valueOf(snapshot.getChildrenCount()));
                ArrayList<String> catInfo = new ArrayList<>();
                catInfo.add("Statistics Department");
                catInfo.add(appStat.getText().toString());
                catInfo.add(offerStat.getText().toString());
                statBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminDashbordActivity.this, CategoryDetailsActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("CATINFORMATION",(Serializable)catInfo);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                    }
                });
                offerStat.setOnClickListener(view -> {
                    startActivity(new Intent(AdminDashbordActivity.this, StatisticsActivity.class));
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //ELECTRICAL ENGINEERING APPLICATIONS AND OFFERS
        electricalPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appElect.setText(String.valueOf(snapshot.getChildrenCount()));
                appElect.setOnClickListener(view -> {
                    String categoy = "Electrical Engineering";
                    Intent intent = new Intent(AdminDashbordActivity.this, PostulationsDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("CATEGORY",(Serializable)categoy);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        electricalOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offerElect.setText(String.valueOf(snapshot.getChildrenCount()));
                ArrayList<String> catInfo = new ArrayList<>();
                catInfo.add("Electrical Engineering Department");
                catInfo.add(appElect.getText().toString());
                catInfo.add(offerElect.getText().toString());
                elecBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminDashbordActivity.this, CategoryDetailsActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("CATINFORMATION",(Serializable)catInfo);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                    }
                });
                offerElect.setOnClickListener(view -> {
                    startActivity(new Intent(AdminDashbordActivity.this, ElectricalActivity.class));
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //MECHANICAL ENGINEERING AND OFFERS
        mechanicalPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appMech.setText(String.valueOf(snapshot.getChildrenCount()));
                appMech.setOnClickListener(view -> {
                    String categoy = "Mechanical Engineering";
                    Intent intent = new Intent(AdminDashbordActivity.this, PostulationsDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("CATEGORY",(Serializable)categoy);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mechanicalOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offerMech.setText(String.valueOf(snapshot.getChildrenCount()));
                ArrayList<String> catInfo = new ArrayList<>();
                catInfo.add("Mechanical Engineering Department");
                catInfo.add(appMech.getText().toString());
                catInfo.add(offerMech.getText().toString());
                mechBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminDashbordActivity.this, CategoryDetailsActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("CATINFORMATION",(Serializable)catInfo);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                    }
                });
                offerMech.setOnClickListener(view -> {
                    startActivity(new Intent(AdminDashbordActivity.this, MechanicalActivity.class));
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //ARCHITECTURE APPLICATIONS AND OFFERS
        architecturePosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appArch.setText(String.valueOf(snapshot.getChildrenCount()));
                appArch.setOnClickListener(view -> {
                    String categoy = "Architecture";
                    Intent intent = new Intent(AdminDashbordActivity.this, PostulationsDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("CATEGORY",(Serializable)categoy);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        architectureOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offerArch.setText(String.valueOf(snapshot.getChildrenCount()));
                ArrayList<String> catInfo = new ArrayList<>();
                catInfo.add("Architecture Department");
                catInfo.add(appArch.getText().toString());
                catInfo.add(offerArch.getText().toString());
                archBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminDashbordActivity.this, CategoryDetailsActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("CATINFORMATION",(Serializable)catInfo);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                    }
                });
                offerArch.setOnClickListener(view -> {
                    startActivity(new Intent(AdminDashbordActivity.this, ArchitectureActivity.class));
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //ADMIN ASSISTANCE APPLICATIONS AND OFFERS
        assistantPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appAssist.setText(String.valueOf(snapshot.getChildrenCount()));
                appAssist.setOnClickListener(view -> {
                    String categoy = "Admin Assistance";
                    Intent intent = new Intent(AdminDashbordActivity.this, PostulationsDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("CATEGORY",(Serializable)categoy);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        assistantOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offerAssist.setText(String.valueOf(snapshot.getChildrenCount()));
                ArrayList<String> catInfo = new ArrayList<>();
                catInfo.add("Administration Department");
                catInfo.add(appAssist.getText().toString());
                catInfo.add(offerAssist.getText().toString());
                assistBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminDashbordActivity.this, CategoryDetailsActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("CATINFORMATION",(Serializable)catInfo);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                    }
                });
                offerAssist.setOnClickListener(view -> {
                    startActivity(new Intent(AdminDashbordActivity.this, AdminAssistanceActivity.class));
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //JOURNALISM APPLICATIONS AND OFFERS
        journalismPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appJour.setText(String.valueOf(snapshot.getChildrenCount()));
                appJour.setOnClickListener(view -> {
                    String categoy = "Journalism";
                    Intent intent = new Intent(AdminDashbordActivity.this, PostulationsDetailsActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("CATEGORY",(Serializable)categoy);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        journalismOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offerJour.setText(String.valueOf(snapshot.getChildrenCount()));
                ArrayList<String> catInfo = new ArrayList<>();
                catInfo.add("Journalism Department");
                catInfo.add(appJour.getText().toString());
                catInfo.add(offerJour.getText().toString());
                journBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminDashbordActivity.this, CategoryDetailsActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("CATINFORMATION",(Serializable)catInfo);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                    }
                });
                offerJour.setOnClickListener(view -> {
                    startActivity(new Intent(AdminDashbordActivity.this, JournalismActivity.class));
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        rate.setOnClickListener(view -> {
            startActivity(new Intent(AdminDashbordActivity.this, RatingReviewsActivity.class));
        });

        back.setOnClickListener(view -> {
            finish();
        });



    }
}