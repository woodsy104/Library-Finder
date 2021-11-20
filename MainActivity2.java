package ie.ul.libraryfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;
import java.util.List;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
    public void OnClickSignIn(View view){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new  AuthUI.IdpConfig.EmailBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                System.out.println(
                        "Sign In Successful \n" +
                                "name = " + user.getDisplayName() + "\n" +
                                "email = " + user.getEmail() + "\n" +
                                "id = " + user.getUid());
            }else {
                if(response == null){
                    System.out.println("Sign In cancelled");
                    return;
                }
                if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK){
                    System.out.println("No internet connection");
                    return;
                }
            }
        }
    }
}