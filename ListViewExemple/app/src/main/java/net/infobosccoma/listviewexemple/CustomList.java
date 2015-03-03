package net.infobosccoma.listviewexemple;

/**
 * Created by PC on 06/02/2015.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] sensor;
    private final Integer[] imageId;
    public CustomList(Activity context,
                      String[] sensor, Integer[] imageId) {
        super(context, R.layout.list_single, sensor);
        this.context = context;
        this.sensor = sensor;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(sensor[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}