package iss.sa45.bookshop;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UpdateFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.updatefragment, container, false);
        Bundle arg = getArguments();
        if (arg != null) {
            final HashMap<String,String> item = (HashMap<String,String>) arg.getSerializable("item");
            if (item != null) {

                TextView textTitle = (TextView) v.findViewById(R.id.textTitle);
                textTitle.setText(item.get("title"));

                TextView textAuthor = (TextView) v.findViewById(R.id.textAuthor);
                textAuthor.setText(item.get("author"));

                TextView textBookID = (TextView) v.findViewById(R.id.textBookID);
                textBookID.setText(item.get("bookId"));

                TextView textCatID = (TextView) v.findViewById(R.id.textCatID);
                textCatID.setText(item.get("catId"));


                TextView textISBN = (TextView) v.findViewById(R.id.textISBN);
                textISBN.setText(item.get("isbn"));

                EditText textPrice = (EditText) v.findViewById(R.id.textPrice);
                textPrice.setText(item.get("price"));


                EditText textStock = (EditText) v.findViewById(R.id.textStock);
                textStock.setText(item.get("stock"));


                //ImageView img = (ImageView) d.findViewById(R.id.imgView);
                String uri = BookItem.URI_BOOKIMAGE+item.get("isbn")+".jpg";

                new AsyncTask<String, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(String... params) {

                        return getImage(params[0]);
                    }

                    @Override
                    protected void onPostExecute(Bitmap resultImg){
                        ImageView img = (ImageView) v.findViewById(R.id.imgView);
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

                Button btnSave=(Button)v.findViewById(R.id.btnSave);
                Button btnCancel=(Button)v.findViewById(R.id.btnCancel);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String bookID=item.get("bookId");
                        String title=item.get("title");
                        String author=item.get("author");
                        String catId=item.get("catId");
                        String isbn=item.get("isbn");
                        String price=((EditText) v.findViewById(R.id.textPrice)).getText().toString();
                        String stock=((EditText) v.findViewById(R.id.textStock)).getText().toString();
                        BookItem bookItem = new BookItem(author, bookID, catId, isbn, price, stock, title);
                        new AsyncTask<BookItem, Void, Void>() {
                            @Override
                            protected Void doInBackground(BookItem... params) {
                                BookItem.updateBookItem(params[0]);
                                return null;
                            }
                            @Override
                            protected void onPostExecute(Void result) {
                                getActivity().finish();
                            }
                        }.execute(bookItem);

                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }


                });

                btnCancel.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        getActivity().finish();
                    }
                });

            }
        }
        return(v);
    }

    void display(BookItem details) {
        final String TAG = "DETAILS_FRAG";
        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();

        Fragment frag = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", details);
        frag.setArguments(args);
        if (fm.findFragmentByTag(TAG) == null)
            // fragment not found -- to be added
            trans.add(R.id.detailsframe, frag, TAG);
        else
            // fragment found -- to be replaced
            trans.replace(R.id.detailsframe, frag, TAG);
        trans.commit();

    }
}
