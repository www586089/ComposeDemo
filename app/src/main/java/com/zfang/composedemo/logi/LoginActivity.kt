package com.zfang.composedemo.logi

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.zfang.composedemo.R

class LoginActivity: AppCompatActivity() {
    private val TAG = "_LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LoginScreen() }
    }

    @Preview
    @Composable
    fun LastLoginScreen() {
        Column {
            Column(modifier = Modifier
                .fillMaxWidth()
                .paddingFrom(alignmentLine = FirstBaseline, before = 112.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "点击下方头像登陆",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Image(painter = painterResource(R.drawable.default_head_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(72.dp)
                        .height(72.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "小可爱在这里",
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }

            //底部区域
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    ClickableText(text = getAnnotateString("换个账号登录", fontSize = 12.sp, color = Color(0xFF666666))) {
                        Log.e(TAG, "xxxx")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Spacer(modifier = Modifier
                        .width(0.5.dp)
                        .height(18.dp)
                        .background(Color(0xff979797)))
                    Spacer(modifier = Modifier.width(4.dp))
                    ClickableText(text = getAnnotateString("遇到问题？", fontSize = 12.sp, color = Color(0xFF666666))) {
                        Log.e(TAG, "XXX问题")
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                val agree = remember {
                    mutableStateOf(false)
                }
                ConstraintLayout {
                    val (checkBox, spacer, text) = createRefs()

                    Checkbox(checked = agree.value, onCheckedChange = {
                        agree.value = it
                        Log.e(TAG, "agree = $agree")
                    }, modifier = Modifier.constrainAs(checkBox) {
                        start.linkTo(parent.start)
                        end.linkTo(spacer.start)
                    })
                    Spacer(modifier = Modifier
                        .width(6.dp)
                        .constrainAs(spacer) {
                            start.linkTo(checkBox.end)
                            end.linkTo(text.start)
                        }
                    )
                    val tagService = "serviceAgreement"
                    val tagPolicy = "privacyPolicy"
                    val annotatedString = getPolicyString(tagService = tagService, tagPolicy = tagPolicy)
                    ClickableText(
                        text = annotatedString,
                        modifier = Modifier.constrainAs(text) {
                        top.linkTo(checkBox.top)
                        bottom.linkTo(checkBox.bottom)
                        start.linkTo(spacer.end)
                    }) { offset ->
                        Log.e(TAG, "offset = $offset")
                        annotatedString.getStringAnnotations(tagService, start = offset, end = offset).firstOrNull()?.let {
                            //点击了服务协议
                            Log.e(TAG, "serviceAgreement = ${it.item}")
                        }
                        annotatedString.getStringAnnotations(tagPolicy, start = offset, end = offset).firstOrNull()?.let {
                            //点击了隐私政策
                            Log.e(TAG, "privacyPolicy = ${it.item}")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
    
    @Composable
    fun LoginScreen() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 120.dp)
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.mipmap.ic_launcher), contentDescription = null, 
                modifier = Modifier
                    .width(92.dp)
                    .height(92.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(100.dp))
            
            Column() {
                val loginStr = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 14.sp, color = Color.White)) {
                        append("手机号码登录")
                    }
                }
                val colors = listOf(Color(0xffBA90FF), Color(0xffFF86E3))
                TextButton(
                    onClick = {
                    },
                    modifier = Modifier
                        .width(260.dp)
                        .height(50.dp)
                        .background(brush = Brush.linearGradient(colors)),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(text = loginStr)
                }
            }
        }
    }

    private fun getAnnotateString(text: String, color: Color, fontSize: TextUnit) = buildAnnotatedString {
        withStyle(style = SpanStyle(color = color, fontSize = fontSize)) {
            append(text)
        }
    }

    private fun getPolicyString(tagService: String, tagPolicy: String) = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xff666666), fontSize = 10.sp)) {
            append("我已阅读并同意")
        }
        pushStringAnnotation(tagService, "https://developer.android.google.cn/jetpack/compose/text#get-click-position")
        withStyle(style = SpanStyle(color = Color(0xffFAC409), fontSize = 10.sp, textDecoration = TextDecoration.Underline)) {
            append("《用户服务协议》")
        }
        pop()

        withStyle(style = SpanStyle(color = Color(0xff666666), fontSize = 10.sp)) {
            append("和")
        }

        pushStringAnnotation(tagPolicy, "https://juejin.cn/post/6990660637257334821?utm_source=gold_browser_extension")
        withStyle(style = SpanStyle(color = Color(0xffFAC409), fontSize = 10.sp, textDecoration = TextDecoration.Underline)) {
            append("《隐私政策》")
        }
        pop()
    }
}