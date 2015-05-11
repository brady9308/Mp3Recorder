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
 * 音量显示Fragment
 * Created by Brady on 2015/4/17.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentVolume extends Fragment
{
    private View view;
    private Volume volume;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.volume_fragment,container,false);
        init();
        findView();
        return view;
    }

    private void init() {
    }

    private void findView()
    {
        volume = (Volume) view.findViewById(R.id.viewVolume);
    }

    //设置声音大小
    public void setVolume(int volume)
    {
        this.volume.setCurrentCount(volume);
    }
}
