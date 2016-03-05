package utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imageprocessing.david.imagefilterapp.R;

/**
 * Created by David on 02/23/2016.
 */
public class ImageArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ImageArrayAdapter(Context context, String[] values) {
        super(context, R.layout.simple_list_item, values);
        this.context = context;
        this.values = values;
    }
}
