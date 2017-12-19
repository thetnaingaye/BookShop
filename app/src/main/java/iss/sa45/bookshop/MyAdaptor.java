package iss.sa45.bookshop;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import iss.sa45.bookshop.BookItem;
import iss.sa45.bookshop.MainActivity;
import iss.sa45.bookshop.R;

public class MyAdaptor extends ArrayAdapter<BookItem> {

    private List<BookItem> items;
    int resource;

    public MyAdaptor(Context context, int resource, List<BookItem> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);
        BookItem book = items.get(position);
        if (book != null) {
            TextView bTitle = (TextView) v.findViewById(R.id.textView1);
            bTitle.setText(book.get("title"));

            TextView bIsbn = (TextView) v.findViewById(R.id.textView2);
            bIsbn.setText((book.get("isbn")));

            ImageView image = (ImageView) v.findViewById(R.id.imgView);

            String uri = "http://172.17.248.45/BookShop/images/"+book.get("isbn")+".jpg";

            Bitmap bitmap = getImage(uri);
            image.setImageBitmap(bitmap);

        }
        return v;
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
}