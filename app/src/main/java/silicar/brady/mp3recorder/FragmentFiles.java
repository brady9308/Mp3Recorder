package silicar.brady.mp3recorder;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 文件浏览
 * Created by Brady on 2015/4/17.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentFiles extends Fragment {

    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.files_fragment,container,false);
        return view;
    }
}
