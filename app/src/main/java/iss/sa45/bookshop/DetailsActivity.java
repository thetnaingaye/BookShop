package iss.sa45.bookshop;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.util.HashMap;

public class DetailsActivity extends Activity {

    final static int CAPTURE_QRCODE = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsfragment);
        Intent intent = getIntent();
        if (intent.hasExtra("details")) {
            HashMap<String, String> m = (HashMap<String,String>)intent.getSerializableExtra("details");
            Bundle args = new Bundle();
            args.putSerializable("item", m);
            Fragment f = new DetailsFragment();
            f.setArguments(args);
            getFragmentManager().beginTransaction()
                    .add(R.id.detailsframe2, f)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchInput).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }


        // Here we are listening if its close. Cos if it is, we are gonna set the
        // in the list back to its original state (populate everything)
        MenuItem searchMenuItem = menu.findItem(R.id.searchInput);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                // Gotta find our fragment activity in order to call our method.
                ListingFragment ls = (ListingFragment) getFragmentManager()
                        .findFragmentById(R.id.fragment1);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                return true;
            }
        });


        // Will listen to any input entered by the user into the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // 'true' here keeps the SearchView in focus
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {

                // Directing to SearchActivity with our string query
                if (query != null)
                {

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("sQuery", query);
                    startActivity(intent);
                }

                // 'false' here stops the focus on SearchView (exits it)
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.option1:

                final Dialog d = new Dialog(DetailsActivity.this);
                d.setTitle(getString(R.string.customdialogtitle));
                d.setContentView(R.layout.customedialog);
                d.setCancelable(true);
                Button btnFind = (Button) d.findViewById(R.id.buttonFind);
                btnFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Spinner s =(Spinner) d.findViewById(R.id.spinner1);
                        int index = s.getSelectedItemPosition();

                        Resources res = getResources();
                        String[] va = res.getStringArray(R.array.category);
                        String catIndex ="";
                        if(index != 0) {
                            catIndex = "" + (index);
                        }



                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("categoryId" , catIndex);
                        startActivity(intent);
                        d.dismiss();
                    }
                });
                d.show();
                return true;

            case R.id.option2:   // QR Code

                Intent intent = new Intent("la.droid.qr.scan");
                intent.putExtra("la.droid.qr.complete", true);
                try {
                    startActivityForResult(intent, CAPTURE_QRCODE);

                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=la.droid.qr.priva")));
                }



                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAPTURE_QRCODE) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("la.droid.qr.result")) {
                    String res = data.getExtras().getString("la.droid.qr.result");


                    // Passing to the main activity as a search query
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("sQuery", res);
                    startActivity(intent);

                }
            } else if (resultCode == RESULT_CANCELED) {
                // Capture cancelled
            } else {
                // Capture failed
            }
        }

    }


}
