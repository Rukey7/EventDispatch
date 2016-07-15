package com.dl7.eventdispatch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.dl7.eventdispatch.entity.EventMsg;
import com.dl7.eventdispatch.views.MyView;
import com.dl7.eventdispatch.views.MyViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static String[] sChoiceItems = new String[] {
            "ViewGroup中断Event",
            "ViewGroup消费Event",
            "View消费Event",
            "ViewGroup启动Listener",
            "View启动Listener",
            "ViewGroup设置OnTouchListener消费",
            "View设置OnTouchListener消费"
    };
    private ListView mChoiceList = null;

    private MyViewGroup mViewGroup;
    private MyView mChildView;
    private AlertDialog multiDialog;
    private TextView mShowMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Event dispatch");
        setSupportActionBar(toolbar);
        mViewGroup = (MyViewGroup) findViewById(R.id.view_group);
        mChildView = (MyView) findViewById(R.id.view);
        mShowMsg = (TextView) findViewById(R.id.tv_desc);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_multi_choose) {
            showMultiDialog();
        } else if (item.getItemId() == R.id.menu_msg_clean) {
            mShowMsg.setText("");
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMultiDialog() {
        if (multiDialog == null) {
            AlertDialog.Builder multiBuilder = new AlertDialog.Builder(this).setTitle("复选对话框");
            // 设置复选列表
            multiBuilder.setMultiChoiceItems(sChoiceItems,
                    new boolean[]{false, false, false, false, false, false, false},
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
                        }
                    });
            multiBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    int listNum = mChoiceList.getCount(); // 列表个数
                    mViewGroup.setIntercept(false);
                    mViewGroup.setConsume(false);
                    mChildView.setConsume(false);
                    mViewGroup.setOpenListener(false);
                    mChildView.setOpenListener(false);
                    mViewGroup.setTouchConsume(false);
                    mChildView.setTouchConsume(false);

                    for (int i = 0; i < listNum; i++) {
                        // 检测哪几项被选中
                        if (mChoiceList.getCheckedItemPositions().get(i)) {
                            switch (i) {
                                case 0:
                                    mViewGroup.setIntercept(true);
                                    break;
                                case 1:
                                    mViewGroup.setConsume(true);
                                    break;
                                case 2:
                                    mChildView.setConsume(true);
                                    break;
                                case 3:
                                    mViewGroup.setOpenListener(true);
                                    break;
                                case 4:
                                    mChildView.setOpenListener(true);
                                    break;
                                case 5:
                                    mViewGroup.setTouchConsume(true);
                                    break;
                                case 6:
                                    mChildView.setTouchConsume(true);
                                    break;
                            }
                        }
                    }
                }
            });
            // 创建AlertDialog对象
            multiDialog = multiBuilder.create();
            // 获取对话框列表项
            mChoiceList = multiDialog.getListView();
        }
        multiDialog.show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMsg eventMsg) {
        mShowMsg.append(eventMsg.getMsg() + "\n");
    }
}
