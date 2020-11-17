package kz.kmg.qorgau.ui.main;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import javax.inject.Inject;

import dagger.android.DaggerActivity;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.local.SharedPrefStorage;
import kz.kmg.qorgau.ui.base.activity.BaseActivity;
import kz.kmg.qorgau.ui.create.CreateQorgayViewModel;



public class MainActivity extends BaseActivity {

    private static final String TAG="MainActivity";

    public LocalStorage prefStorage = new SharedPrefStorage(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        prefStorage.setNotificationToken(token);
                        Log.d(TAG, "Firebase Messaging token: " + token);
                    }
                });
    }

}