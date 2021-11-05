package com.fallgod.explore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fallgod.explore.ui.theme.SproutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            SproutTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }
            DefaultPreview()
        }
    }
}

data class Message(val title: String, val content: String)

private fun msgData(): List<Message> {
    val author = "fallgod compose"
    return listOf(
        Message(author, "我们开始更新啦"),
        Message(author, "为了给广大的读者一个更好的体验，从今天起，我们公众号决定陆续发一些其他作者的高质量文章"),
        Message(author, "每逢佳节倍思亲，从今天起，参加我们公众号活动的同学可以获得精美礼品一份！！"),
        Message(author, "荣华梦一场，功名纸半张，是非海波千丈，马蹄踏碎禁街霜，听几度头鸡唱"),
        Message(author, "唤归来，西湖山上野猿哀。二十年多少风流怪，花落花开。望云霄拜将台，袖星斗安邦策，破烟月迷魂寨。酸斋笑我，我笑酸斋"),
        Message(author, "伤心尽处露笑颜，醉里孤单写狂欢。两路殊途情何奈，三千弱水忧忘川。花开彼岸朦胧色，月过长空爽朗天。青鸟思飞无侧羽，重山万水亦徒然"),
        Message(author, "又到绿杨曾折处，不语垂鞭，踏遍清秋路。衰草连天无意绪，雁声远向萧关去。恨天涯行役苦，只恨西风，吹梦成今古。明日客程还几许，沾衣况是新寒雨"),
        Message(
            author, "莫笑农家腊酒浑，丰年留客足鸡豚。山重水复疑无路，柳暗花明又一村。箫鼓追随春社近，衣冠简朴古风存。从今若许闲乘月，拄杖无时夜叩门"
        )
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
    Text(text = "2Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SproutTheme(darkTheme = false) {
//        MessageCard(Message("This is a title", "This is body"))
        Conversation(messages = msgData())
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages.size) { idx ->
            MessageCard(msg = messages[idx])
        }
    }
}

/**
 * Composable 函数可以通过使用 remember 将本地状态存储在内存中，并跟踪传递给 mutableStateOf 的值的变化。当值被更新时，使用该状态的 Composable 函数（及其子函数）将被自动重新绘制。我们把这称为重组（recomposition）。

通过使用 Compose 的状态 API，如 remember 和 mutableStateOf，任何对状态的改变都会自动更新 UI。

可组合函数可能会像每一帧一样频繁地重新执行，例如在呈现动画时。可组合函数应快速执行，以避免在播放动画期间出现卡顿。如果您需要执行成本高昂的操作（例如从共享偏好设置读取数据），请在后台协程中执行，并将值结果作为参数传递给可组合函数。(类似于UI线程)

可组合函数可以按任何顺序执行
可组合函数可以并行运行
 */
@Composable
fun MessageCard(msg: Message) {
    // 创建一个能够检测卡片是否被展开的变量
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val surfaceColor by animateColorAsState(
        targetValue = if (isExpanded) Color(0xFFCCCCCC) else MaterialTheme.colors.surface
    )
    Surface(
        shape = MaterialTheme.shapes.medium, // 使用 MaterialTheme 自带的形状
        elevation = 5.dp,
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth(fraction = 1f)
            .clickable { // 添加一个新的Modifier拓展方法，让元素具有点击的效果
                // 点击事件的内容s
                isExpanded = !isExpanded
            },
        color = surfaceColor
    ) {
        Row(
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Image(
                painterResource(id = R.drawable.logo_green),
                contentDescription = "logo picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, shape = CircleShape) //添加圆形边框
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column {
                Text(
                    text = msg.title,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = msg.content,
                    style = MaterialTheme.typography.body2,
                    // 默认只显示一行
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    // Composable大小的动画效果
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }
}