package com.apps.dcodertech.coursesurfer;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apps.dcodertech.coursesurfer.data.courseDB;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.List;

/**
 * Created by dhruv on 3/20/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {
    private Context context;
    private List<Courses> courses;
    courseDB courseDB;
    private int whichActivity;



    public Courses getItem(int position) {
        return courses.get(position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public RelativeLayout  viewForeground;
        public RelativeLayout viewBackground;
        public TextView title,author,company,provider,univ,certification,week,hours;
        public Button share, bookmark;
        public MyViewHolder(View view){

            super(view);

            courseDB=new courseDB(view.getContext());
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

            share=view.findViewById(R.id.share);
            bookmark=view.findViewById(R.id.bookmark);
            cardView=view.findViewById(R.id.cardView);
            title=view.findViewById(R.id.cardTitleView);
            author=view.findViewById(R.id.cardAuthorsView);
            company=view.findViewById(R.id.CardCourseCompanyView);
            provider=view.findViewById(R.id.CardCourseProvider);
            univ=view.findViewById(R.id.CardUniversityProvider);
            certification=view.findViewById(R.id.CardCertificationView);
            week=view.findViewById(R.id.cardweeksView);
            hours=view.findViewById(R.id.cardhoursView);

        }


    }
    public RecyclerViewAdapter(Context context, List<Courses> courses, int i){
        whichActivity=i;
        this.context=context;
        this.courses=courses;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_view, parent, false);
        //final MyViewHolder holder = new MyViewHolder(itemView);



        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Courses c = courses.get(position);
        //StyleableToast.makeText(context.getApplicationContext(),c.getCourse_institution(),R.style.mytoast).show();
        if(whichActivity==0){
            holder.bookmark.setVisibility(View.INVISIBLE);

        }

        holder.title.setText(c.getCourse_name());
        holder.author.setText(c.getCourse_prof());
        holder.company.setText(c.getCourse_subject());
        holder.provider.setText(c.getCourse_provider());
        holder.univ.setText(c.getCourse_institution());
        holder.certification.setText(c.getCourse_certifications());
        holder.week.setText(c.getCourse_duration());
        holder.hours.setText(c.getCourse_hours());
        /*holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseDB.deleteData(c.getCourse_name());

                StyleableToast.makeText(context,"Bookmark removed!",R.style.mytoast).show();


                //notifyItemRemoved(position);
            }
        });*/
        holder.bookmark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //StyleableToast.makeText(context.getApplicationContext(),c.getCourse_institution(),R.style.mytoast).show();

                Courses cc=new Courses();
                cc.setCourse_name(c.getCourse_name());
                cc.setCourse_prof(c.getCourse_prof());
                cc.setCourse_subject(c.getCourse_subject());
                cc.setCourse_provider(c.getCourse_provider());
                cc.setCourse_institution(c.getCourse_institution());
                cc.setCourse_certifications(c.getCourse_certifications());
                cc.setCourse_duration(c.getCourse_duration());
                cc.setCourse_hours(c.getCourse_hours());
                courseDB.insert(cc);
                    StyleableToast.makeText(context.getApplicationContext(),"Course Bookmarked!",R.style.mytoast).show();


                //StyleableToast.makeText(view.getContext(),c.getCourse_institution(),R.style.mytoast).show();
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=c.getCourse_link();
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "CourseSurfer");

                intent.putExtra(android.content.Intent.EXTRA_TEXT, url+"\n For more courses use CourseSurfer!");
                context.startActivity(Intent.createChooser(intent, "Share via"));



            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = c.getCourse_link();
                Intent intent=new Intent(view.getContext(),webView.class);
                intent.putExtra("webLink",url);
                intent.putExtra("method",c);
                context.startActivity(intent);
            }
        });
          }
    @Override
    public int getItemCount() {
        return courses.size();
    }
    public void removeItem(int position) {
        courses.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }
}



