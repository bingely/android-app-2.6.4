package net.oschina.app.improve.detail.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import net.oschina.app.R;
import net.oschina.app.bean.User;
import net.oschina.app.improve.base.activities.BaseBackActivity;
import net.oschina.app.improve.user.activities.OtherUserHomeActivity;
import net.oschina.app.ui.MainActivity;
import net.oschina.app.util.UIHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * web url 计划 activity
 * Created by huanghaibin_dev
 * on 2016/7/11.
 */

public class SchemeUrlActivity extends BaseBackActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Intent intent = getIntent();
            if (intent != null) {
                //  oscapp://www.oschina.net/launch/app?type=main(&id=112213)
                String metaData = intent.getDataString();
                long id = 0;
                int type = 0;
                if (metaData != null) {
                    Pattern pattern = Pattern.compile("([^&?]*)");
                    Matcher matcher = pattern.matcher(metaData);
                    while (matcher.find()) {
                        String group = matcher.group();
                        if (!group.contains("www.oschina.net")) {
                            String[] param = group.split("=");
                            if (group.contains("type")) {
                                type = Integer.parseInt(param[1]);
                            } else if (group.contains("id")) {
                                id = Long.parseLong(param[1]);
                            }
                        }
                    }
                    startActivity(new Intent(this, MainActivity.class));
                    if (type == 20) {//跳转到用户中心
                        User user = new User();
                        user.setId((int) id);
                        OtherUserHomeActivity.show(this, user);
                    } else if (type != 0 || type != -1) {
                        UIHelper.showDetail(this, type, id, "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_scheme_url;
    }
}
