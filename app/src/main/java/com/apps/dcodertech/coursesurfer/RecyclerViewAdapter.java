package com.apps.dcodertech.coursesurfer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by dhruv on 3/20/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {
    private Context context;
    private List<Courses> courses;


    public Courses getItem(int position) {
        return courses.get(position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public TextView title,author,company,provider,univ,certification,week,hours;
        public MyViewHolder(View view){
            super(view);
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
    public RecyclerViewAdapter(Context context, List<Courses> courses){
        this.context=context;
        this.courses=courses;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_view, parent, false);
        final MyViewHolder holder = new MyViewHolder(itemView);



        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Courses c = courses.get(position);
        holder.title.setText(c.getCourse_name());
        holder.author.setText(c.getCourse_prof());
        holder.company.setText(c.getCourse_subject());
        holder.provider.setText(c.getCourse_provider());
        holder.univ.setText(c.getCourse_institution());
        holder.certification.setText(c.getCourse_certifications());
        holder.week.setText(c.getCourse_duration());
        holder.hours.setText(c.getCourse_hours());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = c.getCourse_link();
                Intent intent=new Intent(view.getContext(),webView.class);
                intent.putExtra("webLink",url);
                context.startActivity(intent);
            }
        });
          }
    @Override
    public int getItemCount() {
        return courses.size();
    }
}
