package com.blackdog.studentmanager.config;

/**
 * Created by 84412 on 2019/3/16.
 */

public interface Config {
   //bmob
   String BMOB_APP_ID = "";
   String SMS_TEMPLATE_NAME = "默认模板";                                      //Bmob短信的模板名字
   //sp
   String SP_CONFIG_NAME = "sp_blackdog";
   String SP_IS_LOGIN = "sp_is_login";

   int REQUEST_COUNT = 15;
}
