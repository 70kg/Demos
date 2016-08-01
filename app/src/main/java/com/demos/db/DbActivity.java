package com.demos.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

import org.litepal.tablemanager.Connector;

import butterknife.Bind;

/**
 * Created by Mr_Wrong on 16/4/1.
 */
public class DbActivity extends ToolBarActivity {
    @Bind(R.id.tv_db)
    TextView tvDb;
    @Bind(R.id.btn_db)
    Button btnDb;

    @Override
    public int getLayout() {
        return R.layout.db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MySQLiteHelper helper = new MySQLiteHelper(this, "70kg.db", null, 1);
//        SQLiteDatabase db = helper.getWritableDatabase();

        SQLiteDatabase db = Connector.getDatabase();
    }
}
