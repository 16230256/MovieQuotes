package ie.ul.deirdreshanahan.moviequotes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void handlesSignIn(View view) {
        Toast.makeText(this, "Sing in", Toast.LENGTH_LONG).show();
    }
    public void hanlesSingUp(View view){
        Toast.makeText(this, "Sing Up", Toast.LENGTH_LONG).show();
    }
}
