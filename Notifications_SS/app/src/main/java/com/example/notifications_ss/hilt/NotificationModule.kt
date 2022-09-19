package com.example.notifications_ss.hilt

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.NavDeepLinkDslBuilder
import androidx.navigation.Navigation
import com.example.notifications_ss.MainActivity
import com.example.notifications_ss.R
import com.example.notifications_ss.SecondFragment
import com.example.notifications_ss.broad_cast_reciever.MyReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    // im adding this here coz i think the issue might be here
    const val MY_URI = "example.com/notif/{comingFrom}"
    ////

    @Singleton
    @Provides
    @MainNotificationCompatBuilder
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {

        val intent = Intent(context, MyReceiver::class.java).apply {
            putExtra("MESSAGE", "Notification Action Field Clicked!")
        }
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            flag
        )

        /////
        // im adding this here coz  the issue might be here
        val clickNavigationIntent = Intent(Intent.ACTION_VIEW,
            "$MY_URI= Coming From Notif".toUri(), context,
            MainActivity::class.java)

        val intentPending = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.secondFragment)
            .createPendingIntent()


//        val clickedNavigationPendingIntent : PendingIntent =
//            TaskStackBuilder.create(context).run {
//                addNextIntentWithParentStack(clickNavigationIntent)
//                getPendingIntent(1, flag)!!
//            }
        ////

        return NotificationCompat.Builder(context, "Main Channel ID")
            .setContentTitle("Welcome")
            .setContentText("Learning Notification Demo")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)

            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            //set public version only applicable for visibility of private
            .setPublicVersion(
                NotificationCompat.Builder(context, "Main Channel ID")
                    .setContentTitle("Hidden")
                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setContentText("Unlock to see the message.")
                    .build()
            )
            .addAction(R.drawable.ic_baseline_library_add_check_24, "ACTION", pendingIntent)
            .setContentIntent(intentPending)
    }

    @Singleton
    @Provides
    @ProgressBarNotificationCompatBuilder

    fun provideProgressBarNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, "Progress Bar Channel ID")
            .setSmallIcon(R.drawable.ic_baseline_arrow_circle_down_24)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
    }

    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Main Channel ID",
                "Main Channel",
                NotificationManager.IMPORTANCE_LOW
            )

            val channel2 = NotificationChannel(
                "Progress Bar Channel ID",
                "Progress Bar Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(channel2)
        }
        return notificationManager
    }


}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainNotificationCompatBuilder

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProgressBarNotificationCompatBuilder