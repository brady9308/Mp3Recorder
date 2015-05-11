package silicar.brady.mp3recorder;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 录音中Fragment
 * Created by Brady on 2015/4/17.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentRecording extends Fragment
{
    private View view;
    private ImageView pause,stop;
    private boolean resume = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recording_fragment,container,false);
        findView();
        setOnClick();
        return view;
    }

    private void findView()
    {
        pause = (ImageView)view.findViewById(R.id.pause);
        stop = (ImageView)view.findViewById(R.id.stop);
    }

    private void setOnClick()
    {
        //暂停录音
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(getActivity() instanceof RecordPauseListener)
                {
                    if (resume)
                        pause.setImageResource(R.mipmap.pause);
                    else
                        pause.setImageResource(R.mipmap.resume);
                    resume = !resume;
                    ((RecordPauseListener) getActivity()).RecordPauseOnClick();
                }
            }
        });
        //停止录音
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(getActivity() instanceof  RecordStopListener)
                    ((RecordStopListener)getActivity()).RecordStopOnClick();
            }
        });
    }

    //设置暂停、停止录音监听接口
    public interface RecordPauseListener
    {
        void RecordPauseOnClick();
    }

    public interface RecordStopListener
    {
        void RecordStopOnClick();
    }
}
