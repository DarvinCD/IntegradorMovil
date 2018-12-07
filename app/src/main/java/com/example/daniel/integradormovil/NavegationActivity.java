package com.example.daniel.integradormovil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


import com.example.daniel.integradormovil.cetes.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NavegationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentMetas.OnFragmentInteractionListener,
FragmentAhorro.OnFragmentInteractionListener,FragmentPerfil.OnFragmentInteractionListener,
        CalculadoraFragment.OnFragmentInteractionListener, RendimientoFragment.OnFragmentInteractionListener,
        FragmentHome.OnFragmentInteractionListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       // drawer.addDrawerListener(toggle);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        FragmentManager fragmentManager= getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main,new FragmentPerfil()).commit();
        setTitle("Perfil");
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
        getMenuInflater().inflate(R.menu.navegation, menu);
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
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(NavegationActivity.this,LoginActivity.class));
        }
        else {
            if (id == R.id.action_conexión) {
                startActivity(new Intent(NavegationActivity.this,WebConexionActivity.class));
            }
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
       // int duracion=1;
        //String txt="";

        boolean FragmentTransaction = false;
        Fragment fragment = null;

//        if (id == R.id.nav_home) {
            // Handle the camera action
            //  txt= "Seleccionaste perfil";
            //duracion= Toast.LENGTH_SHORT;
      //      fragment = new FragmentHome();
    //        FragmentTransaction = true;
  //      }

         if (id == R.id.nav_camera) {
            // Handle the camera action
          //  txt= "Seleccionaste perfil";
            //duracion= Toast.LENGTH_SHORT;
            fragment = new FragmentPerfil();
            FragmentTransaction = true;
        }

        else if (id == R.id.nav_gallery) {

            //txt= "Seleccionaste Ahorro";
            //duracion= Toast.LENGTH_SHORT;
            fragment = new FragmentAhorro();
            FragmentTransaction = true;
        }

        else if (id == R.id.nav_slideshow) {

            //txt= "Seleccionaste Meta";
            //duracion= Toast.LENGTH_SHORT;
            fragment = new FragmentMetas();
            FragmentTransaction = true;
        }

        else if (id == R.id.nav_rendimiento) {

           // txt= "Seleccionaste Meta";
          //  duracion= Toast.LENGTH_SHORT;
            fragment = new RendimientoFragment();
            FragmentTransaction = true;
        }

        else if (id == R.id.nav_calculadora) {

            // txt= "Seleccionaste Meta";
            //  duracion= Toast.LENGTH_SHORT;
            fragment = new CalculadoraFragment();
            FragmentTransaction = true;

        }

         else if (id == R.id.nav_cetes) {

             // txt= "Seleccionaste Meta";
             //  duracion= Toast.LENGTH_SHORT;
             fragment = new CompraFragment();
             FragmentTransaction = true;

         }

       // else if (id == R.id.nav_manage) {

          //  txt= "Seleccionaste Inversiones";
            //duracion= Toast.LENGTH_SHORT;
        //}

       // else if (id == R.id.nav_share) {

            //txt= "Seleccionaste Compartir";
            //duracion= Toast.LENGTH_SHORT;
        //}

        //else if (id == R.id.nav_send) {

            //txt= "Seleccionaste Comprar cetes";
            //duracion= Toast.LENGTH_SHORT;
        //}

        if (FragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

     //   Toast.makeText(this,txt,duracion).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
