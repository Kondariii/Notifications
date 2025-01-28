package com.example.myapplication;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "default_channel";
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request notification permission for Android 13 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Handle NotificationChannel creation for Android 8 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "Channel for default notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;  // High importance
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
            Log.d("MainActivity", "Notification channel created");
        }

        // Set up the button to trigger a notification when clicked
        Button notifyButton = findViewById(R.id.notifyButton);
        notifyButton.setOnClickListener(v -> sendNotification());

        // Set up the long notification button
        Button longNotifyButton = findViewById(R.id.LongnotifyButton);
        longNotifyButton.setOnClickListener(v -> sendLongNotification());
    }

    private void sendNotification() {
        try {
            Log.d("MainActivity", "Notification is about to be sent");

            // Build and send the notification with a default system icon
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)  // Default system icon
                    .setContentTitle("Button Clicked!")
                    .setContentText("This is a notification triggered by a button click.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)  // Dismiss after clicking
                    .build();

            notificationManager.notify(1, notification);  // Notification ID is 1

        } catch (Exception e) {
            e.printStackTrace();  // Catch any unexpected exceptions and log them
            Log.e("MainActivity", "Error sending notification", e);  // Error log
        }
    }

    private void sendLongNotification() {
        try {
            Log.d("MainActivity", "Long Notification is about to be sent");

            // Build and send the notification with a default system icon
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)  // Default system icon
                    .setContentTitle("Long Button Clicked!")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum et nisi justo. Quisque augue mi, elementum ut sapien in, vestibulum sodales ipsum. Phasellus diam neque, efficitur sit amet tincidunt id, mollis non mi. Nunc vulputate libero vel magna euismod, vel auctor leo rhoncus. Aliquam ut semper turpis. Pellentesque cursus odio nec magna aliquet, id pellentesque orci suscipit. Duis in est nec sapien euismod egestas. Maecenas volutpat dui eget sem vehicula, a dapibus justo viverra. Sed pulvinar purus quis turpis aliquam maximus. Mauris ac vulputate mi. Maecenas laoreet fringilla sollicitudin. Suspendisse a erat nec ipsum varius congue id sit amet felis. Phasellus interdum massa libero, sed eleifend felis interdum vitae. Etiam volutpat neque hendrerit enim consectetur, eget pharetra massa ornare. Cras malesuada arcu et neque laoreet efficitur. Fusce turpis odio, lacinia et dignissim ac, interdum nec purus.."))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)  // Dismiss after clicking
                    .build();

            notificationManager.notify(2, notification);  // Notification ID is 2 for the long notification

        } catch (Exception e) {
            e.printStackTrace();  // Catch any unexpected exceptions and log them
            Log.e("MainActivity", "Error sending long notification", e);  // Error log
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "Notification Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "Notification Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
