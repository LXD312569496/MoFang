package com.example.administrator.mofang.time;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mofang.R;
import com.example.administrator.mofang.common.DateUtil;
import com.example.administrator.mofang.common.DialogUtil;
import com.example.administrator.mofang.time.adapter.ScoreAdapter;
import com.example.administrator.mofang.time.adapter.SpinnerAdapter;
import com.example.administrator.mofang.time.greendao.DaoManager;
import com.example.administrator.mofang.time.greendao.Group;
import com.example.administrator.mofang.time.greendao.GroupDao;
import com.example.administrator.mofang.time.greendao.Score;
import com.example.administrator.mofang.time.greendao.ScoreDao;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

import static android.R.attr.resource;
import static com.example.administrator.mofang.time.greendao.DaoManager.getDao;

/**
 * Created by Administrator on 2017/2/22.
 */

public class ScoreFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    View rootView;
    @BindView(R.id.score_spinner)
    Spinner mSpinner;
    @BindView(R.id.score_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.score_bt_avg)
    TextView mBtAvg;

    private List<String> mSpinnerList;
    private SpinnerAdapter mSpinnerAdapter;

    private String mCurrentGroupName = "分组1";//当前分组的名字

    private List<Score> mScoreList;
    private ScoreAdapter mScoreAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_score, container, false);
        ButterKnife.bind(this, rootView);

        initView();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        initSpinner();

        mScoreList = new ArrayList<>();
        mScoreAdapter = new ScoreAdapter(mScoreList);
        mRecyclerView.setAdapter(mScoreAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mScoreAdapter.setOnDeleteListener(new ScoreAdapter.OnDeleteListener() {
            @Override
            public void onDelete(int positon, Score bean) {
                DaoManager.getScoreDao().delete(bean);
                mScoreList.remove(positon);
                mScoreAdapter.notifyDataSetChanged();
                getAvg();
            }
        });

        getData();


    }

    //从数据库中获取本分组的数据
    private void getData() {
        mScoreList.clear();
        mScoreList.addAll(DaoManager.getScoreDao().queryBuilder()
                .where(ScoreDao.Properties.Name.eq(mCurrentGroupName))
                .list());
        mScoreAdapter.notifyDataSetChanged();
        getAvg();
    }

    private void initSpinner() {
        mSpinnerList = new ArrayList<>();
        mSpinnerAdapter = new SpinnerAdapter(mSpinnerList);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);
        getGroup();
    }

    private void getGroup() {
        mSpinnerList.clear();
        List<Group> list = getDao(Group.class).queryBuilder().list();
        if (list.size() == 0) {
            mSpinnerList.add("分组1");
            mSpinnerList.add("分组2");
            mSpinnerList.add("分组3");
            mSpinnerList.add("分组4");
            mSpinnerList.add("分组5");
            mSpinnerAdapter.notifyDataSetChanged();
            getDao(Group.class).insert(new Group("分组1"));
            getDao(Group.class).insert(new Group("分组2"));
            getDao(Group.class).insert(new Group("分组3"));
            getDao(Group.class).insert(new Group("分组4"));
            getDao(Group.class).insert(new Group("分组5"));
        } else {
            List<Group> list1 = getDao(Group.class).queryBuilder().list();
            for (int i = 0; i < list1.size(); i++) {
                mSpinnerList.add(list1.get(i).getGroupName());
            }
            HashSet<String> set = new HashSet<String>(mSpinnerList); //此时已经去掉重复的数据保存在hashset中
            mSpinnerList.clear();
            mSpinnerList.addAll(set);
        }
        mSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mCurrentGroupName = mSpinnerList.get(i);
        getData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mCurrentGroupName = mSpinnerList.get(0);
    }

    //保存成绩到数据库中
    @Subscribe
    public void onEventMainThread(SaveTimeEvent event) {
        long score = event.getScore();
        String time = event.getTime();
        Score bean = new Score();
        bean.setName(mCurrentGroupName);
        bean.setTime(time);
        bean.setScore(score);
        bean.setScramble(event.getScramble());
        DaoManager.getScoreDao().insert(bean);
        mScoreList.add(bean);
        mScoreAdapter.notifyDataSetChanged();
        getAvg();
    }

    @OnClick({R.id.score_tv_add, R.id.score_tv_select, R.id.score_bt_avg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.score_tv_add:
                addGroup();
                break;
            case R.id.score_tv_select:
                select();
                break;
            case R.id.score_bt_avg:
                break;
        }
    }

    private void select() {
        final AlertDialog.Builder builder = DialogUtil.getBuilder(getActivity());
        String[] items = new String[]{"修改分组名字", "清空成绩", "删除该分组", "统计图表"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        changeGroup();
                        break;
                    case 1:
                        clearGroup();
                        break;
                    case 2:
                        deleteGroup();
                        break;
                    case 3:
                        showChartDialog(mScoreList);
                }
            }
        });
        builder.create().show();
    }

    //显示统计图表
    private void showChartDialog(List<Score> scoreList) {
        if (scoreList.size()==0){
            Toast.makeText(getActivity(),"列表成绩为空，无法产生图表",Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = DialogUtil.getBuilder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time_chart, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        Button btCancel= (Button) view.findViewById(R.id.time_bt_cancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        LineChart mLineChart= (LineChart) view.findViewById(R.id.time_line_chart);
        XAxis xAxis = mLineChart.getXAxis();
        mLineChart.setScaleEnabled(true); //设置图表是否可缩放
        //设置X轴的文字在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //模拟一个x轴的数据  12/1 12/2 ... 12/7
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < scoreList.size(); i++) {
            xValues.add("");
        }
        //模拟一组y轴数据(存放y轴数据的是一个Entry的ArrayList) 他是构建LineDataSet的参数之一
        ArrayList<Entry> yValue = new ArrayList<>();
        for (int i = 0; i < scoreList.size(); i++) {
            yValue.add(new Entry(i, (float) (mScoreList.get(i).getScore()*1.0/1000)));
        }
        //构建一个LineDataSet 代表一组Y轴数据 （比如不同的彩票： 七星彩  双色球）
        LineDataSet dataSet = new LineDataSet(yValue, "成绩(单位：秒)");

        //构建一个LineData  将dataSets放入
        LineData lineData = new LineData(dataSet);
        //将数据插入
        mLineChart.setData(lineData);
    }

    private void clearGroup() {
        getDao(Score.class).deleteInTx(
                getDao(Score.class).queryBuilder()
                        .where(ScoreDao.Properties.Name.eq(mCurrentGroupName))
                        .list()
        );
        getData();
    }

    private void deleteGroup() {
        AlertDialog.Builder builder = DialogUtil.getBuilder(getActivity());
        builder.setTitle("删除该分组");
        builder.setMessage("删除后，该组的数据将无法找回");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                List<Score> list = DaoManager.getScoreDao().queryBuilder().where(ScoreDao.Properties.Name.eq(mCurrentGroupName)).list();
                DaoManager.getScoreDao().deleteInTx(list);
                DaoManager.getDao(Group.class).delete(new Group(mCurrentGroupName));

//                mSpinnerList.remove(mCurrentGroupName);
//                mSpinnerAdapter.notifyDataSetChanged();
                getGroup();
                getData();
                dialogInterface.dismiss();

            }
        });

        builder.show();

    }

    private void changeGroup() {
        final AlertDialog.Builder builder = DialogUtil.getBuilder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_score_add, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final MaterialEditText etName = (MaterialEditText) view.findViewById(R.id.add_et_name);
        TextView tvTitle = (TextView) view.findViewById(R.id.add_tv_title);
        Button btCancel = (Button) view.findViewById(R.id.add_bt_cancel);
        Button btSure = (Button) view.findViewById(R.id.add_bt_sure);
        tvTitle.setText("修改分组名字");

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = etName.getText().toString();
                int size = getDao(Group.class).queryBuilder()
                        .where(GroupDao.Properties.GroupName.eq(newName))
                        .list()
                        .size();
                if (size != 0) {
                    etName.setError("该分组已经存在，请重新输入");
                } else {
                    //执行修改分组
//                    DaoManager.getDaoSession().getDatabase().rawQuery("update GROUP set group_name='"+newName+"' where group_name ='"+mCurrentGroupName+"'",null);
                    DaoManager.getDao(Group.class).delete(new Group(mCurrentGroupName));
                    DaoManager.getDao(Group.class).insert(new Group(newName));

//                   DaoManager.getDaoSession().getDatabase().rawQuery("update SCORE set NAME='" + newName + "' where NAME='" + mCurrentGroupName + "'", null);
                    List<Score> scoreList = DaoManager.getDao(Score.class).queryBuilder().where(ScoreDao.Properties.Name.eq(mCurrentGroupName)).list();
                    for (int i = 0; i < scoreList.size(); i++) {
                        Score score = scoreList.get(i);
                        score.setName(newName);
                        DaoManager.getDao(Score.class).update(score);
                    }

                    mSpinnerList.remove(mCurrentGroupName);
                    mSpinnerList.add(newName);
//                    mSpinnerList.set(mSpinnerList.indexOf(mCurrentGroupName),newName);
                    mSpinnerAdapter.notifyDataSetChanged();
                    mSpinner.setSelection(mSpinnerList.indexOf(newName));

                    Toast.makeText(getActivity(), "修改分组成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }

            }
        });

    }

    private void addGroup() {
        final AlertDialog.Builder builder = DialogUtil.getBuilder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_score_add, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final MaterialEditText etName = (MaterialEditText) view.findViewById(R.id.add_et_name);
        TextView tvTitle = (TextView) view.findViewById(R.id.add_tv_title);
        Button btCancel = (Button) view.findViewById(R.id.add_bt_cancel);
        Button btSure = (Button) view.findViewById(R.id.add_bt_sure);


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = etName.getText().toString();
                int size = getDao(Group.class).queryBuilder()
                        .where(GroupDao.Properties.GroupName.eq(newName))
                        .list()
                        .size();
                if (size != 0) {
                    etName.setError("该分组已经存在，请重新输入");
                } else {
                    //执行创建新的分组
                    getDao(Group.class).insert(new Group(newName));
                    mCurrentGroupName = newName;
                    mSpinnerList.add(newName);
                    mSpinnerAdapter.notifyDataSetChanged();
                    mSpinner.setSelection(mSpinnerList.size() - 1);
                    Toast.makeText(getActivity(), "创建分组成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });

    }

    //获取本组的平均数据
    private void getAvg() {
        List<Score> ALLlist = DaoManager.getDao(Score.class).queryBuilder()
                .where(ScoreDao.Properties.Name.eq(mCurrentGroupName))
                .list();

        List<Score> NotDNFList = DaoManager.getDao(Score.class).queryBuilder()
                .where(ScoreDao.Properties.Name.eq(mCurrentGroupName),
                        ScoreDao.Properties.Score.notEq(0))
                .list();
        int AllCount = ALLlist.size();
        int NotDnfCount = NotDNFList.size();//正常完成的次数

        if (AllCount == 0 || NotDnfCount == 0) {
            mBtAvg.setText("本组平均" + NotDnfCount + "/" + AllCount + ":  0.000");
            return;
        }

        long sum = 0;
        for (int i = 0; i < NotDNFList.size(); i++) {
            sum = sum + NotDNFList.get(i).getScore();
        }
        mBtAvg.setText("本组平均" + NotDnfCount + "/" + AllCount + ":  " + DateUtil.formatTime((long) (sum * 1.0 / NotDnfCount)));
    }


}
