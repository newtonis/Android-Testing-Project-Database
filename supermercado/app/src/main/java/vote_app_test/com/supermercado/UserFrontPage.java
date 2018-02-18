package vote_app_test.com.supermercado;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Output;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.firebase.ui.auth.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Text;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;


public class UserFrontPage extends AppCompatActivity {
    FirebaseUser user;
    String token;
    DialogBuy buy;
    Vector <String> products;
    String selectedProduct;
    MyAdapter adapter;
    Boolean flag_hide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_front_page);
        flag_hide = true;
        buy = new DialogBuy();
        products = new Vector<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    token = task.getResult().getToken();
                } else {
                            // Handle error -> task.getException();
                }
            }
        });

        String username = user.getDisplayName();

        TextView text = (TextView)findViewById(R.id.userName);
        text.setText(username);

        final String uid = user.getUid();
        final TextView moneyText = (TextView)findViewById(R.id.moneyText);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading ...");
        pd.show();

        final ListView simpleList = (ListView)findViewById(R.id.listProducts);



        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.print("\"Hello\"");
                Long money = (Long)dataSnapshot.child("users_money").child(uid).getValue();
                moneyText.setText("$"+String.valueOf(money));

                Map<String,String> countryList = new HashMap<>();
                for (DataSnapshot postSnapshot: dataSnapshot.child("products").getChildren()) {
                    String post = postSnapshot.getKey();
                    Long price = (Long)postSnapshot.getValue();

                    countryList.put(post,"$"+String.valueOf(price));

                }
                adapter = new MyAdapter(countryList);
                simpleList.setAdapter(adapter);

                pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print("\"Database error\"");
            }

        });
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();*/

                buy.show(getFragmentManager(),"Buy?");
                selectedProduct = adapter.getItemName(position);
                flag_hide = false;
            }
        });
        buy.setClickListener(new DialogBuy.MyCustomObjectListener(){
            @Override
            public void onYesButton(){
                if (flag_hide) return;
                flag_hide = true;
                new Thread(){
                    public void run() {
                        try {
                            URL url = new URL("https://us-central1-supermercado-48792.cloudfunctions.net/helloWorld");
                            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                            connection.setRequestProperty("authorization", token);
                            connection.setRequestMethod("POST");
                            connection.setRequestProperty("product",selectedProduct);
                            InputStream in = connection.getInputStream();
                            IOUtils.copy(in, System.out);
                        } catch (MalformedURLException e) {

                        } catch (IOException e) {

                        }
                    }
                }.start();
            }
            @Override
            public void onNoButton(){

            }
        });
    }
}
