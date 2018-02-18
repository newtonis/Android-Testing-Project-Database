
package vote_app_test.com.test_library_import;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import vote_app_test.com.test_library_import.MainActivity;

import java.util.Map;
import java.io.Console;
import java.util.HashMap;
import java.util.Vector;

public class vote extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        Vector<Button> buttons = new Vector();

        buttons.add((Button)findViewById(R.id.button1));
        buttons.add((Button)findViewById(R.id.button2));
        buttons.add((Button)findViewById(R.id.button3));
        buttons.add((Button)findViewById(R.id.button4));


        mDatabase = FirebaseDatabase.getInstance().getReference();

        for (Integer i = 0;i < buttons.size();i++){
            final Integer i_static = i+1;
            buttons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ProgressDialog pd = new ProgressDialog(vote.this);
                    pd.setMessage("Loading ...");
                    pd.show();
                    //Integer last_value = mDatabase.child("encuesta").child("o1");

                    DatabaseReference ref = mDatabase.getRef();

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Long value = (Long)dataSnapshot.child("encuesta").child("o"+Integer.valueOf(i_static)).getValue();
                            mDatabase.child("encuesta").child("o"+String.valueOf(i_static)).setValue(value+1);
                            pd.dismiss();

                            //setContentView(R.layout.activity_main);
                            Intent intent = new Intent(vote.this,MainActivity.class);
                            intent.putExtra("selected",i_static-1);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            pd.dismiss();
                        }
                    });
                }
            });
        }
    }
}
