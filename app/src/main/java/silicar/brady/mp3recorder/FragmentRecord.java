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
 * 录音开始Fragment
 * Created by Brady on 2015/4/17.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentRecord extends Fragment
{
    private View view;
    private RecordQuality recordQuality;
    private ImageView left,right,record;
    private int quality;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.record_fragment,container,false);
        init();
        findView();
        seOnClick();
        return view;
    }

    private void init()
    {
        quality = 3;
    }

    private void findView()
    {
        recordQuality = (RecordQuality)view.findViewById(R.id.viewQuality);
        recordQuality.setSelect(quality);
        left = (ImageView)view.findViewById(R.id.left);
        right = (ImageView)view.findViewById(R.id.right);
        record = (ImageView)view.findViewById(R.id.record);
    }

    //获得录音质量值
    public int getQuality() {
        return quality;
    }

    //设置录音开始监听事件回调接口
    public interface RecordOnClickListener
    {
        void RecordOnClick();
    }

    private void seOnClick()
    {
        //录音质量-
        left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(quality>1)
                    quality--;
                recordQuality.setSelect(quality);
            }
        });
        //录音质量+
        right.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(quality<10)
                    quality++;
                recordQuality.setSelect(quality);
            }
        });
        //开始录音
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(getActivity() instanceof RecordOnClickListener)
                    ((RecordOnClickListener)getActivity()).RecordOnClick();
            }
        });
    }
}
