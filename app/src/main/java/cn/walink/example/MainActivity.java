package cn.walink.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.walink.clairvoyant.annotation.Mobile;
import cn.walink.clairvoyant.annotation.NotNull;
import cn.walink.clairvoyant.annotation.Pattern;
import cn.walink.clairvoyant.exception.ClairvoyantException;
import cn.walink.clairvoyant.listener.ClairvoyantListener;
import cn.walink.clairvoyant.trigger.Clairvoyant;

public class MainActivity extends AppCompatActivity implements ClairvoyantListener {

    /**
     * 非空
     */
    @NotNull(msg = "不能为空",seq = 1)
    EditText editNotEmpty;

    /**
     * 手机号码
     */
    @Mobile(seq=2)
    EditText editMobile;

    /**
     * 正则表达式
     */
    @Pattern(pattern = "\\d\\d", msg = "正则匹配错误",seq = 3)
    EditText editPattern;


    Button btnVerify;

    Clairvoyant clairvoyant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clairvoyant = new Clairvoyant(this, this);

        editNotEmpty = (EditText) findViewById(R.id.edit_not_null);
        editMobile = (EditText) findViewById(R.id.edit_mobile);
        editPattern = (EditText) findViewById(R.id.edit_pattern);

        btnVerify = (Button) findViewById(R.id.btn_verify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    clairvoyant.look();

                    Toast.makeText(MainActivity.this, "校验完毕，输入全部正确", Toast.LENGTH_SHORT).show();

                } catch (ClairvoyantException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void inputError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
