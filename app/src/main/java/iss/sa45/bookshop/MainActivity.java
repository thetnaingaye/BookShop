package iss.sa45.bookshop;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        if(intent.hasExtra("sQuery")){

            String sQuery= intent.getStringExtra("sQuery");

            Bundle args = new Bundle();
            args.putString("sQuery",sQuery);
            Fragment f = new ListingFragment();
            f.setArguments(args);
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment1, f)
                    .commit();


        }
        else if (intent.hasExtra("categoryId")) {

            String cat= intent.getStringExtra("categoryId");

            Bundle args = new Bundle();
            args.putString("categoryId",cat);
            Fragment f = new ListingFragment();
            f.setArguments(args);
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment1, f)
                    .commit();
        }else {
            Fragment f = new ListingFragment();
                    getFragmentManager().beginTransaction()
                    .add(R.id.fragment1, f)
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
              //  ls.DefaultView();
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
//                    // Putting our search query into a bundle
//                    Bundle tempB = new Bundle();
//                    tempB.putString("sQuery", query);
//
//                    // Retrieving our 'ListingFragment' activity in order to call
//                    // our SearchView() method inside it
//                    ListingFragment ls = (ListingFragment) getFragmentManager()
//                            .findFragmentById(R.id.fragment1);
//                   // ls.SearchView(tempB);


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

                final Dialog d = new Dialog(MainActivity.this);
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }


//
//    @Override
//    protected void onListItemClick(ListView l, View v,
//                                   int position, long id) {
//
//        BookItem item = (BookItem) getListAdapter().getItem(position);
//
//        Intent intent = new Intent(this,DetailsActivity.class);
//        intent.putExtra("item",item);
//        startActivity(intent);
//
////        final Dialog d = new Dialog(this);
////        d.setTitle(getString(R.string.customdialogtitle));
////        d.setContentView(R.layout.customedialog);
////        d.setCancelable(true);
////        TextView textAuthor = (TextView) d.findViewById(R.id.textAuthor);
////        textAuthor.setText("Author :"+ item.get("author"));
////
////        TextView textBookID = (TextView) d.findViewById(R.id.textBookID);
////        textBookID.setText("BookID :"+ item.get("bookId"));
////
////        TextView textCatID = (TextView) d.findViewById(R.id.textCatID);
////        textCatID.setText("CategoryID :"+ item.get("catId"));
////
////
////        TextView textISBN = (TextView) d.findViewById(R.id.textISBN);
////        textISBN.setText("ISBN :"+ item.get("isbn"));
////
////        TextView textPrice = (TextView) d.findViewById(R.id.textPrice);
////        textPrice.setText("Price :"+ item.get("price"));
////
////
////        TextView textStock = (TextView) d.findViewById(R.id.textStock);
////        textStock.setText("Stock :"+ item.get("stock"));
////
////        TextView textTitle = (TextView) d.findViewById(R.id.textTitle);
////        textTitle.setText("Title :"+ item.get("title"));
////
////        //ImageView img = (ImageView) d.findViewById(R.id.imgView);
////        String uri = BookItem.URI_BOOKIMAGE+item.get("isbn")+".jpg";
////
////        new AsyncTask<String, Void, Bitmap>() {
////            @Override
////            protected Bitmap doInBackground(String... params) {
////
////                return getImage(params[0]);
////            }
////
////            @Override
////            protected void onPostExecute(Bitmap resultImg){
////                ImageView img = (ImageView) d.findViewById(R.id.imgView);
////                img.setImageBitmap(resultImg);
////            }
////
////            protected Bitmap getImage(String url) {
////
////                Bitmap bitmap = null;
////                try {
////                    InputStream in = new java.net.URL(url).openStream();
////                    bitmap = BitmapFactory.decodeStream(in);
////                } catch (Exception e) {
////                    Log.e("MyApp", e.getMessage());
////                }
////                return bitmap;
////            }
////
////        }.execute(uri);
////
////
//////        Bitmap bitmap = getImage(uri);
//////        img.setImageBitmap(bitmap);
////
////
////        d.show();
//
//    }

}
