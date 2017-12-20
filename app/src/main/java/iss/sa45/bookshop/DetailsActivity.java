package iss.sa45.bookshop;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.util.HashMap;

public class DetailsActivity extends Activity {

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
        return true;
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


}
