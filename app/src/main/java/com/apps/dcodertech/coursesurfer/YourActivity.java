package com.apps.dcodertech.coursesurfer;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class YourActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_your);
        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Course Search", "Click on the search icon to search course on any topic. Also click on the course for further details", R.drawable.tosend);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Bookmark course", "Click on the bookmark icon on the card to bookmark the course you like.", R.drawable.bookmark_caro);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Offline Bookmarks", "Click on the bookmark icon on the toolbar to see the already bookmark courses.", R.drawable.db_caro);
        AhoyOnboarderCard ahoyOnboarderCard4 = new AhoyOnboarderCard("Remove bookmarks", "Left swipe the course card to remove the bookmark.", R.drawable.del);

        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard4.setBackgroundColor(R.color.black_transparent);
        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);
        pages.add(ahoyOnboarderCard4);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.white);

            page.iconHeight=1000;
            page.iconWidth=700;
            page.setDescriptionColor(R.color.white);
        }

        setFinishButtonTitle("Get Started");
        showNavigationControls(false);
        setGradientBackground();

        //set the button style you created
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        }
       /* List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.solid_one);
        colorList.add(R.color.solid_two);
        colorList.add(R.color.solid_three);
        colorList.add(R.color.solid_one);
        setColorBackground(colorList);
*/
       // Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        //setFont(face);

        setOnboardPages(pages);


    }

    @Override
    public void onFinishButtonPressed() {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
