package iss.sa45.bookshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.HashMap;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Bundle b = getIntent().getExtras();
        HashMap<String,String> item = (HashMap<String, String>) b.get("item");

        TextView textAuthor = (TextView) findViewById(R.id.textAuthor);
        textAuthor.setText("Author :"+ item.get("author"));

        TextView textBookID = (TextView) findViewById(R.id.textBookID);
        textBookID.setText("BookID :"+ item.get("bookId"));

        TextView textCatID = (TextView) findViewById(R.id.textCatID);
        textCatID.setText("CategoryID :"+ item.get("catId"));


        TextView textISBN = (TextView) findViewById(R.id.textISBN);
        textISBN.setText("ISBN :"+ item.get("isbn"));

        TextView textPrice = (TextView) findViewById(R.id.textPrice);
        textPrice.setText("Price :"+ item.get("price"));


        TextView textStock = (TextView) findViewById(R.id.textStock);
        textStock.setText("Stock :"+ item.get("stock"));

        TextView textTitle = (TextView) findViewById(R.id.textTitle);
        textTitle.setText("Title :"+ item.get("title"));

        //ImageView img = (ImageView) d.findViewById(R.id.imgView);
        String uri = BookItem.URI_BOOKIMAGE+item.get("isbn")+".jpg";

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {

                return getImage(params[0]);
            }

            @Override
            protected void onPostExecute(Bitmap resultImg){
                ImageView img = (ImageView) findViewById(R.id.imgView);
                img.setImageBitmap(resultImg);
            }

            protected Bitmap getImage(String url) {

                Bitmap bitmap = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("MyApp", e.getMessage());
                }
                return bitmap;
            }

        }.execute(uri);



    }
}
