package iss.sa45.bookshop;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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

        if (intent.hasExtra("categoryId")) {

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
        return true;
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

                        String catIndex = ""+(index+1);

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
